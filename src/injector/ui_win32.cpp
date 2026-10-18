/* ui_win32.cpp — the injector's native Windows front end.
 *
 * A re-creation of the Loader's interface (2ef23a8d.exe): Dear ImGui 1.91.9b
 * on imgui_impl_win32 + imgui_impl_dx11, an undecorated WS_POPUP window
 * ("VapeV4ControllerRewrite", 800x405, DWM glass), one background-coloured
 * ImGui window ("##menu", flags 0x20AB) and hand-drawn widgets — the Loader
 * uses no stock ImGui Button/Selectable, so neither do we.
 *
 * Screens (the state machine from the Loader, minus the login form):
 *   2  picker      "Select a Minecraft to use" + one card per target
 *   3  loading     progress bar while the injection runs
 *   4  cache       "Would you like to cache local files…?"  Yes / No
 *   5  finished    "Vape has finished loading" + "Close Window"
 *   6  error       "Error" + message + "Back"
 *
 * What the injection does is loader.c (order) + os_win32.c (the Loader's own
 * mechanism) + host_dll.c (the injected native host that loads the client).
 */
#define WIN32_LEAN_AND_MEAN
#define _WIN32_WINNT 0x0600
#include <windows.h>
#include <dwmapi.h>
#include <d3d11.h>
#include <stdio.h>
#include <string.h>
#include "imgui.h"
#include "backends/imgui_impl_win32.h"
#include "backends/imgui_impl_dx11.h"
#include "loader.h"
#include "os_win32.h"

extern IMGUI_IMPL_API LRESULT ImGui_ImplWin32_WndProcHandler(HWND, UINT, WPARAM, LPARAM);

/* --- branding ------------------------------------------------------------ */
static const char *const BRAND_NAME = "Skid Solutions";

/* --- window -------------------------------------------------------------- */
static const wchar_t *const WINDOW_CLASS = L"VapeV4ControllerRewrite";
static const float WIN_W = 800.0f;
static const float WIN_H = 405.0f;
static const float CAP_MARGIN = 16.0f;   /* capsule controls, top-right */
static const float CAP_W = 37.0f, CAP_H = 28.0f, CAP_R = 14.0f;
static const float CAP_GAP = 3.0f;

/* --- fonts ---------------------------------------------------------------
 * The Loader embeds a TTF (RCDATA 111) at 20 / 18 / 16.5 px. That resource is
 * not recoverable from the binary, so the same sizes are loaded from the
 * system UI font and the ImGui default is the fallback. */
static ImFont *g_f20;
static ImFont *g_f18;

/* --- colours (from the Loader's constants) ------------------------------- */
static ImU32 col_bg(void)     { return IM_COL32(20, 20, 20, 255); }        /* #141414 */
static ImU32 col_border(void) { return IM_COL32(33, 32, 35, 255); }        /* #212023 */
static ImU32 col_body(void)   { return IM_COL32(145, 143, 145, 255); }     /* #918F91 */
static ImU32 col_head(void)   { return IM_COL32(135, 133, 135, 255); }     /* #878587 */
static ImU32 col_dim(void)    { return IM_COL32(92, 92, 92, 255); }        /* #5C5C5C */
static ImU32 col_off(void)    { return IM_COL32(84, 84, 84, 255); }        /* #545454 */
static ImU32 col_red(void)    { return IM_COL32(255, 51, 51, 255); }       /* #FF3333 */
static ImU32 col_pill(void)   { return IM_COL32(30, 29, 30, 255); }        /* #1E1D1E */
static ImU32 col_pill_hov(void){ return IM_COL32(45, 44, 45, 255); }       /* #2D2C2D */
static ImU32 col_glyph(void)  { return IM_COL32(122, 122, 122, 255); }     /* #7A7A7A */
static ImU32 col_glyph_hov(void){ return IM_COL32(214, 214, 214, 255); }   /* #D6D6D6 */
static ImU32 col_row(void)    { return IM_COL32(31, 30, 31, 255); }        /* #1F1E1F */
static ImU32 col_row_hov(void){ return IM_COL32(42, 41, 42, 255); }        /* #2A292A */
static ImU32 col_row_brd(void){ return IM_COL32(44, 43, 44, 255); }        /* #2C2B2C */
static ImU32 col_row_txt(void){ return IM_COL32(226, 225, 226, 255); }     /* #E2E1E2 */
static ImU32 col_row_off(void){ return IM_COL32(160, 160, 160, 255); }     /* #A0A0A0 */
static ImU32 col_row_sub(void){ return IM_COL32(110, 109, 110, 255); }     /* #6E6D6E */
static ImU32 col_row_red(void){ return IM_COL32(209, 51, 53, 255); }       /* #D13335 */
static ImU32 col_teal(void)     { return IM_COL32(41, 163, 136, 230); }    /* #29A388 @90% */
static ImU32 col_teal_hov(void) { return IM_COL32(60, 190, 159, 255); }    /* #3CBE9F */
static ImU32 col_teal_brd(void) { return IM_COL32(0, 186, 116, 255); }     /* #00BA74 */
static ImU32 col_neutral(void)  { return IM_COL32(45, 44, 45, 128); }      /* #2D2C2D @50% */
static ImU32 col_neutral_brd(void){ return IM_COL32(51, 51, 51, 255); }    /* #333333 */
static ImU32 col_track(void)  { return IM_COL32(31, 32, 32, 255); }        /* #1F2020 */
static ImU32 col_fill(void)   { return IM_COL32(5, 139, 111, 255); }       /* #058B6F */

/* --- ui state ------------------------------------------------------------ */
enum { ST_PICKER = 2, ST_LOADING = 3, ST_CACHE = 4, ST_DONE = 5, ST_ERROR = 6 };

typedef struct {
    int      state;
    target_t targets[16];
    int      ntargets;
    DWORD    last_scan;
    DWORD    t_start;
    int      selected_pid;
    char     error[256];
    volatile LONG busy;      /* worker running  */
    volatile LONG finished;  /* worker finished */
    int      ok;
    char     result[256];
    float    hover[2];       /* capsule hover lerp */
} ui_t;

static ui_t        g_ui;
static HWND        g_wnd;
static ID3D11Device           *g_dev;
static ID3D11DeviceContext    *g_ctx;
static IDXGISwapChain         *g_swap;
static ID3D11RenderTargetView *g_rtv;

/* --- small helpers ------------------------------------------------------- */
static ImVec4 mix4(const ImVec4 &a, const ImVec4 &b, float t)
{
    return ImVec4(a.x + (b.x - a.x) * t, a.y + (b.y - a.y) * t,
                  a.z + (b.z - a.z) * t, a.w + (b.w - a.w) * t);
}
static float lerp(float a, float b, float t) { return a + (b - a) * t; }

static void text_at(ImDrawList *dl, ImFont *font, float size, ImVec2 pos,
                    ImU32 col, const char *s)
{
    dl->AddText(font, size, pos, col, s);
}
static void text_centered(ImDrawList *dl, ImFont *font, float size, float cx,
                          float y, ImU32 col, const char *s)
{
    ImVec2 sz = font->CalcTextSizeA(size, FLT_MAX, 0.0f, s);
    dl->AddText(font, size, ImVec2(cx - sz.x * 0.5f, y), col, s);
}

/* --- the loader's custom button -----------------------------------------
 * Rounded rect, hover-lerped fill/border, centred label; two schemes. */
static float g_btn_hover = 0.0f;
static bool draw_button(const char *id, const char *label, ImVec2 pos,
                        ImVec2 size, bool primary, bool center)
{
    ImDrawList *dl = ImGui::GetWindowDrawList();
    ImVec2 mx(pos.x + size.x, pos.y + size.y);
    bool hov, pressed;

    ImGui::SetCursorScreenPos(pos);
    ImGui::InvisibleButton(id, size);
    hov = ImGui::IsItemHovered();
    pressed = ImGui::IsItemClicked();
    g_btn_hover = lerp(g_btn_hover, hov ? 1.0f : 0.0f, 0.12f);

    ImU32 fill, brd, txt;
    if (primary) {
        fill = ImGui::ColorConvertFloat4ToU32(
            mix4(ImGui::ColorConvertU32ToFloat4(col_teal()),
                 ImGui::ColorConvertU32ToFloat4(col_teal_hov()), g_btn_hover));
        brd = col_teal_brd();
        txt = IM_COL32(255, 255, 255, 255);
    } else {
        fill = col_neutral();
        brd = ImGui::ColorConvertFloat4ToU32(
            mix4(ImGui::ColorConvertU32ToFloat4(col_neutral_brd()),
                 ImVec4(0.25f, 0.25f, 0.25f, 1.0f), g_btn_hover));
        txt = ImGui::ColorConvertFloat4ToU32(
            mix4(ImVec4(0.557f, 0.553f, 0.557f, 1.0f),
                 ImVec4(0.824f, 0.824f, 0.824f, 1.0f), g_btn_hover));
    }
    dl->AddRectFilled(pos, mx, fill, 5.0f, 0);
    dl->AddRect(pos, mx, brd, 5.0f, 0, 1.2f);
    ImVec2 sz = g_f18->CalcTextSizeA(18.0f, FLT_MAX, 0.0f, label);
    ImVec2 p(center ? pos.x + (size.x - sz.x) * 0.5f : pos.x + 12.0f,
             pos.y + (size.y - sz.y) * 0.5f);
    text_at(dl, g_f18, 18.0f, p, txt, label);
    return pressed;
}

/* --- the picker's 205x60 row -------------------------------------------- */
static bool draw_row(ImVec2 pos, const char *title, const char *sub, bool injected)
{
    ImDrawList *dl = ImGui::GetWindowDrawList();
    const float w = 205.0f, h = 60.0f;
    ImVec2 mx(pos.x + w, pos.y + h);
    char id[64];
    bool clicked;
    snprintf(id, sizeof id, "##row%u", (unsigned)(pos.y * 7.0f));
    ImGui::SetCursorScreenPos(pos);
    ImGui::InvisibleButton(id, ImVec2(w, h));
    clicked = ImGui::IsItemClicked();
    bool hov = ImGui::IsItemHovered();

    dl->AddRectFilled(pos, mx, hov ? col_row_hov() : col_row(), 6.0f, 0);
    dl->AddRect(pos, mx, hov ? col_row_hov() : col_row_brd(), 6.0f, 0, 1.0f);
    text_at(dl, g_f18, 18.0f, ImVec2(pos.x + 8, pos.y + 11),
            injected ? col_row_off() : col_row_txt(), title);
    text_at(dl, g_f18, 16.5f, ImVec2(pos.x + 8, pos.y + 34),
            injected ? col_row_red() : col_row_sub(), sub);
    return clicked;
}

/* --- capsule controls (##capsule_minimize / ##capsule_close) ------------- */
static void capsule_controls(ImVec2 origin)
{
    ImDrawList *dl = ImGui::GetWindowDrawList();
    float x0 = origin.x + WIN_W - CAP_MARGIN - (CAP_W * 2.0f + CAP_GAP);
    float y0 = origin.y + CAP_MARGIN;
    const char *ids[2] = {"##capsule_minimize", "##capsule_close"};
    const char *glyph[2] = {"min", "close"};

    for (int i = 0; i < 2; i++) {
        ImVec2 mn(x0 + (float)i * (CAP_W + CAP_GAP), y0);
        ImVec2 mx(mn.x + CAP_W, mn.y + CAP_H);
        ImGui::SetCursorScreenPos(mn);
        ImGui::InvisibleButton(ids[i], ImVec2(CAP_W, CAP_H));
        bool hov = ImGui::IsItemHovered();
        g_ui.hover[i] = lerp(g_ui.hover[i], hov ? 1.0f : 0.0f, 0.12f);

        ImU32 fill = ImGui::ColorConvertFloat4ToU32(
            mix4(ImGui::ColorConvertU32ToFloat4(col_pill()),
                 ImGui::ColorConvertU32ToFloat4(col_pill_hov()), g_ui.hover[i]));
        ImU32 gly = ImGui::ColorConvertFloat4ToU32(
            mix4(ImGui::ColorConvertU32ToFloat4(col_glyph()),
                 ImGui::ColorConvertU32ToFloat4(col_glyph_hov()), g_ui.hover[i]));
        dl->AddRectFilled(mn, mx, fill, CAP_R, 0);

        float cx = (mn.x + mx.x) * 0.5f, cy = (mn.y + mx.y) * 0.5f;
        if (glyph[i][0] == 'm') {
            dl->AddLine(ImVec2(cx - 4.5f, cy), ImVec2(cx + 4.5f, cy), gly, 2.2f);
        } else {
            dl->AddLine(ImVec2(cx - 3.5f, cy - 3.5f), ImVec2(cx + 3.5f, cy + 3.5f), gly, 2.2f);
            dl->AddLine(ImVec2(cx + 3.5f, cy - 3.5f), ImVec2(cx - 3.5f, cy + 3.5f), gly, 2.2f);
        }
        if (ImGui::IsItemClicked()) {
            if (glyph[i][0] == 'm')
                ShowWindow(g_wnd, SW_MINIMIZE);
            else
                PostMessageW(g_wnd, WM_CLOSE, 0, 0);
        }
    }
}

/* --- progress bar ------------------------------------------------------- */
static void draw_progress(ImVec2 origin, float frac)
{
    ImDrawList *dl = ImGui::GetWindowDrawList();
    const float w = 290.0f, h = 8.0f;
    ImVec2 p(origin.x + (WIN_W - w) * 0.5f, origin.y + 312.0f);
    ImVec2 mx(p.x + w, p.y + h);
    if (frac < 0.0f) frac = 0.0f;
    if (frac > 1.0f) frac = 1.0f;
    dl->AddRectFilled(p, mx, col_track(), 4.0f, 0);
    if (frac > 0.0f)
        dl->AddRectFilled(p, ImVec2(p.x + w * frac, mx.y), col_fill(), 4.0f, 0);
}

/* --- the injection worker -----------------------------------------------
 * Reads this image's bundle, stages the host DLL and the client next to the
 * executable, then runs the Loader pipeline against the real backend. */
static uint8_t *read_self(size_t *n)
{
    char path[MAX_PATH];
    FILE *f;
    uint8_t *b;
    long len;
    if (!GetModuleFileNameA(NULL, path, MAX_PATH))
        return NULL;
    f = fopen(path, "rb");
    if (!f)
        return NULL;
    if (fseek(f, 0, SEEK_END) || (len = ftell(f)) <= 0) {
        fclose(f);
        return NULL;
    }
    rewind(f);
    b = (uint8_t *)malloc((size_t)len);
    if (b && fread(b, 1, (size_t)len, f) != (size_t)len) {
        free(b);
        b = NULL;
    }
    fclose(f);
    if (b)
        *n = (size_t)len;
    return b;
}

static DWORD WINAPI inject_worker(LPVOID param)
{
    ui_t *ui = (ui_t *)param;
    uint8_t *image = NULL;
    size_t image_len = 0;
    const uint8_t *blob = NULL, *host = NULL, *client = NULL;
    size_t blob_len = 0, host_len = 0, client_len = 0;
    payload_t p;
    char dll_path[MAX_PATH * 4];
    const char *err = NULL;
    int rc = -1;

    memset(&p, 0, sizeof p);
    image = read_self(&image_len);
    if (!image) {
        snprintf(ui->result, sizeof ui->result, "%s", "cannot read this image");
        goto out;
    }
    if (find_embedded_bundle(image, image_len, &blob, &blob_len)) {
        snprintf(ui->result, sizeof ui->result, "%s", "this image carries no payload");
        goto out;
    }
    if (bundle_extract(blob, blob_len, HOST_DLL_ENTRY, &host, &host_len)) {
        snprintf(ui->result, sizeof ui->result, "%s missing", HOST_DLL_ENTRY);
        goto out;
    }
    if (bundle_extract(blob, blob_len, PAYLOAD_ENTRY, &client, &client_len)) {
        snprintf(ui->result, sizeof ui->result, "%s missing", PAYLOAD_ENTRY);
        goto out;
    }
    if (win_stage_next_to_self(HOST_DLL_ENTRY, host, host_len,
                               dll_path, sizeof dll_path)) {
        snprintf(ui->result, sizeof ui->result, "cannot stage %s", HOST_DLL_ENTRY);
        goto out;
    }
    {
        char jar_path[MAX_PATH * 4];
        if (win_stage_next_to_self(PAYLOAD_ENTRY, client, client_len,
                                   jar_path, sizeof jar_path)) {
            snprintf(ui->result, sizeof ui->result, "cannot stage %s", PAYLOAD_ENTRY);
            goto out;
        }
    }
    p.data = client;
    p.len = client_len;
    p.from_bundle = 1;

    rc = run_loader(&OS_WIN, &p, (uint32_t)ui->selected_pid, dll_path,
                    LOADER_BASE_URL, ZEUS_DEFAULT, 8091, &err);
    if (rc == 0)
        ui->result[0] = '\0';
    else
        snprintf(ui->result, sizeof ui->result, "%s", err ? err : "unknown error");
out:
    free(image);
    ui->ok = (rc == 0);
    InterlockedExchange(&ui->finished, 1);
    PostMessageW(g_wnd, WM_APP + 1, 0, 0);
    return 0;
}

static void start_inject(ui_t *ui)
{
    HANDLE t;
    ui->busy = 1;
    ui->finished = 0;
    ui->ok = 0;
    ui->result[0] = '\0';
    ui->t_start = GetTickCount();
    t = CreateThread(NULL, 0, inject_worker, ui, 0, NULL);
    if (t)
        CloseHandle(t);
    else {
        ui->busy = 0;
        ui->state = ST_ERROR;
        snprintf(ui->error, sizeof ui->error, "%s", "Failed to start Product DLL loading");
    }
}

/* --- screens ------------------------------------------------------------- */
static void screen_picker(ui_t *ui, ImVec2 origin)
{
    ImDrawList *dl = ImGui::GetWindowDrawList();
    if (ui->ntargets <= 0) {
        text_centered(dl, g_f18, 18.0f, origin.x + WIN_W * 0.5f, origin.y + 290.0f,
                      IM_COL32(107, 107, 107, 255), "No Minecraft found");
        text_centered(dl, g_f18, 16.5f, origin.x + WIN_W * 0.5f, origin.y + 312.0f,
                      col_off(), "Open Minecraft to continue");
        return;
    }
    text_centered(dl, g_f20, 20.0f, origin.x + WIN_W * 0.5f, origin.y + 165.0f,
                  col_head(), "Select a Minecraft to use");
    text_centered(dl, g_f18, 16.5f, origin.x + WIN_W * 0.5f, origin.y + 187.0f,
                  col_dim(), "Make sure game is fully loaded first");

    for (int i = 0; i < ui->ntargets; i++) {
        float y = origin.y + 239.0f + (float)i * 65.0f;
        char sub[64];
        if (y + 60.0f > origin.y + WIN_H - 8.0f)
            break;
        snprintf(sub, sizeof sub,
                 ui->targets[i].already ? "Already Injected [%lu]" : "PID %lu",
                 (unsigned long)ui->targets[i].pid);
        if (draw_row(ImVec2(origin.x + (WIN_W - 205.0f) * 0.5f, y),
                     ui->targets[i].name, sub, ui->targets[i].already != 0) &&
            !ui->targets[i].already) {
            ui->selected_pid = (int)ui->targets[i].pid;
            ui->state = ST_LOADING;
            start_inject(ui);
        }
    }
}

static void screen_loading(ui_t *ui, ImVec2 origin)
{
    DWORD elapsed = GetTickCount() - ui->t_start;
    float frac = (float)elapsed / 6000.0f;
    if (frac > 0.9f) frac = 0.9f;
    draw_progress(origin, frac);
    text_centered(ImGui::GetWindowDrawList(), g_f18, 18.0f,
                  origin.x + WIN_W * 0.5f, origin.y + 277.0f, col_body(),
                  "Loading");
}

static void screen_cache(ui_t *ui, ImVec2 origin)
{
    (void)ui;
    ImDrawList *dl = ImGui::GetWindowDrawList();
    text_centered(dl, g_f18, 18.0f, origin.x + WIN_W * 0.5f, origin.y + 277.0f,
                  col_body(), "Would you like to cache local files for faster loading time?");
    float x = origin.x + (WIN_W - 290.0f) * 0.5f;
    if (draw_button("##yes", "Yes", ImVec2(x, origin.y + 336.0f),
                    ImVec2(140, 44), true, false)) {
        g_ui.state = ST_DONE;
    }
    if (draw_button("##no", "No", ImVec2(x + 150.0f, origin.y + 336.0f),
                    ImVec2(140, 44), true, false)) {
        g_ui.state = ST_DONE;
    }
}

static void screen_done(ui_t *ui, ImVec2 origin)
{
    (void)ui;
    ImDrawList *dl = ImGui::GetWindowDrawList();
    text_centered(dl, g_f18, 18.0f, origin.x + WIN_W * 0.5f, origin.y + 277.0f,
                  col_body(), "Vape has finished loading");
    text_centered(dl, g_f18, 16.5f, origin.x + WIN_W * 0.5f, origin.y + 300.0f,
                  IM_COL32(102, 105, 102, 255),
                  "Press RIGHT SHIFT(Default) while in game to open the GUI");
    if (draw_button("##close", "Close Window",
                    ImVec2(origin.x + (WIN_W - 140.0f) * 0.5f, origin.y + 336.0f),
                    ImVec2(140, 44), true, false))
        PostMessageW(g_wnd, WM_CLOSE, 0, 0);
}

static void screen_error(ui_t *ui, ImVec2 origin)
{
    ImDrawList *dl = ImGui::GetWindowDrawList();
    char line[320];
    snprintf(line, sizeof line, "Error: %s", ui->error[0] ? ui->error : "unknown");
    text_centered(dl, g_f18, 18.0f, origin.x + WIN_W * 0.5f, origin.y + 277.0f,
                  col_red(), line);
    if (draw_button("##back", "Back",
                    ImVec2(origin.x + (WIN_W - 140.0f) * 0.5f, origin.y + 336.0f),
                    ImVec2(140, 44), true, false)) {
        ui->ntargets = win_list_targets(ui->targets, 16);
        ui->state = ST_PICKER;
    }
}

static void draw_ui(ui_t *ui)
{
    ImGui::SetNextWindowPos(ImVec2(0, 0), ImGuiCond_FirstUseEver);
    ImGui::SetNextWindowSize(ImVec2(WIN_W, WIN_H), ImGuiCond_Always);
    ImGuiWindowFlags flags = ImGuiWindowFlags_NoTitleBar | ImGuiWindowFlags_NoResize |
                             ImGuiWindowFlags_NoScrollbar | ImGuiWindowFlags_NoCollapse |
                             ImGuiWindowFlags_NoBackground | ImGuiWindowFlags_NoFocusOnAppearing;
    ImGui::PushStyleVar(ImGuiStyleVar_WindowPadding, ImVec2(0, 0));
    ImGui::Begin("##menu", NULL, flags);
    ImGui::PopStyleVar();

    ImVec2 origin = ImGui::GetWindowPos();

    /* background, drawn behind the window by its own draw list */
    ImDrawList *bg = ImGui::GetBackgroundDrawList();
    bg->AddRectFilled(ImVec2(origin.x, origin.y),
                      ImVec2(origin.x + WIN_W, origin.y + WIN_H), col_bg(), 5.0f, 0);
    bg->AddRect(ImVec2(origin.x, origin.y),
                ImVec2(origin.x + WIN_W, origin.y + WIN_H), col_border(), 5.0f, 0, 1.2f);

    capsule_controls(origin);

    switch (ui->state) {
    case ST_PICKER:  screen_picker(ui, origin);  break;
    case ST_LOADING: screen_loading(ui, origin); break;
    case ST_CACHE:   screen_cache(ui, origin);   break;
    case ST_DONE:    screen_done(ui, origin);    break;
    default:         screen_error(ui, origin);   break;
    }

    /* branding */
    ImDrawList *dl = ImGui::GetWindowDrawList();
    text_at(dl, g_f18, 16.5f, ImVec2(origin.x + 16.0f, origin.y + WIN_H - 28.0f),
            col_dim(), BRAND_NAME);
    (void)dl;

    /* the window is draggable: mirror its position onto the OS window */
    if (ImGui::IsWindowHovered() && ImGui::IsMouseDragging(ImGuiMouseButton_Left))
        SetWindowPos(g_wnd, NULL, (int)origin.x, (int)origin.y, 0, 0,
                     SWP_NOSIZE | SWP_NOZORDER);

    ImGui::End();
}

/* --- window / device ----------------------------------------------------- */

typedef struct {
    int   attribute;
    PVOID data;
    SIZE_T size;
} wincomp_t;

typedef struct {
    DWORD state;
    DWORD flags;
    DWORD color;
    DWORD id;
} accent_t;

/* ACCENT_DISABLED + flags 2, exactly what the Loader passes. */
static void set_accent(HWND hwnd)
{
    HMODULE u32 = GetModuleHandleW(L"user32.dll");
    typedef BOOL (WINAPI *pSetWindowCompositionAttribute)(HWND, wincomp_t *);
    pSetWindowCompositionAttribute fn;
    accent_t ac;
    wincomp_t data;

    if (!u32)
        return;
    fn = (pSetWindowCompositionAttribute)(void *)GetProcAddress(u32, "SetWindowCompositionAttribute");
    if (!fn)
        return;
    memset(&ac, 0, sizeof ac);
    ac.state = 0;
    ac.flags = 2;
    data.attribute = 0x13; /* WCA_ACCENT_POLICY */
    data.data = &ac;
    data.size = sizeof ac;
    fn(hwnd, &data);
}

static bool create_device(HWND hwnd)
{
    DXGI_SWAP_CHAIN_DESC sd;
    D3D_FEATURE_LEVEL lvl;
    ID3D11Texture2D *back;

    memset(&sd, 0, sizeof sd);
    sd.BufferCount = 2;
    sd.BufferDesc.Format = DXGI_FORMAT_R8G8B8A8_UNORM;
    sd.BufferUsage = DXGI_USAGE_RENDER_TARGET_OUTPUT;
    sd.OutputWindow = hwnd;
    sd.SampleDesc.Count = 1;
    sd.Windowed = TRUE;
    sd.SwapEffect = DXGI_SWAP_EFFECT_DISCARD;
    if (D3D11CreateDeviceAndSwapChain(NULL, D3D_DRIVER_TYPE_HARDWARE, NULL, 0, NULL, 0,
                                      D3D11_SDK_VERSION, &sd, &g_swap, &g_dev, &lvl,
                                      &g_ctx) != S_OK)
        return false;
    if (g_swap->GetBuffer(0, IID_PPV_ARGS(&back)) != S_OK || !back)
        return false;
    g_dev->CreateRenderTargetView(back, NULL, &g_rtv);
    back->Release();
    return g_rtv != NULL;
}

static void load_fonts(void)
{
    char win[MAX_PATH], path[MAX_PATH];
    ImFontConfig cfg = ImFontConfig();
    cfg.OversampleH = 2;
    cfg.OversampleV = 2;
    GetWindowsDirectoryA(win, MAX_PATH);
    snprintf(path, sizeof path, "%s\\Fonts\\segoeui.ttf", win);
    g_f20 = ImGui::GetIO().Fonts->AddFontFromFileTTF(path, 20.0f, &cfg);
    g_f18 = ImGui::GetIO().Fonts->AddFontFromFileTTF(path, 18.0f, &cfg);
    if (!g_f20 || !g_f18) {
        ImGui::GetIO().Fonts->AddFontDefault();
        g_f20 = g_f18 = ImGui::GetIO().Fonts->Fonts[0];
    }
}

static LRESULT CALLBACK wnd_proc(HWND hwnd, UINT msg, WPARAM wp, LPARAM lp)
{
    if (ImGui_ImplWin32_WndProcHandler(hwnd, msg, wp, lp))
        return 1;
    switch (msg) {
    case WM_APP + 1:
        if (g_ui.finished) {
            g_ui.busy = 0;
            if (g_ui.ok) {
                g_ui.state = ST_CACHE;
            } else {
                snprintf(g_ui.error, sizeof g_ui.error, "%s", g_ui.result);
                g_ui.state = ST_ERROR;
            }
        }
        return 0;
    case WM_SYSCOMMAND:
        if ((wp & 0xfff0) == SC_KEYMENU)
            return 0;
        break;
    case WM_DESTROY:
        PostQuitMessage(0);
        return 0;
    }
    return DefWindowProcW(hwnd, msg, wp, lp);
}

int WINAPI WinMain(HINSTANCE inst, HINSTANCE prev, LPSTR cmd, int show)
{
    WNDCLASSEXW wc;
    RECT r;
    MSG msg;
    (void)prev;
    (void)cmd;
    (void)show;

    wc.cbSize = sizeof wc;
    wc.style = CS_CLASSDC;
    wc.lpfnWndProc = wnd_proc;
    wc.cbClsExtra = 0;
    wc.cbWndExtra = 0;
    wc.hInstance = inst;
    wc.hIcon = LoadIconW(NULL, IDI_APPLICATION);
    wc.hCursor = LoadCursorW(NULL, IDC_ARROW);
    wc.hbrBackground = NULL;
    wc.lpszMenuName = NULL;
    wc.lpszClassName = WINDOW_CLASS;
    wc.hIconSm = NULL;
    if (!RegisterClassExW(&wc))
        return 1;

    r.left = 0; r.top = 0;
    r.right = (LONG)WIN_W; r.bottom = (LONG)WIN_H;
    g_wnd = CreateWindowExW(0, WINDOW_CLASS, L"", WS_POPUP,
                            (GetSystemMetrics(SM_CXSCREEN) - (int)WIN_W) / 2,
                            (GetSystemMetrics(SM_CYSCREEN) - (int)WIN_H) / 2,
                            (int)WIN_W, (int)WIN_H,
                            NULL, NULL, inst, NULL);
    if (!g_wnd)
        return 1;

    SetWindowLongPtrW(g_wnd, GWL_EXSTYLE,
                      GetWindowLongPtrW(g_wnd, GWL_EXSTYLE) | WS_EX_LAYERED);
    SetLayeredWindowAttributes(g_wnd, 0, 255, LWA_ALPHA);
    {
        MARGINS m = {-1, -1, -1, -1};
        DwmExtendFrameIntoClientArea(g_wnd, &m);
    }
    set_accent(g_wnd);

    if (!create_device(g_wnd))
        return 1;

    ShowWindow(g_wnd, SW_SHOW);
    UpdateWindow(g_wnd);

    IMGUI_CHECKVERSION();
    ImGui::CreateContext();
    ImGui::GetIO().IniFilename = "imgui.ini";
    load_fonts();
    ImGui_ImplWin32_Init(g_wnd);
    ImGui_ImplDX11_Init(g_dev, g_ctx);

    memset(&g_ui, 0, sizeof g_ui);
    g_ui.state = ST_PICKER;
    g_ui.ntargets = win_list_targets(g_ui.targets, 16);
    g_ui.last_scan = GetTickCount();

    const float clear[4] = {0.0f, 0.0f, 0.0f, 0.0f};
    bool running = true;
    while (running) {
        while (PeekMessageW(&msg, NULL, 0, 0, PM_REMOVE)) {
            TranslateMessage(&msg);
            DispatchMessageW(&msg);
            if (msg.message == WM_QUIT)
                running = false;
        }
        if (IsIconic(g_wnd)) {
            Sleep(10);
            continue;
        }
        /* rescan while the picker is up, so a game started later appears */
        if (g_ui.state == ST_PICKER && !g_ui.busy &&
            GetTickCount() - g_ui.last_scan > 1500) {
            g_ui.ntargets = win_list_targets(g_ui.targets, 16);
            g_ui.last_scan = GetTickCount();
        }

        ImGui_ImplDX11_NewFrame();
        ImGui_ImplWin32_NewFrame();
        ImGui::NewFrame();
        draw_ui(&g_ui);
        ImGui::Render();

        g_ctx->OMSetRenderTargets(1, &g_rtv, NULL);
        g_ctx->ClearRenderTargetView(g_rtv, clear);
        ImGui_ImplDX11_RenderDrawData(ImGui::GetDrawData());
        g_swap->Present(1, 0);
    }

    ImGui_ImplDX11_Shutdown();
    ImGui_ImplWin32_Shutdown();
    ImGui::DestroyContext();
    if (g_rtv) g_rtv->Release();
    if (g_swap) g_swap->Release();
    if (g_ctx) g_ctx->Release();
    if (g_dev) g_dev->Release();
    DestroyWindow(g_wnd);
    UnregisterClassW(WINDOW_CLASS, inst);
    return 0;
}

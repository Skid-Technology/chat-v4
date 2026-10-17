/* ui_win32.c — the injector's native Windows front end.
 *
 * One window, no UI toolkit: it opens with the Skid Solutions splash (a
 * native re-creation of the Java splash in java_agent/Skid.java) and then
 * hands over to the injector panel, which drives the injection pipeline in
 * loader.c against the payload embedded in this image.
 *
 * Build (only ever for Windows, via the repo Makefile):
 *   x86_64-w64-mingw32-gcc -std=c11 -O2 -mwindows -o injector.exe \
 *       loader.c os_stub.c sha256.c ui_win32.c -lm -lshell32
 */
#define WIN32_LEAN_AND_MEAN
#define _WIN32_WINNT 0x0600 /* SetArcDirection/AngleArc */
#include <windows.h>
#include <windowsx.h>
#include <shellapi.h> /* ShellExecuteA */
#include <math.h>
#include <stdarg.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "loader.h"

/* --- branding -------------------------------------------------------------- */
#define BRAND_NAME    "Skid Solutions"
#define BRAND_PRODUCT "Vape V4 injector"
#define BRAND_URL     "https://discord.gg/AEwatvnvSf"
#define SPLASH_URL    "https://discord.gg/5FB5hqJacr"
#define WINDOW_TITLE  BRAND_NAME " - Vape V4"

/* --- layout ---------------------------------------------------------------- */
#define WIN_W        440
#define WIN_H        270
#define BRAND_PTS    20
#define SMALL_PTS    11
#define BODY_PTS     12
#define ARC_CX       (WIN_W / 2)
#define ARC_CY       82
#define ARC_R        27
#define ARC_PEN      5
#define SPLASH_MS    4000
#define FADE_MS      600
#define SPLASH_TICK  16
#define ROT_STEP     6.5
#define LOG_MAX      6

#define ID_INJECT    1001
#define TIMER_SPLASH 1

enum { PHASE_SPLASH, PHASE_PANEL };

static HINSTANCE g_inst;
static HWND      g_wnd;
static HWND      g_btn;
static HFONT     g_f_brand;
static HFONT     g_f_small;
static HFONT     g_f_body;
static int       g_phase;
static DWORD     g_t0;
static double    g_angle;
static char      g_log[LOG_MAX][128];
static int       g_log_n;

/* --- small helpers --------------------------------------------------------- */

static HFONT font_of(int pts, int weight)
{
    HDC dc = GetDC(NULL);
    int h = -MulDiv(pts, GetDeviceCaps(dc, LOGPIXELSY), 72);
    ReleaseDC(NULL, dc);
    return CreateFontA(h, 0, 0, 0, weight, FALSE, FALSE, FALSE,
                       DEFAULT_CHARSET, OUT_TT_PRECIS, CLIP_DEFAULT_PRECIS,
                       CLEARTYPE_QUALITY, DEFAULT_PITCH | FF_DONTCARE, "Segoe UI");
}

static void log_add(const char *fmt, ...)
{
    va_list ap;
    if (g_log_n == LOG_MAX) {
        memmove(g_log[0], g_log[1], sizeof g_log - sizeof g_log[0]);
        g_log_n--;
    }
    va_start(ap, fmt);
    vsnprintf(g_log[g_log_n], sizeof g_log[0], fmt, ap);
    va_end(ap);
    if (g_log_n < LOG_MAX)
        g_log_n++;
}

/* One 90-degree stroke of the spinner, like Skid.java's drawArc. The pen is
 * moved to the arc's start point first so GDI's connector line has no length. */
static void arc(HDC dc, int cx, int cy, int r, double deg)
{
    const double pi = 3.14159265358979323846;
    double rad = deg * pi / 180.0;
    MoveToEx(dc, cx + (int)lround(r * cos(rad)), cy - (int)lround(r * sin(rad)), NULL);
    AngleArc(dc, cx, cy, r, (FLOAT)deg, 90.0f);
}

static int over_link(POINT p)
{
    return p.x >= 24 && p.x <= 260 && p.y >= 244 && p.y <= 262;
}

/* --- painting -------------------------------------------------------------- */

static void paint_splash(HDC dc)
{
    RECT r = {0, 0, WIN_W, WIN_H}, tx;
    HBRUSH bg = CreateSolidBrush(RGB(0, 0, 0));
    HPEN pen = CreatePen(PS_SOLID, ARC_PEN, RGB(255, 255, 255));
    HGDIOBJ ob, op;
    DWORD el = GetTickCount() - g_t0;
    int v = el >= FADE_MS ? 255 : (int)(el * 255 / FADE_MS);

    FillRect(dc, &r, bg);
    SetBkMode(dc, TRANSPARENT);
    ob = SelectObject(dc, bg);
    op = SelectObject(dc, pen);
    SetArcDirection(dc, AD_COUNTERCLOCKWISE);
    arc(dc, ARC_CX, ARC_CY, ARC_R, g_angle);
    arc(dc, ARC_CX, ARC_CY, ARC_R, g_angle + 180.0);
    SelectObject(dc, op);

    SetTextColor(dc, RGB(v, v, v));
    SelectObject(dc, g_f_brand);
    tx.left = 0;
    tx.right = WIN_W;
    tx.top = 139;
    tx.bottom = 139 + 40;
    DrawTextA(dc, BRAND_NAME, -1, &tx, DT_CENTER | DT_SINGLELINE | DT_NOCLIP);

    SetTextColor(dc, RGB(180, 180, 180));
    SelectObject(dc, g_f_small);
    tx.left = 14;
    tx.right = 300;
    tx.top = 242;
    tx.bottom = 262;
    DrawTextA(dc, SPLASH_URL, -1, &tx, DT_LEFT | DT_SINGLELINE | DT_NOCLIP);

    SelectObject(dc, ob);
    DeleteObject(bg);
    DeleteObject(pen);
}

static void paint_panel(HDC dc)
{
    RECT r = {0, 0, WIN_W, WIN_H}, tx;
    HBRUSH bg = CreateSolidBrush(RGB(12, 12, 14));
    HPEN line = CreatePen(PS_SOLID, 1, RGB(48, 48, 54));
    HGDIOBJ ob, op;
    int i;

    FillRect(dc, &r, bg);
    SetBkMode(dc, TRANSPARENT);

    SetTextColor(dc, RGB(255, 255, 255));
    SelectObject(dc, g_f_brand);
    tx.left = 24;
    tx.right = WIN_W - 24;
    tx.top = 20;
    tx.bottom = 60;
    DrawTextA(dc, BRAND_NAME, -1, &tx, DT_LEFT | DT_SINGLELINE | DT_NOCLIP);

    SetTextColor(dc, RGB(140, 140, 150));
    SelectObject(dc, g_f_small);
    tx.top = 50;
    tx.bottom = 70;
    DrawTextA(dc, BRAND_PRODUCT, -1, &tx, DT_LEFT | DT_SINGLELINE | DT_NOCLIP);

    ob = SelectObject(dc, bg);
    op = SelectObject(dc, line);
    MoveToEx(dc, 24, 76, NULL);
    LineTo(dc, WIN_W - 24, 76);
    SelectObject(dc, op);

    SetTextColor(dc, RGB(220, 220, 225));
    SelectObject(dc, g_f_body);
    for (i = 0; i < g_log_n; i++) {
        tx.left = 24;
        tx.right = WIN_W - 24;
        tx.top = 88 + i * 18;
        tx.bottom = tx.top + 18;
        DrawTextA(dc, g_log[i], -1, &tx, DT_LEFT | DT_SINGLELINE | DT_END_ELLIPSIS);
    }

    SetTextColor(dc, RGB(110, 150, 240));
    SelectObject(dc, g_f_small);
    tx.left = 24;
    tx.right = 300;
    tx.top = 242;
    tx.bottom = 262;
    DrawTextA(dc, BRAND_URL, -1, &tx, DT_LEFT | DT_SINGLELINE | DT_NOCLIP);

    SelectObject(dc, ob);
    DeleteObject(bg);
    DeleteObject(line);
}

/* --- the injection itself --------------------------------------------------
 * The logic is loader.c; the payload is the bundle appended to this image by
 * embed.py. The backend is the portable one from os_stub.c, so the run walks
 * the real pipeline against a simulated process. */

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
    b = malloc((size_t)len);
    if (b && fread(b, 1, (size_t)len, f) != (size_t)len) {
        free(b);
        b = NULL;
    }
    fclose(f);
    if (b)
        *n = (size_t)len;
    return b;
}

static void do_inject(void)
{
    uint8_t *image;
    size_t image_len = 0;
    const uint8_t *blob = NULL;
    size_t blob_len = 0;
    payload_t p;
    const char *err = NULL;
    int rc;

    EnableWindow(g_btn, FALSE);
    log_add("payload: %s", PAYLOAD_ENTRY);

    image = read_self(&image_len);
    if (!image) {
        log_add("error: cannot read this image");
        goto out;
    }
    if (find_embedded_bundle(image, image_len, &blob, &blob_len)) {
        log_add("error: this image carries no payload");
        goto out;
    }
    log_add("bundle: %zu bytes embedded", blob_len);

    memset(&p, 0, sizeof p);
    if (bundle_extract(blob, blob_len, PAYLOAD_ENTRY, &p.data, &p.len)) {
        log_add("error: %s missing or corrupt", PAYLOAD_ENTRY);
        goto out;
    }
    p.from_bundle = 1;
    log_add("payload: %zu bytes, sha256 ok", p.len);
    log_add("target: simulated process (portable backend)");

    rc = run_loader(&OS_STUB, &p, 1234, "C:\\load\\Vape421Native.dll",
                    ZEUS_DEFAULT, 5678, &err);
    if (rc == 0)
        log_add("pipeline: ok - validate, bootstrap, load, ack");
    else
        log_add("pipeline: failed - %s", err ? err : "unknown error");

out:
    free(image);
    EnableWindow(g_btn, TRUE);
    InvalidateRect(g_wnd, NULL, TRUE);
}

/* --- window plumbing ------------------------------------------------------- */

static void enter_panel(void)
{
    g_phase = PHASE_PANEL;
    KillTimer(g_wnd, TIMER_SPLASH);
    g_btn = CreateWindowExA(0, "BUTTON", "Inject",
                            WS_CHILD | WS_VISIBLE | WS_TABSTOP | BS_PUSHBUTTON,
                            300, 214, 116, 30, g_wnd, (HMENU)ID_INJECT,
                            g_inst, NULL);
    if (g_btn)
        SendMessageA(g_btn, WM_SETFONT,
                     (WPARAM)GetStockObject(DEFAULT_GUI_FONT), TRUE);
    log_add("Click Inject to load the bundled payload.");
    InvalidateRect(g_wnd, NULL, TRUE);
}

static LRESULT CALLBACK wnd_proc(HWND hwnd, UINT msg, WPARAM wp, LPARAM lp)
{
    switch (msg) {
    case WM_CREATE:
        g_wnd = hwnd;
        g_t0 = GetTickCount();
        SetTimer(hwnd, TIMER_SPLASH, SPLASH_TICK, NULL);
        return 0;

    case WM_TIMER:
        if (GetTickCount() - g_t0 >= SPLASH_MS) {
            enter_panel();
        } else {
            g_angle = fmod(g_angle + ROT_STEP, 360.0);
            InvalidateRect(hwnd, NULL, FALSE);
        }
        return 0;

    case WM_ERASEBKGND:
        return 1; /* fully painted in WM_PAINT */

    case WM_PAINT: {
        PAINTSTRUCT ps;
        HDC dc = BeginPaint(hwnd, &ps);
        if (g_phase == PHASE_SPLASH)
            paint_splash(dc);
        else
            paint_panel(dc);
        EndPaint(hwnd, &ps);
        return 0;
    }

    case WM_SETCURSOR: {
        POINT p;
        if (g_phase == PHASE_PANEL && GetCursorPos(&p) &&
            ScreenToClient(hwnd, &p) && over_link(p)) {
            SetCursor(LoadCursor(NULL, IDC_HAND));
            return TRUE;
        }
        break;
    }

    case WM_LBUTTONUP: {
        POINT p = {GET_X_LPARAM(lp), GET_Y_LPARAM(lp)};
        if (g_phase == PHASE_PANEL && over_link(p))
            ShellExecuteA(NULL, "open", BRAND_URL, NULL, NULL, SW_SHOWNORMAL);
        return 0;
    }

    case WM_COMMAND:
        if (LOWORD(wp) == ID_INJECT && HIWORD(wp) == BN_CLICKED)
            do_inject();
        return 0;

    case WM_DESTROY:
        PostQuitMessage(0);
        return 0;
    }
    return DefWindowProcA(hwnd, msg, wp, lp);
}

int WINAPI WinMain(HINSTANCE inst, HINSTANCE prev, LPSTR cmd, int show)
{
    WNDCLASSA wc;
    RECT r = {0, 0, WIN_W, WIN_H};
    MSG msg;

    (void)prev;
    (void)cmd;
    (void)show;

    g_inst = inst;
    g_phase = PHASE_SPLASH;
    g_f_brand = font_of(BRAND_PTS, FW_BOLD);
    g_f_small = font_of(SMALL_PTS, FW_NORMAL);
    g_f_body = font_of(BODY_PTS, FW_NORMAL);

    memset(&wc, 0, sizeof wc);
    wc.lpfnWndProc = wnd_proc;
    wc.hInstance = inst;
    wc.hCursor = LoadCursor(NULL, IDC_ARROW);
    wc.lpszClassName = "SkidSolutionsInjector";
    if (!RegisterClassA(&wc))
        return 1;

    AdjustWindowRectEx(&r, WS_OVERLAPPED | WS_CAPTION | WS_SYSMENU | WS_MINIMIZEBOX,
                       FALSE, 0);
    g_wnd = CreateWindowExA(0, wc.lpszClassName, WINDOW_TITLE,
                            WS_OVERLAPPED | WS_CAPTION | WS_SYSMENU | WS_MINIMIZEBOX,
                            (GetSystemMetrics(SM_CXSCREEN) - (r.right - r.left)) / 2,
                            (GetSystemMetrics(SM_CYSCREEN) - (r.bottom - r.top)) / 2,
                            r.right - r.left, r.bottom - r.top,
                            NULL, NULL, inst, NULL);
    if (!g_wnd)
        return 1;

    ShowWindow(g_wnd, SW_SHOW);
    UpdateWindow(g_wnd);
    while (GetMessageA(&msg, NULL, 0, 0) > 0) {
        TranslateMessage(&msg);
        DispatchMessageA(&msg);
    }
    return 0;
}

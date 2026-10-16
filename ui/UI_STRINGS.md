# Vape V4 loader — UI text inventory (static strings only)

Binary: `60e47510.exe` (PE32+ x64, sha256
`581a7ab6c901a9c51bb8fecb35ab91b62e12c03d302b4adc5e1a2be80044470c`).
Method: `python3` scan of the raw image — UTF-16LE runs
(`(?:[\x20-\x7e]\x00){5,}`) plus narrow printable runs (`[\x20-\x7e]{5,}`).
Every string below was observed verbatim in the binary. Tags
`[narrow]` / `[wide]` record which encoding the verbatim bytes were found in
(checked with both a narrow and a UTF-16LE search). Order, layout, and
which screen shows what are **not** verifiable from strings alone.

## 1. Login form — observed-verbatim

- `Username / email` [narrow]
- `##username` [narrow] (ImGui widget ID suffix for the username field)
- `Password` [narrow]
- `##password` [narrow] (ImGui widget ID suffix for the password field)
- `Login` [narrow]
- `Login through browser` [narrow]
- `Logging in` [narrow]
- `Follow the directions` [narrow]
- `in your browser to continue` [narrow]
- `Enter a username` [wide]
- `Re-open` [narrow]
- `Cancel` [narrow]
- `##menu` [narrow] (ImGui ID near the login strings)
- `%s` [narrow] (format fragment in the same block; use unverified)

## 2. Minecraft picker — observed-verbatim

- `No Minecraft found` [narrow]
- `Open Minecraft to continue` [narrow]
- `Select a Minecraft to use` [narrow]
- `Make sure game is fully loaded first` [narrow]
- `Already Injected [%lu]` [narrow]
- `PID %lu` [narrow]
- `/30` [narrow] (bare fragment immediately after `PID %lu`; semantics unverified)

## 3. Status / progress / finished — observed-verbatim

- `Stage ` [narrow] (with trailing space)
- `It is taking abnormally long to load this stage` [narrow]
- `Contact support for assistance` [narrow]
- `Would you like to cache local files for faster loading time?` [narrow]
- `Yes` [narrow] / `No` [narrow] (adjacent to the cache question)
- `Vape has finished loading` [narrow]
- `Press RIGHT SHIFT(Default) while in game to open the GUI` [narrow]
- `Close Window` [narrow]
- `Error` [narrow]

## 4. Loader errors — observed-verbatim

Process / bootstrap:

- `Failed to open Minecraft process` [wide]
- `Failed to create the Loader bootstrap objects` [wide]
- `A Loader bootstrap already exists for this process` [wide]
- `Failed to write the Product DLL path` [wide]
- `Failed to resolve loader entry` [wide]
- `Failed to start Product DLL loading` [wide]
- `VAPE_ONLINE_BASE_URL is too long for the bootstrap` [wide]
- `VAPE_ZEUS_ADDRESS must use host:port format` [wide]
- `NativeLoader.dll was not found` [wide]
- `Bootstrap` [wide] / `BootstrapAck` [wide] (bootstrap protocol tokens)

Payload / download:

- `Failed to download the payload from the server` [wide]
- `Failed to inject — no payload available` [wide] (one contiguous wide
  string; the short form `Failed to inject` is its prefix)
- `Payload image is empty` [wide]
- `Payload is too small to be a valid PE` [wide]
- `Payload has invalid PE headers` [wide]
- `Payload is not a valid 64-bit PE image` [wide]

Reflective / injection stub:

- `ReflectiveLoader` [narrow] (export-name style token)
- `Failed to write reflective image into target process` [wide]
- `Failed to start reflective loader thread` [wide]
- `Reflective loader returned failure` [wide]
- `Failed to allocate memory in target process for payload` [wide]
- `Failed to write mapped payload into target process` [wide]
- `Failed to allocate memory for injection stub` [wide]
- `Failed to write injection stub parameters` [wide]
- `Failed to write injection stub code` [wide]
- `Failed to execute injection stub in target process` [wide]
- `Injection stub execution timed out` [wide]
- `Injection stub returned failure (DllMain or import resolution failed)` [wide]

Product / native DLL loading:

- `Product DLL loading timed out` [wide]
- `Product DLL did not acknowledge the socket bootstrap` [wide]
- `Product DLL rejected the socket bootstrap` [wide]
- `Native loading connection closed unexpectedly` [wide]
- `Native loading timed out` [wide]

App-auth / browser login:

- `Unable to start browser login` [wide]
- `Browser login timed out` [wide]

Service / controller socket:

- `Unable to log in to the local Service` [wide]
- `Failed to create the Loader controller socket` [wide]
- `Loader controller port is unavailable` [wide]

## 5. ImGui-internal strings (deliberately separated, not loader UI)

A small sample of the Dear ImGui `1.91.9b` debug/error strings also present
in the image. Kept out of the sections above:

- `Dear ImGui 1.91.9b (19191)`, `imgui.ini`, `imgui_log.txt`
- `MESSAGE FROM DEAR IMGUI`, `##Tooltip_Error`, `(and more errors)`
- `Calling PopStyleColor() too many times!`,
  `Calling PushStyleVar() variant with wrong type!`,
  `Calling PopStyleVar() too many times!`, `Calling End() too many times!`,
  `Calling PopFont() too many times!`, `Calling PopID() too many times!`,
  `Calling PopItemFlag() too many times!`,
  `Calling EndDisabled() too many times!`,
  `Calling PopFocusScope() too many times!`,
  `Calling PopItemWidth() too many times!`,
  `Must call EndChild() and not End()!`
- `Missing EndChild()`, `Missing EndTable()`, `Missing End()`,
  `Missing EndTabBar()`, `Missing EndMultiSelect()`,
  `Missing EndMenuBar()`, `Missing TreePop()`, `Missing EndGroup()`,
  `Missing PopID()`, `Missing EndDisabled()`, `Missing PopStyleColor()`,
  `Missing PopItemFlag()`, `Missing PopStyleVar()`, `Missing PopFont()`,
  `Missing PopFocusScope()`
- `EndMultiSelect() FocusScope mismatch!`, `EndMultiSelect`
- `Invalid flags for IsItemHovered()!`
- `SetActiveID() cancel MovingWindow`,
  `(Debug Log: Auto-disabled some ImGuiDebugLogFlags after 2 frames)`
- Table-settings serialization keys (NOT network protocol):
  `UserID=0x%08X%n`, ` UserID=%08X`, `RefScale=%f`, `Column %d%n`,
  `Width=%d%n`, `Weight=%f%n`, `Visible=%d%n`, `Order=%d%n`, `Sort=%d%c%n`

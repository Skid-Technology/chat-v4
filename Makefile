# v4-chat — top-level build
#
#   make            portable test loader + Java agent
#   make payload    rebuild the Java client from src/payload (Maven + shade)
#   make agent      Java agent for macOS/Linux (premain + attach + splash)
#   make bundle     pack bundle.dat (native host DLL + Java client)
#   make windows    Windows injector: native UI + injection logic + payload
#   make verify     confirm injector.exe carries bundle.dat
#   make test       build + verify bundle.dat end to end
#   make dist       stage injector.exe + agent.jar into dist/
#   make clean      remove all build output
SHELL     := /bin/bash
PYTHON    ?= python3
MAVEN     ?= mvn
MINGW     ?= x86_64-w64-mingw32-gcc
MINGW_CXX ?= x86_64-w64-mingw32-g++

INJECTOR    := src/injector
AGENT       := $(INJECTOR)/java_agent
PAYLOAD_DIR := src/payload
PAYLOAD_JAR := $(PAYLOAD_DIR)/target/vape_client.jar
BUNDLE      := bundle.dat
BUILD       := build

# The injection logic (portable C11) and, on Windows only, the backend and the
# native front end.
LOADER_SRC := $(INJECTOR)/loader.c $(INJECTOR)/os_stub.c $(INJECTOR)/sha256.c
HOST_SRC   := $(INJECTOR)/host_dll.c
C_OBJ      := $(patsubst $(INJECTOR)/%.c,$(BUILD)/%.o,$(LOADER_SRC) $(INJECTOR)/os_win32.c)
HOST_DLL   := $(BUILD)/Vape421Native.dll

# Dear ImGui 1.91.9b (vendored, MIT) with the Win32 + D3D11 backends — the same
# version and backends the Loader uses.
IMGUI     := $(INJECTOR)/third_party/imgui
IMGUI_SRC := $(IMGUI)/imgui.cpp $(IMGUI)/imgui_draw.cpp $(IMGUI)/imgui_tables.cpp \
             $(IMGUI)/imgui_widgets.cpp \
             $(IMGUI)/backends/imgui_impl_win32.cpp $(IMGUI)/backends/imgui_impl_dx11.cpp
IMGUI_OBJ := $(patsubst $(IMGUI)/%.cpp,$(BUILD)/imgui/%.o,$(IMGUI_SRC))
JNI_DIR   := $(INJECTOR)/third_party/jni

LINK_LIBS := -ld3d11 -ldxgi -ld3dcompiler -ldwmapi -lgdi32 -limm32 -lole32 \
             -luuid -lshell32 -luser32 -lkernel32

.PHONY: all payload injector agent bundle windows verify test dist clean

all: injector agent

# The Java payload, rebuilt from source. Third-party libraries come from
# Maven; ASM and Javassist are relocated to gg.vape.shaded.* by the shade
# plugin, exactly as the original jar had them.
payload:
	$(MAVEN) -q -B -f $(PAYLOAD_DIR)/pom.xml package

injector:
	$(MAKE) -C $(INJECTOR) all

agent:
	$(MAKE) -C $(INJECTOR) agent

# bundle.dat carries the two things the injector consumes: the native host DLL
# (what the Loader injects and starts remotely) and the Java client it hosts.
bundle: $(BUNDLE)

$(BUNDLE): $(HOST_DLL) $(PAYLOAD_JAR) $(INJECTOR)/bundle.py
	$(PYTHON) $(INJECTOR)/bundle.py $(HOST_DLL) $(PAYLOAD_JAR) -o $(BUNDLE)

# The client jar is a build product of src/payload; build it if it is absent
# (CI stages the artifact its own payload job produced instead).
$(PAYLOAD_JAR):
	$(MAKE) payload

# The injected native host: finds the target's JVM and loads the client.
$(HOST_DLL): $(HOST_SRC) $(INJECTOR)/loader.h
	@mkdir -p $(BUILD)
	$(MINGW) -std=c11 -O2 -Wall -Wextra -shared -o $@ $(HOST_SRC) \
	    -I$(INJECTOR) -I$(JNI_DIR) -luser32

$(BUILD)/%.o: $(INJECTOR)/%.c $(INJECTOR)/loader.h
	@mkdir -p $(dir $@)
	$(MINGW) -std=c11 -O2 -Wall -Wextra -c $< -o $@ -I$(INJECTOR)

$(BUILD)/ui_win32.o: $(INJECTOR)/ui_win32.cpp $(INJECTOR)/loader.h $(INJECTOR)/os_win32.h
	@mkdir -p $(dir $@)
	$(MINGW_CXX) -std=c++17 -O2 -Wall -Wextra -DUNICODE -D_UNICODE -c $< -o $@ \
	    -I$(INJECTOR) -I$(IMGUI) -I$(IMGUI)/backends

$(IMGUI_OBJ): $(BUILD)/imgui/%.o: $(IMGUI)/%.cpp
	@mkdir -p $(dir $@)
	$(MINGW_CXX) -std=c++17 -O2 -w -c $< -o $@ -I$(IMGUI) -I$(IMGUI)/backends

# One file, Windows only: the ImGui/D3D11 front end, the Loader's injection
# mechanism (os_win32.c) and the payload appended to the image (embed.py).
windows: $(BUNDLE) dist/injector.exe

dist/injector.exe: $(C_OBJ) $(BUILD)/ui_win32.o $(IMGUI_OBJ) $(BUNDLE)
	@mkdir -p dist
	$(MINGW_CXX) -mwindows -o $@ $(C_OBJ) $(BUILD)/ui_win32.o $(IMGUI_OBJ) $(LINK_LIBS)
	$(PYTHON) $(INJECTOR)/embed.py $@ $(BUNDLE) $@
	@ls -l $@

test: injector
	@test -f $(BUNDLE) || { echo "bundle.dat missing: run 'make bundle' first"; exit 1; }
	$(MAKE) -C $(INJECTOR) test

# The exe is Windows code, so CI checks the payload it carries instead of
# running it: the trailer, the blob and its digest against bundle.dat.
verify:
	@test -f dist/injector.exe || { echo "dist/injector.exe missing: run 'make windows' first"; exit 1; }
	$(PYTHON) $(INJECTOR)/embed.py --check dist/injector.exe $(BUNDLE)

dist: windows agent
	cp $(AGENT)/agent.jar dist/
	@ls -l dist

clean:
	$(MAKE) -C $(INJECTOR) clean
	rm -rf dist $(BUNDLE) $(BUILD)

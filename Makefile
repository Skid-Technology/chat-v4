# v4-chat — top-level build
#
#   make            portable test loader + Java agent
#   make payload    rebuild the Java client from src/payload (Maven + shade)
#   make injector   portable test loader only
#   make agent      Java agent for macOS/Linux (premain + attach + splash)
#   make bundle     pack bundle.dat from the built payload
#   make windows    Windows injector: native UI + injection logic + payload
#   make verify     confirm injector.exe carries bundle.dat
#   make test       build + verify bundle.dat end to end
#   make dist       stage injector.exe + agent.jar into dist/
#   make clean      remove all build output
SHELL   := /bin/bash
PYTHON  ?= python3
MAVEN   ?= mvn
MINGW   ?= x86_64-w64-mingw32-gcc

INJECTOR    := src/injector
AGENT       := $(INJECTOR)/java_agent
PAYLOAD_DIR := src/payload
PAYLOAD_JAR := $(PAYLOAD_DIR)/target/vape_client.jar
BUNDLE      := bundle.dat

# The injector's sources: the pipeline, the portable backend, SHA-256 and, on
# Windows only, the native front end.
LOADER_SRC  := $(INJECTOR)/loader.c $(INJECTOR)/os_stub.c $(INJECTOR)/sha256.c

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

bundle: $(BUNDLE)

$(BUNDLE): payload $(INJECTOR)/bundle.py
	$(PYTHON) $(INJECTOR)/bundle.py $(PAYLOAD_JAR) -o $(BUNDLE)

# One file, Windows only: the native UI (ui_win32.c) + the injection pipeline
# + the payload appended to the image (embed.py). Cross-compiled with mingw-w64.
windows: $(LOADER_SRC) $(INJECTOR)/ui_win32.c
	@test -f $(BUNDLE) || { echo "bundle.dat missing: run 'make bundle' first"; exit 1; }
	mkdir -p dist
	$(MINGW) -std=c11 -O2 -Wall -Wextra -mwindows -o dist/injector.exe \
	    $(LOADER_SRC) $(INJECTOR)/ui_win32.c -lm -lshell32
	$(PYTHON) $(INJECTOR)/embed.py dist/injector.exe $(BUNDLE) dist/injector.exe
	@ls -l dist/injector.exe

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
	rm -rf dist $(BUNDLE)

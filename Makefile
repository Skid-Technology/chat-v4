# v4-chat — top-level build
#
#   make            native loader + Java agent + bundle.dat
#   make injector   native loader only
#   make agent      Java agent only
#   make bundle     pack bundle.dat from the payloads
#   make test       build + verify bundle.dat end to end
#   make dist       stage release artifacts into dist/
#   make clean      remove all build output
SHELL   := /bin/bash
PYTHON  ?= python3

INJECTOR := src/injector
AGENT    := $(INJECTOR)/java_agent
PAYLOADS := payload.bin payload_jar/vape_client.jar

.PHONY: all injector agent bundle test dist update update-payload cli fetch clean

all: injector agent bundle

injector:
	$(MAKE) -C $(INJECTOR) all

agent:
	$(MAKE) -C $(INJECTOR) agent

bundle: bundle.dat

bundle.dat: $(PAYLOADS) $(INJECTOR)/bundle.py
	$(PYTHON) $(INJECTOR)/bundle.py $(PAYLOADS) bundle.dat

test: injector bundle
	$(MAKE) -C $(INJECTOR) test

dist: all
	mkdir -p dist
	cp $(INJECTOR)/loader_test dist/
	cp $(AGENT)/agent.jar dist/
	cp bundle.dat dist/
	@echo "staged release artifacts in dist/"

# Refresh everything from the current payloads (see update.sh).
update:
	./update.sh

# Refresh the payloads + decompiled sources from the live service.
#   make update-payload            # reuse the saved token
#   make update-payload ARGS=--login
update-payload:
	./scripts/update-payload.sh $(ARGS)

# Loader CLI — login + fetch + extract + deobf + decompile (needs an account).
cli:
	$(PYTHON) src/cli/vape_loader.py --help

fetch:
	$(PYTHON) src/cli/vape_loader.py all --browser

clean:
	$(MAKE) -C $(INJECTOR) clean
	rm -rf dist bundle.dat

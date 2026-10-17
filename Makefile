# v4-chat — top-level build
#
#   make            native loader + Java agent + bundle.dat
#   make payload    rebuild the Java client from src/payload (Maven + shade)
#   make injector   native loader only
#   make agent      Java agent only
#   make bundle     pack bundle.dat from the built payload
#   make single     single-file injector for this host OS (loader + payload)
#   make test       build + verify bundle.dat end to end
#   make dist       stage release artifacts into dist/
#   make clean      remove all build output
SHELL   := /bin/bash
PYTHON  ?= python3
MAVEN   ?= mvn

INJECTOR    := src/injector
AGENT       := $(INJECTOR)/java_agent
PAYLOAD_DIR := src/payload
PAYLOAD_JAR := $(PAYLOAD_DIR)/target/vape_client.jar
BUNDLE      := bundle.dat

.PHONY: all payload injector agent bundle single test dist update update-payload cli fetch clean

all: injector agent bundle

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

# One file: the loader image with the bundle appended to it.
single: injector bundle
	mkdir -p dist
	$(PYTHON) $(INJECTOR)/embed.py $(INJECTOR)/loader_test $(BUNDLE) \
	    dist/injector-$$(uname -s | tr '[:upper:]' '[:lower:]')

test: injector bundle
	$(MAKE) -C $(INJECTOR) test

dist: all single
	cp $(AGENT)/agent.jar dist/
	cp $(BUNDLE) dist/
	@echo "staged release artifacts in dist/"

# Refresh everything from the current payloads (see update.sh).
update:
	./update.sh

# Refresh the payload sources from the live service (needs an account).
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
	rm -rf dist $(BUNDLE) $(PAYLOAD_DIR)/target

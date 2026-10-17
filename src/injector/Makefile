# src/injector/Makefile — native loader + Java agent
CC     ?= cc
CFLAGS ?= -std=c11 -Wall -Wextra -pedantic
JAVAC  ?= javac
JAR    ?= jar
PYTHON ?= python3

AGENT_DIR  := java_agent
AGENT_JAR  := $(AGENT_DIR)/agent.jar

.PHONY: all loader_test shatest agent test single clean

all: loader_test agent

# Portable test binary: validate + build bootstrap + inject against the stub.
loader_test: loader.c os_stub.c sha256.c loader.h
	$(CC) $(CFLAGS) -DLOADER_TEST_MAIN -o $@ loader.c os_stub.c sha256.c

# Standalone SHA-256 self-test.
shatest: sha256.c
	$(CC) $(CFLAGS) -DSHA256_SELFTEST -o $@ sha256.c

# Java instrumentation agent (premain + agentmain) + attach/probe helpers.
agent: $(AGENT_JAR) $(AGENT_DIR)/Attach.class $(AGENT_DIR)/Probe.class

$(AGENT_JAR): $(AGENT_DIR)/Agent.java $(AGENT_DIR)/MANIFEST.MF
	$(JAVAC) -d $(AGENT_DIR) $(AGENT_DIR)/Agent.java
	$(JAR) --create --file $@ \
	    --manifest $(AGENT_DIR)/MANIFEST.MF -C $(AGENT_DIR) Agent.class

$(AGENT_DIR)/Attach.class: $(AGENT_DIR)/Attach.java
	$(JAVAC) --add-modules jdk.attach -d $(AGENT_DIR) $(AGENT_DIR)/Attach.java

$(AGENT_DIR)/Probe.class: $(AGENT_DIR)/Probe.java
	$(JAVAC) -d $(AGENT_DIR) $(AGENT_DIR)/Probe.java

test: loader_test ../../bundle.dat
	./loader_test ../../bundle.dat

# Single-file injector: the loader image with the bundle appended, then run
# with no arguments so it reads its own embedded payload.
single: loader_test ../../bundle.dat
	$(PYTHON) embed.py loader_test ../../bundle.dat loader_single
	./loader_single

clean:
	rm -f loader_test shatest loader_single
	rm -f $(AGENT_DIR)/*.class $(AGENT_JAR)

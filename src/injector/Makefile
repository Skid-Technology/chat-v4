# src/injector/Makefile — native loader + Java agent
CC     ?= cc
CFLAGS ?= -std=c11 -Wall -Wextra -pedantic
JAVAC  ?= javac
JAR    ?= jar
PYTHON ?= python3

AGENT_DIR := java_agent
AGENT_JAR := $(AGENT_DIR)/agent.jar
AGENT_SRC := $(AGENT_DIR)/Agent.java $(AGENT_DIR)/Skid.java
AGENT_CLS := $(AGENT_DIR)/Agent.class $(AGENT_DIR)/Skid.class

.PHONY: all loader_test shatest agent test clean

all: loader_test agent

# Portable test binary: validate + build bootstrap + inject against the stub.
loader_test: loader.c os_stub.c sha256.c loader.h
	$(CC) $(CFLAGS) -DLOADER_TEST_MAIN -o $@ loader.c os_stub.c sha256.c

# Standalone SHA-256 self-test.
shatest: sha256.c
	$(CC) $(CFLAGS) -DSHA256_SELFTEST -o $@ sha256.c

# Java instrumentation agent (premain + agentmain + splash) + attach helpers.
agent: $(AGENT_JAR) $(AGENT_DIR)/Attach.class $(AGENT_DIR)/Probe.class

$(AGENT_JAR): $(AGENT_SRC) $(AGENT_DIR)/MANIFEST.MF
	$(JAVAC) -d $(AGENT_DIR) $(AGENT_SRC)
	$(JAR) --create --file $@ \
	    --manifest $(AGENT_DIR)/MANIFEST.MF \
	    -C $(AGENT_DIR) Agent.class -C $(AGENT_DIR) Skid.class

$(AGENT_DIR)/Attach.class: $(AGENT_DIR)/Attach.java
	$(JAVAC) --add-modules jdk.attach -d $(AGENT_DIR) $(AGENT_DIR)/Attach.java

$(AGENT_DIR)/Probe.class: $(AGENT_DIR)/Probe.java
	$(JAVAC) -d $(AGENT_DIR) $(AGENT_DIR)/Probe.java

test: loader_test ../../bundle.dat
	./loader_test ../../bundle.dat

clean:
	rm -f loader_test shatest
	rm -f $(AGENT_DIR)/*.class $(AGENT_JAR)

CC = cc
CFLAGS = -std=c11 -Wall -Wextra -pedantic

all: loader_test

loader_test: loader.c os_stub.c sha256.c loader.h
	$(CC) $(CFLAGS) -DLOADER_TEST_MAIN -o loader_test loader.c os_stub.c sha256.c

shatest: sha256.c
	$(CC) $(CFLAGS) -DSHA256_SELFTEST -o shatest sha256.c && ./shatest

test: loader_test ../../payload.bin
	./loader_test ../../payload.bin

clean:
	rm -f loader_test

/* sha256.c — compact portable SHA-256 (public-domain style).
 * Needed to verify bundle entries offline. C11, no deps. */
#include <stdint.h>
#include <string.h>

typedef struct {
    uint32_t h[8];
    uint64_t len;
    uint8_t buf[64];
    size_t buflen;
} sha256_t;

static uint32_t rotr(uint32_t x, int n) { return (x >> n) | (x << (32 - n)); }

static const uint32_t K[64] = {
    0x428a2f98,0x71374491,0xb5c0fbcf,0xe9b5dba5,0x3956c25b,0x59f111f1,0x923f82a4,0xab1c5ed5,
    0xd807aa98,0x12835b01,0x243185be,0x550c7dc3,0x72be5d74,0x80deb1fe,0x9bdc06a7,0xc19bf174,
    0xe49b69c1,0xefbe4786,0x0fc19dc6,0x240ca1cc,0x2de92c6f,0x4a7484aa,0x5cb0a9dc,0x76f988da,
    0x983e5152,0xa831c66d,0xb00327c8,0xbf597fc7,0xc6e00bf3,0xd5a79147,0x06ca6351,0x14292967,
    0x27b70a85,0x2e1b2138,0x4d2c6dfc,0x53380d13,0x650a7354,0x766a0abb,0x81c2c92e,0x92722c85,
    0xa2bfe8a1,0xa81a664b,0xc24b8b70,0xc76c51a3,0xd192e819,0xd6990624,0xf40e3585,0x106aa070,
    0x19a4c116,0x1e376c08,0x2748774c,0x34b0bcb5,0x391c0cb3,0x4ed8aa4a,0x5b9cca4f,0x682e6ff3,
    0x748f82ee,0x78a5636f,0x84c87814,0x8cc70208,0x90befffa,0xa4506ceb,0xbef9a3f7,0xc67178f2
};

static void sha256_block(sha256_t *c, const uint8_t *p)
{
    uint32_t w[64], a, b, d, e, f, g, h, t1, t2;
    for (int i = 0; i < 16; i++)
        w[i] = (uint32_t)p[4*i]<<24 | (uint32_t)p[4*i+1]<<16 |
               (uint32_t)p[4*i+2]<<8 | p[4*i+3];
    for (int i = 16; i < 64; i++) {
        uint32_t s0 = rotr(w[i-15],7) ^ rotr(w[i-15],18) ^ (w[i-15]>>3);
        uint32_t s1 = rotr(w[i-2],17) ^ rotr(w[i-2],19) ^ (w[i-2]>>10);
        w[i] = w[i-16] + s0 + w[i-7] + s1;
    }
    a=c->h[0];b=c->h[1];d=c->h[2];e=c->h[3];
    f=c->h[4];g=c->h[5];h=c->h[6];t1=c->h[7]; /* names shifted, textbook below */
    {
        uint32_t A=a,B=b,C=d,D=e,E=f,F=g,G=h,H=t1;
        for (int i = 0; i < 64; i++) {
            uint32_t S1 = rotr(E,6)^rotr(E,11)^rotr(E,25);
            uint32_t ch = (E&F)^(~E&G);
            t1 = H + S1 + ch + K[i] + w[i];
            uint32_t S0 = rotr(A,2)^rotr(A,13)^rotr(A,22);
            uint32_t mj = (A&B)^(A&C)^(B&C);
            t2 = S0 + mj;
            H=G;G=F;F=E;E=D+t1;D=C;C=B;B=A;A=t1+t2;
        }
        c->h[0]+=A;c->h[1]+=B;c->h[2]+=C;c->h[3]+=D;
        c->h[4]+=E;c->h[5]+=F;c->h[6]+=G;c->h[7]+=H;
    }
}

static void sha256_init(sha256_t *c)
{
    static const uint32_t H[8] =
        {0x6a09e667,0xbb67ae85,0x3c6ef372,0xa54ff53a,
         0x510e527f,0x9b05688c,0x1f83d9ab,0x5be0cd19};
    memcpy(c->h, H, sizeof(H));
    c->len = 0;
    c->buflen = 0;
}

static void sha256_update(sha256_t *c, const uint8_t *d, size_t n)
{
    c->len += n;
    while (n) {
        size_t take = 64 - c->buflen;
        if (take > n)
            take = n;
        memcpy(c->buf + c->buflen, d, take);
        c->buflen += take;
        d += take;
        n -= take;
        if (c->buflen == 64) {
            sha256_block(c, c->buf);
            c->buflen = 0;
        }
    }
}

static void sha256_final(sha256_t *c, uint8_t out[32])
{
    uint64_t bits = c->len * 8;
    uint8_t pad = 0x80, zero = 0;
    sha256_update(c, &pad, 1);
    while (c->buflen != 56)
        sha256_update(c, &zero, 1);
    uint8_t lb[8];
    for (int i = 0; i < 8; i++)
        lb[i] = (uint8_t)(bits >> (56 - 8*i));
    /* append length directly (buffer has room by construction) */
    memcpy(c->buf + 56, lb, 8);
    sha256_block(c, c->buf);
    for (int i = 0; i < 8; i++) {
        out[4*i]   = (uint8_t)(c->h[i] >> 24);
        out[4*i+1] = (uint8_t)(c->h[i] >> 16);
        out[4*i+2] = (uint8_t)(c->h[i] >> 8);
        out[4*i+3] = (uint8_t)c->h[i];
    }
}

/* one-shot */
void sha256_once(const uint8_t *d, size_t n, uint8_t out[32])
{
    sha256_t c;
    sha256_init(&c);
    sha256_update(&c, d, n);
    sha256_final(&c, out);
}

#ifdef SHA256_SELFTEST
#include <stdio.h>
int main(void)
{
    /* "abc" -> ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad */
    static const uint8_t want[32] = {
        0xba,0x78,0x16,0xbf,0x8f,0x01,0xcf,0xea,0x41,0x41,0x40,0xde,0x5d,0xae,0x22,
        0x23,0xb0,0x03,0x61,0xa3,0x96,0x17,0x7a,0x9c,0xb4,0x10,0xff,0x61,0xf2,0x00,
        0x15,0xad
    };
    uint8_t got[32];
    sha256_once((const uint8_t *)"abc", 3, got);
    if (memcmp(got, want, 32) != 0) {
        printf("SHA256 SELFTEST FAIL\n");
        return 1;
    }
    printf("SHA256 SELFTEST OK\n");
    return 0;
}
#endif

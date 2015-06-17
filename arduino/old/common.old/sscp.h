/*
 * Spoderpod Serial Communication Protocol
 */

#ifndef SSCP_H_
#define SSCP_H_

typedef unsigned char byte;

void SSCP_EncodeCOBS(const byte *src, byte *dst, size_t size);
void SSCP_DecodeCOBS(const byte *src, byte *dst, size_t size);

#endif

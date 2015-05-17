/*
 * Spoderpod Serial Communication Protocol
 */

#ifndef SSCP_H_
#define SSCP_H_

#include <stdio.h>

typedef unsigned char byte;

void SSCP_Send(byte *msg, size_t size, FILE *fd);
void SSCP_Receive(const byte *msg, byte *dst, size_t size);

void SSCP_EncodeCOBS(const byte *src, byte *dst, size_t size);
void SSCP_DecodeCOBS(const byte *src, byte *dst, size_t size);

#endif

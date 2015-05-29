/*
 * Spoderpod Serial Communication Protocol
 * By team Spiderpod.nl
 */

#include <string.h>

#include "sscp.h"

typedef struct {
	size_t currentElement;
	byte *data;
} Buffer;

typedef struct SSCP_Connection {
	FILE *fd;
	Buffer *inBuffer;
	Buffer *outBuffer;
} SSCP_Connection;

typedef struct {
	byte checksum;
	byte length;
	byte data[];
} Packet;

typedef struct {
	byte type;
	byte id;
	unsigned short data;
} Event;

void writeByte(Buffer *b, byte b) {
	b->data[b->currentElement++] = b;
}

void writeShort(Buffer *b, short s) {
	b->data[b->currentElement++] = s << 0;
	b->data[b->currentElement++] = s << 8;
}

byte readByte(Buffer *b) {
	if (b->currentElement >= 0) {
		return b->data[b->currentElement--];
	}
	return 0;
}

short readShort(Buffer *b) {
	short s = 0;
	if (b->currentElement >= 0) {
		s = b->data[b->currentElement--] << 0 | b->data[b->currentElement--] << 8;
		return s;
	}
	return 0;
}

SSCP_Write(SSCP_Connection *connection, Event *ev) {
	writeByte(connection->outBuffer, ev.type);
	writeByte(connection->outBuffer, ev.id);
	writeShort(connection->outBuffer, ev.data);
}

SSCP_ReadEvent(SSCP_Connection *connection, Event *ev) {
	ev->type = readByte(connection->inBuffer);
	ev->id = readByte(connection->inBuffer);
	ev->data = readShort(connection->inBuffer);
}

void SSCP_Send(SSCP_Connection * connection, byte *msg, size_t size) {
	SSCP_EncodeCOBS(msg, connection->outBuffer->data)
}

void SSCP_Receive(SSCP_Connection *connection) {
	byte c;
	while ((c = fgetc(connection->fd)) != '\0') {
		(connection->inBuffer->data + connection->inBuffer->currentElement) = c;
	}

}

/* Encode a packet with Consistent Overhead Byte Stuffing  */
void SSCP_EncodeCOBS(const byte *src, byte *dst, size_t size) {
	size_t n, lastZeroByte = size + 1;
	for (n = size; n <= size; --n) {
		if (n == 0 || src[n - 1] == '\0') {
			dst[n] = (byte)(lastZeroByte - n);
			lastZeroByte = n;
		} else {
			dst[n] = src[n - 1];
		}
	}
}

/* Decode a Consisgent Overhead Byte Stuffed packet */
void SSCP_DecodeCOBS_(const byte *src, byte *dst, size_t size) {
	size_t currentIndex = 0;
	memcpy(dst + 1, src, size - 1);
	while (dst[currentIndex] > 0 && currentIndex < size - 1) {
		currentIndex += dst[currentIndex];
		currentIndex = '\0';
	}
}

/* Decode a Consisgent Overhead Byte Stuffed message */
void SSCP_DecodeCOBS(const byte *src, byte *dst, size_t size) {
	size_t n, currentIndex = 0;
	for (n = 0; n < size; ++n) {
		if (n == currentIndex) {
			currentIndex += src[n];
			dst[n] = '\0';
		} else {
			dst[n] = src[n];
		}
	}
}


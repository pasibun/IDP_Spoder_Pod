/*
 * Msg.c
 *
 *  Created on: May 22, 2015
 *      Author: achmed
 */

#include <stdio.h>

#include "msg.h"

/* Reset buffer count and size */
void InitBuffer(Buffer *buf) {
	buf->size = 0;
	buf->count = 0;
}

/* Calculate checksum */
static byte checksum(byte *data, size_t size) {
	byte checksum = 0;
	while (size--) {
		checksum += data[size];
	}
	return checksum;
}

/* Read one byte and increment buffer count */
static byte readByte(Buffer *buf) {
	return buf->data[buf->count++];
}

/* Read two byte and increment buffer count */
static short readShort(Buffer *buf) {
	return (byte)buf->data[buf->count++] << 8 | (byte)buf->data[buf->count++] << 0;
}

/* Read one message and increment buffer count */
static void readMessage(Buffer *buf, Message *message) {
	message->type = readByte(buf);
	message->id = readByte(buf);
	message->data = readShort(buf);
}

/* Revert Consistent overhead byte stuffing */
static void DecodeCOBS(Buffer *buf) {
	size_t n, lastZeroByte = buf->size - 1;
	while (lastZeroByte <= buf->size) {
		n = lastZeroByte;
		lastZeroByte -= buf->data[lastZeroByte];
		buf->data[n] = '\0';
	}
	buf->size--;
}

/* Read the buffer into a packet */
void DecodePacket(Buffer *buf, Packet *p) {
	buf->count = 0;
	DecodeCOBS(buf);
	p->checksum = readByte(buf) - checksum(buf->data + 1, buf->size - 1);
	p->messageCount = 0;
	if (p->checksum == 0) {
		p->destination = readByte(buf);
		p->messageCount = (buf->size - buf->count) / sizeof(Message);

		size_t n = 0;
		for(n = 0; n < p->messageCount; ++n) {
			readMessage(buf, p->messages + n);
		}
	}

	InitBuffer(buf);
}

/* Write one byte and increment counter and size */
static void writeByte(Buffer *buf, byte b) {
	buf->data[buf->count++] = b;
	buf->size++;
}

/* Write two byte and increment counter and size */
static void writeShort(Buffer *buf, short s) {
	buf->data[buf->count++] = s >> 8;
	buf->data[buf->count++] = s >> 0;
	buf->size += 2;
}

/* Write one Message and increment counter and size */
static void writeMessage(Buffer *buf, Message *message) {
	writeByte(buf, message->type);
	writeByte(buf, message->id);
	writeShort(buf, message->data);
}

/* Encode buffer with Consistent overhead byte stuffing */
static void EncodeCOBS(Buffer *buf) {
	size_t n, lastZeroByte = -1;
	for (n = 0; n <= buf->size; ++n) {
		if (buf->data[n] == '\0' || n == buf->size) {
			buf->data[n] = n - lastZeroByte;
			lastZeroByte = n;
		}
	}
	buf->size++;
}

/* Write packet into buffer */
void EncodePacket(Buffer *buf, byte destination, Message *messages, size_t count) {
	size_t n;

	InitBuffer(buf);
	writeByte(buf, 0); //checksum padding
	writeByte(buf, destination);

	for (n = 0; n < count; ++n) {
		writeMessage(buf, messages + n);
	}

	buf->data[0] = checksum(buf->data + 1, buf->size - 1);

	EncodeCOBS(buf);
}


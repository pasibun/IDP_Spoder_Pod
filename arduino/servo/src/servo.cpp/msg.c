/*
 * Msg.c
 *
 *  Created on: May 22, 2015
 *      Author: achmed
 */

#include <stdio.h>

#include "msg.h"

void InitBuffer(Buffer *buf) {
	buf->size = 0;
	buf->count = 0;
}

byte checksum(byte *data, size_t size) {
	byte checksum = 0;
	while (size--) {
		checksum += data[size];
	}
	return checksum;
}

byte readByte(Buffer *buf) {
	return buf->data[buf->count++];
}

short readShort(Buffer *buf) {
	return (short) buf->data[buf->count++] << 0 | buf->data[buf->count++] << 8;
}

void readCommand(Buffer *buf, Message *message) {
	message->type = readByte(buf);
	message->id = readByte(buf);
	message->data = readShort(buf);
}

void DecodeCOBS(Buffer *buf) {
	size_t n, lastZeroByte = buf->size;
	while (n) {
		buf->data[n] = '\0';
	}
}

void DecodeData(Buffer *buf, Packet *p) {
	//DecodeCOBS(buf);
	p->checksum = readByte(buf);
	p->destination = readByte(buf);

	size_t n = 0;
	while (buf->count < buf->size) {
		readCommand(buf, p->messages + n++);
	}

	InitBuffer(buf);
}

void writeByte(Buffer *buf, byte b) {
	buf->data[buf->count++] = b;
}

void writeShort(Buffer *buf, short s) {
	buf->data[buf->count++] = s << 0;
	buf->data[buf->count++] = s << 8;
}

void writeCommand(Buffer *buf, Message *message) {
	writeByte(buf, message->type);
	writeByte(buf, message->id);
	writeShort(buf, message->data);
}

void EncodeCOBS(Buffer *buf) {
	size_t n, lastZeroByte = 0;
	for (n = buf->size; n > 0; --n) {
		if (buf->data[n - 1] == '\0') {
			buf->data[n - 1] = lastZeroByte - n;
			lastZeroByte = n;
		}
	}
}

void EncodeData(Buffer *buf, byte destination, Message *messages, size_t count) {
	size_t n;

	InitBuffer(buf);
	writeByte(buf, 0);//checksum(buf->data + 1, buf->size - 1)); //checksum
	writeByte(buf, destination); //destination

	for (n = 0; n < count; ++n) {
		writeCommand(buf, messages + n);
	}

	buf->size = buf->count;

	//EncodeCOBS(buf);
}


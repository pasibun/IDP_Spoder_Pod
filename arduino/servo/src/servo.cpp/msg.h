/*
 * msg.h
 *
 *  Created on: May 27, 2015
 *      Author: achmed
 */
#ifndef MSG_H_
#define MSG_H_

typedef unsigned char byte;

#define BUFFER_SIZE 256

typedef struct {
	byte size;
	byte count;
	char data[BUFFER_SIZE];
} Buffer;

typedef struct {
	byte type;
	byte id;
	short data;
} Message;

typedef struct packet {
	byte checksum;
	byte destination;
	Message messages[];
} Packet;

void EncodeData(Buffer *buf, byte destination, Message *messages, size_t count);

#endif

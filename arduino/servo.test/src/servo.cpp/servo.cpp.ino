
/*
 * servo.cpp
 *
 *  Created on: May 22, 2015
 *      Author: achmed
 */

#include <Dynamixel>

#include "msg.h"

Buffer buf;

Packet *packet = malloc(3 + sizeof(Message) * 10);

void moveServo(Packet *p) {
	size_t n;
	for(n = 0; n < p->messageCount; ++n) {
		Dynamixel.move(p->messages[n].id, p->messages[n].data);
	}
}

void setup() {
	Serial1.begin(9600);
}

void loop() {
	while (Serial1.available()) {
		buf.data[buf.size++] = Serial1.read();
		if (buf.data[buf.size] == 0) {
			buf.size--;
			DecodePacket(&buf, packet);
			moveServo(packet);
		}
	}
}

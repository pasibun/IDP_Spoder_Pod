/*
 * test2.c
 *
 *  Created on: May 27, 2015
 *      Author: achmed
 */

#include <stdio.h>

#include "msg.h"

int main() {
	Buffer buf;
	fread(buf.data, 6, 1, stdin);
	buf.size = 6;
	Packet *p = malloc(256);
	DecodeData(&buf, p);
	printf("%d %d %d\n", p->messages[0].type, p->messages[0].id, p->messages[0].data);
	return 0;
}



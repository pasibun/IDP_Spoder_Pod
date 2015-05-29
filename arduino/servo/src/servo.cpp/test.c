/*
 * test.c
 *
 *  Created on: May 27, 2015
 *      Author: achmed
 */

#include <stdio.h>

#include "msg.h"

int main() {
	Buffer buf;
	Message m[] = { { 0, 13, 360 } };
	EncodeData(&buf, 62, m, 1);
	fwrite(buf.data, buf.count, 1, stdout);
	return 0;
}


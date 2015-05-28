#include <stdio.h>

#include "common/sscp.h"

typedef struct {
	byte opCode;
	byte servoID;
	short degrees;
} Message;

/* Send a message with SSCP */
void static send(byte *msg, size_t size, FILE *fd) {
	byte buffer[256];
	SSCP_EncodeCOBS(msg, buffer, size);
	fwrite(buffer, 1, size + 1, fd);
}

/* Decode a SSCP packet */
void static receive(FILE *fd, byte *dst, size_t size) {
	byte buffer[256];
	byte *character = buffer + 1;
	while((*(character++) = fgetc(fd)) != '\0') {
	}
	SSCP_DecodeCOBS(buffer, dst, character - buffer);
}

int main() {
	size_t n;
	Message data = {.opCode = 0, .servoID = 63, .degrees = 180};
	fwrite(&data, 1, sizeof(data), stdout);
	printf("abaa");
	send((byte *)&data, sizeof(data), stdout);
	//char d[] = "abcd\n";
	//fwrite(d, 1, sizeof(d), stdout);
	return 0;
}

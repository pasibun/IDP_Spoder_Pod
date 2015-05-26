#include <stdio.h>

#include "common/sscp.h"

typedef struct {
	unsigned char opCode;
	unsigned char servoID;
	short degrees;
} Message;

int main () {
	char data[] = {0x00, 0xa2, 0, 92, 73, 2};
	char a[sizeof(data) + 1];
	SSCP_Send(data, sizeof(data), stdout);
	printf("\n");
	//Message msg = {.opCode = 0, .servoID = 13, .degrees = 350};
	//fwrite(&msg, 1, sizeof(Message), stdout);
	return 0;
}

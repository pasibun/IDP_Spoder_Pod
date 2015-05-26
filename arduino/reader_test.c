#include <stdio.h>

typedef struct {
	unsigned char opCode;
	unsigned char servoID;
	short degrees;
} Message;

int main () {
	Message msg;
	fread(&msg, 1, sizeof(Message), stdin);
	printf("%d %d %d\n", msg.opCode, msg.servoID, msg.degrees);
	return 0;
}

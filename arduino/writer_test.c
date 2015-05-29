#include <stdio.h>

#include "common/msg.h"

int main () {
	size_t n, count;
	Message m[10];
	Buffer buf;

	scanf("%d", &count);

	for(n = 0; n < count; ++n) {
		scanf("%d %d", &(m[n].id), &(m[n].data));
	}
	EncodePacket(&buf, 3, m, n);
	FILE *fd = fopen("data", "w");
	fputc('\0', fd);
	fwrite(buf.data, buf.size, 1, fd);
	fputc('\0', fd);
	fclose(fd);
	return 0;
}


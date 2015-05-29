#include <stdio.h>
#include <DynamixelSerial.h>

extern "C" {
#include "msg.h"
}

const float DegToBinRatio = 1024.0f / 300.0f;

short DegToBin(short degrees);
void execute(Packet *p);

void printMessage(Message *m);
void moveServo(Message *m);

void (*Commands[]) (Message *m) = {printMessage, moveServo};
Buffer buf;
Packet *packet = (Packet *)malloc(3 + sizeof(Message) * 10);

void setup() {
  Dynamixel.begin(1000000, 2);
  Serial1.begin(9600);
}

void loop() {
  byte c;
  while (Serial1.available()) {
    c = Serial1.read();
    if (c == '\0') {
      if (buf.size > 2) {
        DecodePacket(&buf, packet);
        execute(packet);
      }
      buf.size = 0;
    } else {
      buf.data[buf.size++] = c;
    }
  }
}

short DegToBin(short degrees) {
  return (degrees * DegToBinRatio) + 30 * DegToBinRatio;
}

void execute(Packet *p) {
  size_t n;
  for (n = 0; n < p->messageCount; ++n) {
    Commands[p->messages[n].type](&(p->messages[n]));
  }
}

void printMessage(Message *m) {
  Serial1.print("id: ");
  Serial1.print(m->id);
  Serial1.print(", degrees: ");
  Serial1.println(m->data);
}

void moveServo(Message *m) {
  Dynamixel.move(m->id, m->data);
}

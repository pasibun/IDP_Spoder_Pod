#include <stdio.h>
#include <DynamixelSerial.h>

extern "C" {
#include "msg.h"
}

#define CIRC_BUFFER_SIZE 128

/*
Typedef declarations
*/
typedef enum {
  Arduino, Raspi, GamePad, PC
} Destinations;

typedef struct {
  unsigned short readCount, writeCount;
  char data[CIRC_BUFFER_SIZE];
} CircBuffer;

typedef struct {
  HardwareSerial *serial;
  CircBuffer recvBuf;
  Buffer msgBuf;
} Com;

/*
Function prototypes
*/
short DegToBin(short degrees);
void InitCom(Com *com);
int CircBuf_nextCount(int count);
void handleSerialEvent(Com *com);
void processRecvBuf(Com *com);
void handlePacket(Com *com, Packet *packet);

void printMessage(Message *m);
void moveServoMessage(Message *m);

/*
Constants
*/
const float DegToBinRatio = 1024.0f / 300.0f;

HardwareSerial *DestinationTranslations[] = { &Serial1, &Serial1, &Serial2, &Serial1 };
void (*Commands[]) (Message *m) = {printMessage, moveServoMessage};

Com serial1Com = { .serial = &Serial1 };
Com serial2Com = { .serial = &Serial2 };
Packet *packet = (Packet *) malloc(3 + sizeof(Message) * 96);

/*
Default Arduino functions
*/
void setup() {
  Dynamixel.begin(1000000, 2);
  Serial1.begin(115200);
  Serial2.begin(9600);
  InitCom(&serial1Com);
  InitCom(&serial2Com);
}

void loop() {
  processRecvBuf(&serial1Com);
  processRecvBuf(&serial2Com);
}

void serialEvent1 () {
  handleSerialEvent(&serial1Com);
}

void serialEvent2 () {
  handleSerialEvent(&serial2Com);
}

/*
Custom function declarations
*/
short DegToBin(short degrees) {
  return (degrees - 30) * DegToBinRatio;
}

void InitCom(Com *com) {
  InitBuffer(&com->msgBuf);
  com->recvBuf.readCount = com->recvBuf.writeCount = 0;
}

int CircBuf_nextCount(int count) {
  return (count + 1) % CIRC_BUFFER_SIZE;
}

void handleSerialEvent(Com *com) {
  while (com->serial->available()) {
    com->recvBuf.data[com->recvBuf.writeCount] = com->serial->read();
    com->recvBuf.writeCount = CircBuf_nextCount(com->recvBuf.writeCount);
  }
}

void processRecvBuf(Com *com) {
  char c;
  while (com->recvBuf.readCount != com->recvBuf.writeCount) {
    c = com->recvBuf.data[com->recvBuf.readCount];
    if (c == '\0') {
      if (com->msgBuf.size > 3 && (com->msgBuf.size - 3) % sizeof(Message) == 0) {
        DecodePacket(&com->msgBuf, packet);
        if (packet->checksum == 0) {
          handlePacket(com, packet);
        } else {
          DestinationTranslations[Raspi]->println("Checksum mismatch");
        }
      }
      com->msgBuf.size = 0;
    } else {
      if (com->msgBuf.size < BUFFER_SIZE) {
        com->msgBuf.data[com->msgBuf.size++] = c;
      } else {
        com->msgBuf.size = 0;
      }
    }
    com->recvBuf.readCount = CircBuf_nextCount(com->recvBuf.readCount);
  }
}

void handlePacket(Com *com, Packet *packet) {
  if (packet->destination == Arduino) {
    for (size_t n = 0; n < packet->messageCount; ++n) {
      Commands[packet->messages[n].type](packet->messages + n);
    }
  } else {
    EncodePacket(&com->msgBuf, packet->destination, packet->messages, packet->messageCount);
    com->msgBuf.data[com->msgBuf.size++] = '\0';
    DestinationTranslations[packet->destination]->write(com->msgBuf.data, com->msgBuf.size);
  }
}

void printMessage(Message *m) {
  DestinationTranslations[Raspi]->print("Type: ");
  DestinationTranslations[Raspi]->println(m->type);
}

void moveServoMessage(Message *m) {
  Dynamixel.move(m->id, DegToBin(m->data));
}

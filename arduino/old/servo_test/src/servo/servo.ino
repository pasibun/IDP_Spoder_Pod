/*
Servo controller source
By team spoderpod.nl
*/

/*
Include files
*/
#include <stdio.h>
#include <DynamixelSerial.h>

extern "C" {
#include "msg.h"
}

/* Typedefs */
typedef struct {
  unsigned short readCount, writeCount;
  char data[CIRC_BUFFER_SIZE];
} CircBuffer;

/*
Prototypes for custom functions
*/
short DegToBin(short degrees);

void handleSerial(HardwareSerial *serial, Buffer *buf, void (*callback) (Packet *p));

void sendPacket(Packet *p);
void printPacket(Packet *p);
void executePacket(Packet *p);

void printMessage(Message *m);
void moveServo(Message *m);

/*
Constant declarations
*/
const float DegToBinRatio = 1024.0f / 300.0f;
void (*Commands[]) (Message *m) = {printMessage, moveServo};

enum Destinations {
  Arduino, Raspi, GamePad, PC
};

HardwareSerial DestinationSerialBinding[] = {Serial1, Serial1, Serial2, Serial1};

/* Global variable declarations */
Buffer serial1RecvBuf;
CircBuffer serial1RBuf

Buffer serial2RecvBuf;
Buffer serialSendBuf;
Packet *packet = (Packet *)malloc(3 + sizeof(Message) * 96);


/*
Definitions for default arduino functions
*/
void setup() {
  Dynamixel.begin(1000000, 2);
  Serial1.begin(115200);
  Serial2.begin(9600);
}

void loop() {
}

void serialEvent1() {
  handleSerial(&Serial1, &serial1RecvBuf, executePacket);
}

void serialEvent2() {
  handleSerial(&Serial2, &serial2RecvBuf, printPacket);
}

/*
Definitions for custom functions
*/
short DegToBin(short degrees) {
  return (degrees - 30) * DegToBinRatio;
}

/* Read incomming serial data, parse into packet and execute */
void handleSerial(HardwareSerial *serial, Buffer *buf, void (*callback) (Packet *p)) {
while (buf.readCount != buf.writeCount) {
    byte c = buf.data[buf.readCount];
    if (c == '\0') {
      if (msgBuf.size > (3 + sizeof(Message)) && (msgBuf.size - 3) % sizeof(Message) == 0) {
        DecodePacket(&msgBuf, packet);
        msgBuf.size = 0;
        if (packet->checksum == 0) {
          printPacket(packet);
        } else {
          Serial1.println(packet->checksum);
          Serial1.println("Error");
        }
      }
      msgBuf.size = 0;
    } else {
      if (msgBuf.size < 250) {
        msgBuf.data[msgBuf.size++] = c;
      } else {
        msgBuf.size = 0;
      }
    }
    buf.readCount = nextCount(buf.readCount);
  }
}

/* Rout packet to proper destination */
void sendPacket(Packet *p) {
  EncodePacket(&serialSendBuf, p->destination, p->messages, p->messageCount);
  DestinationSerialBinding[p->destination].write(serialSendBuf.data, serialSendBuf.size);
  DestinationSerialBinding[p->destination].write((byte)0);
}

/* Send debug message to pc */
void printPacket(Packet *p) {
  size_t n;
  DestinationSerialBinding[PC].println("Packet: ");
  DestinationSerialBinding[PC].print("Checksum: ");
  DestinationSerialBinding[PC].print(p->checksum, DEC);
  DestinationSerialBinding[PC].print(", Destination: ");
  DestinationSerialBinding[PC].println(p->destination, DEC);
  for (n = 0; n < p->messageCount; ++n) {
    printMessage(p->messages + n);
  }
}

/* Execute the function associated with packet type */
void executePacket(Packet *p) {
  size_t n;
  for (n = 0; n < p->messageCount; ++n) {
    Commands[p->messages[n].type](p->messages + n);
  }
}

/* Send debug message to pc */
void printMessage(Message *m) {
  DestinationSerialBinding[PC].print("id: ");
  DestinationSerialBinding[PC].print(m->id, DEC);
  DestinationSerialBinding[PC].print(", degrees: ");
  DestinationSerialBinding[PC].println(m->data, DEC);
}

/* Move servo based upon the message data */
void moveServo(Message *m) {
  Dynamixel.move(m->id, DegToBin(m->data));
}

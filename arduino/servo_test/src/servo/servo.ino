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

HardwareSerial DestinationSerialBinding[] = {Serial, Serial1, Serial2, Serial};

/* Global variable declarations */
Buffer serial1RecvBuf;
Buffer serial2RecvBuf;
Buffer serialSendBuf;
Packet *packet = (Packet *)malloc(3 + sizeof(Message) * 96);


/*
Definitions for default arduino functions
*/
void setup() {
  Dynamixel.begin(1000000, 2);
  Serial.begin(9600);
  Serial1.begin(9600);
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
  return (degrees * DegToBinRatio) + 30 * DegToBinRatio;
}

/* Read incomming serial data, parse into packet and execute */
void handleSerial(HardwareSerial *serial, Buffer *buf, void (*callback) (Packet *p)) {
  byte c;
  bool decodedPacket = false;
  while (!decodedPacket && serial->available()) {
    c = serial->read();
    if (c == '\0') {
      if (buf->size > 2) {
        DecodePacket(buf, packet);
        if (packet->destination == Arduino) {
          callback(packet);
        } else {
          sendPacket(packet);
        }
        decodedPacket = true;
      }
      buf->size = 0;
    } else {
      if (buf->size < BUFFER_SIZE) {
        buf->data[buf->size++] = c;
      } else {
        buf->size = 0;
      }
    }
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

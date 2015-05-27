/*
 * SerialUpdatable.cpp
 *
 *  Created on: May 22, 2015
 *      Author: achmed
 */

#include "SerialUpdatable.h"

SerialUpdatable::SerialUpdatable(unsigned char id, HardwareSerial serial) : id(id), serial(serial) {
	this->serial.begin(9600);
}

SerialUpdatable::~SerialUpdatable() {
	// TODO Auto-generated destructor stub
}

void SerialUpdatable::update() {
	while(this->serial.available()) {
		this->serial.read();
	}
}

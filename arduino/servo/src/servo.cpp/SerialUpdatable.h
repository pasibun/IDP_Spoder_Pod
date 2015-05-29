/*
 * SerialUpdatable.h
 *
 *  Created on: May 22, 2015
 *      Author: achmed
 */

#ifndef SERIALUPDATABLE_H_
#define SERIALUPDATABLE_H_

#include <HardwareSerial.h>

#include "msg.c"
#include "Updatable.h"

class SerialUpdatable : public Updatable {
public:
	SerialUpdatable(unsigned char id, const HardwareSerial serial);
	virtual ~SerialUpdatable();

	void update();

private:
	const unsigned char id;
	HardwareSerial &serial;
	Buffer *buf;
};

#endif /* SERIALUPDATABLE_H_ */

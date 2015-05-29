
/*
 * servo.cpp
 *
 *  Created on: May 22, 2015
 *      Author: achmed
 */

#include "Updatable.h"
#include "SerialUpdatable.h"

Updatable *updatables[] = { new SerialUpdatable(0, Serial) };

void setup() {

}

void loop() {
	unsigned int n;
	for (n = 0; n < sizeof(updatables) / sizeof(Updatable *); ++n) {
		updatables[n]->update();
	}
}

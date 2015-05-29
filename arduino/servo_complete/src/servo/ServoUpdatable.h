/*
 * ServoUpdatable.h
 *
 *  Created on: May 22, 2015
 *      Author: achmed
 */

#ifndef SERVOUPDATABLE_H_
#define SERVOUPDATABLE_H_

#include "Updatable.h"

class ServoUpdatable : public Updatable {
public:
	ServoUpdatable();
	virtual ~ServoUpdatable();

	void update();
};

#endif /* SERVOUPDATABLE_H_ */

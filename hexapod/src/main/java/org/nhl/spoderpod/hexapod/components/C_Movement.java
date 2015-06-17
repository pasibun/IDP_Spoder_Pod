package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.Message;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.I_Message;
import org.nhl.spoderpod.hexapod.utils.U_MovementServoMovement;

public final class C_Movement extends BaseComponent {

	private final U_MovementServoMovement servoMovement;

	public C_Movement(String name) {
		super(name);
		servoMovement = new U_MovementServoMovement();

	}

	public void init(MessageBus messageBus) {
		servoMovement.start();
	}

	public void close(MessageBus messageBus) {
		servoMovement.stop();

	}

	@Override
	protected boolean composeMessage(MessageBus messageBus) {
		// fuku
		return true;
	}

	@Override
	protected void receiveMessage(MessageBus messageBus, I_Message message) {
		if (message instanceof Message) {
			Message m = (Message) message;
			// System.out.println(m.getData());
			servoMovement.setCurrentMovement(m.getData());
		}

	}

}

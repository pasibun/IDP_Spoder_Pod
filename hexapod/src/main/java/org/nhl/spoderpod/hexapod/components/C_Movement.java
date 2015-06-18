package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.Message;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.I_Message;
import org.nhl.spoderpod.hexapod.utils.U_MovementServoMovement;

public final class C_Movement extends BaseComponent {

	private final U_MovementServoMovement servoMovement;

	public C_Movement(String name) {
		super(name);
		this.servoMovement = new U_MovementServoMovement();

	}

	public void init(MessageBus messageBus) {
		this.servoMovement.start();
	}

	public void close(MessageBus messageBus) {
		this.servoMovement.stop();
	}

	@Override
	protected boolean composeMessage(MessageBus messageBus) {
		return true;
	}

	@Override
	protected void receiveMessage(MessageBus messageBus, I_Message message) {
		if (message instanceof Message) {
			Message m = (Message) message;
			switch (m.getData().charAt(0)) {
			case 'b':
				this.servoMovement.setCurrentMovement(m.getData());
				break;
			case 'a':
				break;
			}
		}
	}
}

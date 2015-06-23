package org.nhl.spoderpod.hexapod.components;

import java.io.IOException;

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
		try {
			char data = (char) System.in.read();
			switch (data) {
			case 'w':
				this.servoMovement.setDirection(1);
				break;
			case 's':
				this.servoMovement.setDirection(-1);
				break;
			case 'a':
				this.servoMovement.setCurrentMovement("Pirouette");
				this.servoMovement.setDirection(-1);
				break;
			case 'd':
				this.servoMovement.setCurrentMovement("Pirouette");
				this.servoMovement.setDirection(1);
				break;
			case ' ':
				this.servoMovement.setCurrentMovement("Idle");
				break;
			case '1':
				this.servoMovement.setCurrentMovement("StraightWalk");
				break;
			case '2':
				this.servoMovement.setCurrentMovement("Grindbak");
				break;
			case '3':
				this.servoMovement.setCurrentMovement("Rondlopen");
				break;
			case '4':
				this.servoMovement.setCurrentMovement("Spidergap");
				break;
			case '5':
				this.servoMovement.setCurrentMovement("Wedstrijdloop");
				break;
			case '6':
				this.servoMovement.setCurrentMovement("Prikken");
				break;
			case '7':
				this.servoMovement.setCurrentMovement("Crabwalk");
			case '-':
				U_MovementServoMovement.SPEED = Math.max(10, U_MovementServoMovement.SPEED - 5);
				break;
			case '+':
				U_MovementServoMovement.SPEED += 5;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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

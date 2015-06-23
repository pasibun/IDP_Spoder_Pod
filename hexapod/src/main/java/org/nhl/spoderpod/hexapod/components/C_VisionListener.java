package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.ComponentRef;
import org.nhl.spoderpod.hexapod.core.Message;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.I_Message;
import org.nhl.spoderpod.hexapod.utils.U_VisionDetectBalloons;

public class C_VisionListener extends BaseComponent {
	private String position;

	public C_VisionListener(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(MessageBus messageBus) {

	}

	@Override
	public void close(MessageBus messageBus) {

	}

	@Override
	protected boolean composeMessage(MessageBus messageBus) {
		return true;
	}

	@Override
	protected void receiveMessage(MessageBus messageBus, I_Message message) {
		if (message instanceof Message) {
			Message m = (Message) message;
			if (m.getData().equals("red") || m.getData().equals("blue")) {
				U_VisionDetectBalloons vision = new U_VisionDetectBalloons(
						m.getData());
				position = vision.getPosition();
				new ComponentRef("C_VisionFormatter").tell(messageBus,
						getSelf(), position);				
			}
		}
	}

}

package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.ComponentRef;
import org.nhl.spoderpod.hexapod.core.Message;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.I_Message;

public class C_VisionFormatter extends BaseComponent {

	private String returnValue;

	public C_VisionFormatter(String name) {
		super(name);
	}

	public void close(MessageBus messageBus) {
		// TODO Auto-generated method stub

	}

	public void init(MessageBus messageBus) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public ComponentRef getSelf() {
		return super.getSelf();
	}

	@Override
	protected boolean composeMessage(MessageBus messageBus) {
		return true;
	}

	@Override
	protected void receiveMessage(MessageBus messageBus, I_Message message) {
		if (message instanceof Message) {
			Message m = (Message) message;
			new ComponentRef("Logger").tell(messageBus, getSelf(),
					dataFormatter(0, 0, 0, ""));
		}
	}

	private String dataFormatter(double x, double y, double z, String position) {
		returnValue = String.format("X= " + "%s" + "Y= " + "%s" + "X= " + "%s"
				+ "Position=" + "%s", x, y, z, position);
		return returnValue;
	}
}

package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.Message;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.IMessage;

public final class CRandomTalker extends BaseComponent {

	public void init(MessageBus messageBus) {
		messageBus.send(new Message(getName(), "Logger-1", new String[] {"testz"}));
	}

	public void close(MessageBus messageBus) {
	}

	public String getName() {
		// TODO Auto-generated method stub
		return "Talker";
	}

	@Override
	protected void update(IMessage message) {
	}
}

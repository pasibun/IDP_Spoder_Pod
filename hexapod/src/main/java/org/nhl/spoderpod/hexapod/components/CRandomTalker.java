package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.ComponentRef;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.IMessage;

public final class CRandomTalker extends BaseComponent {

	public CRandomTalker(String name) {
		super(name);
	}

	public void init(MessageBus messageBus) {
	}

	public void close(MessageBus messageBus) {
	}

	@Override
	protected boolean preReceive(MessageBus messageBus) {
		if (Math.random() < 0.000001) {
			new ComponentRef("Logger").tell(messageBus, getSelf(), "test -> " + Math.random());
		}
		return false;
	}
	
	@Override
	protected void receive(MessageBus messageBus, IMessage message) {
	}
}

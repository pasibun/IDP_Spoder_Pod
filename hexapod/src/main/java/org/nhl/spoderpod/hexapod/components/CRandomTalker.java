package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.ComponentRef;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.IMessage;

public final class CRandomTalker extends BaseComponent {

	public CRandomTalker(String name) {
		super(name);
	}

	public void init(MessageBus messageBus) {
		new ComponentRef("Logger").tell(messageBus, getSelf(), "test");
	}

	public void close(MessageBus messageBus) {
	}

	@Override
	protected void update(MessageBus messageBus, IMessage message) {
	}
}

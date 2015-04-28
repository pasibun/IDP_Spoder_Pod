package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.ComponentRef;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.IComponent;
import org.nhl.spoderpod.hexapod.interfaces.IMessage;

public abstract class BaseComponent implements IComponent {
	private final ComponentRef self;
	
	public BaseComponent(String name) {
		this.self = new ComponentRef(name);
	}

	public final void update(MessageBus messageBus) {
		if (!preReceive(messageBus)) {
			return;
		}
		while (messageBus.hasMessage(this.getSelf())) {
			receive(messageBus, messageBus.getMessage(this.getSelf()));
		}
	}
	
	protected abstract boolean preReceive(MessageBus messageBus);

	protected abstract void receive(MessageBus messageBus, IMessage message);
	
	public ComponentRef getSelf() {
		return this.self;
	}
}

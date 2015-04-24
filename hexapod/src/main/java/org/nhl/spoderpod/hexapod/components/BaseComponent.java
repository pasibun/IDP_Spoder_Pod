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
		while (messageBus.hasMessage(this.getSelf())) {
			update(messageBus, messageBus.getMessage(this.getSelf()));
		}
	}

	protected abstract void update(MessageBus messageBus, IMessage message);
	
	public ComponentRef getSelf() {
		return this.self;
	}
}

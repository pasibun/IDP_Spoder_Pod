package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.Message;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.IComponent;

public abstract class BaseComponent implements IComponent {
	
	public final void update(MessageBus messageBus) {
		while (messageBus.hasMessage(this.getName())) {
			update(messageBus.getMessage(this.getName()));
		}
	}

	protected abstract void update(Message message);
}

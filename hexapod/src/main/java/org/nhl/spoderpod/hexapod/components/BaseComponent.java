package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.ComponentRef;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.I_Component;
import org.nhl.spoderpod.hexapod.interfaces.I_Message;

/**
 * A base class for simple components. It automatically handles receiving messages and referencing.
 * @author achmed
 */
public abstract class BaseComponent implements I_Component {
	private final ComponentRef self;
	
	/**
	 * @param name Name of the component.
	 */
	public BaseComponent(String name) {
		this.self = new ComponentRef(name);
	}

	public final void update(MessageBus messageBus) {
		if (!composeMessage(messageBus)) {
			return;
		}
		while (messageBus.hasMessage(this.getSelf())) {
			receiveMessage(messageBus, messageBus.getMessage(this.getSelf()));
		}
	}
	
	/**
	 * Decide if the component should reveive messages. Also do an update to the component before reveiving messages.
	 * @param messageBus
	 * @return If the component should go receive messages.
	 */
	protected abstract boolean composeMessage(MessageBus messageBus);

	/**
	 * Receive a message from the message bus.
	 * @param messageBus Messagebus of the service.
	 * @param message The current message that get received.
	 */
	protected abstract void receiveMessage(MessageBus messageBus, I_Message message);
	
	public ComponentRef getSelf() {
		return this.self;
	}
}

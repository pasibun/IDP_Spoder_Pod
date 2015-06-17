package org.nhl.spoderpod.hexapod.core;

import java.io.Serializable;

/**
 * Reference abstraction for components. This is so that components don't have a
 * direct memory reference to other components.
 * 
 * @author achmed
 *
 */
public final class ComponentRef implements Serializable {
	private final String location;

	/**
	 * Name of the component.
	 * 
	 * @param location
	 */
	public ComponentRef(String location) {
		this.location = location;
	}

	/**
	 * Send a message to the the component that this object references.
	 * 
	 * Messagebus of the service.
	 * 
	 * The sender of the message.
	 * 
	 * The data of the message.
	 * 
	 * @param messageBus
	 * @param sender
	 * @param text
	 * @return
	 */
	public boolean tell(MessageBus messageBus, ComponentRef sender, String text) {
		return messageBus.send(new Message(sender, this, text));
	}

	public boolean tell(MessageBus messageBus, ComponentRef sender,
			ComponentRef actualRecipient, String text) {
		return messageBus.send(new OutgoingMessage(sender, actualRecipient,
				this, text));
	}

	/**
	 * Get the component name.
	 * 
	 * @return
	 */
	public String getLocation() {
		return this.location;
	}

	@Override
	public String toString() {
		return this.location;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ComponentRef) {
			return this.location.equals(((ComponentRef) obj).getLocation());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.location.hashCode();
	}
}

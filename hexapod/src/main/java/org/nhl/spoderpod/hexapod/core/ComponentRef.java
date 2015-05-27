package org.nhl.spoderpod.hexapod.core;

/**
 * Reference abstraction for components. This is so that components don't have a direct memory reference to other components.
 * @author achmed the dead terrorist
 *
 */
public final class ComponentRef {
	private final String location;
	
	/**
	 * @param location Name of the component.
	 */
	public ComponentRef(String location) {
		this.location = location;
	}
	
	/**
	 * Send a message to the the component that this object references.
	 * @param messageBus Messagebus of the service.
	 * @param sender The sender of the message.
	 * @param text The data of the message.
	 * @return
	 */
	public boolean tell(MessageBus messageBus, ComponentRef sender, String text) {
		return messageBus.send(new Message(sender, this, text));
	}
	
	/**
	 * Get the component name.
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
			return this.location.equals(((ComponentRef)obj).getLocation());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.location.hashCode();
	}
}

package org.nhl.spoderpod.hexapod.core;

public final class ComponentRef {
	private final String location;
	
	public ComponentRef(String location) {
		this.location = location;
	}
	
	public boolean tell(MessageBus messageBus, ComponentRef sender, String text) {
		return messageBus.send(new Message(sender, this, new String[]{text}));
	}
	
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

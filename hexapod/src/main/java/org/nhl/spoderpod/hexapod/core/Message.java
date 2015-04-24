package org.nhl.spoderpod.hexapod.core;

import org.nhl.spoderpod.hexapod.interfaces.IMessage;

public final class Message implements IMessage {
	private final ComponentRef sender;
	private final ComponentRef recipient;
	private final String data;

	public Message(ComponentRef sender, ComponentRef recipient, String data) {
		this.sender = sender;
		this.recipient = recipient;
		this.data = data;
	}

	public ComponentRef getSender() {
		return this.sender;
	}

	public ComponentRef getRecipient() {
		return this.recipient;
	}

	public String getData() {
		return this.data;
	}

	@Override
	public String toString() {
		return String.format("Sender: %s, Recipient: %s, DataCount: %d",
				this.sender, this.recipient, this.data);
	}
}

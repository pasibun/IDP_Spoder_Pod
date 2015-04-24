package org.nhl.spoderpod.hexapod.core;

import org.nhl.spoderpod.hexapod.interfaces.IMessage;

public final class Message implements IMessage {
	private final String sender;
	private final String recipient;
	private final String[] data;

	public Message(String sender, String recipient, String[] data) {
		this.sender = sender;
		this.recipient = recipient;
		this.data = data;
	}

	public String getSender() {
		return this.sender;
	}

	public String getRecipient() {
		return this.recipient;
	}

	public String[] getData() {
		return this.data;
	}

	@Override
	public String toString() {
		return String.format("Sender: %s, Recipient: %s, DataCount: %d",
				this.sender, this.recipient, this.data.length);
	}
}

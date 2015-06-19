package org.nhl.spoderpod.hexapod.core;

import org.nhl.spoderpod.hexapod.interfaces.I_Message;

/**
 * Simple IMessage implementation. This message has a String as data.
 * 
 * @author achmed
 *
 */
public final class Message implements I_Message {
	private static final long serialVersionUID = 1L;

	private final ComponentRef sender;
	private final ComponentRef recipient;
	private final String data;

	/**
	 * Reference to the sender.
	 * 
	 * Reference to the recipient.
	 * 
	 * The message data.
	 * 
	 * @param sender
	 * @param recipient
	 * @param data
	 */
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
		return String.format("Sender: %s, Recipient: %s, DataCount: %s",
				this.sender, this.recipient, this.data);
	}
}

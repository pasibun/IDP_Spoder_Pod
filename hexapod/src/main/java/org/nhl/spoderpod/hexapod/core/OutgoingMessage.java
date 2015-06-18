package org.nhl.spoderpod.hexapod.core;

import org.nhl.spoderpod.hexapod.interfaces.I_Message;

/**
 * Simple IMessage implementation. This message has a String as data.
 * 
 * @author achmed
 *
 */
public final class OutgoingMessage implements I_Message {
	private static final long serialVersionUID = 1L;

	private final ComponentRef sender;
	private final ComponentRef recipient;
	private final ComponentRef actualrecipient;
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
	public OutgoingMessage(ComponentRef sender, ComponentRef recipient,
			ComponentRef actualrecipient, String data) {
		this.sender = sender;
		this.recipient = recipient;
		this.actualrecipient = actualrecipient;
		this.data = data;
	}

	public ComponentRef getSender() {
		return this.sender;
	}

	public ComponentRef getRecipient() {
		return this.recipient;
	}

	public ComponentRef getActualrecipient() {
		return actualrecipient;
	}

	/**
	 * Get the data of the message.
	 * 
	 * @return The data.
	 */
	public String getData() {
		return this.data;
	}

	@Override
	public String toString() {
		return String.format("Sender: %s, Recipient: %s, DataCount: %s",
				this.sender, this.recipient, this.data);
	}
}

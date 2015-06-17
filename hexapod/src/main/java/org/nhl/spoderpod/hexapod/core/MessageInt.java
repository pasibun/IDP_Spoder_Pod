package org.nhl.spoderpod.hexapod.core;

import org.nhl.spoderpod.hexapod.interfaces.I_Message;

/**
 * Simple IMessage implementation. This message has a String as data.
 * 
 * @author matthijs_laptop
 *
 */
public final class MessageInt implements I_Message {
	private static final long serialVersionUID = 1L;

	private final ComponentRef sender;
	private final ComponentRef recipient;
	private final int[] data;

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
	public MessageInt(ComponentRef sender, ComponentRef recipient, int[] data) {
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

	public int[] getData() {
		return this.data;
	}

	@Override
	public String toString() {
		return String.format("Sender: %s, Recipient: %s, DataCount: %s",
				this.sender, this.recipient, this.data);
	}
}

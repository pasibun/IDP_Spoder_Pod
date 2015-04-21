package org.nhl.spoderpod.hexapod.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class MessageBus {
	private final Map<String, Queue<Message>> messages;

	public MessageBus() {
		this.messages = new HashMap<String, Queue<Message>>();
	}

	public void addComponent(String recipient) {
		this.messages.put(recipient, new ConcurrentLinkedQueue<Message>());
	}

	public boolean send(Message message) {
		if (this.messages.containsKey(message.getRecipient())) {
			this.messages.get(message.getRecipient()).add(message);
			return true;
		}
		return false;
	}

	public Message getMessage(String recipient) {
		return this.messages.get(recipient).poll();
	}

	public boolean hasMessage(String recipient) {
		return this.messages.containsKey(recipient)
				&& !this.messages.get(recipient).isEmpty();
	}
}

package org.nhl.spoderpod.hexapod.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.nhl.spoderpod.hexapod.interfaces.IMessage;

public final class MessageBus {
	private final Map<ComponentRef, Queue<IMessage>> messages;

	public MessageBus() {
		this.messages = new HashMap<ComponentRef, Queue<IMessage>>();
	}

	public void addComponent(ComponentRef recipient) {
		this.messages.put(recipient, new ConcurrentLinkedQueue<IMessage>());
	}

	public boolean send(IMessage message) {
		if (this.messages.containsKey(message.getRecipient())) {
			this.messages.get(message.getRecipient()).add(message);
			return true;
		}
		return false;
	}

	public IMessage getMessage(ComponentRef recipient) {
		return this.messages.get(recipient).poll();
	}

	public boolean hasMessage(ComponentRef recipient) {
		return this.messages.containsKey(recipient)
				&& !this.messages.get(recipient).isEmpty();
	}
}

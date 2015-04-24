package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.Message;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.IMessage;

public final class CLogger extends BaseComponent {
	private final StringBuilder log;
	
	public CLogger(String name) {
		super(name);
		this.log = new StringBuilder();
	}

	public void init(MessageBus messageBus) {
	}

	public void update(MessageBus messageBus, IMessage message) {
		if (message instanceof Message) {
			Message m = (Message)message;
			this.log.append(String.format("%s, From: %s, Data: %s", getSelf(), m.getSender(), m.getData()));
		}
	}

	public void close(MessageBus messageBus) {
		System.out.println(this.log.toString());
	}
}

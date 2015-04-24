package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.IMessage;

public final class CLogger extends BaseComponent {
	public CLogger(String name) {
		super(name);
	}

	public void init(MessageBus messageBus) {
	}

	public void update(IMessage message) {
		System.out.println(String.format("%s: %s", getSelf(), "GotMessage"));
	}

	public void close(MessageBus messageBus) {
	}
}

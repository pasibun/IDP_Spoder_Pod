package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.IMessage;

public final class CLogger extends BaseComponent {
	private final int id;

	public CLogger(int id) {
		this.id = id;
	}

	public void init(MessageBus messageBus) {
	}

	public void update(IMessage message) {
		System.out.println(String.format("%s: %s", getName(), "GotMessage"));
	}

	public void close(MessageBus messageBus) {
	}

	public String getName() {
		return "Logger-" + id;
	}
}

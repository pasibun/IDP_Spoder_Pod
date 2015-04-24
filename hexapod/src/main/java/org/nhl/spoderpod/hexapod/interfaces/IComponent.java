package org.nhl.spoderpod.hexapod.interfaces;

import org.nhl.spoderpod.hexapod.core.MessageBus;

public interface IComponent {
	public void init(MessageBus messageBus);

	public void update(MessageBus messageBus);

	public void close(MessageBus messageBus);

	public String getName();
}

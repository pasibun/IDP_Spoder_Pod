package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.ComponentRef;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.IMessage;

/**
 * Test component that randomly sends the current time to the logger component.
 * @author achmed
 *
 */
public final class CRandomTalker extends BaseComponent {

	/**
	 * @param name Name of the component.
	 */
	public CRandomTalker(String name) {
		super(name);
	}

	public void init(MessageBus messageBus) {
	}

	public void close(MessageBus messageBus) {
	}

	@Override
	protected boolean composeMessage(MessageBus messageBus) {
		if (Math.random() < 0.000001) {
			new ComponentRef("Logger").tell(messageBus, getSelf(), "Time is: " + System.currentTimeMillis());
		}
		return false;
	}
	
	@Override
	protected void receiveMessage(MessageBus messageBus, IMessage message) {
	}
}

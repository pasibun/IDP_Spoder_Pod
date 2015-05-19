package org.nhl.spoderpod.hexapod.interfaces;

import org.nhl.spoderpod.hexapod.core.ComponentRef;
import org.nhl.spoderpod.hexapod.core.MessageBus;

/**
 * Interface for a component.
 * @author achmed
 */
public interface IComponent {
	
	/**
	 * Initializes component. Gets called on service startup.
	 * @param messageBus Messagebus of the service
	 */
	public void init(MessageBus messageBus);

	/**
	 * Update the component
	 * @param messageBus Messagebus of the service
	 */
	public void update(MessageBus messageBus);

	/**
	 * Closes the component. Should close all open io-connections. Gets called on service shutdown.
	 * @param messageBus
	 */
	public void close(MessageBus messageBus);
	
	/**
	 * Get reference to the component.
	 * @return The component reference
	 */
	public ComponentRef getSelf();
}

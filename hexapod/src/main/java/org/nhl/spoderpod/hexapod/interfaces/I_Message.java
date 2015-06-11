package org.nhl.spoderpod.hexapod.interfaces;

import java.io.Serializable;

import org.nhl.spoderpod.hexapod.core.ComponentRef;

/**
 * Interface for Messages.
 * @author achmed
 */
public interface I_Message extends Serializable {
	/**
	 * Get the sender of the message.
	 * @return The reference to the sender
	 */
	public ComponentRef getSender();

	/**
	 * Get the recipient of the message
	 * @return The reference to the recipient
	 */
	public ComponentRef getRecipient();	
	
}

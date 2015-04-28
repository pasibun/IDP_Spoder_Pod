package org.nhl.spoderpod.hexapod.interfaces;

import java.io.Serializable;

import org.nhl.spoderpod.hexapod.core.ComponentRef;

public interface IMessage extends Serializable {
	public ComponentRef getSender();

	public ComponentRef getRecipient();
}

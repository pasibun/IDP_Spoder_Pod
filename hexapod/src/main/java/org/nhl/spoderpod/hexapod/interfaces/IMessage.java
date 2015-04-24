package org.nhl.spoderpod.hexapod.interfaces;

import java.io.Serializable;

public interface IMessage extends Serializable {
	public String getSender();

	public String getRecipient();
}

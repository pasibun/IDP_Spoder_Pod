package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.I_Message;

public final class C_Movement extends BaseComponent {

	public C_Movement(String name){
		super(name);
	}
	
	public void init(MessageBus messageBus) {
		// TODO Auto-generated method stub

	}

	public void close(MessageBus messageBus) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean composeMessage(MessageBus messageBus) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void receiveMessage(MessageBus messageBus, I_Message message) {
		// TODO Auto-generated method stub

	}

}

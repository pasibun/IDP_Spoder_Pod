package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.IMessage;

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
	protected boolean preReceive(MessageBus messageBus) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void receive(MessageBus messageBus, IMessage message) {
		// TODO Auto-generated method stub

	}

}

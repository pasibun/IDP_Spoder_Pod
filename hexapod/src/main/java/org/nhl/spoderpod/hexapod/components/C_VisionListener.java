package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.ComponentRef;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.I_Message;
import org.nhl.spoderpod.hexapod.utils.U_VisionDetectBalloons;

public class C_VisionListener extends BaseComponent  {

	public C_VisionListener(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(MessageBus messageBus) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close(MessageBus messageBus) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean composeMessage(MessageBus messageBus) {
		
		return false;
	}

	@Override
	protected void receiveMessage(MessageBus messageBus, I_Message message) {
		// TODO Auto-generated method stub
		
	}

}

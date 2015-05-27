package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.ComponentRef;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.I_Message;
import org.nhl.spoderpod.hexapod.utils.U_SensorReadWrite;

public class C_SensorReader extends BaseComponent {

	private U_SensorReadWrite u_SensorRW;
	
	public C_SensorReader(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public void init(MessageBus messageBus) {
		this.u_SensorRW = new U_SensorReadWrite();
		
	}

	public void close(MessageBus messageBus) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean composeMessage(MessageBus messageBus) {
		new ComponentRef("DataFormat").tell(messageBus, getSelf(), "afstand");
		return true;
	}

	@Override
	protected void receiveMessage(MessageBus messageBus, I_Message message) {
		// TODO Auto-generated method stub
		
	}
	
	

}

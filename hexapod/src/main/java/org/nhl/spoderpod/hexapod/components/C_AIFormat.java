package org.nhl.spoderpod.hexapod.components;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.nhl.spoderpod.hexapod.core.ComponentRef;
import org.nhl.spoderpod.hexapod.core.Message;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.I_Message;

public class C_AIFormat extends BaseComponent {

	public C_AIFormat(String strName) {
		super(strName);
	}

	public void init(MessageBus messageBus) {
		// TODO Auto-generated method stub
	}

	public void close(MessageBus messageBus) {
		// TODO Auto-generated method stub
	}

	@Override
	protected boolean composeMessage(MessageBus messageBus) {
		new ComponentRef("C_Logger").tell(messageBus, getSelf(), new ComponentRef("C_RouterClient"), "hi");	
		return true;
	}

	@Override
	protected void receiveMessage(MessageBus messageBus, I_Message message) {
		if (message instanceof Message) {
			Message m = (Message) message;
			new ComponentRef("C_Logger").tell(messageBus, getSelf(), new ComponentRef("C_RouterClient"), strDataFormat(m.getData()));	
		}
	}

	private String strDataFormat(String direction) {

		String returnValue = "Error";
		if (direction.equals(null)){
			return returnValue;
		}
		returnValue = String.format(
				"{\"AI Service\": { \"Latest Direction\": \"%s\"}", direction);

		return returnValue;

	}

}

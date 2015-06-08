package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.ComponentRef;
import org.nhl.spoderpod.hexapod.core.Message;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.I_Message;
import org.nhl.spoderpod.hexapod.utils.U_ControlState;

public class C_AIFormat extends BaseComponent {

	public C_AIFormat(String strName) {
		super(strName);
		// TODO Auto-generated constructor stub
	}

	public void init(MessageBus messageBus) {
		// TODO Auto-generated method stub

	}

	public void close(MessageBus messageBus) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean composeMessage(MessageBus messageBus) {
		return true;
	}

	@Override
	protected void receiveMessage(MessageBus messageBus, I_Message message) {
		if (message instanceof Message) {
			Message m = (Message) message;
			System.out.println(m.getData());
			// new ComponentRef("RouterClient").tell(messageBus, getSelf(),
			// strDataFormat(m.getData()) );
		}
	}

	private String strDataFormat(String direction) {

		String returnValue = "Error in SensorFormatter class: Sensorservice. sensorName is neither 'afstand' or 'gyro'";

		returnValue = String.format(
				"{\"AI Service\": { \"Latest Direction\": \"%s\"}", direction);

		return returnValue;

	}

}

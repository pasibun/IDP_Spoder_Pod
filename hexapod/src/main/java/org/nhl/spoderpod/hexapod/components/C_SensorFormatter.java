package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.ComponentRef;
import org.nhl.spoderpod.hexapod.core.Message;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.I_Message;

public class C_SensorFormatter extends BaseComponent {

	public C_SensorFormatter(String name) {
		super(name);
	}

	public void init(MessageBus messageBus) {
	}

	public void close(MessageBus messageBus) {
	}

	@Override
	protected boolean composeMessage(MessageBus messageBus) {
		return true;
	}

	/***
	 * Fires when Formatter receives a message.
	 */
	@Override
	protected void receiveMessage(MessageBus messageBus, I_Message message) {
		if (message instanceof Message) {
			Message m = (Message) message;
			new ComponentRef("Logger").tell(messageBus, getSelf(),
					new ComponentRef("RouterClient"),
					strDataFormat(m.getData(), 0, 0));

		}
	}

	private String strDataFormat(String sensorName, double dblVal,
			int intSenseId) {

		String returnValue = "Error in SensorFormatter class: Sensorservice. sensorName is neither 'afstand' or 'gyro'";
		String strMeasurement = (sensorName.equals("afstand")) ? "Distance"
				: "Gradient";

		returnValue = String
				.format("{\"SensorService\": { \"ID\": \"%s\", \"Type\": \"%s\", \"%s\": \"%f\"}}",
						intSenseId, sensorName, strMeasurement, dblVal);
		return returnValue;

	}

}

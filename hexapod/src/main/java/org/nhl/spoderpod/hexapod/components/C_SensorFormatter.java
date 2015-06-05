package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.ComponentRef;
import org.nhl.spoderpod.hexapod.core.Message;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.I_Message;
import org.nhl.spoderpod.hexapod.utils.U_ControlState;

public class C_SensorFormatter extends BaseComponent {

	public C_SensorFormatter(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public void init(MessageBus messageBus) {
		// TODO Auto-generated method stub
		
	}
	
	public void close(MessageBus messageBus) {
		// TODO Auto-generated method stub
	}

	@Override
	/***
	 * 
	 */
	protected boolean composeMessage(MessageBus messageBus) {
		return true;
	}

	@Override
	/***
	 * Fires when Formatter receives a message.
	 */
	protected void receiveMessage(MessageBus messageBus, I_Message message) {
		if (message instanceof Message) {
			Message m = (Message) message;
			new ComponentRef("Logger").tell(messageBus, getSelf(), strDataFormat(m.getData(), 0, 0));	
			
		}
	}
	
	private String strDataFormat(String sensorName, double dblVal, int intSenseId){
		
		String returnValue = "Error in SensorFormatter class: Sensorservice. sensorName is neither 'afstand' or 'gyro'";
		String strMeasurement = (sensorName.equals("afstand")) ? "Distance" : "Gradient";
		
		returnValue = String.format("{\"SensorService\": { \"ID\": \"%s\", \"Type\": \"%s\", \"%s\": \"%f\"}}" ,
							intSenseId, sensorName, strMeasurement, dblVal );
		return returnValue;
			
	}

}

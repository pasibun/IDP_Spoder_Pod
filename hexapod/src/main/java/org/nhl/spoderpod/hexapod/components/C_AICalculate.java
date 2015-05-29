package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.ComponentRef;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.I_Message;

public class C_AICalculate extends BaseComponent {

	private String lastCommand; 
	private String target; 

	public C_AICalculate(String strName) {
		super(strName);
	}
	
	/***
	 * Method calculates the direction the spider has to walk to when it gets a message. 
	 * 
	 * @param Dist0 First identified distance sensor.
	 * @param Dist1 Second identified distance sensor.
	 * @param Gyro0 First identified gyro sensor data. 
	 * @return string based answer of the direction the spider has to walk to. 
	 * TODO: finish, like really do anything. 
	 */
	private String calcDirection(double dist0, double dist1, double gyro0){
		if((dist0 < 60 || dist1 < 60) && lastCommand.equals("left")){
			lastCommand = "left";
			return "left";
		}
		else if(lastCommand.equals("left"))
		{
			lastCommand = "right";
			return "right";
		}
		lastCommand = "forward";
		return "forward";
	}
	
	public void init(MessageBus messageBus) {
		// TODO Auto-generated method stub
		
	}

	public void close(MessageBus messageBus) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean composeMessage(MessageBus messageBus) {
		new ComponentRef("Formatter").tell(messageBus, getSelf(), lastCommand);
		return true;
	}

	@Override
	protected void receiveMessage(MessageBus messageBus, I_Message message) {
		// TODO Auto-generated method stub
		
	}

}

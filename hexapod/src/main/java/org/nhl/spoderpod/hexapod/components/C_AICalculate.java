package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.ComponentRef;
import org.nhl.spoderpod.hexapod.core.DataPackage;
import org.nhl.spoderpod.hexapod.core.Message;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.I_Message;

public class C_AICalculate extends BaseComponent {
	private String lastCommand;
	private int mode = 0;
	private double centerDistance = 0;

	public C_AICalculate(String strName) {
		super(strName);
	}

	public int getSensorData() {
		return 0;
	}

	public void init(MessageBus messageBus) {
	}

	public void close(MessageBus messageBus) {
	}

	@Override
	protected boolean composeMessage(MessageBus messageBus) {
		return true;
	}

	@Override
	protected void receiveMessage(MessageBus messageBus, I_Message message) {
		if (message instanceof Message) {
			Message m = (Message) message;
			// if SensorReader en modus is
			switch (m.getSender().toString()){
			case "C_SensorReader":
				switch (mode) {
				case 0: // nonActive
					new ComponentRef("C_Logger").tell(messageBus, getSelf(),
							new ComponentRef("C_RouterClient"), "AI Service is INACTIVE.");
					break;
				case 1: // balloon
					new ComponentRef("C_Movement").tell(messageBus, getSelf(),
							new ComponentRef("C_RouterClient"), mode_balloonWalk(Integer.parseInt(m.getData())));
					break;
				case 2: // pole
					new ComponentRef("C_Movement").tell(messageBus, getSelf(),
							new ComponentRef("C_RouterClient"), mode_poleWalk(Integer.parseInt(m.getData())));
					break;
				default:
					break;
				}
				break;
			case "C_ControlCheck":
				switch(m.getData()){
				case "AIState":
					mode = 3;
					break;
				case "HumanState":
					mode = 0;
					break;
				case "BalloonState":
					mode = 1;
					break;
				case "PoleState":
					mode = 2;
					break;
				}
				break;
			case "C_VisionListener": //moves for VisionService
				centerDistance = Integer.parseInt(m.getData());
				break;
			default:
				System.out
						.println("Message Sender is unknown to C_AICalculate");
				break;
			}
		}
	}

	private String mode_balloonWalk(int intSensorData) {
		if(intSensorData < 10){
			return "Prikken";
		}
		if(centerDistance > 0.75){ //ballon zit rechts van center
			return "Right";
		}
		else if(centerDistance < 0.25){ //ballon zit links van center. 
			return "Left";
		}
		else {
			return "WalkState";
		}
	}

	private String mode_poleWalk(int SensorData) {
		return null;
	}
	
	private String mode_raceWalk(int SensorData) {
		return null;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}

}

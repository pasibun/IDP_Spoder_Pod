package org.nhl.spoderpod.hexapod.components;

import java.util.Random;

import org.nhl.spoderpod.hexapod.core.ComponentRef;
import org.nhl.spoderpod.hexapod.core.Message;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.I_Message;

public class C_AICalculate extends BaseComponent {
	private String lastCommand;
	private final int intPoleDist = 70;
	private double centerDistance = 0;
	private int mode = 0;
	private String color;

	public C_AICalculate(String strName) {
		super(strName);
	}

	public void init(MessageBus messageBus) {
	}

	public void close(MessageBus messageBus) {
	}

	@Override
	protected boolean composeMessage(MessageBus messageBus) {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * When receiving a message determine the mode's or meh fuck it Hidde fix
	 * deze comment.
	 */
	@Override
	protected void receiveMessage(MessageBus messageBus, I_Message message) {
		if (message instanceof Message) {
			Message m = (Message) message;
			// if SensorReader en modus is
			switch (m.getSender().toString()) {
			case "C_SensorReader":
				switch (mode) {
				case 0: // nonActive
					new ComponentRef("C_Logger").tell(messageBus, getSelf(),
							new ComponentRef("C_RouterClient"), "INACTIVE.");
					break;
				case 1: // balloon rb
					new ComponentRef("C_VisionListener").tell(messageBus,
							getSelf(), new ComponentRef("C_RouterClient"),
							"red");
					color = "red";

					new ComponentRef("C_Movement").tell(messageBus, getSelf(),
							new ComponentRef("C_RouterClient"),
							mode_balloonWalk(Integer.parseInt(m.getData()), messageBus));
					break;
				case 2: // dance
					new ComponentRef("C_Movement").tell(messageBus, getSelf(),
							new ComponentRef("C_RouterClient"), "Dance");
					break;
				case 3: // pole
					new ComponentRef("C_Movement").tell(messageBus, getSelf(),
							new ComponentRef("C_RouterClient"),
							"bCrabWalk");
					
					String walkState = "aRight";
					//if distance to pole is bigger than allowed.
					if (Integer.parseInt(m.getData()) > intPoleDist) {
						new ComponentRef("C_Movement").tell(messageBus, getSelf(),
								new ComponentRef("C_RouterClient"),
								"bPirouette");
						walkState = "aLeft"; //rotate left
						
					} else if (Integer.parseInt(m.getData()) < intPoleDist) {
						new ComponentRef("C_Movement").tell(messageBus, getSelf(),
								new ComponentRef("C_RouterClient"),
								"bCrabWalk");
						
					}
					new ComponentRef("C_Movement").tell(messageBus, getSelf(),
							new ComponentRef("C_RouterClient"),
							walkState);
					
					break;
				case 4:// balloon br
					new ComponentRef("C_VisionListener").tell(messageBus,
							getSelf(), new ComponentRef("C_RouterClient"),
							"blue");
					
					color = "blue";
					
					new ComponentRef("C_Movement").tell(messageBus, getSelf(),
							new ComponentRef("C_RouterClient"),

							mode_balloonWalk(Integer.parseInt(m.getData()), messageBus));
					break;				

				default:
					System.out
							.println("Mode has defaulted :: C_AICalculate.receiveMessage().C_SensorReader.mode - 68");
					break;
				}
				break;
			case "C_ControlCheck":
				switch (m.getData()) {
				case "BaloonState rb": // mode 1
					mode = 1;
					break;
				case "BaloonState br": // mode 4
					mode = 4;
					break;
				case "DanceState": // mode 2
					mode = 2;
					break;
				case "PoleState": // mode 3
					mode = 3;
					break;
				default: // sets it to NONACTIVE.
					mode = 0;
					break;
				}
				break;
			case "C_VisionListener": // moves for VisionService
				centerDistance = Integer.parseInt(m.getData());
				break;
			default:
				System.out
						.println("Message Sender defaulted :: C_AICalculate.receiveMessage().m.getSender().toString() - 92");

				break;
			}
		}
	}

	private String mode_balloonWalk(int intSensorData, MessageBus messageBus) {
		if (intSensorData < 10) {
			switch(color){		
			
			case "red":
				new ComponentRef("C_VisionListener").tell(messageBus,
						getSelf(), new ComponentRef("C_RouterClient"),
						"blue");
				break;
				
			case "blue":
				new ComponentRef("C_VisionListener").tell(messageBus,
						getSelf(), new ComponentRef("C_RouterClient"),
						"red");
				break;
				
			default :
				System.out.println("Error");
				break;
			}
			return "bPrikken";
		}
		if (centerDistance > 0.75) { // ballon zit rechts van center
			return "aRight";
		} else if (centerDistance < 0.25) { // ballon zit links van center.
			return "aLeft";
		} else if (centerDistance > 0.25 && centerDistance < 0.75) {
			return "aForward";
		} else {
			System.out
					.println("Something Went Wrong! :: C_AICALCUALTE.mode_balloonWalk.centerDistance - 110");
		}
		return "bPirouette";
	}

	private String mode_havingFun(int intSensorData) {
		Random rand = new Random();
		Double random = rand.nextDouble();
		if (intSensorData < 20) {
			return "aBack";
		}

		if (random < 0.2) {
			return "aLeft";
		} else if (random < 0.4) {
			return "aRight";
		} else if (random < 0.9) {
			return "aForward";
		} else {
			return "aBack";
		}
	}

	@Override
	public String toString() {
		return super.toString();
	}

}

package org.nhl.spoderpod.hexapod.components;

import java.io.IOException;
import java.util.List;

import org.nhl.spoderpod.hexapod.core.ComponentRef;
import org.nhl.spoderpod.hexapod.core.DataPackage;
import org.nhl.spoderpod.hexapod.core.Message;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.I_Message;
import org.nhl.spoderpod.hexapod.libraries.L_Decoder;
import org.nhl.spoderpod.hexapod.libraries.L_FileActions;

public class C_SensorReader extends BaseComponent {

	private int mode = 0;

	public C_SensorReader(String name) {
		super(name);
	}

	public void init(MessageBus messageBus) {
	}

	public void close(MessageBus messageBus) {
	}

	/***
	 * ComposeMessage for the this object makes sure that incoming datapackages
	 * are dissected based on the type. And are send to the correct service.
	 */

	/**
	 * Method calculates the direction the spider has to walk to when it gets a
	 * message.
	 * 
	 * TODO: Case 5 moet er wat gebeuren met de data die binnenkomt, dit is van
	 * de joystick. Dit wordt verstuurd door lukas in een short met x en y
	 * waarden. Vraag even aan fre hoe het er precies uitziet en aan de hand
	 * daarvan moet hij de juist gegevens doorsturen naar de movement server.
	 * (dit mag gewoon vooruit, links, rechts, stop ofzo zijn.
	 * 
	 * TODO: Case 6 zijn de buttons, als het goed is 1 ID een sla beweging om de
	 * ballonnen kapot te maken. (vraag aan yannick hoe de data in de movement
	 * server binnen moet komt).
	 * 
	 * TODO: Case 3 is geloof ik prima, dit is de afstand sensor. Volgens mij is
	 * dit prima verder, vraag nog even aan yannink/fre? hoe hij de waarden moet
	 * krijgen in de movement server.
	 * 
	 * Info: Laatste puntje ik heb MessageInt aangemaakt zodat de data een int
	 * is. Ik weet nu even niet of de data vanuit de C_SensorReader een andere
	 * format moet hebben. Misschien moeten hier de "[" en "]" er uit. Overleg
	 * dit even met Lukas.
	 * 
	 * @param message
	 * @return string based answer of the direction the spider has to walk to.
	 */

	@Override
	protected boolean composeMessage(MessageBus messageBus) {
		String strReceiver = "C_Logger";
		int intData, intId, intType;
		List<Byte> i = null;
		try {
			i = L_FileActions.read();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (i.size() > 0) {
			L_Decoder.recieveMsg(i);
		}
		for (DataPackage dp : L_Decoder.getData()) {
			intType = dp.get_byteType();
			intData = dp.get_shortData();
			intId = dp.get_byteId();
			switch (intType) { // terugkrijgende string wijst naar de service.
			case 2: // Servo
				new ComponentRef("C_Logger").tell(messageBus, getSelf(),
						new ComponentRef("C_RouterClient"), String.format(
								"Servo:{ID: %s}{Health: %s}", intId, intData));
				break;
			case 3: // Sensor moet data naar AICalculate sturen voor informatie.
					// De modus daar bepaalt wat er gebeurt.
				new ComponentRef("C_AICalculate").tell(messageBus, getSelf(),
						new ComponentRef("C_RouterClient"),
						String.format("%s %s", intId, intData));
				break;
			case 4: // Gyro
				new ComponentRef("C_Logger").tell(messageBus, getSelf(),
						new ComponentRef("C_RouterClient"),
						String.format("Gyro:{Helling: %s}", intData));
				break;
			case 5: // joystick
				movementSorter(messageBus, intId, intData);
				break;
			case 6: // Buttons
				if (intId == 0) {
				new ComponentRef("C_Movement").tell(messageBus, getSelf(),
						new ComponentRef("C_RouterClient"), "cFaster");
				}else if (intId == 1){
					new ComponentRef("C_Movement").tell(messageBus, getSelf(),
							new ComponentRef("C_RouterClient"), "cSlower");
				}
//				}else if (intId == 2 && intData >= 1) {
//					new ComponentRef("C_Movement").tell(messageBus, getSelf(),
//							new ComponentRef("C_RouterClient"), "bIdle");
//				}
				break;
			case 7:// Touchpad
				intData--;
				switch (intData) {
				case 0:// walking
					new ComponentRef("C_Movement")
							.tell(messageBus, getSelf(), new ComponentRef(
									"C_RouterClient"), "bStraightWalk");
					break;
				case 1:// crabwalk
					new ComponentRef("C_Movement").tell(messageBus, getSelf(),
							new ComponentRef("C_RouterClient"), "bCrabWalk");
					break;
				case 2:// balloon red/blue
					new ComponentRef("C_ControlCheck").tell(messageBus,
							getSelf(), new ComponentRef("C_RouterClient"), "4");
					break;
				case 3:// balloon blue/red
					new ComponentRef("C_ControlCheck").tell(messageBus,
							getSelf(), new ComponentRef("C_RouterClient"), "3");
					break;
				case 4:// stairwalk
					new ComponentRef("C_Movement").tell(messageBus, getSelf(),
							new ComponentRef("C_RouterClient"), "bStairWalk");

					break;
				case 5: // SpiderGap
					new ComponentRef("C_Movement").tell(messageBus, getSelf(),
							new ComponentRef("C_RouterClient"), "bSpidergap");
					break;
				case 6: // speedwalk
					new ComponentRef("C_Movement").tell(messageBus, getSelf(),
							new ComponentRef("C_RouterClient"), "bSpeedWalk");
					break;
				case 7: // dance
					new ComponentRef("C_Movement").tell(messageBus,
							getSelf(), new ComponentRef("C_RouterClient"), "bDance");
					break;
				}
				break;
			default:
				System.out
						.format("Unknown type! :: C_SensorReader.composeMessage().dp.intType - %s\n",
								dp);
				break;
			}
		}
		return true;
	}

	private void movementSorter(MessageBus messageBus, int id, int intData) {
		String movement = "aIdle";
		if (id == 1) {
			if (intData > 3 * (1024 / 4)) {
				movement = "aRight";
			} else if (intData < 1 * (1024 / 4)) {
				movement = "aLeft";
			}
			new ComponentRef("C_Movement").tell(messageBus, getSelf(),
					new ComponentRef("C_RouterClient"), movement);
		} else if (id == 0) {
			if (intData > 3 * (1024 / 4)) {
				movement = "aForward";
			} else if (intData < 1 * (1024 / 4)) {
				movement = "aBack";
			}
			new ComponentRef("C_Movement").tell(messageBus, getSelf(),
					new ComponentRef("C_RouterClient"), movement);
		}
	}

	private byte[] convertByte(int data) {
		byte[] value = new byte[2];
		value[0] = ((byte) ((data >> 8) & 0xff));
		value[1] = (byte) ((byte) (data) & 0xff);
		return value;
	}

	@Override
	protected void receiveMessage(MessageBus messageBus, I_Message message) {
		if (message instanceof Message) {
			Message m = (Message) message;
			if (m.getSender().equals("C_ControlCheck")) {
				mode = Integer.parseInt(m.getData());
			}
		}

	}
}
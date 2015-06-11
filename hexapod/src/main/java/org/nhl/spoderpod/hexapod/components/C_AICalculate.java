package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.DataPackage;
import org.nhl.spoderpod.hexapod.core.Message;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.core.MessageInt;
import org.nhl.spoderpod.hexapod.interfaces.I_Message;

public class C_AICalculate extends BaseComponent {
	private int[] shit;
	private String lastCommand;

	public C_AICalculate(String strName) {
		super(strName);
	}

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
	private String calcDirection(MessageInt message) {
		if (message instanceof MessageInt) {
			MessageInt m = (MessageInt) message;
			switch (shit[0]) {
			case 3:
				/**
				 * als de waarde van de sensor server kleiner is dan 3 dan moet
				 * moet hij stoppen, de afstand sensor kan niet onder de 3 cm
				 * meeten.
				 */
				if (shit[2] > 3)
					return "Vooruit";
				else
					return "Stop";
			case 5:

				break;
			case 6:
				if (shit[1] == 0) {
					// doe dingen met id 0
				} else if (shit[1] == 1) {
					// doe dingen met id 1
				}
				break;
			}
		}

		return "";
	}

	/**
	 * Calls everything.
	 * 
	 * @return ProtocolMessage for Movement Service.
	 */
	private DataPackage fetchCommand() {
		return null;
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
		// update this to send it to Movement Service command and control.
		// new ComponentRef("AppSocket").tell(messageBus, getSelf(), new
		// ComponentRef("RouterClient"), ));
		// new ComponentRef("C_AIFormat").tell(messageBus, getSelf(),
		// lastCommand);

		return true;
	}

	@Override
	protected void receiveMessage(MessageBus messageBus, I_Message message) {
		if (message instanceof MessageInt) {
			MessageInt m = (MessageInt) message;
			shit = m.getData();
			calcDirection(m);
		}
	}

	@Override
	public String toString() {
		return super.toString();
	}

}

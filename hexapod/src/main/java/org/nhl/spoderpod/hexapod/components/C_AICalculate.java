package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.ComponentRef;
import org.nhl.spoderpod.hexapod.core.Message;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.core.ProtMessage;
import org.nhl.spoderpod.hexapod.interfaces.I_Message;

public class C_AICalculate extends BaseComponent {

	private String lastCommand;

	public C_AICalculate(String strName) {
		super(strName);
	}

	/**
	 * Method calculates the direction the spider has to walk to when it gets a
	 * message.
	 * 
	 * @param dist
	 * @param position
	 * @return string based answer of the direction the spider has to walk to.
	 */
	private String calcDirectionSensor(double dist, String position) {
		if (position.isEmpty()) {
			if (dist <= 4) {
				return "Stop";
			} else
				return "forward";

		} else
			return position;
	}

	/**
	 * Calls everything.
	 * 
	 * @return ProtocolMessage for Movement Service.
	 */
	private ProtMessage fetchCommand() {
		// TODO Auto-generated method stub
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
		new ComponentRef("RouterClient").tell(messageBus, getSelf(),
				fetchCommand().toString());
		return true;
	}

	@Override
	protected void receiveMessage(MessageBus messageBus, I_Message message) {
		if (message instanceof Message) {
			Message m = (Message) message;

		}
	}

}

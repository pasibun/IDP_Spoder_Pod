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
	 * @param message
	 * @return string based answer of the direction the spider has to walk to.
	 */
	private int calcDirection(MessageInt message) {
		if (message instanceof MessageInt) {
			MessageInt m = (MessageInt) message;
			switch (shit[0]) {
			case 3:
				return shit[2];
			case 5:
				
				break;
			case 6:
				if (shit[1] == 0) {
					// doe dingen met id 0
				} else {
					// doe dingen met id 1
				}
				break;
			}
		}

		return 2;
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

	// intType, intId, intData
	@Override
	protected void receiveMessage(MessageBus messageBus, I_Message message) {
		if (message instanceof MessageInt) {
			MessageInt m = (MessageInt) message;
			shit = m.getData();
			System.out.println(m.getData());
			//calcDirection(m);
		}
	}

	@Override
	public String toString() {
		return super.toString();
	}

}

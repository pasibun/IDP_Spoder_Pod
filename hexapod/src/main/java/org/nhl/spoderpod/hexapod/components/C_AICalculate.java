package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.ComponentRef;
import org.nhl.spoderpod.hexapod.core.DataPackage;
import org.nhl.spoderpod.hexapod.core.Message;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.I_Message;

public class C_AICalculate extends BaseComponent {

	private String lastCommand;

	public C_AICalculate(String strName) {
		super(strName);
	}

	/**
	 * Method calculates the direction the spider has to walk to when it gets a
	 * message.
	 * @param message
	 * @return string based answer of the direction the spider has to walk to.
	 */
	private String calcDirection(Message message) {
		System.out.println("doe dingen2");
		return "nice";
//		switch (message.getSender().toString()) {
//		case " ":
//			if (3 >= Integer.parseInt(message.getData())) {
//				return "Backward";
//			} else
//				return "forward";
//		case "  ":
//			return message.getData();
//			
//		case"":
//		}
//		return "Error";
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
		//update this to send it to Movement Service command and control. 
		//new ComponentRef("AppSocket").tell(messageBus, getSelf(), new ComponentRef("RouterClient"), ));	
		//new ComponentRef("C_AIFormatter").tell(messageBus, getSelf(), lastCommand);	
		
		return true;
	}

	@Override
	protected void receiveMessage(MessageBus messageBus, I_Message message) {
		if (message instanceof Message) {
			Message m = (Message) message;
			calcDirection(m);
		}
	}

	@Override
	public String toString() {
		return super.toString();
	}

}

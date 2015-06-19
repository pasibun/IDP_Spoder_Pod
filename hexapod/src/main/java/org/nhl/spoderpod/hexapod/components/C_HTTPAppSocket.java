package org.nhl.spoderpod.hexapod.components;

import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.nhl.spoderpod.hexapod.core.Message;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.I_Message;
import org.nhl.spoderpod.hexapod.utils.U_HTTPAppServer;

/***
 * CAppSocket's purpose in life is to redirect the data, formatted by the
 * CFormat class to every client connected to the server. The clients are in a
 * static list in CAppListner CAppListner also takes care of incomming clients.
 * 
 * @author Driving Ghost
 *
 */
public class C_HTTPAppSocket extends BaseComponent {
	private static final int MAX_MESSAGES_SIZE = 10;
	private final U_HTTPAppServer utilAppServer;
	private final Queue<I_Message> messages;

	/***
	 * Constructor for the HTTPAppSocket component.
	 * 
	 * @param strName
	 *            string based name of the service. "AppSocket" is the hardcoded
	 *            norm.
	 * @param intPort
	 *            integer based port, used for the AppServer to host itself on.
	 *            Hardcoded for 8080.
	 */
	public C_HTTPAppSocket(String strName, int intPort) {
		super(strName);
		this.utilAppServer = new U_HTTPAppServer(intPort);
		this.messages = new ConcurrentLinkedQueue<I_Message>();
	}

	/***
	 * Calls the start method of the U_HTTPAppServer class.
	 */
	public void init(MessageBus messageBus) {
		this.utilAppServer.start();
	}

	/***
	 * Calls the stop method of the U_HTTPAppServer class.
	 */
	public void close(MessageBus messageBus) {
		this.utilAppServer.stop();
	}

	/***
	 * 
	 */
	@Override
	protected boolean composeMessage(MessageBus messageBus) {
		if (this.utilAppServer.hasClients()) {
			this.utilAppServer
					.send(String
							.format("{\"server_status\": {\"code\": 0, \"message\": \"Online\"}, \"data\": %s}",
									generateJSONArray(this.messages)));
		}
		return true;
	}

	private void addMessage(I_Message message) {
		while (this.messages.size() == MAX_MESSAGES_SIZE) {
			this.messages.poll();
		}
		this.messages.add(message);
	}

	private static String generateJSONArray(Collection<I_Message> c) {
		StringBuilder builder = new StringBuilder();
		builder.append('[');
		for (I_Message message : c) {
			builder.append(builder.length() > 1 ? ','
					: ' ' + ((Message) message).getData());
		}
		return builder.append(']').toString();
	}

	@Override
	protected void receiveMessage(MessageBus messageBus, I_Message message) {
		addMessage(message);
	}
}

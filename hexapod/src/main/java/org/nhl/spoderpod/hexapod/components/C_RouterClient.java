package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.Message;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.core.OutgoingMessage;
import org.nhl.spoderpod.hexapod.interfaces.I_Message;
import org.nhl.spoderpod.hexapod.utils.U_RouterClient;

/**
 * Component that sends messages across the routerserver
 * 
 * @author achmed
 */
public final class C_RouterClient extends BaseComponent {
	private final U_RouterClient routerClient;

	/**
	 * Name of the component.
	 * 
	 * Host name to connect to.
	 * 
	 * Port number to connect on.
	 * 
	 * @param name
	 * @param host
	 * @param port
	 * 
	 */
	public C_RouterClient(String name, String host, int port) {
		super(name);
		this.routerClient = new U_RouterClient(host, port);
	}

	public void init(MessageBus messageBus) {
		this.routerClient.start();

	}

	public void close(MessageBus messageBus) {
		this.routerClient.stop();
	}

	@Override
	protected boolean composeMessage(MessageBus messageBus) {
		while (this.routerClient.hasMessages()) {
			messageBus.send(this.routerClient.pollMessage());
		}
		return true;
	}

	@Override
	protected void receiveMessage(MessageBus messageBus, I_Message message) {
		if (message instanceof OutgoingMessage) {
			this.routerClient.send(new Message(message.getSender(),
					((OutgoingMessage) message).getActualrecipient(),
					((OutgoingMessage) message).getData()));
		}
	}
}

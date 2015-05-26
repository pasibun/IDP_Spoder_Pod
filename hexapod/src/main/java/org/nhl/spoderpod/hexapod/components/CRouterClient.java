package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.IMessage;
import org.nhl.spoderpod.hexapod.utils.RouterClient;

/**
 * Component that sends messages across the routerserver
 * @author achmed
 */
public final class CRouterClient extends BaseComponent {
	private final RouterClient routerClient;

	/**
	 * @param name Name of the component.
	 * @param host Hostname to connect to.
	 * @param port Port number to connect on.
	 */
	public CRouterClient(String name, String host, int port) {
		super(name);
		this.routerClient = new RouterClient(host, port);
	}

	public void init(MessageBus messageBus) {
		this.routerClient.start();
	}

	public void close(MessageBus messageBus) {
		this.routerClient.stop();
	}

	@Override
	protected boolean composeMessage(MessageBus messageBus) {
		return true;
	}

	@Override
	protected void receiveMessage(MessageBus messageBus, IMessage message) {
		try {
			this.routerClient.send(message);
		} catch (Exception e) {
		}
	}
}

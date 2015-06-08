package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.ComponentRef;
import org.nhl.spoderpod.hexapod.core.Message;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.I_Message;
import org.nhl.spoderpod.hexapod.utils.U_InputServer;

/**
 * Example server which sends log data to all connected clients.
 * The data gets requested from the logger component if there are connected clients.
 * It sends the same log data to all connected clients that are connected when the request to the logger service are done.
 * @author achmed
 */
public final class C_ServerListener extends BaseComponent {
	private final U_InputServer server;

	/**
	 * @param name The component name.
	 */
	public C_ServerListener(String name) {
		super(name);
		this.server = new U_InputServer(8080);
	}

	public void init(MessageBus messageBus) {
		this.server.start();
	}

	@Override
	protected boolean composeMessage(MessageBus messageBus) {
		if (this.server.hasConnectedClients()) {
			new ComponentRef("Logger").tell(messageBus, getSelf(), "Get");
		}
		return this.server.hasConnectedClients();
	}

	protected void receiveMessage(MessageBus messageBus, I_Message message) {
		this.server
				.send(String
						.format("{\"server_status\": {\"code\": 0, \"message\": \"Online\"}, \"data\": [{\"type\": \"log\", \"value\": \"%s\"}]}",
								((Message) message).getData().replace("\n",
										"\\n")));
	}

	public void close(MessageBus messageBus) {
		this.server.stop();
	}
}

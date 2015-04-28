package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.ComponentRef;
import org.nhl.spoderpod.hexapod.core.Message;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.IMessage;
import org.nhl.spoderpod.hexapod.utils.InputServer;

public final class CServerListener extends BaseComponent {
	private final InputServer server;

	public CServerListener(String name) {
		super(name);
		this.server = new InputServer(8080);
	}

	public void init(MessageBus messageBus) {
		this.server.start();
	}

	@Override
	protected boolean preReceive(MessageBus messageBus) {
		if (this.server.hasConnectedClients()) {
			new ComponentRef("Logger").tell(messageBus, getSelf(), "Get");
		}
		return this.server.hasConnectedClients();
	}

	protected void receive(MessageBus messageBus, IMessage message) {
		this.server.send(String.format(
				"[{\"type\": \"log\", \"value\": \"%s\"}]",
				((Message) message).getData().replace("\n", "\\n")));
	}

	public void close(MessageBus messageBus) {
		this.server.stop();
	}
}

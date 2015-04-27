package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.ComponentRef;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.IComponent;
import org.nhl.spoderpod.hexapod.utils.InputServer;

public final class CServerListener implements IComponent {
	private final ComponentRef self;
	private final InputServer server;
	
	public CServerListener(String name) {
		this.self = new ComponentRef(name);
		this.server = new InputServer(8080);
	}

	public void init(MessageBus messageBus) {
		this.server.start();
	}

	public void update(MessageBus messageBus) {
		if (this.server.hasConnectedClients()) {
			this.server.send(String.format("[{\"type\": \"time\", \"value\": %d}]", System.currentTimeMillis()));
		}
	}

	public void close(MessageBus messageBus) {
		this.server.stop();
	}

	public ComponentRef getSelf() {
		return this.self;
	}

}

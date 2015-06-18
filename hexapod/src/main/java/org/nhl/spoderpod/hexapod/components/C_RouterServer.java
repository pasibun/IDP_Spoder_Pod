package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.I_Message;
import org.nhl.spoderpod.hexapod.utils.U_RouterServer;

public final class C_RouterServer extends BaseComponent {

	private final U_RouterServer server;

	public C_RouterServer(String name) {
		super(name);
		server = new U_RouterServer(1234);
	}

	public void init(MessageBus messageBus) {
		this.server.start();
	}

	public void close(MessageBus messageBus) {
		this.server.stop();
	}

	@Override
	protected boolean composeMessage(MessageBus messageBus) {
		return false;
	}

	@Override
	protected void receiveMessage(MessageBus messageBus, I_Message message) {
	}
}

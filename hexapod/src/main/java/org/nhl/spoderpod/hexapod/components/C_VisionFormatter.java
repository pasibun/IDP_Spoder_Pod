package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.ComponentRef;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.IMessage;
import org.nhl.spoderpod.hexapod.utils.U_VisionDetectBalloons;

public class C_VisionFormatter extends BaseComponent {	

	private String returnValue;
	
	public C_VisionFormatter(String name) {
		super(name);
		U_VisionDetectBalloons vision = new U_VisionDetectBalloons();
		dataFormatter(vision.getX(), vision.getY(), vision.getZ(), vision.getPosition());
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public ComponentRef getSelf() {
		return super.getSelf();
	}

	@Override
	protected boolean composeMessage(MessageBus messageBus) {
		return false;
	}

	@Override
	protected void receiveMessage(MessageBus messageBus, IMessage message) {
	}

	private String dataFormatter(double x, double y,
			double z, String position) {
		returnValue = String
				.format("X= "+"%s" + "Y= " + "%s" + "X= " + "%s" + "Position=" + "%s", x, y, z, position);
		return returnValue;
	}

	public void init(MessageBus messageBus) {
		
	}

	public void close(MessageBus messageBus) {
		
	}

}

package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.ComponentRef;
import org.nhl.spoderpod.hexapod.core.Message;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.I_Message;
import org.nhl.spoderpod.hexapod.utils.U_ControlState;

/***
 * C_ControlCheck is a component in charge of ControlState of the spoderpod. It
 * both changes this, and returns it This component uses the U_ControlState
 * utility. This class contains the ConstrolState data.
 * 
 * @author Yannick comments by Hidde desu desu kawaii ^.^ V 4chan.org
 */
public class C_ControlCheck extends BaseComponent {
	private final U_ControlState state;

	/***
	 * Constructor of the ControlCheck Service.
	 * 
	 * @param strName
	 *            name of the component
	 */
	public C_ControlCheck(String strName) {
		super(strName);
		this.state = new U_ControlState(new U_ControlState.HumanState());
	}

	public void init(MessageBus messageBus) {

	}

	public void close(MessageBus messageBus) {

	}

	/***
	 * Activates whether or not component gets a message.
	 */
	@Override
	protected boolean composeMessage(MessageBus messageBus) {
		return true;
	}

	@Override
	/***
	 * activates when shit gets a message. 
	 */
	protected void receiveMessage(MessageBus messageBus, I_Message message) {
		if (message instanceof Message) {
			Message m = (Message) message;
			switch (Integer.parseInt(m.getData())) {
			case 0:// balloon
				new ComponentRef("C_ControlCheck").tell(messageBus, getSelf(),
						new ComponentRef("C_RouterClient"), "BalloonState");
				break;
			case 1:// Dance
				new ComponentRef("C_ControlCheck").tell(messageBus, getSelf(),
						new ComponentRef("C_RouterClient"), "DanceState");
				break;
			case 2:// pole
				new ComponentRef("C_ControlCheck").tell(messageBus, getSelf(),
						new ComponentRef("C_RouterClient"), "PoleState");
				break;
			default:// nonActive mode.
				new ComponentRef("C_ControlCheck").tell(messageBus, getSelf(),
						new ComponentRef("C_RouterClient"), "default");
				break;

			}
			new ComponentRef("Logger").tell(messageBus, getSelf(),
					new ComponentRef("RouterClient"), m.getData());
			new ComponentRef("Calculate").tell(messageBus, getSelf(),
					new ComponentRef("RouterClient"), m.getData());
		}

	}
}

package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.Message;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.IMessage;
import org.nhl.spoderpod.hexapod.utils.ControlState;

public class CControlCheck extends BaseComponent{
	private final ControlState state;
	
	public CControlCheck(String name)
	{
		super(name);
		this.state = new ControlState(new ControlState.HumanState());
	}

	public void init(MessageBus messageBus) {
		// TODO Auto-generated method stub
		
	}

	public void close(MessageBus messageBus) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean preReceive(MessageBus messageBus) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void receive(MessageBus messageBus, IMessage message) {
		if (message instanceof Message) {
			Message m = (Message) message;
			
			if ("SetHumanState".equals(m.getData())) {
				if(state.getState().getType() != ControlState.StateType.HumanState)
					this.state.setState(new ControlState.HumanState());
			} 
			else if("SetAIState".equals(m.getData())){
				if(state.getState().getType() != ControlState.StateType.AIState)
					this.state.setState(new ControlState.AIState());
			}
		}
		
	}
}

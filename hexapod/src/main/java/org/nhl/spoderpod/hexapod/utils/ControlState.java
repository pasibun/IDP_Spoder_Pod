package org.nhl.spoderpod.hexapod.utils;

public class ControlState{
	private State state;
	
	public ControlState(State state){
		this.state = state;
	}
	
	public State getState(){
		return this.state;		
	}
	
	public void setState(State state){
		this.state = state;
	}
	
	public static enum StateType{
		AIState, HumanState
	}
	
	public static interface State{
		StateType getType();
	}
	
	public static class AIState implements State{

		public StateType getType() {
			return StateType.AIState;
		}
		
	}
	public static class HumanState implements State{

		public StateType getType() {
			return StateType.HumanState;
		}
		
	}
}


package org.nhl.spoderpod.hexapod.utils;

/***
 * 
 * U_ControlState is a utility that manages the current ControlState of the
 * spoderpod
 * 
 * @author Dries Roelvink comments by Hidde
 */
public class U_ControlState {
	private State state;

	public U_ControlState(State state) {
		this.state = state;
	}

	public State getState() {
		return this.state;
	}

	public void setState(State state) {
		this.state = state;
	}

	/***
	 * enum containing the different state types. Contains: AIState, HumanState
	 * state types.
	 * 
	 * @author Yannick
	 *
	 */
	public static enum StateType {
		AIState, HumanState
	}

	public static interface State {
		StateType getType();
	}

	/***
	 * Manifests te AIState state.
	 * 
	 * @author Yannick
	 *
	 */
	public static class AIState implements State {

		public StateType getType() {
			return StateType.AIState;
		}

	}

	/***
	 * Manifests the HumanState state.
	 * 
	 * @author Yannick
	 *
	 */
	public static class HumanState implements State {

		public StateType getType() {
			return StateType.HumanState;
		}

	}
}

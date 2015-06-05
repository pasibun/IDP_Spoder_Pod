package org.nhl.spoderpod.hexapod.components;

import org.nhl.spoderpod.hexapod.core.ComponentRef;
import org.nhl.spoderpod.hexapod.core.DataPackage;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.I_Message;
import org.nhl.spoderpod.hexapod.libraries.L_Decoder;
import org.nhl.spoderpod.hexapod.libraries.L_FileActions;
import org.nhl.spoderpod.hexapod.utils.U_SensorReadWrite;

public class C_SensorReader extends BaseComponent {

	private U_SensorReadWrite u_SensorRW;

	public C_SensorReader(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public void init(MessageBus messageBus) {
		this.u_SensorRW = new U_SensorReadWrite();

	}

	public void close(MessageBus messageBus) {
		// TODO Auto-generated method stub

	}

	/***
	 * ComposeMessage for the this object makes sure that incoming datapackages are dissected based on the type.
	 * And are send to the correct service. The actual communication towards the correct service are a TODO!!
	 * 
	 * Data formatting towards the component making sure of the inter-service communication are also still being developed. 
	 */
	@Override
//	0; Wordt gebruikt voor Debugging
//	1; Wordt gebruikt voor Movements
//	2; Wordt gebruikt voor Servos
//	3; Wordt gebruikt voor Sensors
//	4; Wordt gebruikt voor Gyro
//	5; Wordt gebruikt voor joystick
//	6; Wordt gebruikt voor buttons
//	7; Wordt gebruikt voor touchpad
	protected boolean composeMessage(MessageBus messageBus) {
		String strReceiver = "Logger";
		int intData = 0;
		int intId = 0;
		L_Decoder.recieveMsg((L_FileActions.read()));
		for (DataPackage dp : L_Decoder.getData()){
			int intType = dp.get_byteType();
			intData = dp.get_shortData();
			intId = dp.get_byteId();
			switch(intType){
				case 2: 
					strReceiver = "S_Logger";
				case 3:
				case 4:
					strReceiver = "S_AI";
				case 5:
				case 6:
				case 7:
					strReceiver = "S_Human";
			}
		}
		new ComponentRef(strReceiver).tell(messageBus, getSelf(), String.format("[%s %s]", intData, intId));
		return true;
	}

	@Override
	protected void receiveMessage(MessageBus messageBus, I_Message message) {
		// TODO Auto-generated method stubw

	}
}

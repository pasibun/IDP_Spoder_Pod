package org.nhl.spoderpod.hexapod.components;

import java.io.IOException;
import java.util.List;

import org.nhl.spoderpod.hexapod.core.ComponentRef;
import org.nhl.spoderpod.hexapod.core.DataPackage;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.I_Message;
import org.nhl.spoderpod.hexapod.libraries.L_Decoder;
import org.nhl.spoderpod.hexapod.libraries.L_Encoder;
import org.nhl.spoderpod.hexapod.libraries.L_FileActions;
import org.nhl.spoderpod.hexapod.utils.U_SensorReadWrite;

public class C_SensorReader extends BaseComponent {

	public C_SensorReader(String name) {
		super(name);
	}

	public void init(MessageBus messageBus) {
	}

	public void close(MessageBus messageBus) {
	}

	/***
	 * ComposeMessage for the this object makes sure that incoming datapackages
	 * are dissected based on the type. And are send to the correct service.
	 */
	@Override
	// 0; Wordt gebruikt voor Debugging
	// 1; Wordt gebruikt voor Movements
	// 2; Wordt gebruikt voor Servos
	// 3; Wordt gebruikt voor Sensors
	// 4; Wordt gebruikt voor Gyro
	// 5; Wordt gebruikt voor joystick
	// 6; Wordt gebruikt voor buttons
	// 7; Wordt gebruikt voor touchpad
	protected boolean composeMessage(MessageBus messageBus) {
		String strReceiver = "C_Logger";
		int intData = 0;
		int intId = 0;
		int intType = 0;
		List<Byte> i = null;
		try {
			i = L_FileActions.read();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (i.size() > 0) {
			L_Decoder.recieveMsg(i);
		}
		for (DataPackage dp : L_Decoder.getData()) {
			intType = dp.get_byteType();
			intData = dp.get_shortData();
			intId = dp.get_byteId();
			
			switch (intType) { // terugkrijgende string wijst naar de service.
			case 2:
				strReceiver = "C_Logger";
				break;
			case 3:
				strReceiver = "C_AICalculate";
				break;
			case 4:
				strReceiver = "C_Logger";
				break;
			case 5: //joystick
				//<50 = achteren
				//>200 = voorwaarts
				//128 = mid
				//eerste short [0]
				
				//<50 links
				//>200 rechts
				//128 mid
				//tweede short [1]
				strReceiver = "C_Movement";
				byte[] value = convertByte(intData);
//				System.out.println(value[0]);
//				System.out.println(value[1]);
				if(value[1] > 3 * (256 / 4)){
					new ComponentRef(strReceiver).tell(messageBus, getSelf(),
							new ComponentRef("C_RouterClient"),"Crabwalk");
				}
				else if(value[1] < 1 * (256 / 4)) 
				{
					new ComponentRef(strReceiver).tell(messageBus, getSelf(),
							new ComponentRef("C_RouterClient"),"Walkstate");
				}
				System.out.format("intData: %d\n", intData);
//				else if(value[1] < 50)
//				{
//					new ComponentRef(strReceiver).tell(messageBus, getSelf(),
//							new ComponentRef("C_RouterClient"),"Crabwalk");
//				}
//				else if(value[1] > 200)
//				{
//					new ComponentRef(strReceiver).tell(messageBus, getSelf(),
//							new ComponentRef("C_RouterClient"),"Crabwalk");
//				}
//				else
//				{
//					new ComponentRef(strReceiver).tell(messageBus, getSelf(),
//							new ComponentRef("C_RouterClient"),"Idle");
//				}
				return true;
			case 6:
				break;
			case 7:
				strReceiver = "C_ControlCheck";
				break;
			default:
				break;
			}
		}
		L_FileActions.write(i);
//		new ComponentRef(strReceiver).tell(messageBus, getSelf(),
//				new ComponentRef("C_RouterClient"),
//				String.format("%s [%s %s]", intType, intId, intData));

		return true;
	}

	private byte[] convertByte(int data){
		byte[] value = new byte[2];
		value[0] = ((byte) ((data >> 8) & 0xff));
		value[1] = (byte)((byte)(data) & 0xff);
		return value;
	}
	
	@Override
	protected void receiveMessage(MessageBus messageBus, I_Message message) {
		// TODO Auto-generated method stub

	}
}

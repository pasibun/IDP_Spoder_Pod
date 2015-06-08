package org.nhl.spoder.hexapod.movementservice;

import org.nhl.spoderpod.hexapod.components.C_HTTPAppSocket;
import org.nhl.spoderpod.hexapod.components.C_HTTPFormat;
import org.nhl.spoderpod.hexapod.components.C_RouterClient;
import org.nhl.spoderpod.hexapod.core.Service;
import org.nhl.spoderpod.hexapod.interfaces.I_Component;

/***
 * HTTP Service verstuurt de gegevens naar de server van de webapp.
 * 
 * @author Fre-Meine Fuckboys
 */
public class Main {

	public static void main(String[] args) throws InterruptedException {
		Service s = new Service("MovementService", new I_Component[] {
				new C_HTTPAppSocket("AppSocket", 8080),
				new C_HTTPFormat("Formatter"),
				new C_RouterClient("RouterClient","127.0.0.1",1234)});
		s.start();

	}
<<<<<<< HEAD
=======
	// U_MovementCsvReader walkstate = new U_MovementCsvReader();
	// walkstate.read("StraightWalk.csv");
	//
	// CsvReader crabstate = new CsvReader();
	// crabstate.read("StraightWalk.csv");
	//
	// CsvReader idle = new CsvReader();
	// idle.read("StraightWalk.csv");
	//
	// U_MovementServoMovement s = new U_MovementServoMovement();
	// int delay = Integer.parseInt(args[0]);
	//
	// MovingThread m = new MovingThread();
	// m.start();
	//
	// // System.out.println("time id x y z");
	// for (int time = 0;; time++) {
	// L_Vector v;
	// while (m.hasData()) {
	// System.out.println(m.pollData());
	// }
	// for (int n = 0; n < 6; n++) {
	// v = walkstate.getLeg(n + 1)[time % 41];
	// // System.out.format("Time %d, ", (time % 41) + 1);
	// s.updateLeg(n, v.x, v.y, v.z);
	// }
	// s.sendPacket();
	// Thread.sleep(delay);
	// }

	// s.updateLegs(angles2[1], angles2[2], angles2[3]);
	// s.updateLegs(x,y,z);

	// byte destination = (byte) 3;
	// byte type = (byte) 0;
	// byte id = (byte) 13;
	// short data = 180;
	// L_Encoder.addData(type, id, data);
	// L_Encoder.prepMsg(destination);
	// List<Byte> returnmsg = L_Encoder.getMsgs();
	// L_FileActions.write(returnmsg);
	// System.out.println(returnmsg);
	//
	// L_Decoder.recieveMsg(returnmsg);

>>>>>>> c95c3ae3abbcc3f3f40692acbb4e6eb91f63588b
}
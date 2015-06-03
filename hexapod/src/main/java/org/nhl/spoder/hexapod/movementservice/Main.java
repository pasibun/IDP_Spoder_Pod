package org.nhl.spoder.hexapod.movementservice;

/***
 * HTTP Service verstuurt de gegevens naar de server van de webapp.
 * 
 * @author Fre-Meine Fuckboys
 */
public class Main {

	/*
	 * Starts the service.
	 */

	public static void main(String[] args) throws InterruptedException {
		// Service s = new Service("MovementService", new IComponent[] { new
		// C_HTTPAppSocket("AppSocket", 8080),
		// new C_HTTPFormat("Formatter")});
		// s.start();
		// Thread.sleep(10*1000);
		// s.run();

		CsvReader reader = new CsvReader();
		reader.read();

		ServoMovement s = new ServoMovement();

		System.out.println("time id x y z");
		for (int time = 0; time < 42; time++) {
			Vector v;
			for (int n = 0; n < 6; n++) {
				v = reader.getLeg(n + 1)[time % 41];
				System.out.format("Time %d, ", (time % 41) + 1);
				s.updateLeg(n, v.x, v.y, v.z);
			}
			s.sendPacket();
			Thread.sleep(1000);
		}

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

	}
}
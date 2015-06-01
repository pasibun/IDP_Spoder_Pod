package org.nhl.spoder.hexapod.movementservice;


/***
 * HTTP Service verstuurt de gegevens naar de server van de webapp.
 * 
 * @author Driving Gayboy
 */
public class Main {
	
	/*
	 * Starts the service.
	 */
	static int[] angles2;
	
	
	public static void main(String[] args) throws InterruptedException {
//		Service s = new Service("MovementService", new IComponent[] { 	new C_HTTPAppSocket("AppSocket", 8080),
//																	new C_HTTPFormat("Formatter")});
//		s.start();
//		Thread.sleep(10*1000);
//		s.run();
		
		CsvReader reader = new CsvReader();
		angles2 = reader.read();		
	
		int x = 109;
		int y = 35;
		int z = -13;
		
		ServoMovement s = new ServoMovement();
		int a = Integer.parseInt("1234");
		s.updateLegs(angles2[1], angles2[2], angles2[3]);
		s.updateLegs(x,y,z);
		System.out.println(a);
//		
//		byte destination = (byte) 3;
//		byte type = (byte) 0;
//		byte id = (byte) 13;
//		short data = 180;
//		L_Encoder.addData(type, id, data);
//		L_Encoder.prepMsg(destination);
//		List<Byte> returnmsg = L_Encoder.getMsgs();
//		L_FileActions.write(returnmsg);
//		System.out.println(returnmsg);
//		
//		L_Decoder.recieveMsg(returnmsg);
		
	}
}
package org.nhl.spoder.hexapod.movementservice;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

/***
 * HTTP Service verstuurt de gegevens naar de server van de webapp.
 * 
 * @author Driving Gayboy
 */
public class Main {

	/*
	 * Starts the service.
	 */
	static class MovingThread implements Runnable {
		private final Thread thread;
		private final ConcurrentLinkedQueue<Byte> input;

		public MovingThread() {
			this.thread = new Thread(this);
			this.input = new ConcurrentLinkedQueue<Byte>();
		}

		public void start() {
			this.thread.start();
		}

		public boolean hasData() {
			return this.input.size() > 0;
		}

		public byte pollData() {
			return this.input.poll();
		}

		public void run() {
			while (true) {
				try {
					byte b = ((byte) System.in.read());
					if (b != 13 && b != 10) {
						this.input.add(b);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		// Service s = new Service("MovementService", new IComponent[] { new
		// C_HTTPAppSocket("AppSocket", 8080),
		// new C_HTTPFormat("Formatter")});
		// s.start();
		// Thread.sleep(10*1000);
		// s.run();

		CsvReader walkstate = new CsvReader();
		walkstate.read("StraightWalk.csv");

//		CsvReader crabstate = new CsvReader();
//		crabstate.read("StraightWalk.csv");
//
//		CsvReader idle = new CsvReader();
//		idle.read("StraightWalk.csv");

		ServoMovement s = new ServoMovement();
		int delay = Integer.parseInt(args[0]);

		MovingThread m = new MovingThread();
		m.start();

		// System.out.println("time id x y z");
		for (int time = 0;; time++) {
			Vector v;
			while (m.hasData()) {
				System.out.println(m.pollData());
			}
			for (int n = 0; n < 6; n++) {
				v = walkstate.getLeg(n + 1)[time % 41];
				// System.out.format("Time %d, ", (time % 41) + 1);
				s.updateLeg(n, v.x, v.y, v.z);
			}
			 s.sendPacket();
			Thread.sleep(delay);
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
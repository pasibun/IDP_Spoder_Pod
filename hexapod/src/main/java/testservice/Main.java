package testservice;

import org.nhl.spoderpod.hexapod.core.ComponentRef;
import org.nhl.spoderpod.hexapod.core.Message;
import org.nhl.spoderpod.hexapod.utils.U_RouterClient;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		U_RouterClient c = new U_RouterClient("127.0.0.1", 1234);
		c.start();

		// c.send(message);
		// c.hasMessages();
		while (true) {
			if (c.hasMessages()) {
				System.out.println(c.pollMessage());
			}
			if (Math.random() < 0.000001f) {
				c.send(new Message(new ComponentRef("RouterClient"),
						new ComponentRef("RouterServer"), "hallooooo: " + System.currentTimeMillis()));
			}
		}
	}

}

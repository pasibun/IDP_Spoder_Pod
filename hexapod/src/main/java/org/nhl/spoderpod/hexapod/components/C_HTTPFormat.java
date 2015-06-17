package org.nhl.spoderpod.hexapod.components;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.nhl.spoderpod.hexapod.core.ComponentRef;
import org.nhl.spoderpod.hexapod.core.Message;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.I_Message;
import org.nhl.spoderpod.hexapod.interfaces.I_Threaded;

/***
 * Hello!
 * HTTPFormat makes sure that the data that has been received from the Logger Service is formatted to JSON
 * Then sends it to C_HTTPAppSocket.
 * 
 * 
 * NOTE: If logger formats data, this class is redundant. 
 * 
 * @version Alpha
 * @author Driving Ghost
 *
 */
public class C_HTTPFormat extends BaseComponent {

	private volatile boolean running;
	private final BufferedReader br;
	
	/***
	 * C_HTTPFormat Constructor. 
	 * Initialises the BufferedReader for current version. 
	 * 
	 * @param name String based name of the Component
	 */
	public C_HTTPFormat(String name) {
		super(name);
		br = new BufferedReader(new InputStreamReader(
				System.in));	
	}
	
	public void init(MessageBus messageBus) {
			
	}

	public void close(MessageBus messageBus) {
		
	}
	
	/***
	 * This method sends the data to the C_HTTPAppSocket class. 
	 */
	@Override
	protected boolean composeMessage(MessageBus messageBus) {
		try {
			System.out.println("Yes:");
			String x = br.readLine();
			new ComponentRef("AppSocket").tell(messageBus, getSelf(), x);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	protected void receiveMessage(MessageBus messageBus, I_Message message) {
		//CALL FORMAT HERE. 
	}

}

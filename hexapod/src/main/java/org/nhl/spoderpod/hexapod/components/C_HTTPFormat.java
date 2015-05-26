package org.nhl.spoderpod.hexapod.components;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.nhl.spoderpod.hexapod.core.ComponentRef;
import org.nhl.spoderpod.hexapod.core.Message;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.IMessage;
import org.nhl.spoderpod.hexapod.interfaces.IThreaded;

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
	protected void receiveMessage(MessageBus messageBus, IMessage message) {
		//CALL FORMAT HERE. 
	}

}
//
//
//public void format(String msg) {
//	
//	String dude = String.format("HTTP/1.1 200 OK\r\n"
//			+ "Content-Type: text/json\r\n"
//			+ "Access-Control-Allow-Origin: *\r\n"
//			+ "Content-Length: %d\r\n\r\n" 
//			+ "%s\r\n", msg.length() + 2, msg);
//	
//	//incomming data from Logger Service
//	//example: { health[11,12,13....61,62,63], control[0],    }
//	
//	//outward message:
//	// {\"server_status\": {\"code\": 0, \"message\": \"Online\"}, \"data\": [{\"type\": \"health\"l, \"value\": \"[%s,%d]\"}]}
//	
//	String reply = String.format("{\"server_status\": {\"code\": 0, \"message\": \"Online\"}, \"data\": [{\"type\": \"health\", \"value\": \"[%s,%d]\"}]}", 
//			"hello" , 100 );
//	C_HTTPBuffer.queueStrListOutData(reply.replace("\n", "\\n"));
//}
//
//public void request(){
//
//	while (true) {
//		try {
//			System.out.println("Enter String");
//			String x;
//
//			x = br.readLine();
//			C_HTTPBuffer.queueStrListOutData(String
//					.format("{\"server_status\": {\"code\": 0, \"message\": \"Online\"}, \"data\": [{\"type\": \"log\", \"value\": \"%s\"}]}",
//							x.replace("\n", "\\n")));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//}

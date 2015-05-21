package org.nhl.spoder.hexapod.httpservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.nhl.spoderpod.hexapod.core.Message;
import org.nhl.spoderpod.hexapod.interfaces.IThreaded;

public class CFormat implements IThreaded {

	private volatile boolean running;
	private final Thread thread;

	public CFormat() {
		this.thread = new Thread(this);
	}

	public void format(String data) {
		//incomming data from Logger Service
		//example: { health[11,12,13....61,62,63], control[0],    }
		
		//outward message:
		// {\"server_status\": {\"code\": 0, \"message\": \"Online\"}, \"data\": [{\"type\": \"health\"l, \"value\": \"[%s,%d]\"}]}
		
		String reply = String.format("{\"server_status\": {\"code\": 0, \"message\": \"Online\"}, \"data\": [{\"type\": \"health\", \"value\": \"[%s,%d]\"}]}", 
				"hello" , 100 );
		
		CBuffer.queueStrListOutData(reply.replace("\n", "\\n"));
		
	}
	
	public void run() {
		while (true) {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						System.in));
				System.out.println("Enter String");
				String x;

				x = br.readLine();
				CBuffer.queueStrListOutData(String
						.format("{\"server_status\": {\"code\": 0, \"message\": \"Online\"}, \"data\": [{\"type\": \"log\", \"value\": \"%s\"}]}",
								x.replace("\n", "\\n")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void start() {
		if (!this.running) {
			this.running = true;
			this.thread.start();
		}
	}

	public void stop() {
		// TODO Auto-generated method stub

	}

}

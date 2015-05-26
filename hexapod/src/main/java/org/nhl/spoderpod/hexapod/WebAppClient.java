package org.nhl.spoderpod.hexapod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.nhl.spoderpod.hexapod.interfaces.I_Threaded;
/***
 * This class is for testing only. It represents the WebApp Client. It's purpose is to receive data from the server
 * like a little bitch.
 * Yeah take that JSON data you w****
 * 
 * @author Driving Ghost
 *
 */
public class WebAppClient implements I_Threaded {

	
	private final String strIPAddress;
	private final int intPort;
	private Socket appSocketClient;
	private volatile boolean running;
	private final Thread thread;
	
	public WebAppClient(int port) {
		this.thread = new Thread(this);
		this.intPort = port;
		this.strIPAddress = "127.0.0.1";
	}

	public void run() {
		BufferedReader in = null;
		System.out.println("Starting app client.");
		
		try {
			appSocketClient = new Socket(strIPAddress, intPort);
		} catch (Exception e) {

		}
		System.out.println("Connected.");

		try {
			in = new BufferedReader(new InputStreamReader(appSocketClient.getInputStream()));
			String serverResponse = null;
            while ((serverResponse = in.readLine()) != null){
                System.out.println(serverResponse);
            }
            in.close();
            
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void start() {
		
		if (!this.running) {
			this.running = true;
			this.thread.run();
		}
		
	}

	public void stop() {
		this.running = false;
	}

}

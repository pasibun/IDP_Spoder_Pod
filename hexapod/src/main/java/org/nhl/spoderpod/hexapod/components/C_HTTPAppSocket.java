package org.nhl.spoderpod.hexapod.components;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.nhl.spoderpod.hexapod.core.ComponentRef;
import org.nhl.spoderpod.hexapod.core.Message;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.IMessage;
import org.nhl.spoderpod.hexapod.interfaces.IThreaded;
import org.nhl.spoderpod.hexapod.utils.InputServer;
import org.nhl.spoderpod.hexapod.utils.U_HTTPAppServer;

/***
 * CAppSocket's purpose in life is to redirect the data, formatted by the
 * CFormat class to every client connected to the server. The clients are in a
 * static list in CAppListner CAppListner also takes care of incomming clients.
 * 
 * @author Driving Ghost
 *
 */
public class C_HTTPAppSocket extends BaseComponent {

	private final U_HTTPAppServer utilAppServer;
	
	/***
	 * Constructor for the HTTPAppSocket component. 
	 * 
	 * @param strName string based name of the service. "AppSocket" is the hardcoded norm. 
	 * @param intPort integer based port, used for the AppServer to host itself on. Hardcoded for 8080.
	 */
	public C_HTTPAppSocket(String strName, int intPort) {
		super(strName);
		this.utilAppServer = new U_HTTPAppServer(intPort);
	}

	/***
	 * Calls the start method of the U_HTTPAppServer class. 
	 */
	public void init(MessageBus messageBus) {
		this.utilAppServer.start();
	}

	/***
	 *  Calls the stop method of the U_HTTPAppServer class.
	 */
	public void close(MessageBus messageBus) {
		this.utilAppServer.stop();
	}
	
	/***
	 * 
	 */
	@Override
	protected boolean composeMessage(MessageBus messageBus) {
		return this.utilAppServer.hasClients();
	}

	@Override
	protected void receiveMessage(MessageBus messageBus, IMessage message) {
		this.utilAppServer
		.send(String
				.format("{\"server_status\": {\"code\": 0, \"message\": \"Online\"}, \"data\": [{\"type\": \"log\", \"value\": \"%s\"}]}",
						((Message) message).getData().replace("\n",
								"\\n")));
	}
	
}

//
//private final Thread thread;
//private ServerSocket socketServer;
//private Socket clientSocket;
//private final int intPort;
//private ArrayList<C_HTTPClientThread> connectedClients;
//private volatile boolean running;
//private PrintWriter out;
//
//public C_HTTPAppSocket(String name, int port) {
//	super(name);
//	this.thread = new Thread(this);
//	this.intPort = port;
//	this.connectedClients = new ArrayList<C_HTTPClientThread>();
//	
//	try {
//		this.socketServer = new ServerSocket(intPort);
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	C_HTTPBuffer.queueStrListOutData(String
//			.format("{\"server_status\": {\"code\": 0, \"message\": \"Online\"}, \"data\": [{\"type\": \"log\", \"value\": \"%s\"}]}",
//					"Spider online"));
//}
//
///*
// * (non-Javadoc)
// * @see org.nhl.spoderpod.hexapod.interfaces.IThreaded#stop()
// */
//public void stop() {
//	this.running = false;
//	for (C_HTTPClientThread client : connectedClients) {
//		try {
//			client.stop();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	try {
//		socketServer.close();
//	} catch (IOException e) {
//		e.printStackTrace();
//	}
//}
//
///*
// * (non-Javadoc)
// * @see java.lang.Runnable#run()
// */
//public void run() {
//	while (true) {
//		try {
//			clientSocket = socketServer.accept();
//			System.out.println("New Connection Established");
//			(new Thread(new C_HTTPClientThread(clientSocket))).start();
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("Connection Error");
//		}
//	}
//}
//
///*
// * (non-Javadoc)
// * @see org.nhl.spoderpod.hexapod.interfaces.IThreaded#start()
// */
//public void start() {
//	if (!this.running) {
//		this.running = true;
//		this.thread.start();
//	}
//}
//
//public void init(MessageBus messageBus) {
//	// TODO Auto-generated method stub
//	
//}
//
//public void close(MessageBus messageBus) {
//	// TODO Auto-generated method stub
//	
//}
//
//@Override
//protected boolean preReceive(MessageBus messageBus) {
//	// TODO Auto-generated method stub
//	return false;
//}
//
//@Override
//protected void receive(MessageBus messageBus, IMessage message) {
//	// TODO Auto-generated method stub
//	
//}

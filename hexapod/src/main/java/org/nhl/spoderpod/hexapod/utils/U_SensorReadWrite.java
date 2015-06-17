package org.nhl.spoderpod.hexapod.utils;

import java.util.ArrayList;
import java.util.List;

import org.nhl.spoderpod.hexapod.interfaces.I_Threaded;

public class U_SensorReadWrite implements I_Threaded {

	private final Thread thread;
	private volatile boolean running;
	private List<Byte> list;

	public U_SensorReadWrite() {
		this.thread = new Thread(this);
		this.running = false;
		this.list = new ArrayList<Byte>();
	}

	/***
	 * Close method goes through the SocketClient list and closes every
	 * connected client. Afterwards, reduces the life of the socketServer to
	 * zero.
	 */
	private void close() {

	}

	/***
	 * The run method here makes sure the server keeps running and waiting for
	 * clients.
	 */
	public void run() {

	}

	/***
	 * start method starts the thread.
	 */
	public void start() {
		if (!this.running) {
			this.thread.start();
		}
	}

	/***
	 * Stop method stops the thread and calls the close method(), closing all
	 * sockets and the server.
	 */
	public void stop() {
		this.running = false;
		this.thread.interrupt();
		close();
	}

	private void composeArdMsg(byte destination, byte type, byte id, short data) {
		list.add(checkSum()); // checksum
		list.add((byte) 0); // destination
		list.add((byte) (type & 0xff)); // first of messages
		list.add((byte) (id & 0xff));
		list.add((byte) (data & 0xff));
		list.add((byte) ((data >> 8) & 0xff));
		list.add(recentZero());
		list.add(addZero());
	}

	private byte checkSum() {
		return 0;
	}

	private byte addZero() {
		return 0;
	}

	private byte recentZero() {
		byte x = 0;
		for (int i = list.size(); i >= 0; i--) {
			if (list.get(i) == 0) {
				x = (byte) i;
				break;
			}
		}
		return x;
	}
}
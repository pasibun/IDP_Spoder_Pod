package org.nhl.spoderpod.hexapod.utils;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public final class Utils {
	
	/**
	 * Create ServerSocket object.
	 * @param port
	 * @return
	 */
	public static ServerSocket CreateServerSocket(int port) {
		try {
			return new ServerSocket(port);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Create Socket object.
	 * @param host
	 * @param port
	 * @return
	 */
	public static Socket CreateSocket(String host, int port) {
		try {
			return new Socket(host, port);
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * Create ObjectOutputStream
	 * @param socket The socket to get the outputstream from.
	 * @return
	 */
	public static ObjectOutputStream CreateObjectOutputStream(Socket socket) {
		if (socket != null) {
			try {
				return new ObjectOutputStream(socket.getOutputStream());
			} catch (Exception e) {
			}
		}
		return null;
	}
}

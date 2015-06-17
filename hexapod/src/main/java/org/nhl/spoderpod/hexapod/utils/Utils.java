package org.nhl.spoderpod.hexapod.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public final class Utils {

	/**
	 * Create ServerSocket object.
	 * 
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
	 * Create input file
	 * 
	 * @param filename
	 * @return
	 */
	public static FileInputStream CreateFileInput(String filename) {
		try {
			return new FileInputStream(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Create Output file
	 * 
	 * @param filename
	 * @return
	 */
	public static FileOutputStream CreateFileOutput(String filename) {
		try {
			return new FileOutputStream(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Create Socket object.
	 * 
	 * @param host
	 * @param port
	 * @return
	 */
	public static Socket CreateSocket(String host, int port) {
		try {
			return new Socket(host, port);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Create ObjectOutputStream
	 * 
	 * @param socket
	 *            The socket to get the outputstream from.
	 * @return
	 */
	public static ObjectOutputStream CreateObjectOutputStream(Socket socket) {
		try {
			return new ObjectOutputStream(socket.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ObjectInputStream CreateObjectinputStream(Socket socket) {
		try {
			return new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// public static ObjectInputStream ReturnBufferedReader(Socket socket) {
	// try {
	// return new ObjectInputStream(socket.getInputStream());
	// } catch (Exception e) {
	//
	// }
	// return null;
	//
	// }
}

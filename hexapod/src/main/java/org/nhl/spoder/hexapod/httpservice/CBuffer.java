package org.nhl.spoder.hexapod.httpservice;

import java.util.ArrayList;
import org.nhl.spoderpod.hexapod.core.Message;

public class CBuffer {
	
	private static ArrayList<Message> listInMessages = new ArrayList<Message>();
	private static ArrayList<Message> listOutMessages = new ArrayList<Message>();
	private static ArrayList<String> strListOutData = new ArrayList<String>();
	
	public static int getstrListOutSize(){
		return strListOutData.size();
	}
	
	public static int getListOutSize(){
		return listOutMessages.size();
	}
	
	public static int getlistInSize(){
		return listInMessages.size();
	}
	
	public static void queueStrListOutData(String data){
		strListOutData.add(data);
	}
	
	public static void queueOutMessage(String recipient, String data){
		listOutMessages.add(null);
	}
	
	public static void queueInMessage(Message ObjMessage){
		listInMessages.add(ObjMessage);
	}
	
	public static Message getOutMessage(){
		Message objMessage = listOutMessages.get(0);
		listOutMessages.remove(0);
		return objMessage; 
	}
	
	public static String getListOutData(){
		String data = strListOutData.get(0);
		strListOutData.remove(0);
		return data;
	}
	
	public static String getMessageData(){
		String strDataval = listInMessages.get(0).getData();
		listInMessages.remove(0);
		return strDataval;
	}
}

package org.nhl.spoderpod.hexapod.core;

public class DataPackage {

	private final byte type;
	private final byte id;
	private final short data;

	public DataPackage(byte type, byte id, short data){
		this.type = type;
		this.id = id;
		this.data = data; 
	}
	
	public byte get_byteType(){
		return this.type;
	}
	
	public byte get_byteId(){
		return this.id;
	}
	
	/**
	 * Get the contents of this ProtocolMessage
	 * @return The data.
	 */
	public short get_shortData() {
		return this.data;
	}

	@Override
	public String toString() {
		return String.format("type: %s, Id: %s, Data: %s",
				this.type, this.id, this.data);
	}
}

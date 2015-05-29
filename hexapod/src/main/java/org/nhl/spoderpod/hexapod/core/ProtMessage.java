package org.nhl.spoderpod.hexapod.core;

public class ProtMessage {

	private final byte[] data;
	
	public ProtMessage(byte[] data){
		this.data = data; 
	}
	
	/**
	 * Get the contents of this ProtocolMessage
	 * @return The data.
	 */
	public byte[] getData() {
		return this.data;
	}

	@Override
	public String toString() {
		return String.format("Data content: %s ",
				this.data);
	}
}

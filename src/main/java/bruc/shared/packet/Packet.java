package bruc.shared.packet;

public abstract class Packet {

	private final PacketType type;
	
	public Packet(PacketType type) {
		this.type = type;
	}

	public PacketType getType() {
		return type;
	}
	
	
	
	
	
	
}

package bruc.shared.packet;

public class CreateAccountInfoPacket extends Packet {


	public static final int REJECTED = 0;
	public static final int ACCEPTED = 1;

	private final int status;
	
	public CreateAccountInfoPacket(int status) {
		super(PacketType.CREATE_ACCOUNT_INFO);
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

}

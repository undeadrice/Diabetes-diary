package bruc.shared.packet;

public class LoginInfoPacket extends Packet {

	public static final int REJECTED = 0;
	public static final int ACCEPTED = 1;

	private final int status;

	public LoginInfoPacket(int status) {
		super(PacketType.LOGIN_INFO);
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

}

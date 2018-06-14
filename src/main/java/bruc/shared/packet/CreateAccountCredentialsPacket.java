package bruc.shared.packet;

public class CreateAccountCredentialsPacket extends Packet {

	private final String login;
	private final char[] password;

	public CreateAccountCredentialsPacket(String login, char[] password) {
		super(PacketType.CREATE_ACCOUNT_CREDENTIALS);
		this.login = login;
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public char[] getPassword() {
		return password;
	}

}

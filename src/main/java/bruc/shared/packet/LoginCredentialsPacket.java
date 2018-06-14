package bruc.shared.packet;

public class LoginCredentialsPacket extends Packet {

	private final String login;
	private final char[] password;

	public LoginCredentialsPacket(String login, char[] password) {
		super(PacketType.LOGIN_CREDENTIALS);
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

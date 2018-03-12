package security;

public class HackerUser extends User {

	public HackerUser(String name) {
		super(name);
	}

	public void actWhenAccessDenied(Door door) {
		// hack the door system
	}
}

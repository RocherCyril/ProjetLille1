package security;

public class StubbornUser extends User {

	public StubbornUser(String name) {
		super(name);
	}

	public void actWhenAccessDenied(Door door)  {
		// try to open the door again and again...
		try {
			this.open(door);
		} catch (AccessDeniedException e) {
			this.actWhenAccessDenied(door);;
		}
	}
}

package security;

public class User {
	
	private String name;
	
	public User(String name) {
		this.name = name;
	}

	public void open(Door door) throws AccessDeniedException {
		if (! door.acceptUser(this)) {
			throw new AccessDeniedException();
		}
		
	}

	public String getName() {
		return name;
	}
	
	public void actWhenAccessDenied(Door door) {
		// do nothing
	}
}

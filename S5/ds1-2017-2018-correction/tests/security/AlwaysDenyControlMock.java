package security;

public 	class AlwaysDenyControlMock extends AccessControl {

	public void checkIntegrity() throws IntegrityException {}

	protected boolean checkAccess(User user) {
		return false;  // always deny
	}
	
}
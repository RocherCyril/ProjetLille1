package security;

public class ControlByCode extends AccessControl {

		
	@Override
	public void checkIntegrity() throws IntegrityException {
		// do something smart to check integrity of access control is still ok
		// if not exception is thrown
	}

	private final java.util.Random alea = new java.util.Random();
	@Override
	protected boolean checkAccess(User user) {
		// user must provide code and result depends on correctness
		boolean result = alea.nextBoolean();
		return result;
	}

}

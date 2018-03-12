package security;

public class NoControl extends AccessControl {

	@Override
	public void checkIntegrity()  throws IntegrityException {
		// nothing to do
	}

	/** access is always granted
	 * @return true
	 */
	@Override
	protected boolean checkAccess(User user) {
		return true;
	}

}

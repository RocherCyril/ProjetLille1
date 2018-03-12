package security;

public abstract class AccessControl {

	public abstract void checkIntegrity()  throws IntegrityException ;
	
	public final boolean accessGranted(User user)  throws IntegrityException {
		this.checkIntegrity();
 		return this.checkAccess(user);
	}
	
	protected abstract boolean checkAccess(User user);
}

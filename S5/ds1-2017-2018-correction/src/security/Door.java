package security;

import java.util.Calendar;

public class Door {

	protected AccessControl control;
	protected AccessLogger logger;
	
	public Door() {
		this(null);
	}
	public Door(AccessControl control) {
		this.logger = new AccessLogger();
		this.control = control;
	}
	
	public void changeAccessControl(AccessControl control) {
		this.control = control;
	}
	
	public boolean acceptUser(User user) {
		try {
			boolean granted = this.control.accessGranted(user);
			if (granted) {
				this.logger.logValidAccess(user, Calendar.getInstance());
			} else {
				this.logger.logDenyAccess(user, Calendar.getInstance());
			}
			
			return granted;
		} catch (IntegrityException e) {
			this.logger.logIntegrityProblem(Calendar.getInstance());
			return false;
		}
	}

	public AccessLogger getLogger() {
		return this.logger;
	}
}

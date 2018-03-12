package security;

import java.util.Calendar;

public class LogInput {

	private enum State { DENY, ACCEPT, INTEGRITY; }
	
	private String userName;
	private State state;
	private Calendar date;
	
	private LogInput(State state, String userName, Calendar date) {
		this.state = state;
		this.userName = userName;
	}
	
	public static LogInput createDenyLog(String userName, Calendar date) {
		return new LogInput(State.DENY, userName, date);
	}

	public static LogInput createAcceptLog(String userName, Calendar date) {
		return new LogInput(State.ACCEPT, userName, date);
	}
	

	public static LogInput createIntegrityLog(Calendar date) {
		return new LogInput(State.INTEGRITY, "integrity problem", date);
	}
	
	public boolean isDeny() {
		return this.state == State.DENY;
	}
	public boolean isAccept() {
		return this.state == State.ACCEPT;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return this.userName+"@"+this.date+":"+this.state;
	}

	/**
	 * @return the date
	 */
	public Calendar getDate() {
		return this.date;
	}
	
	
}

package security;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AccessLogger {

	private LogInput lastLog;
	private List<LogInput> logs;

	public AccessLogger() {
		this.lastLog = null;
		this.logs = new ArrayList<>();
	}
	
	public void logValidAccess(User user, Calendar date) {
		this.lastLog = LogInput.createAcceptLog(user.getName(), date);
		this.logs.add(lastLog);		
	}

	public void logDenyAccess(User user, Calendar date) {
		this.lastLog = LogInput.createDenyLog(user.getName(), date);
		this.logs.add(this.lastLog);		
	}

	public List<LogInput> getLogs() {
		return this.logs;
	}

	public LogInput getLastLog() {
		return this.lastLog;
	}

	public void logIntegrityProblem( Calendar date) {
		this.lastLog = LogInput.createIntegrityLog(date);
		this.logs.add(this.lastLog);
	}
}

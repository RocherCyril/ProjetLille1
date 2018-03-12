package security;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DoorTest {

	private Door door;
	private User user;
	
	@Before
	public void init() throws AccessDeniedException {
		door = new Door(new NoControl());
		user = new User("timoleon");
		// initialize logger with an input
		door.acceptUser(user);
	}
	@Test
	public void logAnNewAcceptWhenAccessIsGranted() { 
		LogInput previous = door.getLogger().getLastLog();
		door.acceptUser(user);
		// check there is a new log
		assertNotSame(previous, door.getLogger().getLastLog());
		// check this new log is an accept
		assertTrue(door.getLogger().getLastLog().isAccept());
	}

	@Test
	public void logANewDenyWhenAccessIsGranted() {
		LogInput previous = this.door.getLogger().getLastLog();
		door.changeAccessControl(new AlwaysDenyControlMock());
		door.acceptUser(user);
		// check there is a new log
		assertNotSame(previous, door.getLogger().getLastLog());
		// check this new log is an accept
		assertTrue(door.getLogger().getLastLog().isDeny());
	}
	

}

package security;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class UserTest {

	private Door door;
	private User user;	

	protected User createUser() {
		return  new User("timoleon");
	}
	
	@Before
	public void init() throws AccessDeniedException {
		door = new Door(new NoControl());
		user = this.createUser();
		// initialize logger with an input
		door.acceptUser(user);
	}

	@Test
	public void testOpenIsOkWhenAccessGranted() {
		try {
			user.open(door);
			// test pass if no exception
			assertTrue(true);
		} catch (AccessDeniedException e) {
			// no exception if access is granted
			fail();
		}
	}

	@Test(expected = AccessDeniedException.class)
	public void testOpenThrowsExceptionWhenAccessNotGranted() throws AccessDeniedException {
		door.changeAccessControl(new AlwaysDenyControlMock());
		// should throw exception
		user.open(door);
		// test fails if no exception
		fail();
	}

}

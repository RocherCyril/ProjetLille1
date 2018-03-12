package security;

import static org.junit.Assert.*;

import org.junit.Test;

public class AccessControlTest {

	@Test
	public void testAccessGrantedCallCheckIntegrity() {
		MockAccessControl mock = new MockAccessControl();
		try {
			mock.accessGranted(null);
			assertEquals(1, mock.nbCallsIntegrity);
			mock.accessGranted(null);
			assertEquals(2, mock.nbCallsIntegrity);
		} catch(IntegrityException e) {
			// checkIntegrity is not called before checkAccess
			fail();
		}
	}

	private class MockAccessControl extends AccessControl {

		public int nbCallsIntegrity = 0;
		public int nbCallsAccess = 0;

		public void checkIntegrity() throws IntegrityException {
			nbCallsIntegrity++;
			if (nbCallsIntegrity  != nbCallsAccess + 1) throw new IntegrityException();			
		}

		protected boolean checkAccess(User user) {
			nbCallsAccess++;
			return true;
		}

	}
}

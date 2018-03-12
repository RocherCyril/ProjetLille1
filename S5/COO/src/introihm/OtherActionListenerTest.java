package introihm;

/**
 * ActionListenerTest.java
 *
 * @author <a href="mailto:jean-christophe.routier@lifl.fr" Jean-Christophe Routier</a>
 * @version
 */
import javax.swing.*;
import java.awt.event.*;

public class OtherActionListenerTest implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    JOptionPane.showMessageDialog(null, "un second listener pour le click");
	}
}

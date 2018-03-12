package introihm;

/**
 * TestEvenement.java
 *
 * @author <a href="mailto:jean-christophe.routier@lifl.fr">Jean-Christophe Routier</a>
 * @version
 */
import java.awt.event.*;
import java.awt.FlowLayout;
import javax.swing.*;

public class SecondEventExperiment {

	private JButton aButton;

	public void experiment() {
		JFrame f = new JFrame("second event frame");
		f.addWindowListener(new CloseWindowEvent());
		f.setLocation(100, 300);
		f.setSize(100, 100);

		aButton = new JButton("cliquez ici : il y a 2 listeners !");
		f.setLayout(new FlowLayout());

		// abonnement d'un listener :
		aButton.addActionListener(new ActionListenerTest());
		aButton.addActionListener(new OtherActionListenerTest());

		f.add(aButton);

		f.pack();
		f.setVisible(true);
	}

	public static void main(String[] args) {
		new SecondEventExperiment().experiment();
	}

	// ----------------------------------------------------------------------
	// CLASSE INTERNE, revue au point 2.5 du sujet de TP
	// pour gérer la fermeture de l'application lorsqu'on ferme une fenêtre
	// ----------------------------------------------------------------------
	class CloseWindowEvent extends WindowAdapter {
		public void windowClosing(java.awt.event.WindowEvent e) {
			System.exit(0);
		}
	}
}

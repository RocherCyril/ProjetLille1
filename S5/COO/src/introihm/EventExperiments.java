package introihm;

/**
 * TestEvenement.java
 */
import java.awt.event.*;
import java.awt.FlowLayout;

import javax.swing.*;

public class EventExperiments {

	private JButton aButton;

	public void experiment() {
		JFrame f = new JFrame("event frame");
		f.addWindowListener(new CloseWindowEvent());
		f.setLocation(100, 300);
		f.setSize(100, 100);

		aButton = new JButton("cliquez ici");
		f.setLayout(new FlowLayout());

		// abonnement d'un listener :
		aButton.addActionListener(new ActionListenerTest());

		f.add(aButton);

		f.pack();
		f.setVisible(true);
	}

	public static void main(String[] args) {
		new EventExperiments().experiment();
	}



	// ----------------------------------------------------------------------
	// CLASSE INTERNE, revue au point 2.5 du sujet de TP
        // pour gérér la fermeture de l'application lorsqu'on ferme une fenêtre
	// ----------------------------------------------------------------------
	private class CloseWindowEvent extends WindowAdapter {
		public void windowClosing(java.awt.event.WindowEvent e) {
			System.exit(0);
		}
	}  
}

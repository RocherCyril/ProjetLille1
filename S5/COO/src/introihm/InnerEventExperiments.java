package introihm;

/**
 * TestInterneEvenement.java
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class InnerEventExperiments {

	private JButton aButton;
	private JLabel aLabel;

	public void experiment() {
		JFrame f = new JFrame("event frame");
		f.addWindowListener(new CloseWindowEvent());

		f.setLocation(100, 300);
		f.setSize(100, 100);

		f.setLayout(new FlowLayout());

		aButton = new JButton("cliquez ici");

		// abonnement d'un listener :
		aButton.addActionListener(new InnerActionListener());

		f.add(aButton);

		f.pack();
		f.setVisible(true);
	}

	public static void main(String[] args) {
		new InnerEventExperiments().experiment();
	}

	// ----------------------------------------------------------------------
        // le listener au clic sur le bouton
	// ----------------------------------------------------------------------

	private class InnerActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
		    InnerEventExperiments.this.aButton.setText("j'ai été cliqué");
		}
	}



	// ----------------------------------------------------------------------
        // pour gérer la fermeture de l'application lorsqu'on ferme une fenêtre
	// ----------------------------------------------------------------------
	private class CloseWindowEvent extends WindowAdapter {
		public void windowClosing(java.awt.event.WindowEvent e) {
			System.exit(0);
		}
	}  
}

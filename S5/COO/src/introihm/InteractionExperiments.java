package introihm;

/**
 * TestInteraction.java
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class InteractionExperiments {

	private JButton aButton;
	private JLabel aLabel;

	public void experiment() {
		JFrame f = new JFrame("event frame");
		f.setLocation(100, 300);
		f.setSize(100, 100);

		f.setLayout(new FlowLayout());

		aButton = new JButton("cliquez ici");
		aLabel = new JLabel("depart");

		// abonnement d'un listener :
		aButton.addActionListener(new InnerActionListener());

		f.add(aButton);
		f.add(aLabel);

		f.pack();
		f.setVisible(true);
	}

	public static void main(String[] args) {
		new InteractionExperiments().experiment();
	}

	private class InnerActionListener implements ActionListener {		
		private boolean truc = true;

		public void actionPerformed(ActionEvent e) {
			this.truc = !this.truc;
			InteractionExperiments.this.aLabel.setText(""+this.truc);
		}
	}

}

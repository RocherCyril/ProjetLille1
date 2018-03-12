package introihm;

/**
 * TestTextField.java
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class TextFieldExperiments {

	private JButton aButton;

	private JLabel aLabel;

	private JTextField aTextField;


	public void experiment() {
		JFrame f = new JFrame("event frame");
		f.setLocation(100, 300);
		f.setSize(100, 100);
		f.addWindowListener(new CloseWindowEvent());

		f.setLayout(new BorderLayout());

		aButton = new JButton("cliquez ici");
		aLabel = new JLabel("depart");
		aTextField = new JTextField();

		// abonnement d'un listener unBouton
		aButton.addActionListener(new ButtonActionListenerExample());
		// abonnement d'un listener à unTexte
		aTextField.addActionListener(new TextFieldActionListenerExample());
		aTextField.addKeyListener(new TextKeyListenerExample());

		f.add(aButton, BorderLayout.NORTH);
		f.add(aLabel, BorderLayout.CENTER);
		f.add(aTextField, BorderLayout.SOUTH);

		f.pack();
		f.setVisible(true);
	}

	public static void main(String[] args) {
		new TextFieldExperiments().experiment();
	}

	//
	// CLASSES INTERNES POUR LES EVENEMENTS
	//
    // pour le clic sur le bouton
	private class ButtonActionListenerExample implements ActionListener {
		private boolean truc = true;
		public void actionPerformed(ActionEvent e) {
			this.truc = ! this.truc;
			TextFieldExperiments.this.aLabel.setText(new Boolean(this.truc).toString());
		}
	}// 

    // pour le ENTRE sur le textfield
	private class TextFieldActionListenerExample implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			TextFieldExperiments.this.aLabel.setText(aTextField.getText()+" saisi");
		}
	}// 

    // pour la frappe d'une touche dans le textfield
	private class TextKeyListenerExample implements KeyListener {
		public void keyTyped(KeyEvent e) {
			char c = e.getKeyChar();
			if (c == 'X') {
				TextFieldExperiments.this.aButton.setText("X appuyé !");
			} else if (c == 'x') {
				TextFieldExperiments.this.aButton.setText("cliquez ici");
			}
		}

		// obligatoire à cause de l'interface KeyListener mais ici on ne souhaite rien faire dans ce casa
		public void keyPressed(KeyEvent e) {
		}
		// obligatoire à cause de l'interface KeyListener mais ici on ne souhaite rien faire dans ce casa
		public void keyReleased(KeyEvent e) {
		}
	}

    // pour l'événement de fermeture de la fenêtre
	private class CloseWindowEvent extends WindowAdapter {
		public void windowClosing(java.awt.event.WindowEvent e) {
			System.exit(0);
		}
	}

}

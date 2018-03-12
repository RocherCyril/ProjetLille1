package introihm;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;



public class LayoutExperiments {


	/*
	 * placement des éléments avec un FlowLayout : tant qu'on peut de gauche à droite puis de haut en
	 * bas
	 */
	public void flowLayoutExperiment() {
		JFrame f = new JFrame("flow frame");
		f.addWindowListener(new CloseWindowEvent());
		f.setLocation(100, 100);

		f.setLayout(new FlowLayout());

		f.add(new JButton("b1"));
		f.add(new JButton("b2"));
		f.add(new JButton("b3"));
		f.add(new JButton("b4"));
		f.add(new JButton("b5"));
		f.add(new JButton("b6"));

		f.pack();
		f.setVisible(true);
	}


	/*
	 * placement des éléments avec un BorderLayout : notion de Nord,Sud,...
	 */
	public void borderLayoutExperiment() {
		JFrame f = new JFrame("border frame");
		f.addWindowListener(new CloseWindowEvent());
		f.setLocation(100, 200);

		f.setLayout(new BorderLayout());

		f.add(new JButton("b1"), BorderLayout.NORTH);
		f.add(new JButton("b2"), BorderLayout.SOUTH);
		f.add(new JButton("b3"), BorderLayout.EAST);
		f.add(new JButton("b4"), BorderLayout.WEST);
		f.add(new JButton("b5"), BorderLayout.CENTER);


		// celui-ci est placé au meme endroit que B1,
		// constatez-en la conséquence sur le composant
		// graphique
		f.add(new JButton("b6"), BorderLayout.NORTH);

		f.pack();
		f.setVisible(true);
	}

	/*
	 * placement des éléments avec un GridLayout : ici une grille de 3 lignes et 2 colonnes, chaque
	 * élément est placé dans une "case" donnée
	 */
	public void gridLayoutExperiment() {
		JFrame f = new JFrame("grid frame");
		f.addWindowListener(new CloseWindowEvent());
		f.setLocation(100, 400);

		f.setLayout(new GridLayout(3, 2));

		f.add(new JButton("b1"));
		f.add(new JButton("b2"));
		f.add(new JButton("b3"));
		f.add(new JButton("b4"));
		f.add(new JButton("b5"));
		f.add(new JButton("b6"));

		f.pack();
		f.setVisible(true);
	}





	public void combinedLayoutExperiment() {
		JFrame f = new JFrame("combined layout frame");
		f.addWindowListener(new CloseWindowEvent());
		f.setLocation(300, 200);

		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(2,2));

		panel1.add(new JButton("b1-1 dans panel1"));
		panel1.add(new JButton("b2-1 dans panel1"));
		panel1.add(new JButton("b3-1 dans panel1"));
		panel1.add(new JButton("b4-1 dans panel1"));

		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());

		panel2.add(new JButton("b1-2 dans panel2"));
		panel2.add(new JButton("b2-2 dans panel2"));
		panel2.add(new JButton("b3-2 dans panel2"));

		f.add(panel2, BorderLayout.NORTH);
		f.add(new JLabel("panel1 au sud et panel2 au nord"), BorderLayout.CENTER);
		f.add(panel1, BorderLayout.SOUTH);

		f.pack();
		f.setVisible(true);
	}

	public static void main(String[] args) {
	    LayoutExperiments t = new LayoutExperiments();

	    t.flowLayoutExperiment();
	    t.borderLayoutExperiment();
	    t.gridLayoutExperiment();

	    
	    t.combinedLayoutExperiment();
	}



	// ----------------------------------------------------------------------
	// CLASSE INTERNE, revue au point 2.5 du sujet de TP
        // pour gérer la fermeture de l'application lorsuq'on ferme une fenêtre
	// ----------------------------------------------------------------------
	private class CloseWindowEvent extends WindowAdapter {
		public void windowClosing(java.awt.event.WindowEvent e) {
			System.exit(0);
		}
	}  
}

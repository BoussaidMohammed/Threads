package org.mql.threads.app;


import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
public class Examples extends JFrame implements MouseListener {
	private JPanel screen;

	public Examples() {
		exp2();
		info();
	}

	void exp1() {
		init();
		Pingouin p = new Pingouin("p01", 100, 100);
		p.start();
		screen.add(p);
		showJFrame();
	}
	
	void exp2() {
		init();
		MyCharacter p = new MyCharacter("p01", 100, 100);
		p.start();
		screen.add(p);
		showJFrame();
	}

	public static void main(String[] args) {
		new Examples();
	}
	
	private void init() {
		screen = new JPanel();
		screen.setBackground(Color.black);
		screen.setLayout(null);
		setContentPane(screen);
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setVisible(true);
	}

	private void showJFrame() {
		screen.addMouseListener(this);
		setVisible(true);
	}
	
	private static void info() {
		JFrame j = new JFrame("GUIDE D'UTILISATION");
		j.setDefaultCloseOperation(HIDE_ON_CLOSE);
		JPanel content = new JPanel();
		j.setContentPane(content);
		
		JTextPane text = new JTextPane();
		text.setBackground(null);
		text.setText(
				"Le caractère cible est le caractère entouré par un rectangle rouge,\n"+
				"vous pouvez le déplacer par les buttons du clavier (Up, down, left, right) \n"
				+ "et également d'effectuer sur lui les actions ci-dessous :\n\n"+
				"1) Pour cibler un caractère cliquer sur le caractère voulue.\n" +
				"2) Pour changer le caractère cible veuillez taper sur ('C')\n"+
				"3) Pour augmenter la vitesse du caractère cible veuillez taper sur (SHIFT)\n"+
				"4) Pour diminuer la vitesse du caractère cible veuillez taper sur (ESPACE)\n"+
				"5) Pour afficher les statistiques du caractère cible veuillez cliquer sur la touche:'S'**\n"+
				"6) Pour supprimer un caractère veuillez taper sur la touche: (SUPPR) \n"+
				"7) Pour renommer un caractère veuillez cliquer deux fois sur: (SOURIE GAUCHE) \n"+
				"8) Pour changer le pas d'un caractère veuillez taper sur: ('P') \n"+
				"9) Pour ajouter un nouveau caractère cliquer quelque part sur l'écran"
		);
		content.setLayout(new GridLayout(1,1));
		text.setFont(new Font(null,Font.BOLD,14));
		text.setBorder(new TitledBorder(new EtchedBorder()," info d'utilisation "));
		
		content.add(text);
		j.setSize(600,400);
		j.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1) {
			MyCharacter p = null;
			p = new MyCharacter("p02", e.getX(), e.getY());
			screen.add(p);
			p.start();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	void exp() {

	}

}

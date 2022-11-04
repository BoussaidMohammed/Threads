package org.mql.threads.app;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.mql.threads.models.Numbers;

public class StatisticThread extends JFrame implements Runnable {
	private Thread runner;
	private JPanel content;
	private JLabel l1,l2,l3,l4,l5,l6;
	private Numbers s;
	
	
	public StatisticThread(Numbers s) {
		runner = new Thread(this);
		setTitle(runner.getName() + " statistiques:");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		content = new JPanel();setContentPane(content);
		setContentPane(content); content.setLayout(new GridLayout(6,1));
		l1 = new JLabel();l2 = new JLabel();l3 = new JLabel();
		l4 = new JLabel();l5 = new JLabel();l6 = new JLabel();
		content.add(l1);content.add(l2);content.add(l3);
		content.add(l4);content.add(l5);content.add(l6);
		setSize(400,300);
		this.s =s;
		setVisible(true);
	}

	@Override
	public void run() {
		do {
			l1.setText("Nombre total des pas:"+ s.getSteps());
			l2.setText("Nombre des fois le caractère a pris la gauche: "+ s.getGoLeft());
			l3.setText("Nombre des fois le caractère a pris la gauche: "+s.getGoRight()+ "");
			l4.setText("Nombre des fois le caractère a pris l'haut: "+ s.getGoUp());
			l5.setText("Nombre des fois le caractère a pris le bas: "+ s.getGoDown());
			l6.setText("Nombre des fois le caractère a arrêté: "+ s.getStop());
			pause(39);
		}while(true);
	}
	
	private void pause(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {}
	}

	public void start() {
		runner.start();
	}

}

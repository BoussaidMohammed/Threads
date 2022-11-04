package org.mql.threads.app;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.mql.threads.models.CharacterGame;
import org.mql.threads.models.Numbers;
import org.mql.threads.parser.CharactersParser;

public class MyCharacter extends JPanel implements Runnable, MouseListener, KeyListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	private Thread runner;
	private static Vector<Position> positions = new Vector<>();
	private Position position;
	private int width = 80;
	private int height = 60;
	private Image images[];
	private int current = 0;
	private int step = 5;
	private int tempo = 90;
	private static int minX = 0;
	private static int maxX = 1336; // 1330;
	private static int minxY = 0;
	private static int maxY = 670;// 670;
	private static long activeThread = 0;
	private int keyCode = 0;
	private int OldKeyCode = 0;
	public static final int NORMAL_SPEED = 0, SPEED1 = 1, SPEED2 = 2, SPEED3 = 3;
	public static final int LOW_SPEED1 = -1, LOW_SPEED2 = -2, LOW_SPEED3 = -3;
	private int speed = NORMAL_SPEED;
	private int type;
	private Numbers statistics;
	private List<CharacterGame> characters;

	public MyCharacter(String name, int x, int y) {
		runner = new Thread(this, name);
		characters = new CharactersParser("resources/characters.xml").getCharacters();
		type = (int) (Math.random() * characters.size());
		statistics = new Numbers(0, 0, 0, 0, 0, 0);
		position = new Position(x, y);
		positions.add(position);
		while (!isSpaceIsValable()) {
			position.setX(position.getX() + 30);
			position.setY(position.getY() + 30);
		}
		setLocation(position.getX(), position.getY());
		setSize(width, height);
		setBackground(Color.black);
		loadImages();
		repaint();
		activeListeners();
	}

	private void activeListeners() {
		addMouseListener(this);
		addKeyListener(this);
		addMouseMotionListener(this);
	}

	public MyCharacter(String name, int x, int y, int tempo, int step, int type) {
		this(name, x, y);
		this.tempo = tempo;
		this.step = step;
	}

	private void loadImages() {
		CharacterGame character = characters.get(type);
		runner.setName(character.getName());
		// String resources[] = new String[character.getRessources().size()];
		List<String> resources = character.getRessources();
	
		images = new Image[resources.size()];
		for (int i = 0; i < images.length; i++) {
			// Toolkit.getDefaultToolkit().createImage("");
			ImageIcon icon = new ImageIcon(character.getIconsFolder() + resources.get(i) + character.getImgType());
			images[i] = icon.getImage();
			System.out.println(character.getIconsFolder() + resources.get(i) + character.getImgType());
		}
	}

	public void run() {

		do {

			if (activeThread == runner.getId()) {
				if (keyCode == KeyEvent.VK_DELETE) {
					stop(1);
					setVisible(false);
					;
					break;
				} else
					action();
			} else {
				int decision = (int) (Math.random() * 100);
				if (decision < 20) {
					goRight(5);
				} else if (decision < 40) {
					goLeft(5);
				} else if (decision < 60) {
					stop(5);
				} else if (decision < 80) {
					goUp(5);
				} else
					goDown(5);
			}
		} while (true);
		getParent().remove(this);
	}

	public void goRight(int times) {
		for (int n = 0; n < times; n++) {
			for (int i = 1; i <= 3; i++) {
				position.setX(position.getX() + step);
				if (position.getX() >= maxX || !isSpaceIsValable())
					goLeft(5);
				setLocation(position.getX(), position.getY());
				current = i;
				repaint();
				pause(tempo);
				statistics.setGoRight(statistics.getGoRight() + 1);
				statistics.setSteps(statistics.getSteps() + step);
			}
		}

	}

	private void goLeft(int times) {
		for (int n = 0; n < times; n++) {
			for (int i = 4; i <= 6; i++) {
				position.setX(position.getX() - step);
				if (position.getX() <= minX || !isSpaceIsValable())
					goRight(5);
				setLocation(position.getX(), position.getY());
				current = i;
				repaint();
				pause(tempo);
				statistics.setGoLeft(statistics.getGoLeft() + 1);
				statistics.setSteps(statistics.getSteps() + step);
			}
		}
	}

	private void goUp(int times) {
		for (int n = 0; n < times; n++) {
			for (int i = 10; i <= 12; i++) {
				position.setY(position.getY() - step);
				if (position.getY() <= minxY || !isSpaceIsValable())
					goDown(5);
				setLocation(position.getX(), position.getY());
				current = i;
				repaint();
				pause(tempo);
				statistics.setGoUp(statistics.getGoUp() + 1);
				statistics.setSteps(statistics.getSteps() + step);
			}
		}
	}

	private void goDown(int times) {
		for (int n = 0; n < times; n++) {
			for (int i = 7; i <= 9; i++) {
				position.setY(position.getY() + step);
				if (position.getY() >= maxY || !isSpaceIsValable())
					goUp(5);
				setLocation(position.getX(), position.getY());
				current = i;
				repaint();
				pause(tempo);
				statistics.setGoDown(statistics.getGoDown() + 1);
				statistics.setSteps(statistics.getSteps() + step);
			}
		}
	}

	public void stop(int times) {
		for (int n = 0; n < times; n++) {
			current = 0;
			repaint();
			pause(tempo);
			statistics.setStop(statistics.getStop() + 1);
		}
	}

	public void pause(int time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(images[current], 15, 10, null);
		g.setColor(Color.white);
		String name = runner.getName();
		if (name.length() <= 7)
			g.drawString("\"" + name + "\"", 25, 10);
		else if (name.length() <= 10)
			g.drawString("\"" + name + "\"", 20, 10);
		else
			g.drawString("\"" + name + "\"", 0, 10);

		if (activeThread == runner.getId()) {
			g.setColor(Color.RED);
			g.drawRect(0, 0, 79, 59);
			g.drawRect(1, 1, 77, 57);
		}
	}

	public boolean isSpaceIsValable() {
		for (Position p : positions) {
			if (position != p)
				if (p.isItNear(position))
					return false;
		}
		return true;
	}

	public void start() {
		runner.start();
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
			String msg = JOptionPane.showInputDialog("Entrez un nom");
			if (msg != null)
				runner.setName(msg);
		}

		if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1) {
			activeThread = runner.getId();
			requestFocus();
		}

	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		System.out.println(e.getKeyCode());
		OldKeyCode = keyCode;
		keyCode = e.getKeyCode();
	}

	public void mouseDragged(MouseEvent e) {
		setLocation(e.getX(), e.getY());
		current = 0;
		repaint();
	}

	private void action() {
		pause(5);
		if (keyCode == 0) {
			stop(2);
		}
		if (keyCode == KeyEvent.VK_RIGHT) {
			goRight(1);
		} else if (keyCode == KeyEvent.VK_LEFT) {
			goLeft(1);
		} else if (keyCode == KeyEvent.VK_DOWN) {
			goDown(1);
		} else if (keyCode == KeyEvent.VK_UP) {
			goUp(1);
		} else if (keyCode == 'V') {
			String msg = JOptionPane.showInputDialog("Enter le rhytme");
			tempo = Integer.parseInt(msg);
			keyCode = OldKeyCode;
		} else if (keyCode == 'P') {
			String msg = JOptionPane.showInputDialog("Enter le pas");
			keyCode = OldKeyCode;
			step = Integer.parseInt(msg);
		}else if (keyCode == 'S') {
			setStatisticsFrame();
		}
		else if (keyCode == KeyEvent.VK_SPACE) {
			speed--;
			if (speed < LOW_SPEED3)
				speed = 0;
			speed();
			keyCode = OldKeyCode;
		} else if (keyCode == KeyEvent.VK_SHIFT) {
			speed++;
			if (speed > SPEED3)
				speed = NORMAL_SPEED;
			speed();
			keyCode = OldKeyCode;
		} else if (keyCode == 'C') {
			type++;
			if (type >= characters.size())
				type = 0;
			pause(20);
			loadImages();
			keyCode = 0;
		}
	}

	public void speed() {
		switch (speed) {
		case NORMAL_SPEED:
			step = 5;
			tempo = 100;
			break;
		case SPEED1:
			step = 7;
			tempo = 70;
			break;
		case SPEED2:
			step = 10;
			tempo = 50;
			break;
		case SPEED3:
			step = 13;
			tempo = 35;
			break;
		case LOW_SPEED1:
			step = 5;
			tempo = 130;
			break;
		case LOW_SPEED2:
			step = 5;
			tempo = 170;
			break;
		case LOW_SPEED3:
			step = 3;
			tempo = 200;
			break;
		}
	}
	
	private void setStatisticsFrame() {
		StatisticThread s = new StatisticThread(statistics);
		s.start();
		keyCode = OldKeyCode;
	}

}

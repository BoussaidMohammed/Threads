package org.mql.threads.app;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Pingouin extends JPanel implements Runnable, MouseListener, KeyListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	private Thread runner;
	private static Vector<Position> positions = new Vector<>();
	private Position position;
	private int width = 60;
	private int height = 40;
	private String resources[] = { "stop", "right1", "right2", "right3", "left1", "left2", "left3" };
	private String type = ".gif";
	private String path = "resources/";
	private Image images[];
	private int current = 0;
	private int step = 5;
	private int tempo = 90;
	private int minX = 0;
	private int maxX = 1336; // 1330;
	private int minxY = 0;
	private int maxY = 670;// 670;
	private static long activeThread = 0;
	private int keyCode = 0;
	private Point oldPoint;

	public Pingouin(String name, int x, int y) {
		runner = new Thread(this, name);

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
		repaint();// Appeler paintConponent
		activeListeners();
	}

	private void activeListeners() {
		addMouseListener(this);
		addKeyListener(this);
		addMouseMotionListener(this);
	}

	public Pingouin(String name, int x, int y, int tempo, int step) {
		this(name, x, y);
		this.tempo = tempo;
		this.step = step;

	}

	private void loadImages() {
		images = new Image[resources.length];
		for (int i = 0; i < images.length; i++) {
			// Toolkit.getDefaultToolkit().createImage("");
			ImageIcon icon = new ImageIcon(path + resources[i] + type);
			images[i] = icon.getImage();
		}
	}

	public void run() {

		do {

			if (activeThread == runner.getId()) {
				pause(20);stop(1);
				if (keyCode == KeyEvent.VK_RIGHT) {
					goRight(5);
				} else if (keyCode == KeyEvent.VK_LEFT) {
					goLeft(5);
				}
				else if (keyCode == KeyEvent.VK_DOWN) {
					goDown(5);
				}else if(keyCode == KeyEvent.VK_UP) {
					 goUp(5);
				}else if(keyCode == KeyEvent.VK_DELETE) {
					stop(2);setVisible(false);;break;
				}else if(keyCode == 'V') {
					String msg = JOptionPane.showInputDialog("Enter Speed");
					tempo = Integer.parseInt(msg);
				}else if(keyCode == 'P') {
					String msg = JOptionPane.showInputDialog("Enter Speed");
					step = Integer.parseInt(msg);
				}else if(keyCode == KeyEvent.VK_SPACE) {
					step = 3;
					tempo = 1000;
				}else if(keyCode == KeyEvent.VK_SHIFT) {
					tempo = 50;
					step = 10;
				}
				keyCode = 0;
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
			}
		}
	}

	private void goUp(int times) {
		for (int n = 0; n < times; n++) {
			position.setY(position.getY() - step);
			if (position.getY() <= minxY || !isSpaceIsValable())
				goDown(5);
			setLocation(position.getX(), position.getY());
			current = 0;
			repaint();
			pause(tempo);

		}
	}

	private void goDown(int times) {
		for (int n = 0; n < times; n++) {
			position.setY(position.getY() + step);
			if (position.getY() >= maxY || !isSpaceIsValable())
				goUp(5);
			setLocation(position.getX(), position.getY());
			current = 0;
			repaint();
			pause(tempo);
		}
	}

	public void stop(int times) {
		for (int n = 0; n < times; n++) {
			current = 0;
			repaint();
			pause(tempo);
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
		if (name.length() <= 5)
			g.drawString("\"" + name + "\"", 15, 10);
		else if (name.length() <= 7)
			g.drawString("\"" + name + "\"", 10, 10);
		else
			g.drawString("\"" + name + "\"", 0, 10);

		if (activeThread == runner.getId()) {
			g.setColor(Color.RED);
			g.drawRect(0, 0, 59, 39);
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

	@Override
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

		if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON3) {
			getParent().remove(this);
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		oldPoint = e.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println(e.getKeyCode());
		keyCode = e.getKeyCode();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		setLocation(e.getX(),e.getY());
		current = 0;
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}

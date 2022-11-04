package org.mql.threads.app;

public class Position {
	private int x,y;
	
	public Position() {
		// TODO Auto-generated constructor stub
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Position(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	

	
	@Override
	public String toString() {
		return  "(" + x + "," + y+ ")";
	}

	public boolean isItNear(Position position) {
			if (Math.abs(x - position.x) < 80 && Math.abs(y - position.y) < 60) return true;
			return false;
	}
}

package org.mql.threads.models;

public class Numbers {
	private int steps;
	private int goLeft;
	private int goRight;
	private int goUp;
	private int goDown;
	private int stop;
	
	public Numbers() {
		// TODO Auto-generated constructor stub
	}

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public int getGoLeft() {
		return goLeft;
	}

	public void setGoLeft(int goLeft) {
		this.goLeft = goLeft;
	}

	public int getGoRight() {
		return goRight;
	}

	public void setGoRight(int goRight) {
		this.goRight = goRight;
	}

	public int getGoUp() {
		return goUp;
	}

	public void setGoUp(int goUp) {
		this.goUp = goUp;
	}

	public int getGoDown() {
		return goDown;
	}

	public void setGoDown(int goDown) {
		this.goDown = goDown;
	}

	public int getStop() {
		return stop;
	}

	public void setStop(int stop) {
		this.stop = stop;
	}

	public Numbers(int steps, int goLeft, int goRight, int goUp, int goDown, int stop) {
		super();
		this.steps = steps;
		this.goLeft = goLeft;
		this.goRight = goRight;
		this.goUp = goUp;
		this.goDown = goDown;
		this.stop = stop;
	}
	
	
}

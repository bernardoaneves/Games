import java.awt.Color;

import sedgewick.StdDraw;

public class Square {

	// INSTANCE VARIABLES
	private final int x;
	private final int y;
	private final double radius = 0.0625;
	private final boolean isBlack;
	private boolean isTaken = false;
	private boolean isTakenByRed = false;
	private boolean isTakenByWhite = false;

	// CONSTRUCTOR
	public Square(int thisX, int thisY, boolean black){

		this.x = thisX;
		this.y = thisY;
		this.isBlack = black;

	}

	// OTHER METHODS
	public void draw(){

		if(this.isBlack)
			StdDraw.setPenColor(Color.black);
		else
			StdDraw.setPenColor(Color.white);

		double posX = this.getPosX();
		double posY = this.getPosY();
		StdDraw.filledSquare(posX, posY, this.radius);

	}

	public double getPosX(){
		return 0.0625 + this.x*0.125;
	}
	public double getPosY(){
		return 0.0625 + this.y*0.125;
	}

	public boolean isTakenByWhite(){
		return this.isTakenByWhite;
	}
	public boolean isTakenByRed(){
		return this.isTakenByRed;
	}
	public boolean isTaken(){
		return this.isTaken;
	}
	public void occupyWithWhite(){
		this.isTakenByWhite = true;
		this.isTaken = true;
	}
	public void occupyWithRed(){
		this.isTakenByRed = true;
		this.isTaken = true;
	}
	public void occupy(){
		this.isTaken = true;
	}
	public void free(){
		this.isTaken = false;
		this.isTakenByRed = false;
		this.isTakenByWhite = false;
	}

	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}

	public boolean isBlack(){
		return this.isBlack;
	}

}

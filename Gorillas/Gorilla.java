import java.awt.Color;

import sedgewick.StdDraw;

public class Gorilla {

	// INSTANCE VARIABLES
	private final double posX;
	private double posY;
	private int numLives = 5;
	private final double radius = 0.05;

	// CONSTRUCTOR
	public Gorilla(double x, double y){

		this.posX = x;
		this.posY = y;

	}

	// METHODS
	// draw
	public void draw(){
		StdDraw.setPenColor(Color.red);
		StdDraw.filledCircle(this.posX, this.posY, this.radius);
		StdDraw.setPenColor(Color.black);
		StdDraw.text(this.posX, this.posY, ""+this.numLives);
		StdDraw.setPenColor(Color.blue);
		StdDraw.filledRectangle(this.posX, (this.posY - this.radius)/2.0, this.radius, (this.posY - this.radius)/2.0);
	}

	// getters
	public double getPosX(){
		return this.posX;
	}
	public double getPosY(){
		return this.posY;
	}
	public double getRadius(){
		return this.radius;
	}
	public int getNumLives(){
		return this.numLives;
	}

	// randomize height
	public void changeHeight(){
		double newY = 0.1 + Math.random()*0.5;
		this.posY = newY;
	}
	// take a life
	public void takeLife(){
		this.numLives--;
	}

}

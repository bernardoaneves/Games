import java.awt.Color;

import sedgewick.StdDraw;

public class Food {

	// INSTANCE VARIABLES
	private double posX;
	private double posY;
	private final double radius = 0.009;

	// CONSTRUCTOR
	public Food(double x, double y){

		this.posX = x;
		this.posY = y;

	}

	// METHODS
	// draw
	public void draw(){

		StdDraw.setPenColor(Color.red);
		StdDraw.filledCircle(this.posX, this.posY, this.radius);

	}

	// move
	public void move(){
		double newX = 0.05 + Math.random()*0.9;
		double newY = 0.05 + Math.random()*0.9;
		this.posX = newX;
		this.posY = newY;
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

}

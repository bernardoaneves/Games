import java.awt.Color;

import sedgewick.StdDraw;

public class Piece {

	// INSTANCE VARIABLES
	private double posX;
	private double posY;
	private final double speed = 3/1000.0;
	private final double radius = 15/1000.0;
	private char direction;


	// CONSTRUCTOR
	public Piece(double x, double y, char d){

		this.posX = x;
		this.posY = y;
		this.direction = d;

	}

	// METHODS
	// draw
	public void draw(){

		StdDraw.setPenColor(Color.white);
		StdDraw.filledSquare(this.posX, this.posY, this.radius*1.1);
		StdDraw.setPenColor(Color.blue);
		StdDraw.filledSquare(this.posX, this.posY, this.radius);

	}

	// move
	public void move(){

		// up
		if(this.direction == 'u'){
			this.posY += this.speed;
		}
		// right
		else if(this.direction == 'r'){
			this.posX += this.speed;
		}
		// down
		else if(this.direction == 'd'){
			this.posY -= this.speed;
		}
		// left
		else if(this.direction == 'l'){
			this.posX -= this.speed;
		}

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
	public char getDirection(){
		return this.direction;
	}

	// setters
	public void setDirection(char c){
		this.direction = c;
	}

}

import java.awt.Color;

import sedgewick.StdDraw;

public class Projectile {

	// INSTANCE VARIABLES
	private double posX;
	private double posY;
	private final double radius = 0.008;
	private boolean isFlying = false;
	private double vX;
	private double vY;
	private final double gravity = 0.001;

	// CONSTRUCTOR
	public Projectile(double x, double y){

		this.posX = x;
		this.posY = y;

	}

	// METHODS
	// draw
	public void draw(){
		if(!this.isFlying)
			return;
		else{
			StdDraw.setPenColor(Color.black);
			StdDraw.filledCircle(this.posX, this.posY, this.radius);
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

	// setters
	public void shoot(){
		this.isFlying = true;
	}
	public void move(){
		this.posX += this.vX;
		this.posY += this.vY;
		this.vY -= this.gravity;
	}
	public void wind(double vWind){
		this.vX += vWind;
	}
	public void calculateVelocities(int speed, int angle, int turn){
		double velocityX = speed*Math.cos(Math.toRadians(angle));
		double velocityY = speed*Math.sin(Math.toRadians(angle));
		this.vX = velocityX/2000.0;
		this.vY = velocityY/2000.0;
		if(turn % 2 != 0)
			this.vX *= -1;
	}

	// reset the bullet to the gorrila position
	public void resetToGorilla(Gorilla g){
		this.posX = g.getPosX();
		this.posY = g.getPosY();
		this.isFlying = false;
	}

}

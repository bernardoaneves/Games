import java.awt.Color;

import cse131.ArgsProcessor;
import sedgewick.StdDraw;

public class Game {

	public static void main(String[] args) {

		// to get player info
		ArgsProcessor ap = new ArgsProcessor(args);
		// create the 2 Gorillas
		Gorilla g1 = new Gorilla(0.1, 0.1);
		Gorilla g2 = new Gorilla(0.9, 0.1);
		// create the projectile
		Projectile bullet = new Projectile(g1.getPosX(), g1.getPosY());
		// count to keep track of whose turn it is
		int count = 0;
		// get info needed from player
		double wind = getWindInfo(count, ap);

		// play
		while(g1.getNumLives() != 0 && g2.getNumLives() != 0){

			// change the heights every turn
			if(count > 1 && count %2 == 0){
				g1.changeHeight();
				g2.changeHeight();
				bullet.resetToGorilla(g1);
			}
			// draw everything
			drawEverything(g1, g2, bullet, wind);
			// get the info for the shot
			int speed = ap.nextInt("Speed:");
			int angle;
			do{
				angle = ap.nextInt("Angle");
			}
			while(angle < 0 || angle > 90);
			// shoot
			shootProjectile(bullet, speed, angle, count);

			while(!collide(bullet, g1, g2, count)){
				drawEverything(g1, g2, bullet, wind);
				bullet.move();
				bullet.wind(wind);
			}

			// next player
			count++;

		}

		// the end
		int winner = (g1.getNumLives() > 0) ? (1) : (2);
		end(winner);

	}

	/**
	 * displays the end screen
	 */
	public static void end(int whoWon){

		StdDraw.clear();
		StdDraw.setPenColor(Color.black);
		StdDraw.filledSquare(0.5, 0.5, 1);
		StdDraw.setPenColor(Color.white);
		StdDraw.text(0.5, 0.5, "Gorilla #" + whoWon + " won");
		StdDraw.show(20);

	}

	/**
	 * takes the necessary action if the bullet has hit a gorilla or the frame of the board
	 * @param b - the bullet
	 * @param g1 - gorilla A
	 * @param g2 - gorilla B
	 * @param turn - whose turn is it?
	 * @return - true if the bullet has hit something
	 */
	public static boolean collide(Projectile b, Gorilla g1, Gorilla g2, int turn){

		if(turn %2 == 0){
			if(collideWall(b)){
				b.resetToGorilla(g2);
				return true;
			}
			else if(collideGorilla(b, g2)){
				g2.takeLife();
				b.resetToGorilla(g2);
				return true;
			}
			return false;
		}
		else{
			if(collideWall(b)){
				b.resetToGorilla(g1);
				return true;
			}
			else if(collideGorilla(b, g1)){
				g1.takeLife();
				b.resetToGorilla(g1);
				return true;
			}
			return false;
		}

	}

	/**
	 * determines if the bullet has hit a gorilla
	 * @param b - the projectile
	 * @param g - a gorilla
	 * @return true if a gorilla has gone down
	 */
	public static boolean collideGorilla(Projectile b, Gorilla g){

		// get the bullet info
		double bx = b.getPosX();
		double by = b.getPosY();
		double br = b.getRadius();
		// get gorilla info
		double gx = g.getPosX();
		double gy = g.getPosY();
		double gr = g.getRadius();
		// do the math
		if(Math.sqrt(Math.pow(bx - gx, 2) + Math.pow(by - gy, 2)) <= br + gr)
			return true;
		return false;

	}

	/**
	 * determines if the bullet has hit the wall
	 * @param b - the bullet
	 * @return true if the bullet hit the wall
	 */
	public static boolean collideWall(Projectile b){

		// get the bullet info
		double x = b.getPosX();
		double y = b.getPosY();
		double r = b.getRadius();
		// return true if bullet hit the wall
		if(y+r>=1 || y-r<=0 || x+r>=1 || x-r<=0)
			return true;
		return false;

	}

	/**
	 * shoots the bullet, calculating its x and y velocities
	 * @param p - the bullet
	 * @param s - the speed
	 * @param a - the angle
	 * @param turn - the count that keeps track of whose turn it is
	 */
	public static void shootProjectile(Projectile p, int s, int a, int turn){

		// do the math
		p.calculateVelocities(s, a, turn);
		// shoot the projectile
		p.shoot();

	}

	/**
	 * gets the desired wind
	 * @param turn - the count that keeps track of whose turn it is
	 * @return the wind
	 */
	public static double getWindInfo(int turn, ArgsProcessor ap){

		// get the info needed to calulate the wind
		int input = -1;
		do{
			input = ap.nextInt("What value for the Wind?");
		}
		while(input < 0 || input > 100);
		String dir = "q";
		do{
			dir = ap.nextString("What direction for the Wind? ('l' or 'r')");
		}
		while(dir.compareTo("l") != 0 && dir.compareTo("r") != 0);
		// do the math
		double wind;
		if(dir.compareTo("l") == 0){
			wind = input/100000.0;
			wind *= -1;
		}
		else{
			wind = input/100000.0;
		}
		// there you go
		return wind;

	}

	/**
	 * draws everything
	 * @param ga - gorilla 1
	 * @param gb - gorilla 2
	 * @param p - the projectile
	 */
	public static void drawEverything(Gorilla ga, Gorilla gb, Projectile p, double w){

		StdDraw.clear();
		drawOutline();
		ga.draw();
		gb.draw();
		p.draw();
		StdDraw.setPenColor(Color.black);
		String dir = (w<0)?(" west"):(" east");
		if(w==0)
			dir = "";
		StdDraw.text(.5, .95, "Wind: "+Math.abs(Math.round(w*100000))+dir);
		StdDraw.show(30);

	}

	/**
	 * draws an outline for the board
	 */
	public static void drawOutline(){

		StdDraw.setPenColor(Color.black);
		StdDraw.setPenRadius(0.004);
		StdDraw.square(.5, .5, .5);

	}

}

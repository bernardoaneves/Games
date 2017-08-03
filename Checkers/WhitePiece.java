import java.awt.Color;

import sedgewick.StdDraw;

public class WhitePiece {

	// INSTANCE VARIABLES
	private int x;
	private int y;
	private final double radius = 0.05;
	private boolean isAlive = true;
	private boolean isSelected = false;
	private boolean isLit = false;

	// CONSTRUCTOR
	public WhitePiece(int thisX, int thisY){

		this.x = thisX;
		this.y = thisY;

	}

	// OTHER METHODS
	public void draw(){

		if(this.isAlive){
			double posX = this.getPosX();
			double posY = this.getPosY();
			if(this.isSelected){
				StdDraw.setPenColor(Color.cyan);
				StdDraw.filledCircle(posX, posY, this.radius*1.15);
			}
			StdDraw.setPenColor(Color.WHITE);
			StdDraw.filledCircle(posX, posY, this.radius);
			if(this.isLit){
				StdDraw.setPenColor(Color.BLUE);
				StdDraw.text(posX, posY, "Lit");
			}
		}
		else
			return;

	}

	public double getPosX(){
		return 0.0625 + this.x*0.125;
	}
	public double getPosY(){
		return 0.0625 + this.y*0.125;
	}
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	public boolean isSelected(){
		return this.isSelected;
	}

	public void kill(){
		this.isAlive = false;
		this.x = -1;
		this.y = -1;
	}
	public void select(){
		this.isSelected = true;
	}
	public void deselect(){
		this.isSelected = false;
	}
	public void makeLit(){
		this.isLit = true;
	}
	public boolean isAlive(){
		return this.isAlive;
	}

	public void movePiece(int targetX, int targetY){

		this.x = targetX;
		this.y = targetY;

	}

	public boolean checkLegalMove(int targetX, int targetY){

		if(this.isLit){
			if(targetX > this.x + 1 || targetX < this.x - 1)
				return false;
			if(targetY > this.y + 1 || targetY < this.y - 1)
				return false;
			return true;
		}
		else{
			if(targetX > this.x + 1 || targetX < this.x - 1)
				return false;
			if(targetY >= this.y || targetY < this.y - 1)
				return false;
			return true;
		}

	}

}

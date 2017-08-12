public class TurningPoint {

	// INSTANCE VARIABLES
	private double posX;
	private double posY;
	private char direction;
	private boolean isActivated = true;

	// CONSTRUCTOR
	public TurningPoint(double x, double y, char dir){

		this.posX = x;
		this.posY = y;
		this.direction = dir;

	}

	// METHODS
	// deactivate
	public void deactivate(){
		this.isActivated = false;
	}
	// getters
	public boolean isActivated(){
		return this.isActivated;
	}
	public double getPosX(){
		return this.posX;
	}
	public double getPosY(){
		return this.posY;
	}
	public char getDirection(){
		return this.direction;
	}

}

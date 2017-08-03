// speed powerup
public class Speed
{
	// INSTANCE VARIABLES
	//    position:
	private double x;
	private double y;
	//    size:
	private double radius;
	//    isFlying:
	private boolean isFlying;

	// CONSTRUCTOR
	public Speed(double givenX, double givenY)
	{
		this.x = givenX;
		this.y = givenY;
		this.radius = 0.035;
		this.isFlying = false;
	}

	// GETTERS
	public double getSpeedX()
	{
		return this.x;
	}
	public double getSpeedY()
	{
		return this.y;
	}
	public double getSpeedRadius()
	{
		return this.radius;
	}
	public boolean isSpeedFlying()
	{
		return this.isFlying;
	}

	// SETTERS
	public void setSpeedX(double newX)
	{
		this.x = newX;
	}
	public void setSpeedY(double newY)
	{
		this.y = newY;
	}
	public void makeSpeedFly()
	{
		this.isFlying = true;
	}
	public void makeSpeedStopFlying()
	{
		this.isFlying = false;
	}
}

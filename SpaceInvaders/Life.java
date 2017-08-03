// life powerup
public class Life
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
	public Life(double givenX, double givenY)
	{
		this.x = givenX;
		this.y = givenY;
		this.radius = 0.035;
		this.isFlying = false;
	}

	// GETTERS
	public double getLifeX()
	{
		return this.x;
	}
	public double getLifeY()
	{
		return this.y;
	}
	public double getLifeRadius()
	{
		return this.radius;
	}
	public boolean isLifeFlying()
	{
		return this.isFlying;
	}

	// SETTERS
	public void setLifeX(double newX)
	{
		this.x = newX;
	}
	public void setLifeY(double newY)
	{
		this.y = newY;
	}
	public void makeLifeFly()
	{
		this.isFlying = true;
	}
	public void makeLifeStopFlying()
	{
		this.isFlying = false;
	}
}

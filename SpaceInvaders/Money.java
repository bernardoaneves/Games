// money powerup
public class Money 
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
	public Money(double givenX, double givenY)
	{
		this.x = givenX;
		this.y = givenY;
		this.radius = 0.035;
		this.isFlying = false;
	}

	// GETTERS
	public double getMoneyX()
	{
		return this.x;
	}
	public double getMoneyY()
	{
		return this.y;
	}
	public double getMoneyRadius()
	{
		return this.radius;
	}
	public boolean isMoneyFlying()
	{
		return this.isFlying;
	}

	// SETTERS
	public void setMoneyX(double newX)
	{
		this.x = newX;
	}
	public void setMoneyY(double newY)
	{
		this.y = newY;
	}
	public void makeMoneyFly()
	{
		this.isFlying = true;
	}
	public void makeMoneyStopFlying()
	{
		this.isFlying = false;
	}
}

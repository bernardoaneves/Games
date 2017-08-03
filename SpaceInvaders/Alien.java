// defines the alien object.
public class Alien
{
	// INSTANCE VARIABLES
	//    position:
	private double x;
	private double y;
	//    is it alive:
	private boolean isAlive;
	//    size:
	private final double radius;

	// CONSTRUCTOR
	public Alien(double givenX, double givenY, double givenRadius)
	{
		this.x = givenX;
		this.y = givenY;
		this.isAlive = true;
		this.radius = givenRadius;
	}

	// GETTERS
	public double getAlienX()
	{
		return this.x;
	}
	public double getAlienY()
	{
		return this.y;
	}
	public boolean isAlienAlive()
	{
		return this.isAlive;
	}
	public double getAlienRadius()
	{
		return this.radius;
	}

	// SETTERS
	public void setAlienX(double otherX)
	{
		this.x = otherX;
	}
	public void setAlienY(double otherY)
	{
		this.y = otherY;
	}
	public void killAlien()
	{
		this.isAlive = false;
	}
	public void reviveAlien()
	{
		this.isAlive = true;
	}
}

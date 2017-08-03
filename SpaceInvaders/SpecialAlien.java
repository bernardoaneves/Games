// mothership
public class SpecialAlien
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
	public SpecialAlien(double givenX, double givenY, double givenRadius)
	{
		this.x = givenX;
		this.y = givenY;
		this.radius = givenRadius;
		this.isFlying = false;
	}

	// GETTERS
	public double getSpecialAlienX()
	{
		return this.x;
	}
	public double getSpecilAlienY()
	{
		return this.y;
	}
	public double getSpecialAlienRadius()
	{
		return this.radius;
	}
	public boolean isSpecialAlienFlying()
	{
		return this.isFlying;
	}

	// SETTERS
	public void setSpecialAlienX(double newX)
	{
		this.x = newX;
	}
	public void activateSpecialAlien()
	{
		this.isFlying = true;
	}
	public void deactivateSpecialAlien()
	{
		this.isFlying = false;
	}
}

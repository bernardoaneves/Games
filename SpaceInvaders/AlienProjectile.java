// alien projectile
public class AlienProjectile
{
	// INSTANCE VARIABLES
	//    position:
	private double x;
	private double y;
	//    size
	private final double radius;
	//    isFlying:
	private boolean isFlying;

	// CONSTRUCTOR
	public AlienProjectile(double givenX, double givenY, double givenRadius)
	{
		this.x = givenX;
		this.y = givenY;
		this.radius = givenRadius;
		this.isFlying = false;
	}

	// GETTERS
	public double getAlienProjectileX()
	{
		return this.x;
	}
	public double getAlienProjectileY()
	{
		return this.y;
	}
	public boolean isAlienProjectileFlying()
	{
		return this.isFlying;
	}
	public double getAlienProjectileRadius()
	{
		return this.radius;
	}

	// SETTERS
	public void setAlienProjectileX(double newX)
	{
		this.x = newX;
	}
	public void setAlienProjectileY(double newY)
	{
		this.y = newY;
	}
	public void shootAlienProjectile()
	{
		this.isFlying = true;
	}
	public void alienProjectileHit()
	{
		this.isFlying = false;
	}
}

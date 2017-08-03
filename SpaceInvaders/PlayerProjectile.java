// player projectile
public class PlayerProjectile
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
	public PlayerProjectile(double givenX, double givenY, double givenRadius)
	{
		this.x = givenX;
		this.y = givenY;
		this.radius = givenRadius;
		this.isFlying = false;
	}

	// GETTERS
	public double getPlayerProjectileX()
	{
		return this.x;
	}
	public double getPlayerProjectileY()
	{
		return this.y;
	}
	public double getPlayerProjectileRadius()
	{
		return this.radius;
	}
	public boolean isPlayerProjectileFlying()
	{
		return this.isFlying;
	}

	// SETTERS
	public void playerProjectileHit()
	{
		this.isFlying = false;
	}
	public void shootPlayerProjectile()
	{
		this.isFlying = true;
	}
	public void setPlayerProjectileX(double newX)
	{
		this.x = newX;
	}
	public void setPlayerProjectileY(double newY)
	{
		this.y = newY;
	}
}

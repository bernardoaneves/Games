// defenses
public class Defense
{
	// INSTANCE VARIABLES
	//    position:
	private final double x;
	private final double y;
	//    lives:
	private boolean isAlive;
	private int numLives;
	//    size:
	private final double radius;

	// CONSTRUCTOR
	public Defense(double givenX, double givenY, double givenRadius, int givenLives)
	{
		this.x = givenX;
		this.y = givenY;
		this.radius = givenRadius;
		this.isAlive = true;
		this.numLives = givenLives;
	}

	// GETTERS
	public double getDefenseX()
	{
		return this.x;
	}
	public double getDefenseY()
	{
		return this.y;
	}
	public double getDefenseRadius()
	{
		return this.radius;
	}
	public boolean isDefenseAlive()
	{
		if (this.numLives <= 0)
			return false;
		else
			return true;
	}
	public int getDefenseLives()
	{
		return this.numLives;
	}

	// SETTERS
	public void killDefense()
	{
		this.isAlive = false;
	}
	public void defenseLostALife()
	{
		this.numLives--;
	}
}

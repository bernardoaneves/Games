// defines the player object
public class Player
{
	// INSTANCE VARIABLES
	//    position:
	private double x;
	private final double y;
	private double speed;
	//    lives:
	private int numLives;
	//    size:
	private final double radius;
	//    score:
	private int score;

	// CONSTRUCTOR
	public Player(double givenRadius, int numLives, double givenSpeed)
	{
		this.speed = givenSpeed;
		this.radius = givenRadius;
		this.x = 0.5;
		this.y = -0.03;
		this.numLives = numLives;
		this.score = 0;
	}

	// GETTERS
	public double getPlayerSpeed()
	{
		return this.speed;
	}
	public int getPlayerScore()
	{
		return this.score;
	}
	public double getPlayerX()
	{
		return this.x;
	}
	public double getPlayerY()
	{
		return this.y;
	}
	public boolean isPlayerAlive()
	{
		if (this.numLives == 0)
			return false;

		return true;
	}
	public int getNumLivesPlayer()
	{
		return this.numLives;
	}
	public double getPlayerRadius()
	{
		return this.radius;
	}

	// SETTERS
	public void setPlayerSpeed(double newSpeed)
	{
		this.speed = newSpeed;
	}
	public void setPlayerX(double newX)
	{
		this.x = newX;
	}
	public void loseALife()
	{
		this.numLives--;
	}
	public void winALife()
	{
		this.numLives++;
	}
	public void addPoints(int points)
	{
		this.score += points;
	}
}

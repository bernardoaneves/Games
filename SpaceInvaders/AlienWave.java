// alien wave
public class AlienWave
{
	// INSTANCE VARIABLES
	//    array of aliens:
	private Alien[][] alienWave;
	//    boundaries:
	private boolean movingToTheRight;
	private boolean isTooLow;

	// CONSTRUCTOR
	public AlienWave(Alien[][] aliens)
	{
		this.isTooLow = false;
		this.movingToTheRight = true;
		this.alienWave = aliens;
	}

	// GETTERS
	public boolean isAlienWaveTooLow()
	{
		return this.isTooLow;
	}
	public boolean isWaveMovingRight()
	{
		return this.movingToTheRight;
	}
	public Alien[][] getAliens()
	{
		return this.alienWave;
	}
	public int getLeftMostAlien()
	{
		for (int i = 0; i < 6; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				if (this.alienWave[i][j].isAlienAlive())
					return i;
			}
		}
		return 0;
	}
	public int getRightMostAlien()
	{
		for (int i = 5; i >= 0; i--)
		{
			for (int j = 0; j < 5; j++)
			{
				if (this.alienWave[i][j].isAlienAlive())
					return i;
			}
		}
		return 5;
	}
	public int getSouthernMostAlien()
	{
		for (int i = 4; i >= 0; i--)
		{
			for (int j = 0; j < 6; j++)
			{
				if(this.alienWave[j][i].isAlienAlive())
					return i;
			}
		}
		return 0;
	}
	public int getNumAliens()
	{
		int counter = 0;
		for (int i = 0; i < 6; i++)
		{
			for (int j = 0; j < 5;j++)
			{
				if(this.alienWave[i][j].isAlienAlive())
					counter++;
			}
		}
		return counter;
	}
	public boolean isWaveAlive()
	{
		for(int i = 0; i < 6; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				if(this.alienWave[i][j].isAlienAlive())
					return true;
			}
		}
		return false;
	}

	// SETTERS
	public void changeWaveDrirection()
	{
		this.movingToTheRight = !this.movingToTheRight;
	}
	public void AlienWaveIsTooLow()
	{
		this.isTooLow = true;
	}
}

// THE GAME
public class SpaceInvaders 
{
	public static void main(String[] args)
	{
		while(true){
		// create a start screen, with a play button
		begin();
		StdDraw.pause(250);
		// the player can select an easy, medium or hard mode
		int level = selectLevel();
		StdDraw.pause(250);
		clear();
		// the player will have less lives on harder levels
		int playerLives = 0;
		if(level == 1) playerLives = 5;
		else if(level == 2) playerLives = 3;
		else if(level == 3) playerLives = 1;
		betweenRoundsPause(1);

		// create an array of aliens
		Alien aliens[][] = createAliens();
		// use this array to create a wave of aliens
		AlienWave wave = createWaveOfAliens(aliens);
		// draw the wave
		drawWave(wave);
		// create a player
		Player player = new Player(0.07, playerLives, 0.015);
		// create a player projectile
		PlayerProjectile playerShot = createPlayerProjectile(player);
		// create an alien projectile
		AlienProjectile alienShot = createAlienProjectile(wave);
		// create 3 defenses
		Defense[] defense = createDefenses(level);
		// create a special alien
		SpecialAlien specialAlien = new SpecialAlien(1.2, 1.0, 0.07);
		// create a Life (PowerUp)
		Life life = new Life(1.2, 1.0);
		// create a money (PowerUp)
		Money money = new Money(1.2, 1.0);
		// create a speed booster
		Speed speed = new Speed(1.2, 1.0);
		// variable for the alien shots and the special alien
		int timer = 1;
		// variable to keep track of how many waves the player has alreay defeated
		int round = 1;
		// booleans to know if the player has won or lost
		boolean lost = false;

		// the player needs to clear 4 rounds
		while(true)
		{
			// play the game
			while(true)
			{
				// move the wave of aliens
				moveWave(wave);
				// check to see if the alien wave is too low
				if(wave.isAlienWaveTooLow())
				{
					// wave is too low
					lost = true;
					break;
				}

				// move the player
				movePlayer(player);

				// check if it is time to activate the special alien
				checkSpecialAlien(specialAlien, timer);
				// move the special alien
				moveSpecialAlien(specialAlien);
				// move the power ups
				movePowerUp(specialAlien, life, money, speed);
				// check if the power up was caught or lost
				checkPowerUpHit(life, money, speed, player);

				// check if the player has shot
				checkPlayerShot(playerShot);
				// move the player projectile
				movePlayerProjectile(playerShot, player);
				// check to see if the player projectile has hit anything
				checkPlayerProjectileHit(wave, playerShot, defense, player, specialAlien, life, money, speed);
				// check if the alien wave is dead
				if(!wave.isWaveAlive())
				{
					break;
				}

				// check if its time for the aliens to shoot
				checkAlienShot(alienShot, timer, level);
				// move the alien projectile around, shooting it every 200 iterations
				moveAlienProjectile(alienShot, wave, level);
				// check to see if the player projectile has hit anything
				checkAlienProjectileHit(alienShot, defense, player, playerShot);
				// check if the player is dead
				if(!player.isPlayerAlive())
				{
					// the player is dead
					lost = true;
					break;
				}

				// draw everything
				drawEverything(wave, player, defense, playerShot, alienShot, round, specialAlien, life, money, speed);

				// increase this variable
				timer++;
			}
			// only move to the next round if the player is alive and the
			// wave has been cleared
			if (!lost)
			{
				// move to the next round
				round++;
				if (round > 5)
					break;
				// dramatic pause
				betweenRoundsPause(round);
				// reset the wave
				reset(wave);
			}
			// if one of these conditions is not met, game over
			else
				break;
		}
		// Lost :(
		if(lost)
		{
			Lost(player);
		}
		// Won :)
		else
		{
			Won(player);
		}}
	}

	/**
	 * checks if all the powerUps have been caught or lost
	 * @param life
	 * @param money
	 * @param p
	 */
	public static void checkPowerUpHit(Life life, Money money, Speed speed, Player p)
	{
		// check the life
		checkLifeHit(life, p);

		// check the money
		checkMoneyHit(money, p);

		// check the speed
		checkSpeedHit(speed, p);
	}

	/**
	 * moves all the power ups
	 * @param specialAlien
	 * @param life
	 * @param money
	 */
	public static void movePowerUp(SpecialAlien specialAlien, Life life, Money money, Speed speed)
	{
		// move the life
		moveLife(specialAlien, life);

		// move the money
		moveMoney(specialAlien, money);

		// move the speed
		moveSpeed(specialAlien, speed);
	}

	/**
	 * moves the speed (PowerUp). If it is not flying, move it with the special alien.
	 * If it is flying, make it fall slowly
	 * @param specialAlien
	 * @param speed
	 */
	public static void moveSpeed(SpecialAlien specialAlien, Speed speed)
	{
		// if the life is not flying, keep it with the special alien
		if(!speed.isSpeedFlying())
		{
			speed.setSpeedX(specialAlien.getSpecialAlienX());
			speed.setSpeedY(specialAlien.getSpecilAlienY());
		}
		// if it is flying, it will fall with a speed of 0.04
		else
		{
			double y = speed.getSpeedY();
			double newY = y - 0.005;
			speed.setSpeedY(newY);
		}
	}

	/**
	 * moves the money (PowerUp). If it is not flying, move it with the special alien.
	 * If it is flying, make it fall slowly
	 * @param specialAlien
	 * @param money
	 */
	public static void moveMoney(SpecialAlien specialAlien, Money money)
	{
		// if the life is not flying, keep it with the special alien
		if(!money.isMoneyFlying())
		{
			money.setMoneyX(specialAlien.getSpecialAlienX());
			money.setMoneyY(specialAlien.getSpecilAlienY());
		}
		// if it is flying, it will fall with a speed of 0.04
		else
		{
			double y = money.getMoneyY();
			double newY = y - 0.005;
			money.setMoneyY(newY);
		}
	}

	/**
	 * checks if the player has caught the life and if the life has hit the ground.
	 * takes the necessary precautions
	 * @param life
	 * @param p
	 */
	public static void checkLifeHit(Life life, Player p)
	{
		if(checkPlayerCaughtLife(life, p))
		{
			// he caught the life
			p.winALife();
			// the life is not flying anymore
			life.makeLifeStopFlying();
		}
		else if(checkLifeHitGround(life))
		{
			// the life is not flying anymore
			life.makeLifeStopFlying();
		}
	}

	/**
	 * checks if the player has caught the speed and if the speed has hit the ground.
	 * takes the necessary precautions
	 * @param speed
	 * @param p
	 */
	public static void checkSpeedHit(Speed speed, Player p)
	{
		if(checkPlayerCaughtSpeed(speed, p))
		{
			// he gets faster
			p.setPlayerSpeed(p.getPlayerSpeed() + 0.003);
			// the speed is not flying anymore
			speed.makeSpeedStopFlying();
		}
		else if(checkSpeedHitGround(speed))
		{
			// the speed is not flying anymore
			speed.makeSpeedStopFlying();
		}
	}

	/**
	 * checks if the player has caught the money and if the money has hit the ground.
	 * takes the necessary precautions
	 * @param money
	 * @param p
	 */
	public static void checkMoneyHit(Money money, Player p)
	{
		if(checkPlayerCaughtMoney(money, p))
		{
			// he wins points
			p.addPoints(100);
			// the money is not flying anymore
			money.makeMoneyStopFlying();
		}
		else if(checkMoneyHitGround(money))
		{
			// the life is not flying anymore
			money.makeMoneyStopFlying();
		}
	}

	/**
	 * checks if the life has hit the ground
	 * @param life
	 * @return
	 */
	public static boolean checkLifeHitGround(Life life)
	{
		// get positions
		double y = life.getLifeY();
		double radius = life.getLifeRadius();
		if (y + radius < 0)
			return true;

		// hasnt gone away yet
		return false;
	}

	/**
	 * checks if the speed has hit the ground
	 * @param speed
	 * @return
	 */
	public static boolean checkSpeedHitGround(Speed speed)
	{
		// get positions
		double y = speed.getSpeedY();
		double radius = speed.getSpeedRadius();
		if (y + radius < 0)
			return true;

		// hasnt gone away yet
		return false;
	}

	/**
	 * checks if the money has hit the ground
	 * @param life
	 * @return
	 */
	public static boolean checkMoneyHitGround(Money money)
	{
		// get positions
		double y = money.getMoneyY();
		double radius = money.getMoneyRadius();
		if (y + radius < 0)
			return true;

		// hasnt gone away yet
		return false;
	}

	/**
	 * checks if the player has caught the life. makes the player faster if this has happened
	 * @param life
	 * @param p
	 */
	public static boolean checkPlayerCaughtSpeed(Speed speed, Player p)
	{
		// get positions
		double speedX = speed.getSpeedX();
		double speedY = speed.getSpeedY();
		double speedRadius = speed.getSpeedRadius();

		double playerX = p.getPlayerX();
		double playerY = p.getPlayerY();
		double playerRadius = p.getPlayerRadius();

		// check if the y position is ok
		if ((speedY - speedRadius <= playerY + playerRadius/1.5) && (speedY + speedRadius >= playerY - playerRadius))
		{
			// check if the x position is ok
			if ((speedX + speedRadius >= playerX - playerRadius) && (speedX - speedRadius <= playerX + playerRadius))
			{
				// got a hit
				return true;
			}
		}

		// didnt catch it
		return false;
	}

	/**
	 * checks if the player has caught the speed. adds a life to the plpayer if this has happened
	 * @param life
	 * @param p
	 */
	public static boolean checkPlayerCaughtLife(Life life, Player p)
	{
		// get positions
		double lifeX = life.getLifeX();
		double lifeY = life.getLifeY();
		double lifeRadius = life.getLifeRadius();

		double playerX = p.getPlayerX();
		double playerY = p.getPlayerY();
		double playerRadius = p.getPlayerRadius();

		// check if the y position is ok
		if ((lifeY - lifeRadius <= playerY + playerRadius/1.5) && (lifeY + lifeRadius >= playerY - playerRadius))
		{
			// check if the x position is ok
			if ((lifeX + lifeRadius >= playerX - playerRadius) && (lifeX - lifeRadius <= playerX + playerRadius))
			{
				// got a hit
				return true;
			}
		}

		// didnt catch it
		return false;
	}

	/**
	 * checks if the player has caught the money. adds points to the plpayer if this has happened
	 * @param money
	 * @param p
	 */
	public static boolean checkPlayerCaughtMoney(Money money, Player p)
	{
		// get positions
		double moneyX = money.getMoneyX();
		double moneyY = money.getMoneyY();
		double moneyRadius = money.getMoneyRadius();

		double playerX = p.getPlayerX();
		double playerY = p.getPlayerY();
		double playerRadius = p.getPlayerRadius();

		// check if the y position is ok
		if ((moneyY - moneyRadius <= playerY + playerRadius/1.5) && (moneyY + moneyRadius >= playerY - playerRadius))
		{
			// check if the x position is ok
			if ((moneyX + moneyRadius >= playerX - playerRadius) && (moneyX - moneyRadius <= playerX + playerRadius))
			{
				// got a hit
				return true;
			}
		}

		// didnt catch it
		return false;
	}

	/**
	 * randomly activates on of the power ups, when the special alien is hit
	 * @param life
	 */
	public static void makePowerUpFly(Life life, Money money, Speed speed)
	{
		//get a random number
		double x = Math.random();
		// activate a power up randomly
		if (x <= 1.0/3.0)
			life.makeLifeFly();
		else if((x > 1.0/3.0) && (x <= 2.0/3.0))
			money.makeMoneyFly();
		else
			speed.makeSpeedFly();

	}

	/**
	 * moves the life (PowerUp). If it is not flying, move it with the special alien.
	 * If it is flying, make it fall slowly
	 * @param specialAlien
	 * @param life
	 */
	public static void moveLife(SpecialAlien specialAlien, Life life)
	{
		// if the life is not flying, keep it with the special alien
		if(!life.isLifeFlying())
		{
			life.setLifeX(specialAlien.getSpecialAlienX());
			life.setLifeY(specialAlien.getSpecilAlienY());
		}
		// if it is flying, it will fall with a speed of 0.04
		else
		{
			double y = life.getLifeY();
			double newY = y - 0.005;
			life.setLifeY(newY);
		}
	}

	/**
	 * activates the special alien every 650 iterartions
	 * @param specialAlien
	 * @param round
	 */
	public static void checkSpecialAlien(SpecialAlien specialAlien, int round)
	{
		// activate the special alien every 650 iterations
		if(round % 650 == 0)
			specialAlien.activateSpecialAlien();
	}

	/**
	 * moves the special alien, only if it is flying
	 * @param specialAlien
	 */
	public static void moveSpecialAlien(SpecialAlien specialAlien)
	{
		// only move the alien if it is flying
		if(!specialAlien.isSpecialAlienFlying())
			return;

		// if it is flying:
		// check if it is already too much to the left
		if(specialAlien.getSpecialAlienX() <= -0.1)
		{
			// if yes, make it not fly anymore, and reset it to its starting position
			specialAlien.setSpecialAlienX(1.2);
			specialAlien.deactivateSpecialAlien();
		}
		else
		{
			// move it to the left
			double x = specialAlien.getSpecialAlienX();
			double newX = x - 0.0055;
			specialAlien.setSpecialAlienX(newX);
		}
	}

	/**
	 * returns the level the user selected
	 * @return
	 */
	public static int selectLevel()
	{
		// clear the screen
		clear();
		StdDraw.setPenColor(Color.white);
		StdDraw.text(0.5, 0.8, "Select Difficulty");
		// draw the level buttons
		StdDraw.setPenColor(Color.green);
		StdDraw.filledRectangle(0.2, 0.5, 0.12, 0.09);
		StdDraw.filledRectangle(0.5, 0.5, 0.12, 0.09);
		StdDraw.filledRectangle(0.8, 0.5, 0.12, 0.09);
		// enter the text
		StdDraw.setPenColor(Color.black);
		StdDraw.text(0.2, 0.5, "easy");
		StdDraw.text(0.5, 0.5, "medium");
		StdDraw.text(0.8, 0.5, "hard");
		StdDraw.show(10);
		// see where the user clicked

		// will get the mouse position
		double[] mousePosition = new double[2];

		while(!StdDraw.mousePressed())
		{
			if (StdDraw.mousePressed())
			{
				while (StdDraw.mousePressed())
				{
					// wait
					StdDraw.pause(15);
				}

				// here, the mouse has been released
				break;
			}
			// wait
			StdDraw.pause(15);
		}

		// get mouse coordinates
		mousePosition[0] = StdDraw.mouseX();
		mousePosition[1] = StdDraw.mouseY();

		// ;)
		int level = checkMousePositionLevel(mousePosition);
		return level;
	}

	/**
	 * checks which level the player selected
	 * @param mousePosition
	 * @return
	 */
	public static int checkMousePositionLevel(double[] mousePosition)
	{
		// check that the Y position works
		if(!(mousePosition[1] >= 0.41 && mousePosition[1] <= 0.59))
			selectLevel();

		// check the X position
		if((mousePosition[0] >= 0.08) && (mousePosition[0] <= 0.32))
			return 1;
		else if((mousePosition[0] >= 0.38) && (mousePosition[0] <= 0.62))
			return 2;
		else if((mousePosition[0] >= 0.68) && (mousePosition[0] <= 0.92))
			return 3;

		// he didnt click any of the buttons
		return selectLevel();
	}

	/**
	 * Player Won
	 */
	public static void Won(Player p)
	{
		// clear the screen
		clear();
		StdDraw.setPenColor(Color.white);
		// write
		StdDraw.text(0.5, 0.5, "Congratulations");
		StdDraw.text(0.5, 0.42, "You saved the Galaxy");
		// write the score
		String s = "Your score: " + p.getPlayerScore();
		StdDraw.text(0.5, 0.34, s);
		StdDraw.show(5000);
	}

	/**
	 * Player Lost
	 */
	public static void Lost(Player p)
	{
		// clear the screen
		clear();
		StdDraw.setPenColor(Color.white);
		// write
		StdDraw.text(0.5, 0.5, "Game Over");
		// write the score
		String s = "Your score: " + p.getPlayerScore();
		StdDraw.text(0.5, 0.42, s);
		StdDraw.show(5000);
	}

	/**
	 * just does a 5 second countdown between rounds
	 * @param numRound
	 */
	public static void betweenRoundsPause(int numRound)
	{
		// what will be written
		String s = "Round " + numRound;
		// clear the screen
		clear();
		StdDraw.setPenColor(Color.white);
		// write it, with a dramatic countdown
		StdDraw.text(0.5, 0.5, s);
		StdDraw.text(0.5, 0.42, "5");
		StdDraw.show(1000);
		clear();
		StdDraw.setPenColor(Color.white);
		StdDraw.text(0.5, 0.5, s);
		StdDraw.text(0.5, 0.42, "4");
		StdDraw.show(1000);
		clear();
		StdDraw.setPenColor(Color.white);
		StdDraw.text(0.5, 0.5, s);
		StdDraw.text(0.5, 0.42, "3");
		StdDraw.show(1000);
		clear();
		StdDraw.setPenColor(Color.white);
		StdDraw.text(0.5, 0.5, s);
		StdDraw.text(0.5, 0.42, "2");
		StdDraw.show(1000);
		clear();
		StdDraw.setPenColor(Color.white);
		StdDraw.text(0.5, 0.5, s);
		StdDraw.text(0.5, 0.42, "1");
		StdDraw.show(1000);
	}

	/**
	 * resets the wave of aliens: makes all the aliens alive and
	 * resets them to the middle of the screen
	 * @param waveOfAliens
	 */
	public static void reset(AlienWave waveOfAliens)
	{
		// reseting means making all the aliens alive again
		for (int i = 0; i < 6; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				waveOfAliens.getAliens()[i][j].reviveAlien();
				waveOfAliens.getAliens()[i][j].setAlienX(0.25 + i*0.1);
			}
		}
	}

	/**
	 * checks if the alien shot has hit anything:
	 * the player, the defenses or the ground
	 * @param aProjectile
	 * @param arrayOfDefenses
	 * @param p
	 */
	public static void checkAlienProjectileHit(AlienProjectile aProjectile, Defense[] arrayOfDefenses, Player p, PlayerProjectile pProjectile)
	{
		// check to see if the alien projectile has hit a defense
		if(hasAlienProjectileHitADefense(aProjectile, arrayOfDefenses))
		{
			// if it has hit a defense, it is not flying anymore
			aProjectile.alienProjectileHit();
		}
		// check to see if the alien projectile has hit the player
		else if(hasAlienProjectileHitThePlayer(aProjectile, p, pProjectile))
		{
			// if it has hit the player, it is not flying anymore
			aProjectile.alienProjectileHit();
		}
		// check to see if the alien Projectile has hit the ground
		else if(hasAlienProjectileGoneAway(aProjectile))
		{
			// if it has, it is not flying anymore
			aProjectile.alienProjectileHit();
		}
	}

	/**
	 * checks if the alien shot has hit the ground
	 * @param aProjectile
	 * @return true if it has
	 */
	public static boolean hasAlienProjectileGoneAway(AlienProjectile aProjectile)
	{
		// get the projectile's y position
		double y = aProjectile.getAlienProjectileY();
		if (y <= -0.1)
			return true;

		// it hasn't hit anything
		return false;
	}

	/**
	 * checks if the alien shot has hit the player
	 * @param aProjectile
	 * @param p
	 * @return
	 */
	public static boolean hasAlienProjectileHitThePlayer(AlienProjectile aProjectile, Player p, PlayerProjectile pProjectile)
	{
		// get the projectile's X and Y positions
		double projectileX = aProjectile.getAlienProjectileX();
		double projectileY = aProjectile.getAlienProjectileY();
		// get the player's X and Y positions
		double playerX = p.getPlayerX();
		double playerY = p.getPlayerY();
		// player radius
		double playerRadius = p.getPlayerRadius();
		// get the limits
		double upLimit = playerY + playerRadius;
		double downLimit = playerY;
		double rightLimit = playerX + playerRadius;
		double leftLimit = playerX - playerRadius;

		// check if the y positions coincide
		if ((projectileY <= upLimit) && (projectileY >= downLimit))
		{
			// check if the x positions coincide
			if ((projectileX <= rightLimit) && (projectileX >= leftLimit))
			{
				// man down
				// take a life off the player
				p.loseALife();
				// reset the player position
				p.setPlayerX(0.5);
				pProjectile.setPlayerProjectileX(0.5);
				return true;
			}
		}

		// it didn't hit the player
		return false;
	}

	/**
	 * checks if the projectile shot by the alien has hit a defense.
	 * takes a life out of the defense if it has
	 * @param aProjectile
	 * @param arrayOfDefenses
	 * @return true if a defense was hit
	 */
	public static boolean hasAlienProjectileHitADefense(AlienProjectile aProjectile, Defense[] arrayOfDefenses)
	{
		// get the defense's Y position
		double defenseY = arrayOfDefenses[1].getDefenseY();
		// get the projectile's X and Y position
		double projectileX = aProjectile.getAlienProjectileX();
		double projectileY = aProjectile.getAlienProjectileY();
		// get the defense's vertical boundaries
		double radius = arrayOfDefenses[1].getDefenseRadius();
		double upLimit = defenseY + (radius/8.0);
		double downLimit = defenseY - (radius/8.0);

		// iterate over each defense
		for (int i = 0; i < 3; i++)
		{
			// make sure the defense is alive
			if(!arrayOfDefenses[i].isDefenseAlive())
				continue;
			// get the defense's horizontal boundaries
			double rightLimit = arrayOfDefenses[i].getDefenseX() + radius;
			double leftLimit = arrayOfDefenses[i].getDefenseX() - radius;
			// check if the Y positions coincide
			if ((projectileY >= downLimit) && (projectileY <= upLimit))
			{
				// check if the X positions coincide
				if ((projectileX >= leftLimit) && (projectileX <= rightLimit))
				{
					// the defense got hit :(
					// take a life
					arrayOfDefenses[i].defenseLostALife();
					return true;
				}
			}
		}

		// it didn't hit anything
		return false;
	}

	/**
	 * checks if it is time for the aliens to shoot. if
	 * yes, sets aProjectile.isFlying to true
	 * @param aProjectile
	 * @param count
	 */
	public static void checkAlienShot(AlienProjectile aProjectile, int count, int level)
	{
		int frequency = 0;
		// the players will shoot more frequently for harder levels
		if(level == 1)
			frequency = 150;
		else if(level == 2)
			frequency = 100;
		else if(level == 3)
			frequency = 50;
		// the aliens will only shoot every 90 iterations
		if(count % frequency == 0)
		{
			// its time for the aliens to shoot
			aProjectile.shootAlienProjectile();
		}
	}

	/**
	 * creates an alien projectile
	 * @param waveOfAliens
	 * @return an alien projectile
	 */
	public static AlienProjectile createAlienProjectile(AlienWave waveOfAliens)
	{
		// the alien projectile will be initiallized for the alien [2][0]
		double x = waveOfAliens.getAliens()[2][0].getAlienX();
		double y = waveOfAliens.getAliens()[2][0].getAlienY();
		AlienProjectile aProjectile = new AlienProjectile(x, y, 0.008);
		// return it
		return aProjectile;
	}

	/**
	 * moves the alien projectile:
	 * if it hasn't been shot, the projectile is given to the first live alien
	 * found on the array.
	 * if it has been shot, it is moved vertically
	 * @param aProjectile
	 * @param waveOfAliens
	 */
	public static void moveAlienProjectile(AlienProjectile aProjectile, AlienWave waveOfAliens, int level)
	{
		// if the projectile has not yet been shot
		if (!aProjectile.isAlienProjectileFlying())
		{
			// try some random aliens
			int randomCol = (int)(Math.random()*5);
			int randomRow = (int)(Math.random()*4);
			// check if this alien is alive
			if(waveOfAliens.getAliens()[randomCol][randomRow].isAlienAlive())
			{
				// if yes it will get the projectile
				// get the position of the alien
				double x = waveOfAliens.getAliens()[randomCol][randomRow].getAlienX();
				double y = waveOfAliens.getAliens()[randomCol][randomRow].getAlienY();
				// move the projectile
				// move the projectile
				aProjectile.setAlienProjectileX(x);
				aProjectile.setAlienProjectileY(y);
			}
			// if its not alive, try the one to the right
			else if (waveOfAliens.getAliens()[randomCol+1][randomRow].isAlienAlive())
			{
				// if yes it will get the projectile
				// get the position of the alien
				double x = waveOfAliens.getAliens()[randomCol+1][randomRow].getAlienX();
				double y = waveOfAliens.getAliens()[randomCol+1][randomRow].getAlienY();
				// move the projectile
				// move the projectile
				aProjectile.setAlienProjectileX(x);
				aProjectile.setAlienProjectileY(y);
			}
			// it its not alive, try the one down
			else if(waveOfAliens.getAliens()[randomCol][randomRow+1].isAlienAlive())
			{
				// if yes it will get the projectile
				// get the position of the alien
				double x = waveOfAliens.getAliens()[randomCol][randomRow+1].getAlienX();
				double y = waveOfAliens.getAliens()[randomCol][randomRow+1].getAlienY();
				// move the projectile
				// move the projectile
				aProjectile.setAlienProjectileX(x);
				aProjectile.setAlienProjectileY(y);
			}
			// if none are alive, the first alive alien will get the projectile
			else
			{
				// variables for the projectile position
				double x = 0;
				double y = 0;
				// look for the first alive alien, and hand it the projectile
				for (int i = 0; i < 6; i++)
				{
					for (int j = 0; j < 5; j++)
					{
						// found an alive alien
						if(waveOfAliens.getAliens()[i][j].isAlienAlive())
						{
							// get its position
							x = waveOfAliens.getAliens()[i][j].getAlienX();
							y = waveOfAliens.getAliens()[i][j].getAlienY();
							break;
						}
					}
				}

				// move the projectile
				aProjectile.setAlienProjectileX(x);
				aProjectile.setAlienProjectileY(y);
			}
		}
		// if it has been shot
		else if(aProjectile.isAlienProjectileFlying())
		{
			double speed = 0.01;
			// it will fly faster on harder levels
			if(level == 1)
				speed = 0.02;
			else if(level == 2)
				speed = 0.025;
			else if(level == 3)
				speed = 0.03;
			double y = aProjectile.getAlienProjectileY();
			double newY = y - speed;
			aProjectile.setAlienProjectileY(newY);
		}
	}

	/**
	 * checks if the player projectile has hit a defense, or an alien, or has gone away
	 * @param waveOfAliens
	 * @param pProjectile
	 * @param arrayOfDefenses
	 * @param p
	 * @param specialAlien
	 */
	public static void checkPlayerProjectileHit(AlienWave waveOfAliens, PlayerProjectile pProjectile, Defense[] arrayOfDefenses, Player p, SpecialAlien specialAlien, Life life, Money money, Speed speed)
	{
		double radius = p.getPlayerRadius();
		// check to see if it has hit any of defenses
		if(hasPlayerProjectileHitADefense(pProjectile, arrayOfDefenses))
		{
			// if it has hit a defense:
			// reset the projectile to the player
			pProjectile.setPlayerProjectileX(p.getPlayerX());
			pProjectile.setPlayerProjectileY(p.getPlayerY() + radius);
			// it is not flying anymore
			pProjectile.playerProjectileHit();
		}
		// check to see if it has hit any of the aliens
		else if(hasPlayerProjectileHitAnAlien(pProjectile, waveOfAliens))
		{
			// if the projectile has hit an alien:
			// reset the projectile to the player
			pProjectile.setPlayerProjectileX(p.getPlayerX());
			pProjectile.setPlayerProjectileY(p.getPlayerY() + radius);
			// it is not flying anymore
			pProjectile.playerProjectileHit();
			// the player gets 10 points
			p.addPoints(10);
		}
		// check to see if it has hit the special alien
		else if(hasPlayerProjectileHitSpecialAlien(pProjectile, specialAlien))
		{
			// if the projectile has hit the special alien
			// reset the projectile to the player
			pProjectile.setPlayerProjectileX(p.getPlayerX());
			pProjectile.setPlayerProjectileY(p.getPlayerY() + radius);
			// it is not flying anymore
			pProjectile.playerProjectileHit();
			// the player gets 70 points
			p.addPoints(70);
			// a power up starts to fall
			makePowerUpFly(life, money, speed);

		}
		// check to see if it has gone away
		else if(hasPlayerProjectileGoneAway(pProjectile))
		{
			// if it has gone away:
			// reset the projectile to the player
			pProjectile.setPlayerProjectileX(p.getPlayerX());
			pProjectile.setPlayerProjectileY(p.getPlayerY() + radius);
			// it is not flying anymore
			pProjectile.playerProjectileHit();
		}
	}

	/**
	 * checks if the player shot has hit the special alien
	 * @param pProjectile
	 * @param specialAlien
	 * @return
	 */
	public static boolean hasPlayerProjectileHitSpecialAlien(PlayerProjectile pProjectile, SpecialAlien specialAlien)
	{
		// get the y positions
		double projectileY = pProjectile.getPlayerProjectileY();
		double alienY = specialAlien.getSpecilAlienY();
		double alienRadius = specialAlien.getSpecialAlienRadius();
		// check if the y positions match
		if (projectileY < (alienY - alienRadius/8))
			return false;

		// get the x positions
		double projectileX = pProjectile.getPlayerProjectileX();
		double alienX = specialAlien.getSpecialAlienX();
		// check if the x positions match
		if((projectileX >= (alienX - alienRadius)) && (projectileX <= (alienX + alienRadius)))
		{
			// we got a hit
			// reset the special alien to special position
			specialAlien.setSpecialAlienX(1.2);
			// it is not flying anymore
			specialAlien.deactivateSpecialAlien();
			return true;
		}

		// no hit
		return false;
	}

	/**
	 * checks if the player projectile has reached the ceiling
	 * @param pProjectile
	 * @return true if it has
	 */
	public static boolean hasPlayerProjectileGoneAway(PlayerProjectile pProjectile)
	{
		// get the projectile position
		double y = pProjectile.getPlayerProjectileY();
		double radius = pProjectile.getPlayerProjectileRadius();
		// if it has gone too far
		if (y - radius >= 1.1)
			return true;

		// it is still in play
		return false;
	}

	/**
	 * checks if the playerProjectile has hit an alien, and kills the alien
	 * @param pProjectile
	 * @param waveOfAliens
	 * @return true if the projectile has hit the alien
	 */
	public static boolean hasPlayerProjectileHitAnAlien(PlayerProjectile pProjectile, AlienWave waveOfAliens)
	{
		// get the position of the projectile
		double projectileX = pProjectile.getPlayerProjectileX();
		double projectileY = pProjectile.getPlayerProjectileY();
		double projectileRadius = pProjectile.getPlayerProjectileRadius();
		// get the alien radius
		double alienRadius = waveOfAliens.getAliens()[0][0].getAlienRadius();

		// iterate over the entire array of aliens, checking if the
		// projectile has hit an alien, making sure the alien is alive
		for (int i = 0; i < 6; i++)
		{
			for (int j = 4; j >= 0; j--)
			{
				// if the alien is alive
				if(waveOfAliens.getAliens()[i][j].isAlienAlive())
				{
					// get the position of the alien
					double alienX = waveOfAliens.getAliens()[i][j].getAlienX();
					double alienY = waveOfAliens.getAliens()[i][j].getAlienY();
					// check to see if the projectile has the right x position
					if ((projectileX + projectileRadius >= alienX - alienRadius) && (projectileX - projectileRadius <= alienX + alienRadius))
					{
						// check to see if the projectile has the right y position
						if ((projectileY + projectileRadius >= alienY - alienRadius) && (projectileY - projectileRadius <= alienY + alienRadius))
						{
							// we got a hit!
							// kill the alien
							waveOfAliens.getAliens()[i][j].killAlien();
							return true;
						}
					}
				}
			}
		}

		// it didn't hit anything
		return false;
	}

	/**
	 * checks if the player projectile has hit one of his own defenses
	 * @param pProjectile
	 * @param arrayOfDefenses
	 * @return true if it has hit a defense
	 */
	public static boolean hasPlayerProjectileHitADefense(PlayerProjectile pProjectile, Defense[] arrayOfDefenses)
	{
		// get the defense radius
		double radius = arrayOfDefenses[1].getDefenseRadius();
		// get the defenses Y position
		double defenseY = arrayOfDefenses[1].getDefenseY();
		// get the defenses vertical boundaries
		double downLimit = defenseY - radius/8;
		double upLimit = defenseY + radius/8;
		// get the projectile Y position
		double playerShotYPosition = pProjectile.getPlayerProjectileY();
		// get the projectile X position
		double playerShotXPosition = pProjectile.getPlayerProjectileX();

		// iterate over each defense
		for (int i = 0; i < 3; i++)
		{
			// make sure the defense is alive
			if (!arrayOfDefenses[i].isDefenseAlive())
				continue;
			// get the defense's boundaries
			double leftLimit = arrayOfDefenses[i].getDefenseX() - radius;
			double rightLimit = arrayOfDefenses[i].getDefenseX() + radius;
			// check if the X positions coincide
			if (playerShotXPosition >= leftLimit && playerShotXPosition <= rightLimit)
			{
				// check if the Y positions Coincide
				if ((playerShotYPosition >= downLimit) && (playerShotYPosition <= upLimit))
				{
					// we got a hit!
					// take a life out of the defense
					arrayOfDefenses[i].defenseLostALife();
					return true;
				}
			}
		}

		// it has not hit a defense
		return false;
	}

	/**
	 * moves the player projectile
	 * if the projectile has not been shot, it moves with the player.
	 * if it has been shot, it moves vertically.
	 * @param pProjectile
	 */
	public static void movePlayerProjectile(PlayerProjectile pProjectile, Player p)
	{
		// if the projectile hasn't yet been shot (it is not yet flying)
		if (!pProjectile.isPlayerProjectileFlying())
		{
			// move the projectile with the player
			pProjectile.setPlayerProjectileX(p.getPlayerX());
		}
		// else if the projectile has been shot
		else if (pProjectile.isPlayerProjectileFlying())
		{
			// move it vertically
			double y = pProjectile.getPlayerProjectileY();
			double newY = y + 0.03;
			pProjectile.setPlayerProjectileY(newY);
		}
	}

	/**
	 * creates a player projectile
	 * @param p the player
	 * @return a player projectile at the same position as the player
	 */
	public static PlayerProjectile createPlayerProjectile(Player p)
	{
		double x = p.getPlayerX();
		double y = p.getPlayerY();
		double radius = p.getPlayerRadius();
		// create the projectile
		PlayerProjectile pProjectile = new PlayerProjectile(x, y+radius, 0.008);
		// return it
		return pProjectile;
	}

	/**
	 * creates an array of 3 defenses
	 * @return
	 */
	public static Defense[] createDefenses(int level)
	{
		int numLives = 1;
		// it will have less lives on harder levels
		if(level == 1)
			numLives = 15;
		else if(level == 2)
			numLives = 10;
		else if(level == 3)
			numLives = 5;
		// create the array of defenses
		Defense[] arrayOfDefenses = new Defense[3];
		// fill it with defenses
		for (int i = 0; i < 3; i++)
		{
			arrayOfDefenses[i] = new Defense(0.16 + i*0.34, 0.25, 0.09, numLives);
		}
		// return it
		return arrayOfDefenses;
	}

	/**
	 * moves the player from left to right, making sure the player hasn't hit the wall
	 * @param p the player to be drawn
	 */
	public static void movePlayer(Player p)
	{
		// if the player is trying to go left
		if (ArcadeKeys.isKeyPressed(1, ArcadeKeys.KEY_LEFT))
		{
			// make sure he hasn't hit the left wall
			double x = p.getPlayerX();
			if (x <= 0.05)
				return;
			// move him to the left
			double newX = x - p.getPlayerSpeed();
			p.setPlayerX(newX);
		}
		// if the player is trying to go to the right
		else if (ArcadeKeys.isKeyPressed(1, ArcadeKeys.KEY_RIGHT))
		{
			// make sure the player hasn't hit the right wall
			double x = p.getPlayerX();
			if (x >= 0.95)
				return;
			// move him to the right
			double newX = x + p.getPlayerSpeed();
			p.setPlayerX(newX);
		}
	}

	/**
	 * checks if the player has pressed the up arrow, meaning he has shot
	 * sets pProjectile.isFlying to true
	 * @param pProjectile
	 */
	public static void checkPlayerShot(PlayerProjectile pProjectile)
	{
		// if the player presses A, it means he has shot
		if(ArcadeKeys.isKeyPressed(1, ArcadeKeys.KEY_UP))
		{
			// the projectile has been shot:
			// it is now flying
			pProjectile.shootPlayerProjectile();
		}
	}

	/**
	 * draws the alien projectile, only if it has been shot
	 * @param aProjectile
	 */
	public static void drawAlienProjectile(AlienProjectile aProjectile)
	{
		// only draw it if it has been shot
		if(aProjectile.isAlienProjectileFlying())
		{
			// draw it as a little orange ball
			StdDraw.setPenColor(Color.orange);
			double x = aProjectile.getAlienProjectileX();
			double y = aProjectile.getAlienProjectileY();
			double radius = aProjectile.getAlienProjectileRadius();
			StdDraw.filledCircle(x, y, radius);
		}
	}

	/**
	 * draws a player projectile as a white circle, only
	 * if the projectile has been shot
	 * @param pProjectile
	 */
	public static void drawPlayerProjectile(PlayerProjectile pProjectile)
	{
		// only draw it if it has been shot
		if(pProjectile.isPlayerProjectileFlying())
		{
			// draw it as a little white circle
			StdDraw.setPenColor(Color.white);
			double x = pProjectile.getPlayerProjectileX();
			double y = pProjectile.getPlayerProjectileY();
			double r = pProjectile.getPlayerProjectileRadius();
			StdDraw.filledCircle(x, y, r);
		}
	}

	/**
	 * draws everything on the screen:
	 * the aliens, the player, the defenses and the projectiles
	 * @param waveOfAliens the aliens to be drawn
	 * @param p the player to be drawn
	 * @param arrayOfDefenses the 3 defenses to be drawn
	 * @param pProjectile the player projectile to be drawn
	 */
	public static void drawEverything(AlienWave waveOfAliens, Player p, Defense[] arrayOfDefenses, PlayerProjectile pProjectile, AlienProjectile aProjectile, int numRound, SpecialAlien specialAlien, Life life, Money money, Speed speed)
	{
		clear();
		// draw the player
		drawPlayer(p);
		// draw the wave
		drawWave(waveOfAliens);
		// draw the 3 defenses
		for (int i = 0; i < 3; i++)
		{
			drawDefense(arrayOfDefenses[i]);
		}
		// draw the player projectile
		drawPlayerProjectile(pProjectile);
		// draw the alien projectile
		drawAlienProjectile(aProjectile);
		// draw the secial alien
		drawSpecialAlien(specialAlien);
		// draw the powerUp
		drawPowerUp(life, money, speed);
		// let the player know what round he's in
		whatRound(numRound);
		// give the player's score
		printScore(p);
		// big pause
		StdDraw.show(30);
	}

	/**
	 * draws the powerUp that is activated
	 * @param life
	 */
	public static void drawPowerUp(Life life, Money money, Speed speed)
	{
		// draws the life if it is activated
		if (life.isLifeFlying())
			drawLife(life);
		// draws the money if it is activated
		else if (money.isMoneyFlying())
			drawMoney(money);
		// draws the speed if it is activated
		else if (speed.isSpeedFlying())
			drawSpeed(speed);
	}

	/**
	 * draws the speed booster as a running man
	 * @param speed
	 */
	public static void drawSpeed(Speed speed)
	{
		// get positions
		double x = speed.getSpeedX();
		double y = speed.getSpeedY();
		double radius = speed.getSpeedRadius();
		// cyan square
		StdDraw.setPenColor(Color.cyan);
		StdDraw.filledSquare(x, y, radius);
		// running man
		StdDraw.setPenColor(Color.black);
		// head
		StdDraw.filledCircle(x + radius/2, y + radius/1.5, radius/3.8);
		// body
		StdDraw.setPenRadius(0.004);
		StdDraw.line(x+radius/2, y+radius/1.5, x, y-radius/2);
		// legs
		StdDraw.line(x, y-radius/2, x-radius/1.7, y-radius/1.3);
		StdDraw.line(x+radius/7, y-radius/3.5, x+radius/1.7, y-radius/5);
		StdDraw.line(x+radius/1.7, y-radius/5, x+radius/2.5, y-radius/1.3);
		// arms
		StdDraw.line(x+radius/2.3, y+radius/1.8, x+radius/1.8, y+radius/8);
		StdDraw.line(x+radius/1.8, y+radius/8, x+radius/1.1, y+radius/3);
		StdDraw.line(x+radius/3, y+radius/2.4, x-radius/4.4, y+radius/2.4);
		StdDraw.line(x-radius/4.4, y+radius/2.4, x-radius/2.8, y);
		// wind
		StdDraw.line(x-radius/2, y+radius/2.3, x-radius, y+radius/2.3);
		StdDraw.line(x-radius/2.3, y, x-radius, y);
		StdDraw.line(x-radius/1.6, y-radius/2.3, x-radius, y-radius/2.3);
	}

	/**
	 * draws the money as a golden coin
	 * @param money
	 */
	public static void drawMoney(Money money)
	{
		// get position
		double x = money.getMoneyX();
		double y = money.getMoneyY();
		double radius = money.getMoneyRadius();
		// outer gray circle
		StdDraw.setPenColor(Color.gray);
		StdDraw.filledCircle(x, y, radius);
		// inner yellow circle
		StdDraw.setPenColor(Color.yellow);
		StdDraw.filledCircle(x, y, radius/1.35);
		// draw a money symbol
		StdDraw.setPenColor(Color.black);
		StdDraw.text(x, y, "$$");
	}

	/**
	 * draws the life as a medication box
	 * @param life
	 */
	public static void drawLife(Life life)
	{
		// get position
		double x = life.getLifeX();
		double y = life.getLifeY();
		double radius = life.getLifeRadius();
		// red square
		StdDraw.setPenColor(Color.red);
		StdDraw.filledSquare(x, y, radius);
		// white cross
		StdDraw.setPenColor(Color.white);
		StdDraw.filledRectangle(x, y, radius/3, radius/1.2);
		StdDraw.filledRectangle(x, y, radius/1.2, radius/3);
	}

	/**
	 * prints the player's score at the upper left corner
	 * @param p
	 */
	public static void printScore(Player p)
	{
		int score = p.getPlayerScore();
		String s = "Score: " + score;
		StdDraw.text(0.22, 1.025, s);
	}

	/**
	 * draws the special alien as an orange space craft, only if it is flying
	 * @param specialAlien
	 */
	public static void drawSpecialAlien(SpecialAlien specialAlien)
	{
		// only draw the alien if it is flying
		if(!specialAlien.isSpecialAlienFlying())
			return;

		// draw it as an orange alien spacecraft
		StdDraw.setPenColor(Color.orange);
		double x = specialAlien.getSpecialAlienX();
		double y = specialAlien.getSpecilAlienY();
		double radius = specialAlien.getSpecialAlienRadius();
		StdDraw.filledRectangle(x, y, radius, radius/8);
		// landers
		StdDraw.setPenRadius(0.005);
		StdDraw.line(x-radius/3, y, x-radius/1.5, y-radius/2.5);
		StdDraw.line(x+radius/3, y, x+radius/1.5, y-radius/2.5);
		StdDraw.filledCircle(x-radius/1.5, y-radius/2.5, radius/10);
		StdDraw.filledCircle(x+radius/1.5, y-radius/2.5, radius/10);
		// Anthena
		StdDraw.line(x, y, x, y+radius/2.5);
		StdDraw.filledCircle(x, y+radius/2.5, radius/10);
		// windows
		StdDraw.setPenColor(Color.black);
		StdDraw.filledCircle(x-radius/1.5, y, radius/12);
		StdDraw.filledCircle(x+radius/1.5, y, radius/12);
		StdDraw.filledCircle(x, y, radius/12);
	}

	/**
	 * writes the round on the upper left corner of the screen
	 * @param numRound
	 */
	public static void whatRound(int numRound)
	{
		// write the round in the upper left corner
		StdDraw.setPenColor(Color.white);
		String s = "Round: " + numRound + "/5";
		StdDraw.text(0.04, 1.025, s);
	}

	/**
	 * draws a defense in its right position, only if it is alive
	 * draws some damage if it has been hit
	 * @param d
	 */
	public static void drawDefense(Defense d)
	{
		// draw the defense as a yellow rectangle
		StdDraw.setPenColor(Color.yellow);

		// get the number of lives of this defense
		int numLives = d.getDefenseLives();

		// only draw it if still has lives
		if (numLives != 0)
		{
			// gets its position
			double x = d.getDefenseX();
			double y = d.getDefenseY();
			double radius = d.getDefenseRadius();
			// draw it
			StdDraw.filledRectangle(x, y, radius, radius/5);
			// write how many lives it still has left
			String livesCounter = "" + d.getDefenseLives();
			StdDraw.setPenColor(Color.black);
			StdDraw.text(x, y-0.003, livesCounter);
			// draw some damage
			if(numLives > 5 && numLives <= 8)
			{
				StdDraw.setPenColor(Color.black);
				StdDraw.filledSquare(x-radius/2, y+radius/7, radius/16);
				StdDraw.filledSquare(x+radius/2, y-radius/8, radius/12);
			}
			else if(numLives > 3 && numLives <= 5)
			{
				StdDraw.setPenColor(Color.black);
				StdDraw.filledSquare(x-radius/2, y+radius/7, radius/16);
				StdDraw.filledSquare(x+radius/2, y-radius/8, radius/12);
				StdDraw.filledSquare(x+radius, y+radius/8, radius/5);
				StdDraw.filledSquare(x-radius/1.2, y-radius/8, radius/6);
			}
			else if(numLives <= 3)
			{
				StdDraw.setPenColor(Color.black);
				StdDraw.filledSquare(x-radius/2, y+radius/7, radius/16);
				StdDraw.filledSquare(x+radius/2, y-radius/8, radius/12);
				StdDraw.filledSquare(x+radius, y+radius/8, radius/5);
				StdDraw.filledSquare(x-radius/1.2, y-radius/8, radius/6);
				StdDraw.filledSquare(x-radius/4, y-radius/8, radius/8);
				StdDraw.filledSquare(x+radius/3, y+radius/10, radius/8);
			}
		}
	}

	/**
	 * draws the player in the right position
	 * @param p
	 */
	public static void drawPlayer(Player p)
	{
		// draw the player as a red spaceship
		StdDraw.setPenColor(Color.red);
		// get the player position
		double x = p.getPlayerX();
		double y = p.getPlayerY();
		double radius = p.getPlayerRadius();
		// draw it
		StdDraw.filledRectangle(x, y+radius/2, radius, radius/2);
		StdDraw.filledRectangle(x, y+radius/1.4, radius/1.5, radius/2);
		StdDraw.filledSquare(x, y+radius/0.8, radius/4);
		StdDraw.filledRectangle(x, y+radius, radius/12, radius/1.2);
		// write how many lives you have left
		StdDraw.setPenColor(Color.white);
		String numLives = "" + p.getNumLivesPlayer();
		StdDraw.text(x, y + radius/2.3, numLives);
	}

	/**
	 * decides whether the wave should be moved left or right
	 * @param waveOfAliens
	 */
	public static void moveWave(AlienWave waveOfAliens)
	{
		// if the wave is moving to the right
		if (waveOfAliens.isWaveMovingRight())
		{
			// move wave to the right
			moveWaveToTheRight(waveOfAliens);
		}
		// if the wave is moving to the left
		else if (!waveOfAliens.isWaveMovingRight())
		{
			// move wave to the left
			moveWaveToTheLeft(waveOfAliens);
		}
	}

	/**
	 * move the wave down, if it has reached the boundaries
	 * @param waveOfAliens
	 */
	public static void moveWaveDown(AlienWave waveOfAliens)
	{
		// get the southern most alien
		int southernMost = waveOfAliens.getSouthernMostAlien();
		// get the Y position of the southern most alien
		double southernMostY = waveOfAliens.getAliens()[0][southernMost].getAlienY();
		// check if this alien is too low
		if(southernMostY <= 0.3)
			waveOfAliens.AlienWaveIsTooLow();
		// if it's not too low:
		else
		{
			// move each alien a bit down
			for (int i = 0; i < 6; i++)
			{
				for (int j = 0; j < 5; j++)
				{
					double y = waveOfAliens.getAliens()[i][j].getAlienY();
					double newY = y - 0.02;
					waveOfAliens.getAliens()[i][j].setAlienY(newY);
				}
			}
		}
	}

	/**
	 * move all the aliens to left, if they haven't reached the left limit.
	 * if they have reached the left limit, move the wave down and make it go right
	 * @param waveOfAliens
	 */
	public static void moveWaveToTheLeft(AlienWave waveOfAliens)
	{
		// make sure the wave can keep going to the left
		// the left most column of aliens
		int leftMostAlien = waveOfAliens.getLeftMostAlien();
		// position of the left most alien
		double positionLeftMost = waveOfAliens.getAliens()[leftMostAlien][0].getAlienX();
		// if this alien has gone enough to the left
		if (positionLeftMost <= 0)
		{
			// make the wave go right
			waveOfAliens.changeWaveDrirection();
			// move the wave down
			moveWaveDown(waveOfAliens);
		}
		// if it can keep going left
		else
		{
			// move each alien a little bit to the left
			for (int i = 0; i < 6; i++)
			{
				for (int j = 0; j < 5; j++)
				{
					double x = waveOfAliens.getAliens()[i][j].getAlienX();
					double newX = x - 0.0012;
					waveOfAliens.getAliens()[i][j].setAlienX(newX);
				}
			}
		}
	}

	/**
	 * move the wave to right, if they haven't reached the right limit.
	 * If they have reached the right limit, move the wave down and make it go left
	 * @param waveOfAliens
	 */
	public static void moveWaveToTheRight(AlienWave waveOfAliens)
	{
		// make sure the wave can keep going to the right
		// the right most column of aliens
		int rightMostAlien = waveOfAliens.getRightMostAlien();
		// position of the right most alien
		double positionRightMost = waveOfAliens.getAliens()[rightMostAlien][0].getAlienX();
		// if this alien has gone enough to the right
		if (positionRightMost >= 1)
		{
			// make the wave go left
			waveOfAliens.changeWaveDrirection();
			// move the wave down
			moveWaveDown(waveOfAliens);
		}
		// if it can keep going right
		else
		{
			// move each alien a little bit to the right
			for (int i = 0; i < 6; i++)
			{
				for (int j = 0; j < 5; j++)
				{
					double x = waveOfAliens.getAliens()[i][j].getAlienX();
					double newX = x + 0.0012;
					waveOfAliens.getAliens()[i][j].setAlienX(newX);
				}
			}
		}
	}

	/**
	 * draw the alien in its correct position
	 * @param alien the alien to be drawn
	 */
	public static void drawAlien(Alien alien)
	{
		// get the position and size of the alien
		double x = alien.getAlienX();
		double y = alien.getAlienY();
		double radius = alien.getAlienRadius();
		// draw the alien, which is a green square
		StdDraw.setPenColor(Color.green);
		StdDraw.filledSquare(x, y, radius);
		// draw an angry face
		StdDraw.setPenColor(Color.black);
		StdDraw.setPenRadius(0.01);
		// eyes
		StdDraw.point(x-radius/1.85, y+radius/3.8);
		StdDraw.point(x+radius/1.85, y+radius/3.8);
		// angry eyebrows
		StdDraw.setPenRadius(0.006);
		StdDraw.line(x-radius, y+radius, x-0.005, y+radius/3);
		StdDraw.line(x+radius, y+radius, x+0.005, y+radius/3);
		// mouth
		StdDraw.setPenRadius(0.013);
		StdDraw.line(x-radius/1.6, y-radius/2, x+radius/1.6, y-radius/2);
	}

	/**
	 * draws a wave of aliens, one alien at a time, only if the alien is alive
	 * @param waveOfAliens a double array of aliens
	 */
	public static void drawWave(AlienWave waveOfAliens)
	{
		// go over the array, looking for live aliens
		for (int i = 0; i < 6; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				// check if that alien is alive
				if (waveOfAliens.getAliens()[i][j].isAlienAlive())
				{
					// draw the alien
					drawAlien(waveOfAliens.getAliens()[i][j]);
				}
			}
		}
	}

	/**
	 * creates a new Wave of Aliens
	 * @param arrayOfAliens an array of Aliens
	 * @return a wave of Aliens
	 */
	public static AlienWave createWaveOfAliens(Alien[][] arrayOfAliens)
	{
		AlienWave waveOfAliens = new AlienWave(arrayOfAliens);
		return waveOfAliens;
	}

	/**
	 * creates an array of aliens, each with its starting position
	 * @return the created array of aliens
	 */
	public static Alien[][] createAliens()
	{
		// create the new Array
		Alien[][] arrayOfAliens = new Alien[6][5];
		// fill it with aliens
		for (int i = 0; i < 6; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				arrayOfAliens[i][j] = new Alien(0.25 + i*0.1, 0.95 - j*0.1, 0.035);
			}
		}
		// return it
		return arrayOfAliens;
	}

	/**
	 * creates a start screen, with the name of the
	 * game and a play button
	 */
	public static void begin()
	{
		// draw a black background
		StdDraw.setPenColor(Color.black);
		StdDraw.filledSquare(0.5, 0.5, 0.5);
		// draw a play button
		StdDraw.setPenColor(Color.green);
		StdDraw.filledRectangle(0.5, 0.5, 0.2, 0.1);
		StdDraw.setPenColor(Color.white);
		double[] triX = {0.46, 0.46, 0.56};
		double[] triY = {0.45, 0.55, 0.5};
		StdDraw.filledPolygon(triX, triY);
		// Some text
		StdDraw.text(0.5, 0.85, "SPACE INVADERS");
		StdDraw.text(0.5, 0.8, "Clear 5 rounds of Aliens to save the Galaxy");
		// instructions
		StdDraw.text(0.3, 0.68, "> right");
		StdDraw.text(0.5, 0.68, "< left");
		StdDraw.text(0.7, 0.68, "^ shoot");
		StdDraw.show(20);

		// will return the mouse position
		double[] mousePosition = new double[2];

		while(!StdDraw.mousePressed())
		{
			if (StdDraw.mousePressed())
			{
				while (StdDraw.mousePressed())
				{
					// wait
					StdDraw.pause(15);
				}

				// here, the mouse has been released
				break;
			}
			// wait
			StdDraw.pause(15);
		}

		// get mouse coordinates
		mousePosition[0] = StdDraw.mouseX();
		mousePosition[1] = StdDraw.mouseY();

		// ;)
		checkMousePosition(mousePosition);
	}

	/**
	 * check that the user clicked on the play button
	 * @param mousePosition array containing the coordinates of the mouse
	 *                      when the click happened
	 */
	public static void checkMousePosition(double[] mousePosition)
	{
		// if the user clicked on the button
		if ((mousePosition[0] >= 0.3 && mousePosition[0] <= 0.7)
			 && (mousePosition[1] >= 0.4 && mousePosition[1] <= 0.6))
		{
			clear();
			return;
		}

		// else
		else
		{
			begin();
		}
	}

	/**
	 * clears the canvas and draws the black background
	 */
	public static void clear()
	{
		// clear the canvas
		StdDraw.clear();
		// draw the black background
		StdDraw.setPenColor(Color.black);
		StdDraw.filledSquare(0.5, 0.5, 1.0);
	}
}

import java.awt.Color;
import java.util.LinkedList;

import sedgewick.StdDraw;

public class Game {

	public static void main(String[] args) {

		// create the head of the snake, going right
		Piece head = new Piece(0.4, 0.5, 'r');
		// create the snake, with 4 pieces
		Snake s = createSnake(head);
		// create the food
		Food f = new Food(0.6, 0.5);
		// create the turningPoint Grid
		TurnGrid grid = createTPGrid();

		// Play Game
		while(true){

			// move the snake
			s.move();
			// check if the snake hit the wall
			if(snakeDied(head))
				break;
			// update board
			updateBoard(s, f);
			// check if the snake ate food, and take the necessary actions
			checkEat(s, head, f);
			// check if the player is trying to turn the snake, and take the necessary actions
			checkMove(head, grid);
			// check the need to turn other pieces
			turnOtherPieces(s, grid);

		}

		// end
		end();

	}

	/**
	 * displays the end screen
	 */
	public static void end(){

		StdDraw.clear();
		StdDraw.setPenColor(Color.black);
		StdDraw.filledSquare(0.5, 0.5, 1);
		StdDraw.setPenColor(Color.white);
		StdDraw.text(0.5, 0.5, "You Died");
		StdDraw.show(20);

	}

	/**
	 * checks if the snake's head hit the wall
	 * @param p - the head of the snake
	 * @return true if the snake died
	 */
	public static boolean snakeDied(Piece p){

		// get the necessary info
		double posX = p.getPosX();
		double posY = p.getPosY();
		double radius = p.getRadius();

		// do the math
		if(posY + radius >= 1 || posY - radius <= 0)
			return true;
		if(posX + radius >= 1 || posX - radius <= 0)
			return true;
		return false;

	}

	/**
	 * Checks if any of the snake pieces has reached a TurningPoint
	 * If yes, makes the piece turn accordingly
	 * @param s - the snake
	 * @param tg - the grid containing the turning points
	 */
	public static void turnOtherPieces(Snake s, TurnGrid tg){

		// iterate over every turning point
		for(int tp = 0; tp < tg.getListOfTP().size(); tp++){

			if(!tg.getListOfTP().get(tp).isActivated())
				continue;

			// get turning point info
			double tpX = tg.getListOfTP().get(tp).getPosX();
			double tpY = tg.getListOfTP().get(tp).getPosY();
			char dir = tg.getListOfTP().get(tp).getDirection();

			// iterate over every piece of the snake
			for(int piece = 0; piece < s.getListOfPieces().size(); piece++){

				// get the piece info
				double pieceX = s.getListOfPieces().get(piece).getPosX();
				double pieceY = s.getListOfPieces().get(piece).getPosY();
				// check if they coincide
				if(tpX == pieceX && tpY == pieceY){
					s.getListOfPieces().get(piece).setDirection(dir);
					if(piece == s.size() - 1)
						tg.getListOfTP().get(tp).deactivate();
				}
			}

		}

	}

	/**
	 * Creates the TP Grid, which stores all the previously created Turning points
	 * @return the TP Grid
	 */
	public static TurnGrid createTPGrid(){

		// create the list
		LinkedList<TurningPoint> list = new LinkedList<TurningPoint>();
		// create the grid
		TurnGrid tg = new TurnGrid(list);
		return tg;

	}

	/**
	 * Checks if the player is trying to turn the snake
	 * if yes, turns the head and creates a turningPoint at that position
	 * @param p - the head of the snake
	 */
	public static void checkMove(Piece p, TurnGrid tg){

		char d = p.getDirection();
		// trying to turn right
		if(ArcadeKeys.isKeyPressed(1, ArcadeKeys.KEY_RIGHT)){
			if(d == 'l' || d == 'r')
				return;
			createTurningPoint(p, 'r', tg);
			p.setDirection('r');
		}
		// trying to turn left
		if(ArcadeKeys.isKeyPressed(1, ArcadeKeys.KEY_LEFT)){
			if(d == 'l' || d == 'r')
				return;
			createTurningPoint(p, 'l', tg);
			p.setDirection('l');
		}
		// trying to turn up
		if(ArcadeKeys.isKeyPressed(1, ArcadeKeys.KEY_UP)){
			if(d == 'u' || d == 'd')
				return;
			createTurningPoint(p, 'u', tg);
			p.setDirection('u');
		}
		// trying to turn down
		if(ArcadeKeys.isKeyPressed(1, ArcadeKeys.KEY_DOWN)){
			if(d == 'u' || d == 'd')
				return;
			createTurningPoint(p, 'd', tg);
			p.setDirection('d');
		}

	}

	/**
	 * creates a turning point at the position of the snake's head
	 * @param p - the head of the snake
	 */
	public static void createTurningPoint(Piece p, char dir, TurnGrid tg){

		// get the needed info
		double x = p.getPosX();
		double y = p.getPosY();

		// create the turning point
		TurningPoint tp = new TurningPoint(x, y, dir);

		// add it to the grid
		tg.addTurningPoint(tp);

	}

	/**
	 * Checks if the snake has eaten a food, and makes the snake bigger if it has/
	 * @param p - the head of the snake
	 * @param f - the food
	 */
	public static void checkEat(Snake s, Piece p, Food f){

		// head info
		double headX = p.getPosX();
		double headY = p.getPosY();
		double headR = p.getRadius();
		// food info
		double foodX = f.getPosX();
		double foodY = f.getPosY();
		double foodR = f.getRadius();
		// eat?
		boolean eat = false;

		if(p.getDirection() == 'u'){
			// check x
			if(foodX + foodR < headX - headR || foodX - foodR > headX + headR){
				eat = false;
				return;
			}
			// check y
			if(foodY - foodR <= headY + headR){
				eat = true;
			}
		}
		else if(p.getDirection() == 'r'){
			// check y
			if(foodY + foodR < headY - headR || foodY - foodR > headY + headR){
				eat = false;
				return;
			}
			// check x
			if(foodX - foodR <= headX + headR){
				eat = true;
			}
		}
		else if(p.getDirection() == 'd'){
			// check x
			if(foodX + foodR < headX - headR || foodX - foodR > headX + headR){
				eat = false;
				return;
			}
			// check y
			if(foodY + foodR >= headY - headR){
				eat = true;
			}
		}
		else if(p.getDirection() == 'l'){
			// check y
			if(foodY + foodR < headY - headR || foodY - foodR > headY + headR){
				eat = false;
				return;
			}
			// check x
			if(foodX + foodR >= headX - headR){
				eat = true;
			}
		}

		// if the snake ate food
		if(eat){
			// snake gets bigger
			s.addPiece();
			// food moves
			f.move();
		}

	}

	/**
	 * Updates the board
	 * Moves the snake, draws the snake, draws the food
	 */
	public static void updateBoard(Snake s, Food f){

		StdDraw.clear();
		drawLine();
		StdDraw.text(.5, 1.018, "Points: "+s.size());
		s.draw();
		f.draw();
		StdDraw.show(5);

	}

	/**
	 * Draws an outer boundary for where the snake can move
	 */
	public static void drawLine(){

		StdDraw.setPenColor(Color.black);
		StdDraw.setPenRadius(0.005);
		StdDraw.square(.5, .5, .5);

	}

	/**
	 * Creates and returns the snake to be used in the game
	 * @param first - the head of the snake
	 */
	public static Snake createSnake(Piece first){

		// create the linked list and add the head
		LinkedList<Piece> list = new LinkedList<Piece>();
		list.addLast(first);
		// create the snake and add 3 more pieces
		Snake s = new Snake(list);
		s.addPiece();
		s.addPiece();
		s.addPiece();
		// return the snake
		return s;

	}

}

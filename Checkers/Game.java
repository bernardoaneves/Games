import java.awt.Color;

import sedgewick.StdDraw;

// THE GAME
public class Game {

	public static void main(String[] args) {

		// create the board
		Board board = createBoard();
		// create the Red and White Pieces
		RedArmy reds = createReds();
		WhiteArmy whites = createWhites();
		// draw everything
		drawEverything(board, reds, whites);

		// play Game
		while(reds.RedAlive() && whites.WhiteAlive()){

			// draw everything
			drawEverything(board, reds, whites);
			// occupy the board
			occupyBoard(board, reds, whites);
			// look for player piece selection
			int coordinates[] = identifySelection();
			while(!pieceSelected(board, coordinates)){
				coordinates = identifySelection();
			}
			// react accordingly
			selectPiece(reds, whites, coordinates);
			// draw everything
			drawEverything(board, reds, whites);
			// look for player move attempt, and execute it if legal
			int moreCoordinates[] = identifySelection();
			move(board, reds, whites, coordinates, moreCoordinates);
			// check for litness
			reds.checkLitness();
			whites.checkLitness();

		}
		// someone won
		end();

	}

	/**
	 * End screen
	 */
	public static void end(){

		StdDraw.clear();
		StdDraw.setPenColor(Color.black);
		StdDraw.filledSquare(.5, .5, 2);
		StdDraw.setPenColor(Color.white);
		StdDraw.text(.5, .5, "End");
		StdDraw.show(10);

	}

	/**
	 *
	 * Draws Everything
	 *
	 */
	public static void drawEverything(Board b, RedArmy rA, WhiteArmy wA){

		StdDraw.clear();
		b.draw();
		rA.draw();
		wA.draw();
		StdDraw.setPenColor(Color.black);
		StdDraw.text(.5, 1.022, "" + wA.numSoldiers());
		StdDraw.text(.5, -.03, "" + rA.numSoldiers());

		StdDraw.show(200);

	}

	/**
	 * checks if the desired move is legal. if yes, executes it
	 * @param b
	 * @param rA
	 * @param wA
	 * @param nowCoords
	 * @param targetCoords
	 */
	public static void move(Board b, RedArmy rA, WhiteArmy wA, int[] nowCoords, int[] targetCoords){

		boolean redPieceSelected = false;
		int selectedIndex = 0;
		for(int i = 0; i < 12; i++){
			if(rA.getTheArray()[i].isSelected()){
				redPieceSelected = true;
				selectedIndex = i;
				break;
			}
		}
		if(!redPieceSelected){
			for(int i = 0; i < 12; i++){
				if(wA.getTheArray()[i].isSelected()){
					selectedIndex = i;
					break;
				}
			}
		}
		int targetX = targetCoords[0];
		int targetY = targetCoords[1];
		int thisX = nowCoords[0];
		int thisY = nowCoords[1];
		if(!b.getTheDoubleArray()[targetX][targetY].isBlack()){
			rA.getTheArray()[selectedIndex].deselect();
			return;
		}
		// determine if it is a legal move for a red piece
		if(redPieceSelected){
			// checks if the move is fundamentally legal
			if(!rA.getTheArray()[selectedIndex].checkLegalMove(targetX, targetY)){
				rA.getTheArray()[selectedIndex].deselect();
				return;
			}
			else{
				// if the selected square is empty
				if(!b.getTheDoubleArray()[targetX][targetY].isTaken()){
					rA.getTheArray()[selectedIndex].movePiece(targetX, targetY);
					rA.getTheArray()[selectedIndex].deselect();
					b.getTheDoubleArray()[thisX][thisY].free();
					return;
				}
				// if it is not empty
				else{
					// if it is taken by a red piece
					if(b.getTheDoubleArray()[targetX][targetY].isTakenByRed()){
						rA.getTheArray()[selectedIndex].deselect();
						return;
					}
					// if it is taken by a white piece
					else{
						// moving up and right
						if(targetX == thisX + 1 && targetY == thisY + 1){
							if(targetX == 7 || targetY == 7){
								rA.getTheArray()[selectedIndex].deselect();
								return;
							}
							else{
								if(b.getTheDoubleArray()[targetX + 1][targetY + 1].isTaken()){
									rA.getTheArray()[selectedIndex].deselect();
									return;
								}
								else{
									rA.getTheArray()[selectedIndex].movePiece(targetX + 1, targetY + 1);
									rA.getTheArray()[selectedIndex].deselect();
									b.getTheDoubleArray()[thisX][thisY].free();
									for(int i = 0; i < 12; i++){
										if(wA.getTheArray()[i].getX() == targetX && wA.getTheArray()[i].getY() == targetY){
											wA.getTheArray()[i].kill();
											b.getTheDoubleArray()[targetX][targetY].free();
										}
									}
									return;
								}
							}
						}
						// moving up and left
						else if(targetX == thisX - 1 && targetY == thisY + 1){
							if(targetX == 0 || targetY == 7){
								rA.getTheArray()[selectedIndex].deselect();
								return;
							}
							else{
								if(b.getTheDoubleArray()[targetX - 1][targetY + 1].isTaken()){
									rA.getTheArray()[selectedIndex].deselect();
									return;
								}
								else{
									rA.getTheArray()[selectedIndex].movePiece(targetX - 1, targetY + 1);
									rA.getTheArray()[selectedIndex].deselect();
									b.getTheDoubleArray()[thisX][thisY].free();
									for(int i = 0; i < 12; i++){
										if(wA.getTheArray()[i].getX() == targetX && wA.getTheArray()[i].getY() == targetY){
											wA.getTheArray()[i].kill();
											b.getTheDoubleArray()[targetX][targetY].free();
										}
									}
									return;
								}
							}
						}
						// moving down and right
						else if(targetX == thisX + 1 && targetY == thisY - 1){
							if(targetX == 7 || targetY == 0){
								rA.getTheArray()[selectedIndex].deselect();
								return;
							}
							else{
								if(b.getTheDoubleArray()[targetX + 1][targetY - 1].isTaken()){
									rA.getTheArray()[selectedIndex].deselect();
									return;
								}
								else{
									rA.getTheArray()[selectedIndex].movePiece(targetX + 1, targetY - 1);
									rA.getTheArray()[selectedIndex].deselect();
									b.getTheDoubleArray()[thisX][thisY].free();
									for(int i = 0; i < 12; i++){
										if(wA.getTheArray()[i].getX() == targetX && wA.getTheArray()[i].getY() == targetY){
											wA.getTheArray()[i].kill();
											b.getTheDoubleArray()[targetX][targetY].free();
										}
									}
									return;
								}
							}
						}
						// moving down and left
						else{
							if(targetX == 0 || targetY == 0){
								rA.getTheArray()[selectedIndex].deselect();
								return;
							}
							else{
								if(b.getTheDoubleArray()[targetX - 1][targetY - 1].isTaken()){
									rA.getTheArray()[selectedIndex].deselect();
									return;
								}
								else{
									rA.getTheArray()[selectedIndex].movePiece(targetX - 1, targetY - 1);
									rA.getTheArray()[selectedIndex].deselect();
									b.getTheDoubleArray()[thisX][thisY].free();
									for(int i = 0; i < 12; i++){
										if(wA.getTheArray()[i].getX() == targetX && wA.getTheArray()[i].getY() == targetY){
											wA.getTheArray()[i].kill();
											b.getTheDoubleArray()[targetX][targetY].free();
										}
									}
									return;
								}
							}
						}
					}
				}
			}
		}
		// determine if it is a legal move for a white piece
		else{
			// checks if the move is fundamentally legal
			if(!wA.getTheArray()[selectedIndex].checkLegalMove(targetX, targetY)){
				wA.getTheArray()[selectedIndex].deselect();
				return;
			}
			else{
				// if the selected square is empty
				if(!b.getTheDoubleArray()[targetX][targetY].isTaken()){
					wA.getTheArray()[selectedIndex].movePiece(targetX, targetY);
					wA.getTheArray()[selectedIndex].deselect();
					b.getTheDoubleArray()[thisX][thisY].free();
					return;
				}
				// if it is not empty
				else{
					// if it is taken by a white piece
					if(b.getTheDoubleArray()[targetX][targetY].isTakenByWhite()){
						wA.getTheArray()[selectedIndex].deselect();
						return;
					}
					// if it is taken by a red piece
					else{
						// moving up and right
						if(targetX == thisX + 1 && targetY == thisY + 1){
							if(targetX == 7 || targetY == 7){
								wA.getTheArray()[selectedIndex].deselect();
								return;
							}
							else{
								if(b.getTheDoubleArray()[targetX + 1][targetY + 1].isTaken()){
									wA.getTheArray()[selectedIndex].deselect();
									return;
								}
								else{
									wA.getTheArray()[selectedIndex].movePiece(targetX + 1, targetY + 1);
									wA.getTheArray()[selectedIndex].deselect();
									b.getTheDoubleArray()[thisX][thisY].free();
									for(int i = 0; i < 12; i++){
										if(rA.getTheArray()[i].getX() == targetX && rA.getTheArray()[i].getY() == targetY){
											rA.getTheArray()[i].kill();
											b.getTheDoubleArray()[targetX][targetY].free();
										}
									}
									return;

								}
							}
						}
						// moving up and left
						else if(targetX == thisX - 1 && targetY == thisY + 1){
							if(targetX == 0 || targetY == 7){
								wA.getTheArray()[selectedIndex].deselect();
								return;
							}
							else{
								if(b.getTheDoubleArray()[targetX - 1][targetY + 1].isTaken()){
									wA.getTheArray()[selectedIndex].deselect();
									return;
								}
								else{
									wA.getTheArray()[selectedIndex].movePiece(targetX - 1, targetY + 1);
									wA.getTheArray()[selectedIndex].deselect();
									b.getTheDoubleArray()[thisX][thisY].free();
									for(int i = 0; i < 12; i++){
										if(rA.getTheArray()[i].getX() == targetX && rA.getTheArray()[i].getY() == targetY){
											rA.getTheArray()[i].kill();
											b.getTheDoubleArray()[targetX][targetY].free();
										}
									}
									return;
								}
							}
						}
						// moving down and right
						else if(targetX == thisX + 1 && targetY == thisY - 1){
							if(targetX == 7 || targetY == 0){
								wA.getTheArray()[selectedIndex].deselect();
								return;
							}
							else{
								if(b.getTheDoubleArray()[targetX + 1][targetY - 1].isTaken()){
									wA.getTheArray()[selectedIndex].deselect();
									return;
								}
								else{
									wA.getTheArray()[selectedIndex].movePiece(targetX + 1, targetY - 1);
									wA.getTheArray()[selectedIndex].deselect();
									b.getTheDoubleArray()[thisX][thisY].free();
									for(int i = 0; i < 12; i++){
										if(rA.getTheArray()[i].getX() == targetX && rA.getTheArray()[i].getY() == targetY){
											rA.getTheArray()[i].kill();
											b.getTheDoubleArray()[targetX][targetY].free();
										}
									}
									return;
								}
							}
						}
						// moving down and left
						else{
							if(targetX == 0 || targetY == 0){
								wA.getTheArray()[selectedIndex].deselect();
								return;
							}
							else{
								if(b.getTheDoubleArray()[targetX - 1][targetY - 1].isTaken()){
									wA.getTheArray()[selectedIndex].deselect();
									System.out.println("here");
									return;
								}
								else{
									wA.getTheArray()[selectedIndex].movePiece(targetX - 1, targetY - 1);
									wA.getTheArray()[selectedIndex].deselect();
									b.getTheDoubleArray()[thisX][thisY].free();
									for(int i = 0; i < 12; i++){
										if(rA.getTheArray()[i].getX() == targetX && rA.getTheArray()[i].getY() == targetY){
											rA.getTheArray()[i].kill();
											b.getTheDoubleArray()[targetX][targetY].free();
										}
									}
									return;
								}
							}
						}
					}
				}
			}
		}

	}

	/**
	 * selects the piece
	 * @param rA
	 * @param wA
	 * @param coords
	 */
	public static void selectPiece(RedArmy rA, WhiteArmy wA, int[] coords){

		int x = coords[0];
		int y = coords[1];
		// look in reds
		for(int i = 0; i < 12; i++){

			if(rA.getTheArray()[i].getX() == x && rA.getTheArray()[i].getY() == y){
				rA.getTheArray()[i].select();
				return;
			}

		}
		// look in whites
		for(int i = 0; i < 12; i++){

			if(wA.getTheArray()[i].getX() == x && wA.getTheArray()[i].getY() == y){
				wA.getTheArray()[i].select();
				return;
			}

		}

	}

	/**
	 * determines if a piece has been selected
	 * @return T / F
	 */
	public static boolean pieceSelected(Board b, int[] coords){

		int x = coords[0];
		int y = coords[1];
		if(b.getTheDoubleArray()[x][y].isTaken())
			return true;
		return false;

	}

	/**
	 * looks for where the player clicked on the board
	 * @return the coordinates of the suqare the player selected
	 */
	public static int[] identifySelection(){

		int coordinates[] = new int[2];
		while(!StdDraw.mousePressed())
		{
			if (StdDraw.mousePressed())
			{
				while (StdDraw.mousePressed())
				{
					StdDraw.pause(15);
				}
				// got it
				break;
			}
			StdDraw.pause(15);
		}

		double posX = StdDraw.mouseX();
		double posY = StdDraw.mouseY();
		for(int i = 0; i < 8; i++){

			if(posX < 0.125 + i*0.125){
				coordinates[0] = i;
				break;
			}

		}
		for(int i = 0; i < 8; i++){

			if(posY < 0.125 + i*0.125){
				coordinates[1] = i;
				break;
			}

		}

		return coordinates;

	}

	/**
	 * Occupies the Squares that are Taken by Pieces
	 * @param b the board
	 * @param rA the red pieces
	 * @param wA the white pieces
	 */
	public static void occupyBoard(Board b, RedArmy rA, WhiteArmy wA){

		for(int i = 0; i < 12; i++){
			int x = rA.getTheArray()[i].getX();
			int y = rA.getTheArray()[i].getY();
			if(rA.getTheArray()[i].isAlive())
				b.getTheDoubleArray()[x][y].occupyWithRed();
		}

		for(int i = 0; i < 12; i++){
			int x = wA.getTheArray()[i].getX();
			int y = wA.getTheArray()[i].getY();
			if(wA.getTheArray()[i].isAlive())
				b.getTheDoubleArray()[x][y].occupyWithWhite();
		}

	}

	/**
	 * Creates all the White Pieces
	 * @return the White Army
	 */
	public static WhiteArmy createWhites(){

		WhitePiece array[] = new WhitePiece[12];
		int index = 0;
		for(int y = 7; y > 4; y--){
			for(int x = 0; x < 4; x++){

				array[index] = new WhitePiece((y % 2 == 0) ? (1 + x*2) : (x*2), y);
				index++;

			}
		}

		WhiteArmy wA = new WhiteArmy(array);
		return wA;

	}

	/**
	 * Creates all the Red Pieces
	 * @return the Red Army
	 */
	public static RedArmy createReds(){

		RedPiece array[] = new RedPiece[12];
		int index = 0;
		for(int y = 0; y < 3; y++){
			for(int x = 0; x < 4; x++){

				array[index] = new RedPiece((y % 2 == 0) ? (1 + x*2) : (x*2), y);
				index++;

			}
		}

		RedArmy rA = new RedArmy(array);
		return rA;

	}

	/**
	 * Creates the Board where the Game is played
	 * @return the Board
	 */
	public static Board createBoard(){

		boolean black = false;
		Square array[][] = new Square[8][8];
		for(int y = 0; y < 8; y++){
			for(int x = 0; x < 8; x++){

				if(y % 2 == 0){
					if(x % 2 == 0)
						black = false;
					else
						black = true;
				}
				else{
					if(x % 2 == 0)
						black = true;
					else
						black = false;
				}
				Square square = new Square(x, y, black);
				array[x][y] = square;

			}
		}

		Board b = new Board(array);
		return b;

	}

}

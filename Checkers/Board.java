import java.awt.Color;

import sedgewick.StdDraw;

// defines the board object
public class Board {

	// DOUBLE ARRAY OF SQUARES
	private Square squares[][];

	// CONSTRUCTOR
	public Board(Square[][] array){

		this.squares = array;

	}

	// OTHER METHODS
	public Square[][] getTheDoubleArray(){
		return this.squares;
	}

	public void draw(){
		StdDraw.setPenColor(Color.black);
		StdDraw.setPenRadius(.005);
		StdDraw.square(.5, .5, .5);
		for(int row = 0; row < 8; row++){
			for(int col = 0; col < 8; col++){

				this.squares[col][row].draw();

			}
		}
	}

}

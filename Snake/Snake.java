import java.util.LinkedList;

public class Snake {

	// INSTANCE VARIABLES
	// a snake is simply a linked list of snakePieces
	private LinkedList<Piece> snake = new LinkedList<Piece>();

	// CONSTRUCTOR
	public Snake(LinkedList<Piece> ll){

		this.snake = ll;

	}

	// METHODS
	// draw
	public void draw(){
		for(int i = 0; i < this.snake.size(); i++){
			this.snake.get(i).draw();
		}
	}
	// move
	public void move(){
		for(int i = 0; i < this.snake.size(); i++){
			this.snake.get(i).move();
		}
	}

	// add a piece
	public void addPiece(){

		// create a new piece, based on the last piece of the list
		Piece last = this.snake.getLast();
		// info for the new piece
		double newX;
		double newY;
		// do the math
		char dir = last.getDirection();
		if(dir == 'u'){
			newX = last.getPosX();
			newY = last.getPosY() - 2*last.getRadius();
		}
		else if(dir == 'r'){
			newY = last.getPosY();
			newX = last.getPosX() - 2*last.getRadius();
		}
		else if(dir == 'd'){
			newX = last.getPosX();
			newY = last.getPosY() + 2*last.getRadius();
		}
		else{
			newY = last.getPosY();
			newX = last.getPosX() + 2*last.getRadius();
		}

		// create the new piece and add it to the list
		Piece newPiece = new Piece(newX, newY, last.getDirection());
		this.snake.addLast(newPiece);

	}

	// getters
	public LinkedList<Piece> getListOfPieces(){
		return this.snake;
	}
	public int size(){
		return this.snake.size();
	}

}

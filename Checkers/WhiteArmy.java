// the white pieces
public class WhiteArmy {

	// ARRAY OF WHITE PIECES
	private WhitePiece whites[];

	// CONSTRUCTOR
	public WhiteArmy(WhitePiece[] array){

		this.whites = array;

	}

	// OTHER METHODS
	public void draw(){

		for(int i = 0; i < 12; i++){
			this.whites[i].draw();
		}

	}

	public int numSoldiers(){

		int count = 0;
		for(int i = 0; i < 12; i++){

			if(this.whites[i].isAlive())
				count++;

		}
		return count;

	}


	public WhitePiece[] getTheArray(){
		return this.whites;
	}

	public boolean WhiteAlive(){

		for(int i = 0; i < 12; i++){

			if(this.whites[i].isAlive())
				return true;

		}
		return false;

	}

	public void checkLitness(){

		for(int i = 0; i < 12; i++){

			if(this.whites[i].getY() == 0)
				this.whites[i].makeLit();

		}

	}


}

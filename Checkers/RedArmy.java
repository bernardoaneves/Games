// the red pieces
public class RedArmy {

	// ARRAY OF RED PIECES
	private RedPiece reds[];
	
	// CONSTRUCTOR
	public RedArmy(RedPiece[] array){

		this.reds = array;

	}

	// OTHER METHODS
	public void draw(){

		for(int i = 0; i < 12; i++){
			this.reds[i].draw();
		}

	}

	public int numSoldiers(){

		int count = 0;
		for(int i = 0; i < 12; i++){

			if(this.reds[i].isAlive())
				count++;

		}
		return count;

	}

	public RedPiece[] getTheArray(){
		return this.reds;
	}

	public boolean RedAlive(){

		for(int i = 0; i < 12; i++){

			if(this.reds[i].isAlive())
				return true;

		}
		return false;

	}

	public void checkLitness(){

		for(int i = 0; i < 12; i++){

			if(this.reds[i].getY() == 7)
				this.reds[i].makeLit();

		}

	}

}

import java.util.LinkedList;

public class TurnGrid {

	// INSTANCE VARIABLES
	LinkedList<TurningPoint> grid = new LinkedList<TurningPoint>();

	// CONSTRUCTOR
	public TurnGrid(LinkedList<TurningPoint> ll){

		this.grid = ll;

	}

	// METHODS
	// addTurningPoint
	public void addTurningPoint(TurningPoint tp){

		this.grid.addLast(tp);

	}
	// removeTurningPoint
	public void removeTurningPoint(TurningPoint tp){

		this.grid.remove(tp);

	}

	// getters
	public LinkedList<TurningPoint> getListOfTP(){
		return this.grid;
	}

}

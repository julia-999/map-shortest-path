
/**
 * This class finds the shortest path to the destination
 * 
 * @author Julia Anantchenko
 */
public class ShortestPath {

	/** The map on which to find the path */
	private CityMap cityMap;

	/**
	 * The constructor, initializes the map variable
	 * 
	 * @param theMap: the map to use
	 */
	public ShortestPath(CityMap theMap) {
		cityMap = theMap;
	}

	/**
	 * Finds the shortest path to the destination
	 */
	public void findShortestPath() {

		// declares/initializes variables
		int nextDistance;
		MapCell next, current = null;

		// creates an ordered circular array and inserts the starting cell
		OrderedCircularArray<MapCell> list = new OrderedCircularArray<MapCell>();
		list.insert(cityMap.getStart(), 0);
		cityMap.getStart().isMarkedInList();

		// while loop that goes on until the list is empty, will also break out if destination is found
		while (!list.isEmpty()) { 

			// takes the current cell out of the list and checks if it's the destination
			current = list.getSmallest();
			current.markOutList();
			if (current.isDestination())
				break;

			// loops through the neighboring cells
			for (int i = 0; i < 4; i++) {
				next = nextCell(current);

				// checks that the neighboring cell is valid
				if(next != null) {

					// compares the distance to start of the next cell to what the calculated distance is, updates to get the lowest value
					nextDistance = 1 + current.getDistanceToStart();
					if (next.getDistanceToStart() > nextDistance) {
						next.setDistanceToStart(nextDistance);

						// updates the path to this next cell with the shorter one
						next.setPredecessor(current);
					}

					// updates the nextDistance variable with the appropriate distance to start
					nextDistance = next.getDistanceToStart();

					// inserts the cell with the correct distance to start
					list.insert(next, nextDistance);
					next.markInList();					
				}
			}
		}

		// checks if the destination was reached and prints the appropriate message
		if (current.isDestination())
			System.out.println("Path found containing " + (current.getDistanceToStart()+1) + " cells.");
		else
			System.out.println("Path not found.");

	}

	/**
	 * Returns the first unmarked neighboring map cell that can be used to continue the path
	 * 
	 * @param cell: the original cell
	 * @return the next cell
	 */
	private MapCell nextCell(MapCell cell) {

		// checks if it's north, east, west or south roads
		for (int i = 0; i < 4; i++) {
			// creates new cells based on the 0-3 indexes in the for loop
			MapCell next = cell.getNeighbour(i);

			// checks the new cell and original cell for conditions that would make it eligible to be a path
			if (next != null && !next.isMarked()) {

				// cells that are either the start or an intersection
				if (cell.isStart() || cell.isIntersection()) {

					if (next.isIntersection() || next.isDestination()) 
						return next;
					if (i==0 && next.isNorthRoad())
						return next;
					if (i==1 && next.isEastRoad())
						return next;
					if (i==2 && next.isSouthRoad())
						return next;
					if (i==3 && next.isWestRoad())
						return next;
				}

				// cells that are north, east, south or west roads
				if (cell.isNorthRoad() && i==0 && (next.isNorthRoad() || next.isIntersection() || next.isDestination()))
					return next;
				if (cell.isEastRoad() && i==1 && (next.isEastRoad() || next.isIntersection() || next.isDestination())) 
					return next;
				if (cell.isSouthRoad() && i==2 && (next.isSouthRoad() || next.isIntersection() || next.isDestination())) 
					return next;
				if (cell.isWestRoad() && i==3 && (next.isWestRoad() || next.isIntersection() || next.isDestination())) 
					return next;
			}
		}
		// returns null if no cell is a possible option
		return null;
	}

}

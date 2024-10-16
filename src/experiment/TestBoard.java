package experiment;

import java.util.HashSet;
import java.util.Set;

/* TestBoard class - contains our board for testing
 * 
 * @Author: Carson D.
 * @Author: Charlie D.
 * 
 * @Date: 2/24/2024
 * 
 */

public class TestBoard {

	private Set<TestBoardCell> targets = new HashSet<TestBoardCell>();;
	private Set<TestBoardCell> boardCells = new HashSet<TestBoardCell>();
	private TestBoardCell test;

	// C14A-1 variables
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> visited = new HashSet<TestBoardCell>();
	final static int COLS = 4;
	final static int ROWS = 4;

	// Temp Vars
	private TestBoardCell temp;

	// TestBoard Constructor
	public TestBoard() {
		// Intializes and fills the grid with empty cells
		grid = new TestBoardCell[ROWS][COLS];
		for (int y = 0; y < COLS; y++) {
			for (int x = 0; x < ROWS; x++) {
				temp = new TestBoardCell(x,y);
				grid[x][y] = temp;
				boardCells.add(temp);
			}
		}
		// Generates each cells adjacency lists (Attempted to do in a switch/case but couldn't get it to compile)
		for (int y = 0; y < COLS; y++) {
			for (int x = 0; x < ROWS; x++) {
				test = getCell(x, y);
				temp = getCell(x + 1, y); // Tests adjacent cell to the right
				if (temp != null) {
					if (temp.isOccupied() == false && temp.isRoom() == false) {
						test.addAdjacency(temp);
					}
				}
				temp = getCell(x - 1, y); // Tests adjacent cell to the left
				if (temp != null) {
					if (temp.isOccupied() == false && temp.isRoom() == false) {
						test.addAdjacency(temp);
					}
				}
				temp = getCell(x, y + 1); // Tests adjacent cell above
				if (temp != null) {
					if (temp.isOccupied() == false && temp.isRoom() == false) {
						test.addAdjacency(temp);
					}
				}
				temp = getCell(x, y - 1); // Tests adjacent cell below
				if (temp != null) {
					if (temp.isOccupied() == false && temp.isRoom() == false) {
						test.addAdjacency(temp);
					}
				}
			}
		}
	}
	
	 public void calcTargets(TestBoardCell startCell, int pathLength) {
		 visited.add(startCell);
		 
		 // iterate through every cell of startCell adjacent list
		 for (TestBoardCell cell: startCell.getAdjList()) {
			 // if already visited/isRoom/isOccupied, skip this cell
			 if (visited.contains(cell) || cell.isOccupied() || cell.isRoom()) continue;
			 
			 // add cell to visited list
			 visited.add(cell);
			 
			 // add cell to targets if out of moves
			 if (pathLength == 1) {
				 targets.add(cell);
			 }
			 // recursive call if moves left
			 else {
				 this.calcTargets(cell, pathLength - 1);
			 }
			 
			 visited.remove(cell);
		 }
	 }
	 	
	// getCell: returns cell given row+column. If cell doesn't exist, returns null
	public TestBoardCell getCell(int row, int col) {
		// iterate through each cell in targets list
		for (TestBoardCell cell : boardCells) {
			if (cell.getColumn() == col && cell.getRow() == row) {
				return cell;
			}
		}
		// if no cell returned, return null
		return null;
	}

	public Set<TestBoardCell> getTargets() {
		return targets;
	}
}

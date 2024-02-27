package experiment;
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
	
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> boardCells;
	private TestBoardCell test;
	
	// C14A-1 variables
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> visited;
	final static int COLS = 4;
	final static int ROWS = 4;
	
	 public TestBoard() {

	 }
	 
	 public void calcTargets(TestBoardCell startCell, int pathLength) {
		 // iterate through every cell of startCell adjacent list
		 for (TestBoardCell cell: startCell.getAdjList()) {
			 // if already visited, skip this cell
			 if (visited.contains(cell)) continue;
			 
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
		for (TestBoardCell cell: boardCells) {
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

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
	 
	 public void calcTargets(TestBoardCell startCell, int pathlength) {
		 
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

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
	
	// constructor: empty for now
	public TestBoard() {
		
	}
	
	// calcTargets: empty for now
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

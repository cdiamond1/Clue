package experiment;
import java.util.Set;

/* TestBoard class - contains our board for testing
 * 
 * @Author: Carson D.
 * @Author: Charlie D.
 * 
 * @Date:
 * 
 */

public class TestBoard {
	
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> boardCells;
	private TestBoardCell test;
	
	 public TestBoard() {
		 
	 }
	 
	 public void calcTargets(TestBoardCell startCell, int pathlength) {
		 
	 }
	 
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

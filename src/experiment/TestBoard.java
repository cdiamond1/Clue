package experiment;
import java.util.Set;

/* TestBoard class - contains our board for testing
 * 
 * @Author: Carson D.
 * @Author:
 * 
 * @Date:
 * 
 */

public class TestBoard {
	
	private Set<TestBoardCell> targets;
	private TestBoardCell test;
	
	 public TestBoard() {
		 
	 }
	 
	 public void calcTargets(TestBoardCell startCell, int pathlength) {
		 
	 }
	 
	 public TestBoardCell getCell(int row, int col) {
		 // idk how this is supposed to work, somehow get cell via row/column
		 return test;
	 }
	 
	 public Set<TestBoardCell> getTargets(){
		 return targets;
	 }
	 
}

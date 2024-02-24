package experiment;
import java.util.Set;

/* TestBoardCell class - contains our board cells for testing
 * 
 * @Author: Carson D.
 * @Author:
 * 
 * @Date: 2/24/2024
 * 
 */

public class TestBoardCell {
	
	private int row, column;
	private boolean isOccupied = false;
	private boolean isRoom = false;
	private Set<TestBoardCell> adjList;
	
	public TestBoardCell(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public Set<TestBoardCell> getAdjList() {
		return adjList;
	}
	
	public void addAdjacency(TestBoardCell cell) {
		adjList.add(cell);
	}

	public boolean isRoom() {
		return isRoom;
	}

	public void setRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}

	public boolean isOccupied() {
		return isOccupied;
	}

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	
}

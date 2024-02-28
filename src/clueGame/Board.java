package clueGame;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* Board class - contains our board
 * 
 * @Author: Carson D.
 * @Author: Charlie D.
 * 
 * @Date: 2/27/2024
 * 
 */

public class Board {

	private Set<BoardCell> targets = new HashSet<BoardCell>();;
	private Set<BoardCell> boardCells = new HashSet<BoardCell>();
	private BoardCell test;

	// C14A-1 additional variables
	private BoardCell[][] grid;
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	final static int COLS = 4;
	final static int ROWS = 4;

	// C14A-2 additional variables
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
	private static Board theInsrance;
	
	// Temp Vars
	private BoardCell temp;

	// Board Constructor
	public Board() {
		// Intializes and fills the grid with empty cells
		grid = new BoardCell[ROWS][COLS];
		for (int y = 0; y < COLS; y++) {
			for (int x = 0; x < ROWS; x++) {
				temp = new BoardCell(x, y);
				grid[x][y] = temp;
				boardCells.add(temp);
			}
		}
		// Generates each cells adjacency lists (Attempted to do in a switch/case but
		// couldn't get it to compile)
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

	public void calcTargets(BoardCell startCell, int pathLength) {
		visited.add(startCell);

		// iterate through every cell of startCell adjacent list
		for (BoardCell cell : startCell.getAdjList()) {
			// if already visited/isRoom/isOccupied, skip this cell
			if (visited.contains(cell) || cell.isOccupied() || cell.isRoom())
				continue;

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
	public BoardCell getCell(int row, int col) {
		// iterate through each cell in targets list
		for (BoardCell cell : boardCells) {
			if (cell.getColumn() == col && cell.getRow() == row) {
				return cell;
			}
		}
		// if no cell returned, return null
		return null;
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	public void initialize() {
		
	}
	
	public void loadConfigSetup() {
		
	}
	
	public void loadLayoutSetup() {
		
	}
	
}

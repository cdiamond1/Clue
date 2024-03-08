package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
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
	public BoardCell[][] grid;
	private Set<BoardCell> visited = new HashSet<BoardCell>();

	private static int boardCols = 30;
	private static int boardRows = 30;

	// C14A-2 additional variables
	private String initial;
	private Map<Character, Room> roomMap = new HashMap<Character, Room>();
	private static Board theInsrance;

	private File csv;
	private File txt;

	/*
	 * variable and methods used for singleton pattern
	 */
	private static Board theInstance = new Board();

	// constructor is private to ensure only one can be created
	private Board() {
		super();
	}

	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	/*
	 * initialize the board (since we are using singleton pattern)
	 */
	public void initialize() {
		try {
			loadSetupConfig();
			loadLayoutConfig();
			addDoorways();
			calcAdjacencyList();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
	}

	public void setConfigFiles(String string, String string2) {
		csv = new File(string);
		txt = new File(string2);
	}

	// readData gets passed a file name, and returns a 2D array of the
	// information in the file
	public ArrayList<String[]> readData(File file) throws BadConfigFormatException {
		ArrayList<String[]> dataList = new ArrayList<>();

		// read file
		try {
			Scanner scanner = new Scanner(file);

			boardRows = 0;
			String currLine;
			// get lines from file one at a time - split each line at comma
			while (scanner.hasNextLine()) {
				// add all file data to string
				currLine = scanner.nextLine();
				dataList.add(currLine.split(","));

				// check configuration
				for (String item : dataList.get(boardRows)) {
					if (item.equals("") || item.equals(" ") || item.equals(null)) {
						throw new BadConfigFormatException();
					}
				}

				boardRows++;
			}

			boardCols = dataList.get(0).length;

			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataList;
	}

	// Loads setup file, populating roomMap
	public void loadSetupConfig() throws BadConfigFormatException {
		Scanner in = null;
		String line;
		String[] lineSplit = new String[3];

		// read file
		try {
			// move global variables into methods if possible
			in = new Scanner(txt);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (in.hasNextLine()) {
			line = in.nextLine();

			// skip comments
			if (line.charAt(0) == '/') {
				continue;
			}

			// split at comma
			lineSplit = line.split(", ");
			if ((lineSplit[0].equals("Room") || lineSplit[0].equals("Space")) && lineSplit.length == 3) {
				// create room object
				Room temp = new Room(lineSplit[1]);
				roomMap.put(lineSplit[2].charAt(0), temp);

			} else {
				throw new BadConfigFormatException("Format error in setup file: " + lineSplit[0]);
			}
		}
		in.close();
	}

	// loads game board layout
	public void loadLayoutConfig() throws BadConfigFormatException {
		ArrayList<String[]> dataList = new ArrayList<>();
		BoardCell temp; // Temp var

		// read data from file
		dataList = readData(csv);

		// create grid with proper size
		grid = new BoardCell[boardRows][boardCols];

		// iterate through every cell in game board
		for (int row = 0; row < boardRows; row++) {
			for (int col = 0; col < boardCols; col++) {
				initial = dataList.get(row)[col];
				// make temp variables in the method they are used in - it generally bad to have
				// global variables that can be moved into methods
				temp = new BoardCell(row, col, initial);

				// make initial.charAt(0) a variable for readibility, can also use a switch
				// statement
				if (initial.charAt(0) == 'X') {
					temp.setRoomLoc(roomMap.get('X'));
					temp.setRoom(false);
					temp.setOccupied(true);
				} else if (initial.charAt(0) == 'W') {
					temp.setRoomLoc(roomMap.get('W'));
					temp.setRoom(true);
				} else if (roomMap.get(initial.charAt(0)) != null) {
					temp.setRoomLoc(roomMap.get(initial.charAt(0)));
					temp.setRoom(true);
				} else {
					throw new BadConfigFormatException("Bad room symbol: " + initial.charAt(0));
				}

				// Reading second char if there is one
				if (initial.length() > 1) {					
					switch (initial.charAt(1)) {
					case '<':
						temp.setDoorway(true);
						temp.setDoorDirection(DoorDirection.LEFT);
						break;
					case '^':
						temp.setDoorway(true);
						temp.setDoorDirection(DoorDirection.UP);
						break;
					case '>':
						temp.setDoorway(true);
						temp.setDoorDirection(DoorDirection.RIGHT);
						break;
					case 'v':
						temp.setDoorway(true);
						temp.setDoorDirection(DoorDirection.DOWN);
						break;
					case '*':
						temp.setRoomCenter(true);
						temp.getRoom().setCenterCell(temp);
						break;
					case '#':
						temp.setRoomLabel(true);
						temp.getRoom().setLabelCell(temp);
						break;
					default:
						temp.setSecretPassage(initial.charAt(1));
						
						break;
					}
				}

				// rename temp to be more descriptive
				grid[row][col] = temp;
				boardCells.add(temp);
			}
		}
	}
	
	// iterate through every cell and add doorways to their associated room 
	public void addDoorways() {
		BoardCell currCell = new BoardCell(0, 0, null);
		BoardCell currTargetCell = new BoardCell(0, 0, null);
		Room currRoom = new Room();
		Room adjRoom = new Room();
		
		// iterate through each row
		for (int row = 0; row < boardRows; row++) {
		for (int col = 0; col < boardCols; col++) {
			currCell = getCell(row, col);
			
			// skip cells that aren't doorways
			if (currCell.isDoorway()) {
				// get direction
				switch (currCell.getDoorDirection()) {
				case LEFT:
					currTargetCell = getCell(row, col - 1);
					break;
				case RIGHT:
					currTargetCell = getCell(row, col + 1);
					break;
				case UP:
					currTargetCell = getCell(row + 1, col);
					break;
				case DOWN:
					currTargetCell = getCell(row - 1, col);
					break;
				default:
					continue;
				}
				
				adjRoom = currTargetCell.getRoom();
				// skip walkways
				if (adjRoom.getName().equals("Walkway")) continue;
				adjRoom.addDoorCell(currCell);
				
			} 
			else if (currCell.isSecretPassage()) {
				currRoom = roomMap.get(currCell.getSymbol().charAt(0));
				adjRoom = roomMap.get(currCell.getSecretPassage());
				
				currTargetCell = adjRoom.getCenterCell();
				currRoom.addDoorCell(currTargetCell);
			}
			else {
				continue;
			}
			
			
		}
		}
	}
	
	public void calcAdjacencyList() {
		BoardCell currCell = new BoardCell(0, 0, null);
		BoardCell temp = new BoardCell(0, 0, null);
		
		// iterate through every cell
		for (int y = 0; y < boardRows; y++) {
		for (int x = 0; x < boardCols; x++) {
			currCell = getCell(y, x);
			
			if (currCell.isRoomCenter()) {
				for (BoardCell cell: currCell.getRoom().getDoors()) {
					currCell.addAdjacency(cell);
				}
			}
			else {
				if (x + 1 < boardCols) {
					temp = getCell(y, x + 1); // Tests adjacent cell to the right
					if (temp != null && !temp.isOccupied() && temp.getSymbol().contains("W") ) {
						currCell.addAdjacency(temp);
					}
				}
	
				if (x - 1 >= 0) {
					temp = getCell(y, x - 1); // Tests adjacent cell to the left
					if (temp != null && !temp.isOccupied() && temp.getSymbol().contains("W") ) {
						currCell.addAdjacency(temp);
					}
				}
	
				if (y + 1 < boardRows) {
					temp = getCell(y + 1, x); // Tests adjacent cell above
					if (temp != null && !temp.isOccupied() && temp.getSymbol().contains("W") ) {
						currCell.addAdjacency(temp);
					}
				}
	
				if (y - 1 >= 0) {
					temp = getCell(y - 1, x); // Tests adjacent cell below
					if (temp != null && !temp.isOccupied() && temp.getSymbol().contains("W") ) {
						currCell.addAdjacency(temp);
					}
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
		return grid[row][col];
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

	public Room getRoom(char c) {
		return roomMap.get(c);
	}

	public Room getRoom(BoardCell cell) {
		return cell.getRoom();
	}

	public int getNumRows() {
		return boardRows;
	}

	public int getNumColumns() {
		return boardCols;
	}

	public Set<BoardCell> getAdjList(int row, int col) {
		return getCell(row, col).getAdjList();
	}

}

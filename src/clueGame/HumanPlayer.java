package clueGame;

import java.awt.Color;
import java.util.ArrayList;

/*
 * HumanPlayer Class. Extends Player class. contains methods
 * for human player
 *  
 * @author Carson Diamond
 * @author Charlie Dupras
 * @date 3/25/2024
 */

public class HumanPlayer extends Player{
	
	public HumanPlayer(String name) {
		super(name);
	}
	
	public HumanPlayer(String name, int row, int col, Color color) {
		super(name, row, col, color);
	}
	
	public HumanPlayer(String name, int row, int col, String color) {
		super(name, row, col, color);
	}

	@Override
	public boolean isHuman() {
		return true;
	}

	
	//		These methods should not be called for human players
	@Override
	public BoardCell selectTarget(int roll, int newRow, int newCol) {
		return null;
	}
	@Override
	public BoardCell selectTarget(int roll) {
		return null;
	}

	@Override
	public Solution createSuggestion() {
		return null;
	}

	@Override
	public void createAccusation(Card room, Card person, Card weapon) {
		return;
		
	}

	@Override
	public Solution getAccusation() {
		return null;
	}
	
}

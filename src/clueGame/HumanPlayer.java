package clueGame;

import java.awt.Color;
import java.util.ArrayList;

/*
 * HumanPlayer Class - 
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
	
}

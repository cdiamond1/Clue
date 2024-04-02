package clueGame;

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

	@Override
	public boolean isHuman() {
		return true;
	}
	
}

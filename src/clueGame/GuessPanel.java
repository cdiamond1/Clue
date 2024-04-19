package clueGame;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuessPanel extends JDialog {
	private static Board board = Board.getInstance();
	private JFrame frame = new JFrame();
	private JLabel roomText = new JLabel();
	private JLabel personText = new JLabel("   Person");
	private JLabel weaponText = new JLabel("   Weapon");

	private JButton submitButton = new JButton("Submit");
	private JButton cancelButton = new JButton("Cancel");
	
	private boolean correct;
	private boolean suggestionCorrect;
	private Solution suggestion;
	private Card tempCard = null;

	public GuessPanel() {
		JDialog panel = new JDialog(frame, "Accuse", true);
		panel.setTitle("Accuse");
		panel.setModal(true);
		panel.setLayout(new GridLayout(4, 2));
		panel.setResizable(false);
		panel.setSize(300, 300);

		roomText.setText("   Room");

		JComboBox<String> roomList = new JComboBox<String>();
		for (Card C : board.getRoomsList()) {
			roomList.addItem(C.getCardName());
		}

		JComboBox<String> playerList = new JComboBox<String>();
		for (Card C : board.getPlayerCardList()) {
			playerList.addItem(C.getCardName());
		}

		JComboBox<String> weaponList = new JComboBox<String>();
		for (Card C : board.getWeaponsList()) {
			weaponList.addItem(C.getCardName());
		}
		
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				suggestion = new Solution(board.getRoomsList().get(roomList.getSelectedIndex()), board.getPlayerCardList().get(playerList.getSelectedIndex()), board.getWeaponsList().get(weaponList.getSelectedIndex()));
				if(board.checkAccusation(suggestion)) {
					correct = true;
				} else {
					correct = false;
				}
				panel.dispose();
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.dispose();

			}
		});

		panel.add(roomText);
		panel.add(roomList);
		panel.add(personText);
		panel.add(playerList);
		panel.add(weaponText);
		panel.add(weaponList);
		panel.add(submitButton);
		panel.add(cancelButton);

		panel.setVisible(true);
	}

	public GuessPanel(Room room, Player currPlayer) {
		JDialog panel = new JDialog();
		panel.setTitle("Suggest");
		panel.setModal(true);
		panel.setLayout(new GridLayout(4, 2));
		panel.setResizable(false);
		panel.setSize(300, 300);		

		roomText.setText("   Current Room");

		JLabel currentRoom = new JLabel(room.getName());
		currentRoom.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		JComboBox<String> playerList = new JComboBox<String>();
		for (Card C : board.getPlayerCardList()) {
			playerList.addItem(C.getCardName());
		}

		JComboBox<String> weaponList = new JComboBox<String>();
		for (Card C : board.getWeaponsList()) {
			weaponList.addItem(C.getCardName());
		}

		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(Card C : board.getRoomsList()) {
					if(C.getCardName() == room.getName()) {
						tempCard = C;
					}
				}
				suggestion = new Solution(tempCard, board.getPlayerCardList().get(playerList.getSelectedIndex()), board.getWeaponsList().get(weaponList.getSelectedIndex()));
				tempCard = board.handleSuggestion(suggestion, currPlayer);
				if(tempCard != null && !(currPlayer.getHand().contains(tempCard))) {
					currPlayer.updateSeen(tempCard);
					suggestionCorrect = false;
				} else {
					suggestionCorrect = true;
				}
				panel.dispose();
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.dispose();

			}
		});
		
		panel.add(roomText);
		panel.add(currentRoom);
		panel.add(personText);
		panel.add(playerList);
		panel.add(weaponText);
		panel.add(weaponList);
		panel.add(submitButton);
		panel.add(cancelButton);

		panel.setVisible(true);
	}

	public boolean isAccusationRight() {
		return correct;
	}

	public boolean isSuggestionRight() {
		return suggestionCorrect;
	}
	
	public Solution getSuggestion() {
		return suggestion;
	}
	
	public Card getNewCard() {
		return tempCard;
	}

		
	public static void main(String[] args) {

	}
}

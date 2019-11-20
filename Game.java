/* 017 Data Structures
 * Part A
 * Nektarios Mastoras			Eirini Michaltsi
 * 9808							9736
 * 6948692024					6980780734
 * nmastoras@ece.auth.gr		Michalts@ece.auth.gr
 * 
 * This class is used to access Game objects.
 * The methods of this class are its constructors which are used to create Game objects, its getters which are used to access an object's 
 * variables and its setters which are used to change an object's variables.
 * In this class objects from all the other classes are initialized so that the game can be played. Moreover the main method is used in this class.
 * The board is created with data given in the instructions of the project. 
 * The players are initialized on opposite sides of the board as given in the instructions and with other data to complete their information.
 * Methods of other classes are used so the game can go forward: The players move, the board gets smaller.
 * The checks happening in this class are to either finish the game and find out who won, or check if it is an eligible round for the board to shrink.
 * In addition to the checks, messages and the board are also printed out in each turn for the user to be informed about the current state of the game.
 */	
public class Game {
	int round;
	// Constructors of the CLASS
	public Game(int round) {
		this.round = round;
	}

	public Game(Game game) {
		this.round = game.round;
	}
	// Variable's getters
	public int getRound() {
		return round;
	}
	// Variable's setters
	public void setRound(int rou) {
		this.round = rou;
	}

	public static void main(String[] args) {
		Game game = new Game(1);
		Board board = new Board(20, 20, 6, 10, 8); // Initializing the board as a 20x20 map with 6 Weapons, 10 Food and  8 Traps
		board.createBoard();
		Player player1 = new Player(0, "mNektar", board, 0, -10, -10, null, null, null); // Initializing the 2 players
		Player player2 = new Player(1, "Aspirenie", board, 0, 10, 10, null, null, null);
		System.out.println("Let the games begin!");
		System.out.println("May the odds be ever in your favor!");	// Message that wishes the players good luck. They are going to need it!
		do {
			System.out.println("This is round No." + String.valueOf(game.getRound()));	// Message to show the user in which round the game is
			player1.move(player1, board);	// Moving the players across the board
			player2.move(player2, board);
			if (game.getRound() % 3 == 0)	// Every 3 rounds the board shrinks 
				board.resizeBoard(player1, player2);
			String[][] arr = board.getStringRepresentation();	// Every round the board is being printed
			for (int i = 0; i < board.getN(); i++) {
				for (int j = 0; j < board.getM(); j++)
					System.out.print(arr[i][j] + " ");
				System.out.print("\n");
			}
			game.round++;
			System.out.println(player1.getName() + " has " + player1.getScore() + " points.");	// Messages to show the user how many points each player has
			System.out.println(player2.getName() + " has " + player2.getScore() + " points. \n");
		} while ((board.getN() > 4) && (board.getM() > 4));
		System.out.println("Game Over!");
		if (player1.getScore() > player2.getScore())		// Messages to show the user which player has won
			System.out.println(player1.getName() + " has won! He has made his district proud!");
		else if (player1.getScore() < player2.getScore())
			System.out.println(player2.getName() + " has won! She has made her district proud!");
		else
			System.out.println("It's a tie! What a phenomenal game it has been!");
	}
}
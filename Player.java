/* This class is used to access Player objects.
 * The methods of this class are its constructors which are used to create Player objects, its getters which are used to access an object's 
 * variables and its setters which are used to change an object's variables.
 * Other methods used in this class are: 
 * getRandomMove() 	that checks if the player is eligible to move to a certain location and will not move outside the board with a move 
 * 					and returns the displacement of the player and which move the player performed according to the die (1 - 8)
 * move() 			that takes the data returned by the getRandomMove() method, moves the player according to them, checks if the player
 * 					after being moved is on a tile of interest (Weapon, Food, Trap) and prints out the appropriate message. 
 */
 
import java.util.Random;

public class Player {
	int id; 	// Player's ID
	String name;// Player's Name
	Board board;
	int score; 	// Player's Score
	int x; 		// Player's x-coordinate on the board
	int y; 		// Player's y-coordinate on the board
	Weapon bow;
	Weapon pistol;
	Weapon sword;
	// Constructors of the CLASS
	public Player(Player player) {
		this.id = player.id;
		this.name = player.name;
		this.board = player.board;
		this.score = player.score;
		this.x = player.x;
		this.y = player.y;
		this.bow = player.bow;
		this.pistol = player.pistol;
		this.sword = player.sword;
	}

	public Player(int id, String name, Board board, int score, int x, int y, Weapon bow, Weapon pistol, Weapon sword) {
		this.id = id;
		this.name = name;
		this.board = board;
		this.score = score;
		this.x = x;
		this.y = y;
		this.bow = bow;
		this.pistol = pistol;
		this.sword = sword;
	}
	// Variable's getters
	public int getId() {
		return id;
	} 

	public String getName() {
		return name;
	}

	public Board getBoard() {
		return board;
	}

	public int getScore() {
		return score;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Weapon getBow() {
		return bow;
	}

	public Weapon getPistol() {
		return pistol;
	}

	public Weapon getSword() {
		return sword;
	}
	// Variable's setters
	public void setId(int pid) {
		this.id = pid;
	} 

	public void setName(String pname) {
		this.name = pname;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setBow(Weapon weaponbow) {
		this.bow = weaponbow;
	}

	public void setPistol(Weapon weaponpistol) {
		this.pistol = weaponpistol;
	}

	public void setSword(Weapon weaponsword) {
		this.sword = weaponsword;
	}

	Random rand = new Random();
	
	int[] getRandomMove() { // FUNCTION that returns the change of the player's movement in the x-y axis
		int a = 0, b = 0, c = 0;	// The variables a and b indicate the change in the directions of x and y respectively. The variable c indicates the change in direction in words so it can be printed out as a message
		do { // The die is rolled in a loop as the players can roll a number which will move them outside of the board's borders
			int die = rand.nextInt(8) + 1; // Our die can take values between 1 and 8. Each value corresponds to a change in either the x-axis (a), the y-axis (b), or both.
			switch (die) {
			case 1:
				a = 0;
				b = -1;
				c = 1;
				break;
			case 2:
				a = 1;
				b = -1;
				c = 2;
				break;
			case 3:
				a = 1;
				b = 0;
				c = 3;
				break;
			case 4:
				a = 1;
				b = 1;
				c = 4;
				break;
			case 5:
				a = 0;
				b = 1;
				c = 5;
				break;
			case 6:
				a = -1;
				b = 1;
				c = 6;
				break;
			case 7:
				a = -1;
				b = 0;
				c = 7;
				break;
			case 8:
				a = -1;
				b = -1;
				c = 8;
				break;
			}
		} while ((Math.abs(getX() + a) > board.getN() / 2) || (Math.abs(getY() + b) > board.getM() / 2));
		int[] arr = { a, b , c };
		return arr;
	}

	int[] move(Player player, Board board) { // FUNCTION that moves the player around the board making the necessary checks for food, traps and weapons
		int[] temp = getRandomMove(); // Assign the random and valid values of change in position in an array to be checked later on
		if ((player.getX() == 1) && (temp[0] == -1)) 		// Since the points (0, y) and (x, 0) cannot be represented with the x-y axis given,
			player.setX(player.getX() + temp[0] - 1); 		// these if-statements exist to check if the player's move would be in those lines.
		else if ((player.getX() == -1) && (temp[0] == 1)) 	// These checks happen for the points (1, y), (-1, y), (x, 1) and (x, -1)
			player.setX(player.getX() + temp[0] + 1);		// E.g. Player's die roll is 1 and Player's current position on the board (3, 1)
		else												// the player would now move on the point (3, -1).
			player.setX(player.getX() + temp[0]); 
		if ((player.getY() == 1) && (temp[1] == -1))
			player.setY(player.getY() + temp[1] - 1);
		else if ((player.getY() == -1) && (temp[1] == 1))
			player.setY(player.getY() + temp[1] + 1);
		else
			player.setY(player.getY() + temp[1]);
		String msg = "";
		switch (temp[2]) {
		case 1:
			msg = "North";
			break;
		case 2:
			msg = "Northeast";
			break;
		case 3:
			msg = "East";
			break;
		case 4:
			msg = "Southeast";
			break;
		case 5:
			msg = "South";
			break;
		case 6:
			msg = "Southwest";
			break;
		case 7:
			msg = "West";
			break;
		case 8:
			msg = "Northwest";
			break;
		}
		System.out.println(player.getName() + " has moved to the " + msg + " and their coordinates are (" + player.getX() + ", " + player.getY() + ")");	// Message to let the user know about the movement of the player
		int weaponCounter = 0, foodCounter = 0, trapCounter = 0; // These counters will be some of the returning values of the FUNCTION move and will increase each time a player moves to a place of interest
		for (int i = 0; i < board.getW(); i++)
			if ((board.weapons[i].getX() == player.getX()) && (board.weapons[i].getY() == player.getY()) && (board.weapons[i].getPlayerId() == player.getId())) { // Checks if the player landed on a weapon AND if the weapon is meant for this player
				switch (board.weapons[i].getId() % 3) { // The same kind of switch is used in CLASS Board, FUNCTION createRandomWeapon to distinguish the different types of weapons from each other
				case 0:
					player.setPistol(board.weapons[i]);
					break;
				case 1:
					player.setSword(board.weapons[i]);
					break;
				case 2:
					player.setBow(board.weapons[i]);
					break;
				}
				System.out.println(player.getName() + " is now armed and dangerous. " + board.weapons[i].getType() + " added."); // Prints out information about the player
				board.weapons[i].setX(0); // The weapon is in the player's position so it no longer exists on the board
				board.weapons[i].setY(0); // Since no player can go to the point (0, 0) the useless weapon's new coordinates are these
				weaponCounter++; // The player has collected a weapon and the counter goes up
			}
		for (int i = 0; i < board.getF(); i++) // Loop that checks if the player's coordinates on the board are the same as a food's
			if ((board.food[i].getX() == player.getX()) && (board.food[i].getY() == player.getY())) {
				player.setScore(player.getScore() + board.food[i].getPoints()); // Add the food's score to the player's current score
				board.food[i].setX(0); // The food is now useless and "vanishes" from the board
				board.food[i].setY(0); // Since no player can go to the point (0, 0) the useless food's new coordinates are these
				foodCounter++; // The player has gathered food and the counter goes up
				System.out.println(player.getName() + " has gathered food."); // Prints out information about the player
			}
		for (int i = 0; i < board.getT(); i++) // Loop that checks if the player's coordinates on the board are the same as a trap's
			if ((board.traps[i].getX() == player.getX()) && (board.traps[i].getY() == player.getY())) {
				if ((board.traps[i].getType() == "Ropes" && player.getSword() == null) || (board.traps[i].getType() == "Animals" && player.getBow() == null)) {// Checks if the player has the kind of weapon to deal with the trap
					player.setScore(player.getScore() + board.traps[i].getPoints());// Add the trap's (negative) score to the player's current score
					System.out.println(player.getName() + " has been ambushed."); 	// Prints out information about the player
				}
				else System.out.println(player.getName() + " has been ambushed but had the necessary weapons to deal with the trap."); // Prints out information about the player
				board.traps[i].setX(0); // The trap is now useless and "vanishes" from the board
				board.traps[i].setY(0); // Since no player can go to the point (0, 0) the useless trap's new coordinates are these
				trapCounter++; // The player has been ambushed and the counter goes up
			}
		int[] arr = { player.getX(), player.getY(), weaponCounter, foodCounter, trapCounter }; // The array will return the players' new coordinates and the number of weapons, food and traps that were collected in their round
		return arr;
	}
}

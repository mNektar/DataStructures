import java.util.*;
public class HeuristicPlayer extends Player{
	ArrayList<int[]> path = new ArrayList<int[]>();;
	int r;
	// Constructors of the CLASS
	public HeuristicPlayer(int id, String name, Board board, int score, int x, int y, Weapon bow, Weapon pistol, Weapon sword) {
		super(id, name, board, score, x, y, bow, pistol, sword);
	}
	
	public HeuristicPlayer(HeuristicPlayer player) {
		super(player);
	}
	// Variables' getters
	public ArrayList<int[]> getPath() {
		return path;
	}
	
	public int getR() {
		return r;
	}
	// Variables' setters
	public void setPath(ArrayList<int[]> path) {
		this.path = path;
	}
	
	public void setR(int r) {
		this.r = r;
	}
	
	float playersDistance(Player p) {// FUNCTION that calculates and returns the distance between the two players 
		float d = (float)Math.sqrt((super.getX() - p.getX())*(super.getX() - p.getX()) + (super.getY() - p.getY())*(super.getY() - p.getY()));
		if (d > getR()) return -1;
		else return d;
	}
	
	double evaluate(int dice, Player p) {	// FUNCTION that evaluates the possible tiles the Heuristic player can move to and assigns them a specific score based on their importance to the player
		int tempX = 0, tempY = 0;
		int gainWeapon = 0, avoidTrap = 0, gainPoints = 0, forceKill = 0;
		switch(dice) {	// Create 2 temporary variables to store the coordinates of the Heuristic Player so that the original coordinates do not change
		case 1:  
			tempX = this.getX();
			tempY = this.getY() - 1;
			if (tempY == 0) tempY = -1;
			break;
		case 2:
			tempX = this.getX() + 1;
			tempY = this.getY() - 1;
			if (tempX == 0) tempX = 1;
			if (tempY == 0) tempY = -1;
			break;
		case 3:
			tempX = this.getX() + 1;
			tempY = this.getY();
			if (tempX == 0) tempX = 1;
			break;
		case 4:
			tempX = this.getX() + 1;
			tempY = this.getY() + 1;
			if (tempX == 0) tempX = 1;
			if (tempY == 0) tempY = 1;
			break;
		case 5:
			tempX = this.getX();
			tempY = this.getY() + 1;
			if (tempY == 0) tempY = 1;
			break;
		case 6:
			tempX = this.getX() - 1;
			tempY = this.getY() + 1;
			if (tempX == 0) tempX = -1;
			if (tempY == 0) tempY = 1;
			break;
		case 7:
			tempX = this.getX() - 1;
			tempY = this.getY();
			if (tempX == 0) tempX = -1;
			break;
		case 8:
			tempX = this.getX() - 1;
			tempY = this.getY() - 1;
			if (tempX == 0) tempX = -1;
			if (tempY == 0) tempY = -1;
			break;
		}
		for (int i = 0; i < board.getW(); i++) 	// Check if there is a weapon at the coordinates being checked 
			if (((board.weapons[i].getX() == tempX) && (board.weapons[i].getY() == tempY)) && (board.weapons[i].getPlayerId() == this.getId())) {
				if (board.weapons[i].getType() == "Pistol") gainWeapon = 10;	// Pistol gets more points than the other weapons because it can win the game
				else gainWeapon = 5;
			}
		for (int i = 0; i < board.getF(); i++)	// Check if there is a food at the coordinates being checked 
			if ((board.food[i].getX() == tempX) && (board.food[i].getY() == tempY))
				gainPoints = board.food[i].getPoints();
		for (int i = 0; i < board.getT(); i++)	// Check if there is a trap at the coordinates being checked  
			if ((board.traps[i].getX() == tempX) && (board.traps[i].getY() == tempY))
				if ((board.traps[i].getType() == "Ropes" && this.getSword() == null) || (board.traps[i].getType() == "Animals" && this.getBow() == null))
					avoidTrap = 0;	// Checks if the player has the weapon to deal with the trap. If so the tile is okay to move to
				else avoidTrap = board.traps[i].getPoints();
		double dist = Math.sqrt((tempX - p.getX())*(tempX - p.getX()) + (tempY - p.getY())*(tempY - p.getY()));	// Checks where the other player is
		if ((dist < 2) && (this.getPistol() != null)) forceKill = 100;		// If the Heuristic player has the Pistol then the move will surely kill the opponent ending the game
		else if ((dist >= 2) && (p.getPistol() != null)) forceKill = -100;	// If the random player has the Pistol then the move will surely kill the heuristic player so he tries to avoid the other player to avoid dying
		int loc = 0;	// This variable is used to evaluate each tile based on its coordinates. The further away the tile is from the center the smaller the value of this will be.
		if ((Math.abs(tempX) > 2) || (Math.abs(tempY) > 2)) 	// This is done in order to bring the Heuristic player closer to the center where all the food and weapons are.
			loc = 0 - Math.abs(tempX) - Math.abs(tempY);
		double f = gainWeapon * 0.5 + gainPoints * 0.4 + avoidTrap * 0.4 + forceKill + loc * 0.01; 
		return f;
	}
	
	boolean kill(Player player1, Player player2, float d) {	// FUNCTION that returns TRUE if one of the players meets the requirements to kill the other		
		if ((player1.pistol != null) && (d > playersDistance(player2)) && (playersDistance(player2) > 0)) return true;	// The requirements are having the pistol and the distance between them being smaller than the distance given in this FUNCTION as an argument
		else return false;
	}
	
	int[] move(Player p, int round){// FUNCTION that moves the Heuristic player to the tile with the greatest value
		Map<Integer, Double> values = new HashMap<Integer, Double>();
		double max = -100; int maxI = 0;	// Max variables to store the best available move
		for (int i = 1; i < 9; i++) 
			switch(i) {	// Inside in each case is checked if the potential move will move the player out of bounds. If not then the FUNCTION evaluate is called to give a value to the tile being checked.
			case 1:			
				if (getY() - 1 < board.getM() / 2) {
					values.put(i, evaluate(i, p));
					if (max < values.get(i)) {
						max = values.get(i);
						maxI = i;
					}
				}
				break;
			case 2:
				if ((getX() + 1 < board.getN() / 2) && (getY() - 1 < board.getM() / 2)) {
					values.put(i, evaluate(i, p));
					if (max < values.get(i)) {
						max = values.get(i);
						maxI = i;
					}
				}
				break;
			case 3:
				if (getX() + 1 < board.getN() / 2) {
					values.put(i, evaluate(i, p));
					if (max < values.get(i)) {
						max = values.get(i);
						maxI = i;
					}
				}
				break;
			case 4:
				if ((getX() + 1 < board.getN() / 2) && (getY() + 1 < board.getM() / 2)) {
					values.put(i, evaluate(i, p));
					if (max < values.get(i)) {
						max = values.get(i);
						maxI = i;
					}
				}
				break;
			case 5:
				if (getY() + 1 < board.getM() / 2) {
					values.put(i, evaluate(i, p));
					if (max < values.get(i)) {
						max = values.get(i);
						maxI = i;
					}
				}
				break;
			case 6:
				if ((getX() - 1 < board.getN() / 2) && (getY() + 1 < board.getM() / 2)) {
					values.put(i, evaluate(i, p));
					if (max < values.get(i)) {
						max = values.get(i);
						maxI = i;
					}
				}
				break;				
			case 7:
				if (getX() - 1 < board.getN() / 2) {
					values.put(i, evaluate(i, p));
					if (max < values.get(i)) {
						max = values.get(i);
						maxI = i;
					}
				}
				break;
			case 8:
				if ((getX() - 1 < board.getN() / 2) && (getY() - 1 < board.getM() / 2)) {
					values.put(i, evaluate(i, p));
					if (max < values.get(i)) {
						max = values.get(i);
						maxI = i;
					}
				}
				break;
			}
		switch(maxI) {	// This switch moves the Heuristic player to the tile where the FUNCTION evaluate() assigned the greatest value
		case 1:
			setY(getY() - 1);
			if (getY() == 0) setY(-1);
			break;
		case 2:
			setX(getX() + 1);
			setY(getY() - 1);
			if (this.getX() == 0) setX(1);
			if (this.getY() == 0) setY(-1);
			break;
		case 3:
			setX(getX() + 1);
			if (getX() == 0) setX(1);
			break;
		case 4:
			setX(getX() + 1);
			setY(getY() + 1);
			if (getX() == 0) setX(1);
			if (getY() == 0) setY(1);
			break;
		case 5:
			setY(getY() + 1);
			if (getY() == 0) setY(1);
			break;
		case 6:
			setX(getX() - 1);
			setY(getY() + 1);
			if (getX() == 0) setX(-1);
			if (getY() == 0) setY(1);
			break;
		case 7:
			setX(getX() - 1);
			if (getX() == 0) setX(-1);
			break;
		case 8:
			setX(getX() - 1);
			setY(getY() - 1);
			if (getX() == 0) setX(-1);
			if (getY() == 0) setY(-1);
			break;
		}
		int weaponCounter = 0, foodCounter = 0, trapCounter = 0;
		for (int i = 0; i < board.getW(); i++)
			if ((board.weapons[i].getX() == this.getX()) && (board.weapons[i].getY() == this.getY()) && (board.weapons[i].getPlayerId() == this.getId())) { // Checks if the player landed on a weapon AND if the weapon is meant for this player
				switch (board.weapons[i].getId() % 3) { // The same kind of switch is used in CLASS Board, FUNCTION createRandomWeapon to distinguish the different types of weapons from each other
				case 0:
					this.setPistol(board.weapons[i]);
					break;
				case 1:
					this.setSword(board.weapons[i]);
					break;
				case 2:
					this.setBow(board.weapons[i]);
					break;
				}
				board.weapons[i].setX(0); // The weapon is in the player's position so it no longer exists on the board
				board.weapons[i].setY(0); // Since no player can go to the point (0, 0) the useless weapon's new coordinates are these
				weaponCounter++;
			}
		for (int i = 0; i < board.getF(); i++) // Loop that checks if the player's coordinates on the board are the same as a food's
			if ((board.food[i].getX() == this.getX()) && (board.food[i].getY() == this.getY())) {
				this.setScore(this.getScore() + board.food[i].getPoints()); // Add the food's score to the player's current score
				board.food[i].setX(0); // The food is now useless and "vanishes" from the board
				board.food[i].setY(0); // Since no player can go to the point (0, 0) the useless food's new coordinates are these
				foodCounter++;
			}
		for (int i = 0; i < board.getT(); i++) // Loop that checks if the player's coordinates on the board are the same as a trap's
			if ((board.traps[i].getX() == this.getX()) && (board.traps[i].getY() == this.getY())) {
				if ((board.traps[i].getType() == "Ropes" && this.getSword() == null) || (board.traps[i].getType() == "Animals" && this.getBow() == null)) // Checks if the player has the kind of weapon to deal with the trap
					this.setScore(this.getScore() + board.traps[i].getPoints());// Add the trap's (negative) score to the player's current score
				else System.out.println(this.getName() + " has been ambushed but had the necessary weapons to deal with the trap."); // Prints out information about the player
				board.traps[i].setX(0); // The trap is now useless and "vanishes" from the board
				board.traps[i].setY(0); // Since no player can go to the point (0, 0) the useless trap's new coordinates are these
				trapCounter++;
			}
		int[] uselessInfo = { maxI, this.getScore(), weaponCounter, foodCounter, trapCounter }; 
		path.add(round, uselessInfo);
		int[] arr = { getX(), getY() };
		return arr;
	}
	
	void statistics(int round) {	// FUNCTION that prints out information about the Heuristic Player's actions in  the round given
		int[] arr = this.getPath().get(round);
		System.out.println(this.getName() + " in the round No." + round + " has chosen to make the move " + arr[0] + ", has collected " + arr[2] +  " weapons, has gathered " + arr[3] + " food, and has fallen in " + arr[4] + " traps.\nTheir new coordinates are (" + this.getX() + ", " + this.getY() +")");
		System.out.println(this.getName() + " has " + arr[1] + " points.");
	}
}

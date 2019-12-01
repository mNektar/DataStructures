import java.util.*;
public class HeuristicPlayer extends Player{
	ArrayList<int[]> path = new ArrayList<int[]>();;
	int r;
	public HeuristicPlayer(int id, String name, Board board, int score, int x, int y, Weapon bow, Weapon pistol, Weapon sword) {
		super(id, name, board, score, x, y, bow, pistol, sword);
	}
	
	public HeuristicPlayer(HeuristicPlayer player) {
		super(player);
	}
	
	public ArrayList<int[]> getPath() {
		return path;
	}
	
	public int getR() {
		return r;
	}
	
	public void setPath(ArrayList<int[]> path) {
		this.path = path;
	}
	
	public void setR(int r) {
		this.r = r;
	}
	
	float playersDistance(Player p) {
		float d = (float)Math.sqrt((super.getX() - p.getX())*(super.getX() - p.getX()) + (super.getY() - p.getY())*(super.getY() - p.getY()));
		if (d > getR()) return -1;
		else return d;
	}
	
	double evaluate(int dice, Player p) {
		int tempX = 0, tempY = 0;
		int gainWeapon = 0, avoidTrap = 0, gainPoints = 0, forceKill = 0;
		switch(dice) {
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
		for (int i = 0; i < board.getW(); i++) 
			if (((board.weapons[i].getX() == tempX) && (board.weapons[i].getY() == tempY)) && (board.weapons[i].getPlayerId() == this.getId())) {
				if (board.weapons[i].getType() == "Pistol") gainWeapon = 10;
				else gainWeapon = 5;
			}
		for (int i = 0; i < board.getF(); i++) 
			if ((board.food[i].getX() == tempX) && (board.food[i].getY() == tempY))
				gainPoints = board.food[i].getPoints();
		for (int i = 0; i < board.getT(); i++) 
			if ((board.traps[i].getX() == tempX) && (board.traps[i].getY() == tempY))
				if ((board.traps[i].getType() == "Ropes" && this.getSword() == null) || (board.traps[i].getType() == "Animals" && this.getBow() == null))
					avoidTrap = 0;
				else avoidTrap = board.traps[i].getPoints();		
		double dist = Math.sqrt((tempX - p.getX())*(tempX - p.getX()) + (tempY - p.getY())*(tempY - p.getY())); 
		if ((dist < 2) && (this.pistol != null)) forceKill = 100;
		else if ((dist >= 2) && (p.pistol != null)) forceKill = -100;
		int loc = 0;
		if ((Math.abs(tempX) > 2) || (Math.abs(tempY) > 2)) 
			loc = 0 - Math.abs(tempX) - Math.abs(tempY);
		double f = gainWeapon * 0.5 + gainPoints * 0.4 + avoidTrap * 0.4 + forceKill + loc * 0.01; 
		return f;
	}
	
	boolean kill(Player player1, Player player2, float d) { 
		
		if ((player1.pistol != null) && (d >= playersDistance(player2))) return true;
		else return false;
	}
	
	int[] move(Player p, int round){
		Map<Integer, Double> values = new HashMap<Integer, Double>();
		double max = -100; int maxI = 0;
		for (int i = 1; i < 9; i++) 
			switch(i) {
			case 1:	//getX() getY()			
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
		switch(maxI) {
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
	
	void statistics(int round) {
		int[] arr = getPath().get(round);
		System.out.println(this.getName() + " in the round No." + round + " has chosen to make the move " + arr[0] + ", has collected " + arr[2] +  " weapons, has gathered " + arr[3] + " food, and has fallen in " + arr[4] + " traps.");
		System.out.println("He now has " + arr[1] + " points.");
	}
}

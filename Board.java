/* This class is used to access Board objects.
 * The methods of this class are its constructors which are used to create Board objects, its getters which are used to access an object's 
 * variables and its setters which are used to change an object's variables.
 * Other methods used in this class are:
 * createRandomWeapon()					which is used to initialize all of the weapons' variables as they are randomly generated.
 * 										Checks are also happening so 2 weapons cannot share the same coordinates and all of the weapons are inside 
 * 										the weaponAreaLimits as given in the instructions. Finally, their Id + PlayerId
 * 										are initialized in such way so that they are unique in each weapon and can be recognized on the board.
 * createRandomFood()					which is used to initialize all of the food's variables as they are randomly generated.
 * 										Checks are also happening so 2 food cannot share the same coordinates and all of the food are inside 
 * 										the foodAreaLimits as given in the instructions.
 * createRandomTrap()					which is used to initialize all of the traps' variables as they are randomly generated.
 * 										Checks are also happening so 2 food cannot share the same coordinates and all of the traps are inside 
 * 										the trapAreaLimits as given in the instructions.
 * createBoard()						which uses the 3 previous methods to create the starting condition of the board
 * resizeBoard(Player p1, Player p2)	which checks if there are players on the boundaries of the board and shrinks the board's perimeter by one point.
 * 										It is also checked if the board going to shrink and there are either traps or food on its boundaries and if so
 * 										those objects get "deleted" by being transfered to (0, 0) which is not represented on the board.
 * getStringRepresentation()			which uses the coordinates of all the objects created so far and lists them in the string array it returns.
 * 										However due to the uniqueness of the board and its lack of all the (x, 0) and (0, y) points, there are 4 loops,
 * 										one for each quadrant, which not only assign the elements as string representations on the board but in the cases
 * 										of the North - East, South - West and South - East quadrants, they also move the elements one point to the left, 
 * 										one point upwards and one point to the left and upwards respectively.
 */
import java.util.Random;

public class Board {
	int n, m; 	// Board's Dimensions
	int w, f, t;// No. of (W)eapons, (F)ood and (T)raps
	int[][] weaponAreaLimits = { { -2, -2 }, { 2, -2 }, { 2, 2 }, { -2, 2 } }; 	// Coordinates of Weapon boundaries
	int[][] foodAreaLimits = { { -3, -3 }, { 3, -3 }, { 3, 3 }, { -3, 3 } }; 	// Coordinates of Food boundaries
	int[][] trapAreaLimits = { { -4, -4 }, { 4, -4 }, { 4, 4 }, { -4, 4 } }; 	// Coordinates of Trap boundaries
	Weapon[] weapons;
	Food[] food;
	Trap[] traps;
	// Variables' getters
	int getN() {
		return n;
	} 

	int getM() {
		return m;
	}

	int getW() {
		return w;
	}

	int getF() {
		return f;
	}

	int getT() {
		return t;
	}
	// Variables' setters
	void setN(int n) {
		this.n = n;
	} 

	void setM(int m) {
		this.m = m;
	}

	void setW(int we) {
		this.w = we;
	}

	void setF(int fo) {
		this.f = fo;
	}

	void setT(int tr) {
		this.t = tr;
	}
	// Constructors of the CLASS
	public Board() {
	} 

	public Board(int N, int M, int W, int F, int T) {
		this.n = N;
		this.m = M;
		this.w = W;
		this.f = F;
		this.t = T;
	}

	public Board(Board board) {
		this.n = board.n;
		this.m = board.m;
		this.w = board.w;
		this.f = board.f;
		this.t = board.t;
	}

	Random rand = new Random(); // FUNCTION rand.nextInt will be used in the FUNCTIONS createRandomWeapon, createRandomFood and createRandomTrap

	void createRandomWeapon() { // FUNCTION that randomizes the elements of all the weapons
		weapons = new Weapon[w];
		int temp = 0;
		for (int i = 0; i < w; i++) {
			this.weapons[i] = new Weapon(0, 0, 0, 0, "");
			this.weapons[i].setId(i % 3); // ID is not random. Each type of weapon gets its own ID: 0 - Pistol, 1 - Sword, 2 - Bow
			boolean check = true; // This variable will be used to check if the random weapon's coordinates generated already belong to another weapon
			do {
				check = true;
				temp = rand.nextInt(4);	// The coordinates of all the weapons must be between the points (-2, -2), (2, -2), (-2, 2) and (2, 2).
				if (temp < 2) {			// With these checks we ensure that the coordinates of the weapons will be [-2, 2] in both the x-axis and y-axis excluding 0
					temp = temp - 2;
				} else {
					temp = temp - 1;
				}
				this.weapons[i].setX(temp);
				temp = rand.nextInt(4);
				if (temp < 2) {
					temp = temp - 2;
				} else {
					temp = temp - 1;
				}
				this.weapons[i].setY(temp); 
				for (int j = 0; j < i; j++) // Inside this loop is checked if the random weapon's coordinates belong to another weapon
					if ((this.weapons[i].getX() == this.weapons[j].getX()) && (this.weapons[i].getY() == this.weapons[j].getY())) {
						check = false;
						break;
					}
			} while (!check); // If the random coordinates of the weapon belong to another weapon go into the loop again to pick new random coordinates
			this.weapons[i].setPlayerId(i % 2); 	// Since the players playing are two, we create the weapons alternately
			switch (i % 3) { 						// E.g. First pistol for player 1 (id = 0), second pistol for player 2 (id = 1),
			case 0:									// next weapon is the sword: First sword for player 1, second sword for player 2,
				this.weapons[i].setType("Pistol"); 	// and finally the bow: First bow for player 1, second bow for player 2. 													
				break; 								// Using % we ensure that every two weapons' playerId will be the same,
			case 1:									// Using % we ensure that every three weapons' type will be the same
				this.weapons[i].setType("Sword"); 	// Weapons as will be shown with the getStringRepresentation method will be created 
				break; 								// in the order: W00, W11, W02, W10, W01, W12, thus having one weapon for each player.
			case 2:
				this.weapons[i].setType("Bow");
				break;
			}
		}
	}

	void createRandomFood() { 	// FUNCTION that randomizes the elements of all the food
		food = new Food[f];
		int temp = 0;
		for (int i = 0; i < f; i++) {
			this.food[i] = new Food(0, 0, 0, 0);
			this.food[i].setId(i); // ID is not random
			this.food[i].setPoints(rand.nextInt(10) + 1); // Randomly assigns positive numbers between 1 and 10 to each food as their score
			boolean check; // This variable will be used to check if the random food's coordinates generated already belong to another food
			do {
				check = true; // This variable becomes false every time the coordinates of one food belong to another
				temp = rand.nextInt(6);	// The coordinates of all the food must be between the points (-3, -3), (3, -3), (-3, 3) and (3, 3)
				if (temp < 3) {			// With these checks we ensure that the coordinates of the food will be [-3, 3] in both the x-axis and y-axis excluding 0
					temp = temp - 3;	// Food must also not be inside the weapons' area
				} else {
					temp = temp - 2;
				}
				this.food[i].setX(temp);
				temp = rand.nextInt(6);	
				if (temp < 3) {
					temp = temp - 3;
				} else {
					temp = temp - 2;
				}
				this.food[i].setY(temp); 
				for (int j = 0; j < i; j++) // Inside this loop is checked if the random food's coordinates belong to another food
					if ((this.food[i].getX() == this.food[j].getX()) && (this.food[i].getY() == this.food[j].getY())) {
						check = false;
						break;
					}
			} while (((Math.abs(this.food[i].getX()) != 3) && (Math.abs(this.food[i].getY()) != 3)) || check == false); // This loop checks if the coordinates generated for the food are outside the weapons' area boundaries and inside the food's area and if the variable check is true or false
		}
	}

	void createRandomTrap() {	// FUNCTION that randomizes the elements of all the traps
		traps = new Trap[t];
		int temp = 0;
		for (int i = 0; i < t; i++) {
			this.traps[i] = new Trap(0, 0, 0, 0, "");
			this.traps[i].setId(i); // ID is not random
			this.traps[i].setPoints(-(rand.nextInt(10) + 1)); // Randomly assigns negative numbers between -10 and -1 to each trap as their score
			boolean check;
			do {
				check = true; // This variable becomes false every time the coordinates of one trap belong to another
				temp = rand.nextInt(8);	// The coordinates of all the traps must be between the points (-4, -4), (4, -4), (-4, 4) and (4, 4)
				if (temp < 4) {			// With this checks we ensure that the coordinates of the traps will be [-4, 4] in both the x-axis and y-axis excluding 0
					temp = temp - 4;	// Traps must also not be inside the food's area
				} else {			
					temp = temp - 3;
				}
				this.traps[i].setX(temp);
				temp = rand.nextInt(8);
				if (temp < 4) {
					temp = temp - 4;
				} else {
					temp = temp - 3;
				}
				this.traps[i].setY(temp);
				for (int j = 0; j < i; j++) // Inside this loop is checked if the random trap's coordinates belong to another trap
					if ((this.traps[i].getX() == this.traps[j].getX()) && (this.traps[i].getY() == this.traps[j].getY())) {
						check = false;
						break;
					}
			} while (((Math.abs(this.traps[i].getX()) != 4) && (Math.abs(this.traps[i].getY()) != 4)) || check == false); // This loop checks if the coordinates generated for the trap are outside the food's area boundaries and inside the traps' area and if the variable check is true or false
			int type = rand.nextInt(2); // There are 2 types of traps, Ropes-Traps and Animals-Traps. A number is randomly generated and inside the If-statement the type of each type is assigned to it
			if (type == 1)
				this.traps[i].setType("Ropes");
			else
				this.traps[i].setType("Animals");
		}
	}

	void createBoard() { 		// FUNCTION that calls the 3 FUNCTIONS createRandomWeapon, createRandomFood and createRandomTrap to create the board
		createRandomWeapon();
		createRandomFood();
		createRandomTrap();
	}

	void resizeBoard(Player p1, Player p2) { // FUNCTION that will make the board smaller as long as there are no players on the edge of the board	
		if ((Math.abs(p1.getX()) != (getN() / 2)) && (Math.abs(p1.getY()) != (getM() / 2)) && (Math.abs(p2.getX()) != (getN() / 2)) && (Math.abs(p2.getY()) != (getM() / 2))) {
			if (getN() == 8)	// When the board's dimensions are 8x8 and will shrink, all the traps will be out of bounds. This is why with this check all the traps go to (0, 0) which does not exist on the map.
				for(int i = 0; i < getT(); i++) {
					traps[i].setX(0);
					traps[i].setY(0);
				}
			if (getN() == 6)	// When the board's dimensions are 6x6 and will shrink, all the food will be out of bounds. This is why with this check all the food go to (0, 0) which does not exist on the map.
				for(int i = 0; i < getF(); i++) {
					food[i].setX(0);
					food[i].setY(0);
				}
			setN(getN() - 2);
			setM(getM() - 2);
			System.out.println("The walls are closing in!");
		}
	}	
	
	String[][] getStringRepresentation(Player p1, Player p2) { 	// FUNCTION that will create and return a 2-dimensional string array containing the board's information
		String[][] arr = new String[getN()][getM()];
		for (int i = 0; i < getN(); i++)	// Setting every element of the array that is to be printed to ___ as given in the instructions of the project
			for (int j = 0; j < getM(); j++) {
				arr[i][j] = "___";
			}
		for (int i = -10; i < 0; i++)	// This loop is for the North - West section of the board and it assigns all the elements (Weapons, Food, Traps) one point to the left (....j - 1)
			for (int j = -10; j < 0; j++) {
				for (int k = 0; k < getW(); k++)
					if ((weapons[k].getX() == i) && (weapons[k].getY() == j))
						arr[(getN() / 2) + i][(getM() / 2) + j] = "W" + String.valueOf(weapons[k].getPlayerId())
								+ String.valueOf(weapons[k].getId());
				for (int k = 0; k < getF(); k++)
					if ((food[k].getX() == i) && (food[k].getY() == j))
						arr[(getN() / 2) + i][(getM() / 2) + j] = "F" + String.valueOf(food[k].getId() + " ");
				for (int k = 0; k < getT(); k++)
					if ((traps[k].getX() == i) && (traps[k].getY() == j))
						arr[(getN() / 2) + i][(getM() / 2) + j] = "T" + String.valueOf(traps[k].getId() + " ");
				if ((p1.getX() == i) && (p1.getY() == j)) arr[(getN() / 2) + i][(getN() / 2) + j] = "P1 ";
				if ((p2.getX() == i) && (p2.getY() == j)) arr[(getN() / 2) + i][(getN() / 2) + j] = "P2 ";
			}
		for (int i = -10; i < 0; i++)	// This loop is for the North - East section of the board and it assigns all the elements (Weapons, Food, Traps) one point to the left (....j - 1)
			for (int j = 1; j < 11; j++) {
				for (int k = 0; k < getW(); k++)
					if ((weapons[k].getX() == i) && (weapons[k].getY() == j))
						arr[(getN() / 2) + i][(getM() / 2) + j - 1] = "W" + String.valueOf(weapons[k].getPlayerId())
								+ String.valueOf(weapons[k].getId());
				for (int k = 0; k < getF(); k++)
					if ((food[k].getX() == i) && (food[k].getY() == j))
						arr[(getN() / 2) + i][(getM() / 2) + j - 1] = "F" + String.valueOf(food[k].getId() + " ");
				for (int k = 0; k < getT(); k++)
					if ((traps[k].getX() == i) && (traps[k].getY() == j))
						arr[(getN() / 2) + i][(getM() / 2) + j - 1] = "T" + String.valueOf(traps[k].getId() + " ");
				if ((p1.getX() == i) && (p1.getY() == j)) arr[(getN() / 2) + i][(getM() / 2) + j - 1] = "P1 ";
				if ((p2.getX() == i) && (p2.getY() == j)) arr[(getN() / 2) + i][(getM() / 2) + j - 1] = "P2 ";
			}
		for (int i = 1; i < 11; i++)		// This loop is for the South - West section of the board and it assigns all the elements (Weapons, Food, Traps) one point upwards (....i - 1)
			for (int j = -10; j < 0; j++) {
				for (int k = 0; k < getW(); k++)
					if ((weapons[k].getX() == i) && (weapons[k].getY() == j))
						arr[(getN() / 2) + i - 1][(getM() / 2) + j] = "W" + String.valueOf(weapons[k].getPlayerId())
								+ String.valueOf(weapons[k].getId());
				for (int k = 0; k < getF(); k++)
					if ((food[k].getX() == i) && (food[k].getY() == j))
						arr[(getN() / 2) + i - 1][(getM() / 2) + j] = "F" + String.valueOf(food[k].getId() + " ");
				for (int k = 0; k < getT(); k++)
					if ((traps[k].getX() == i) && (traps[k].getY() == j))
						arr[(getN() / 2) + i - 1][(getM() / 2) + j] = "T" + String.valueOf(traps[k].getId() + " ");
				if ((p1.getX() == i) && (p1.getY() == j)) arr[(getN() / 2) + i - 1][(getN() / 2) + j] = "P1 ";
				if ((p2.getX() == i) && (p2.getY() == j)) arr[(getN() / 2) + i - 1][(getN() / 2) + j] = "P2 ";
			}
		for (int i = 1; i < 11; i++)		// This loop is for the South - East section of the board and it assigns all the elements (Weapons, Food, Traps) one point upwards and to the left (....i - 1), (....j - 1)
			for (int j = 1; j < 11; j++) {
				for (int k = 0; k < getW(); k++)
					if ((weapons[k].getX() == i) && (weapons[k].getY() == j))
						arr[(getN() / 2) + i - 1][(getM() / 2) + j - 1] = "W" + String.valueOf(weapons[k].getPlayerId())
								+ String.valueOf(weapons[k].getId());
				for (int k = 0; k < getF(); k++)
					if ((food[k].getX() == i) && (food[k].getY() == j))
						arr[(getN() / 2) + i - 1][(getM() / 2) + j - 1] = "F" + String.valueOf(food[k].getId() + " ");
				for (int k = 0; k < getT(); k++)
					if ((traps[k].getX() == i) && (traps[k].getY() == j))
						arr[(getN() / 2) + i - 1][(getM() / 2) + j - 1] = "T" + String.valueOf(traps[k].getId() + " ");
				if ((p1.getX() == i) && (p1.getY() == j)) arr[(getN() / 2) + i - 1][(getM() / 2) + j - 1] = "P1 ";
				if ((p2.getX() == i) && (p2.getY() == j)) arr[(getN() / 2) + i - 1][(getM() / 2) + j - 1] = "P2 ";
			}
		return arr;
	}
}
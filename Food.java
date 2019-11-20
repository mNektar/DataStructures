/* This class is used to access Food objects.
 * The methods of this class are its constructors which are used to create Food objects, its getters which are used to access an object's 
 * variables and its setters which are used to change an object's variables.
 */
public class Food {
	int id; 	// Food's ID
	int x; 		// Food's x-coordinate on the board
	int y; 		// Food's y-coordinate on the board
	int points; // Food's Points
	// Constructors of the CLASS
	public Food(int id, int x, int y, int points) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.points = points;
	}

	public Food(Food food) {
		this.id = food.id;
		this.x = food.x;
		this.y = food.y;
		this.points = food.points;
	}
	// Variable's getters
	public int getId() {
		return id;
	} 

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getPoints() {
		return points;
	}
	// Variable's setters
	public void setId(int fid) {
		this.id = fid;
	}

	public void setX(int fx) {
		this.x = fx;
	}

	public void setY(int fy) {
		this.y = fy;
	}

	public void setPoints(int fpoints) {
		this.points = fpoints;
	}
}

/* This class is used to access Trap objects.
 * The methods of this class are its constructors which are used to create Trap objects, its getters which are used to access an object's 
 * variables and its setters which are used to change an object's variables.
 */
public class Trap {
	int id; 	// Trap's ID
	int x; 		// Trap's x-coordinate on the board
	int y; 		// Trap's y-coordinate on the board
	int points; // Trap's Points
	String type;// Trap's Type (Ropes/Animals)
	// Constructors of the CLASS
	public Trap(int id, int x, int y, int points, String type) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.points = points;
		this.type = type;
	}

	public Trap(Trap trap) {
		this.id = trap.id;
		this.x = trap.x;
		this.y = trap.y;
		this.points = trap.points;
		this.type = trap.type;
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

	public String getType() {
		return type;
	}
	// Variable's setters
	public void setId(int tid) {
		this.id = tid;
	} 

	public void setX(int tx) {
		this.x = tx;
	}

	public void setY(int ty) {
		this.y = ty;
	}

	public void setPoints(int tpoints) {
		this.points = tpoints;
	}

	public void setType(String ttype) {
		this.type = ttype;
	}
}

/* This class is used to access Weapon objects.
 * The methods of this class are its constructors which are used to create Weapon objects, its getters which are used to access an object's 
 * variables and its setters which are used to change an object's variables.
 */
public class Weapon {
	int id; 		// Weapon's ID
	int x; 			// Weapon's x coordinate
	int y; 			// Weapon's y coordinate
	int playerId;	// Weapon's owner's ID
	String type; 	// Weapon's type (Pistol/Sword/Bow)
	// Constructors of the CLASS
	public Weapon(int id, int x, int y, int playerId, String type) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.playerId = playerId;
		this.type = type;
	}

	public Weapon(Weapon weapon) {
		this.id = weapon.id;
		this.x = weapon.x;
		this.y = weapon.y;
		this.playerId = weapon.playerId;
		this.type = weapon.type;
	}
	// Variables' getters
	public int getId() {
		return id;
	} 

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getPlayerId() {
		return playerId;
	}

	public String getType() {
		return type;
	}
	// Variable's setters
	public void setId(int wid) {
		this.id = wid;
	} 

	public void setX(int wx) {
		this.x = wx;
	}

	public void setY(int wy) {
		this.y = wy;
	}

	public void setPlayerId(int wplayerId) {
		this.playerId = wplayerId;
	}

	public void setType(String wtype) {
		this.type = wtype;
	}
}

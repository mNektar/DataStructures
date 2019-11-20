
public class HeuristicPlayer extends Player{
	ArrayList<Integer[]> path;

	public HeuristicPlayer(int id, String name, Board board, int score, int x, int y, Weapon bow, Weapon pistol,Weapon sword) {
		super(id, name, board, score, x, y, bow, pistol, sword);
		// TODO Auto-generated constructor stub
	}

	public HeuristicPlayer(Player player) {
		super(player);
		// TODO Auto-generated constructor stub
	}
	
	
}

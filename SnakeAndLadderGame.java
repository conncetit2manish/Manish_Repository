import java.util.HashMap;
import java.util.Map;
import java.io.FileInputStream;
import java.util.Properties;

public class SnakeAndLadderGame {
	
	public static void main(String[] args) throws Exception {
		Properties p = new Properties();
		FileInputStream sc = new FileInputStream("input.properties");
		p.load(sc);
		String str1 = p.getProperty("str1");
		String str2 = p.getProperty("str2");
		
		//positions of the ladders where key -> bottom of a ladder and value -> top of the ladder
		Map<Integer, Integer> ladders = new HashMap<>();
		ladders.put(1, 38);
		ladders.put(4, 14);
		ladders.put(8, 30);
		ladders.put(28, 76);
		ladders.put(21, 42);
		ladders.put(50, 67);
		ladders.put(71, 92);
		ladders.put(80, 99);
		
		// positions of the snakes where key -> head of a snake and value -> tail of a snake
		Map<Integer, Integer> snakes = new HashMap<>();
		
		snakes.put(32, 10);
		snakes.put(36, 6);
		snakes.put(48, 26);
		snakes.put(62, 18);
		snakes.put(88, 24);
		snakes.put(95, 56);
		snakes.put(97, 78);
		
		String[] result = playGame(str1, str2, ladders, snakes);
		for(String s : result)
			System.out.println(s);
	}
	
	public static String[] playGame(String str1, String str2, Map<Integer, Integer> ladders, Map<Integer, Integer> snakes) throws Exception {
		
		//checking if either one of the input is null or not.
		if(str1 == null || str2 == null) 
			throw new Exception("Input cannot be null");
		
		//checking if any one of the ladder is in wrong position or not
		for (Map.Entry<Integer, Integer> entry : ladders.entrySet()) {
			int key = entry.getKey();
			if(key <= 0)
				throw new Exception("Invalid ladder's bottom position");
			int value = ladders.get(key);
			if(value > 100) {
				throw new Exception("Invalid ladder's head position");
			}
		}
		
		//checking if any one of the snakes are in wrong position or not.
		for (Map.Entry<Integer, Integer> entry : snakes.entrySet()) {
			int key = entry.getKey();
			if(key > 100) 
				throw new Exception("Invalid snake's head position");
			if(key == 100) 
				throw new Exception("Non of the player can win game as snake head is at the position 100");
			int value = snakes.get(key);
			if(value < 0) {
				throw new Exception("Invalid snake's tail position");
			}
		}
		
		//initially the player's scores should be zero as starting position is zero.
		int scoreOfPlayer1 = 0;
		int scoreOfPlayer2 = 0;
		int i = 0, j = 0;
		while(i < str1.length() && j < str2.length()) {
			
			//converting each characters of the input into integers.
			int diceFaceForPlayer1 = str1.charAt(i) - '0';
			int diceFaceForPlayer2 = str2.charAt(j) - '0';
			
			//checking if the dice face is greater than 6 or not.
			if(diceFaceForPlayer1 > 6 || diceFaceForPlayer2 > 6) 
				throw new Exception("Dice cannot have vale more than six ");
			
			//checking if the score of a player is exactly 100 then returning the positions of players and winner of the game.
			if(diceFaceForPlayer1 + scoreOfPlayer1 == 100) 
				return new String[] {"Position of Player1 = 100", "Position of Player2 = " + scoreOfPlayer2, "Winner is Player1"};
	
			/*If the scores of the player does not exceeds 100 then updating the player's score.
			If the score of the player exceeds 100 then keeping the players to the previous position assuming that to win a game
			player need to score exactly 100 points.*/
			if(diceFaceForPlayer1 + scoreOfPlayer1 < 100)
				scoreOfPlayer1 = getPosition(diceFaceForPlayer1, scoreOfPlayer1, snakes, ladders);
			
			if(diceFaceForPlayer2 + scoreOfPlayer2 == 100) 
				return new String[] {"Position of Player1 = " + scoreOfPlayer1, "Position of Player2 = 100", "Winner is Player2"};
			if(diceFaceForPlayer2 + scoreOfPlayer2 < 100)
				scoreOfPlayer2 = getPosition(diceFaceForPlayer2, scoreOfPlayer2, snakes, ladders);
			++ i;
			++ j;
		}
		return new String[] {"Position of Player1 = " + scoreOfPlayer1, "Position of Player2 = " + scoreOfPlayer2, "Match is still not completed"};
	}
	
	public static int getPosition(int diceFaceForPlayer, int scoreOfPlayer, Map<Integer, Integer> snakes, Map<Integer, Integer> ladders) {
		
		int totalScore = diceFaceForPlayer + scoreOfPlayer;
		
		//checking if the scores of the player encounters the bottom of the ladder then returning the top of the ladder.
		if(ladders.containsKey(totalScore)) 
			return ladders.get(totalScore);
			
		//checking if the score of the player encounters the head of the snake then returning the bottom of the snake.
		if(snakes.containsKey(totalScore)) 
			return snakes.get(totalScore);
		
		//Returning the normal position if player dosn't encounter ladder and snake.
		return totalScore;
	}
	
}


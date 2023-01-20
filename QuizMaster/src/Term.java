//  Name: Benjamin Carroll
//  Description: The Term class holds a word, definition, and score and represents a
//               term that must be studied or memorized. The score is used to determine
//               if a term has been successfully learned by the user. It also implements the
//               Serializable interface in order to be saved.

import java.io.Serializable;

public class Term implements Serializable {
	private String word;
	private String def;
	private int score;
	
	//Set instance variables and set score to default of -1
	public Term(String word, String def) {
		this.word = word;
		this.def = def;
		score = -1;
	}
	
	//Getters and setters
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getDef() {
		return def;
	}

	public void setDef(String def) {
		this.def = def;
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	//Increment score by given value
	public void changeScore(int n) {
		score += n;
	}
}

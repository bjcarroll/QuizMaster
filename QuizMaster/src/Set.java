//  Name: Benjamin Carroll
//  Description: The Set class is a collection of Terms in an ArrayList with a name.
//               Other than standard getter and setters methods, it also has a method
//               for reseting all scores of the terms it holds. It also implements the
//               Serializable interface in order to be saved.

import java.io.Serializable;
import java.util.ArrayList;

public class Set implements Serializable {
	private String name; //Name of the set
	private ArrayList<Term> terms; //Terms contained within the set
	
	//Set instance variables
	public Set(String name, ArrayList<Term> terms) {
		this.name = name;
		this.terms = terms;
	}
	
	//Getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Term> getTerms() {
		return terms;
	}

	public void setTerms(ArrayList<Term> terms) {
		this.terms = terms;
	}
	
	//Reset all scores of the terms in the set to the default value of -1
	public void resetScores() {
		for(Term t : terms) {
			t.setScore(-1);
		}
	}
}

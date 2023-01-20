//  Name: Benjamin Carroll
//  Description: PracticePane is the screen on the third tab. It quizzes the user by
//               displaying a random definition of a Term in the selected Set. If the user
//               enters the corresponding word, the score of that term increases, and if not,
//               it decreases. After all terms have been studied, a message box pops up giving
//               the current progress. Then it repeats with all terms without a score of 0.

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class PracticePane extends BorderPane{
	private Set set; //The selected set
	private ArrayList<Term> terms; //Temporary list of terms
	private TextArea defText; //Where the definition is displayed
	private TextField inputField; //Where the user enters their answer
	private Button submitButton; //Submits the user's answer
	private Term currTerm; //current displayed term
	private int correctCount; //Number of correct guesses this round
	private int incorrectCount; //Number of incorrect guesses this round
	private boolean isRedo; //Whether or not the current screen is a redo
	
	//Create PracticePane
	public PracticePane(Set set) {
		this.set = set;
		correctCount = 0;
		incorrectCount = 0;
		isRedo = false;
		defText = new TextArea();
		defText.setEditable(false);
		defText.setWrapText(true);
		defText.setFont(Font.font(20));
		inputField = new TextField();
		submitButton = new Button("Submit");
		submitButton.setOnAction(new SubmitHandler());
		//Creates the user input area
		HBox inputHolder = new HBox();
		inputHolder.setAlignment(Pos.CENTER);
		inputHolder.getChildren().addAll(inputField, submitButton);
		//Add everything to the screen
		this.setCenter(defText);
		this.setBottom(inputHolder);
		//Loads all the terms into the temp list, then shows the first term
		loadTerms();
		nextTerm();
	}
	
	//Loads all terms into the temp list, then removes terms that are learned
	private void loadTerms() {
		terms = (ArrayList<Term>) set.getTerms().clone();
		for(int i = terms.size() - 1; i >= 0; i--) {
			if(terms.get(i).getScore() >= 0) {
				terms.remove(i);
			}
		}
	}
	
	//Gets a random term from the temp list as the next term. Also handles if the
	//term list becomes empty by reloading it and showing the status message after each pass
	private void nextTerm() {
		if(set.getTerms().isEmpty()) { //If there are no terms
			defText.setText("No terms in this set");
		}
		else {
			if(!terms.isEmpty()) { //If there are terms left, get a random one and set it to screen
				currTerm = terms.remove((int) (Math.random() * terms.size()));
				defText.setText(currTerm.getDef());
			}
			else { //Attempt to reload temp list when empty
				loadTerms();
				if(terms.isEmpty()) { //If its still empty after reload, that means the user is done
					defText.setText("All terms have been studied!");
				}
				else { //If it reloads, that means the next pass has started, show status message box
					nextTerm();
					Alert statusAlert = new Alert(AlertType.INFORMATION);
					statusAlert.setTitle("Quiz Status");
					statusAlert.setHeaderText("Here's how you did this round!");
					statusAlert.setContentText("Correct: " + correctCount + 
							"\nIncorrect: " + incorrectCount + 
							"\nTerms Learned: " + (set.getTerms().size() - terms.size() - 1) + "/" + set.getTerms().size());
					statusAlert.showAndWait();
					correctCount = 0;
					incorrectCount = 0;
				}
			}
		}
	}
	
	//Handler for when the submitButton is clicked
	private class SubmitHandler implements EventHandler<ActionEvent> {

		//Checks if user is correct. If so, increase score and get next term. If not,
		//display the correct answer until it is entered. Do not change the scores if
		//the user has already entered this word once and is incorrect
		@Override
		public void handle(ActionEvent event) {
			if(inputField.getText().equalsIgnoreCase(currTerm.getWord())) {
				if(!isRedo) {
					currTerm.changeScore(1);
					correctCount++;
				}
				else {
					isRedo = false;
				}
				nextTerm();
			}
			else {
				defText.setText(defText.getText() + "\n\nINCORRECT, the correct word was " + currTerm.getWord());
				if(!isRedo) {
					currTerm.changeScore(-1);
					incorrectCount++;
					isRedo = true;
				}
			}
			inputField.setText("");
		}
	}
}

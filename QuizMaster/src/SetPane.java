//  Name: Benjamin Carroll
//  Description: SetPane is the screen on the first tab. It lists all of the Sets the user has
//               created and lets the user add and remove them. It also allows the user to
//               reset the scores of the Terms contained in a Set to study them again.
//               The sets in this screen are what are saved to the disk.

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SetPane extends VBox {
	private ArrayList<Set> sets; //All sets
	private VBox setList; //VBox containing set names as RadioButtons and Buttons
	private TextField setName; //Set name TextField input
	private Button createButton; //Button used for creating a set
	private ToggleGroup toggleGroup; //ToggleGroup for RadioButtons
	private int currIndex; //current selected index
	
	//Create SetPane
	public SetPane(ArrayList<Set> sets) {
		this.sets = sets;
		toggleGroup = new ToggleGroup();
		setList = new VBox();
		setName = new TextField("Set Name");
		//currIndex default is -1 since nothing is selected
		currIndex = -1;
		//Load all sets in the list to display
		loadSets();
		//Make setList scroll
		ScrollPane scrollPane = new ScrollPane(setList);
		createButton = new Button("Create!");
		createButton.setOnAction(new CreateHandler());
		//Make user controls in an HBox
		HBox createHolder = new HBox(20);
		createHolder.getChildren().addAll(setName, createButton);
		//Add all to this Pane
		this.getChildren().addAll(scrollPane, createHolder);
	}
	
	//List out each Set as a radioButton and two Buttons in a VBox
	private void loadSets() {
		//Reset setList, toggleGroup and currIndex
		setList.getChildren().clear();
		toggleGroup.getToggles().clear();
		currIndex = -1;
		for(Set s : sets) {
			//Add a radioButton with each sets name, an edit button, and remove button to an HBox, then add that to setList
			HBox hBox = new HBox();
			RadioButton radioButton = new RadioButton(s.getName());
			radioButton.setOnAction(new SelectHandler());
			radioButton.setToggleGroup(toggleGroup);
			Button resetButton = new Button("Reset");
			resetButton.setOnAction(new ResetHandler(s));
			Button removeButton = new Button("Remove");
			removeButton.setOnAction(new RemoveHandler(s, radioButton));
			hBox.getChildren().addAll(radioButton, resetButton, removeButton);
			setList.getChildren().add(hBox);
		}
	}
	
	//Return current index of the selected set
	public int getCurrIndex() {
		return currIndex;
	}
	
	//Handler for when the createButton is clicked
	private class CreateHandler implements EventHandler<ActionEvent> {

		//Add new set to the list of sets with the given name and an empty list of terms, then refresh the displayed sets
		@Override
		public void handle(ActionEvent event) {
			sets.add(new Set(setName.getText(), new ArrayList<Term>()));
			loadSets();
		}
	}
	
	//Handler for when a resetButton is clicked
	private class ResetHandler implements EventHandler<ActionEvent> {
		private Set set;
		public ResetHandler(Set set) {
			this.set = set;
		}
		
		//Resets the scores of the corresponding set
		@Override
		public void handle(ActionEvent event) {
			set.resetScores();
		}
	}
	
	//Handler for when a removeButton is clicked
	private class RemoveHandler implements EventHandler<ActionEvent> {
		private Set set;
		private RadioButton radioButton;
		public RemoveHandler(Set set, RadioButton radioButton) {
			this.set = set;
			this.radioButton = radioButton;
		}
		
		//Remove a set and refresh the displayed sets
		@Override
		public void handle(ActionEvent event) {
			sets.remove(set);
			loadSets();
		}
	}
	
	//Handler for when a RadioButton is selected
	private class SelectHandler implements EventHandler<ActionEvent> {

		//Sets currIndex to the index of the selected RadioButton
		@Override
		public void handle(ActionEvent event) {
			currIndex = toggleGroup.getToggles().indexOf(toggleGroup.getSelectedToggle());
		}
	}
}

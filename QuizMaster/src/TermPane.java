//  Name: Benjamin Carroll
//  Description: TermPane is the screen on the second tab. It lists all of the Terms contained within
//               the selected Set on the first tab, and lists them out. It also allows the user to add to,
//               edit and remove these terms.

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class TermPane extends VBox {
	private Set set; //Selected Set
	private VBox termList; //Collection of terms and buttons, displayed in a VBox
	private TextArea wordArea; //Word input TextArea
	private TextArea defArea; //Definition input TextArea
	private double width; //Width of window
	
	//Create TermPane
	public TermPane(Set set, double width) {
		this.set = set;
		this.width = width;
		termList = new VBox();
		//Take terms in set and convert them into a Node
		loadTerms();
		//set termList as a child of a ScrollPane
		this.getChildren().add(new ScrollPane(termList));
		//Declare and instantiated create term button
		Button createButton = new Button("Create");
		createButton.setOnAction(new CreateHandler());
		//Create Text Areas
		wordArea = new TextArea();
		wordArea.setPrefRowCount(6);
		wordArea.setWrapText(true);
		wordArea.setMinHeight(USE_PREF_SIZE);
		defArea = new TextArea();
		defArea.setPrefRowCount(6);
		defArea.setWrapText(true);
		//Set all Nodes to screen
		GridPane createGrid = new GridPane();
		createGrid.add(new Label("Word"), 0, 0);
		createGrid.add(new Label("Definition"), 1, 0);
		createGrid.add(wordArea, 0, 1);
		createGrid.add(defArea, 1, 1);
		createGrid.add(createButton, 2, 1);
		ColumnConstraints cc1 = new ColumnConstraints();
        cc1.setPercentWidth(40);
        ColumnConstraints cc2 = new ColumnConstraints();
        cc2.setPercentWidth(40);
        ColumnConstraints cc3 = new ColumnConstraints();
        cc3.setPercentWidth(20);
        createGrid.getColumnConstraints().addAll(cc1,cc2,cc3);
		this.getChildren().add(createGrid);
	}
	
	//List out each word and definition with edit and remove buttons for each term in a VBox
	private void loadTerms() {
		int i = 0; //Keep track of index of the Term
		termList.getChildren().clear();
		for(Term t : set.getTerms()) {
			//For each term, create two labels and two buttons in a GridPane with specific spacing
			GridPane gridPane = new GridPane();
			gridPane.setMinWidth(width - 20);
			Label wordText = new Label(t.getWord());
			wordText.setMaxWidth(width / 4);
			wordText.setWrapText(true);
			Label defText = new Label(t.getDef());
			defText.setMaxWidth(width / 4);
			defText.setWrapText(true);
			Button editButton = new Button("Edit");
			editButton.setOnAction(new EditHandler(i));
			Button removeButton = new Button("Remove");
			removeButton.setOnAction(new RemoveHandler(i));
			ColumnConstraints cc1 = new ColumnConstraints();
	        cc1.setPercentWidth(35);
	        ColumnConstraints cc2 = new ColumnConstraints();
	        cc2.setPercentWidth(35);
	        ColumnConstraints cc3 = new ColumnConstraints();
	        cc3.setPercentWidth(15);
	        ColumnConstraints cc4 = new ColumnConstraints();
	        cc4.setPercentWidth(15);
	        gridPane.getColumnConstraints().addAll(cc1,cc2,cc3, cc4);
	        
	        //Add all elements to gridPane
			gridPane.add(wordText, 0, 0);
			gridPane.add(defText, 1, 0);
			gridPane.add(editButton, 2, 0);
			gridPane.add(removeButton, 3, 0);
			gridPane.setHgap(20);
			
			//Add final GridPane to the termList
			termList.getChildren().add(gridPane);
			i++;
		}
	}
	
	//Handler for when createButton is clicked
	private class CreateHandler implements EventHandler<ActionEvent> {
		
		//Adds new Term to current Set, then reloads the displayed Terms
		@Override
		public void handle(ActionEvent event) {
			set.getTerms().add(new Term(wordArea.getText(), defArea.getText()));
			loadTerms();
		}
	}
	
	//Handler for when an editButton is clicked
	private class EditHandler implements EventHandler<ActionEvent> {
		private int index;
		
		public EditHandler(int index) {
			this.index = index;
		}
		
		//Sets the textAreas to the respective word and definition, then removes the term from the set
		@Override
		public void handle(ActionEvent event) {
			wordArea.setText(set.getTerms().get(index).getWord());
			defArea.setText(set.getTerms().get(index).getDef());
			set.getTerms().remove(index);
			loadTerms();
		}
	}
	
	//Handler for when a removeButton is clicked
	private class RemoveHandler implements EventHandler<ActionEvent> {
		private int index;
		
		public RemoveHandler(int index) {
			this.index = index;
		}
		
		//Remove respective Term from the Set then reload the displayed terms
		@Override
		public void handle(ActionEvent event) {
			set.getTerms().remove(index);
			loadTerms();
		}
	}
}

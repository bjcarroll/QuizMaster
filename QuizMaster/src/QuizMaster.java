//  Name: Benjamin Carroll
//  Description: This is the driver program of QuizMaster. It creates a TermPane
//               and the SetPane that goes on the first tab. When a different tab
//               is clicked, it generates the respective tab's content and sets it
//               to that tab. It also saves the sets in a file on close and loads
//               them on open.

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class QuizMaster extends Application {
	private ArrayList<Set> sets; //Contains all sets created
	private SetPane setPane; //Set creation screen
	
	public void start(Stage stage) {
		//Try to read previous data of sets, if anything goes wrong, create a new ArrayList
		try {
			FileInputStream fis = new FileInputStream("sets.dat");
			ObjectInputStream ois = new ObjectInputStream(fis);
			sets = (ArrayList<Set>) ois.readObject();
			ois.close();
		} 
		catch (FileNotFoundException e) {
			sets = new ArrayList<Set>();
		}
		catch (IOException e) {
			sets = new ArrayList<Set>();
		}
		catch (ClassNotFoundException e) {
			sets = new ArrayList<Set>();
		}
		//Create new SetPane Object
		setPane = new SetPane(sets);
		//Create Set List Tab
		TabPane tabPane = new TabPane();
		Tab tab1 = new Tab("Sets");
		tab1.setContent(setPane);
		//Create Term List Tab
		Tab tab2 = new Tab("Terms");
		tab2.setOnSelectionChanged(new TermTabGenerator(tab2, 1000));
		//Create Practice Tab
		Tab tab3 = new Tab("Study");
		tab3.setOnSelectionChanged(new PracticeTabGenerator(tab3));
		//Make all tabs non-closable
		tab1.setClosable(false);
		tab2.setClosable(false);
		tab3.setClosable(false);
		//Set scene and attach closeHandler
		tabPane.getTabs().addAll(tab1, tab2, tab3);
		Scene scene = new Scene(tabPane, 1000, 700);
		stage.setScene(scene);
		stage.setTitle("QuizMaster");
		stage.setOnCloseRequest(new closeHandler());
		stage.show();
	}
	
	public static void main(String[] args) {
	     launch(args);
	 }
	
	//Handler for when the Term List tab is clicked
	private class TermTabGenerator implements EventHandler<Event> {
		private Tab tab;
		private double width;
		
		//Pass in the tab that is being used and the width of the window
		public TermTabGenerator(Tab tab, double width) {
			this.tab = tab;
			this.width = width;
		}
		
		//Set the content of the Term List tab to a new TermPane with the current selected set or a message if nothing is selected
		@Override
		public void handle(Event event) {
				if(setPane.getCurrIndex() == -1) {
					tab.setContent(new Label("No set was selected"));
				}
				else {
					tab.setContent(new TermPane(sets.get(setPane.getCurrIndex()), width));
				}
			}
	}
	
	//Handler for when the Practice Tab is clicked
	private class PracticeTabGenerator implements EventHandler<Event> {
		private Tab tab;
		
		//Pass in the tab that is being used
		public PracticeTabGenerator(Tab tab) {
			this.tab = tab;
		}
		
		@Override
		//Set the content of the Practice tab to a new PracticePane with the current selected set or a message if nothing is selected
		public void handle(Event event) {
				if(setPane.getCurrIndex() == -1) {
					tab.setContent(new Label("No set was selected"));
				}
				else {
					tab.setContent(new PracticePane(sets.get(setPane.getCurrIndex())));
				}
			}
	}
	
	//Handler for when program closes
	private class closeHandler implements EventHandler<WindowEvent> {

		@Override
		//Save sets to file
		public void handle(WindowEvent event) {
				try {
					FileOutputStream fos = new FileOutputStream("sets.dat");
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					oos.writeObject(sets);
					oos.close();
				} 
				catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
}

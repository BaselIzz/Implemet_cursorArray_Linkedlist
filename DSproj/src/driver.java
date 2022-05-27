// COMP242
// Project No. # 1
// Done by : Basel Said Izz
// ID  : 1180336
// Supervisor : Dr. M. Nawahdah

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyCode;

public class driver extends Application {
	public int indexword = 0;
	final static int MAXWORDS = 100;
	final static int MAXCHARSINWORD = 50;
	public int maxWidthWord = 0; // The largest length of the words processed

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage ps) throws Exception {

		// instantiate the class cursorArray and initialize the cursor.
		cursorArray<String> names = new cursorArray<>();
		// Create 27 Buckets. 26 for [a-z] , one for space
		names.createBuckets();
		// create the main list
		int mainList = names.createList();

		// setup the GUI
		Label lblInput = new Label("INPUT");
		Label lblSorted = new Label("SORTED");
		Label lblEnteredword = new Label("");

		lblInput.setLayoutX(300.0);
		lblInput.setLayoutY(15.0);

		lblSorted.setLayoutX(650);
		lblSorted.setLayoutY(15.0);

		lblEnteredword.setLayoutX(30.0);
		lblEnteredword.setLayoutY(30.0);
		lblEnteredword.setText("Count of words in list is ");

		AnchorPane pane = new AnchorPane();

		ListView<String> listInput = new ListView<>();
		ListView<String> listSorted = new ListView<>();

		Button btnSort = new Button("Sort");
		Button btnAdd = new Button("Add");
		Button btnRemove = new Button("Remove");
		Button btnClear = new Button("Clear");
		Button btnFile = new Button("File");
		btnClear.setDisable(true);
		TextField txt = new TextField();
		txt.setLayoutX(14.0);
		txt.setLayoutY(48.0);
		// If the TextField is not empty the button add changed to enabled
		// else disabled
		txt.setOnKeyReleased(e -> {
			if (txt.getText().length() > 0) {
				btnAdd.setDisable(false);
			} else
				btnAdd.setDisable(true);
		});
		// pressing key ENTER add the text field to List input
		txt.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent k) {
				if (k.getCode().equals(KeyCode.ENTER)) {
					btnAdd.fire();
				}
			}
		});

		btnSort.setLayoutX(470.0);
		btnSort.setLayoutY(240.0);
		btnSort.setPrefWidth(60);
		btnSort.setDisable(true);
		
		btnSort.setOnAction(e -> {
			// Clear the sorted list view
			listSorted.getItems().clear();
			// Copy the content of Input ListView to Main list for sorting
			for (int i = 0; i < listInput.getItems().size(); i++) {																	
				String inWord = listInput.getItems().get(i);
				if (inWord.length() > maxWidthWord)
					maxWidthWord = inWord.length();
				// Finding the largest length
				names.insertAtFinal(inWord, mainList);
			}
			// Start time
			Instant start = Instant.now(); 
			//  invoke the sort method
			names.sortRadixLSD(mainList, maxWidthWord);  
			Instant end = Instant.now();
			// End time of sorting
			String s = names.traversList(mainList); 
			// Return sorted list to Sorted List View
			for (String listitem : s.split("@")) { 													
				if (listitem != null)
					listSorted.getItems().add(listitem);
			}
			// Delete the main list 
			names.clear(mainList); 
			ShowMessage(AlertType.INFORMATION, "Thank you for playing my project", 																				
					"Time elapsed: " + Duration.between(start, end).toMillis() + " milliseconds");
		});

		btnAdd.setLayoutX(60.0);
		btnAdd.setLayoutY(100.0);
		btnAdd.setPrefWidth(60);
		btnAdd.setDisable(true);
		// Filter Input words so that words with length >  MAXCHARSINWORD is not allowed
		// and English letters from a-z and space are allowed
		// checking for count of words to restrict it to MAXWORDS
		// Add the next input to list view inputList 
		// adding counter of words indexword++
		btnAdd.setOnAction(e -> {
			if (txt.getText().length() < MAXCHARSINWORD) { 
				if (isChar(txt.getText())) { 
					if (indexword < MAXWORDS) { 
						String Str = txt.getText();
						listInput.getItems().add(Str);
						txt.clear();
						btnAdd.setDisable(true);
						indexword++;
						btnSort.setDisable(false);
						btnClear.setDisable(false);
						lblEnteredword.setText("Count of words in list is " + indexword);
					} else {
						ShowMessage(AlertType.WARNING, "Warning ", "You can't add more words ");
						btnAdd.setDisable(true);
						txt.setEditable(false);
					}
				} else {
					ShowMessage(AlertType.WARNING, "Warning", "Numeric input is not allowed");
					txt.clear();
					btnAdd.setDisable(true);
				}
			} else {
				ShowMessage(AlertType.WARNING, "Warning", "You are trying to type more than 50 characters");
				txt.clear();
				btnAdd.setDisable(true);
			}
			txt.requestFocus();
		});

		btnRemove.setLayoutX(60.0);
		btnRemove.setLayoutY(140.0);
		btnRemove.setPrefWidth(60);
		btnRemove.setDisable(true);
		btnRemove.setOnAction(e -> {
			// Ability to remove a string (Word) from the input list
			// Counter of words -1
			listInput.getItems().remove(listInput.getSelectionModel().getSelectedIndex());
			indexword--;
			lblEnteredword.setText("Count of words in list is " + indexword);
			if (indexword == 0)
				btnSort.setDisable(true);
		});

		btnClear.setLayoutX(60.0);
		btnClear.setLayoutY(180.0);
		btnClear.setPrefWidth(60);
		btnClear.setDisable(true);
		btnClear.setOnAction(e -> {
			// Ability to clear the input list
			// Zero the counter of words 
			listInput.getItems().clear();
			indexword = 0;
			lblEnteredword.setText("Count of words in list is " + indexword);
			btnClear.setDisable(true);
			btnRemove.setDisable(true);
			btnSort.setDisable(true);
		});

		listInput.setLayoutX(183.0);
		listInput.setLayoutY(35.0);
		listSorted.setLayoutX(550.0);
		listSorted.setLayoutY(35.0);

		// Remove the selected item in Input list
		listInput.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {

				if (listInput.getSelectionModel().getSelectedIndex() >= 0)
					btnRemove.setDisable(false);
			}
		});
		btnFile.setLayoutX(60.0);
		btnFile.setLayoutY(220.0);
		btnFile.setPrefWidth(60);
		btnFile.setDisable(false);
		// importing the words from file
		btnFile.setOnAction(e -> {
			listSorted.getItems().clear();
			File f = new File("1000000.txt");
			if (f.exists()) {
				try {
					int inputnumber = 0;
					Scanner src = new Scanner(f);
					int maxlength = 0;
					while (src.hasNext() /*&&inputnumber<=100*/) {

						String str = src.next();
						if (str.length() <= MAXCHARSINWORD) {

							if (maxlength < str.length())
								maxlength = str.length();

							names.insertAtFinal(str, mainList);

						}
						inputnumber++;
					}
					src.close();
					Instant start = Instant.now();
					names.sortRadixLSD(mainList, maxlength);
					Instant end = Instant.now();

					String s = names.traversList(mainList);
					for (String line : s.split("@"))
						listSorted.getItems().add(line);
					names.clear(mainList);
					ShowMessage(AlertType.INFORMATION, "Thank you for playing my proj",
							"Time elapsed: " + Duration.between(start, end).toMillis() + " milliseconds");

				} catch (FileNotFoundException e1) {

					e1.printStackTrace();
				}
			}
		});

		pane.getChildren().addAll(txt, btnAdd, btnRemove, btnClear, btnSort, listInput, listSorted, lblInput, lblSorted,
				lblEnteredword, btnFile);
		Scene scr = new Scene(pane, 850, 480);
		ps.setScene(scr);
		ps.setResizable(true);
		ps.setTitle("LSD Radix Sort By Basel Izz --> Supervisor: Dr. Mamoun Nawahdah");
		ps.show();
	}
	// Method to show message  
	public static void ShowMessage(AlertType alert, String Header, String ContentText) {
		Alert alt = new Alert(null);
		alt.setAlertType(alert);
		alt.setHeaderText(Header);
		alt.setContentText(ContentText);
		alt.showAndWait();
	}
	
	// method to check if the text is Character and return true or false
	private boolean isChar(String ch) { 

		Pattern pattren = Pattern.compile("[a-zA-Z[ ]]+");
		Matcher matcher = pattren.matcher(ch);
		if (matcher.find() && matcher.group().equals(ch))
			return true;
		return false;
	}
}

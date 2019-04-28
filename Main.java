package application;
	
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		
		ArrayList<Question> questions = new ArrayList<Question>();
		Question currQuestion;
		int currQuestionNum;
		int totalNumQuestions;
		int numIncorrect;
		
		
		
		
		try {
			BorderPane root = new BorderPane();
			
			// button to create quiz
			Button createButton = new Button();
			createButton.setText("Create Quiz");
			createButton.setTextFill(Color.DARKGREEN);
		    createButton.setStyle("-fx-font: 18 arial;");

		    // title table to go in the top corner
			Label title = new Label("Quiz Generator");
		    title.setFont(Font.font("Verdana", 25));
		    title.setTextFill(Color.DARKGREEN);
			
		    // button to load a JSON file
			Button loadButton = new Button();
			loadButton.setText("Load Questions from JSON");
			loadButton.setTextFill(Color.DARKGREEN);
		    loadButton.setStyle("-fx-font: 18 arial;");
		    
		    // button to save questions to JSON
			Button saveButton = new Button();
			saveButton.setText("Save Questions from JSON");
			saveButton.setTextFill(Color.DARKGREEN);
		    saveButton.setStyle("-fx-font: 18 arial;");
		    
		    // button to add a question
		    Button addButton = new Button();
		    addButton.setText("Add a Question");
		    addButton.setTextFill(Color.DARKGREEN);
		    addButton.setStyle("-fx-font: 18 arial;");
			
		    // label and drop-down menu for number of questions
		    Label numQuestions = new Label("Number of Questions:");
		    numQuestions.setTextFill(Color.DARKGREEN);
			ObservableList<String> numQuestion = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
			ComboBox<String> questionBox = new ComboBox<String>(numQuestion);
			
			// label and drop-down menu for quiz topics
			Label quizTopic = new Label("Quiz Topic:");
			quizTopic.setTextFill(Color.DARKGREEN);
			ObservableList<String> topics = FXCollections.observableArrayList("math", "science", "english");
			ComboBox<String> topicsBox = new ComboBox<String>(topics);
			
			primaryStage.setTitle("Quiz Generator");
			GridPane grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(25, 25, 25, 25));
			
			grid.add(title, 0, 0);
			grid.add(loadButton, 0, 1);
			GridPane.setMargin(loadButton, new Insets(5, 10, 5, 10));
			GridPane.setMargin(saveButton, new Insets(5, 10, 5, 10));
			GridPane.setMargin(addButton, new Insets(5, 10, 5, 10));
			grid.add(saveButton, 0, 2);
			grid.add(addButton, 0, 3);
			grid.add(quizTopic, 2, 0);
			grid.add(topicsBox, 2, 1);
			grid.add(numQuestions, 2, 2);
			grid.add(questionBox, 2, 3);
			grid.add(createButton, 2, 5);

			
			Scene scene = new Scene(grid,500,275);
			grid.setStyle("-fx-background-color: #f5f5dc");
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

package application;
	
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,600,400);

			// button to create quiz
			Button createButton = new Button();
			createButton.setText("Create Quiz");
			createButton.setTextFill(Color.BLACK);
		    createButton.setStyle("-fx-font: 18 arial;");

		    // title table to go in the top corner
			Label title = new Label("Quiz Generator");
			title.setTextFill(Color.BLACK);
		    title.setStyle("-fx-font: 36 arial;");
			
		    // button to load a JSON file
			Button loadButton = new Button();
			loadButton.setText("Load Questions from JSON");
			loadButton.setTextFill(Color.BLACK);
		    loadButton.setStyle("-fx-font: 18 arial;");
		    
		    // button to save questions to JSON
			Button saveButton = new Button();
			saveButton.setText("Save Questions from JSON");
			saveButton.setTextFill(Color.BLACK);
		    saveButton.setStyle("-fx-font: 18 arial;");
		    
		    // button to add a question
		    Button addButton = new Button();
		    addButton.setText("Add a Question");
		    addButton.setTextFill(Color.BLACK);
		    addButton.setStyle("-fx-font: 18 arial;");
			
		    // label and drop-down menu for number of questions
		    Label numQuestions = new Label("Number of Questions:");
			ObservableList<String> questions = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
			ComboBox<String> questionBox = new ComboBox<String>(questions);
			
			// label and drop-down menu for quiz topics
			Label quizTopic = new Label("Quiz Topic:");
			ObservableList<String> topics = FXCollections.observableArrayList("math", "science", "english");
			ComboBox<String> topicsBox = new ComboBox<String>(topics);
			
			
			VBox vbox1 = new VBox();
			root.setLeft(vbox1);
			vbox1.setSpacing(10);
			vbox1.setPadding(new Insets(15, 12, 15, 12));
			vbox1.getChildren().add(title);
			vbox1.getChildren().add(addButton);
			vbox1.getChildren().add(loadButton);
			vbox1.getChildren().add(saveButton);
			
			VBox vbox = new VBox();
			root.setRight(vbox);
			vbox.setSpacing(10);
			vbox.setPadding(new Insets(15, 12, 15, 12));
			vbox.getChildren().add(quizTopic);
			vbox.getChildren().add(topicsBox);
			vbox.getChildren().add(numQuestions);
			vbox.getChildren().add(questionBox);
			vbox.getChildren().add(createButton);

			
			
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("Quiz Generator");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

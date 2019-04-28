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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
      saveButton.setText("Save Questions to JSON");
      saveButton.setTextFill(Color.DARKGREEN);
      saveButton.setStyle("-fx-font: 18 arial;");

      // button to add a question
      Button addButton = new Button();
      addButton.setText("Add a Question");
      addButton.setTextFill(Color.DARKGREEN);
      addButton.setStyle("-fx-font: 18 arial;");

      // FIXME we should look to see if javafx has a text field for ints so we dont
      // have to worry about parsing faulty input.
      // label and drop-down menu for number of questions
      Label numQuestions = new Label("Number of Questions:");
      numQuestions.setTextFill(Color.DARKGREEN);
      TextField numText = new TextField();

      // label and drop-down menu for quiz topics
      Label quizTopic = new Label("Quiz Topic:");
      quizTopic.setTextFill(Color.DARKGREEN);

      // FIXME Right here we should access the observable list within the question database
      // class
      ObservableList<String> topics =
          FXCollections.observableArrayList("math", "science", "english");
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
      grid.add(numText, 2, 3);
      grid.add(createButton, 2, 5);


      Scene scene = new Scene(grid, 500, 300);
      grid.setStyle("-fx-background-color: #f5f5dc");
      scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      primaryStage.setScene(scene);
      primaryStage.show();

      // Creating the mouse event handler for the Add Question Button
      EventHandler<MouseEvent> addEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
          addQuestion(primaryStage);
        }
      };
      // Registering the event filter
      addButton.addEventFilter(MouseEvent.MOUSE_CLICKED, addEventHandler);


      // Creating the mouse event handler for the Save Questions Button
      EventHandler<MouseEvent> saveEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {

          saveButton.setTextFill(Color.GREEN);
        }
      };
      // Registering the event filter
      saveButton.addEventFilter(MouseEvent.MOUSE_CLICKED, saveEventHandler);

      // Creating the mouse event handler for the Add Question Button
      EventHandler<MouseEvent> createEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
          // FIXME we need to fix this parsing it doesn't work. we need to look into
          // how to obtain the number entered.
          int numQuestions = Integer.parseInt(numText.getText());
          // must figure out how to pull quiz topic from combo box
          String topic = "howdy";

          displayQuiz(primaryStage, numQuestions, topic);
        }
      };
      // Registering the event filter
      createButton.addEventFilter(MouseEvent.MOUSE_CLICKED, createEventHandler);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * This method creates the scene for adding a question. The user is prompted to enter the question
   * itself, the multiple choice options, a picture image, the topic it is associated with, and the
   * correct answer. This question will be added to the main database.
   * 
   * @param primaryStage
   */
  public void addQuestion(Stage primaryStage) {
    try {
      // Adds a text field and title for the user to enter the topic of the question.
      Label topicLabel = new Label("Topic:");
      topicLabel.setTextFill(Color.DARKGREEN);
      TextField topicText = new TextField();

      // Adds a text field for the user to enter a question
      Label questionLabel = new Label("Question:");
      questionLabel.setTextFill(Color.DARKGREEN);
      TextField questionText = new TextField();

      // Adds text fields for the user to enter in the different choices.
      Label choiceLabel = new Label("Choices:");
      choiceLabel.setTextFill(Color.DARKGREEN);
      // Correct answer should be entered first.
      Label correctLabel = new Label("*Please enter correct answer here*");
      correctLabel.setTextFill(Color.RED);
      TextField choiceOneText = new TextField();
      TextField choiceTwoText = new TextField();
      TextField choiceThreeText = new TextField();
      TextField choiceFourText = new TextField();

      // button to submit a question
      Button submitButton = new Button();
      submitButton.setText("Submit Question");
      submitButton.setTextFill(Color.DARKGREEN);
      submitButton.setStyle("-fx-font: 18 arial;");

      // button to submit a question
      Button homeButton = new Button();
      homeButton.setText("Back to Home");
      homeButton.setTextFill(Color.DARKGREEN);
      homeButton.setStyle("-fx-font: 18 arial;");


      // Adds a text field for the user to enter a question
      Label imageLabel = new Label("Image File:");
      imageLabel.setTextFill(Color.DARKGREEN);
      TextField imageText = new TextField();

      // Creates a grid to add the pieces to.
      GridPane grid = new GridPane();
      grid.setAlignment(Pos.CENTER);
      grid.setHgap(5);
      grid.setVgap(10);
      grid.setPadding(new Insets(25, 25, 25, 25));

      grid.add(topicLabel, 0, 0);
      grid.add(topicText, 0, 1);
      grid.add(imageLabel, 2, 1);
      grid.add(imageText, 2, 2);
      grid.add(questionLabel, 0, 2);
      grid.add(questionText, 0, 3);
      grid.add(choiceLabel, 0, 4);
      grid.add(choiceOneText, 0, 5);
      grid.add(correctLabel, 1, 5);
      grid.add(choiceTwoText, 0, 6);
      grid.add(choiceThreeText, 0, 7);
      grid.add(choiceFourText, 0, 8);
      grid.add(submitButton, 2, 7);
      grid.add(homeButton, 2, 8);

      // Creates the new scene.
      Scene scene = new Scene(grid, 700, 400);
      grid.setStyle("-fx-background-color: #f5f5dc");

      // Adds the scene to the stage.
      scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      primaryStage.setScene(scene);
      primaryStage.show();
      primaryStage.setTitle("Add Question");

      // Creating the mouse event handler for the Save Questions Button
      EventHandler<MouseEvent> backEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
          start(primaryStage);
        }
      };
      // Registering the event filter
      homeButton.addEventFilter(MouseEvent.MOUSE_CLICKED, backEventHandler);

      // Creating the mouse event handler for the Submit Questions Button
      EventHandler<MouseEvent> submitEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
          // Obtains the topic.
          String topic = topicText.getText();

          // Creates the choice array.
          Choice[] choices = new Choice[4];
          choices[0] = new Choice(choiceOneText.getText(), true);
          choices[1] = new Choice(choiceTwoText.getText(), false);
          choices[2] = new Choice(choiceThreeText.getText(), false);
          choices[3] = new Choice(choiceFourText.getText(), false);

          // Obtains the question
          String theQuestion = questionText.getText();

          // Obtains the correct answer
          String answer = choices[0].getChoice();

          Question question = new Question(topic, choices, theQuestion, answer);

          // FIXME Add quesiton to question data base
          AddQuestionSuccess(primaryStage);
        }
      };
      // Registering the event filter
      submitButton.addEventFilter(MouseEvent.MOUSE_CLICKED, submitEventHandler);


    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * Just a lil method that lets the user know their question was successfully added.
   * 
   * @param primaryStage
   */
  public void AddQuestionSuccess(Stage primaryStage) {

    // title table to go in the top corner
    Label title = new Label("Your Question Was");
    title.setFont(Font.font("Verdana", 25));
    title.setTextFill(Color.DARKGREEN);

    // title table to go in the top corner
    Label title2 = new Label("Successfully Added!");
    title2.setFont(Font.font("Verdana", 25));
    title2.setTextFill(Color.DARKGREEN);

    // button to go bak home
    Button homeButton = new Button();
    homeButton.setText("Back to Home");
    homeButton.setTextFill(Color.DARKGREEN);
    homeButton.setStyle("-fx-font: 18 arial;");

    // Creates a grid to add the pieces to.
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(5);
    grid.setVgap(10);
    grid.setPadding(new Insets(25, 25, 25, 25));

    // Adds to the grid
    grid.add(title, 0, 0);
    grid.add(title2, 0, 1);
    grid.add(homeButton, 0, 2);

    // Creates the new scene.
    Scene scene = new Scene(grid, 500, 300);
    grid.setStyle("-fx-background-color: #f5f5dc");

    // Adds the scene to the stage.
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    primaryStage.setScene(scene);
    primaryStage.show();

    // Creating the mouse event handler for the Save Questions Button
    EventHandler<MouseEvent> backEventHandler = new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        start(primaryStage);
      }
    };
    // Registering the event filter
    homeButton.addEventFilter(MouseEvent.MOUSE_CLICKED, backEventHandler);

  }

  /**
   * This is a method that displays the information for the create quiz page.
   * 
   * @param primaryStage
   * @param numQuestions
   * @param topic
   */
  public void displayQuiz(Stage primaryStage, int numQuestions, String topic) {
    try {
      // title to go on the main page
      Label title = new Label("To Begin the quiz, select 'Start', otherwise return to home");
      title.setTextFill(Color.DARKGREEN);
      title.setStyle("-fx-font: 18 arial;");

      // button to enter quiz
      Button submitButton = new Button();
      submitButton.setText("Start the Quiz");
      submitButton.setTextFill(Color.DARKGREEN);
      submitButton.setStyle("-fx-font: 18 arial;");

      // button to submit a question
      Button homeButton = new Button();
      homeButton.setText("Back to Home");
      homeButton.setTextFill(Color.DARKGREEN);
      homeButton.setStyle("-fx-font: 18 arial;");

      GridPane grid = new GridPane();
      grid.setAlignment(Pos.CENTER);
      grid.setHgap(5);
      grid.setVgap(10);
      grid.setPadding(new Insets(25, 25, 25, 25));

      grid.add(title, 0, 1);
      grid.add(submitButton, 1, 2);
      grid.add(homeButton, 1, 0);

      Scene scene = new Scene(grid, 700, 400);
      grid.setStyle("-fx-background-color: #f5f5dc");

      // Adds the scene to the stage.
      scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      primaryStage.setScene(scene);
      primaryStage.show();
      primaryStage.setTitle("Start the Quiz");

      // Creating the mouse event handler for going back to homepage
      EventHandler<MouseEvent> backEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
          start(primaryStage);
        }
      };
      // Registering the event filter
      homeButton.addEventFilter(MouseEvent.MOUSE_CLICKED, backEventHandler);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}

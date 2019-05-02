package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.parser.ParseException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
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
  private static QuestionDatabase questionDB;
  // Data fields for when the quiz is occurring.
  private ArrayList<Question> questions = new ArrayList<Question>();
  private Question currQuestion;
  private int currQuestionNum;
  private int totalNumQuestions;
  private int numIncorrect;
  private int numQuestionsAnswered;

  @Override
  public void start(Stage primaryStage) {

    try {
      BorderPane root = new BorderPane();

      // displays total number of questions
      Label displayNumQs = new Label("Total Questions: " + questionDB.getNumAllQuestions());
      displayNumQs.setFont(Font.font("Verdana", 18));
      displayNumQs.setTextFill(Color.DARKGREEN);


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

      // Obtains the number ofquestions the user wishes for the quiz. If the input is anything
      // other than a number the error page will appear.
      Label numQuestions = new Label("Number of Questions:");
      numQuestions.setTextFill(Color.DARKGREEN);
      TextField numText = new TextField();

      // label and drop-down menu for quiz topics
      Label quizTopic = new Label("Quiz Topic:");
      quizTopic.setTextFill(Color.DARKGREEN);

      // The Topic combo box, it accesses the list of topics from the question database class.
      ObservableList<String> topics = questionDB.getTopics();
      ComboBox<String> topicsBox = new ComboBox<String>(topics);

      // Creates the grid that holds all of the buttons and text fields.
      primaryStage.setTitle("Quiz Generator");
      GridPane grid = new GridPane();
      grid.setAlignment(Pos.CENTER);
      grid.setHgap(10);
      grid.setVgap(10);
      grid.setPadding(new Insets(25, 25, 25, 25));

      // Adds all of the buttons and text boxes to the grid.
      grid.add(title, 0, 0);
      grid.add(loadButton, 0, 1);
      GridPane.setMargin(loadButton, new Insets(5, 10, 5, 10));
      GridPane.setMargin(saveButton, new Insets(5, 10, 5, 10));
      GridPane.setMargin(addButton, new Insets(5, 10, 5, 10));
      grid.add(saveButton, 0, 2);
      grid.add(addButton, 0, 3);
      grid.add(displayNumQs, 2, 0);
      grid.add(quizTopic, 2, 1);
      grid.add(topicsBox, 2, 2);
      grid.add(numQuestions, 2, 3);
      grid.add(numText, 2, 4);
      grid.add(createButton, 2, 5);

      // Creates the scene.
      Scene scene = new Scene(grid, 550, 350);
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

          try {
            saveJSON(primaryStage);

          } catch (Exception e1) {
            // File was not correctly made.
            ErrorOccurred(primaryStage);
          }
        }
      };
      // Registering the event filter
      saveButton.addEventFilter(MouseEvent.MOUSE_CLICKED, saveEventHandler);

      // Creating the mouse event handler for the Create Quiz Button
      EventHandler<MouseEvent> createEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
          // Obtains number from text field. If the input is not a number, it will print the
          // error screen.
          try {
            boolean error = false;
            int numQuestions = Integer.parseInt(numText.getText());
            // Obtains quiz topic chosen.
            String topic = topicsBox.getValue();
            if ((topic == null) || (topic.equals(""))) {
              error = true;
            }
            // Calls the display quiz page if no errors
            if (error) {
              ErrorOccurred(primaryStage);
            } else {
              displayQuiz(primaryStage, numQuestions, topic);
            }
          } catch (NumberFormatException n) {
            ErrorOccurred(primaryStage);
          }
        }
      };
      // Registering the event filter
      createButton.addEventFilter(MouseEvent.MOUSE_CLICKED, createEventHandler);


      // Creating the mouse event handler for the load questions from json button
      EventHandler<MouseEvent> loadEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
          loadJSON(primaryStage);
        }
      };
      // Registering the event filter
      loadButton.addEventFilter(MouseEvent.MOUSE_CLICKED, loadEventHandler);

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
      TextField choiceFiveText = new TextField();

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
      // button to go back home
      Button loadImageButton = new Button();
      loadImageButton.setText("Choose Image");
      loadImageButton.setTextFill(Color.DARKGREEN);
      loadImageButton.setStyle("-fx-font: 18 arial;");

      // Adds a text field and title for the user to enter the topic of the question.
      Label metaDataLabel = new Label("MetaData:");
      metaDataLabel.setTextFill(Color.DARKGREEN);
      TextField metaDataText = new TextField();

      // Creates a grid to add the pieces to.
      GridPane grid = new GridPane();
      grid.setAlignment(Pos.CENTER);
      grid.setHgap(5);
      grid.setVgap(10);
      grid.setPadding(new Insets(25, 25, 25, 25));

      grid.add(topicLabel, 0, 0);
      grid.add(topicText, 0, 1);
      grid.add(imageLabel, 2, 1);
      grid.add(loadImageButton, 2, 2);
      grid.add(metaDataLabel, 2, 3);
      grid.add(metaDataText, 2, 4);
      grid.add(questionLabel, 0, 2);
      grid.add(questionText, 0, 3);
      grid.add(choiceLabel, 0, 4);
      grid.add(choiceOneText, 0, 5);
      grid.add(correctLabel, 1, 5);
      grid.add(choiceTwoText, 0, 6);
      grid.add(choiceThreeText, 0, 7);
      grid.add(choiceFourText, 0, 8);
      grid.add(choiceFiveText, 0, 9);
      grid.add(submitButton, 2, 7);
      grid.add(homeButton, 2, 8);

      // Creates the new scene.
      Scene scene = new Scene(grid, 700, 450);
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
          // Keeps track of whether or not the user entered in all the necessary information.
          boolean error = false;
          // Obtains the topic.
          String topic = topicText.getText();
          // If user didn't enter a topic error screen prints.
          if ((topic == null) || (topic.equals(""))) {
            error = true;
          }

          // Creates the choice array.
          Choice[] choices = new Choice[5];
          choices[0] = new Choice(choiceOneText.getText(), true);
          choices[1] = new Choice(choiceTwoText.getText(), false);
          choices[2] = new Choice(choiceThreeText.getText(), false);
          choices[3] = new Choice(choiceFourText.getText(), false);
          choices[4] = new Choice(choiceFiveText.getText(), false);

          // If user didn't enter a choice error screen prints.
          for (int i = 0; i < choices.length; i++) {
            if ((choices[i].getChoice() == null) || (choices[i].getChoice().equals(""))) {
              error = true;
            }
          }

          // Obtains the question
          String theQuestion = questionText.getText();
          // If user didn't enter a topic error screen prints.
          if ((theQuestion == null) || (theQuestion.equals(""))) {
            error = true;
          }

          // Obtains the correct answer
          String answer = choices[0].getChoice();

          String image = loadImageButton.getText();

          // FIXME passes in unused as default parameter becuase I dont understand metadata.
          Question question = new Question(topic, choices, theQuestion, answer, image, "unused");

          if (error) {
            ErrorOccurred(primaryStage);
          } else {
            questionDB.addQuestion(topic, question);
            AddQuestionSuccess(primaryStage, "Your Question Was", "Successfully Added!");
          }
        }
      };
      // Registering the event filter
      submitButton.addEventFilter(MouseEvent.MOUSE_CLICKED, submitEventHandler);

      // Creating the mouse event handler for going to computer's files.
      EventHandler<MouseEvent> loadImageEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
          FileChooser fileChooser = new FileChooser();
          File file = fileChooser.showOpenDialog(primaryStage);
          if (file != null) {
            loadImageButton.setText(file.getName());
            // title to let the user know their file was successfully added.
            Label titleImage = new Label("Image Chosen.");
            titleImage.setTextFill(Color.RED);
            titleImage.setStyle("-fx-font: 18 arial;");
            // Adds title to the screen.
            grid.add(titleImage, 2, 3);
          }
        }
      };
      // Registering the event filter
      loadImageButton.addEventFilter(MouseEvent.MOUSE_CLICKED, loadImageEventHandler);

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * Just a lil method that lets the user know their question was successfully added.
   * 
   * @param primaryStage
   */
  public void AddQuestionSuccess(Stage primaryStage, String phrase, String phrase2) {

    // title table to go in the top corner
    Label title = new Label(phrase);
    title.setFont(Font.font("Verdana", 25));
    title.setTextFill(Color.DARKGREEN);

    // title table to go in the top corner
    Label title2 = new Label(phrase2);
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

      // Creates the grid to put all the buttons and texts fields on
      GridPane grid = new GridPane();
      grid.setAlignment(Pos.CENTER);
      grid.setHgap(5);
      grid.setVgap(10);
      grid.setPadding(new Insets(25, 25, 25, 25));

      // Adds them to the grid.
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

      // Comment
      EventHandler<MouseEvent> startQuizEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
          List<Question> allQuestionsList = questionDB.getQuestions(topic);
          List<Question> quizQuestions = new ArrayList<Question>();
          // FIXME random method?
          for (int i = 0; i < numQuestions; i++) {
            quizQuestions.add(allQuestionsList.get(i));
          }
          totalNumQuestions = quizQuestions.size();
          currQuestionNum = 0;
          numQuestionsAnswered = 0;
          numIncorrect = 0;
          displayQuestion(primaryStage, quizQuestions);
        }
      };
      // Registering the event filer
      submitButton.addEventFilter(MouseEvent.MOUSE_CLICKED, startQuizEventHandler);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * This method is for when the user wishes to load questions from a JSON file.
   * 
   * @param primaryStage
   */
  public void loadJSON(Stage primaryStage) {
    try {
      // title to go on the loading JSON file page
      Label title = new Label("Load Questions File");
      title.setTextFill(Color.DARKGREEN);
      title.setStyle("-fx-font: 18 arial;");

      // button to go back home
      Button loadFileButton = new Button();
      loadFileButton.setText("Choose File");
      loadFileButton.setTextFill(Color.DARKGREEN);
      loadFileButton.setStyle("-fx-font: 18 arial;");

      // button to go back home
      Button homeButton = new Button();
      homeButton.setText("Back to Home");
      homeButton.setTextFill(Color.DARKGREEN);
      homeButton.setStyle("-fx-font: 18 arial;");

      GridPane grid = new GridPane();
      grid.setAlignment(Pos.CENTER);
      grid.setHgap(5);
      grid.setVgap(5);
      grid.setPadding(new Insets(25, 25, 25, 25));

      grid.add(title, 0, 0);
      grid.add(loadFileButton, 0, 1);
      grid.add(homeButton, 2, 9);

      Scene scene = new Scene(grid, 500, 300);
      grid.setStyle("-fx-background-color: #f5f5dc");

      // Adds the scene to the stage.
      scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      primaryStage.setScene(scene);
      primaryStage.show();
      primaryStage.setTitle("Load Questions From JSON");

      // Creating the mouse event handler for going to computer's files.
      EventHandler<MouseEvent> loadFileEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
          FileChooser fileChooser = new FileChooser();
          File file = fileChooser.showOpenDialog(primaryStage);
          if (file != null) {
            loadFileButton.setText(file.getName());
            // Obtains the questions from the JSON file and saves them from the Database.
            try {
              questionDB.loadQuestionsFromJSON(file);
            } catch (ParseException p) {
              p.printStackTrace();
            } catch (FileNotFoundException f) {
              f.printStackTrace();
            } catch (IOException i) {
              i.printStackTrace();
            }

            // title to let the user know their file was successfully added.
            Label title1 = new Label("Questions Successfully Loaded.");
            title1.setTextFill(Color.RED);
            title1.setStyle("-fx-font: 18 arial;");
            // Adds title to the screen.
            grid.add(title1, 0, 2);
          }
        }
      };
      // Registering the event filter
      loadFileButton.addEventFilter(MouseEvent.MOUSE_CLICKED, loadFileEventHandler);


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

  public void saveJSON(Stage primaryStage) {
    try {
      // title to go on the save JSON file page
      Label title = new Label("Save Questions To File");
      title.setTextFill(Color.DARKGREEN);
      title.setStyle("-fx-font: 18 arial;");

      // button to go back home
      Button homeButton = new Button();
      homeButton.setText("Back to Home");
      homeButton.setTextFill(Color.DARKGREEN);
      homeButton.setStyle("-fx-font: 18 arial;");

      // text field for file name
      Label fileNameLabel = new Label("Choose name for new file:");
      fileNameLabel.setTextFill(Color.DARKGREEN);
      TextField fileText = new TextField();

      // instructions for file name
      Label fileNameGuidlines = new Label("*file name can't include: | / \\ \" : ? * < >");
      fileNameGuidlines.setTextFill(Color.RED);

      // button to save file
      Button saveFile = new Button();
      saveFile.setText("Save file");
      saveFile.setTextFill(Color.DARKGREEN);
      saveFile.setStyle("-fx-font: 18 arial;");

      // create grid
      GridPane grid = new GridPane();
      grid.setAlignment(Pos.CENTER);
      grid.setHgap(5);
      grid.setVgap(5);
      grid.setPadding(new Insets(25, 25, 25, 25));

      // add elements to the grid
      grid.add(title, 0, 0);
      grid.add(fileNameLabel, 0, 2);
      grid.add(fileText, 0, 4);
      grid.add(fileNameGuidlines, 2, 4);
      grid.add(saveFile, 0, 9);
      grid.add(homeButton, 2, 9);

      Scene scene = new Scene(grid, 500, 300);
      grid.setStyle("-fx-background-color: #f5f5dc");

      // Adds the scene to the stage.
      scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      primaryStage.setScene(scene);
      primaryStage.show();
      primaryStage.setTitle("Save Questions To JSON");

      EventHandler<MouseEvent> saveFileEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
          String name = fileText.getText();

          if (name == "") { // text field cannot be empty
            ErrorOccurred(primaryStage);
          } else if (questionDB.getNumAllQuestions() == 0) {
            // no questions in database
            ErrorOccurred(primaryStage);
          } else {


            try {
              File file = new File(name); // create file with the name the user entered
              // file.createNewFile();
              questionDB.saveQuestionsToJSON(file);

              // successfully added file.
              AddQuestionSuccess(primaryStage, "Successfullly Loaded", "Questions To JSON!");

            } catch (Exception e1) {
              ErrorOccurred(primaryStage);
            }
          }
        }

      };
      // Registering the event filter
      saveFile.addEventFilter(MouseEvent.MOUSE_CLICKED, saveFileEventHandler);

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
      ErrorOccurred(primaryStage);
    }
  }

  /**
   * This is an error screen indicating an error occured in adding questions.
   * 
   * @param primaryStage
   */
  public void ErrorOccurred(Stage primaryStage) {

    // title to go on the loading JSON file page
    Label title = new Label("Error Occured!");
    Label title1 = new Label("Please Try Again.");
    title.setTextFill(Color.DARKGREEN);
    title.setStyle("-fx-font: 25 arial;");
    title1.setTextFill(Color.DARKGREEN);
    title1.setStyle("-fx-font: 25 arial;");

    // button to go back home
    Button homeButton = new Button();
    homeButton.setText("Back to Home");
    homeButton.setTextFill(Color.DARKGREEN);
    homeButton.setStyle("-fx-font: 18 arial;");

    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(5);
    grid.setVgap(5);
    grid.setPadding(new Insets(25, 25, 25, 25));

    grid.add(title, 0, 0);
    grid.add(title1, 0, 1);
    grid.add(homeButton, 0, 2);

    Scene scene = new Scene(grid, 500, 300);
    grid.setStyle("-fx-background-color: #f5f5dc");

    // Adds the scene to the stage.
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    primaryStage.setScene(scene);
    primaryStage.show();
    primaryStage.setTitle("ERROR");

    // Creating the mouse event handler for going back to homepage
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
   * This method is the display screen for a given quiz question. it will for loop each question the
   * number of questions that the user requested.
   * 
   * @param primaryStage
   * @param quizQuestions
   */
  public void displayQuestion(Stage primaryStage, List<Question> quizQuestions) {
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(5);
    grid.setVgap(5);
    grid.setPadding(new Insets(25, 25, 25, 25));

    Scene scene = new Scene(grid, 500, 300);
    grid.setStyle("-fx-background-color: #f5f5dc");

    primaryStage.setTitle("Quiz");

    for (int i = 0; i < totalNumQuestions; i++) {
      // Correct Label
      Label correct = new Label("Correct");
      correct.setTextFill(Color.GREEN);
      correct.setStyle("-fx-font: 18 arial;");

      // Correct Label
      Label incorrect = new Label("Incorrect");
      incorrect.setTextFill(Color.RED);
      incorrect.setStyle("-fx-font: 18 arial;");

      // Results Button.
      Button results = new Button("Results");
      results.setTextFill(Color.DARKGREEN);
      results.setStyle("-fx-font: 18 arial;");

      // FIXME include this.
      // Tells the user what question they are on.
      Label currQuestion = new Label("Current Question: " + (i + 1) + "/" + totalNumQuestions);
      currQuestion.setTextFill(Color.DARKGREEN);
      currQuestion.setStyle("-fx-font: 18 arial;");

      // Button to take the user to the next question.
      Button nextButton = new Button("Next");
      Label questionLabel =
          new Label("Question " + (currQuestionNum + 1) + quizQuestions.get(i).getQuestion());
      questionLabel.setTextFill(Color.DARKGREEN);
      questionLabel.setStyle("-fx-font: 18 arial;");

      // These buttons are for the choices.
      Button answer1 = new Button(quizQuestions.get(i).getChoices()[0].getChoice());
      answer1.setTextFill(Color.DARKGREEN);
      answer1.setStyle("-fx-font: 18 arial;");

      Button answer2 = new Button(quizQuestions.get(i).getChoices()[1].getChoice());
      answer2.setTextFill(Color.DARKGREEN);
      answer2.setStyle("-fx-font: 18 arial;");

      Button answer3 = new Button(quizQuestions.get(i).getChoices()[2].getChoice());
      answer3.setTextFill(Color.DARKGREEN);
      answer3.setStyle("-fx-font: 18 arial;");

      Button answer4 = new Button(quizQuestions.get(i).getChoices()[3].getChoice());
      answer4.setTextFill(Color.DARKGREEN);
      answer4.setStyle("-fx-font: 18 arial;");

      grid.add(nextButton, 3, 4);
      grid.add(questionLabel, 1, 0);
      grid.add(answer1, 1, 2);
      grid.add(answer2, 2, 2);
      grid.add(answer3, 1, 3);
      grid.add(answer4, 2, 3);

      // Displays button to end quiz and show results if all questions have been answered.
      if (numQuestionsAnswered == totalNumQuestions) {
        // FIXME Add this to grid
        // grid.add(results, 0, 0);
      }

      // Creating the mouse event to show results.
      EventHandler<MouseEvent> answer1EventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
          if (quizQuestions.get(currQuestionNum).getChoices()[0].getIsCorrect()) {
            // FIXME correct add to page
            // grid.add(correct, 0, 0);

          } else {
            // FIXME display incorrect
            // grid.add(incorrect, 0, 0);
            numIncorrect++;
          }
          numQuestionsAnswered++;
          grid.add(nextButton, 3, 4);
        }
      };
      // Registering the event filter
      answer1.addEventFilter(MouseEvent.MOUSE_CLICKED, answer1EventHandler);

      // Creating the mouse event to show results.
      EventHandler<MouseEvent> answer2EventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
          if (quizQuestions.get(currQuestionNum).getChoices()[1].getIsCorrect()) {
            // FIXME correct add to page
            // grid.add(correct, 0, 0);

          } else {
            // FIXME display incorrect
            // grid.add(incorrect, 0, 0);
            numIncorrect++;
          }
          numQuestionsAnswered++;
          grid.add(nextButton, 3, 4);
        }
      };
      // Registering the event filter
      answer2.addEventFilter(MouseEvent.MOUSE_CLICKED, answer2EventHandler);

      // Creating the mouse event to show results.
      EventHandler<MouseEvent> answer3EventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
          if (quizQuestions.get(currQuestionNum).getChoices()[2].getIsCorrect()) {
            // FIXME correct add to page
            // grid.add(correct, 0, 0);

          } else {
            // FIXME display incorrect
            // grid.add(incorrect, 0, 0);
            numIncorrect++;
          }
          numQuestionsAnswered++;
          grid.add(nextButton, 3, 4);
        }
      };
      // Registering the event filter
      answer3.addEventFilter(MouseEvent.MOUSE_CLICKED, answer3EventHandler);

      // Creating the mouse event to show results.
      EventHandler<MouseEvent> answer4EventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
          if (quizQuestions.get(currQuestionNum).getChoices()[3].getIsCorrect()) {
            // FIXME correct add to page
            // grid.add(correct, 0, 0);

          } else {
            // FIXME display incorrect
            // grid.add(incorrect, 0, 0);
            numIncorrect++;
          }
          numQuestionsAnswered++;
          grid.add(nextButton, 3, 4);
        }
      };
      // Registering the event filter
      answer4.addEventFilter(MouseEvent.MOUSE_CLICKED, answer4EventHandler);


      // Creating the mouse event handler that goes to next question.
      EventHandler<MouseEvent> nextEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
          currQuestionNum++;
          displayQuestion(primaryStage, quizQuestions);
        }
      };
      // Registering the event filter
      nextButton.addEventFilter(MouseEvent.MOUSE_CLICKED, nextEventHandler);

      // Creating the mouse event to show results.
      EventHandler<MouseEvent> resultsEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
          // FIXME Go to results page. (finished quiz)
        }
      };
      // Registering the event filter
      results.addEventFilter(MouseEvent.MOUSE_CLICKED, resultsEventHandler);


      // Adds the scene to the stage.
      scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      primaryStage.setScene(scene);
      primaryStage.show();
    }
  }

  /**
   * This method presents a new stage that will tell the user the final results of the quiz. It will
   * display the score and give the user the option to take it again or go back to the main page.
   * 
   * @param primaryStage
   * @param numRight
   * @param totalNumQuestions
   */
  public void finishedQuiz(Stage primaryStage, int numRight, int totalNumQuestions) {
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(5);
    grid.setVgap(5);
    grid.setPadding(new Insets(25, 25, 25, 25));

    Scene scene = new Scene(grid, 500, 300);
    grid.setStyle("-fx-background-color: #f5f5dc");

    // title to go on the save JSON file page
    Label title = new Label("Final Score:");
    title.setTextFill(Color.DARKGREEN);
    title.setStyle("-fx-font: 26 arial;");

    // label for the score
    Label score = new Label(numRight + "/" + totalNumQuestions);
    score.setTextFill(Color.LIGHTSLATEGREY);
    score.setStyle("-fx-font: 26 arial;");


    // button to go back home
    Button homeButton = new Button();
    homeButton.setText("Back to Home");
    homeButton.setTextFill(Color.DARKGREEN);
    homeButton.setStyle("-fx-font: 18 arial;");

    // button to take again home
    Button retakeQuizButton = new Button();
    retakeQuizButton.setText("Take Quiz Again");
    retakeQuizButton.setTextFill(Color.DARKGREEN);
    retakeQuizButton.setStyle("-fx-font: 18 arial;");

    // add elements to the grid
    grid.add(title, 0, 0);
    grid.add(score, 1, 0);
    grid.add(homeButton, 2, 9);
    grid.add(retakeQuizButton, 0, 9);

    // Adds the scene to the stage.
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    primaryStage.setScene(scene);
    primaryStage.show();

    EventHandler<MouseEvent> reStartQuizEventHandler = new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        // FIXME restart quiz not sure how that part of the code functions yet
        // also probs not needed if its difficult to implement
        // displayQuestion(primaryStage);
      }
    };
    retakeQuizButton.addEventFilter(MouseEvent.MOUSE_CLICKED, reStartQuizEventHandler);

    // Creating the mouse event handler for going back to homepage
    EventHandler<MouseEvent> backEventHandler = new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        start(primaryStage);
      }
    };
    // Registering the event filter
    homeButton.addEventFilter(MouseEvent.MOUSE_CLICKED, backEventHandler);

  }

  public static void main(String[] args) {
    questionDB = new QuestionDatabase();
    launch(args);
  }
}

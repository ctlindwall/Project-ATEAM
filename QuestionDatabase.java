package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * This class contains all the questions that can be chosen from, and stores them in a hash map
 * based on their topic.
 * 
 * @author irene
 *
 */
public class QuestionDatabase {
  private Map<String, List<Question>> topics; // hash map with all the topics and questions
  private ObservableList<String> observableTopics;

  /**
   * Default constructor
   */
  public QuestionDatabase() {
    topics = new HashMap<String, List<Question>>(20);
    observableTopics = FXCollections.observableArrayList();
  }

  /**
   * Adds a question to the topics hash map
   * 
   * @param topic is the topic of the question
   * @param question contains all the data from a single question
   */
  public void addQuestion(String topic, Question question) {

    if (topics.containsKey(topic)) {
      // get the list of questions associated with the topic
      List<Question> topicQuestions = topics.get(topic);
      topicQuestions.add(question); // add new question to the list
      topics.replace(topic, topicQuestions); // replace the topic key with the updated list of
                                             // questions
    } else { // topic is not in the hash map
      observableTopics.add(topic);
      List<Question> topicQuestions = new LinkedList<Question>(); // new linked list of questions
      topicQuestions.add(question); // add the new question to the list
      topics.put(topic, topicQuestions); // add the new topic and question to the hash map
    }
  }

  /**
   * Gets the number of questions contained in a specific topic
   * 
   * @param topic is the topic of the question
   * @return num the number of questions for a topic, will return 0 if topic does not exist
   */
  public int getNumQuestions(String topic) {
    int numQuestions = 0;
    if (topics.containsKey(topic)) { // check if topic is contained in hashmap
      List<Question> topicQuestions = topics.get(topic);
      numQuestions = topicQuestions.size(); // get the number of questions for that specific topic
    }

    return numQuestions; // returns number of questions
  }

  // FIXME need a number of total questions

  /**
   * This saves the given question file to a json file
   * 
   * @param question
   */
  public void saveQuestionsToJSON(File question) {
    // not sure how to use this
  }

  /**
   * This method gets all the questions under a certain topic
   * 
   * @param topic
   * @return questionList a list of questions for the given topic, null if topic does not exist
   */
  public List<Question> getQuestions(String topic) {
    List<Question> topicQuestions = null;

    if (topics.containsKey(topic)) {
      topicQuestions = topics.get(topic); // get the questions for the specific topic
    }

    return topicQuestions;
  }

  /**
   * Loads questions from the json file
   * 
   * @param questions
   * @throws ParseException
   * @throws IOException
   */
  public void loadQuestionsFromJSON(File questions)
      throws FileNotFoundException, IOException, ParseException {

    String metaData; // meta data field from the json file
    String questionText; // string of the question being asked
    String topic; // topic of the question
    String image; // image associated with the question
    Choice[] choices = null; // array of type Choice with all possible choices
    String correctAnswer = null; // the correct answer to the question

    Object obj = new JSONParser().parse(new FileReader(questions)); // start parsing json
    JSONObject jo = (JSONObject) obj;
    JSONArray questionArray = (JSONArray) jo.get("questionArray");

    for (int i = 0; i < questionArray.size(); i++) { // for every question
      JSONObject jsonQuestion = (JSONObject) questionArray.get(i);
      metaData = (String) jsonQuestion.get("meta-data"); // parse data for meta data
      questionText = (String) jsonQuestion.get("questionText"); // parse question text
      topic = (String) jsonQuestion.get("topic"); // parse question topic
      image = (String) jsonQuestion.get("image"); // parse image file

      JSONArray questionChoices = (JSONArray) jsonQuestion.get("choiceArray"); // array for possible
                                                                               // choices
      for (int c = 0; c < questionChoices.size(); c++) { // for each choice
        JSONObject jsonChoice = (JSONObject) questionChoices.get(c);
        String isCorrect = (String) jsonChoice.get("isCorrect"); // saves if it is correct or not to
                                                                 // isCorrect variable
        String choice = (String) jsonChoice.get("choice"); // parse the string for the choice
        choices = new Choice[questionChoices.size()];
        if (isCorrect.equals("T")) { // if correct answer
          correctAnswer = (String) jsonChoice.get("choice"); // should be only one correct answer
          Choice choiceObj = new Choice(choice, true); // create a new choice that is the correct
                                                       // answer
          choices[c] = choiceObj; // add to choice array
        } else {
          Choice choiceObj = new Choice(choice, false); // create a new choice that is the incorrect
                                                        // answer
          choices[c] = choiceObj; // add to choice array
        }
      }
      Question finishedQuestion =
          new Question(topic, choices, questionText, correctAnswer, image, metaData);
    }
  }

  /**
   * Returns a list of all the possible topics for the user to choose from
   * 
   * @return topicsList list of all possible topics
   */
  public ObservableList<String> getTopics() {

    return observableTopics;
  }
}

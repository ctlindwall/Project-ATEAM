package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
  private ObservableList<String> observableTopics; // observable list for all the topics
  private int totalQuestions; // total number of questions in the database

  /**
   * Default constructor
   */
  public QuestionDatabase() {
    // FIXME might need to account for resizing probs is ok tho
    topics = new HashMap<String, List<Question>>(20);
    observableTopics = FXCollections.observableArrayList();
    totalQuestions = 0;
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
      // topics.replace(topic, topicQuestions); // replace the topic key with the updated list of
      // questions
    } else { // topic is not in the hash map
      observableTopics.add(topic);
      List<Question> topicQuestions = new LinkedList<Question>(); // new linked list of questions
      topicQuestions.add(question); // add the new question to the list
      topics.put(topic, topicQuestions); // add the new topic and question to the hash map
    }

    totalQuestions++; // after adding the a question increase the count for total questions
  }

  /**
   * Gets the number of questions contained in a specific topic
   * 
   * @param topic is the topic of the question
   * @return num the number of questions for a topic, will return 0 if topic does not exist
   */
  public int getNumQuestions(String topic) {
    // FIXME not sure if we need this anymore -Irene

    int numQuestions = 0;
    if (topics.containsKey(topic)) { // check if topic is contained in hashmap
      List<Question> topicQuestions = topics.get(topic);
      numQuestions = topicQuestions.size(); // get the number of questions for that specific topic
    }

    return numQuestions; // returns number of questions
  }

  /**
   * This method returns the number of all the questions that are in the database
   * 
   * @return totalQuestions the total number of questions
   */
  public int getNumAllQuestions() {
    return totalQuestions;
  }

  /**
   * This saves the given question file to a json file
   * 
   * @param question
   */
  public void saveQuestionsToJSON(File question)
      throws FileNotFoundException, IOException, ParseException {
    // FIXME needs to me commented

    // creating JSONObject
    JSONObject qa = new JSONObject();

    JSONArray ja = new JSONArray();

    for (String topic : observableTopics) {
      List<Question> questions = topics.get(topic);
      JSONObject qq = new JSONObject();
      for (Question q : questions) {
        qq = new JSONObject();
        qq.put("meta-data", q.getMetaData());
        qq.put("questionText", q.getQuestion());
        qq.put("topic", q.getTopic());
        qq.put("image", q.getImage());

        JSONArray choiceArray = new JSONArray();

        Choice[] choices = q.getChoices();
        for (Choice c : choices) {
          JSONObject cc = new JSONObject();
          if (c.getIsCorrect())
            cc.put("isCorrect", "T");
          else
            cc.put("isCorrect", "F");
          cc.put("choice", c.getChoice());
          choiceArray.add(cc);
        }
        qq.put("choiceArray", choiceArray);
      }
      ja.add(qq);
    }

    qa.put("questionArray", ja);
    PrintWriter pw = new PrintWriter(question);
    pw.write(qa.toJSONString());

    pw.flush();
    pw.close();
  }

  /**
   * This method gets all the questions under a certain topic
   * 
   * @param topic
   * @return questionList a list of questions for the given topic, null if topic does not exist
   */
  public List<Question> getQuestions(String topic) {
    // FIXME not sure if we need this anymore -Irene

    List<Question> topicQuestions = null;

    if (topics.containsKey(topic)) {
      topicQuestions = topics.get(topic); // get the questions for the specific topic
    }

    return topicQuestions; // list of all the questions from that topic
  }


  /**
   * Randomizes the questions in a specific topic. Then returns them as a linked list to be used to
   * display the quiz.
   * 
   * @param topic the topic that the user requested the questions from
   * @param numQuestions the number of questions that the user requested for the quiz
   * @return topicQuestionsLinked the questions for the topic in a randomized linked list
   */
  public List<Node<Question>> getQuestionsRandom(String topic, int numQuestions) {
    List<Question> topicQuestions = null; // list of questions from the hash map
    List<Node<Question>> topicQuestionsLinked = new LinkedList<Node<Question>>(); // linked list to
                                                                                  // be returned

    if (topics.containsKey(topic)) {
      topicQuestions = topics.get(topic); // get the questions for the specific topic
      Collections.shuffle(topicQuestions); // randomize the list of questions
    }

    for (int i = 0; i < numQuestions; i++) { // add each element from the array list to a
                                             // linked list
      Node<Question> n = new Node<Question>(topicQuestions.get(i));
      topicQuestionsLinked.add(n);
      if (i != 0)
        topicQuestionsLinked.get(i - 1).setNext(n);
    }

    return topicQuestionsLinked; // returned the randomizes linked list
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
      choices = new Choice[questionChoices.size()];
      for (int c = 0; c < questionChoices.size(); c++) { // for each choice

        JSONObject jsonChoice = (JSONObject) questionChoices.get(c);
        String isCorrect = (String) jsonChoice.get("isCorrect"); // saves if it is correct or not to
                                                                 // isCorrect variable
        String choice = (String) jsonChoice.get("choice"); // parse the string for the choice

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
      addQuestion(topic, finishedQuestion);
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
package application;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
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

  /**
   * Default constructor
   */
  public QuestionDatabase() {
    topics = new HashMap<String, Integer>();
  }

  /**
   * Adds a question to the topics hash map
   * 
   * @param topic is the topic of the question
   * @param question contains all the data from a single question
   */
  public void addQuestion(String topic, Question question) {

  }

  /**
   * Gets the number of questions contained in a specific topic
   * 
   * @param topic is the topic of the question
   * @return num the number of questions for a topic
   */
  public int getNumQuestions(String topic) {

  }

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
   * @return questionList a list of questions for the given topic
   */
  public List<Question> getQuestions(String topic) {


  }

  /**
   * Loads questions from the json file
   * 
   * @param questions
   */
  public void loadQuestionsFromJSON(File questions) {
    // not sure how to use this
  }

  /**
   * Returns a list of all the possible topics for the user to choose from
   * 
   * @return topicsList list of all possible topics
   */
  public ObservableList<String> getTopics() {

  }
}

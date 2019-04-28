package application;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
  // private int currentCapacity; // current capacity of the hashmap

  /**
   * Default constructor
   */
  public QuestionDatabase() {
    topics = new HashMap<String, List<Question>>(20);

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
    // not sure
    return null;
  }
}

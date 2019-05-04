package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.json.simple.parser.ParseException;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public interface QuestionDatabaseADT {

  /**
   * Adds a question to the Database, given a string that contains the topic, and the Question
   * object that stores the data for each question. The question is added to a hash map with they
   * key being the topic, and the value being a linked list of Question nodes.
   * 
   * @param topic string for the topic of the question
   * @param question the instance holding all data for a given question
   */
  public void addQuestion(String topic, Question question);

  /**
   * Getter method for the number of questions with in a given topic.
   * 
   * @param topic
   * @return the number of questions in a topic
   */
  public int getNumQuestions(String topic);

  /**
   * Getter method for the number of questions in the entire database. This should be displayed on
   * the GUI
   * 
   * @return number of questions in the database
   */
  public int getNumAllQuestions();

  /**
   * This takes all the questions in the database and saves them to a json file. This is used when
   * users want to keep the question sets they created..
   * 
   * @param question the name of the file
   * @throws FileNotFoundException
   * @throws IOExeption
   * @throws ParseException
   */
  public void saveQuestionsToJSON(File question)
      throws FileNotFoundException, IOException, ParseException;


  /**
   * This returns a randomized linked list of Question Node's that the size of the parameter
   * numQuestions. This is used for when displaying questions to the user. All the questions will be
   * linked, and easily traversable.
   * 
   * @param topic the topic that the user wants their quiz to be
   * @param numQuestions number of questions for the quiz
   * @return list of questions for the quiz in random order
   */
  public List<Question> getQuestionsRandom(String topic, int numQuestions);


  /**
   * Loads questions from a json file that the user submits, and adds them to the question database.
   * 
   * @param questions the json file to be parsed
   * @throws FileNotFoundException
   * @throws IOExeption
   * @throws ParseException
   */
  public void loadQuestionsFromJSON(File questions)
      throws FileNotFoundException, IOException, ParseException;

  /**
   * Returns an observable list of all topics in the question database. This is to be used on the
   * main GUI to display all the topics to the user.
   * 
   * @return the observable list of all the topics
   */
  public ObservableList<String> getTopics();
}
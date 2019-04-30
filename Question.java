/**
 * The question class. Each question has a list of fields associated to it and this class allows for
 * the access of that information.
 * 
 * @author Turner
 *
 */
public class Question {

  // private fields for the question class. Each question has a topic it is associated with and a
  // list
  // of options for the user to choose. There is a field for the correct answer, and an image that
  // may
  // be involved with the question.
  private String topic;
  private Choice[] choices;
  private String image;
  private String question;
  private String answer;
  private String metaData;
  
  // metadata (possibly use a boolean value instead? thoughts?)

  /**
   * A costructor for the Question class.
   * 
   * @param theTopic
   * @param theChoices
   * @param theQuestion
   */
  public Question(String theTopic, Choice[] theChoices, String theQuestion,
      String correctAnswer, String image, String metaData) {
    topic = theTopic;
    choices = theChoices;
    question = theQuestion;
    answer = correctAnswer;
    this.image = image;
    this.metaData = metaData;
  }

  /**
   * a getter method for the question
   * 
   * @return
   */
  public String getQuestion() {
    return question;
  }

  /**
   * a getter method for the list of choices
   * 
   * @return
   */
  public Choice[] getChoices() {
    return choices;
  }

  /**
   * a getter method for the answer
   * 
   * @return
   */
  public String getAnswer() {
    return answer;
  }

  /**
   * a getter method for the topic
   * 
   * @return
   */
  public String getTopic() {
    return topic;
  }

  /**
   * a getter method for the getImage
   * 
   * @return
   */
  public String getImage() {
    return image;
  }
  
  /**
   * getter method for the metaData
   */
  public String getMetaData() {
	  return metaData;
  }
}

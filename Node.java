package application;

/**
 * This class is for a particular node. Each node represents a question.
 * 
 * @author Turner
 *
 * @param <Question>
 */
public class Node<Question> {
  // Private fields.
  private Question value;
  private Node<Question> next;
  private boolean answered;

  /**
   * A constructor when we are not provided with a next node. 
   * @param q
   */
  public Node(Question q) {
    // Sets the fields. 
    value = q;
    this.next = null;
    answered = false;
  }

  /**
   * A constructor for a node when we have a next. 
   * @param q
   * @param next
   */
  public Node(Question q, Node<Question> next) {
    // Sets the fields. 
    value = q;
    this.next = next;
    answered = false;
  }

  /**
   * A getter method for the next node. 
   * @return
   */
  public Node<Question> getNext() {
    return next;
  }

  /**
   * A setter method for the next node. 
   * @return
   */
  public void setNext(Node<Question> next) {
    this.next = next;
  }

  /**
   * A getter method for the value of a node. 
   * @return
   */
  public Question getValue() {
    return value;
  }

  /**
   * A setter method for the answer key of a node. 
   * @return
   */
  public void setAnswered(boolean value) {
    answered = value;
  }

  /**
   * A getter method for the whether or not a question has been answered. 
   * @return
   */
  public boolean getAnswered() {
    return answered;
  }
}
package application;

/**
 * A class for an individual choice. Each choice has a boolean value that says whether it is the
 * correct option or not.
 * 
 * @author Turner
 *
 */
public class Choice {

  private String option;
  private boolean correct;

  /**
   * a Constructor for the Choice class. The individual choice is given a boolean value that says if
   * it is the correct choice or not.
   * 
   * @param aChoice
   * @param isCorrect
   */
  public Choice(String aChoice, boolean isCorrect) {
    option = aChoice;
    correct = isCorrect;
  }

  /**
   * A getter method for a singular choice.
   * 
   * @return
   */
  public String getChoice() {
    return option;
  }

  /**
   * A getter method that tells if the choice is true or false.
   * 
   * @return
   */
  public boolean getIsCorrect() {
    return correct;
  }

}

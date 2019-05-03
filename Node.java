
public class Node<Question>{
	private Question value;
	private Node<Question> next;
	private boolean answered;
	public Node(Question q) {
		value = q;
		this.next = null;
		answered = false;
	}
	
	public Node(Question q, Node<Question> next) {
		value = q;
		this.next = next;
		answered = false;
	}
	
	public Node<Question> getNext() {
		return next;
	}

	public void setNext(Node<Question> next) {
		this.next = next;
	}
	
	public Question getValue() {
		return value;
	}
	
	public void setAnswered(boolean value) {
		answered = value;
	}
	
	public boolean getAnswered() {
		return answered;
	}
}

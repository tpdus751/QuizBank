package quizbank.model;

public class QuizCollection {
	private int num;
	String quizCollectionName;
	private int price;

	public QuizCollection(int num, String quizCollectionName, int price) {
		this.num = num;
		this.quizCollectionName = quizCollectionName;
		this.price = price;
	}

	public int getNum() {
		return num;
	}

	public String getQuizCollectionName() {
		return quizCollectionName;
	}
	
	public int getPrice() {
		return price;
	}
	
	
	
	
}
	
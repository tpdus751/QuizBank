package quizbank.model;

public class Cart {
	QuizCollection quizCollection;
	Customer customer;
	int num;
	String customerId;
	String quizCollectionName;
	int quantity;

	public Cart(QuizCollection quizCollection) {
		this.quizCollection = quizCollection;
		this.num = quizCollection.getNum();
		this.quizCollectionName = quizCollection.getQuizCollectionName();
		this.quantity = 1;
	}
	
	public QuizCollection getQuizCollection() {
		return quizCollection;
	}

	public void setQuizCollection(QuizCollection quizCollection) {
		this.quizCollection = quizCollection;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getQuizCollectionName() {
		return quizCollectionName;
	}

	public void setQuizCollectionName(String quizCollectionName) {
		this.quizCollectionName = quizCollectionName;
	}

	public String getCustomerId() {
		return customerId;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void addQuantity(int quantity) {
		this.quantity += quantity;
		
	}
	
	public int getPrice() {
		return quantity * quizCollection.getPrice();
	}
	
	@Override
	public String toString() {
		return num + "번 : " + quizCollectionName + " " + quantity + "권, 총 가격 : " + getPrice() + "원";
	}
	
}

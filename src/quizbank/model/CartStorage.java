package quizbank.model;

import java.util.ArrayList;

public class CartStorage {
	private ArrayList<Cart> CartList = new ArrayList<>();

	public boolean isEmpty() {
		return CartList.isEmpty();
	}

	public ArrayList<Cart> getCartList() {
		return CartList;
	}

	public int getNumItems() {
		return CartList.size();
	}

	public String getItemInfo(int index) {
		return CartList.get(index).toString();
	}

	public void addItem(QuizCollection quizCollection) {
		Cart cart = getCartItem(quizCollection);
		if (cart == null) {
			CartList.add(new Cart(quizCollection));
		} else {
			cart.addQuantity(1);
		}
	}

	private Cart getCartItem(QuizCollection quizCollection) {
		for (Cart cart : CartList) {
			if (cart.getQuizCollection().equals(quizCollection)) return cart;
		}
		return null;
	}

	private Cart getCartItem(String quizCollectionName) {
		for (Cart cart : CartList) {
			if (cart.getQuizCollection().getQuizCollectionName().equals(quizCollectionName)) return cart;
		}
		return null;
	}

	public void resetCart() {
		CartList.clear();
	}

	public boolean isValidItem(String quizCollectionName) {
		for (Cart cart : CartList) {
			if (cart.getQuizCollection().getQuizCollectionName().equals(quizCollectionName)) return true;
		}
		return false;
	}

	public void deleteItem(String quizCollectionName) {
		Cart cart = getCartItem(quizCollectionName);
		if (cart != null) {
			CartList.remove(cart);
		} else {
			System.out.println("해당 문제집을 찾을 수 없습니다.");
		}
	}

	public void updateQuantity(String quizCollectionName, int quantity) {
		if (quantity == 0) {
			deleteItem(quizCollectionName);
		} else {
			Cart cart = getCartItem(quizCollectionName);
			if (cart != null) {
				cart.setQuantity(quantity);
			}
		}
	}

	public int getTotalPrice() {
		int totalPrice = 0;
		for (Cart cart : CartList) {
			totalPrice += cart.getPrice();
		}
		return totalPrice;
	}
}

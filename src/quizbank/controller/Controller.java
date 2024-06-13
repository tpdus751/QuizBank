package quizbank.controller;


import quizbank.model.AlgorithmsQuizStorage;
import quizbank.model.Cart;
import quizbank.model.CartStorage;
import quizbank.model.Customer;
import quizbank.model.CustomerStorage;
import quizbank.model.InterfaceQuizStorage;
import quizbank.model.JavaQuizStorage;
import quizbank.model.QuizCollection;
import quizbank.model.QuizCollectionStorage;
import quizbank.view.View;


public class Controller {

	JavaQuizStorage quizStorage;
	View view;
	CustomerStorage customerStorage;
	JavaQuizStorage javaQuizStorage;
	AlgorithmsQuizStorage algorithmsQuizStorage;
	InterfaceQuizStorage interfaceQuizStorage;
	Customer customer;
	Cart cart;
	CartStorage cartStorage;
	QuizCollectionStorage quizCollectionStorage;
	QuizCollection quizCollection;
	
	String[] menuList = {
			"0. 로그아웃",
			"1. 과목별 문제 보기",
			"2. 과목별 문제 풀기",
			"3. 장바구니에 문제집 담기",
			"4. 장바구니 확인하기 / 주문하기"
	};
	
//	String[] adminMenuList = {
//			"0. 관리자모드 종료",
//			"1. 과목별 문제 추가",
//			"2. 과목별 문제 삭제",
//			"3. 과목별 문제 수정"
//	};
	

	String[] QuizType = {
			"0. 이전으로 돌아가기",
			"1. 자바 문제 확인하기",
			"2. 알고리즘 문제 확인하기",
			"3. 인터페이스 문제 확인하기"
	};
	
	String[] StartAsUserList = {
			"0. 프로그램 종료",
			"1. 로그인",
			"2. 회원가입"
	};
	
	String[] StartQuizType = {
			"0. 이전으로 돌아가기",
			"1. 랜덤 자바 20문제 풀기",
			"2. 랜덤 알고리즘 20문제 풀기",
			"3. 랜덤 인터페이스디자인 20문제 풀기"
	};
	
	String[] CollectionMenuList = {
			"0. 이전으로 돌아가기",
			"1. 자바 문제집 담기",
			"2. 알고리즘 문제집 담기",
			"3. 인터페이스 문제집 담기"
	};
	
	String[] CheckCartMenu = {
			"0. 이전으로 돌아가기",
			"1. 장바구니 수량 변경",
			"2. 장바구니 삭제하기",
			"3. 장바구니 모두 비우기",
			"4. 주문하기"
	};
	
	
	public Controller(View view, CustomerStorage customerStorage, JavaQuizStorage javaQuizStorage, AlgorithmsQuizStorage algorithmsQuizStorage, InterfaceQuizStorage interfaceQuizStorage, QuizCollectionStorage quizCollectionStorage, CartStorage cartStorage) {
		this.interfaceQuizStorage = interfaceQuizStorage;
		this.algorithmsQuizStorage = algorithmsQuizStorage;
		this.javaQuizStorage = javaQuizStorage;
		this.view = view;
		this.customerStorage = customerStorage;
		this.quizCollectionStorage = quizCollectionStorage;
		this.cartStorage = cartStorage;
	}
	
	
	public void start() {
		
		view.displayWelcome();
		int loginNum;
		do {
			loginNum = view.selectMenuNo(StartAsUserList);
			switch (loginNum) {
				case 1:
					this.customer = view.goToLogin(customerStorage);	
					if (customer != null) {
						int menu;
						
						do {
							menu = view.selectMenuNo(menuList);
							
							switch (menu) {
							case 1:
								viewQuiz();
								break;
							case 2:
								playQuiz();
								break;
							case 3:
								addCollectionToCart();
								break;
							case 4:
								checkCart();
								break;
//							case 5:
//								loginAdminMode();
							case 0:
								break;
							default :
								view.showMessage("잘못된 메뉴 번호입니다.");
							}
							
						} while (menu != 0);
						view.showMessage("로그아웃 합니다.");
						break;
					} 
					break;
				case 2:
					registerCustomer();
					break;
				case 0:
					break;
				default :
					view.showMessage("번호를 다시 입력하세요.");
			}
			
		} while (!(loginNum == 0));
		view.showMessage("프로그램을 종료합니다.(다음에 봐요!)");
	}





//	private void loginAdminMode() {
//		
//	}


	private void checkCart() {
		
		int menu;
		
		if (cartStorage.getNumItems() == 0) {
			view.showMessage("장바구니가 비어 있습니다.");
		} else {
			do {
				view.displayCart(cartStorage);
				
				menu = view.selectMenuNo(CheckCartMenu);
				
				switch (menu) {
				case 1:
					updateBookInCart();
					break;
				case 2:
					deleteBookInCart();
					break;
				case 3:
					resetCart();
					break;
				case 4:
					order();
					break;
				case 0:
					break;
				default :
					view.showMessage("잘못된 메뉴 번호입니다.");
				}
				
			} while (menu != 0);
		}
		
		
	}
		
	private void order() {
		// 배송 정보 추가
		getDeliveryInfo();
		// 구매 정보 보기 : 장바구니 내역, 배송 정보
		viewOrderInfo();
		// 진짜 주문할거니?
		if (view.askConfirm(">> 진짜 주문하려면 yes를 입력하세요 : ", "yes") ) {
			view.showMessage(">> 주문이 완료되었습니다.");
			// 주문 처리 -> 장바구니 초기화
			cartStorage.resetCart();
				}
		
	}


	private void getDeliveryInfo() {
		view.inputDeliveryInfo(customer);	
	}

	private void viewOrderInfo() {
		view.displayOrder(cartStorage, customer);
	}


	private void resetCart() {
		view.displayCart(cartStorage);
		
		if (!cartStorage.isEmpty()) {
			if (view.askConfirm(">> 장바구니를 비우려면 yes를 입력하세요 : ", "yes")) {
				cartStorage.resetCart();
				view.showMessage(">> 장바구니를 비웠습니다.");
			}
		}
		
	}


	private void deleteBookInCart() {
		// 장바구니 보여주기
				view.displayCart(cartStorage);
				if (!cartStorage.isEmpty()) {
					// 도서 ID 입력 받기
					String quizCollectionName = view.selectCollectionName(cartStorage);
					if (view.askConfirm(">> 해당 문제집을 삭제하려면 yes를 입력하세요 : ", "yes")) {
						// 해당 도서 ID의 cartItem 삭제
						cartStorage.deleteItem(quizCollectionName);
						view.showMessage(">> 해당 문제집을 삭제했습니다.");
					}
				}
		
	}


	private void updateBookInCart() {
		if (!cartStorage.isEmpty()) {
			// 도서 ID 입력 받기
			String quizCollectionName = view.selectCollectionName(cartStorage);
			// 수량 입력 받기
			int quantity = view.inputQuantity(0, 100);
			// 도서 ID에 해당하는 cartItem 가져와서 cartItem quantity set 수량
			cartStorage.updateQuantity(quizCollectionName, quantity);
		}
		
	}

	


	private void addCollectionToCart() {
		view.displayQuizCollectionInfo(quizCollectionStorage);
		String quizCollectionName = view.selectCollectionName(quizCollectionStorage);
		cartStorage.addItem(quizCollectionStorage.getQuizCollectionByCollectionName(quizCollectionName));
		view.showMessage(">>장바구니에 도서를 추가하였습니다.");	
	}
		
//	private void addToCartWhat(int menu) {
//		Scanner input = new Scanner(System.in);
//		if (menu == 1) {
//			System.out.print("추가할 수량을 입력하세요 : ");
//			int java_Num = input.nextInt();
//		}
//
//	}


	private void checkMyScore() {
		// TODO Auto-generated method stub
		
	}


	private void playQuiz() {
		int menu;
		
		do {
			menu = view.selectMenuNo(StartQuizType);
			
			switch (menu) {
			case 1:
				playJavaQuiz();
				break;
			case 2:
				playAlgorithmsQuiz();
				break;
			case 3:
				playInterfaceQuiz();
				break;
			case 0:
				break;
			default :
				view.showMessage("잘못된 메뉴 번호입니다.");
			}
			
		} while (menu != 0);
	}
		
	private void playInterfaceQuiz() {
		interfaceQuizStorage.printRandomQuizzes();
		
	}


	private void playAlgorithmsQuiz() {
		algorithmsQuizStorage.printRandomQuizzes();
		
	}


	private void playJavaQuiz() {
		javaQuizStorage.printRandomQuizzes();
	}

	


	private void registerCustomer() {
		view.goToRegister(customerStorage);
	}


//	private boolean login() {
//		 if (view.goToLogin(customerStorage) == null) {
//			 return false;
//		 }
//		 return true;
//	}


	private void viewQuiz() {
		int menu;
		
		do {
			menu = view.selectMenuNo(QuizType);
			
			switch (menu) {
			case 1:
				viewJavaQuiz();
				break;
			case 2:
				viewAlgorithmsQuiz();
				break;
			case 3:
				viewInterfaceQuiz();
				break;
			case 0:
				break;
			default :
				view.showMessage("잘못된 메뉴 번호입니다.");
			}
			
		} while (menu != 0);
	}


	private void viewInterfaceQuiz() {
		interfaceQuizStorage.viewAllQuiz();
		
	}
	private void viewAlgorithmsQuiz() {
		algorithmsQuizStorage.viewAllQuiz();

	}


	private void viewJavaQuiz() {
		javaQuizStorage.viewAllQuiz();
	}
		
	
}



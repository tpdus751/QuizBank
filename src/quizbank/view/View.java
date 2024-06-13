package quizbank.view;

import java.util.Scanner;

import quizbank.model.Cart;
import quizbank.model.CartStorage;
import quizbank.model.Customer;
import quizbank.model.CustomerStorage;
import quizbank.model.QuizCollectionStorage;

public class View {

	public Cart cart;

	public void displayWelcome() {
		String welcome = ("*****************************************\n"
                + "*********    세연이의 퀴즈 은행      *********\n"
                + "*****************************************");
		System.out.println(welcome);
      
	
		
	}
//	public void askQuestion(String[] StartAsUserList, CustomerStorage customerStorage) {
//		Scanner input = new Scanner(System.in);
//		displayMenu(StartAsUserList);
//		int answer = input.nextInt();
//		try {
//			do {
//				answer = selectMenuNo(StartAsUserList);
//				switch (answer) {
//					case 1: 
//						while(true) {
//							Customer customer = goToLogin(customerStorage);
//								break;
//							
//						}
//						break;
//					case 2:
//						goToRegister(customerStorage);
//						break;
//					case 0:
//						break;
//					default :
//						System.out.println("잘못된 번호 입니다.");
//				}
//			} while (!true);
//		} catch (InputMismatchException e) {
//			System.out.println("숫자를 입력하세요");
//		}
//		
//	}

	public void goToRegister(CustomerStorage customerStorage) {
		String id = null;
		boolean isTrue = true;
		Scanner input = new Scanner(System.in);
		System.out.println("회원가입 진행");
		System.out.print(">> 이름 : ");
		String name = input.nextLine();
		while (isTrue) {
			System.out.print(">> ID : ");
			id = input.nextLine();
			isTrue = customerStorage.isPossibleId(id);
			if (isTrue) {
				System.out.println("이미 등록되어 있는 ID 입니다.");
			} 
				System.out.println("사용 가능한 ID 입니다.");
		}
		System.out.print(">> PASSWORD : ");
		String password = input.nextLine();
		System.out.print(">> 휴대폰 번호 : ");
		String phoneNumber = input.nextLine();
		System.out.print(">> 주소 : ");
		String address = input.nextLine();
		System.out.print(">> e-mail : ");
		String email = input.nextLine();
		customerStorage.addCustomer(name, id, password, phoneNumber, address, email);
		customerStorage.saveBookList2File();
	}



	public Customer goToLogin(CustomerStorage customerStorage) {

		Scanner input = new Scanner(System.in);
		System.out.println("퀴즈 은행을 이용하시려면 아이디와 패스워드를 입력하세요.");
		System.out.print(">> ID : ");
		String id = input.nextLine();
		System.out.print(">> PASSWORD : ");
		String password = input.nextLine();
		if (customerStorage.isValidCustomer(id, password)) {
			System.out.println("로그인 성공하였습니다.");
			return (Customer) customerStorage.getCustomerById(id);
		} else {
			System.out.println("로그인 실패하였습니다.");
			return null;
		}
		
	}

	public int selectMenuNo(String[] menuList) {
		Scanner input = new Scanner(System.in);
		displayMenu(menuList);
		
		int menu;
		do {
			System.out.print(">> 메뉴 입력 : ");
			menu = inputNumberWithVaildation();
			if (menu < 0  || menu >= menuList.length) {
				System.out.println("0 부터 " + (menuList.length-1) + " 까지의 숫자를 입력하세요.");
			}
			
		} while (menu < 0 || menu >= menuList.length);
		
		return menu;
		
	}

	private int inputNumberWithVaildation() {
		Scanner input = new Scanner(System.in);
		try {
			int number = input.nextInt();
			return number;
		} catch (Exception e) {
			System.out.print(">> 숫자를 입력하세요 : ");
			return inputNumberWithVaildation();
		}
	}

	private void displayMenu(String[] menuList) {
		System.out.println("=========================================");
		for (int i = 0; i < menuList.length; i++) {
			System.out.println(menuList[i]);
		}
		System.out.println("=========================================");
	}

	public void showMessage(String string) {
		System.out.println(string);
	}

	public void displayQuizCollectionInfo(QuizCollectionStorage quizCollectionStorage) {
			quizCollectionStorage.viewAllQuizCollection();
		
		
	}
	
	
	public String readString(String message) {
		if (message != null || !message.equals(""))
			System.out.print(message);
			
		Scanner input = new Scanner(System.in);
		try {
			String string = input.nextLine();
			return string;
		} catch (Exception e) {
			System.out.print("문자를 입력하세요 :");
			return readString(message);
		}
	}
	
	public int readNumber(String message) {
		if (message != null || !message.equals(""))
			System.out.print(message);
		
		Scanner input = new Scanner(System.in);
		try {
			int number = input.nextInt();
			return number;
		} catch (Exception e) {
			System.out.print("숫자를 입력하세요 :");
			return readNumber(message);
		}
	}

	public String selectCollectionName(QuizCollectionStorage quizCollectionStorage) {
		String quizCollectionName;
		boolean result;
		do {
			quizCollectionName = readString("추가할 문제집의 이름을 입력하세요 : ");
			result = quizCollectionStorage.isValidItem(quizCollectionName);
			if (!result)
				System.out.print("잘못된 문제집 이름입니다.");
		} while (!result);
		
		return quizCollectionName;
	}
	
	// cart에 있는 책을 ID로 선택하기
		public String selectCollectionName(CartStorage cartStorage) {
			
			String quizCollectionName;
			boolean result;
			do {
				quizCollectionName = readString("변경할 문제집의 이름을 입력하세요 : ");
				result = cartStorage.isValidItem(quizCollectionName);
				if (!result)
					System.out.print("존재하지 않는 이름의 문제집 입니다.");
			} while (!result);
			
			return quizCollectionName;
		}

	public void displayCart(CartStorage cartStorage) {
		if (cartStorage.isEmpty()) {
			System.out.println(">> 장바구니가 비어 있습니다.");
			return;
		}
		System.out.println("=========================================");
		for (int i = 0; i < cartStorage.getNumItems(); i++) {
			System.out.println(cartStorage.getItemInfo(i));	
		}
		System.out.println();
	}
	
	public int inputQuantity(int min, int max) {
		int number;
		do {
			number = readNumber(">> 수량 입력 (" + min + " ~ " + max + "): ");
			if (number < min || number > max)
				System.out.println("잘못된 수량입니다.");
		} while (number < min || number > max);
		
		return number;
	}
		
	
	public boolean askConfirm(String message, String yes) {
		
		System.out.print(message);
		
		Scanner input = new Scanner(System.in);
		if (input.nextLine().equals(yes)) return true;
		return false;
		
	}
	
	// 배송 정보 입력 받기 - 주소와 이메일주소가 없는 경우
		public void inputDeliveryInfo(Customer customer) {
			if (customer.getEmail() == null) {
				Scanner input = new Scanner(System.in);
				System.out.println("배송을 위하여 이메일 주소와 배송받을 곳의 주소를 입력받습니다.");
				System.out.print(">> 이메일 : ");
				customer.setEmail(input.nextLine());
				System.out.print(">> 주소 : ");
				customer.setAddress(input.nextLine());
			}
		}
		
		public void displayOrder(CartStorage cartStorage, Customer customer) {
			
			System.out.println();
			// 장바구니 보여주기
			System.out.println("***** 주문할 도서 ******");
			displayCart(cartStorage);
			
			// 배송 정보 보여주기 - 고객 이름, 전화번호, 주소, 이메일주소
			System.out.println("***** 배송 정보 ******");
			System.out.println(">> 이름 : " + customer.getName());
			System.out.println(">> 전화번호 : " + customer.getPhoneNumber());
			System.out.println(">> 이메일 : " + customer.getEmail());
			System.out.println(">> 주소 : " + customer.getAddress());
			System.out.println();

		}
	

}

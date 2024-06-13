package quizbank;

import java.io.IOException;

import quizbank.controller.Controller;
import quizbank.model.AlgorithmsQuizStorage;
import quizbank.model.CartStorage;
import quizbank.model.CustomerStorage;
import quizbank.model.InterfaceQuizStorage;
import quizbank.model.JavaQuizStorage;
import quizbank.model.QuizCollectionStorage;
import quizbank.view.View;

public class Test {

	public static void main(String[] args) throws IOException {
				
		// 뷰 생성
		View view = new View();
		
		// 고객 생성
		CustomerStorage customerStorage = new CustomerStorage();
		
		// 문제집 생성
		JavaQuizStorage javaQuizStorage = new JavaQuizStorage();
		
		AlgorithmsQuizStorage algorithmsQuizStorage = new AlgorithmsQuizStorage();
		
		InterfaceQuizStorage interfaceQuizStorage = new InterfaceQuizStorage();
		
		QuizCollectionStorage quizCollectionStorage = new QuizCollectionStorage();
		
		
		CartStorage cartStorage = new CartStorage();
		
		
		// 컨트롤러 생성
		Controller controller = new Controller(view, customerStorage, javaQuizStorage, algorithmsQuizStorage, interfaceQuizStorage, quizCollectionStorage, cartStorage);

				
				
		controller.start();
	}

}

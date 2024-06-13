package quizbank.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import quizbank.view.View;

public class JavaQuizStorage {

	ArrayList<Quiz> javaQuizList = new ArrayList<>();
	final int MAX_QUANTITY = 20;
	private String JavaQuizFilename = "JavaQuizList.txt";
	private int lastNum;
	private boolean isSaved;
	View view = new View();
	
	public JavaQuizStorage() throws IOException {
		loadBookListFromFile();		
		generateLastId();
		isSaved = true;
	}
	
	
		private void loadBookListFromFile() throws IOException {
			FileReader fr;
			try {
				fr = new FileReader(JavaQuizFilename);
				BufferedReader br = new BufferedReader(fr);
				String idStr;
				while ((idStr = br.readLine()) != null) {
					if (idStr.equals("")) continue;
					int num = Integer.parseInt(idStr);
					String quizName = br.readLine();
					String answer = br.readLine();
					String comment = br.readLine();
					javaQuizList.add(new Quiz(num, quizName, answer, comment));
				}
				br.close();
				fr.close();
			} catch (FileNotFoundException |  NumberFormatException e) {
				System.out.println(e.getMessage());
			}
		}

		private void generateLastId() {
			lastNum = 0;
			for (Quiz quiz : javaQuizList) {
				int Num = quiz.getNum();
				if (Num > lastNum) {
					lastNum = Num;
				}
			}
			
		}
		
		public void deleteCustomer(int num) {
			javaQuizList.remove(getCustomerByNum(num));
			isSaved = false;
		}

		private Object getCustomerByNum(int num) {
			for (Quiz quiz : javaQuizList) {
				if (quiz.getNum() == num) {
					return quiz;
				}
			}
			return null;
		}

		public void addQuiz(String quizName, String answer, String comment) {
			Quiz quiz = new Quiz(++lastNum, quizName, answer, comment);
			javaQuizList.add(quiz);
			isSaved = false;
		}

		public boolean isSaved() {
			return isSaved;
		}
		
		public void saveBookList2File() {
			
			try {
				FileWriter fw = new FileWriter(JavaQuizFilename);
				for (Quiz quiz : javaQuizList) {
					fw.write(quiz.getNum() + "\n");
					fw.write(quiz.getQuizName() + "\n");
					fw.write(quiz.getAnswer() + "\n");
					fw.write(quiz.getComment() + "\n");;
				}
				fw.close();
				isSaved = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}


		public void viewAllQuiz() {
			for (Quiz quiz : javaQuizList) {
				view.showMessage("----------------------------------");
				System.out.println(quiz.getNum() + "번 : " + quiz.getQuizName());
				System.out.println("정답 : " + quiz.getAnswer());
				System.out.println("해설 : " + quiz.getComment());
				view.showMessage("----------------------------------");
				System.out.println();
			}
			
		}
		
		// 랜덤으로 20개의 퀴즈를 출력하는 메서드 추가
	    public void printRandomQuizzes() {
	        if (javaQuizList.size() < MAX_QUANTITY) {
	            System.out.println("! 퀴즈 리스트에 20개 이상의 문제가 필요합니다. !");
	            return;
	        }
	        
	        System.out.println("자바 퀴즈 20문제를 시작합니다.");
	        

	        // 리스트를 셔플하여 무작위로 섞음
	        Collections.shuffle(javaQuizList);

	        // 첫 번째 20개의 문제를 추출하여 출력
	        Scanner input = new Scanner(System.in);
	        ArrayList<String> submitList = new ArrayList<>();
	        ArrayList<String> incorrectList = new ArrayList<>();
	        int score = 0;
	        int count = 0;
	        
	        for (int i = 0; i < MAX_QUANTITY; i++) {
	            Quiz quiz = javaQuizList.get(i);
	            view.showMessage("----------------------------------");
	            System.out.println(++count + "번 : " + quiz.getQuizName());
	            System.out.print("정답 : ");
	            String submit = input.nextLine();
	            if (submit.equals(quiz.getAnswer())) {
	            	submitList.add(submit);
	            	score += 5;
	            } else {
	            	incorrectList.add(quiz.getQuizName());
	            }
	            view.showMessage("----------------------------------");
	            System.out.println();
	        }
	        System.out.println("총점 : " + score + "점");
	        System.out.println();
	        System.out.println("<틀린 문제>");
	        System.out.println();
	        for (int i = 0; i < incorrectList.size(); i++) {
	        	System.out.println(incorrectList.get(i));
	        	System.out.println();
	        }
	    }
		
}

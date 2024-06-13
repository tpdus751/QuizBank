package quizbank.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import quizbank.view.View;

public class QuizCollectionStorage {
	ArrayList<QuizCollection> QuizCollectionList = new ArrayList<>();
	private String QuizCollectionFilename = "QuizCollectionList.txt";
	private int lastNum;
	private boolean isSaved;
	
	public QuizCollectionStorage() throws IOException {
		loadBookListFromFile();		
		generateLastId();
		isSaved = true;
	}
	
	
		private void loadBookListFromFile() throws IOException {
			FileReader fr;
			try {
				fr = new FileReader(QuizCollectionFilename);
				BufferedReader br = new BufferedReader(fr);
				String idStr;
				while ((idStr = br.readLine()) != null) {
					if (idStr.equals("")) continue;
					int num = Integer.parseInt(idStr);
					String quizCollectionName = br.readLine();
					int price = Integer.parseInt(br.readLine());
					QuizCollectionList.add(new QuizCollection(num, quizCollectionName, price));
				}
				br.close();
				fr.close();
			} catch (FileNotFoundException |  NumberFormatException e) {
				System.out.println(e.getMessage());
			}
		}

		private void generateLastId() {
			lastNum = 0;
			for (QuizCollection quizCollection : QuizCollectionList) {
				int Num = quizCollection.getNum();
				if (Num > lastNum) {
					lastNum = Num;
				}
			}
			
		}
		
		public void deleteQuizCollection(String quizCollectionName) {
			QuizCollectionList.remove(getQuizCollectionByCollectionName(quizCollectionName));
			isSaved = false;
		}

		public QuizCollection getQuizCollectionByCollectionName(String quizCollectionName) {
			for (QuizCollection quizCollection : QuizCollectionList) {
				if (quizCollection.getQuizCollectionName().equals(quizCollectionName)) {
					return quizCollection;
				}
			}
			return null;
		}

		public void addQuizCollection(String quizCollectionName, int price) {
			QuizCollection quizCollection = new QuizCollection(++lastNum, quizCollectionName, price);
			QuizCollectionList.add(quizCollection);
			isSaved = false;
		}

		public boolean isSaved() {
			return isSaved;
		}
		
		public void saveBookList2File() {
			
			try {
				FileWriter fw = new FileWriter(QuizCollectionFilename);
				for (QuizCollection quizCollection : QuizCollectionList) {
					fw.write(quizCollection.getNum() + "\n");
					fw.write(quizCollection.getQuizCollectionName() + "\n");
					fw.write(quizCollection.getPrice() + "\n");
				}
				fw.close();
				isSaved = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}


		public void viewAllQuizCollection() {
			for (QuizCollection quizCollection : QuizCollectionList) {
				System.out.println("----------------------------------");
				System.out.println(quizCollection.getNum() + "번 : " + quizCollection.getQuizCollectionName() + ", " + quizCollection.getPrice() + "원");
				System.out.println("----------------------------------");
				System.out.println();
			}
			
		}
		
		public int getNumItems() {
			return QuizCollectionList.size();
		}
		
		public String getItemInfo(int i) {
			return QuizCollectionList.get(i).toString();
		}
		
		public boolean isValidItem(String quizCollectionName) {
			for (QuizCollection quizCollection : QuizCollectionList) {
				if (quizCollection.getQuizCollectionName().equals(quizCollectionName)) return true;
			}
			return false;
		}
}


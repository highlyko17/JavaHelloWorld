package com.mycom.word;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class WordCRUD implements ICRUD {
	ArrayList<Word> list;
	Scanner s;
	final String fname = "Dictionary.txt";
	
	WordCRUD(Scanner s){
		list = new ArrayList<>();
		this.s = s;
	}
	
	@Override
	public Object add() {
		System.out.print("=> 난이도(1,2,3) & 새 단어 입력 : ");
		int level = s.nextInt();
		//만약에 String word = s.next(); 이렇게 하면
		//바로 전의 엔터가 아직 입력 버퍼에 남아있어서 
		//String meaning = s.nextLine();에 들어가버림
		//그래서 String word = s.nextLine(); 이렇게 해야함
		String word = s.nextLine();
		
		System.out.print("뜻 입력 : ");
		String meaning = s.nextLine();
		
		return new Word(0, level, word, meaning);
	}
	
	public void addItem() {
		Word one = (Word)add();
		list.add(one);
		System.out.println("\n새 단어가 단어장에 추가되었습니다.\n");
		
	}

	@Override
	public int update(Object obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Object obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void selectOne(int id) {
		// TODO Auto-generated method stub
		
	}
	
	public void listAll() {
		System.out.println("\n----------------------------------");
		for(int i = 0; i < list.size(); i++) {
			System.out.print((i + 1) + " ");
			System.out.println(list.get(i).toString());
		}
		System.out.println("----------------------------------\n");
	}
	
	public ArrayList<Integer> listAll(String keyword) {
		ArrayList<Integer> idlist = new ArrayList<>();
		int j = 0;
		
		System.out.println("\n----------------------------------");
		for(int i = 0; i < list.size(); i++) {
			String word = list.get(i).getWord();
			if(!word.contains(keyword)) continue;
			System.out.print((j + 1) + " ");
			System.out.println(list.get(i).toString());
			idlist.add(i);
			j++;
		}
		System.out.println("----------------------------------\n");
		
		return idlist;
	}
	
	public void listAll(int level) {
		int j = 0;
		
		System.out.println("\n----------------------------------");
		for(int i = 0; i < list.size(); i++) {
			int ilevel = list.get(i).getLevel();
			if(ilevel != level) continue;
			System.out.print((j + 1) + " ");
			System.out.println(list.get(i).toString());
			j++;
		}
		System.out.println("----------------------------------\n");
	}

	public void updateItem() {
		System.out.print("=> 수정할 단어 검색 : ");
		String keyword = s.next(); // 공백을 허용하지 않기 위해서 next()
		ArrayList<Integer> idlist = this.listAll(keyword); 
		
		System.out.print("=> 수정할 번호 선택 : ");
		int id = s.nextInt();
		s.nextLine(); // 번호 입력 받고 친 엔터가 뜻 입력에 들어가버려서 이 줄 추
		System.out.print("=> 뜻 입력 : ");
		String meaning = s.nextLine(); // 공백 포함에서 받아야하니 nextLine()
		Word word = list.get(idlist.get(id - 1));
		word.setMeaning(meaning);
		System.out.println("단어가 수정되었습니다.");
	}

	public void deleteItem() {
		System.out.print("=> 삭제할 단어 검색 : ");
		String keyword = s.next(); // 공백을 허용하지 않기 위해서 next()
		ArrayList<Integer> idlist = this.listAll(keyword); 
		
		System.out.print("=> 삭제할 번호 선택 : ");
		int id = s.nextInt();
		s.nextLine(); // 번호 입력 받고 친 엔터가 뜻 입력에 들어가버려서 이 줄 추
		System.out.print("=> 정말로 삭제하실래요? (Y/N) ");
		String ans = s.next(); // 한 단어로 받아야하니 next
		if(ans.equalsIgnoreCase("y")) {
			list.remove((int)idlist.get(id - 1)); // remove 함수는 파라미터에 객체를 넣던지 정수형으로 몇 번째 인덱스의 객체를 삭제할 것인지 넣어야 함.
			System.out.println("단어가 삭제되었습니다.");
		} else
			System.out.println("취소되었습니다.");
	}
	
	public void loadFile() {
		try { // 파일이 없으면 에러 발생하기 때문에 try-catch문 사
			BufferedReader br = new BufferedReader(new FileReader(fname));
			String line;
			int count = 0;
			
			while(true) {
				line = br.readLine(); // 한 줄씩 불러온다
				if(line == null) break;
				String data[] = line.split("\\|"); // 그냥 |만 하면 정규식으로 인식할 수도 있어서 이걸 문자로 인식하기 위해서 \\입력
				int level = Integer.parseInt(data[0]); // 데이터는 문자로 되어있어서 정수로 변환해야함. 그래서 래퍼 클래스 Integer.parseInt로 문자열을 숫자로 바꾸기
				String word = data[1];
				String meaning = data[2];
				list.add(new Word(0, level, word, meaning));
				count++;
			}
			
			br.close(); // 파일 사용 후 닫기
			System.out.println("==> " + count + "개 로딩 완료!!!");
		} catch (IOException e) { // IOException e는 br.close()에 대한 catch
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void saveFile() {
		try {
			PrintWriter pr = new PrintWriter(new FileWriter(fname));
			for(Word one : list) { // for-each문 사용
				pr.write(one.toFileString() + "\n"); // one.toFileString() 질문하기
			}
			pr.close(); // 파일 다 사용 후 닫기
			System.out.println("===> 데이터 저장 완료!!!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void searchLevel() {
		System.out.print("=> 원하는 레벨은? (1-3) ");
		int level = s.nextInt();
		listAll(level);
		
	}

	public void searchWord() {
		System.out.print("=> 원하는 단어는? ");
		String keyword = s.next();
		listAll(keyword);
		
	}
}

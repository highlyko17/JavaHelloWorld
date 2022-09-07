package com.mycom.word;

import java.util.ArrayList;
import java.util.Scanner;

public class WordCRUD implements ICRUD {
	ArrayList<Word> list;
	Scanner s;
	
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
}

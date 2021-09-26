package com.todo.service;

import java.util.*;
import java.io.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc, category, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n===[할 일 추가]===\n"
				+ "제목 >> ");
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("같은 제목이 이미 있습니다.");
			return;
		}
		
		sc.nextLine();
	
		System.out.print("카테고리 >> ");
		category = sc.nextLine().trim();
		
		System.out.print("내용 >> ");
		desc = sc.nextLine().trim();
		
		System.out.print("마감일자(YYYY/MM/DD) >> ");
		due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, desc, category, due_date);
		list.addItem(t);
		System.out.println("할 일이 성공적으로 추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n===[할 일 삭제]===\n"
				+ "삭제할 항목의 번호 >> ");
		int num = sc.nextInt();
		
		if(num > l.getList().size() || num <= 0) {
			System.out.println("해당 항목을 찾지 못했습니다.");
			return;
		}
		
		TodoItem del_item = l.getList().get(num-1);
		
		System.out.println((l.indexOf(del_item)+1) + ". " + del_item.toString());
		while(true){
			System.out.print("위 항목을 삭제하시겠습니까?(y/n) >> ");
			String confirm_del = sc.next();
			switch (confirm_del) {
			case "y":
				l.deleteItem(del_item);
				System.out.println("할 일이 성공적으로 삭제되었습니다.");
				return;
			case "n":
				System.out.println("삭제를 취소했습니다.");
				return;
			default:
				System.out.println("\n정확한 명령어를 입력하세요.");
			}
		}
			
		
//		for (TodoItem item : l.getList()) {
//			if (title.equals(item.getTitle())) {
//				l.deleteItem(item);
//				System.out.println("할 일이 성공적으로 삭제되었습니다.");
//				return;
//			}
//		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n===[할 일 수정]===\n"
				+ "수정할 항목의 번호 >> ");
		
		int num = sc.nextInt();
		
		if(num > l.getList().size() || num <= 0) {
			System.out.println("해당 항목을 찾지 못했습니다.");
			return;
		}
		
		TodoItem edit_item = l.getList().get(num-1);
		
		System.out.println((l.indexOf(edit_item)+1) + ". " + edit_item.toString());
		System.out.println("위 항목을 수정합니다.");
		
		String new_title;
		while(true) {
			System.out.print("새 제목 >> ");
			new_title = sc.next().trim();
			if (!l.isDuplicate(new_title)) break;
			System.out.println("같은 제목이 이미 있습니다. 다시 입력해주세요.");
		}
		
		sc.nextLine();
		
		System.out.print("새 카테고리 >> ");
		String new_category = sc.next().trim();
		
		sc.nextLine();
		
		System.out.print("새 내용 >> ");
		String new_description = sc.nextLine().trim();
		
		System.out.print("새 마감일자(YYYY/MM/DD) >> ");
		String new_due_date = sc.next().trim();
		
		l.deleteItem(edit_item);
		TodoItem t = new TodoItem(new_title, new_description, new_category, new_due_date);
		l.addItem(t);
		System.out.println("할 일이 성공적으로 수정되었습니다.");
	}

	public static void listAll(TodoList l) {
		int count = l.getList().size();
		System.out.println("\n***[전체 목록]***\n"
				+ "총 " + count + "개의 할 일이 있습니다.");
		for (TodoItem item : l.getList()) {
			System.out.println((l.indexOf(item)+1) + ". " + item.toString());
		}
		
	}
	
	public static void find(TodoList l, String keyword) {
		int count = 0;
		System.out.println("\n***[검색 결과]***");
		for (TodoItem item : l.getList()) {
			if(item.getTitle().contains(keyword) || item.getDesc().contains(keyword)) {
				System.out.println((l.indexOf(item)+1) + ". " + item.toString());
				count++;
			}
		}
		System.out.println("총 " + count + "개의 할 일을 찾았습니다.");
	}
	
	public static void find_cate(TodoList l, String keyword) {
		int count = 0;
		System.out.println("\n***[검색 결과]***");
		for (TodoItem item : l.getList()) {
			if(item.getCategory().contains(keyword)) {
				System.out.println((l.indexOf(item)+1) + ". " + item.toString());
				count++;
			}
		}
		System.out.println("총 " + count + "개의 할 일을 찾았습니다.");
	}
	
	public static void ls_cate(TodoList l) {
		HashSet<String> category_set = new HashSet<>();
		
		for (TodoItem item : l.getList())
			category_set.add(item.getCategory());
		
		for(String category : category_set)
			System.out.print(category + "  ");
		
		int count = category_set.size();
		
		System.out.println("\n총 " + count + "개의 카테고리를 찾았습니다.");
	}
	
	public static void saveList(TodoList l, String filename) {
		try {
			FileWriter fw = new FileWriter(filename);
			for (TodoItem item : l.getList()) {
				fw.write(item.toSaveString());
			}
			fw.close();
		} catch (IOException e) {
			System.out.println(" 파일을 열 수 없습니다.");
			return;
		}
		
		System.out.println(filename + " 파일에 정상적으로 리스트를 저장했습니다.");
	}
	
	public static void loadList(TodoList l, String filename) {
		int count = 0;
		try {
			BufferedReader fr = new BufferedReader(new FileReader(filename));
			
			String line;
			while ((line = fr.readLine()) != null) {
				StringTokenizer stk = new StringTokenizer(line,"##");
				TodoItem t = new TodoItem(stk.nextToken(), stk.nextToken(), stk.nextToken(), stk.nextToken());
				t.setCurrent_date(stk.nextToken());
				l.addItem(t);
				count++;
			}
			fr.close();
		} catch (IOException e) {
			System.out.println(filename + " 파일을 열 수 없습니다.");
			return;
		}
		
		System.out.println(filename + " 파일을 정상적으로 불러왔습니다.");
		System.out.println("총 " + count + "개의 할 일이 있습니다.");
		
	}
}

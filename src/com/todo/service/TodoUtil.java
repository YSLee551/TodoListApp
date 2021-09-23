package com.todo.service;

import java.util.*;
import java.io.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n===[할 일 추가]===\n"
				+ "제목 >> ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("같은 제목이 이미 있습니다.");
			return;
		}
		sc.nextLine();
		System.out.print("내용 >> ");
		desc = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		System.out.println("할 일이 성공적으로 추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n===[할 일 삭제]===\n"
				+ "삭제할 항목의 제목 >> ");
		String title = sc.next();
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("할 일이 성공적으로 삭제되었습니다.");
				return;
			}
		}
		System.out.println("해당 제목을 찾지 못했습니다.");
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n===[할 일 수정]===\n"
				+ "수정할 항목의 제목 >> ");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("해당 제목이 목록에 없습니다.");
			return;
		}

		System.out.print("새 제목 >> ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("같은 제목이 이미 있습니다.");
			return;
		}
		
		sc.nextLine();
		
		System.out.print("새 내용 >> ");
		String new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("할 일이 성공적으로 수정되었습니다.");
			}
		}

	}

	public static void listAll(TodoList l) {
		System.out.println("\n***[전체 목록]***");
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
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
		try {
			BufferedReader fr = new BufferedReader(new FileReader(filename));
			
			String line;
			while ((line = fr.readLine()) != null) {
				StringTokenizer stk = new StringTokenizer(line,"##");
				TodoItem t = new TodoItem(stk.nextToken(), stk.nextToken());
				t.setCurrent_date(stk.nextToken());
				l.addItem(t);
			}
			fr.close();
		} catch (IOException e) {
			System.out.println(filename + " 파일을 열 수 없습니다.");
			return;
		}
		
		System.out.println(filename + " 파일을 정상적으로 불러왔습니다.");
	}
}

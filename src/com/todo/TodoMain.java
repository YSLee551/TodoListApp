package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		boolean isList = false;
		boolean quit = false;
		TodoUtil.loadList(l,"todolist.txt");
		Menu.displaymenu();
		do {
			Menu.prompt();
			isList = false;
			
			String raw_command = sc.nextLine().trim();
			String[] command_array = raw_command.split(" ");
			String command = command_array[0];
			String keyword = "";
			if(command_array.length >= 2)
				keyword = command_array[1];
			
			switch (command) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;

			case "ls_name_asc":
				l.sortByName();
				isList = true;
				break;

			case "ls_name_desc":
				l.sortByName();
				l.reverseList();
				isList = true;
				break;
				
			case "ls_date":
				l.sortByDate();
				isList = true;
				break;
			
			case "ls_date_desc":
				l.sortByDate();
				l.reverseList();
				isList = true;
				break;
				
			case "find":
				TodoUtil.find(l, keyword);
				break;
			
			case "find_cate":
				TodoUtil.find_cate(l, keyword);
				break;
				
			case "ls_cate":
				TodoUtil.ls_cate(l);
				break;
				
			case "save":
				TodoUtil.saveList(l,"todolist.txt");
				break;
				
			case "help":
				Menu.displaymenu();
				break;

			case "exit":
				quit = true;
				break;

			default:
				System.out.println("\n정확한 명령어를 입력하세요. 명령어 목록을 보시려면 \"help\"를 입력하세요.");
				break;
			}
			
			if(isList) TodoUtil.listAll(l);
		} while (!quit);
		System.out.println("앱을 종료합니다.");
	}
}

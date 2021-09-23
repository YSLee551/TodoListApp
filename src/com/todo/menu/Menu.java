package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("***[명령어 메뉴]***");
        System.out.println("1. 할 일 추가 ( 입력: add )");
        System.out.println("2. 할 일 삭제 ( 입력: del )");
        System.out.println("3. 할 일 수정 ( 입력: edit )");
        System.out.println("4. 전체 목록 보기 ( 입력: ls )");
        System.out.println("5. 제목순 정렬 ( 입력: ls_name_asc )");
        System.out.println("6. 제목역순 정렬 ( 입력: ls_name_desc )");
        System.out.println("7. 날짜순 정렬 ( 입력: ls_date )");
        System.out.println("8. 종료 ( 입력: exit 또는 esc 버튼)");
    }
    
    public static void prompt()
    {
    	System.out.print("\n실행할 메뉴 >> ");
    }
}

package com.example.springproject.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.springproject.domain.Board;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BoardController {
    @RequestMapping("/getBoardList")
    public String getBoardList(Model model) {
        //List 타입으로 Board 객체를 넣는 boardList 변수명 선언
        // = 대입연산자로 heap메모리에 ArrayList 타입으로 할당
        //List는 ArrayList의 부모클래스
        List<Board> boardList = new ArrayList<Board>();

        for (int i=1; i<=10; i++) {
            //Board 클래스로 board 인스턴스 생성
            Board board = new Board();
            //롬복으로 자동생성된 setter 메서드로 데이터 입력
            board.setSeq(new Long(i));
            board.setTitle("게시판 프로그램 테스트");
            board.setWriter("도우너");
            board.setContent("게시판 프로그램 테스트입니다...");
            board.setCreateDate(new Date());
            board.setCnt(0L);
            //boardList 배열에 board객체 넣기 (for문 10번)
            boardList.add(board);
        }
        model.addAttribute("boardList", boardList);
        System.out.println("출력");
        //viewResolver
        //return getBoardList라는 문자열로 templates에 있는 같은 이름에 html 파일을 찾는다
        return "getBoardList";
    }
}

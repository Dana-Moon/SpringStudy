package com.example.springproject.controller.board;

//내장 라이브러리 호출(import), 설치 x
import java.util.Date;
import java.util.List;

//외장 라이브러리 호출(import), gradle로 설치한 라이브러리
import com.example.springproject.entity.board.Board;
import com.example.springproject.service.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/getBoardList")
    public String getBoardList(Model model, Board board) {
        List<Board> boardList = boardService.getBoardList();
//        model.addAttribute("boardList", boardService.getBoardList());
        model.addAttribute("boardList", boardList);
        return "getBoardList";
    }

    @GetMapping("/insertBoard")
    public String insertBoard() {
        return "insertBoard";
    }

    @PostMapping("/insertBoard")
    public String insertBoard(Board board) {
        board.setCreateDate(new Date());

        /*클라이언트에서 board 객체를 받아서 매개변수로 사용
         * [1]BoardService의 insertBoard 메서드 실행
         * [2]BoardRepository(CrudRepository).save(board)를 통해서 (JPA번역)
         * DB의 저장(SQL)
         * insertBoard라는 메서드에 board객체 인자값으로 넣기
         * */
        boardService.insertBoard(board);
        return "redirect:getBoardList";
    }

    @GetMapping("/getBoard")
    public String getBoard(Board board, Model model) {
        model.addAttribute("board", boardService.getBoard(board));
        return "getBoard";
    }

/**
 * Board domain CONTROLLER
 * @Param String HTML에서 받아온 데이터
 * @return String HTML 파일과 연결 (ViewResolver)
 * @author Dana
 * @version 20220808.0.0.1 (예시)
 */
    @PostMapping ("/updateBoard")
    public String updateBoard(Board board) {
        boardService.updateBoard(board);
        return "redirect:getBoard?seq="+board.getSeq();
    }

    @GetMapping("/updateBoard")
    public String updateBoardView(Board board, Model model) {
        model.addAttribute("board", boardService.getBoard(board));
        return "insertBoard";
    }

    @PostMapping("/deleteBoard")
    public String deleteBoard(Board board) {
        boardService.deleteBoard(board);
        return "redirect:getBoardList";
    }
}


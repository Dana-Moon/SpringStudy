package com.example.springproject.controller.board;

//내장 라이브러리 호출(import), 설치 x
import java.util.Date;
import java.util.List;

//외장 라이브러리 호출(import), gradle로 설치한 라이브러리
import com.example.springproject.entity.account_info.Member;
import com.example.springproject.entity.board.Board;
import com.example.springproject.service.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(path = "/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/getBoardList")
    public String getBoardList(Model model, Board board) {
        List<Board> boardList = boardService.getBoardList();
//        model.addAttribute("boardList", boardService.getBoardList());
        model.addAttribute("boardList", boardList);
        return "/board/getBoardList";
    }

    @GetMapping("/insertBoard")
    public String insertBoard() {
        return "/board/insertBoard";
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
        return "redirect:/board/getBoardList";
    }

    @GetMapping("/getBoard")
    public String getBoard(Board board, Model model) {
        model.addAttribute("board", boardService.getBoard(board));
        return "/board/getBoard";
    }

/**
 * Board domain CONTROLLER
 * @Param String HTML에서 받아온 데이터
 * @return String HTML 파일과 연결 (ViewResolver)
 * @author Dana
 * @version 20220808.0.0.1 (예시)
 */
    @PostMapping ("/updateBoard")
    public String updateBoard(Board board, Model model) {
        model.addAttribute("board", boardService.updateBoard(board));
        return "redirect:/board/getBoard?seq="+board.getSeq();
    }

    @GetMapping("/updateBoard")
    public String updateBoardView(Board board, Model model) {
        model.addAttribute("board", boardService.updateBoard(board));
        return "/board/insertBoard";
    }

    @PostMapping("/deleteBoard")
    public String deleteBoard(Board board) {
        boardService.deleteBoard(board);
        return "redirect:/board/getBoardList";
    }

    // 8/22
    @GetMapping("/selectBoard")
    public String selectBoard(Member member, Model model) {
        System.out.println("---------board select-----------");
//        System.out.println(board.getSeq());
//        boardService.deleteBoard(board);

        //board.getId()는 클라이언트에서 가져옴

        //@Service에 board를 인자값으로 넣고 메서드 실행
//        boardService.getBoardListByMemberId(member);

        //회원이 작성한 게시글리스트(List<Board>)
        // > HTML에다가 뿌려주면 끝 (Controller에 가면 메서드가 실행되서 다른 결과물을 리턴 받기 때문)
        //어느 HTML로 가느냐? = 객체지향은 재활용성이 중요한 요인 중 하나
        //HTML 중에 재사용 할만한 것을 먼저 찾고, 그 후에 새로 만들기에 대해 고민
        // > getBoardList 가서 확인하기

        model.addAttribute("boardList",boardService.getBoardListByMemberId(member));
        return "redirect:/board/getBoardList";
    }
}


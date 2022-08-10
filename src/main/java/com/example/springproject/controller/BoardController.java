package com.example.springproject.controller;

//내장 라이브러리 호출(import), 설치 x
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//외장 라이브러리 호출(import), gradle로 설치한 라이브러리
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.springproject.domain.Board;

//board setter나 model addattribute 가 중복되는 것들이 보인다. 그런 것들을 클래스나 메소드에 넣어서 간결하게 쓸 수 있지 않을까? 를 고민해볼 수 있다.
@Controller
public class BoardController {

    static String title_string = "";
    static String writer_string = "";
    static String content_string = "";

    //step2. 배열 객체 사용
    static ArrayList<String> title_array = new ArrayList<String>();
    static ArrayList<String> writer_array = new ArrayList<String>();
    static ArrayList<String> content_array = new ArrayList<String>();

    //step3. 사용자 생성 객체 사용. 아래 array가 게시판의 DB가 되는 셈.
    static ArrayList<Board> board_array = new ArrayList<Board>();
    static int count = 0;

//    @GetMapping 또는 @PostMapping은 @RequestMapping의 자식 클래스이다.
//    @RequestMapping의 기능을 모두 쓸 수 있다.
//    자식클래스 어노테이션이 아닌 부모클래스 어노테이션을 쓰는 이유는 기능의 한정을 통해서
//    서버의 리소스 감소 및 보안을 위해서 이다.
//    보안을 위해 한정된 라이브러리만 사용하는 것이 좋다.(필요한 것만)
    @RequestMapping("/getBoardList")
    public String getBoardList(Model model) {
        //List 타입으로 Board 객체를 넣는 boardList 변수명 선언
        // = 대입연산자로 heap메모리에 ArrayList 타입으로 할당
        //List는 ArrayList의 부모클래스
//        List<Board> boardList = new ArrayList<Board>();

//        for (int i=1; i<=10; i++) {
//            //Board 클래스로 board 인스턴스 생성
//            Board board = new Board();
//            //롬복으로 자동생성된 setter 메서드로 데이터 입력
//            board.setSeq(new Long(i));
//            board.setTitle("게시판 프로그램 테스트");
//            board.setWriter("도우너");
//            board.setContent("게시판 프로그램 테스트입니다...");
//            //내장 클래스인 java.util.Date 객체로 시간 데이터 출력
//            board.setCreateDate(new Date());
//            board.setCnt(0L);
//            //boardList 배열에 board객체 넣기 (for문 10번)
//            boardList.add(board);
//        }

        //step2. 배열 객체 사용
        //title_array.size()로 게시판 글이 1개 이상일 경우에만 model에 데이터 입력하여 [클라이언트]에 전달
//        if (title_array.size() > 0) {
//            for(int i=0; i<title_array.size(); i++) {
//                Board board = new Board();
//                board.setSeq(new Long(i)+1);
//                board.setTitle(title_array.get(i));
//                board.setWriter(writer_array.get(i));
//                board.setContent(content_array.get(i));
//                //내장 클래스인 java.util.Date 객체로 시간 데이터 출력
//                board.setCreateDate(new Date());
//                board.setCnt(0L);
//                boardList.add(board);
//            }
//        }

//        아래의 것은 해보았으나, 필요가 없음.
//        if(board_array.size() > 0) {
//            for(int i=0; i<board_array.size(); i++) {
//                Board board = new Board();
//                board.setSeq(new Long(i)+1);
//                board.setTitle(title_array.get(i));
//                board.setWriter(writer_array.get(i));
//                board.setContent(content_array.get(i));
//                board.setCreateDate(new Date());
//                board.setCnt(0L);
//                board_array.add(board);
//            }
//        }
//        String str = content.length() > 20 ? content.substring(0, 20) : content;

        List<String> summary = new ArrayList<>();
        for(Board i : board_array) {
//            Board board = new Board();
            String str = i.getContent().length() > 20 ? i.getContent().substring(0,20) : i.getContent();
            summary.add(str);
//            String str = i.getContent();
//            summary.add(board) = board.getContent(i).length() > 20 ? board.getContent(i).substring(0, 20) : board.getContent(i);

        }
        model.addAttribute("summary", summary);

        //model 객체에 boardList(arrayList)를 boardList key값으로 넣음
        //attributeName = key
        //attributeValue = Value
        //model에는 참조타입만 넣을 수 있다 (addAttribute 메서드 안에 매개변수 타입으로 확인 가능)
        model.addAttribute("boardList", board_array);

        //디스패쳐 서블릿이 뷰 리졸버를 찾아서 연결해 줍니다.
        //viewResolver
        //return getBoardList라는 문자열로 templates에 있는 같은 이름에 html 파일을 찾는다
        return "getBoardList";
    }


    //@어노테이션은 메서드 혹은 클래스에 속성, 정의를 해서 스프링이나 자바에서 찾기 쉽도록 해주는 선언부. 스프링은 어노테이션을 권장함.
    //ex) @Override은 부모 메서드를 재저으이하여 사용한다고 자바나 스프링에게 속성 명시
    //@RequestParam : [클라이언트]에서 String 문자열을 [서버]에 전달하는 매개변수 선언
    //ex) RequestParam("title") String title에서 ("title")은 [클라이언트]의 name이라는 속성으로써 key값을 매개변수를 전달
    @RequestMapping("/getBoard")
    public String getBoard (
            @RequestParam("seq")String seq,
            @RequestParam("cate")String cate,
            @RequestParam("userRole")String userRole,
            @RequestParam("userId")String userId,
            @RequestParam("title")String title,
            @RequestParam("writer")String writer,
            @RequestParam("content")String content,
            @RequestParam("createDate")String createDate,
            @RequestParam("cnt")String cnt,
            Model model) {
        model.addAttribute("seq", seq);
        model.addAttribute("cate", cate);
        model.addAttribute("title", title);
        model.addAttribute("writer", writer);
        model.addAttribute("content", content);
        model.addAttribute("createDate", createDate);
        model.addAttribute("cnt", cnt);
        model.addAttribute("userId", userId);
        model.addAttribute("userRole", userRole);
        return "getBoard";
    }

    //@RequestMapping은 서버에서 디스페처서블릿을 통해 [클라이언트]html의 action 태그의 주소와 동일한 문자열을 찾는 매핑기능(연결)이 실행되고 하단의 메서드가 실행
    //return String인 이유는 뷰리졸버가 html 파일을 찾기 위한 문자열을 리턴. int나 다른 기본타입으로도 할 수 있다면 자바를 잘 사용하는 것임.
//    @RequestMapping(/"getBoardList")
/**
 * Board domain CONTROLLER
 * @Param String HTML에서 받아온 데이터
 * @return String HTML 파일과 연결 (ViewResolver)
 * @author Dana
 * @version 20220808.0.0.1 (예시)
 */

    @GetMapping("insertBoard")
    public String insertBoard() {
        return "insertBoard";
    }

    //[클라이언트]html form 태그의 method 속성의 값인 post를 인식하여 아래의 @PostMapping에 연결
    @PostMapping("insertBoard")
    public String insertBoard(@RequestParam("cate")String cate,
                               @RequestParam("title")String title,
                               @RequestParam("writer")String writer,
                               @RequestParam("content")String content,
                               Model model) {
//        title_string = title_t;
//        writer_string = writer_w;
//        content_string = content_c;

        //------------------------------------------------------------
//        선생님 방식 (이게 더 깔끔한 거 같다)

//        String str = content.length() > 20 ? content.substring(0, 20) : content;

//        String str = content.substring(0,20);
        count++;
        Board board = new Board();

        board.setSeq((long) count);
        board.setCate(cate);
        board.setTitle(title);
        board.setWriter(writer);
        board.setContent(content);
        board.setCreateDate(new Date());
        board.setCnt(0L);
        board_array.add(board);

        model.addAttribute("boardList", board_array);
        //------------------------------------------------------------

//        title_array.add(title_t);
//        model.addAttribute("title", title_t);
//
//        writer_array.add(writer_w);
//        model.addAttribute("writer", writer_w);
//
//        content_array.add(content_c);
//        model.addAttribute("content", content_c);

        //redirect와 forword를 이후에 배우게 될 예정.
        //redirect : 페이지 전환 이동 (getBoardList 페이지로 이동)

        /* BoardService의 insetBoard 메서드 실행 > BoardRepository
        * */
        return "redirect:getBoardList";
    }

    @GetMapping("deleteBoard")
    public String deleteBoard(@RequestParam("seq")String seq) {
        //seq 매개변수 (getBoard.html 에서 받아옴)로 board_array 배열에서
        //.getSeq로 같은 index를 찾아 board_array에 있는 board 객체 삭제 -> 원하는 게시글 삭제

//        System.out.println(seq);
        for(int i=0; i<board_array.size(); i++) {
            //board_array.get(i).getSeq() : board_array의 i번째 객체를 찾아서 getSeq() 메서드를 통해 seq 필드값 가져오기
            //equals() 메서드를 통해서 매개변수 seq 값과 비교 (참조 타입이므로)
            //seq 타입은 Long이다. Long : 소수점있는 데이터(숫자)이므로, 매개변수 seq(String)과 같은 타입이 아니므로 비교 불가
            //Long을 String으로 바꿔주어야 함. board_array.get(i).getSeq()의 값 Long을 String으로 변환 = Long.toString().
            if (Long.toString(board_array.get(i).getSeq()).equals(seq)) {
                //board_array의 i 번째 객체 삭제.
                board_array.remove(i);
            }
        }


        return "redirect:getBoardList";
    }


    //Post 방식으로 [클라이언트]에서 [서버]로 맵핑.
    @PostMapping("updateBoard")
    public String updateBoard (
            //HTML에서 name속성을 가진 값을 매개변수 String seq에 할당.
            @RequestParam("seq")String seq,
            @RequestParam("title")String title,
            @RequestParam("content")String content
    ) {
        //board_array 배열을 순회하여 board 객체의 seq 필드값을 매개변수 seq와 비교하여 true 값 찾기
        for(int i=0; i<board_array.size(); i++) {
            if(Long.toString(board_array.get(i).getSeq()).equals(seq)) {
                //setTitle과 같은 setter로 데이터 변경
                board_array.get(i).setTitle(title);
                board_array.get(i).setContent(content);
            }
        }
        return "redirect:getBoardList";
    }





}


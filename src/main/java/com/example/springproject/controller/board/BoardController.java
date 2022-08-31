package com.example.springproject.controller.board;

//내장 라이브러리 호출(import), 설치 x
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

//외장 라이브러리 호출(import), gradle로 설치한 라이브러리
import com.example.springproject.entity.account_info.Member;
import com.example.springproject.entity.board.Board;
import com.example.springproject.entity.board.Comments;
import com.example.springproject.entity.data.FileUploadEntity;
import com.example.springproject.repository.board.CommentsRepository;
import com.example.springproject.service.board.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@Controller
@Slf4j
@RequestMapping(path = "/board")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    protected BoardController(BoardService boardService) {
        this.boardService = boardService;
    }


    @GetMapping("/getBoardList")
    public String getBoardList(Model model) {
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
//        model.addAttribute("commentList",boardService.getComments(comments));
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
    public String updateBoard(Board board) {
         boardService.updateBoard(board);
        return "redirect:/board/getBoard?seq="+board.getSeq();
    }

    @GetMapping("/updateBoard")
    public String updateBoardView(Board board, Model model) {
        model.addAttribute("board", boardService.getBoard(board));
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

    @PostMapping("/insertComment")
    public String insertComment(Comments comments) {
        comments.setCreateDate(new Date());
        comments.setUpdateDate(new Date());
        boardService.insertComments(comments);
        return "redirect:/board/getBoard";
    }

    //board Seq 전달하면 전체 comments를 불러오는 controller method
//    @GetMapping("/getCommentList")
//    public String getCommentsList(Comments comments, Model model) {
//        model.addAttribute("CommentsList", boardService.getAllComments(comments));
//        return "/board/getCommentList";
//    }

    /* 8/31
    1)client에서 server로 이미지 파일 전송 (데이터 전송)
    html form태그에 upload버튼으로 이미지 데이터 전송(MultopartFile) > Entity 기준으로 데이터를 전달
    - server는 이미지 파일을 특정 폴더에 저장
    장점 : 서버에 원본 이미지 파일을 저장하므로 필요할 때, 서버에서 바로 전달 받을 수 있음 = db에 부담감이 줄어듦.
    단점 : 다수의 서버에 이미지 파일을 저장할 경우, 동일한 이미지 데이터 처리에 대한 이슈 발생 여부가 있다. = UUID()를 통해 이미지 이름을 구분해서 클라이언트에 책임 넘김(?)
    -server는 이미지 파일을 byte 코드로 db에 저장
    장점 : 이미지 데이터를 한 곳에 저장하고 관리
    단점 : DB에 많은 부하가 걸림. 데이터 저장 포맷의 한계. (oracle 기준으로 Blob 단위로 저장할 때 4gb 한계에 이슈)

    2) server에서 client로 이미지 전송
    springboot에서 URL주소를 통해 이미지를 받음. InputStream을 통해 파일을 http 프로토콜에 전달하여 클라이언트에게 전송.
     */

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("uploadfile")MultipartFile[] uploadfile) throws IOException {
        //MultipartFile을 클라이언트에서 서버로 RequestParam 데이터를 받아옴. name = uploadfile
        System.out.println("test");
        //@Slf4j Lombok 라이브러리로 log 데이터 찍음
        //info / error / debug 단위가 있고 단위마다 필터링하여 정보를 수집하고 관리 가능
        log.info("img load session");
        //MultipartFile 데이터를 수집하여 entity FileUploadEntity에 데이터 저장
        List<FileUploadEntity> list = new ArrayList<>();
        for (MultipartFile file : uploadfile) {
            //MultipartFile file이 있을 때까지 작업 진행
            if(!file.isEmpty()) {
                FileUploadEntity dto = new FileUploadEntity(null,
                        UUID.randomUUID().toString(),
                        file.getContentType(),
                        file.getName(),
                        file.getOriginalFilename());
                list.add(dto);
                //File은 java io이다.
                File newFileName = new File(dto.getUuid()+"_"+dto.getName()+".png");
                //file을 서버에 저장하는 스트림 행위는 서버가 성공할지 여부를 체크하므로 exception 처리 필요
                //메서드에 throws IOException 처리 = try catch 처리 필요
                file.transferTo(newFileName);
            }
        }

        return "/board/getBoardList";
    }

    //중괄호 안에 이름은 자기가 하고 싶은대로 지으면 된다.
    @GetMapping("/viewImage/{imgname}")
    public ResponseEntity<byte[]> viewImage(@PathVariable("imgname")String input_imgName) throws IOException {
        //ResponseEntity : http프로토콜을 통해서 byte 데이터를 전달하는 객체 (byte 배열로 전달.), byte(소문자 = 기본타입. 대문자로 시작하는 것은 참조타입이다.)
        //@PathVariable : URL 주소의 값을 받아옴.
        //InputStream 클라이언트에게 전송하는 규칙을 java io를 통해 정의하고 있다.
        String path = "C:\\\\\\\\Users\\\\\\\\user\\\\\\\\Desktop\\\\\\\\Coding\\\\\\\\spring\\\\\\\\SpringProject_version 2\\\\\\\\src\\\\\\\\main\\\\\\\\resources\\\\\\\\static\\\\\\\\upload"+input_imgName;
        //데이터(이미지)를 전송하기 위한 객체로서 java에서는 항상 데이터를 스트림타입으로 전달
        InputStream inputStream = new FileInputStream(path);
        //데이터를 잘라서 가져오기 때문에, 가져오는 중간에 문제가 생길 수도 있다. 그런데 그 때에 예외처리를 안하면 롤백을 해야하므로, 예외처리를 꼭 명시해주어야 한다.
        byte[] imgByteArr = toByteArray(inputStream);
        inputStream.close();

        //자원은 꼭 닫아주기!!
        //ResponseEntity를 통해 http프로토콜로 클라이언트에게 데이터 전송
        return new ResponseEntity<byte[]>(imgByteArr, HttpStatus.OK);
    }

}


package com.example.springproject.service.board;

import com.example.springproject.entity.account.Member;
import com.example.springproject.entity.board.Board;
import com.example.springproject.entity.board.Comments;
import com.example.springproject.entity.data.FileUploadEntity;
import com.example.springproject.repository.board.BoardRepository;
import com.example.springproject.repository.board.CommentsRepository;
import com.example.springproject.repository.board.FileUploadInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//JPA가 @Seivice로 선언된 클래스를 갖고 JDBC에게 기능적인 구현을 위한 속성
@Service
public class BoardServiceImpl implements BoardService {


    private final BoardRepository boardRepo;
    private final CommentsRepository commentsRepo;

    private final FileUploadInfoRepository fileUploadInfoRepo;

    @Autowired
    protected BoardServiceImpl(BoardRepository boardRepo, CommentsRepository commentsRepo, FileUploadInfoRepository fileUploadInfoRepo) {
        this.boardRepo = boardRepo;
        this.commentsRepo = commentsRepo;
        this.fileUploadInfoRepo = fileUploadInfoRepo;
    }
    //BoardRepository에 있는 DB와 연동하여 기능하는 것을 명시

    //클라이언트에서 받아온 Board 객체의 데이터를 BoardRepository의 상속받은 CrudRepository의
    @Override
    public List<Board> getBoardList() {
        return (List<Board>) boardRepo.findAll();
    }

    // 9/1 전까지는 void로 만들어서 진행함. 9/1에 BoardController에 insertBoard 수정하면서 Long 타입으로 변경. boardRepo.save(board).getseq();해줌
    @Override
    public Long insertBoard(Board board) {
        return boardRepo.save(board).getSeq();
    }

    @Override
    public Board getBoard(Board board) {
        return boardRepo.findById(board.getSeq()).get();
    }

    @Override
    public Board updateBoard(Board board) {
        Board findBoard = boardRepo.findById(board.getSeq()).get();
        findBoard.setTitle(board.getTitle());
        findBoard.setContent(board.getContent());
        return boardRepo.save(findBoard);
    }
    //클라이언트에서 받아온 Board 객체의 데이터를 BoardRepository의 상속받은 CrudRepository의 Save 메서드를 통해서
    //DB에 저장 (저장하는 SQL문 만들어서 실행)
    @Override
    public void deleteBoard(Board board) {
        boardRepo.deleteById(board.getSeq());
    }

    @Override
    public List<Board> getBoardListByMemberId(Member member) {
        return boardRepo.findAllByMemberIdEqualsBoardWriter(member.getId());
    }
    @Override
    public void insertComments(Comments comments) {
        commentsRepo.save(comments);
    }
    @Override
    public List<Comments> getAllComments(Comments comments) {
        return commentsRepo.findCommentsByBoard_seq(comments.getBoard_seq());
    }

//    @Override
//    public Board getBoard(Board board) {
//        return boardRepo.findById(board.getSeq()).get();
//    }
    @Override
    public Comments getComments(Comments comments) {
        return commentsRepo.findById(comments.getSeq()).get();
    }

    @Override
    public List<Comments> getCommentsList() {
        return (List<Comments>) commentsRepo.findAll();
    }

    @Override
    public Long insertFileUploadEntity(FileUploadEntity fileUploadEntity) {
        return fileUploadInfoRepo.save(fileUploadEntity).getId();
    }

    //이 구조를 이해하는 것이 중요하다. id를 가져와서 다른 테이블에서 사용할 수 있는(?) 그런 관계를 위해 아래외 같은 식이 필요하다.
    @Override
    public FileUploadEntity getFileUploadEntity(String board_seq) {
        return fileUploadInfoRepo.findByBoardSeq(Long.parseLong(board_seq));
    }

    @Override
    public FileUploadEntity getFileUploadEntity2(Long board_seq) {
        return fileUploadInfoRepo.
    }
}

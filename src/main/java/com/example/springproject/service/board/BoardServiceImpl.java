package com.example.springproject.service.board;

import com.example.springproject.entity.account_info.Member;
import com.example.springproject.entity.board.Board;
import com.example.springproject.repository.board.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//JPA가 @Seivice로 선언된 클래스를 갖고 JDBC에게 기능적인 구현을 위한 속성
@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepository boardRepo;
    //BoardRepository에 있는 DB와 연동하여 기능하는 것을 명시

    //클라이언트에서 받아온 Board 객체의 데이터를 BoardRepository의 상속받은 CrudRepository의
    @Override
    public List<Board> getBoardList() {
        return (List<Board>) boardRepo.findAll();
    }
    @Override
    public void insertBoard(Board board) {
        boardRepo.save(board);
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
}

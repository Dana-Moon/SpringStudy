package com.example.springproject.service.board;

import com.example.springproject.entity.account_info.Member;
import com.example.springproject.entity.board.Board;
import com.example.springproject.entity.board.Comments;

import java.util.List;

public interface BoardService {
    List<Board> getBoardList();

    void insertBoard(Board board);

    Board getBoard(Board board);

    Board updateBoard(Board board);

    void deleteBoard(Board board);

    List<Board> getBoardListByMemberId(Member member);

    void insertComments(Comments comments);
    List<Comments> getAllComments(Comments comments);

    Comments getComments(Comments comments);
    List<Comments> getCommentsList();
}

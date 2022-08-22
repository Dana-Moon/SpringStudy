package com.example.springproject.service.board;

import com.example.springproject.entity.account_info.Member;
import com.example.springproject.entity.board.Board;

import java.util.List;

public interface BoardService {
    List<Board> getBoardList();

    void insertBoard(Board board);

    Board getBoard(Board board);

    Board updateBoard(Board board);

    void deleteBoard(Board board);

    List<Board> getBoardListByMemberId(Member member);
}

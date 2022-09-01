package com.example.springproject.service.board;

import com.example.springproject.entity.account.Member;
import com.example.springproject.entity.board.Board;
import com.example.springproject.entity.board.Comments;
import com.example.springproject.entity.data.FileUploadEntity;

import java.util.List;

public interface BoardService {
    List<Board> getBoardList();

    Long insertBoard(Board board);

    Board getBoard(Board board);

    Board updateBoard(Board board);

    void deleteBoard(Board board);

    List<Board> getBoardListByMemberId(Member member);

    void insertComments(Comments comments);
    List<Comments> getAllComments(Comments comments);

    Comments getComments(Comments comments);
    List<Comments> getCommentsList();

    Long insertFileUploadEntity(FileUploadEntity fileUploadEntity);

    public FileUploadEntity getFileUploadEntity(String board_seq);

    public FileUploadEntity getFileUploadEntity2(Long board_seq);
}

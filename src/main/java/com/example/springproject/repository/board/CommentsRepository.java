package com.example.springproject.repository.board;

import com.example.springproject.entity.board.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// 어떤 entity 클래스인지, 기본키의 타입이 무엇인지
public interface CommentsRepository extends JpaRepository<Comments, Long> {
    @Query (value = "SELECT c FROM Comments c join fetch c.board WHERE c.board.seq = :input_board_seq")
    List<Comments> findCommentsByBoard_seq(Long input_board_seq);
}

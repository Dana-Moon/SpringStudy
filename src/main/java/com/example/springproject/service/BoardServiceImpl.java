package com.example.springproject.service;

import com.example.springproject.persistence.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//JPA가 @Seivice로 선언된 클래스를 갖고 JDBC에게 기능적인 구현을 위한 속성
@Service
public class BoardServiceImpl implements BoardService{
    @Autowired
    private BoardRepository boardRepo;

    //클라이언트에서 받아온 Board 객체의 데이터를 BoardRepository의 상속받은 CrudRepository의
    @Override
    public List<Board> getBoardList() {
        return (List<Board>) boardRepo.findAll;
    }

    //클라이언트에서 받아온 Board 객체의 데이터를 BoardRepository의 상속받은 CrudRepository의 Save 메서드를 통해서
    //DB에 저장 (저장하는 SQL문 만들어서 실행)
    @Override
}

package com.example.springproject.persistence;

import com.example.springproject.domain.Board;
import org.springframework.data.repository.CrudRepository;

//CrudRepository를 상속받음
//CrudRepository : Entity를 매개체로 create, read, update, delete를 가리킴
//CrudRepository<Board, Long>의 매개변수 Board(Entity)와 Long(PK 기본키의 타입)을 선언
//JPA가 어떤 객체로 어떤 타입으로 찾아야하는지 알아 들음

public interface BoardRepository extends CrudRepository<Board, Long> {

}

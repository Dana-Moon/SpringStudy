package com.example.springproject.repository.board;

import com.example.springproject.entity.board.Board;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

//CrudRepository를 상속받음
//CrudRepository : Entity를 매개체로 create, read, update, delete를 가리킴
//CrudRepository<Board, Long>의 매개변수 Board(Entity)와 Long(PK 기본키의 타입)을 선언
//JPA가 어떤 객체로 어떤 타입으로 찾아야하는지 알아 들음

public interface BoardRepository extends CrudRepository<Board, Long> {
    // 8/22 (아래부터 List<Board> findAllByMemberIdEqualsBoardWriter(String memberId); 까지
    //@Query(value = "SELECT c from Comment c where c.email = :email_1 or m.id = :id_1")
    //Member findMemberByEmailOrId(String email_1, String id_1);

    //튜닝 : JOIN과 WHERE의 순서를 정함으로써 SELECT 속도 튜닝을 어떻게 할지 전략적 구성 1>2>[3>4]
    //튜닝이라 하면 아카텍쳐 기술자가 되었을 때에나 고민할 수준.
    //유지보수를 하는 곳으로 간다면 생각할 일이 없겠으나, SI 쪽이나 대기업 쪽으로 간다면 이러한 것에 대해 물어볼 수도 있다.
    //Member의 튜플이 무척 많을 경우 WHERE 절을 통해 ID 검색 이후 Board와 JOIN 하는 것이 DB검색 속도에 유리

    //회원 ID를 검색하면 (writer와 ID가 동일) 관련된 writer의 게시글 모두 출력받아 리턴
    //inner join : ANSI query <> Oracle query
    //<> : 다르다는 뜻
    //Board의 튜플을 가져와야 하기 때문에 From Board(Board 테이블이 기준)
    //SELECT b FROM Board b // board 테이블의 튜플을 검색하겠다 (모든 컬럼) (1)
    //INNER JOIN Member m // member 테이블과 교집합 조인 (INNER JOIN) 하겠다 (2)
    //ON b.writer = m.id // board의 writer와 member의 id가 동일한 튜플을 검색하겠다 ( b는 board의 별칭, m은 memebr의 별칭) (3)
    //WHERE m.id = :memberId // INNER 조인한 튜플들의 결과물 중에 member.id가 매개변수 memberId와 동일한 조건을 걸겠다. (4)
    @Query(value = "SELECT b FROM Board b INNER JOIN Member m on b.writer = m.id WHERE m.id = :memberId")
    List<Board> findAllByMemberIdEqualsBoardWriter(String memberId);
}

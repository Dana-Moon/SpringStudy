package com.example.springproject.persistence.account_info;

import com.example.springproject.domain.account_info.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

//MemberRepository는 CrudRepository를 상속받아 기능을 온전히 씀
//CrudRepository : JPA를 통해 DB에 기본적인 SQL문을 통해 소통 (INSERT INTO, SELECT, UPDATE, DELETE)
//필수적으로 쓰인다.
//Repository에는 총 4종류가 있다.
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByIdOrPassword (String id, String password);

    //Return 내용 선언, Find~ 변수명에 맞춰서 메서드 생성, 필요한 매개변수
    //Member는 레파지토리와 연결되어 있어서, 아래처럼 Member를 꼭 안 써도 된다.
//    List<Member> findMemberByIdOrEmail(String Id, String Email);

    //(ID는 중복 가능한 구조에서) Id값의 일부분만 매개변수로 넣고, 아이디 생성날짜가 가장 최신인 것
//    void findFirstById(String Id);

    //m을 *라고 볼 수도 있지만, 개념이 다르다. m은 별칭이다. from Member m 이라고 하여 Member의 별칭 m이라고 써준 것임.
    //* 대신 별칭을 쓴다.
    @Query(value = "select m from Member m where m.email =:email or m.id = :id")
//    List<Member> findMemberByIdOrEmail(String id, String email);
    Member findMemberByIdOrEmail(String id, String email);

    //(ID는 중복 가능한 구조에서) Id 값을 매개변수로 넣고, 아이디 생성날짜가 가장 최신인 것
    @Query(value = "select m from Member m where m.id = :id order by m.createDate desc")
    Member findFirstById(String id);
}

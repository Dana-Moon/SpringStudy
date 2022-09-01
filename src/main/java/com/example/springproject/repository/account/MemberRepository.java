package com.example.springproject.repository.account;

import com.example.springproject.entity.account.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
//    @Query(value = "select m from Member m  where m.id = :id order by m.createDate desc")
//    Member findFirstById(String id);

    @Query(value = "select m from Member m where m.email like concat('%',:keyword,'%')")
    List<Member> findMembesrByEmail(String keyword);
//    @Query(value = "select m from Member m where m.email like concat('%',:email,'%')")
//    List<Member> findByEmail(String email);

    /* 8/26
    JPA는 메서드 이름으로 DB에 조회하는 기능
    JPQL : JPA를 통해 JPA에서 제공하는 쿼리문으로 조회 (단, 엔티티 기준으로만 조회 가능)
    NativeQuery : 일반 SQL문으로 DB 조회하며 보통 DTO 단위로 리턴 (Entity 단위로 리턴x)

    *jpql containing (SQL문의 like처럼 유사한 단어를 찾는 메서드명)
    List<Member> findByEmailContaining(String email);

    Member findAllBy 라고 할 경우 배열로 받아야 한다. List를 해주어야한다는 뜻.
    List<Member> findAllBy


    *jpql contians

     */
}

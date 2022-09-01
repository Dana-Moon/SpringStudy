package com.example.springproject.entity.account;

import com.example.springproject.entity.base.BaseTimeEntity;
import com.example.springproject.entity.board.Board;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// 8/12
//@AllArgsConstructor : 모든 매개변수를 갖는 생성자
//@NoArgsConstuctor : 매개변수 없는 생성자
//@Builder 를 쓰거나 @Setter를 쓰는 것을 알아보고 사용하기.

//@Entity가 JPA가 이 객체를 기준으로 table을 만들어야 한다고 선언
@Setter
@Getter
@ToString
@Entity
public class Member extends BaseTimeEntity implements Serializable {
    //@Id :table을 만들 때, 테이블의 튜플(row)를 식별할 수 있는 기본키
    //그 기본키 = seq (보통은 id이지만, board와 최대한 똑같이 만들고 싶기 때문에 seq로 진행.)

    /*SELECT * FROM TABLE_NAME;
    이런 구조에서 *의 위치는 컬럼명이고, 여기에는 객체의 필드가 온다. TABLE_NAME 도 객체
    CREATE TABLE (
        seq NUMBER primary key,
        id  VARCHAR2(40) NOT NULL
    )
    -JPA : 객체에 맞춰서 SQL문으로 바꿔주는 것 (번역)
    */

    /* 8/12
    - 어제(8/11)는 Getter와 Setter(Temporal에만 있었음)를 상단에 안 썼었는데, 막판에 위에 쓰니 해결됨.
    그것에 대해 선생님이 알아보니, 생성자를 만든 게 없으니까 Member 클래스에 아무것도 없는 것처럼 됨. 그저 껍데기.
    - 변경사항이 없을 것에는 setter를 붙이면 좋지 않아서, setter는 각각 요소에 써주는 것이 변경되는 것을 방지할 수 있다.
    */
    /* 8/25
    GeneratedValue에 대한 질문에서 출발하여, 답하기를.
    테이블마다 시퀀스가 있다. 이 Id 값을 다른 시퀀스와 공유를 할 것이다. 이것을 어떻게 처리할 지 4가지 전략이 있다.
     */
    /* 8/25 영속화 (아래 strategy 내용 얘기할 때 말함.)
    JVM 밖에서도 객체를 (영원히) 저장
    Commit, flush, persist를 포괄하는 내용
    SQL(MyBatis)는 DB틀에 맞춘 mapper라고 정의한다면, JPA는 객체 (Entity, 튜플) 단위로 데이터 베이스에 저장하는 개념을 영속화 한다고 정의

    Identity : DB에 필드값을 저장 후에 기본키를 생성함 (둘다 Id가 없는 상태로 들어간다?)
    Entity가 영속 상태에 되기 위해서는 식별자가 필수
    Sequence : DB(Oracle) Sequence 함수 기능을 활용하여 생성 (둘다 Id가 없는 상태로 들어간다?)
    Table : Seq(시퀀스)를 정보로 갖고 있는 테이블을 만들고, seq 컬럼값을 저장 뒤에 불러온다. (최적화가 되어 있지 않는 단점. 여타 위의 전력(strategy)과 달리 임의의 seq table을 만들기 때문에 table 선능이 좋지 않을 경우(튜닝하지 않을 경우), 속도적인 문제를 야기할 수 있다.)
     */

    /* 8/26
    빈번한 조회가 없다 = Identity / 빈번한 조회를 한 번에 해결해야 한다 = Sequence 이 좋다.
    꼭 필요한 정보는 아니다보니 구글링해도 나오는 결과가 별로 없을 듯 하다. 그러나 이 정보를 알고 있는 것이 구조를 짜는 데 있어서 도움을 줄 것이므로, 알려주는 것이다.

    * Identity
    Auto_INCREMENT처럼 DB에 값을 저장하고 나서야 기본키를 구할 수 있을 때 쓰임
    = DB에 클라이언트에서 받은 정보를 저장 후에 기본키((ex)seq)를 부여
    그런데 문제가 있다.
    Entity가 영속 상태가 되기 위해서는 식별자가 필수다.
    em : EntityManager
    em.persist()를 하는 즉시 insert SQL (기본키 저장)이 DB에 전달
    = 필드값을 테이블에 저장함과 동시에 기본키 생성해서 집어 넣는다.
    트랜잭션이 지원하는 쓰기 지연이 동작 x

    * Sequence : DB(Oracle) Sequence 함수 기능을 활용하여 생성
    DB마다 index를 생성하고 관리하는 함수가 있음 (DB에서 관리)
    시퀀스 전략은 em.persist()를 호출할 때 먼저 DB 시퀀스를 사용해서 식별자를 조회 이후 트랜잭션 커밋 시점에 플러시가 발생하면 Entity를 DB에 저장

    * Table : Seq(시퀀스)를 정보로 갖고 있는 테이블을 만들고, seq 컬럼값을 저장 뒤에 불러온다.
    (최적화가 되어 있지 않는 단점. 여타 위의 전력(strategy)과 달리 임의의 seq table을 만들기 때문에 table 선능이 좋지 않을 경우(튜닝하지 않을 경우), 속도적인 문제를 야기할 수 있다.)
    allocationSize 시퀀스 접근하는 횟수를 줄이기 위한 방편.

    * 예를 들어, allocationSize가 50이라면, 시퀀스 함수 한 번 조회 시 50씩 증가하고, 1~50 사이에서는 메모리에서 식별자를 할당.
    백엔드(서버)마다 DB를 조회해서 여러 서버가 동시에 접근하고, 시퀀스 함수를 사용하여 시퀀스를 할당할 때 1 단위로 size가 증가하면, DB 저장에 문제를 야기할 수 있으므로 size를 넓게 잡아 메모리가 알아서 접속한 서버마다 할당해주는 전략.
    ~50으로 시퀀스 값을 선정하므로, 여러 JVM (Spring boot 서버)가 동시에 동작해도 기본키 값이 충돌하지 않는 장점, DB 부하를 피할 수 있다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    /* 8/22
    table끼리 조인을 하는 조건
    1. Member.id는 member의 튜플이다. 유일한 값(unique 키)
    member마다 게시글(board)를 쓸 수 있다는 조건이 있으므로,
    board 입장에서는 member의 id가 유일해야지 식별할 수 있다.
    2. null 처리 (null이 들어가면 board는 Id는 식별할 수 없다.)
     */
    @Column(length = 40, nullable = false, unique = true)
    private String id;

    private String password;

    private String email;

//    @Temporal(TemporalType.DATE)
//    private Date createDate;
//
//    @Temporal(TemporalType.DATE)
//    private Date updateDate;

    //deleteYN

//    private String keyword;

    /* 8/22
    member는 여러 개의 board를 가질 수 있다고 선언
    board들을 가지고 있다고 필드에 넣음 (JPA는 이 필드 내용으로 테이블 연관관계(JOIN)으로 식별)
    @OneToMany는 1 튜플마다 여러 개의 board를 가진다는 속성 선언과 다수 Entity 연동에 Springboot는 Serializable 상속 요구함
    Selializable을 왜 implements 하였는가?라는 질문이 들어온다면, @OneToMany라는 어노테이션을 쓰기 위해서라고만 답변하면 해결됨.
     */
    @OneToMany(mappedBy = "member")
    private List<Board> boardList = new ArrayList<>();

    //memebr field로 board를 사용하기 위해서 implements Selializable을 해주어야 함.
    //이를 위해서는 Hibernate를 알아야 이해할 수 있어야 함. JPA보다 더 큰 범주의 지식이라고 할 수 있기 때문에 Hibernate에 대해 설명하지 않음.
}

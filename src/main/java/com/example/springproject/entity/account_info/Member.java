package com.example.springproject.entity.account_info;

import com.example.springproject.entity.base.BaseTimeEntity;
import com.example.springproject.entity.board.Board;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
    /* 8/25 영속화
    JVM 밖에서도 객체를 (영원히) 저장
    Commit, flush, persist를 포괄하는 내용
    SQL(MyBatis)는 DB틀에 맞춘 mapper라고 정의한다면, JPA는 객체 (Entity, 튜플) 단위로 데이터 베이스에 저장하는 개념을 영속화 한다고 정의

    Identity : DB에 필드값을 저장 후에 기본키를 생성함 (둘다 Id가 없는 상태로 들어간다?)
    Entity가 영속 상태에 되기 위해서는 식별자가 필수
    Sequence : DB(Oracle) Sequence 함수 기능을 활용하여 생성 (둘다 Id가 없는 상태로 들어간다?)
    Table : Seq(시퀀스)를 정보로 갖고 있는 테이블을 만들고, seq 컬럼값을 저장 뒤에 불러온다. (최적화가 되어 있지 않는 단점. 여타 위의 전력(strategy)과 달리 임의의 seq table을 만들기 때문에 table 선능이 좋지 않을 경우(튜닝x), 속도적인 문제를 야기할 수 있다.)
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

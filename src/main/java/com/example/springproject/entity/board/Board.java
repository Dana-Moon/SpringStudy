package com.example.springproject.entity.board;



//외장 라이브러리(gradle로 다운로드)
import com.example.springproject.entity.account.Member;
import com.example.springproject.entity.base.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//롬복에 있는 Getter라는 메서드를 통해 하단에 있는 클래스 Board는 자동으로 getter, setter 메서드가 생성됨을 암시함.
//암시 -> 어노테이션을 썼기 때문.
//@Entity가 class가 JPA를 통해 데이터베이스 테이블로 쓰겠다고 명시 해주는 속성
@Getter
@Setter
@ToString
@Entity
public class Board extends BaseTimeEntity {
    //@ID : PK (primary key) SQL문의 기본키
    //@GeneratedValue 자동생성 속성

//    @GenericGenerator(
//            name = "myBoardGenerator",
//            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
//            parameters = {
//                    @Parameter (name = "table_name", value = "MY_TABLE")
//            }
//    )
//    @GeneratedValue(generator = "myBoardGenerator")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

//    @Column()
//    private String cate;

    //@Column은 title 필드값을 컬럼화할 때, 깊이와 null 입력 가능 여부 옵션
    @Column(length = 40, nullable = false)
    private String title;

    @Column(nullable = false, updatable = false)
    private String writer;

    @Column(nullable = false)
    @ColumnDefault("'no content'")
    private String content;

//    @Temporal(TemporalType.DATE)
//    private Date createDate;
//
    @ColumnDefault("0")
    @Column(insertable = false, updatable = false)
    private Long cnt;

    //원래는 setter, getter라는 메서드가 있어야 private 필드값에 데이터를 넣을 수 있지만, (gradle 라이브러리 설치)롬복이라는 라이브러리로
    //자동 getter, setter 메서드 생성

    //deleteYN

    //@ManyToOne 다양한 board는 1개의 memebr를 바라본다
    //member를 필드에 선언
    //참조키가 어디인지 선언(memebr 기본키가 board의 참조키로 기본적으로 할당)
    //board의 writer는 member의 id와 연관되어 있고, 참조키로 id로 연결되어 있다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Member member;

    // 8/24
    @OneToMany(mappedBy = "board")
    private List<Comments> commentsList = new ArrayList<>();
}

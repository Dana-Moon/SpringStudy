package com.example.springproject.domain.account_info;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.util.Date;

// 8/12
//@AllArgsConstructor : 모든 매개변수를 갖는 생성자
//@NoArgsConstuctor : 매개변수 없는 생성자
//@Builder 를 쓰거나 @Setter를 쓰는 것을 알아보고 사용하기.

//@Entity가 JPA가 이 객체를 기준으로 table을 만들어야 한다고 선언
@Setter
@Getter
@ToString
@Entity
public class Member {
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
    @Id
    @GeneratedValue
    private Long seq;

    @Column(length = 40, nullable = false)
    private String id;

    private String password;

    private String email;

    @Temporal(TemporalType.DATE)
    private Date createDate;

    @Temporal(TemporalType.DATE)
    private Date updateDate;
}

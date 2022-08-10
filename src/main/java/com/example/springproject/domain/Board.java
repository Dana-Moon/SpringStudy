package com.example.springproject.domain;

import java.util.Date;

//외장 라이브러리(gradle로 다운로드)
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

//롬복에 있는 Getter라는 메서드를 통해 하단에 있는 클래스 Board는 자동으로 getter, setter 메서드가 생성됨을 암시함.
//암시 -> 어노테이션을 썼기 때문.
//@Entity가 class가 JPA를 통해 데이터베이스 테이블로 쓰겠다고 명시 해주는 속성
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Board {
    //@ID : PK (primary key) SQL문의 기본키
    //@GeneratedValue 자동생성 속성
    @ID @GeneratedValue
    private Long seq;


    private String cate;

    //@Column은 title 필드값을 컬럼화할 때, 깊이와 null 입력 가능 여부 옵션
    @Column(length = 40, updatable = false)
    private String title;

//    @Column(nullable = false, )
    private String writer;
    private String content;
    private Date createDate;
    private Long cnt;

    //원래는 setter, getter라는 메서드가 있어야 private 필드값에 데이터를 넣을 수 있지만, (gradle 라이브러리 설치)롬복이라는 라이브러리로
    //자동 getter, setter 메서드 생성
}

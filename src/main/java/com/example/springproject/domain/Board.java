package com.example.springproject.domain;

import java.util.Date;
import java.util.Locale;

//외장 라이브러리(gradle로 다운로드)
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//롬복에 있는 Getter라는 메서드를 통해 하단에 있는 클래스 Board는 자동으로 getter, setter 메서드가 생성됨을 암시함.
//암시 -> 어노테이션을 썼기 때문.
@Getter
@Setter
@ToString
public class Board {
    private Long seq;
    private String cate;
    private String title;
    private String writer;
    private String content;
    private Date createDate;
    private Long cnt;

    //원래는 setter, getter라는 메서드가 있어야 private 필드값에 데이터를 넣을 수 있지만, (gradle 라이브러리 설치)롬복이라는 라이브러리로
    //자동 getter, setter 메서드 생성
}

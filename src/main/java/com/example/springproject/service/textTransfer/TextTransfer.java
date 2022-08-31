package com.example.springproject.service.textTransfer;

import org.springframework.stereotype.Service;

@Service
public class TextTransfer {

    public String transferText3Word (String text) throws Exception {

        //java 문자열치환 내장메서드 : split, subString..

        //subString과 split 구조의 차이 (생각보다 중요함.)
        //subString : 원본 배열을 참조해서 순번과 길이만 가지고 지름 (객체를 따로 생성해서 관리 x)
        //split : 새로운 인스턴스를 만들어서 문자열을 자르고, 더불어 결과값을 String배열로 받아옴 (객체를 따로 생성해서 관리 O)
        String wordFirst3 = text.substring(0,3);
        String wordLast = text.substring(4, text.length());
        //replaceAll 메서드의 변경할 값에 "."을 쓰면 모든 문자를 지정.
        wordLast = wordLast.replaceAll(".", "*");
        System.out.println("앞의 3글자 = " +wordFirst3);
        System.out.println("뒤의 3글자 = " +wordLast);


        return wordFirst3+wordLast;
    }
}

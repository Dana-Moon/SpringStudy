package com.example.springproject;

import com.example.springproject.entity.account.Member;
import com.example.springproject.service.apiTest.ApiTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringProjectApplicationTests {

    //생성자 주입 방식이 에러를 뿜을 거라, 필드 생성 방식으로 만듦.
    @Autowired
    ApiTest apitest_1;


    @Test
    void apiTest_2() {
        apitest_1.resultAPI();
    };

    @Test
    @DisplayName("저장, 데이터가 잘 들어갔는지 확인")
    void contextSave() {
        //Setter로 엔티티를 생성하고 repository가 정상 작동하는지 확인
        Member member = new Member();
        //클라이언트에서 controller에 데이터를 전달하는 내용을 setter를 통해 대체
//        member.setId();
    }
}

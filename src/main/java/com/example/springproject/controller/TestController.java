package com.example.springproject.controller;

import com.example.springproject.entity.CustomDto.CustomAPIDtoExample;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class TestController {

    //CRUD RestFul API : rest하게 API를 만들자 (암묵적인 규칙)
    //Create, Read, Update, Delete
    //data를 전달하는 controller URI 주소 만들기
    @ResponseBody
    @RequestMapping("/read/alldata")
    public CustomAPIDtoExample readAlldata() {
        CustomAPIDtoExample dd = new CustomAPIDtoExample();

        dd.setName("Dana");
        dd.setGroup(1);
        dd.setPosition("학생");

        return dd;
    }

//    @ResponseBody
//    @RequestMapping("/test1")
//    public CustomAPIDtoExample test1() {
//
//    }
}

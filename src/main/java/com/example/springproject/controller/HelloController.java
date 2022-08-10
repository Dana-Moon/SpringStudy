package com.example.springproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//@Controller 선언을 해주면 HelloController 클래스는 컨트롤을 담당이라고 spring boot는 인식. 분기점을 알아서 찾는다.
@Controller
public class HelloController {

    private final static String main = "index";
    private final static String account = "account";

    //@GetMapping 선언을 해주면 특정 (html에서 지정한)url로 인식해서 받아오게 된다.
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!!");
        //아래 return hello는 템플릿에 hello.html로 이동
        return "hello";
    }
}

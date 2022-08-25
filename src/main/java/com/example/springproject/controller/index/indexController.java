package com.example.springproject.controller.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {
    /* 8/24
    index로 가는 것 만들기
     */
    @GetMapping("/index")
    public String index() {
        return "/index";
    }
}

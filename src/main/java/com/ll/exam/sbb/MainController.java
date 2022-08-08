package com.ll.exam.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @RequestMapping("/sbb") // GET POST 둘 다 Mapping 가능
    public void index() {
        System.out.println("index");
    }
}

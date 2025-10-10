package com.example.Demo_App;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {
    @GetMapping("/hello")
    String say_Hello(){
        return "Hii Spring_Boot!!";
    }

}

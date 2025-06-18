package com.demcha.spring_cart_api.controlers;


import com.demcha.spring_cart_api.entities.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {


    @RequestMapping("/hello")
    public Message sayHello(){
        return new Message("Hello World");
    }

}

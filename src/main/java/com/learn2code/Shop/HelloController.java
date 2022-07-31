package com.learn2code.Shop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("sayHello") //definicia endpointu - na adrese http://localhost:8080/sayHello nám ukaže na stranke "hello world"
    public String helloWorld() {
        return "hello world";
    }
}

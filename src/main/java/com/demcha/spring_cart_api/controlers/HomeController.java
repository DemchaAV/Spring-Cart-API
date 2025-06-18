package com.demcha.spring_cart_api.controlers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {

    @RequestMapping("/")
    public String index(Model model) {

        model.addAttribute("name", "Artem");
        return "hello";
    }

}

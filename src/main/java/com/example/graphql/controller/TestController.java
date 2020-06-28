package com.example.graphql.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author pcs
 * Date         2020/4/14
 * Description:
 */
@Controller
@RequestMapping("test")
public class TestController {

    @RequestMapping("book")
    public String book() {
        return "Book/list";
    }

    @RequestMapping("author")
    public String author() {
        return "Author/list";
    }

    @RequestMapping("character")
    public String character() {
        return "Character/list";
    }

    @RequestMapping("subscription")
    public String subscription() {
        return "Subscription/list";
    }
}

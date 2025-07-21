package com.skax.eatool.mbc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 테스트 컨트롤러
 */
@Controller
public class TestController {

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "Test Controller is working!";
    }

    @GetMapping("/mbc/test")
    @ResponseBody
    public String mbcTest() {
        return "MBC Test Controller is working!";
    }
}
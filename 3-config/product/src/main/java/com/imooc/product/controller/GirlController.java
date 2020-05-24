package com.imooc.product.controller;


import com.imooc.product.config.GirlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/girl")
public class GirlController {


    @Autowired
    private GirlConfig girlConfig;

    @GetMapping("/print")
    public String print() {
        return "name:" + girlConfig.getName() + ", age:" + girlConfig.getAge();
    }
}

package com.example.demo;

import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
    @Autowired
    KieHelper kieHelper;

    @RequestMapping("/fireAll")
    public String fireAll(){
        return null;
    }
}

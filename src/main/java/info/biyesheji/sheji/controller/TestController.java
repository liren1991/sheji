package info.biyesheji.sheji.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    @RequestMapping(value = "/testIndex.html")
    public Object testIndex(){
        return "404.html";
    }


}

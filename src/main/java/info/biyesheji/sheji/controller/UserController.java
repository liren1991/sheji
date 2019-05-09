package info.biyesheji.sheji.controller;

import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @RequestMapping("/shop.html")
    public Object shopIndex(ModelAndView modelAndView){


        return "shop.html";
    }


















}

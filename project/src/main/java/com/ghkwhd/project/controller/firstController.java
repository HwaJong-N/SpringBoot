package com.ghkwhd.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class firstController {

    @GetMapping("/hi")
    public String niceToMeetYou(Model model) {

        model.addAttribute("username", "HJ");
        return "greetings";
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model) {

        model.addAttribute("nickname", "HJ");
        return "goodbye";
    }
}

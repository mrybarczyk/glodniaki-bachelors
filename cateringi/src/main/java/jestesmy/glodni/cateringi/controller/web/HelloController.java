package jestesmy.glodni.cateringi.controller.web;

import jestesmy.glodni.cateringi.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    private final AuthenticationService authenticationService;

    @Autowired
    public HelloController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/")
    public String HomePage(Model model) {
        model.addAttribute("username", authenticationService.getCurrentUsername());
        return "index";
    }

}

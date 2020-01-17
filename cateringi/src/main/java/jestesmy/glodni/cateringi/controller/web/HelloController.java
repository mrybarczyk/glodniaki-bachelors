package jestesmy.glodni.cateringi.controller.web;

import jestesmy.glodni.cateringi.security.CurrentAuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @Autowired
    CurrentAuthenticatedUserService currentAuthenticatedUserService;

    @GetMapping("/")
    public String HomePage(Model model) {
        model.addAttribute("currentUser", currentAuthenticatedUserService.getCurrentUserName());
        return "redirect:/login";
    }

}

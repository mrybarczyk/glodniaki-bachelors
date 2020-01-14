package jestesmy.glodni.cateringi.controller.web.authentication;

import jestesmy.glodni.cateringi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public String showLoginForm(WebRequest request, Model model) {
        model.addAttribute("registered",false);
        return "login";
    }

    @GetMapping(params = "registered")
    public String showLoginFormAfterRegister(Model model) {
        model.addAttribute("registered",true);
        return "login";
    }


}

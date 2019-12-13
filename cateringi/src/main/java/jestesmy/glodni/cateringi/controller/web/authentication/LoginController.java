package jestesmy.glodni.cateringi.controller.web.authentication;

import jestesmy.glodni.cateringi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequestMapping("/login")
=======
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;

//@Controller
//@RequestMapping("/login")
>>>>>>> master
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public String showLoginForm(WebRequest request, Model model) {
        return "login";
    }


}

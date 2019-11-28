package jestesmy.glodni.cateringi.controller.web.authentication;

import jestesmy.glodni.cateringi.model.Account;
import jestesmy.glodni.cateringi.repository.AccountRepository;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    AccountRepository accountRepository;

    @GetMapping
    public String showLoginForm(WebRequest request, Model model) {
        return "login";
    }

    @PostMapping
    public String login(WebRequest request, Model model) {
        String login = request.getParameter("login");
        String password = Hex.encodeHexString(DigestUtils.md5Digest(request.getParameter("password").getBytes()));
        Account account = accountRepository.findByAccountName(login);
        if(account.getPassword().equals(password)) {
            return "index";
        }
        return "login";
    }

}

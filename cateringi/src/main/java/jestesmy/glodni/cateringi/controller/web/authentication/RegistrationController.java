package jestesmy.glodni.cateringi.controller.web.authentication;

import jestesmy.glodni.cateringi.model.Account;
import jestesmy.glodni.cateringi.repository.AccountRepository;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping()
    public String showRegistrationForm(WebRequest request, Model model) {
        Account account = new Account();
        model.addAttribute("account",account);
        return "registration";
    }

    @PostMapping()
    public ModelAndView createAccount(@ModelAttribute("account")Account account, WebRequest request, BindingResult result, Errors errors) {
        byte [] encrypted = DigestUtils.md5Digest(account.getPassword().getBytes());
        account.setPassword(Hex.encodeHexString(encrypted));
        accountRepository.save(account);
        return new ModelAndView("successRegistration","account",account);
    }

}

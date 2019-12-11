package jestesmy.glodni.cateringi.controller.web.authentication;

import jestesmy.glodni.cateringi.model.Client;
import jestesmy.glodni.cateringi.model.User;
import jestesmy.glodni.cateringi.model.UserType;
import jestesmy.glodni.cateringi.repository.ClientRepository;
import jestesmy.glodni.cateringi.repository.UserRepository;
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
@RequestMapping("/client/register")
public class ClientRegistrationController {

    private class RegistrationFormClient {
        private String userName;
        private String password;
        private String email;
        private String name;
        private String lastName;

        public RegistrationFormClient(){}

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping()
    public String showRegistrationForm(WebRequest request, Model model) {
        RegistrationFormClient user = new RegistrationFormClient();
        model.addAttribute("user", user);
        return "registration-client";
    }

    @PostMapping()
    public ModelAndView createAccount(@ModelAttribute("user") RegistrationFormClient user, WebRequest request, BindingResult result, Errors errors) {
        byte [] encrypted = DigestUtils.md5Digest(user.getPassword().getBytes());
        User registeredUser = new User(user.getUserName(),user.email,Hex.encodeHexString(encrypted));
        registeredUser.setUserType(UserType.CLIENT);
        Client registeredClient = new Client(user.getName(),user.getLastName());
        userRepository.save(registeredUser);
        registeredClient.setUser(registeredUser);
        clientRepository.save(registeredClient);
        return new ModelAndView("successRegistration","user", user);
    }

}

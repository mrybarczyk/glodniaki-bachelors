package jestesmy.glodni.cateringi.controller.web.authentication;

import jestesmy.glodni.cateringi.domain.model.Client;
import jestesmy.glodni.cateringi.domain.model.User;
import jestesmy.glodni.cateringi.domain.model.UserType;
import jestesmy.glodni.cateringi.domain.util.validation.UserValidator;
import jestesmy.glodni.cateringi.repository.ClientRepository;
import jestesmy.glodni.cateringi.repository.UserRepository;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/client/register")
public class ClientRegistrationController {

    private class RegistrationFormClient {
        private String userName;
        private String password;
        private String email;
        private String name;
        private String lastName;
        private String phoneNumber;

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

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserValidator userValidator;

    @GetMapping()
    public String showRegistrationForm(Model model) {
        RegistrationFormClient user = new RegistrationFormClient();
        model.addAttribute("user", user);
        model.addAttribute("errors",new ArrayList<String>());
        return "registration-client";
    }

    @PostMapping()
    public String createAccount(@ModelAttribute("user") RegistrationFormClient user,Model model) {
        byte [] encrypted = DigestUtils.md5Digest(user.getPassword().getBytes());
        User registeredUser = new User(user.getUserName(),user.email,user.getPhoneNumber(),Hex.encodeHexString(encrypted));
        registeredUser.setUserType(UserType.CLIENT);
        Client registeredClient = new Client(user.getName(),user.getLastName());
        List<String> validationErrors = userValidator.validate(registeredUser);
        if(validationErrors.isEmpty()) {
            userRepository.save(registeredUser);
            registeredClient.setUser(registeredUser);
            clientRepository.save(registeredClient);
            return "redirect:/login?registered";
        } else {
            model.addAttribute("user",user);
            model.addAttribute("errors",validationErrors);
            return "registration-company";
        }
    }

}

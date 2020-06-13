package jestesmy.glodni.cateringi.controller.web.authentication;

import jestesmy.glodni.cateringi.domain.model.User;
import jestesmy.glodni.cateringi.domain.model.UserType;
import jestesmy.glodni.cateringi.domain.model.Admin;
import jestesmy.glodni.cateringi.repository.AdminRepository;
import jestesmy.glodni.cateringi.repository.UserRepository;
import jestesmy.glodni.cateringi.security.CurrentAuthenticatedUserService;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/register")
public class AdminRegistrationController {

    private class RegistrationFormAdmin {
        private String userName;
        private String password;
        private String email;
        private String name;
        private String lastName;
        private String phoneNumber;

        public RegistrationFormAdmin(){}

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
    private AdminRepository adminRepository;

    @Autowired
    private CurrentAuthenticatedUserService currentAuthenticatedUserService;

    @GetMapping()
    public String showRegistrationForm(Model model) {
        RegistrationFormAdmin user = new RegistrationFormAdmin();
        model.addAttribute("admin",adminRepository.findByUser(currentAuthenticatedUserService.getCurrentUser()));
        model.addAttribute("user", user);
        return "registration-admin";
    }

    @PostMapping()
    public String createAccount(@ModelAttribute("user") RegistrationFormAdmin user) {
        byte [] encrypted = DigestUtils.md5Digest(user.getPassword().getBytes());
        User registeredUser = new User(user.getUserName(),user.email, user.phoneNumber, Hex.encodeHexString(encrypted));
        registeredUser.setUserType(UserType.ADMIN);
        Admin registeredAdmin = new Admin(user.getName(),user.getLastName());
        userRepository.save(registeredUser);
        registeredAdmin.setUser(registeredUser);
        adminRepository.save(registeredAdmin);
        return "redirect:/admin/userlist";
    }

}

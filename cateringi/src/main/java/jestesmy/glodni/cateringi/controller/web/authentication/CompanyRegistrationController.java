package jestesmy.glodni.cateringi.controller.web.authentication;

import jestesmy.glodni.cateringi.domain.model.Company;
import jestesmy.glodni.cateringi.domain.model.User;
import jestesmy.glodni.cateringi.domain.model.UserType;
import jestesmy.glodni.cateringi.repository.CompanyRepository;
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

@Controller
@RequestMapping("/company/register")
public class CompanyRegistrationController {

    private class RegistrationFormCompany {
        private String userName;
        private String email;
        private String password;
        private String companyName;
        private String nip;
        private String regon;
        private String websiteAddress;
        private String phoneNumber;

        public RegistrationFormCompany(){
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getNip() {
            return nip;
        }

        public void setNip(String nip) {
            this.nip = nip;
        }

        public String getRegon() {
            return regon;
        }

        public void setRegon(String regon) {
            this.regon = regon;
        }

        public String getWebsiteAddress() {
            return websiteAddress;
        }

        public void setWebsiteAddress(String websiteAddress) {
            this.websiteAddress = websiteAddress;
        }
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping()
    public String showRegistrationForm(WebRequest request, Model model) {
        RegistrationFormCompany user = new RegistrationFormCompany();
        model.addAttribute("user", user);
        return "registration-company";
    }

    @PostMapping()
    public String createAccount(@ModelAttribute("user") RegistrationFormCompany user, WebRequest request, BindingResult result, Errors errors) {
        byte [] encrypted = DigestUtils.md5Digest(user.getPassword().getBytes());
        User registeredUser = new User(user.getUserName(),user.getEmail(),user.getPhoneNumber(),Hex.encodeHexString(encrypted));
        Company registeredCompany = new Company(user.getCompanyName(),user.getNip(),user.getRegon(),user.getWebsiteAddress());
        registeredUser.setUserType(UserType.COMPANY);
        userRepository.save(registeredUser);
        registeredCompany.setUser(registeredUser);
        companyRepository.save(registeredCompany);

        return "redirect:/login?registered";
    }

}

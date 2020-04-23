package jestesmy.glodni.cateringi.controller.web.company;

import jestesmy.glodni.cateringi.domain.model.Company;
import jestesmy.glodni.cateringi.domain.model.User;
import jestesmy.glodni.cateringi.domain.util.CompanyWithUser;
import jestesmy.glodni.cateringi.repository.CompanyRepository;
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
import org.springframework.web.context.request.WebRequest;


@Controller
@RequestMapping("/company")
public class CompanyController {

    private final CurrentAuthenticatedUserService currentAuthenticatedUserService;

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyController(CurrentAuthenticatedUserService currentAuthenticatedUserService, CompanyRepository companyRepository) {
        this.currentAuthenticatedUserService = currentAuthenticatedUserService;
        this.companyRepository = companyRepository;
    }

    @GetMapping
    public String showWelcomeMessage(Model model){
        User user = currentAuthenticatedUserService.getCurrentUser();
        Company company = companyRepository.findByUser(user);
        model.addAttribute("company", company);
        return "welcome-company";
    }

    @GetMapping("settings")
    public String showSettingsForm(Model model) {
        User user = currentAuthenticatedUserService.getCurrentUser();
        Company company = companyRepository.findByUser(user);
        CompanyWithUser cwu = new CompanyWithUser(company, user);
        model.addAttribute("company", company);
        model.addAttribute("cwu", cwu);
        model.addAttribute("passwordChanged",false);
        return "company-settings";
    }

    @GetMapping(value="settings", params="passwordChanged")
    public String showSettingsFormWithInfo(Model model) {
        User user = currentAuthenticatedUserService.getCurrentUser();
        Company company = companyRepository.findByUser(user);
        CompanyWithUser cwu = new CompanyWithUser(company, user);
        model.addAttribute("company", company);
        model.addAttribute("cwu", cwu);
        model.addAttribute("passwordChanged",true);
        return "company-settings";
    }

    @PostMapping("update")
    public String updateCompanyInfo(@ModelAttribute("cwu")CompanyWithUser modified) {
        User user = currentAuthenticatedUserService.getCurrentUser();
        Company company = companyRepository.findByUser(user);
        user.setEmail(modified.getEmail());
        user.setPhoneNumber(modified.getPhoneNumber());
        company.setName(modified.getCompanyName());
        company.setWebsiteAddress(modified.getWebsiteAddress());
        companyRepository.save(company);
        return "redirect:/company";
    }

    @GetMapping("update/password")
    public String showPasswordForm(Model model) {
        User user = currentAuthenticatedUserService.getCurrentUser();
        Company company = companyRepository.findByUser(user);
        model.addAttribute("company",company);
        return "company-change-password";
    }

    @PostMapping("update/password")
    public String updatePassword(WebRequest request) {
        User user = currentAuthenticatedUserService.getCurrentUser();
        String oldPassword = request.getParameter("oldPassword");
        if(!oldPassword.isEmpty()) {
            byte[] encrypted = DigestUtils.md5Digest(oldPassword.getBytes());
            String oldPasswordMd5 = Hex.encodeHexString(encrypted);
            if(oldPasswordMd5.equals(user.getPassword())) {
                String newPassword = request.getParameter("newPassword");
                if(!newPassword.isEmpty()) {
                    byte[] newEncrypted = DigestUtils.md5Digest(newPassword.getBytes());
                    String newPasswordMd5 = Hex.encodeHexString(newEncrypted);
                    Company company = companyRepository.findByUser(user);
                    user.setPassword(newPasswordMd5);
                    companyRepository.save(company);
                    return "redirect:/company/settings?passwordChanged";
                }
            }
        }
        return "redirect:/company";
    }
}

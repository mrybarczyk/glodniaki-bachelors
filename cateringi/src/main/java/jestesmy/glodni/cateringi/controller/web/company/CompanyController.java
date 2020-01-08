package jestesmy.glodni.cateringi.controller.web.company;

import jestesmy.glodni.cateringi.model.Company;
import jestesmy.glodni.cateringi.model.User;
import jestesmy.glodni.cateringi.model.util.CompanyWithUser;
import jestesmy.glodni.cateringi.repository.CompanyRepository;
import jestesmy.glodni.cateringi.security.CurrentAuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


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
        CompanyWithUser companyUser = new CompanyWithUser(company,user);
        model.addAttribute("company",companyUser);
        return "welcome-company";
    }

    @GetMapping("settings")
    public String showSettingsForm(Model model) {
        User user = currentAuthenticatedUserService.getCurrentUser();
        Company company = companyRepository.findByUser(user);
        CompanyWithUser companyUser = new CompanyWithUser(company,user);
        model.addAttribute("company",companyUser);
        return "company-settings";
    }

    @PostMapping("update")
    public String updateCompanyInfo(Model model, @ModelAttribute("company")CompanyWithUser modified) {
        User user = currentAuthenticatedUserService.getCurrentUser();
        Company company = companyRepository.findByUser(user);
        user.setEmail(modified.getEmail());
        user.setPhoneNumber(modified.getPhoneNumber());
        company.setName(modified.getCompanyName());
        company.setWebsiteAddress(modified.getWebsiteAddress());
        companyRepository.save(company);
        return "welcome-company";
    }

}

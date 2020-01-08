package jestesmy.glodni.cateringi.controller.web.company;

import jestesmy.glodni.cateringi.domain.model.Company;
import jestesmy.glodni.cateringi.domain.model.User;
import jestesmy.glodni.cateringi.repository.CompanyRepository;
import jestesmy.glodni.cateringi.security.CurrentAuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        model.addAttribute("user",user);
        model.addAttribute("company",company);
        return "welcome-company";
    }

}

package jestesmy.glodni.cateringi.controller.web.company;

import jestesmy.glodni.cateringi.domain.model.Company;
import jestesmy.glodni.cateringi.domain.model.Rate;
import jestesmy.glodni.cateringi.repository.ClientRepository;
import jestesmy.glodni.cateringi.repository.CompanyRepository;
import jestesmy.glodni.cateringi.repository.RateRepository;
import jestesmy.glodni.cateringi.security.CurrentAuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/company/rating")
public class RateCompanyController {

    private CurrentAuthenticatedUserService currentAuthenticatedUserService;

    private RateRepository rateRepository;

    private CompanyRepository companyRepository;

    @Autowired
    public RateCompanyController(CurrentAuthenticatedUserService currentAuthenticatedUserService,
                                 RateRepository rateRepository, CompanyRepository companyRepository) {
        this.currentAuthenticatedUserService = currentAuthenticatedUserService;
        this.rateRepository = rateRepository;
        this.companyRepository = companyRepository;
    }

    @GetMapping("all")
    public String getRatings(Model model) {
        Company company = companyRepository.findByUser(currentAuthenticatedUserService.getCurrentUser());
        List<Rate> rates = rateRepository.findByCompany(company);
        model.addAttribute("company", company);
        model.addAttribute("rates", rates);
        return "client-company-rates";
    }
}

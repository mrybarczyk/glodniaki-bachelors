package jestesmy.glodni.cateringi.controller.web.client;

import jestesmy.glodni.cateringi.domain.model.Client;
import jestesmy.glodni.cateringi.domain.model.Company;
import jestesmy.glodni.cateringi.domain.model.Rate;
import jestesmy.glodni.cateringi.repository.*;
import jestesmy.glodni.cateringi.security.CurrentAuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/client/company/rating")
public class RateClientController {

    private CurrentAuthenticatedUserService currentAuthenticatedUserService;

    private ClientRepository clientRepository;

    private RateRepository rateRepository;

    private CompanyRepository companyRepository;

    @Autowired
    public RateClientController(CurrentAuthenticatedUserService currentAuthenticatedUserService,
                                ClientRepository clientRepository, RateRepository rateRepository,
                                CompanyRepository companyRepository) {
        this.currentAuthenticatedUserService = currentAuthenticatedUserService;
        this.clientRepository = clientRepository;
        this.rateRepository = rateRepository;
        this.companyRepository = companyRepository;
    }

    @GetMapping("all/{companyID}")
    public String companyRatings(@PathVariable("companyID") int companyID, Model model) {
        Client client = clientRepository.findByUser(currentAuthenticatedUserService.getCurrentUser());
        Company company = companyRepository.findById(companyID).get();
        List<Rate> rates = rateRepository.findByCompany(company);
        model.addAttribute("company",company);
        model.addAttribute("client",client);
        model.addAttribute("rates", rates);
        return "client/client-company-rates";
    }
}

package jestesmy.glodni.cateringi.controller.web.company;

import jestesmy.glodni.cateringi.domain.model.Company;
import jestesmy.glodni.cateringi.domain.model.User;
import jestesmy.glodni.cateringi.repository.*;
import jestesmy.glodni.cateringi.security.CurrentAuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/company/orders")
public class OrderCompanyController {

    private CurrentAuthenticatedUserService currentAuthenticatedUserService;

    private CompanyRepository companyRepository;

    private OrderRepository orderRepository;

    @Autowired
    public OrderCompanyController(CurrentAuthenticatedUserService currentAuthenticatedUserService,
                                  CompanyRepository companyRepository, OrderRepository orderRepository) {
        this.currentAuthenticatedUserService = currentAuthenticatedUserService;
        this.companyRepository = companyRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("history")
    public String allCompanyOrders(Model model){
        User user = currentAuthenticatedUserService.getCurrentUser();
        Company company = companyRepository.findByUser(user);
        model.addAttribute("company", company);
        model.addAttribute("orders", orderRepository.findByCompany(company));
        return "company/company-order-history";
    }
}

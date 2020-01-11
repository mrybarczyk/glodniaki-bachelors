package jestesmy.glodni.cateringi.controller.web.service;

import jestesmy.glodni.cateringi.domain.model.*;
import jestesmy.glodni.cateringi.repository.*;
import jestesmy.glodni.cateringi.security.CurrentAuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private CurrentAuthenticatedUserService currentAuthenticatedUserService;

    private ServiceRepository serviceRepository;

    private CompanyRepository companyRepository;

    private ClientRepository clientRepository;

    private ServiceVariantRepository serviceVariantRepository;

    private OrderRepository orderRepository;

    @Autowired
    public OrderController(CurrentAuthenticatedUserService currentAuthenticatedUserService,
                                              ServiceRepository serviceRepository,
                                              CompanyRepository companyRepository, ClientRepository clientRepository,
                                              ServiceVariantRepository serviceVariantRepository, OrderRepository orderRepository) {
        this.currentAuthenticatedUserService = currentAuthenticatedUserService;
        this.serviceRepository = serviceRepository;
        this.companyRepository = companyRepository;
        this.clientRepository = clientRepository;
        this.serviceVariantRepository = serviceVariantRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping()
    public String home(HttpServletResponse httpServletResponse, Model model) throws IOException {
        if(currentAuthenticatedUserService.getCurrentUser().getUserType() == UserType.COMPANY) {
            model.addAttribute("company", companyRepository.findByUser(currentAuthenticatedUserService.getCurrentUser()));
            httpServletResponse.sendRedirect("/company");
        }

        User user = currentAuthenticatedUserService.getCurrentUser();
        Client client = clientRepository.findByUser(user);
        model.addAttribute("user", user);
        model.addAttribute("client", client);
        model.addAttribute("orders", orderRepository.findByClient(client));
        return "client-orders";
    }
//
//    @GetMapping("{serviceVariantID}/new")
//    public String newOrder(@PathVariable("serviceVariantID") int serviceVariantID, Model model){
//        User user = currentAuthenticatedUserService.getCurrentUser();
//        Client client = clientRepository.findByUser(user);
//        Order order = new Order();
//        order.setServiceVariant(serviceVariantRepository.findById(serviceVariantID).get());
//        model.addAttribute("user", user);
//        model.addAttribute("client", client);
//        model.addAttribute("order", order);
//        return "client-order-new";
//    }
}

package jestesmy.glodni.cateringi.controller.web.client;

import jestesmy.glodni.cateringi.domain.model.*;
import jestesmy.glodni.cateringi.repository.*;
import jestesmy.glodni.cateringi.security.CurrentAuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/client/orders")
public class OrderClientController {

    private CurrentAuthenticatedUserService currentAuthenticatedUserService;

    private ClientRepository clientRepository;

    private ServiceVariantRepository serviceVariantRepository;

    private OrderRepository orderRepository;

    @Autowired
    public OrderClientController(CurrentAuthenticatedUserService currentAuthenticatedUserService,
                                 ClientRepository clientRepository, ServiceVariantRepository serviceVariantRepository,
                                 OrderRepository orderRepository) {
        this.currentAuthenticatedUserService = currentAuthenticatedUserService;
        this.clientRepository = clientRepository;
        this.serviceVariantRepository = serviceVariantRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping()
    public String allClientOrders(Model model){
        User user = currentAuthenticatedUserService.getCurrentUser();
        Client client = clientRepository.findByUser(user);
        model.addAttribute("user", user);
        model.addAttribute("client", client);
        model.addAttribute("orders", orderRepository.findByClient(client));
        return "client-orders";
    }

    @GetMapping("{serviceVariantID}/new")
    public String newOrder(@PathVariable("serviceVariantID") int serviceVariantID, Model model){
        User user = currentAuthenticatedUserService.getCurrentUser();
        Client client = clientRepository.findByUser(user);
        Order order = new Order();
        order.setServiceVariant(serviceVariantRepository.findById(serviceVariantID).get());
        model.addAttribute("user", user);
        model.addAttribute("client", client);
        model.addAttribute("order", order);
        return "client-order-new";
    }

    @PostMapping("{serviceVariantID}/add")
    public String addOrder(@PathVariable("serviceVariantID") int serviceVariantID, Order order){
        User user = currentAuthenticatedUserService.getCurrentUser();
        LocalDateTime now = LocalDateTime.now();
        Client client = clientRepository.findByUser(user);
        order.setServiceVariant(serviceVariantRepository.findById(serviceVariantID).get());
        order.setCompany(order.getServiceVariant().getService().getCompany());
        order.setClient(client);
        order.setOrderDate(Timestamp.valueOf(now));
        order.setToDate(Timestamp.valueOf(now.plusDays(order.getServiceVariant().getDayNumber())));
        return "redirect:/orders";
    }
}

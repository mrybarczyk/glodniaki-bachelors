package jestesmy.glodni.cateringi.controller.web.client;

import jestesmy.glodni.cateringi.domain.model.*;
import jestesmy.glodni.cateringi.repository.*;
import jestesmy.glodni.cateringi.security.CurrentAuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("new")
    public String newOrder(ServiceVariant selectedVariant, Model model){
        User user = currentAuthenticatedUserService.getCurrentUser();
        Client client = clientRepository.findByUser(user);
        LocalDateTime now = LocalDateTime.now();
        Order order = new Order();
        order.setClient(client);
        order.setServiceVariant(serviceVariantRepository.findById(selectedVariant.getServiceVariantID()).get());
        order.setCompany(order.getServiceVariant().getService().getCompany());
        order.setOrderDate(Timestamp.valueOf(now));
        order.setToDate(Timestamp.valueOf(now.plusDays(order.getServiceVariant().getDayNumber())));
        order.setIsPaid(false);
        orderRepository.save(order);
        model.addAttribute("user", user);
        model.addAttribute("client", client);
        model.addAttribute("order", order);
        model.addAttribute("total",order.getServiceVariant().getPrice()*order.getServiceVariant().getDayNumber());
        return "client-order-summary";
    }

    @PostMapping("add")
    public String addOrder(@ModelAttribute("order") Order order){
        Order paidOrder = orderRepository.findById(order.getOrderID()).get();
        paidOrder.setIsPaid(true);
        orderRepository.save(paidOrder);
        return "redirect:/orders";
    }
}

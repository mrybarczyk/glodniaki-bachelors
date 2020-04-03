package jestesmy.glodni.cateringi.controller.web.client;

import jestesmy.glodni.cateringi.domain.model.*;
import jestesmy.glodni.cateringi.domain.util.ServiceVariantIDWithAddress;
import jestesmy.glodni.cateringi.repository.*;
import jestesmy.glodni.cateringi.security.CurrentAuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/client/orders")
public class OrderClientController {

    private CurrentAuthenticatedUserService currentAuthenticatedUserService;

    private ClientRepository clientRepository;

    private ServiceVariantRepository serviceVariantRepository;

    private OrderRepository orderRepository;

    private RateRepository rateRepository;

    private CompanyRepository companyRepository;

    @Autowired
    public OrderClientController(CurrentAuthenticatedUserService currentAuthenticatedUserService,
                                 ClientRepository clientRepository, ServiceVariantRepository serviceVariantRepository,
                                 OrderRepository orderRepository, RateRepository rateRepository, CompanyRepository companyRepository) {
        this.currentAuthenticatedUserService = currentAuthenticatedUserService;
        this.clientRepository = clientRepository;
        this.serviceVariantRepository = serviceVariantRepository;
        this.orderRepository = orderRepository;
        this.rateRepository = rateRepository;
        this.companyRepository = companyRepository;
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
        ServiceVariant serviceVariant = serviceVariantRepository.findById(selectedVariant.getServiceVariantID()).get();
        Company company = serviceVariant.getService().getCompany();
        ServiceVariantIDWithAddress serviceVariantIDWithAddress = new ServiceVariantIDWithAddress();
        serviceVariantIDWithAddress.setSelectedServiceVariantID(serviceVariant.getServiceVariantID());
        model.addAttribute("serviceVariantIDWithAddress", serviceVariantIDWithAddress);
        model.addAttribute("user", user);
        model.addAttribute("client", client);
        model.addAttribute("serviceVariant", serviceVariant);
        model.addAttribute("total",selectedVariant.getPrice()*selectedVariant.getDayNumber());
        model.addAttribute("company", company);
        return "client-order-summary";
    }

    @PostMapping("add")
    public String addOrder(@ModelAttribute("serviceVariantIDWithAddress") ServiceVariantIDWithAddress serviceVariantIDWithAddress){
        User user = currentAuthenticatedUserService.getCurrentUser();
        Client client = clientRepository.findByUser(user);
        LocalDateTime now = LocalDateTime.now();
        Order order = new Order();
        order.setClient(client);
        order.setServiceVariant(serviceVariantRepository.findById(serviceVariantIDWithAddress.getSelectedServiceVariantID()).get());
        order.setCompany(order.getServiceVariant().getService().getCompany());
        order.setOrderDate(Timestamp.valueOf(now));
        order.setToDate(Timestamp.valueOf(now.plusDays(order.getServiceVariant().getDayNumber())));
        order.setRated(false);
        order.setIsPaid(true);
        orderRepository.save(order);
        return "redirect:/client/orders/history";
    }

    @GetMapping("history")
    public String showOrderHistory(Model model) {
        Client client = clientRepository.findByUser(currentAuthenticatedUserService.getCurrentUser());
        List<Order> orders = orderRepository.findByClient(client);
        model.addAttribute("client",client);
        model.addAttribute("orders",orders);
        return "client-order-history";
    }

    @GetMapping("rate/new/{orderID}")
    public String newOrderRate(@PathVariable("orderID") int orderID, Model model) {
        Client client = clientRepository.findByUser(currentAuthenticatedUserService.getCurrentUser());
        model.addAttribute("client",client);
        Order order = orderRepository.findById(orderID).orElseThrow(() -> new IllegalArgumentException("Invalid order Id:" + orderID));
        model.addAttribute("order",order);
        model.addAttribute("rates", new Rate());
        return "client-order-rate";
    }

    @PostMapping("rate/add/{orderID}")
    public String addRate(@PathVariable("orderID") int orderID, Rate rate, Model model) {
        Order order = orderRepository.findById(orderID).orElseThrow(() -> new IllegalArgumentException("Invalid order Id:" + orderID));
        rate.setClient(clientRepository.findByUser(currentAuthenticatedUserService.getCurrentUser()));
        Company company = order.getCompany();
        rate.setCompany(company);
        rateRepository.save(rate);
        order.setRated(true);
        orderRepository.save(order);
        BigDecimal bd = BigDecimal.valueOf(rateRepository.getRatingByCompanyID(company.getCompanyID()) / (double)rateRepository.countAllByCompany(company));
        bd = bd.setScale(0, RoundingMode.HALF_UP);
        company.setAverageRating(bd.doubleValue());
        companyRepository.save(company);
        return "redirect:/client/orders/history";
    }
}

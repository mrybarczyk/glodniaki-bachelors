package jestesmy.glodni.cateringi.controller.web.service;

import jestesmy.glodni.cateringi.model.*;
import jestesmy.glodni.cateringi.repository.ClientRepository;
import jestesmy.glodni.cateringi.repository.CompanyRepository;
import jestesmy.glodni.cateringi.repository.RateRepository;
import jestesmy.glodni.cateringi.repository.ServiceRepository;
import jestesmy.glodni.cateringi.security.CurrentAuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Controller()
@RequestMapping("/services")
public class ServiceController {

    @Autowired
    private CurrentAuthenticatedUserService currentAuthenticatedUserService;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    public ServiceController(CurrentAuthenticatedUserService currentAuthenticatedUserService, ServiceRepository serviceRepository, CompanyRepository companyRepository, ClientRepository clientRepository, RateRepository rateRepository) {
        this.currentAuthenticatedUserService = currentAuthenticatedUserService;
        this.serviceRepository = serviceRepository;
        this.companyRepository = companyRepository;
        this.clientRepository = clientRepository;
        this.rateRepository = rateRepository;
    }

    @GetMapping()
    public String home(HttpServletResponse httpServletResponse, Model model) throws IOException {
        if (currentAuthenticatedUserService.getCurrentUser().getUserType() == UserType.CLIENT) {
            model.addAttribute("services", serviceRepository.findAll());
            return "allServicesClient";
        }
        Company company = companyRepository.findByUser(currentAuthenticatedUserService.getCurrentUser());
        model.addAttribute("services", serviceRepository.findByCompany(company));
        return "allServicesCompany";
    }

    @GetMapping("/new")
    public String newService(Model model) {
        model.addAttribute("service", new Service());
        return "addService";
    }

    @PostMapping("/add")
    public String addService(Service service, Model model) {
        Company company = companyRepository.findByUser(currentAuthenticatedUserService.getCurrentUser());
        model.addAttribute("company", company);
        service.setCompany(company);
        serviceRepository.save(service);
        model.addAttribute("services", serviceRepository.findByCompany(company));
        return "allServicesCompany";
    }

    @GetMapping("/edit/{serviceID}")
    public String showUpdateForm(@PathVariable("serviceID") int serviceID, Model model) {
        Service service = serviceRepository.findById(serviceID).orElseThrow(() -> new IllegalArgumentException("Invalid service Id:" + serviceID));
        model.addAttribute("services", service);
        return "updateService";
    }

    @PostMapping("/update/{serviceID}")
    public String updateService(@PathVariable("serviceID") int serviceID, Service service, BindingResult result, Model model) {
        if (result.hasErrors()) {
            service.setServiceID(serviceID);
            return "updateService";
        }
        Company company = companyRepository.findByUser(currentAuthenticatedUserService.getCurrentUser());
        model.addAttribute("company", company);
        service.setCompany(company);
        serviceRepository.save(service);
        model.addAttribute("services", serviceRepository.findByCompany(company));
        return "allServicesCompany";
    }

    @GetMapping("/rate/{serviceID}/{star}")
    public String rateService(@PathVariable("serviceID") int serviceID, @PathVariable("star") int star, Model model) {
        Service service = serviceRepository.findById(serviceID).orElseThrow(() -> new IllegalArgumentException("Invalid service Id:" + serviceID));
        model.addAttribute("services", serviceRepository.findAll());
        Client client = clientRepository.findByUser(currentAuthenticatedUserService.getCurrentUser());
        Rate rate;
        if (rateRepository.existsByClientAndCompany(client, service.getCompany())) {
            rate = rateRepository.findByClientAndCompany(client, service.getCompany()).get(0);
            if (star != rate.getRate()) {
                rate.setRate(star);
            } else {
                return "allServicesClient";
            }

        } else {
            rate = new Rate(star);
            client = clientRepository.findByUser(currentAuthenticatedUserService.getCurrentUser());
            rate.setClient(client);
            rate.setCompany(service.getCompany());
        }
        rateRepository.save(rate);
        BigDecimal bd = BigDecimal.valueOf(rateRepository.getRatingByCompanyID(service.getCompany().getCompanyID()) / (double)rateRepository.countAllByCompany(service.getCompany()));
        bd = bd.setScale(0, RoundingMode.HALF_UP);
        service.getCompany().setAverageRating(bd.doubleValue());
        serviceRepository.save(service);
        return "allServicesClient";
    }

    @GetMapping("/delete/{serviceID}")
    public String deleteService(@PathVariable("serviceID") int serviceID, Model model) {
        Service service = serviceRepository.findById(serviceID).orElseThrow(() -> new IllegalArgumentException("Invalid service Id:" + serviceID));
        serviceRepository.delete(service);
        Company company = companyRepository.findByUser(currentAuthenticatedUserService.getCurrentUser());
        model.addAttribute("services", serviceRepository.findByCompany(company));
        return "allServicesCompany";
    }
}

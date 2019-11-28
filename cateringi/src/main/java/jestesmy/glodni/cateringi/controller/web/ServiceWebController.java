package jestesmy.glodni.cateringi.controller.web;

import jestesmy.glodni.cateringi.model.Company;
import jestesmy.glodni.cateringi.model.Service;
import jestesmy.glodni.cateringi.controller.api.ServiceApiController;
import jestesmy.glodni.cateringi.repository.ServiceRepository;
import jestesmy.glodni.cateringi.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller("servicewebcontroller")
@RequestMapping(value="/services")
public class ServiceWebController {

    private ServiceRepository serviceRepository;

    @Autowired
    private Company company;

    @Autowired
    public ServiceWebController(ServiceRepository sc){
        this.serviceRepository = sc;
    }

    @GetMapping()
    public String home(Model model){
        model.addAttribute("services", serviceRepository.findAll());
        return "allServices";
    }

    @GetMapping("/new")
    public String newService(Model model){
        model.addAttribute("service", new Service());
        model.addAttribute("company", company);
        return "addService";
    }

    @PostMapping("/add")
    public String addService (Service service, Model model) {
        service.setCompany(company);
        serviceRepository.save(service);
        model.addAttribute("services", serviceRepository.findAll());
        return "allServices";
    }


}

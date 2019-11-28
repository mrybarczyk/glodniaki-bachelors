package jestesmy.glodni.cateringi.controller.web;

import jestesmy.glodni.cateringi.model.Service;
import jestesmy.glodni.cateringi.controller.api.ServiceController;
import jestesmy.glodni.cateringi.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("servicewebcontroller")
@RequestMapping(value="/services")
public class ServiceWebController {

    private ServiceRepository serviceRepository;

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
        return "addService";
    }

    @PostMapping("/add")
    public String addService (Service service, Model model) {
        serviceRepository.save(service);
        model.addAttribute("services", serviceRepository.findAll());
        return "allServices";
    }


}

package jestesmy.glodni.cateringi.controller.web;


import jestesmy.glodni.cateringi.model.Service;
import jestesmy.glodni.cateringi.model.ServiceVariant;
import jestesmy.glodni.cateringi.repository.ServiceRepository;
import jestesmy.glodni.cateringi.repository.ServiceVariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("servicevariantwebcontroller")
@RequestMapping(value = "/servicesvariant")
public class ServiceVariantWebController {

    @Autowired
    private ServiceVariantRepository serviceVariantRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private Service service;

    @Autowired
    public ServiceVariantWebController(ServiceVariantRepository scv){
        this.serviceVariantRepository = scv;
    }

    @GetMapping()
    public String home(Model model){
        model.addAttribute("service", serviceRepository.findAll());
        model.addAttribute("serviceVariant", serviceVariantRepository.findAll());
        return "allServiceVariants"; //
    }

    @GetMapping("/new")
    public String newServiceVariant(Model model){
        model.addAttribute("service", service);
        model.addAttribute("serviceVariant", new ServiceVariant());
        return "addServiceVariant"; //
    }

    @PostMapping("/add")
    public String addService (ServiceVariant serviceVariant, Model model){
        Service withID = new Service();
        withID.setServiceID(service.getServiceID());
        serviceVariant.setService(withID);
        serviceVariantRepository.save(serviceVariant);
        model.addAttribute("serviceVariant", serviceVariantRepository.findAll());
        return "allServiceVariants"; //
    }
}

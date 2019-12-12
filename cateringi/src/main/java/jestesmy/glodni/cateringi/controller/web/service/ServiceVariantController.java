package jestesmy.glodni.cateringi.controller.web.service;


import jestesmy.glodni.cateringi.model.Service;
import jestesmy.glodni.cateringi.model.ServiceVariant;
import jestesmy.glodni.cateringi.model.UserType;
import jestesmy.glodni.cateringi.repository.ClientRepository;
import jestesmy.glodni.cateringi.repository.ServiceRepository;
import jestesmy.glodni.cateringi.repository.ServiceVariantRepository;
import jestesmy.glodni.cateringi.security.CurrentAuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Controller()
@RequestMapping("/servicesvariant")
public class ServiceVariantController {

    @Autowired
    private CurrentAuthenticatedUserService currentAuthenticatedUserService;

    @Autowired
    private ServiceVariantRepository serviceVariantRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    public ServiceVariantController(ServiceVariantRepository scv) {
        this.serviceVariantRepository = scv;
    }

    @GetMapping()
    public String home(HttpServletResponse httpServletResponse, Model model) throws IOException {
        if(currentAuthenticatedUserService.getCurrentUser().getUserType() == UserType.CLIENT) {
            model.addAttribute("client", clientRepository.findByUser(currentAuthenticatedUserService.getCurrentUser()));
            httpServletResponse.sendRedirect("/client");
        }
        model.addAttribute("service", serviceRepository.findAll());
        model.addAttribute("serviceVariant", serviceVariantRepository.findAll());
        return "allServiceVariants";
    }

    @GetMapping("/new")
    public String newServiceVariant(Model model) {
        model.addAttribute("serviceVariant", new ServiceVariant());
        return "addServiceVariant";
    }

    @PostMapping("/add")
    public String addServiceVariant(ServiceVariant serviceVariant, Model model) {
        Service withID = new Service();
        withID.setServiceID(serviceVariant.getService().getServiceID());
        serviceVariant.setService(withID);
        serviceVariantRepository.save(serviceVariant);
        model.addAttribute("serviceVariant", serviceVariantRepository.findAll());
        return "allServiceVariants";
    }

    @GetMapping("/edit/{serviceVariantID}")
    public String showUpdateForm(@PathVariable("serviceVariantID") int serviceVariantID, Model model) {
        ServiceVariant serviceVariant = serviceVariantRepository.findById(serviceVariantID).orElseThrow(() -> new IllegalArgumentException("Invalid service Id:" + serviceVariantID));
        model.addAttribute("servicesVariant", serviceVariant);

        return "updateServiceVariant";
    }

    @PostMapping("/update/{serviceVariantID}")
    public String updateServiceVariant(@PathVariable("serviceVariantID") int serviceVariantID, @Valid ServiceVariant serviceVariant, BindingResult result, Model model) {
        if (result.hasErrors()) {
            serviceVariant.setServiceVariantID(serviceVariantID);
            return "updateServiceVariant";
        }
        serviceVariantRepository.save(serviceVariant);
        model.addAttribute("servicesVariant", serviceVariantRepository.findAll());
        return "allServiceVariants";
    }

    @GetMapping("/delete/{serviceVariantID}")
    public String deleteServiceVariant(@PathVariable("serviceVariantID") int serviceVariantID, Model model) {
        ServiceVariant serviceVariant = serviceVariantRepository.findById(serviceVariantID).orElseThrow(() -> new IllegalArgumentException("Invalid service Id:" + serviceVariantID));
        serviceVariantRepository.delete(serviceVariant);
        model.addAttribute("servicesVariant", serviceVariantRepository.findAll());
        return "allServiceVariants";
    }
}

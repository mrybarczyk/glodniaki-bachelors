package jestesmy.glodni.cateringi.controller.web.client;

import jestesmy.glodni.cateringi.domain.model.*;
import jestesmy.glodni.cateringi.domain.util.ServiceAndServiceVariant;
import jestesmy.glodni.cateringi.repository.ClientRepository;
import jestesmy.glodni.cateringi.repository.ServiceRepository;
import jestesmy.glodni.cateringi.repository.ServiceVariantRepository;
import jestesmy.glodni.cateringi.security.CurrentAuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("/client/services")
public class ServiceAndServiceVariantClientController {

    private CurrentAuthenticatedUserService currentAuthenticatedUserService;

    private ServiceRepository serviceRepository;

    private ClientRepository clientRepository;

    private ServiceVariantRepository serviceVariantRepository;

    @Autowired
    public ServiceAndServiceVariantClientController(CurrentAuthenticatedUserService currentAuthenticatedUserService,
                                              ServiceRepository serviceRepository, ClientRepository clientRepository,
                                              ServiceVariantRepository serviceVariantRepository) {
        this.currentAuthenticatedUserService = currentAuthenticatedUserService;
        this.serviceRepository = serviceRepository;
        this.clientRepository = clientRepository;
        this.serviceVariantRepository = serviceVariantRepository;
    }

    @GetMapping()
    public String servicesAll(Model model){
        User user = currentAuthenticatedUserService.getCurrentUser();
        Client client = clientRepository.findByUser(user);
        model.addAttribute("user",user);
        model.addAttribute("client", client);
        model.addAttribute("services", serviceRepository.findAllByActiveIsTrue());
        return "client-services";
    }

    @GetMapping("/{serviceID}/details")
    public String showServiceWithVariants(@PathVariable("serviceID")int serviceID, Model model) {
        User user = currentAuthenticatedUserService.getCurrentUser();
        Client client = clientRepository.findByUser(user);
        ServiceAndServiceVariant serviceAndServiceVariant = new ServiceAndServiceVariant();
        serviceAndServiceVariant.setService(serviceRepository.findById(serviceID).orElseThrow(
                () -> new IllegalArgumentException("Invalid service id"+ serviceID)
        ));
        serviceAndServiceVariant.setServiceVariants(serviceVariantRepository
                .findByServiceAndActiveIsTrue(serviceAndServiceVariant.getService()));
        model.addAttribute("client", client);
        model.addAttribute("service", serviceAndServiceVariant);
        model.addAttribute("selectedVariant",new ServiceVariant());
        return "service-details";
    }
}

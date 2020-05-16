package jestesmy.glodni.cateringi.controller.web.client;

import com.sun.xml.bind.v2.schemagen.xmlschema.Union;
import jestesmy.glodni.cateringi.domain.model.*;
import jestesmy.glodni.cateringi.domain.util.CityAndCategories;
import jestesmy.glodni.cateringi.domain.util.ServiceAndServiceVariant;
import jestesmy.glodni.cateringi.repository.ClientRepository;
import jestesmy.glodni.cateringi.repository.CompanyRepository;
import jestesmy.glodni.cateringi.repository.ServiceRepository;
import jestesmy.glodni.cateringi.repository.ServiceVariantRepository;
import jestesmy.glodni.cateringi.security.CurrentAuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

@Controller()
@RequestMapping("/client/services")
public class ServiceAndServiceVariantClientController {

    private CurrentAuthenticatedUserService currentAuthenticatedUserService;

    private ServiceRepository serviceRepository;

    private ClientRepository clientRepository;

    private ServiceVariantRepository serviceVariantRepository;

    private CompanyRepository companyRepository;

    @Autowired
    public ServiceAndServiceVariantClientController(CurrentAuthenticatedUserService currentAuthenticatedUserService,
                                                    ServiceRepository serviceRepository, ClientRepository clientRepository,
                                                    ServiceVariantRepository serviceVariantRepository, CompanyRepository companyRepository) {
        this.currentAuthenticatedUserService = currentAuthenticatedUserService;
        this.serviceRepository = serviceRepository;
        this.clientRepository = clientRepository;
        this.serviceVariantRepository = serviceVariantRepository;
        this.companyRepository = companyRepository;
    }

    @GetMapping()
    public String servicesAll(Model model){
        User user = currentAuthenticatedUserService.getCurrentUser();
        Client client = clientRepository.findByUser(user);
        CityAndCategories cityAndCategories = new CityAndCategories();
        model.addAttribute("user",user);
        model.addAttribute("client", client);
        model.addAttribute("services", serviceRepository.findAllByActiveIsTrue());
        model.addAttribute("cityAndCategories", cityAndCategories);
        return "client-services";
    }

    @PostMapping("/search")
    public String searchedServices(CityAndCategories cityAndCategories, Model model){
        User user = currentAuthenticatedUserService.getCurrentUser();
        Client client = clientRepository.findByUser(user);
        List<Company> companies = companyRepository.findByCity(cityAndCategories.getCity());
        List<Service> services = new ArrayList<>();
        for (Company company : companies) {
            services.addAll(serviceRepository.findByCompanyAndActiveIsTrue(company));
        }
        model.addAttribute("user",user);
        model.addAttribute("client", client);
        model.addAttribute("services", services);
        model.addAttribute("cityAndCategories", cityAndCategories);
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

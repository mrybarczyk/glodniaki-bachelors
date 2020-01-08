package jestesmy.glodni.cateringi.controller.web.service;

import jestesmy.glodni.cateringi.domain.model.Company;
import jestesmy.glodni.cateringi.domain.model.Service;
import jestesmy.glodni.cateringi.domain.model.ServiceVariant;
import jestesmy.glodni.cateringi.domain.model.UserType;
import jestesmy.glodni.cateringi.domain.util.ServiceAndServiceVariant;
import jestesmy.glodni.cateringi.repository.ClientRepository;
import jestesmy.glodni.cateringi.repository.CompanyRepository;
import jestesmy.glodni.cateringi.repository.ServiceRepository;
import jestesmy.glodni.cateringi.repository.ServiceVariantRepository;
import jestesmy.glodni.cateringi.security.CurrentAuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    private ServiceVariantRepository serviceVariantRepository;
    @Autowired
    public ServiceController(CurrentAuthenticatedUserService currentAuthenticatedUserService, ServiceRepository serviceRepository, CompanyRepository companyRepository, ClientRepository clientRepository) {
        this.currentAuthenticatedUserService = currentAuthenticatedUserService;
        this.serviceRepository = serviceRepository;
        this.companyRepository = companyRepository;
        this.clientRepository = clientRepository;
    }

    @GetMapping()
    public String home(HttpServletResponse httpServletResponse, Model model) throws IOException {
        if(currentAuthenticatedUserService.getCurrentUser().getUserType() == UserType.CLIENT) {
            model.addAttribute("client", clientRepository.findByUser(currentAuthenticatedUserService.getCurrentUser()));
            httpServletResponse.sendRedirect("/client");
        }
        Company company = companyRepository.findByUser(currentAuthenticatedUserService.getCurrentUser());
        model.addAttribute("services", serviceRepository.findByCompany(company));
        return "allServices";
    }

    @GetMapping("/new")
    public String newService(Model model) {
        ServiceAndServiceVariant serviceAndServiceVariant = new ServiceAndServiceVariant();
        serviceAndServiceVariant.getServiceVariants().add(new ServiceVariant());
        model.addAttribute("serviceAndServiceVariant", serviceAndServiceVariant);
        return "addService";
    }

    @PostMapping("/add")
    public String addService(ServiceAndServiceVariant serviceAndServiceVariant, Model model) {
        Company company = companyRepository.findByUser(currentAuthenticatedUserService.getCurrentUser());
        model.addAttribute("company", company);
        Service service = new Service();
        service.setCompany(company);
        service.setServiceName(serviceAndServiceVariant.getService().getServiceName());
        service.setDescription(serviceAndServiceVariant.getService().getDescription());
        serviceRepository.save(service);
        for(ServiceVariant serviceVariant : serviceAndServiceVariant.getServiceVariants()){
            serviceVariant.setService(service);
            serviceVariantRepository.save(serviceVariant);
        }
        model.addAttribute("services", serviceRepository.findByCompany(company));
        return "allServices";
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
        return "allServices";
    }

    @GetMapping("/delete/{serviceID}")
    public String deleteService(@PathVariable("serviceID") int serviceID, Model model) {
        Service service = serviceRepository.findById(serviceID).orElseThrow(() -> new IllegalArgumentException("Invalid service Id:" + serviceID));
        serviceRepository.delete(service);
        Company company = companyRepository.findByUser(currentAuthenticatedUserService.getCurrentUser());
        model.addAttribute("services", serviceRepository.findByCompany(company));
        return "allServices";
    }
}

package jestesmy.glodni.cateringi.controller.web.company;

import jestesmy.glodni.cateringi.domain.model.*;
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
import java.util.List;

@Controller()
@RequestMapping("/company/services")
public class ServiceAndServiceVariantController {

    private CurrentAuthenticatedUserService currentAuthenticatedUserService;

    private ServiceRepository serviceRepository;

    private CompanyRepository companyRepository;

    private ClientRepository clientRepository;

    private ServiceVariantRepository serviceVariantRepository;

    @Autowired
    public ServiceAndServiceVariantController(CurrentAuthenticatedUserService currentAuthenticatedUserService,
                                              ServiceRepository serviceRepository,
                                              CompanyRepository companyRepository, ClientRepository clientRepository,
                                              ServiceVariantRepository serviceVariantRepository) {
        this.currentAuthenticatedUserService = currentAuthenticatedUserService;
        this.serviceRepository = serviceRepository;
        this.companyRepository = companyRepository;
        this.clientRepository = clientRepository;
        this.serviceVariantRepository = serviceVariantRepository;
    }

    @GetMapping()
    public String servicesAll(HttpServletResponse httpServletResponse, Model model) throws IOException {
        if(currentAuthenticatedUserService.getCurrentUser().getUserType() == UserType.CLIENT) {
            model.addAttribute("client", clientRepository.findByUser(currentAuthenticatedUserService.getCurrentUser()));
            httpServletResponse.sendRedirect("/client");
        }

        User user = currentAuthenticatedUserService.getCurrentUser();
        Company company = companyRepository.findByUser(user);
        model.addAttribute("user",user);
        model.addAttribute("company",company);
        model.addAttribute("services", serviceRepository.findByCompanyAndActiveIsTrue(company));
        return "company-services";
    }

    @GetMapping("/{serviceID}/variants")
    public String showServiceWithVariants(@PathVariable("serviceID")int serviceID,Model model) {
        User user = currentAuthenticatedUserService.getCurrentUser();
        Company company = companyRepository.findByUser(user);
        ServiceAndServiceVariant serviceAndServiceVariant = new ServiceAndServiceVariant();
        serviceAndServiceVariant.setService(serviceRepository.findById(serviceID).orElseThrow(
                () -> new IllegalArgumentException("Invalid service id"+ serviceID)
                ));
        serviceAndServiceVariant.setServiceVariants(serviceVariantRepository
                .findByServiceAndActiveIsTrue(serviceAndServiceVariant.getService()));
        model.addAttribute("user",user);
        model.addAttribute("company",company);
        model.addAttribute("service",serviceAndServiceVariant);
        return "company-service-var";
    }

    @GetMapping("/new")
    public String newService(Model model) {
        User user = currentAuthenticatedUserService.getCurrentUser();
        Company company = companyRepository.findByUser(user);
        ServiceAndServiceVariant serviceAndServiceVariant = new ServiceAndServiceVariant();
        serviceAndServiceVariant.getServiceVariants().add(new ServiceVariant());
        model.addAttribute("user",user);
        model.addAttribute("company",company);
        model.addAttribute("serviceAndServiceVariant", serviceAndServiceVariant);
        return "company-services-new";
    }

    @PostMapping("/add")
    public String addService(ServiceAndServiceVariant serviceAndServiceVariant, Model model) {
        User user = currentAuthenticatedUserService.getCurrentUser();
        Company company = companyRepository.findByUser(user);
        model.addAttribute("company", company);
        Service service = serviceAndServiceVariant.getService();
        service.setCompany(company);
        service.setActive(true);
        service.setMinPrice(serviceAndServiceVariant.getServiceVariants().get(0).getPrice());
        serviceRepository.save(service);
        for(ServiceVariant serviceVariant : serviceAndServiceVariant.getServiceVariants()){
            serviceVariant.setService(service);
            serviceVariant.setActive(true);
            serviceVariantRepository.save(serviceVariant);
        }
        return "redirect:/company/services";
    }

    @GetMapping("/edit/{serviceID}")
    public String showUpdateForm(@PathVariable("serviceID") int serviceID, Model model) {
        User user = currentAuthenticatedUserService.getCurrentUser();
        Company company = companyRepository.findByUser(user);
        Service service = serviceRepository.findById(serviceID).orElseThrow(() -> new IllegalArgumentException("Invalid service Id:" + serviceID));
        model.addAttribute("service", service);
        model.addAttribute("user",user);
        model.addAttribute("company",company);
        return "updateService";
    }

    @PostMapping("/update/{serviceID}")
    public String updateService(@PathVariable("serviceID") int serviceID, Service service, BindingResult result) {
        if (result.hasErrors()) {
            service.setServiceID(serviceID);
            return "updateService";
        }
        User user = currentAuthenticatedUserService.getCurrentUser();
        Company company = companyRepository.findByUser(user);
        service.setCompany(company);
        service.setActive(true);
        service.setMinPrice(serviceRepository.findById(serviceID).get().getMinPrice());
        serviceRepository.save(service);
        return "redirect:/company/services";
    }

    @GetMapping("/delete/{serviceID}")
    public String deleteService(@PathVariable("serviceID") int serviceID) {
        Service service = serviceRepository.findById(serviceID).orElseThrow(() -> new IllegalArgumentException("Invalid service Id:" + serviceID));
        List<ServiceVariant> serviceVariants = serviceVariantRepository.findByServiceAndActiveIsTrue(service);
        for(ServiceVariant sv : serviceVariants){
            sv.setActive(false);
            serviceVariantRepository.save(sv);
        }
        service.setActive(false);
        serviceRepository.save(service);
        return "redirect:/company/services";
    }

    @GetMapping("/{serviceID}/variants/new")
    public String newServiceVariant(@PathVariable("serviceID") int serviceID, Model model) {
        User user = currentAuthenticatedUserService.getCurrentUser();
        Company company = companyRepository.findByUser(user);
        ServiceAndServiceVariant serviceAndServiceVariant = new ServiceAndServiceVariant();
        Service service = serviceRepository.findById(serviceID).orElseThrow(() ->
                new IllegalArgumentException("Invalid service Id:" + serviceID));
        serviceAndServiceVariant.setService(service);
        serviceAndServiceVariant.setServiceVariants(serviceVariantRepository.findByService(service));
        ServiceVariant newVariant = new ServiceVariant();
        model.addAttribute("user",user);
        model.addAttribute("company",company);
        model.addAttribute("service", serviceAndServiceVariant);
        model.addAttribute("newVariant",newVariant);
        return "company-service-var-new";
    }

    @PostMapping("/{serviceID}/variants/add")
    public String addServiceVariant(@ModelAttribute("newVariant")ServiceVariant newVariant,
                                    @PathVariable("serviceID")int serviceId) {
        newVariant.setActive(true);
        Service service = serviceRepository.findById(serviceId).orElseThrow(
                () -> new IllegalArgumentException("Invalid service ID" + serviceId));
        newVariant.setService(service);
        if(newVariant.getPrice()<service.getMinPrice()){
            service.setMinPrice(newVariant.getPrice());
            serviceRepository.save(service);
        }
        serviceVariantRepository.save(newVariant);
        return "redirect:/company/services/"+serviceId+"/variants";
    }

    @GetMapping("/serviceVariant/delete/{serviceVariantID}")
    public String deleteServiceVariant(@PathVariable("serviceVariantID") int serviceVariantID){
        ServiceVariant serviceVariant = serviceVariantRepository.findById(serviceVariantID).orElseThrow(() ->
                new IllegalArgumentException("Invalid serviceVariant Id:" + serviceVariantID));
        serviceVariant.setActive(false);
        serviceVariantRepository.save(serviceVariant);
        return "redirect:/company/services";
    }

}

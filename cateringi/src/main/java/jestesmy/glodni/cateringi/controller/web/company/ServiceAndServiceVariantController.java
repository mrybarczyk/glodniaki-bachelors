package jestesmy.glodni.cateringi.controller.web.company;

import jestesmy.glodni.cateringi.domain.model.*;
import jestesmy.glodni.cateringi.domain.util.ServiceAndServiceVariant;
import jestesmy.glodni.cateringi.domain.util.validation.ServiceValidator;
import jestesmy.glodni.cateringi.repository.*;
import jestesmy.glodni.cateringi.security.CurrentAuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller()
@RequestMapping("/company/services")
public class ServiceAndServiceVariantController {
    private CurrentAuthenticatedUserService currentAuthenticatedUserService;
    private ServiceRepository serviceRepository;
    private CompanyRepository companyRepository;
    private ClientRepository clientRepository;
    private ServiceVariantRepository serviceVariantRepository;

    private CategoryRepository categoryRepository;

    @Autowired
    public ServiceAndServiceVariantController(CurrentAuthenticatedUserService currentAuthenticatedUserService,
                                              ServiceRepository serviceRepository,
                                              CompanyRepository companyRepository, ClientRepository clientRepository,
                                              ServiceVariantRepository serviceVariantRepository,
                                              CategoryRepository categoryRepository) {
        this.currentAuthenticatedUserService = currentAuthenticatedUserService;
        this.serviceRepository = serviceRepository;
        this.companyRepository = companyRepository;
        this.clientRepository = clientRepository;
        this.serviceVariantRepository = serviceVariantRepository;
        this.categoryRepository = categoryRepository;
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
        model.addAttribute("categories",categoryRepository.findAll());
        model.addAttribute("selectedCategory",new Category());
        model.addAttribute("errors",new ArrayList<String>());
        return "company-services-new";
    }

    @PostMapping("/add")
    public String addService(ServiceAndServiceVariant serviceAndServiceVariant,Category selectedCategory, Model model) {
        User user = currentAuthenticatedUserService.getCurrentUser();
        Company company = companyRepository.findByUser(user);
        model.addAttribute("company", company);
        Service service = serviceAndServiceVariant.getService();
        service.setCompany(company);
        service.setActive(true);
        service.setCategory(selectedCategory);
        service.setMinPrice(serviceAndServiceVariant.getServiceVariants().get(0).getPrice());
        List<String> validationErrors = ServiceValidator.validate(serviceAndServiceVariant.getServiceVariants().get(0));
        if(validationErrors.isEmpty()) {
            serviceRepository.save(service);
            for (ServiceVariant serviceVariant : serviceAndServiceVariant.getServiceVariants()) {
                serviceVariant.setService(service);
                serviceVariant.setActive(true);
                serviceVariantRepository.save(serviceVariant);
            }
            return "redirect:/company/services";
        } else {
            model.addAttribute("user",user);
            model.addAttribute("serviceAndServiceVariant", serviceAndServiceVariant);
            model.addAttribute("errors",validationErrors);
            return "company-services-new";
        }
    }

    @GetMapping("/edit/{serviceID}")
    public String showUpdateForm(@PathVariable("serviceID") int serviceID, Model model) {
        User user = currentAuthenticatedUserService.getCurrentUser();
        Company company = companyRepository.findByUser(user);
        Service service = serviceRepository.findById(serviceID).orElseThrow(() -> new IllegalArgumentException("Invalid service Id:" + serviceID));
        model.addAttribute("service", service);
        model.addAttribute("user",user);
        model.addAttribute("company",company);
        model.addAttribute("categories",categoryRepository.findAll());
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
        serviceAndServiceVariant.setServiceVariants(serviceVariantRepository.findByServiceAndActiveIsTrue(service));
        ServiceVariant newVariant = new ServiceVariant();
        model.addAttribute("user",user);
        model.addAttribute("company",company);
        model.addAttribute("service", serviceAndServiceVariant);
        model.addAttribute("newVariant",newVariant);
        model.addAttribute("errors",new ArrayList<String>());
        return "company-service-var-new";
    }

    @PostMapping("/{serviceID}/variants/add")
    public String addServiceVariant(@ModelAttribute("newVariant")ServiceVariant newVariant,
                                    @PathVariable("serviceID")int serviceId,
                                    Model model) {
        newVariant.setActive(true);
        Service service = serviceRepository.findById(serviceId).orElseThrow(
                () -> new IllegalArgumentException("Invalid service ID" + serviceId));
        newVariant.setService(service);
        List<String> validationErrors = ServiceValidator.validate(newVariant);
        if(validationErrors.isEmpty()) {
            if (newVariant.getPrice() < service.getMinPrice()) {
                service.setMinPrice(newVariant.getPrice());
                serviceRepository.save(service);
            }
            serviceVariantRepository.save(newVariant);
            return "redirect:/company/services/" + serviceId + "/variants";
        } else {
            User user = currentAuthenticatedUserService.getCurrentUser();
            Company company = companyRepository.findByUser(user);
            ServiceAndServiceVariant serviceAndServiceVariant = new ServiceAndServiceVariant();
            serviceAndServiceVariant.setService(service);
            serviceAndServiceVariant.setServiceVariants(serviceVariantRepository.findByServiceAndActiveIsTrue(service));
            model.addAttribute("user",user);
            model.addAttribute("company",company);
            model.addAttribute("service", serviceAndServiceVariant);
            model.addAttribute("newVariant",newVariant);
            model.addAttribute("errors",validationErrors);
            return "company-service-var-new";
        }
    }

    @GetMapping("/serviceVariant/delete/{serviceVariantID}")
    public String deleteServiceVariant(@PathVariable("serviceVariantID") int serviceVariantID){
        ServiceVariant serviceVariant = serviceVariantRepository.findById(serviceVariantID).orElseThrow(() ->
                new IllegalArgumentException("Invalid serviceVariant Id:" + serviceVariantID));
        serviceVariant.setActive(false);
        serviceVariantRepository.save(serviceVariant);
        Service service = serviceVariant.getService();
        List<ServiceVariant> serviceVariants = serviceVariantRepository.findByServiceAndActiveIsTrue(service);
        double minPrice = serviceVariants.get(0).getPrice();
        for(ServiceVariant sv : serviceVariants){
            if(sv.getPrice() < minPrice)
                minPrice = sv.getPrice();
        }
        service.setMinPrice(minPrice);
        serviceRepository.save(service);
        return "redirect:/company/services/"+serviceVariant.getService().getServiceID()+"/variants";
    }

}

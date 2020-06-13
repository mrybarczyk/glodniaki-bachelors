package jestesmy.glodni.cateringi.controller.web.client;

import jestesmy.glodni.cateringi.domain.model.*;
import jestesmy.glodni.cateringi.domain.util.CityAndCategories;
import jestesmy.glodni.cateringi.domain.util.ServiceAndServiceVariant;
import jestesmy.glodni.cateringi.repository.*;
import jestesmy.glodni.cateringi.security.CurrentAuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    private CategoryRepository categoryRepository;

    @Autowired
    public ServiceAndServiceVariantClientController(CurrentAuthenticatedUserService currentAuthenticatedUserService,
                                                    ServiceRepository serviceRepository, ClientRepository clientRepository,
                                                    ServiceVariantRepository serviceVariantRepository, CompanyRepository companyRepository,
                                                    CategoryRepository categoryRepository) {
        this.currentAuthenticatedUserService = currentAuthenticatedUserService;
        this.serviceRepository = serviceRepository;
        this.clientRepository = clientRepository;
        this.serviceVariantRepository = serviceVariantRepository;
        this.companyRepository = companyRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping()
    public String servicesAll(Model model){
        User user = currentAuthenticatedUserService.getCurrentUser();
        Client client = clientRepository.findByUser(user);
        CityAndCategories cityAndCategories = new CityAndCategories();
        cityAndCategories.setCategories(new ArrayList<>());
        List<Category> categories = categoryRepository.findAll();
        for(Category category : categories) {
            cityAndCategories.getCategories().add(0);
        }
        model.addAttribute("user",user);
        model.addAttribute("client", client);
        model.addAttribute("services", serviceRepository.findAllByActiveIsTrue());
        model.addAttribute("cityAndCategories", cityAndCategories);
        model.addAttribute("allCategories", categories);
        return "client/client-services";
    }

    @PostMapping("/search")
    public String searchedServices(CityAndCategories cityAndCategories, Model model){
        User user = currentAuthenticatedUserService.getCurrentUser();
        Client client = clientRepository.findByUser(user);
        List<Company> companies = companyRepository.findByCity(cityAndCategories.getCity());
        List<Service> services = new ArrayList<>();
        if(!cityAndCategories.getCity().equals("")){
            for (Company company : companies) {
                if(cityAndCategories.getCategories().size() == 0)
                    services.addAll(serviceRepository.findByCompanyAndActiveIsTrue(company));
                else{
                    for(Integer categoryID : cityAndCategories.getCategories()){
                        if(categoryID != 0)
                            services.addAll(serviceRepository.findByCompanyAndCategoryAndActiveIsTrue(company, categoryRepository.findById(categoryID).get()));
                    }
                }
            }
        } else {
            if(cityAndCategories.getCategories().size() == 0){
                return "redirect:/client/services";
            }
            for(Integer categoryID : cityAndCategories.getCategories()){
                services.addAll(serviceRepository.findByCategoryAndActiveIsTrue(categoryRepository.findById(categoryID).get()));
            }
        }
        model.addAttribute("user",user);
        model.addAttribute("client", client);
        model.addAttribute("services", services);
        model.addAttribute("cityAndCategories", cityAndCategories);
        model.addAttribute("allCategories", categoryRepository.findAll());
        return "client/client-services";
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
        return "client/service-details";
    }
}

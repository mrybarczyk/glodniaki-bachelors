package jestesmy.glodni.cateringi.controller.web.rate;

import jestesmy.glodni.cateringi.model.*;
import jestesmy.glodni.cateringi.repository.ClientRepository;
import jestesmy.glodni.cateringi.repository.CompanyRepository;
import jestesmy.glodni.cateringi.repository.RateRepository;
import jestesmy.glodni.cateringi.repository.ServiceRepository;
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
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Controller
@RequestMapping("/rates")
public class RateController {

    private CurrentAuthenticatedUserService currentAuthenticatedUserService;

    private final RateRepository rateRepository;

    private CompanyRepository companyRepository;

    private ClientRepository clientRepository;

    private ServiceRepository serviceRepository;

    @Autowired
    public RateController(CurrentAuthenticatedUserService currentAuthenticatedUserService, RateRepository rateRepository, CompanyRepository companyRepository, ClientRepository clientRepository, ServiceRepository serviceRepository) {
        this.currentAuthenticatedUserService = currentAuthenticatedUserService;
        this.rateRepository = rateRepository;
        this.companyRepository = companyRepository;
        this.clientRepository = clientRepository;
        this.serviceRepository = serviceRepository;
    }

    @GetMapping()
    public String home(HttpServletResponse httpServletResponse, Model model) throws IOException {
        if (currentAuthenticatedUserService.getCurrentUser().getUserType() == UserType.CLIENT) {
            Client client = clientRepository.findByUser(currentAuthenticatedUserService.getCurrentUser());
            model.addAttribute("rates", rateRepository.findByClient(client));
            return "allRatesClient";
        }
        Company company = companyRepository.findByUser(currentAuthenticatedUserService.getCurrentUser());
        model.addAttribute("services", serviceRepository.findByCompany(company));
        return "allRatesCompany";
    }

    @GetMapping("/new/{serviceID}")
    public String newRate(@PathVariable("serviceID") int serviceID, Model model) {
        Service service = serviceRepository.findById(serviceID).orElseThrow(() -> new IllegalArgumentException("Invalid service Id:" + serviceID));
        Client client = clientRepository.findByUser(currentAuthenticatedUserService.getCurrentUser());
        if(!rateRepository.findByClientAndService(client,service).isEmpty())
        {
            Rate rate = rateRepository.findByClientAndService(client, service).get(0);
            model.addAttribute("rates", rate);
            model.addAttribute("service", service);
            return "updateRate";
        }
        model.addAttribute("rates", new Rate());
        model.addAttribute("service", service);
        return "addRate";
    }

    @PostMapping("/add/{serviceID}")
    public String addRate(@PathVariable("serviceID") int serviceID, Rate rate, Model model) {
        Service service = serviceRepository.findById(serviceID).orElseThrow(() -> new IllegalArgumentException("Invalid service Id:" + serviceID));
        rate.setClient(clientRepository.findByUser(currentAuthenticatedUserService.getCurrentUser()));
        rate.setService(service);
        rateRepository.save(rate);
        BigDecimal bd = BigDecimal.valueOf(rateRepository.getRatingByServiceID(service.getServiceID()) / (double)rateRepository.countAllByService(service));
        bd = bd.setScale(0, RoundingMode.HALF_UP);
        service.setAverageRating(bd.doubleValue());
        serviceRepository.save(service);
        bd = BigDecimal.valueOf(serviceRepository.getRatingByCompanyID(service.getCompany().getCompanyID()) / (double)serviceRepository.countByCompanyNotZero(service.getCompany().getCompanyID()));
        bd = bd.setScale(0, RoundingMode.HALF_UP);
        service.getCompany().setAverageRating(bd.doubleValue());
        serviceRepository.save(service);
        model.addAttribute("services", serviceRepository.findAll());
        return "allServicesClient";
    }

    @PostMapping("/update/{serviceID}")
    public String updateRate(@PathVariable("serviceID") int serviceID, Rate rate, BindingResult result, Model model) {
        Service service = serviceRepository.findById(serviceID).orElseThrow(() -> new IllegalArgumentException("Invalid service Id:" + serviceID));
        if (result.hasErrors()) {
            service.setServiceID(serviceID);
            return "updateRate";
        }
        Client client = clientRepository.findByUser(currentAuthenticatedUserService.getCurrentUser());
        String description = rate.getDescription();
        int rating = rate.getRating();
        rate = rateRepository.findByClientAndService(client, service).get(0);
        rate.setRating(rating);
        rate.setDescription(description);
        rateRepository.save(rate);
        BigDecimal bd = BigDecimal.valueOf(rateRepository.getRatingByServiceID(service.getServiceID()) / (double)rateRepository.countAllByService(service));
        bd = bd.setScale(0, RoundingMode.HALF_UP);
        service.setAverageRating(bd.doubleValue());
        serviceRepository.save(service);
        bd = BigDecimal.valueOf(serviceRepository.getRatingByCompanyID(service.getCompany().getCompanyID()) / (double)serviceRepository.countByCompanyNotZero(service.getCompany().getCompanyID()));
        bd = bd.setScale(0, RoundingMode.HALF_UP);
        service.getCompany().setAverageRating(bd.doubleValue());
        serviceRepository.save(service);
        model.addAttribute("services", serviceRepository.findAll());
        return "allServicesClient";
    }

    @GetMapping("/delete/{rateID}")
    public String deleteServiceRate(@PathVariable("rateID") int rateID, Model model) {
        Rate rate = rateRepository.findById(rateID).orElseThrow(() -> new IllegalArgumentException("Invalid service Id:" + rateID));
        Service service = rate.getService();
        rateRepository.delete(rate);
        BigDecimal bd;
        if(rateRepository.getRatingByServiceID(service.getServiceID()) == 0)
        {
            bd = BigDecimal.valueOf(0);
        }
        else
            bd = BigDecimal.valueOf(rateRepository.getRatingByServiceID(service.getServiceID()) / (double)rateRepository.countAllByService(service));
        bd = bd.setScale(0, RoundingMode.HALF_UP);
        service.setAverageRating(bd.doubleValue());
        serviceRepository.save(service);
        if(serviceRepository.getRatingByCompanyID(service.getCompany().getCompanyID()) == 0)
        {
            bd = BigDecimal.valueOf(0);
        }
        else
        bd = BigDecimal.valueOf(serviceRepository.getRatingByCompanyID(service.getCompany().getCompanyID()) / (double)serviceRepository.countByCompanyNotZero(service.getCompany().getCompanyID()));
        bd = bd.setScale(0, RoundingMode.HALF_UP);
        service.getCompany().setAverageRating(bd.doubleValue());
        serviceRepository.save(service);
        model.addAttribute("rates", rateRepository.findAll());
        return "allRatesClient";
    }

    @GetMapping("/rated/{serviceID}")
    public String showServiceRates(@PathVariable("serviceID") int serviceID, Model model) {
        Client client = clientRepository.findByUser(currentAuthenticatedUserService.getCurrentUser());
        Service service = serviceRepository.findById(serviceID).orElseThrow(() -> new IllegalArgumentException("Invalid service Id:" + serviceID));
        model.addAttribute("rates", rateRepository.findByClientAndService(client, service).get(0));
        return "allRatesService";
    }

}

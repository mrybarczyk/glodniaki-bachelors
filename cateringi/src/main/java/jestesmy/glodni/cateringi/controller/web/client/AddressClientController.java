package jestesmy.glodni.cateringi.controller.web.client;

import jestesmy.glodni.cateringi.domain.model.*;
import jestesmy.glodni.cateringi.domain.util.validation.AddressValidator;
import jestesmy.glodni.cateringi.repository.AddressRepository;
import jestesmy.glodni.cateringi.repository.ClientRepository;
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

@Controller
@RequestMapping("/client/addresses")
public class AddressClientController {

    private CurrentAuthenticatedUserService currentAuthenticatedUserService;

    private ClientRepository clientRepository;

    private AddressRepository addressRepository;

    @Autowired
    public AddressClientController(CurrentAuthenticatedUserService currentAuthenticatedUserService,
                                   ClientRepository clientRepository,AddressRepository addressRepository){
        this.currentAuthenticatedUserService = currentAuthenticatedUserService;
        this.clientRepository = clientRepository;
        this.addressRepository = addressRepository;
    }

    @GetMapping("/new")
    public String newAddress(Model model){
        User user = currentAuthenticatedUserService.getCurrentUser();
        Client client = clientRepository.findByUser(user);
        Address address = new Address();
        model.addAttribute("user", user);
        model.addAttribute("client", client);
        model.addAttribute("address", address);
        model.addAttribute("errors", new ArrayList<String>());
        return "client/client-address-new";
    }

    @PostMapping("/add")
    public String addAddress(Model model, Address address){
        List<String> validationErrors = AddressValidator.validate(address);
        User user = currentAuthenticatedUserService.getCurrentUser();
        if(!validationErrors.isEmpty()) {
            model.addAttribute("user", user);
            model.addAttribute("client", clientRepository.findByUser(user));
            model.addAttribute("address", address);
            model.addAttribute("errors", validationErrors);
            return "client/client-address-new";
        }
        address.setApartmentNumber(address.getApartmentNumber().trim());
        address.setUser(user);
        address.setCompanyName(address.getCompanyName().trim());
        addressRepository.save(address);
        return "redirect:/client/profile";
    }

    @GetMapping("/delete/{addressID}")
    public String deleteService(@PathVariable("addressID") int addressID) {
        Address address = addressRepository.findById(addressID).get();
        addressRepository.delete(address);
        return "redirect:/client/profile";
    }
}

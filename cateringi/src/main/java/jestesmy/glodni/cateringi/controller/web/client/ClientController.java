package jestesmy.glodni.cateringi.controller.web.client;

import jestesmy.glodni.cateringi.domain.model.Address;
import jestesmy.glodni.cateringi.domain.model.Client;
import jestesmy.glodni.cateringi.domain.model.Company;
import jestesmy.glodni.cateringi.domain.model.User;
import jestesmy.glodni.cateringi.repository.AddressRepository;
import jestesmy.glodni.cateringi.repository.ClientRepository;
import jestesmy.glodni.cateringi.repository.CompanyRepository;
import jestesmy.glodni.cateringi.repository.ServiceRepository;
import jestesmy.glodni.cateringi.security.CurrentAuthenticatedUserService;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientController {

    private final
    CurrentAuthenticatedUserService currentAuthenticatedUserService;

    private final
    ClientRepository clientRepository;

    private final
    ServiceRepository serviceRepository;

    private final CompanyRepository companyRepository;

    private final AddressRepository addressRepository;

    @Autowired
    public ClientController(CurrentAuthenticatedUserService currentAuthenticatedUserService,
                            ClientRepository clientRepository,
                            ServiceRepository serviceRepository,
                            CompanyRepository companyRepository,
                            AddressRepository addressRepository) {
        this.currentAuthenticatedUserService = currentAuthenticatedUserService;
        this.clientRepository = clientRepository;
        this.serviceRepository = serviceRepository;
        this.companyRepository = companyRepository;
        this.addressRepository = addressRepository;
    }

    @GetMapping
    public String showClientWelcomePage(Model model) {
        return "redirect:/client/services";
    }

    @GetMapping("profile")
    public String showClientProfile(Model model) {
        Client client = clientRepository.findByUser(currentAuthenticatedUserService.getCurrentUser());
        List<Address> addresses = addressRepository.findByUser(client.getUser());
        model.addAttribute("client",client);
        model.addAttribute("addresses", addresses);
        return "client/client-profile";
    }

    @GetMapping("settings")
    public String showSettingsForm(Model model) {
        Client client = clientRepository.findByUser(currentAuthenticatedUserService.getCurrentUser());
        model.addAttribute("client",client);
        model.addAttribute("passwordChanged",false);
        return "client/client-settings";
    }

    @GetMapping("settings/password")
    public String showPasswordChangeForm(Model model) {
        Client client = clientRepository.findByUser(currentAuthenticatedUserService.getCurrentUser());
        model.addAttribute("client",client);
        return "client/client-change-password";
    }

    @PostMapping("settings/password")
    public String updatePassword(WebRequest request) {
        User user = currentAuthenticatedUserService.getCurrentUser();
        String oldPassword = request.getParameter("oldPassword");
        if(!oldPassword.isEmpty()) {
            byte[] encrypted = DigestUtils.md5Digest(oldPassword.getBytes());
            String oldPasswordMd5 = Hex.encodeHexString(encrypted);
            if(oldPasswordMd5.equals(user.getPassword())) {
                String newPassword = request.getParameter("newPassword");
                if(!newPassword.isEmpty()) {
                    byte[] newEncrypted = DigestUtils.md5Digest(newPassword.getBytes());
                    String newPasswordMd5 = Hex.encodeHexString(newEncrypted);
                    Client client = clientRepository.findByUser(user);
                    user.setPassword(newPasswordMd5);
                    clientRepository.save(client);
                    return "redirect:/client/settings?passwordChanged";
                }
            }
        }
        return "redirect:/client/profile";
    }

    @PostMapping("settings")
    public String updateClientInfo(@ModelAttribute("client") Client modified) {
        Client client = clientRepository.findByUser(currentAuthenticatedUserService.getCurrentUser());
        client.setLastName(modified.getLastName());
        client.setName(modified.getName());
        client.getUser().setEmail(modified.getUser().getEmail());
        client.getUser().setPhoneNumber(modified.getUser().getPhoneNumber());
        clientRepository.save(client);
        return "redirect:/client/profile";
    }

    @GetMapping(value = "settings", params = "passwordChanged")
    public String showSettingsFormAfterPasswordChange(Model model) {
        Client client = clientRepository.findByUser(currentAuthenticatedUserService.getCurrentUser());
        model.addAttribute("client",client);
        model.addAttribute("passwordChanged",true);
        return "client/client-settings";
    }

    @GetMapping("company/profile/{companyID}")
    public String showCompanyProfile(@PathVariable("companyID") int companyID, Model model) {
        Company company = companyRepository.findById(companyID).get();
        Client client = clientRepository.findByUser(currentAuthenticatedUserService.getCurrentUser());
        model.addAttribute("company",company);
        model.addAttribute("client",client);
        return "client/client-company-profile";
    }
}

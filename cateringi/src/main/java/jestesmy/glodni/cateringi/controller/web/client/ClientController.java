package jestesmy.glodni.cateringi.controller.web.client;

import jestesmy.glodni.cateringi.model.Client;
import jestesmy.glodni.cateringi.repository.ClientRepository;
import jestesmy.glodni.cateringi.security.CurrentAuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/client")
public class ClientController {

    private final
    CurrentAuthenticatedUserService currentAuthenticatedUserService;

    private final
    ClientRepository clientRepository;

    @Autowired
    public ClientController(CurrentAuthenticatedUserService currentAuthenticatedUserService, ClientRepository clientRepository) {
        this.currentAuthenticatedUserService = currentAuthenticatedUserService;
        this.clientRepository = clientRepository;
    }

    @GetMapping
    public String showClientWelcomePage(Model model) {
        Client client = clientRepository.findByUser(currentAuthenticatedUserService.getCurrentUser());
        model.addAttribute("client",client);
        return "welcome-client";
    }

}

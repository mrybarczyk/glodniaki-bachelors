package jestesmy.glodni.cateringi.controller.web.message;

import jestesmy.glodni.cateringi.domain.model.Client;
import jestesmy.glodni.cateringi.domain.model.Message;
import jestesmy.glodni.cateringi.domain.model.User;
import jestesmy.glodni.cateringi.repository.ClientRepository;
import jestesmy.glodni.cateringi.repository.CompanyRepository;
import jestesmy.glodni.cateringi.repository.MessageRepository;
import jestesmy.glodni.cateringi.repository.UserRepository;
import jestesmy.glodni.cateringi.security.CurrentAuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/messages")
public class MessageController {
    private final CurrentAuthenticatedUserService currentAuthenticatedUserService;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final CompanyRepository companyRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public MessageController(ClientRepository clientRepository, CompanyRepository companyRepository, CurrentAuthenticatedUserService currentAuthenticatedUserService, UserRepository userRepository, MessageRepository messageRepository) {
        this.currentAuthenticatedUserService = currentAuthenticatedUserService;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.companyRepository = companyRepository;
        this.clientRepository = clientRepository;
    }

    @GetMapping("/send/{id}")
    //wysyłanie z profilu firmy
    public String sendMessage(@PathVariable("id") int id, Model model){
        User author = currentAuthenticatedUserService.getCurrentUser();
        User d = companyRepository.findById(id).get().getUser();
        model.addAttribute("message");
        return "redirect:/messages";
    }

    //Both for sent and received?
    //raz listowanie od to=user, raz listowanie od from=user?
    @GetMapping("/")
    public String getMessages(Model model){
        User user = currentAuthenticatedUserService.getCurrentUser();
        List<Message> lm = messageRepository.findByFrom(user);
        model.addAttribute("companyRepo",companyRepository);
        return "list-messages";
    }

    /*@Mapping
    public String deleteMessage(Model model){
        User user = currentAuthenticatedUserService.getCurrentUser();
        return "redirect:/";
    }*/
}

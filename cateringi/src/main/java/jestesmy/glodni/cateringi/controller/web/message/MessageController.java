package jestesmy.glodni.cateringi.controller.web.message;

import jestesmy.glodni.cateringi.domain.model.*;
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

    @GetMapping("/new/{id}")
    //wysyłanie z profilu firmy
    public String sendMessage(@PathVariable("id") int id, Model model){
        Message m = new Message();
        User author = currentAuthenticatedUserService.getCurrentUser();
        Client c = clientRepository.findByUser(author);
        User addressee = companyRepository.findById(id).get().getUser();
        m.setFrom(author);
        m.setTo(addressee);
        model.addAttribute("message", m);
        model.addAttribute("client", c);
        return "new-message";
    }

    @PostMapping("/send")
    public String newMessage(Message newmessage, Model model){
        messageRepository.save(newmessage);
        return "redirect:/messages/";
    }

    //Both for sent and received?
    //raz listowanie od to=user, raz listowanie od from=user?
    @GetMapping(value={"", "/"})
    public String getMessages(Model model){
        User user = currentAuthenticatedUserService.getCurrentUser();
        List<Message> from = messageRepository.findByFrom(user);
        List<Message> to = messageRepository.findByTo(user);
        model.addAttribute("messagesFrom", from);
        model.addAttribute("messagesTo", to);
        if (user.getUserType() == UserType.CLIENT){
            Client c = clientRepository.findByUser(user);
            model.addAttribute("client", c);
            return "list-messages-client";
        }
        if (user.getUserType() == UserType.COMPANY){
            Company c = companyRepository.findByUser(user);
            model.addAttribute("user", user);
            model.addAttribute("company", c);
            return "list-messages-company";
        }
        return "redirect:/";
        //model.addAttribute("companyRepo",companyRepository);
    }

    /*@Mapping
    public String deleteMessage(Model model){
        User user = currentAuthenticatedUserService.getCurrentUser();
        return "redirect:/";
    }*/
}

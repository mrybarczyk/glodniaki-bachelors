package jestesmy.glodni.cateringi.controller.web.message;

import jestesmy.glodni.cateringi.domain.model.*;
import jestesmy.glodni.cateringi.repository.ClientRepository;
import jestesmy.glodni.cateringi.repository.CompanyRepository;
import jestesmy.glodni.cateringi.repository.MessageRepository;
import jestesmy.glodni.cateringi.repository.UserRepository;
import jestesmy.glodni.cateringi.security.CurrentAuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return "message-new";
    }

    @GetMapping("/reply/{id}")
    public String reply(@PathVariable("id") int id, Model model){
        Message m = new Message();
        User addressee = currentAuthenticatedUserService.getCurrentUser();
        User author = messageRepository.findById(id).get().getFrom();
        m.setFrom(author);
        m.setTo(addressee);
        m.setSubject(messageRepository.findById(id).get().getSubject());
        model.addAttribute("message", m);
        if (author.getUserType() == UserType.CLIENT){
            Client c = clientRepository.findByUser(author);
            model.addAttribute("client", c);
            return "message-reply-client-to-company";
        }
        if (author.getUserType() == UserType.COMPANY){
            Company c = companyRepository.findByUser(author);
            model.addAttribute("user", author);
            model.addAttribute("company", c);
            return "message-reply-company-to-client";
        }
        return "redirect:/messages/";
    }

    @PostMapping("/send")
    public String newMessage(Message newmessage, Model model){
        messageRepository.save(newmessage);
        return "redirect:/messages/";
    }

    @PostMapping("/reply/send")
    public String newReply(Message newmessage, Model model){
        Message reply = new Message();
        String replySubject = "Re: ";
        replySubject = replySubject.concat(newmessage.getSubject());
        reply.setTo(newmessage.getFrom());
        reply.setFrom(newmessage.getTo());
        reply.setSubject(replySubject);
        reply.setContents(newmessage.getContents());
        messageRepository.save(reply);
        return "redirect:/messages/";
    }

    //Both for sent and received
    @GetMapping(value={"", "/"})
    public String getMessages(Model model){
        User user = currentAuthenticatedUserService.getCurrentUser();
        List<Message> from = messageRepository.findByFrom(user);
        for (int i = 0; i < from.size(); i++){
            if (from.get(i).isDeletedFrom()){
                from.remove(i);
            }
        }
        List<Message> to = messageRepository.findByTo(user);
        for (int i = 0; i < to.size(); i++){
            if (to.get(i).isDeletedTo()){
                to.remove(i);
            }
        }
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

    @GetMapping("/delete/{id}")
    String deleteMessage(@PathVariable("id") int id, Model model){
        Message message = messageRepository.findById(id).get();
        User user = currentAuthenticatedUserService.getCurrentUser();
        boolean test_from = false;
        boolean test_to = false;
        if (message.getFrom() == user){
            test_from = true;
        }
        if (message.getTo() == user) {
            test_to = true;
        }
        if (test_from && !test_to){
            message.setDeletedFrom(true);
            messageRepository.save(message);
            if (message.isDeletedOnBothSides()){
                messageRepository.delete(message);
                return "redirect:/messages";
            }
            return "redirect:/messages";
        }
        if (!test_from && test_to){
            message.setDeletedTo(true);
            messageRepository.save(message);
            if (message.isDeletedOnBothSides()){
                messageRepository.delete(message);
                return "redirect:/messages";
            }
            return "redirect:/messages";
        }
        return "redirect:/";
    }

}

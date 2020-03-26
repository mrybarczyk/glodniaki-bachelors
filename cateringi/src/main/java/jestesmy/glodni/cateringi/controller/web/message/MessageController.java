package jestesmy.glodni.cateringi.controller.web.message;

import jestesmy.glodni.cateringi.domain.model.*;
import jestesmy.glodni.cateringi.repository.*;
import jestesmy.glodni.cateringi.security.CurrentAuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/messages")
public class MessageController {
    private final CurrentAuthenticatedUserService currentAuthenticatedUserService;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final CompanyRepository companyRepository;
    private final ClientRepository clientRepository;
    private final AdminRepository adminRepository;

    @Autowired
    public MessageController(ClientRepository clientRepository, CompanyRepository companyRepository, CurrentAuthenticatedUserService currentAuthenticatedUserService, UserRepository userRepository, MessageRepository messageRepository, AdminRepository adminRepository) {
        this.currentAuthenticatedUserService = currentAuthenticatedUserService;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.companyRepository = companyRepository;
        this.clientRepository = clientRepository;
        this.adminRepository = adminRepository;
    }

    @GetMapping("/new/{id}")
    //wysyłanie z profilu firmy
    public String sendMessage(@PathVariable("id") int id, Model model){
        Message m = new Message();
        User author = currentAuthenticatedUserService.getCurrentUser();
        if (author.getUserType() == UserType.ADMIN){
            User addressee = userRepository.findById(id).get();
            Admin a = adminRepository.findByUser(author);
            m.setFrom(author);
            m.setTo(addressee);
            model.addAttribute("user", author);
            model.addAttribute("message", m);
            model.addAttribute("admin", a);
            return "message-new-admin";
        }
        Client c = clientRepository.findByUser(author);
        User addressee = companyRepository.findById(id).get().getUser();
        m.setFrom(author);
        m.setTo(addressee);
        model.addAttribute("user", author);
        model.addAttribute("message", m);
        model.addAttribute("client", c);
        return "message-new-client";
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
            model.addAttribute("user", author);
            model.addAttribute("client", c);
            return "message-reply-client";
        }
        if (author.getUserType() == UserType.COMPANY){
            Company c = companyRepository.findByUser(author);
            model.addAttribute("user", author);
            model.addAttribute("company", c);
            return "message-reply-company";
        }
        if (author.getUserType() == UserType.ADMIN){
            Admin a = adminRepository.findByUser(author);
            model.addAttribute("user", author);
            model.addAttribute("admin", a);
            return("message-reply-admin");
        }
        return "redirect:/messages/";
    }

    @PostMapping("/send")
    public String newMessage(Message newmessage, Model model){
        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formatted = dt.format(formatter);
        newmessage.setDatetime(formatted);
        messageRepository.save(newmessage);
        return "redirect:/messages/";
    }

    @PostMapping("/reply/send")
    public String newReply(Message newmessage, Model model){
        Message reply = new Message();
        String replySubject = "Re: ";
        replySubject = replySubject.concat(newmessage.getSubject());
        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formatted = dt.format(formatter);
        reply.setDatetime(formatted);
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
        List<Message> from = messageRepository.findByFrom(user)/*(Sort.by(Sort.Direction.ASC, "datetime"))*/; //też nie działa
        // To sortowanie nie działa
        //from.sort(Comparator.comparing(Message::getDatetime));
        for (int i = 0; i < from.size(); i++){
            if (from.get(i).isDeletedFrom()){
                from.remove(i);
            }
        }
        List<Message> to = messageRepository.findByTo(user);
        // To sortowanie nie działa
        //to.sort(Comparator.comparing(Message::getDatetime));
        for (int i = 0; i < to.size(); i++){
            if (to.get(i).isDeletedTo()){
                to.remove(i);
            }
        }
        model.addAttribute("messagesFrom", from);
        model.addAttribute("messagesTo", to);
        if (user.getUserType() == UserType.CLIENT){
            Client c = clientRepository.findByUser(user);
            model.addAttribute("user", user);
            model.addAttribute("client", c);
            return "list-messages-client";
        }
        if (user.getUserType() == UserType.COMPANY){
            Company c = companyRepository.findByUser(user);
            model.addAttribute("user", user);
            model.addAttribute("company", c);
            return "list-messages-company";
        }
        if (user.getUserType() == UserType.ADMIN){
            Admin a = adminRepository.findByUser(user);
            model.addAttribute("user", user);
            model.addAttribute("admin", a);
            return "list-messages-admin";
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

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
import java.util.ArrayList;
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

    @GetMapping("/contactus")
    public String contactAdmin(Model model){
        Message m = new Message();
        User author = currentAuthenticatedUserService.getCurrentUser();
        User addressee = adminRepository.findAll().get(0).getUser();
        m.setFrom(author);
        m.setTo(addressee);
        model.addAttribute("user", author);
        model.addAttribute("message", m);
        if (author.getUserType() == UserType.CLIENT){
            Client c = clientRepository.findByUser(author);
            model.addAttribute("client", c);
            return "messages-contact-admin-client";
        }
        if (author.getUserType() == UserType.COMPANY){
            Company c = companyRepository.findByUser(author);
            model.addAttribute("company", c);
            return "messages-contact-admin-company";
        }
        return "redirect:/messages/";
    }

    @PostMapping("/contactus/send")
    public String newContact(Message newmessage, Model model){
        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formatted = dt.format(formatter);
        List<Admin> admins = adminRepository.findAll();
        List<User> adminUsers = new ArrayList<>();
        for (int i = 0; i < admins.size(); i++){
            adminUsers.add(admins.get(i).getUser());
        }
        for (int i = 0; i < admins.size(); i++) {
            Message m = new Message();
            m.setFrom(newmessage.getFrom());
            m.setTo(adminUsers.get(i));
            m.setDatetime(formatted);
            m.setSubject(newmessage.getSubject());
            m.setContents(newmessage.getContents());
            messageRepository.save(m);
            m.getTo().setMessageCounter(m.getTo().getMessageCounter() + 1);
            userRepository.save(m.getTo());
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
        newmessage.getTo().setMessageCounter(newmessage.getTo().getMessageCounter() + 1);
        userRepository.save(newmessage.getTo());
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
        reply.getTo().setMessageCounter(reply.getTo().getMessageCounter() + 1);
        userRepository.save(reply.getTo());
        return "redirect:/messages/";
    }

    @GetMapping(value={"", "/", "/received"})
    public String getReceived(Model model){
        User user = currentAuthenticatedUserService.getCurrentUser();
        user.setMessageCounter(0);
        userRepository.save(user);
        List<Message> to = messageRepository.findByTo(user);
        // To sortowanie nie działa
        //to.sort(Comparator.comparing(Message::getDatetime));
        for (int i = 0; i < to.size(); i++){
            if (to.get(i).isDeletedTo()){
                to.remove(i);
            }
        }
        model.addAttribute("messages", to);
        if (user.getUserType() == UserType.CLIENT){
            Client c = clientRepository.findByUser(user);
            model.addAttribute("user", user);
            model.addAttribute("client", c);
            return "list-received-client";
        }
        if (user.getUserType() == UserType.COMPANY){
            Company c = companyRepository.findByUser(user);
            model.addAttribute("user", user);
            model.addAttribute("company", c);
            return "list-received-company";
        }
        if (user.getUserType() == UserType.ADMIN){
            Admin a = adminRepository.findByUser(user);
            model.addAttribute("user", user);
            model.addAttribute("admin", a);
            return "list-received-admin";
        }
        return "redirect:/";
    }

    @GetMapping(value={"/sent"})
    public String getSent(Model model){
        User user = currentAuthenticatedUserService.getCurrentUser();
        List<Message> from = messageRepository.findByFrom(user)/*(Sort.by(Sort.Direction.ASC, "datetime"))*/; //też nie działa
        // To sortowanie nie działa
        //from.sort(Comparator.comparing(Message::getDatetime));
        for (int i = 0; i < from.size(); i++){
            if (from.get(i).isDeletedFrom()){
                from.remove(i);
            }
        }
        model.addAttribute("messages", from);
        if (user.getUserType() == UserType.CLIENT){
            Client c = clientRepository.findByUser(user);
            model.addAttribute("user", user);
            model.addAttribute("client", c);
            return "list-sent-client";
        }
        if (user.getUserType() == UserType.COMPANY){
            Company c = companyRepository.findByUser(user);
            model.addAttribute("user", user);
            model.addAttribute("company", c);
            return "list-sent-company";
        }
        if (user.getUserType() == UserType.ADMIN){
            Admin a = adminRepository.findByUser(user);
            model.addAttribute("user", user);
            model.addAttribute("admin", a);
            return "list-sent-admin";
        }
        return "redirect:/";
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

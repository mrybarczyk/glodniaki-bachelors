package jestesmy.glodni.cateringi.controller.web.admin;

import jestesmy.glodni.cateringi.model.Admin;
import jestesmy.glodni.cateringi.model.User;
import jestesmy.glodni.cateringi.model.UserType;
import jestesmy.glodni.cateringi.repository.AdminRepository;
import jestesmy.glodni.cateringi.repository.UserRepository;
import jestesmy.glodni.cateringi.security.CurrentAuthenticatedUserService;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final
    CurrentAuthenticatedUserService currentAuthenticatedUserService;

    private final
    UserRepository userRepository;

    private final
    AdminRepository adminRepository;

    @Autowired
    public AdminController(CurrentAuthenticatedUserService currentAuthenticatedUserService, AdminRepository adminRepository, UserRepository userRepository) {
        this.currentAuthenticatedUserService = currentAuthenticatedUserService;
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String showAdminWelcomePage(Model model) {
        User user = currentAuthenticatedUserService.getCurrentUser();
        Admin admin = adminRepository.findByUser(user);
        model.addAttribute("user",user);
        model.addAttribute("admin",admin);
        return "welcome-admin";
    }

    @GetMapping("/userlist")
    public String userList(Model model){
        User user = userRepository.findByUserName(currentAuthenticatedUserService.getCurrentUserName());
        Admin admin = adminRepository.findByUser(user);
        model.addAttribute("allusers", userRepository.findAll());
        model.addAttribute("user", user);
        model.addAttribute("admin", admin);
        return "userlist";
    }

    @GetMapping("/ban/{userId}")
    public String ban(Model model, @PathVariable int userId){
        User user = userRepository.findById(userId).orElse(null);
        if (user == null){
            return "error";
        }
        else {
            if (user.getUserType() != UserType.ADMIN) {
                if (user.getIsActive()) user.setIsActive(false);
                else user.setIsActive(true);
            } else return "error-cannotbanadmin";
        }
        userRepository.save(user);
        model.addAttribute("allusers", userRepository.findAll());
        return "userlist";
    }

    @GetMapping("/resetpassword/{userId}")
    public String resetpassword(Model model, @PathVariable int userId){
        /*String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());
            sb.append(AlphaNumericString
                    .charAt(index));
        }*/
        String sb = "LosoweHasłoBędziePóźniejBoNaRazieNieWiemJakJeDawaćUżytkownikowi";
        User user = userRepository.findById(userId).orElse(null);
        if (user == null){
            return "error";
        }
        else {
            byte [] encrypted = DigestUtils.md5Digest(sb.getBytes());
            user.setPassword(Hex.encodeHexString(encrypted));
        }
        userRepository.save(user);
        User a = userRepository.findByUserName(currentAuthenticatedUserService.getCurrentUserName());
        Admin admin = adminRepository.findByUser(a);
        model.addAttribute("user", a);
        model.addAttribute("admin", admin);
        model.addAttribute("allusers", userRepository.findAll());
        return "userlist";
    }

}

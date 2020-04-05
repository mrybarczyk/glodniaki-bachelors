package jestesmy.glodni.cateringi.controller.web.admin;

import jestesmy.glodni.cateringi.domain.model.Category;
import jestesmy.glodni.cateringi.domain.model.User;
import jestesmy.glodni.cateringi.domain.model.UserType;
import jestesmy.glodni.cateringi.domain.model.Admin;
import jestesmy.glodni.cateringi.repository.AdminRepository;
import jestesmy.glodni.cateringi.repository.CategoryRepository;
import jestesmy.glodni.cateringi.repository.ServiceRepository;
import jestesmy.glodni.cateringi.repository.UserRepository;
import jestesmy.glodni.cateringi.security.CurrentAuthenticatedUserService;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final
    CurrentAuthenticatedUserService currentAuthenticatedUserService;

    private final
    UserRepository userRepository;

    private final
    AdminRepository adminRepository;

    private final CategoryRepository categoryRepository;

    private final ServiceRepository serviceRepository;

    @Autowired
    public AdminController(CurrentAuthenticatedUserService currentAuthenticatedUserService, AdminRepository adminRepository, UserRepository userRepository,
                           ServiceRepository serviceRepository, CategoryRepository categoryRepository) {
        this.currentAuthenticatedUserService = currentAuthenticatedUserService;
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.serviceRepository =serviceRepository;
        this.categoryRepository = categoryRepository;
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
        User currentUser = userRepository.findByUserName(currentAuthenticatedUserService.getCurrentUserName());
        Admin admin = adminRepository.findByUser(currentUser);
        if (user == null){
            return "error";
        }
        else {
            if (user.getUserType() != UserType.ADMIN) {
                if (user.getIsActive()) user.setIsActive(false);
                else user.setIsActive(true);
            } else{
                model.addAttribute("user", currentUser);
                model.addAttribute("admin", admin);
                return "error-cannotbanadmin";
            }
        }
        userRepository.save(user);
        model.addAttribute("user", currentUser);
        model.addAttribute("admin", admin);
        model.addAttribute("allusers", userRepository.findAll());
        return "userlist";
    }

    @GetMapping("/resetpassword/{userId}")
    public String resetpassword(Model model, @PathVariable int userId){
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        User user = userRepository.findById(userId).orElse(null);
        if (user == null){
            return "error";
        }
        else {
            byte [] encrypted = DigestUtils.md5Digest(sb.toString().getBytes());
            user.setPassword(Hex.encodeHexString(encrypted));
        }
        userRepository.save(user);
        User a = userRepository.findByUserName(currentAuthenticatedUserService.getCurrentUserName());
        Admin admin = adminRepository.findByUser(a);
        model.addAttribute("user", a);
        model.addAttribute("admin", admin);
        model.addAttribute("resetpassword", sb.toString());
        return "reset";
    }

    @GetMapping("/categories")
    public String showCategories(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("serviceRepository", serviceRepository);
        model.addAttribute("admin", adminRepository.findByUser(currentAuthenticatedUserService.getCurrentUser()));
        return "admin-categories";
    }

    @GetMapping("/categories/{id}")
    public String showEditCategoryForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("admin",adminRepository.findByUser(currentAuthenticatedUserService.getCurrentUser()));
        model.addAttribute("category", categoryRepository.findById(id).get());
        return "admin-category-edit";
    }

    @PostMapping("/categories/{id}")
    public String updateCategory(@PathVariable("id") int id,
                                 @ModelAttribute("category") Category category, Model model) {
        Category toUpdate = categoryRepository.findById(id).get();
        toUpdate.setName(category.getName());
        categoryRepository.save(toUpdate);
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/new")
    public String showNewCategoryForm(Model model) {
        model.addAttribute("admin",adminRepository.findByUser(currentAuthenticatedUserService.getCurrentUser()));
        model.addAttribute("category", new Category());
        return "admin-category-new";
    }

    @PostMapping("/categories/new")
    public String saveCategory(@ModelAttribute("category") Category category) {
        categoryRepository.save(category);
        return "redirect:/admin/categories";
    }

}

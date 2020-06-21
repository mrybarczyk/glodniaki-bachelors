package jestesmy.glodni.cateringi.controller.web;

import jestesmy.glodni.cateringi.domain.model.User;
import jestesmy.glodni.cateringi.domain.model.UserType;
import jestesmy.glodni.cateringi.security.CurrentAuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @Autowired
    CurrentAuthenticatedUserService currentAuthenticatedUserService;

    @GetMapping("/")
    public String HomePage(Model model) {
        User currentUser = currentAuthenticatedUserService.getCurrentUser();
        if(currentUser==null)
            return "redirect:/login";
        UserType currentUserType = currentUser.getUserType();
        if(currentUserType == UserType.ADMIN)
            return "redirect:/admin";
        else if(currentUserType == UserType.CLIENT)
            return "redirect:/client";
        else
            return "redirect:/company";
    }

    @GetMapping("/403")
    public String accessDenied() {
        return "403";
    }

}

package jestesmy.glodni.cateringi.controller.web;

import jestesmy.glodni.cateringi.model.Service;
import jestesmy.glodni.cateringi.controller.api.ServiceController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("servicewebcontroller")
@RequestMapping(value="/api/services", method=RequestMethod.POST)
public class ServiceWebController {

    private ServiceController sc;

    @Autowired
    public ServiceWebController(ServiceController sc){
        this.sc = sc;
    }

    @GetMapping("/all")
    public String home(Model model){
        model.addAttribute("services", sc.findAll());
        return "allServices";
    }

    @GetMapping("/new")
    public String newService(Model model){
        model.addAttribute("service", new Service());
        return "addService";
    }

    @PostMapping("/add")
    public String addService (Service service, Model model) {
        sc.create(service);
        model.addAttribute("services", sc.findAll());
        return "allServices";
    }


}

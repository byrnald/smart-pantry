package com.byrnald.smart_pantry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.byrnald.smart_pantry.service.PantryService;

@Controller
public class PantryWebController {

    private final PantryService pantryService;

    public PantryWebController(PantryService pantryService) {
        this.pantryService = pantryService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // we put the list of all items into the model like a shopping cart, so that we can access it in our 
        // dashboard.html file and display it there
        model.addAttribute("items", pantryService.getAllItems());
        //this just returns the name of the html file which is dashboard.html

        model.addAttribute("urgentItems", pantryService.getUrgentItems());

        model.addAttribute("threshold", PantryService.DEFAULT_THRESHOLD);

        return "dashboard";
    }
    // use http://localhost:8080/dashboard to access the dashboard and see all the items in a nice format 
    // instead of just json like in the API endpoints
}
package com.byrnald.smart_pantry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import com.byrnald.smart_pantry.model.PantryItem;
import com.byrnald.smart_pantry.repository.PantryRepository;

import com.byrnald.smart_pantry.service.PantryService;

@Controller
public class PantryWebController {

    private final PantryService pantryService;
    private final PantryRepository pantryRepository;

    public PantryWebController(PantryService pantryService, PantryRepository pantryRepository) {
        this.pantryService = pantryService;
        this.pantryRepository = pantryRepository;
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

    @PostMapping("/dashboard/delete/{id}")
    public String deleteItem(@PathVariable Long id) { 
        pantryService.deleteItem(id);
        return "redirect:/dashboard";
    }

    @PostMapping("/dashboard/restock/{id}")
    public String restockItem(@PathVariable Long id) { 
        pantryService.restockItem(id);
        return "redirect:/dashboard";
    }

    @PostMapping("/dashboard/subtract/{id}")
    public String subtractItem(@PathVariable Long id) { 
        pantryService.substractItem(id);
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard/add")
    public String showAddForm(){
        return "add-item"; //this tells spring to look for add-item.html
    }

    @PostMapping("/dashboard/add")
    public String addNewItem(@RequestParam String name, @RequestParam int quantity, @RequestParam(required = false) String expirationDate, @RequestParam String category) {
        PantryItem newItem = new PantryItem();
        newItem.setName(name);
        newItem.setQuantity(quantity);
        newItem.setCategory(category); 
        
        if (expirationDate != null && !expirationDate.isEmpty()) {
            newItem.setExpirationDate(java.time.LocalDate.parse(expirationDate)); // this converts the string date to a LocalDate object
        } else { 
            newItem.setExpirationDate(null); // if no expiration date is provided, we can set it to null or some default value
        }

        pantryService.saveItem(newItem);
        return "redirect:/dashboard";
    }

    //show the edit form with existing info
    @GetMapping("/dashboard/edit/{id}")
    public String showEditForm(@PathVariable Long id, org.springframework.ui.Model model) { 
        pantryRepository.findById(id).ifPresent(item -> model.addAttribute("item", item));
        return "edit-item"; // this tells spring to look for edit-item.html
    }
    //process the update
    @PostMapping("/dashboard/edit/{id}")
    public String updateItem(@PathVariable Long id, @RequestParam String name, @RequestParam int quantity, @RequestParam(required = false) String expirationDate, @RequestParam String category) { 
        pantryRepository.findById(id).ifPresent(item -> {
            item.setName(name);
            item.setQuantity(quantity);
            item.setCategory(category);
            if (expirationDate != null && !expirationDate.isEmpty()) {
                item.setExpirationDate(java.time.LocalDate.parse(expirationDate));
            } else { 
                item.setExpirationDate(null);
            }
            pantryRepository.save(item);
        });
        return "redirect:/dashboard";
    }

}
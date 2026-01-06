package com.byrnald.smart_pantry.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.byrnald.smart_pantry.model.PantryItem;
import com.byrnald.smart_pantry.service.PantryService;


@RestController //this annotation tells spring that this class is a controller
//  which means it will handle incoming HTTP requests and return responses
@RequestMapping("/api/pantry") // this annotation specifies the base URL for all endpoints in this controller
// so for example: google.com/api/pantry would be the base URL for this controller
public class PantryController {
    
    private final PantryService pantryService;

    //then we inject the service.
    public PantryController(PantryService pantryService) {
        this.pantryService = pantryService;
    }

    //then we create a GET endpoint to see all the items.
    @GetMapping
    public List<PantryItem> getAllItems() { 
        return pantryService.getAllItems();
    }

    @PostMapping //Url (POST): post https://localhost:8080/api/pantry
    public PantryItem addItem(@RequestBody PantryItem item) { 
        return pantryService.addItem(item);
    }
    // url (GET): https://localhost:8080/api/pantry/expiring
    @GetMapping("/expiring")
    public List<PantryItem> getExpiringSoon() {
        return pantryService.getExpiringSoon();
    }

    // lets say we dont have any items in our pantry, we need to find a way to delete items.
    // URL (DELETE): http://localhost:8080/api/pantry/1 (1 is just the ID of the item we want to delete) so just an example
    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) { 
        pantryService.deleteItem(id);
        //delete item should not work just yet because we havent added it to our service and repository, but we will add it soon
        //it will delete by id
    }

    @PutMapping("/{id}")
    public PantryItem updateItem(@PathVariable Long id, @RequestBody PantryItem itemDetails) { 
        return pantryService.updateItem(id, itemDetails);
        // this will update an item by id, and the new details will be in the request body
    }
    
}

package com.byrnald.smart_pantry.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.byrnald.smart_pantry.model.PantryItem;
import com.byrnald.smart_pantry.repository.PantryRepository;

@Service // this just contains the logic for our application, it acts as the middle man for our controller.
// The controller will call the service, and the service will call the repository to interact with the database
// when we setup service we create an instance of this class.
public class PantryService {
    /// we need to inject the PantryRepository into this service so that we
    //  can use it to interact with the database. We do this through constructor injection, 
    // which is a common pattern in spring boot applications. This allows us to easily 
    // test our service by mocking the repository if needed.
    private final PantryRepository pantryRepository;

    public static final int DEFAULT_THRESHOLD = 5; // we can adjust this threshold as needed, but for now we will just set it to 5
    //this is synchronizing the low stock threshold across the service, so that we can easily change it in one place if we need to

    public PantryService(PantryRepository pantryRepository) {
        this.pantryRepository = pantryRepository;
    }

    public List<PantryItem> getAllItems() {
        return pantryRepository.findAll();
    }

    public PantryItem addItem(PantryItem item) {
        return pantryRepository.save(item);
    }

    public List<PantryItem> getExpiringSoon() {
        LocalDate today = LocalDate.now();
        LocalDate threeDaysFromNow = today.plusDays(3);
        //this method calls the findByExpirationDateBetween method in the PantryRepository 
        /// which retrieves all pantry items that have an expiration date between today and three days from now.
        return pantryRepository.findByExpirationDateBetween(today, threeDaysFromNow);
    }
    //so after all of this
    /// we have a repository, service, and soon the controller.
    
    //now we need to add the deleteItem method from our controller to this service, so that
    //we can actually delete the items from our pantry
    
    /*
    public void deleteItem(Long id) { 
        for (int i = 0; i < pantryRepository.findAll().size(); i++) {
            if (pantryRepository.findAll().get(i).getId() == id) { 
                pantryRepository.deleteById(id);
            } else { 
                System.out.println("Item with id " + id + " not found.");
            }
        }
    }
    */ //this is my old deletion method, but it was not efficient as it just loops/iterates through all of the items
    //until it finds the item with the ID.

    public void deleteItem(Long id) { 
        if (pantryRepository.existsById(id)) { 
            pantryRepository.deleteById(id);
        } else { 
            //we could throw an exception here but for now we will just print out a message
            System.out.println("Item with id " + id + " not found.");
        }
    }

    public PantryItem updateItem(Long id, PantryItem newItemData) { 
        return pantryRepository.findById(id).map(item -> {
            item.setName(newItemData.getName());
            item.setQuantity(newItemData.getQuantity());
            item.setExpirationDate(newItemData.getExpirationDate());
            return pantryRepository.save(item);
        }).orElseThrow(() -> new RuntimeException("Item with id: " + id));
    }
    
    public List<PantryItem> getLowStockItems(int threshold) { 
        return pantryRepository.findByQuantityLessThanEqual(threshold);
    }

    public List<PantryItem> getUrgentItems()  {
        LocalDate start = LocalDate.of(2000, 1, 1); //old date to make sure we get all items that are expired
        LocalDate end = LocalDate.now().plusDays(3); // we want to get all items that are expiring in the next 3 days

        // we start with an old date to make sure we get all items
        //then we set the end date to 3 days from now to get all items that are expiring soon 
        // so anything that expires in 3 days or less will be considered urgent

        // so for example our eggs expire the 16th of jan
        // so eggs would show up as urgent on the 13th, 14th and 15th of jan

        List<PantryItem> expiringUrgent = pantryRepository.findByExpirationDateBetween(start, end);
        List<PantryItem> lowStock = pantryRepository.findByQuantityLessThanEqual(DEFAULT_THRESHOLD);

        java.util.Set<PantryItem> urgentSet = new java.util.HashSet<>(expiringUrgent);
        urgentSet.addAll(lowStock);

        return new java.util.ArrayList<>(urgentSet);
    }

    //now we are going to add a method for restocking items so that we can easily update the quantity of an item 
    //when we restock it, instead of having to update the entire item with the updateItem method, we can just 
    //update the quantity with this method
    public void restockItem(Long id) { 
        pantryRepository.findById(id).ifPresent(item -> {
            item.setQuantity(item.getQuantity() + 1); //we can adjust the restock amount we want, for now 1
            pantryRepository.save(item);
        });
    }
    //find by id looks for the item in the database
    //ifpresent is just a safety check if it item exists it runs the code inside.
    //item.setQuantity takes the current number and adds 1
    //pantryRepository.save sends the updated itme back to the database to commit the change

    //now that the featur to add (restock) is added, now we make a method to subtract
    public void substractItem(Long id) { 
        pantryRepository.findById(id).ifPresent(item -> {
            item.setQuantity(item.getQuantity() - 1); //just by 1
            pantryRepository.save(item);
        });
    }

}

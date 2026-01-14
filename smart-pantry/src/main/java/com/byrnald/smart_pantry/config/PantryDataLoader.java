package com.byrnald.smart_pantry.config;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.byrnald.smart_pantry.model.PantryItem;
import com.byrnald.smart_pantry.repository.PantryRepository;

@Configuration
public class PantryDataLoader {

    @Bean
    CommandLineRunner initDatabase(PantryRepository repository) {
        return args -> {
            // This check prevents duplicates when you restart!
            if (repository.count() == 0) {
                System.out.println("--- Seeding database with initial items ---");
                
                // Create items manually to set all fields including Category
                PantryItem cables1 = new PantryItem();
                cables1.setName("Patch Cables");
                cables1.setQuantity(21);
                cables1.setCategory("Electronics");
                cables1.setExpirationDate(null); // Cables don't expire

                PantryItem cables2 = new PantryItem();
                cables2.setName("HDMI Cables");
                cables2.setQuantity(4);
                cables2.setCategory("Electronics");
                cables2.setExpirationDate(null);

                PantryItem eggs = new PantryItem();
                eggs.setName("Eggs");
                eggs.setQuantity(36);
                eggs.setCategory("Pantry");
                eggs.setExpirationDate(LocalDate.now().plusDays(7));

                repository.saveAll(List.of(cables1, cables2, eggs));

                System.out.println("--- Database Seeded Successfully ---");
            } else {
                System.out.println("--- Database already contains data, skipping seed ---");
            }
        };
    }
}
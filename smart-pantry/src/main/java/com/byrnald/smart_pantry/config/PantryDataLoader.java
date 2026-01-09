package com.byrnald.smart_pantry.config;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.byrnald.smart_pantry.model.PantryItem;
import com.byrnald.smart_pantry.repository.PantryRepository;

@Configuration // this annotation tells spring that this class is a configuration class,
// which means it will contain bean definitions and other settings for our application
// this will be used to seed our database will some initial data, that way we can test it and not have to manually add items 
// every time we run the app.
public class PantryDataLoader {

    @Bean
    CommandLineRunner initDatabase(PantryRepository repository) {
        return args -> {
            // this check prevents duplicates
            if (repository.count() == 0) {
                System.out.println("--- Seeding database with initial items ---");
                // Added items i have in my pantry/inventory.
                // cable types are because of the homelab im currently working on,
                //its nice to have a inventory of the cables i have so i dont have to go out of my way to check again when i forget
                // the quantity
                //i eat a lot of eggs. just did math based on the boxes i have and eggs i eat in a week
                repository.saveAll(List.of(
                    new PantryItem("Patch Cables", 21, LocalDate.now().plusYears(10)),
                    new PantryItem("HDMI Cables", 4, LocalDate.now().plusYears(10)),
                    new PantryItem("Eggs", 36, LocalDate.now().plusDays(7))
                ));

                System.out.println("--- Database Seeded Successfully ---");
            } else {
                System.out.println("--- Database already contains data, skipping seed ---");
            }
        };
    }
}
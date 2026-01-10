package com.byrnald.smart_pantry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartPantryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartPantryApplication.class, args);
	}
	// this is just the main class, runs on the spring boot server and starts the application
	//to look at the database/seeded data you can go to http://localhost:8080/h2-console and then use the following settings:
	// JDBC URL: jdbc:h2:mem:testdb (this has to be the same as the one in application.properties)
	// or to see its json format you can go to http://localhost:8080/api/pantry and it will show you all the items

	//AFTER DATA SEEDING:
	// now that weve seeded our database with some data from my homelab and eggs
	// we can test our endpoints to see if they work, go to http://localhost:8080/api/pantry/expiring to see whats expiring soon
	// to test out by the URL http://localhost:8080/api/pantry/low-stock?threshold=10
	// by changing the threshold in the URL you can see different items that are low stock based on quantity
	// so with threshold of 10 only the items with quantity less than 10 will show up
	
	//for DASHBOARD: 
	// use http://localhost:8080/dashboard to access the dashboard and see all the items in a nice format 
	// instead of just json like in the API endpoints

}

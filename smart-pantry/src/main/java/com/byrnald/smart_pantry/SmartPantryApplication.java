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

}

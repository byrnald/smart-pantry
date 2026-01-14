# 📘 Technical Documentation & Code Reference

**Project:** Smart Pantry Inventory System
**Author:** Byron
**Tech Stack:** Java Spring Boot, Thymeleaf, H2 Database, Bootstrap 5

This document serves as a deep-dive technical reference for the Smart Pantry application. It explains the "Why" and "How" behind every major class, following the flow of data from the database up to the user.

---

## 1. `PantryItem.java` (The Blueprint) 📐
**Location:** `src/main/java/com/byrnald/smart_pantry/model/PantryItem.java`

This class is the foundation. It tells Java exactly what an "Item" looks like.

```java
@Entity
public class PantryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int quantity;
    private String category;
    private LocalDate expirationDate;
    // ... getters and setters ...
}
```
Think of this as the form you fill out. It defines the specific fields (Name, Quantity, Category) that every single item must have.

So:

**@Entity:** This annotation tells Hibernate (the ORM) to treat this class as a database table.
**@Id & @GeneratedValue:** These automatically generate a unique ID number (1, 2, 3...) for every item so we don't have to manage them manually.

## 2. `PantryRepository.java` (The Translator)  

This interface allows our Java code to talk to the SQL database without writing SQL queries.
```java
public interface PantryRepository extends JpaRepository<PantryItem, Long> {
    // Derived Query
    List<PantryItem> findByCategoryAndNameContainingIgnoreCase(String category, String keyword);
}
```
Java speaks "Objects", Databases speak "Tables". This file translates between them. We don't write the actual code here; we just define the method names, and Spring Boot writes the SQL for us.
(CRUD: CREATE, READ, UPDATE, DELETE)

So:

`extends JpaRepository` : Inherits standard CRUD methods (`save`, `delete`, `findAll`) automatically.
Method Parsing: Spring analyzes `findByCategoryAndName...` and creates the SQL query for us, for ex: `SELECT * FROM items WHERE category = ? AND name LIKE ?`

## 3. `PantryController.java` (The API Traffic Cop)

This handles the raw data/JSON API, separate from the Website
```java
@RestController
@RequestMapping("/api/pantry")
public class PantryController {
    // ... endpoints returning JSON ...
    @GetMapping
    public List<PantryItem> getAllItems() {
        return pantryService.getAllItems();
    }
}
```

This acts like a waiter for machines, not humans. If another program (or a mobile app) asked for your data, this file sends it back as raw text (JSON), not a visual website.

So:

`@RestController`: Tells Spring that this class responds with data bodies (JSON), not HTML views.

`@RequestMapping("/api/pantry")`: Sets the base URL for all API requests.

## 4. `PantryService.java` (The Brain)

This is where the thinking happens. It sits between the Controllers and the Repository.

```java
private List<PantryItem> sortItems(List<PantryItem> items) {
    Comparator<PantryItem> smartSort = (item1, item2) -> {
        // Rule 1: Low Stock (<= 5) First
        boolean item1Low = item1.getQuantity() <= DEFAULT_THRESHOLD;
        boolean item2Low = item2.getQuantity() <= DEFAULT_THRESHOLD;
        if (item1Low && !item2Low) return -1; 
        
        // Rule 2: Expiring Soon First
        // Rule 3: Alphabetical
    };
    return items.stream().sorted(smartSort).collect(Collectors.toList());
}
```
The database just stores items in the order they arrived. The Service is the organizer. It shuffles the list so that "Low Stock" and "Expiring" items are always at the top before handing the list to the Controller.

So:

Business Logic: All sorting, filtering, and threshold calculations (`DEFAULT_THRESHOLD = 5`) live here.

Custom Comparator: We use a Java Lambda expression to define complex sorting rules that SQL cannot easily handle.

## 5. `PantryWebController.java` (The Website Traffic Cop)

This handles interaction with the human user via the browser.

```java
@Controller
public class PantryWebController {
    private final PantryService pantryService;
    
    // Dependency Injection
    public PantryWebController(PantryService pantryService) {
        this.pantryService = pantryService;
    }
    // ... endpoints returning HTML views ...
}
```

This acts like a waiter for humans. It takes your order (clicks), asks the Kitchen (Service) for food, and brings you a fully plated meal (HTML Page).

So:

`@Controller`: Indicates this class returns views (templates), unlike the `@RestController` which returns JSON.
Constructor Injection: We inject the `PantryService` so the controller can access the business logic.

## 6. The Dashboard Endpoint: `PantryWebController.java`

```java
@GetMapping("/dashboard")
public String dashboard(Model model, 
                        @RequestParam(required = false) String keyword, 
                        @RequestParam(required = false) String category) {
    
    // 1. Get the list (filtered & sorted by Service)
    model.addAttribute("items", pantryService.searchItems(keyword, category));

    // 2. Get urgent items (for yellow highlighting)
    model.addAttribute("urgentItems", pantryService.getUrgentItems());

    // 3. Render the page
    return "dashboard";
}
```

When you visit `/dashboard`:

It checks if you searched for anything (`keyword`).

It asks the Service for the matching items.

It asks the Service "Which items are urgent?"

It packs all that info into a box (`Model`) and sends it to `dashboard.html`.

So:

`@RequestParam`: Handles optional query parameters from the URL (e.g., `?category=Electronics`).

`Model model`: The standard Spring interface for passing data to the View layer

## 7. Extras

`PantryDataLoader.java` (The Starter)

What it does: Runs once when the app starts. If the database is empty, it automatically adds test items (Cables, Eggs) so you don't start with a blank screen.

`dashboard.html`:

**What it does:** The visual interface. It uses **Thymeleaf** to loop through the data sent by the Controller.

Logic:`<tr th:classappend="${#lists.contains(urgentItems, item)} ? 'urgent-row' : ''">`

Translation: "If this item is in the urgent list, make the row yellow."





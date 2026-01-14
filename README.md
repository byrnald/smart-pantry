# Smart Pantry & Inventory Manager 📦

A full-stack inventory management system built with **Java** and **Spring Boot**. designed to track groceries, electronics, and household items (Specifically for my homelab items and food). The application features a smart dashboard with sorting algorithms, category filtering, and persistent data storage.

## 🚀 Key Features

* **Smart Sorting Algorithm:** A custom Java `Comparator` automatically prioritizes items based on urgency:
    1.  **Low Stock** items (Quantity ≤ 5) float to the top.
    2.  **Expiring Soon** items (within 7 days) appear next.
    3.  Remaining items are sorted alphabetically.
* **Search & Filtering:** Real-time filtering by Item Name and Category (Electronics, Pantry, Household).
* **CRUD Operations:** Full capability to Create, Read, Update, and Delete inventory items.
* **Visual Status Indicators:** Color-coded badges for "Low Stock" and "Expiring Soon" alerts.
* **Data Persistence:** Uses H2 Database with file-based storage, ensuring data survives application restarts.
* **Responsive UI:** Clean, GitHub-inspired interface built with **Thymeleaf** and **Bootstrap 5**.

## 🛠️ Tech Stack

* **Backend:** Java, Spring Boot (Web, DevTools)
* **Database:** H2 Database (File-based Persistence), Spring Data JPA
* **Frontend:** Thymeleaf (Server-side rendering), Bootstrap 5, CSS
* **Tools:** Maven, Git, VS Code

## 📸 Screenshots

![Dashboard Screenshot](smart-pantry/dashboard.png)

## ⚙️ How to Run

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/YourUsername/smart-pantry.git](https://github.com/YourUsername/smart-pantry.git)
    cd smart-pantry
    ```

2.  **Run the Application:**
    If you are using VS Code, simply run the `SmartPantryApplication.java` file.
    Or via terminal:
    ```bash
    ./mvnw spring-boot:run
    ```

3.  **Access the Dashboard:**
    Open your browser and go to: `http://localhost:8080/dashboard`

## 🧠 Smart Logic Explained

The core logic resides in `PantryService.java`. The application uses a custom comparator to dynamically sort the inventory list every time the dashboard loads:

```java
Comparator<PantryItem> smartSort = (item1, item2) -> {
    // 1. Prioritize Low Stock
    // 2. Prioritize Expiration Dates
    // 3. Fallback to Alphabetical Sort
};
```
## How It Works:

Below is a breakdown of the core logic inside the application.

### 1. `PantryService.java` (The Brain)
**Location:** `src/main/java/com/byrnald/smart_pantry/service/PantryService.java`

This file is where the thinking happens. Instead of just grabbing a random list of items from the database, we use Java to reorganize them so the most important items appear first.

**Code Block: Smart Sorting Logic**
```java
Comparator<PantryItem> smartSort = (item1, item2) -> {
    boolean item1Low = item1.getQuantity() <= DEFAULT_THRESHOLD;
    boolean item2Low = item2.getQuantity() <= DEFAULT_THRESHOLD;

    // Rule 1: Low Stock items (<= 5) get VIP priority
    if (item1Low && !item2Low) return -1; // Item 1 moves up
    if (!item1Low && item2Low) return 1;  // Item 2 moves up

    // Rule 2: If stock is fine, sort by Expiration Date
    if (item1.getExpirationDate() != null && item2.getExpirationDate() != null) {
        return item1.getExpirationDate().compareTo(item2.getExpirationDate());
    }

    // Rule 3: If everything else is equal, sort Alphabetically
    return item1.getName().compareToIgnoreCase(item2.getName());
};
```
### 2. `PantryWebController.java` (The Traffic Cop)

This file handles the communication between the user (clicking buttons in the browser) and the backend code.

```java
@GetMapping("/dashboard")
public String dashboard(Model model, 
                        @RequestParam(required = false) String keyword, 
                        @RequestParam(required = false) String category) {
    
    // 1. Ask the Service to find specific items
    model.addAttribute("items", pantryService.searchItems(keyword, category));

    // 2. Ask the Service which items are "Urgent" (for color highlighting)
    model.addAttribute("urgentItems", pantryService.getUrgentItems());

    // 3. Load the HTML page
    return "dashboard"; 
}
```

### 3. `PantryRepository.java` (The Translator)

This interface allows our Java code to talk to the SQL database without us writing complex SQL queries.

```java
// Spring automatically translates this Java method into SQL
List<PantryItem> findByCategoryAndNameContainingIgnoreCase(String category, String keyword);
```


### 4. `dashboard.html` (Dynamic Canvas)

This isn't just a static web page, it is a template that changes based on the data it receives.

<tr th:each="item : ${items}" 
    th:classappend="${#lists.contains(urgentItems, item)} ? 'urgent-row' : ''">
    
    <td th:text="${item.name}">Item Name</td>
    <td>
        <span th:if="${item.quantity <= 5}" class="badge-low-stock">LOW STOCK</span>
    </td>
</tr>

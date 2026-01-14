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

![Dashboard Screenshot](dashboard.png)

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

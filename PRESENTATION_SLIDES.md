# Project Presentation Slide Deck

**Project Title**: Automated Vehicle Fleet & Car Rental Management System  
**Course**: CSE 307 - Advanced Java Programming  
**Student**: Aarush Dubey  
**Institution**: SRM University AP  
**File**: `Car_Rental_System_Presentation.pptx` (10 Widescreen 16:9 Slides)

---

## Slide 1: Title Slide
* **Title**: Automated Vehicle Fleet & Car Rental Management System
* **Subtitle**: A Unified Object-Oriented Application in Java
* **Curriculum**: CSE 307 • Advanced Java Programming Project
* **Student Name**: Aarush Dubey
* **Institution**: SRM University AP, Andhra Pradesh
* **Technology Stack**: Java SE, SQLite JDBC, Java Swing & AWT

---

## Slide 2: Project Overview & Core Objectives
* **Left Card: Problem Statement & Solution**
  * **Industry Need**: Car rental businesses face operational friction and billing errors when managing vehicle availability, tariffs, and maintenance checks manually.
  * **Our Solution**: An end-to-end desktop software solution providing interactive CRUD management, concurrent background task automation, and embedded relational database persistence.
  * **Architecture**: Engineered inside a single, highly readable Java file (`Main.java`) organized sequentially into 5 curriculum units.
  * **Accessibility**: Dual-mode capability with an interactive Swing GUI and an automated command-line unit test runner.
* **Right Card: Key Engineering Objectives**
  * **Unit 1 (OOP & Hierarchy)**: Model a realistic fleet using interfaces, abstract classes, and multilevel inheritance (`Vehicle` $\rightarrow$ `RentalCar` $\rightarrow$ `LuxurySedan`).
  * **Unit 2 (Polymorphism & Robustness)**: Demonstrate method overloading, dynamic method dispatch, and custom exception handling (`VehicleNotFoundException`).
  * **Unit 3 (Concurrency & Streams)**: Automate background maintenance diagnostics, thread coordination (`wait()` and `notify()`), and raw byte stream persistence.
  * **Unit 4 (Database & Collections)**: Implement parameterized SQLite CRUD operations with `PreparedStatement`, synchronized with `ArrayList`, `HashSet`, and `HashMap`.
  * **Unit 5 (Generics & Interactive UI)**: Apply generic classes (`Pair<K, V>`), wildcard collection methods, and an interactive CRUD Swing desktop interface.

---

## Slide 3: System Architecture & Technology Stack
* **Card 1: Presentation Layer (Desktop GUI)**
  * **Java Swing & AWT**: Uses `JFrame`, `JTextField`, `JButton`, and `JLabel`.
  * **Delegation Event Model**: `ActionListener` intercepts click events for non-blocking UI response.
  * **Responsive Layout**: `FlowLayout` arranges components neatly across all desktop screen resolutions.
  * **Headless Detection**: `GraphicsEnvironment.isHeadless()` ensures clean automated execution in terminal test environments.
* **Card 2: Business Logic Layer**
  * **Inheritance Tree**: Encapsulates common and specialized vehicle properties across `Vehicle`, `RentalCar`, and `LuxurySedan`.
  * **Tariff Classifier**: `if-else` ladder classifies mileage efficiency into Premium, Comfort, or Economy.
  * **Multithreading**: `FleetMaintenanceThread` (priority 10) and `DailyBillingTask` run concurrently.
  * **Thread Coordinator**: `FleetDispatchSync` synchronizes inspection and vehicle dispatch using `wait()` and `notify()`.
* **Card 3: Persistence Layer**
  * **SQLite Database**: Embedded relational storage in `rentals.db` via `org.sqlite.JDBC`.
  * **PreparedStatement**: Pre-compiled parameterized queries protect against SQL injection vulnerabilities.
  * **Collections Cache**: Keeps in-memory `ArrayList`, `HashSet`, and `HashMap` in sync with SQLite tables.
  * **Byte Stream Output**: Serializes rental agreements to `rental_agreement.dat` using `FileOutputStream` / `FileInputStream`.

---

## Slide 4: Unit 1 — Classes, Objects & Inheritance
* **Left Card: Object-Oriented Design**
  * **Interface (`Rentable`)**: Declares the public contract method `displayRentalInfo()` guaranteeing uniform reporting across all vehicle types.
  * **Abstract Class (`Vehicle`)**: Encapsulates protected fields `vehicleId` and `modelName`. Defines abstract method `getVehicleTier()` forcing subclass specialization.
  * **Multilevel Hierarchy**: `Vehicle` (Base) $\rightarrow$ `RentalCar` (Derived with category and daily rate) $\rightarrow$ `LuxurySedan` (Specialized with chauffeur toggle).
  * **Encapsulation**: Protected access specifiers protect data integrity while allowing direct, clean access in derived classes.
* **Right Card: Keywords & Control Logic**
  * **`this` Keyword**: Eliminates variable shadowing in constructors (`this.vehicleId = vehicleId`).
  * **`super()` Keyword**: Chained constructor calls pass initialization data upward to parent classes.
  * **`static` Keyword**: `static int totalFleet` maintains a class-wide counter incremented on every vehicle creation.
  * **`final` Keyword**: `public static final String AGENCY = "SRM Rentals"` establishes an immutable branding constant.
  * **Control Statements**: `getFuelEfficiencyClass()` uses an `if-else` ladder based on daily rate ($\ge 3500$ Premium, $\ge 2000$ Comfort, else Economy).

---

## Slide 5: Unit 2 — Polymorphism & Custom Exception Handling
* **Left Card: Polymorphic Implementations**
  * **Compile-Time Polymorphism (Method Overloading)**: Implemented through overloaded `search()` methods:
    * `search(List<RentalCar>, int vehicleId)`: Fast integer ID lookup.
    * `search(List<RentalCar>, String model)`: Case-insensitive model name search.
  * **Runtime Polymorphism (Dynamic Method Dispatch)**: An abstract base reference (`Vehicle polyVehicle`) points to a `LuxurySedan` object.
  * **Dynamic Resolution**: Calling `polyVehicle.getVehicleTier()` dynamically invokes `LuxurySedan`'s overridden method at runtime.
  * **Method Overriding**: `RentalCar` and `LuxurySedan` override `displayRentalInfo()` and `getVehicleTier()` to supply specialized vehicle descriptions.
* **Right Card: Custom Exception Handling**
  * **Custom Exception Class**: `class VehicleNotFoundException extends Exception` defines an application-specific checked exception.
  * **`throw` Keyword**: `search()` explicitly triggers `VehicleNotFoundException` when a queried car ID or model does not exist.
  * **`throws` Clause**: Declared on method headers to document error propagation to callers.
  * **`try-catch-finally`**: Wraps search and database operations, catching `VehicleNotFoundException` and `NumberFormatException` cleanly without application crashes.
  * **`finally` Block**: Guarantees post-execution cleanup and confirmation logging under all outcomes.

---

## Slide 6: Unit 3 — Multithreading & Inter-Thread Synchronization
* **Left Card: Two Thread Creation Mechanisms**
  * **Subclassing `Thread`**: `FleetMaintenanceThread extends Thread` overrides `run()` to perform background vehicle diagnostics.
  * **Thread Priorities**: `t1.setPriority(Thread.MAX_PRIORITY)` sets priority 10, signaling the OS scheduler to prioritize safety audits.
  * **Implementing `Runnable`**: `DailyBillingTask implements Runnable` executed via `new Thread(task).start()` to perform asynchronous revenue audits.
  * **Why Both Approaches?**: Demonstrates complete understanding of Java's threading model (class extension vs. interface implementation).
* **Right Card: Inter-Thread Coordination (`wait()` & `notify()`)**
  * **Operational Need**: A rented vehicle must never be handed over to a customer until the safety inspection thread passes.
  * **Synchronization Monitor**: `FleetDispatchSync` encapsulates shared state boolean `inspectionDone` within `synchronized` methods.
  * **`wait()` Method**: `dispatchVehicle()` calls `wait()`, releasing the object monitor lock and pausing the dispatch thread.
  * **`notify()` Method**: `completeInspection()` completes safety checks, sets `inspectionDone = true`, and calls `notify()` to wake up the waiting dispatch thread.
  * **Outcome**: Eliminates CPU-heavy busy-waiting loops and guarantees safe execution order.

---

## Slide 7: Unit 3 (Contd.) — Byte Streams & Delegation Event Model
* **Left Card: Java Byte Streams (I/O)**
  * **Byte Stream Concept**: Operates on raw 8-bit binary bytes rather than 16-bit character encodings, making it universal for all file types.
  * **`FileOutputStream`**: Serializes rental agreement data (`101,Hyundai Creta,SUV,Rate:2800`) directly to disk as `rental_agreement.dat`.
  * **`FileInputStream`**: Reads raw byte buffers back from disk into memory, reconstructed into standard Java strings.
  * **Stream Hygiene**: Structured `close()` execution guarantees zero file handle or memory leaks.
  * **Syllabus Fulfillment**: Direct demonstration of Unit 3 byte stream requirements.
* **Right Card: Delegation Event Model (GUI)**
  * **Event Sources**: Swing components (`JButton btnAdd`, `btnSearch`, `btnUpdate`, `btnDelete`) generate `ActionEvent` triggers when clicked.
  * **Event Listeners**: `ActionListener` interfaces intercept user actions cleanly without freezing the user interface.
  * **Decoupled Architecture**: Keeps visual GUI components decoupled from the underlying SQLite database operations.
  * **Action Routing**: Lambda expressions (`e -> {...}`) route button clicks to corresponding CRUD methods.
  * **Interactive Feedback**: Updates status `JLabel` with real-time confirmation or validation error messages.

---

## Slide 8: Unit 4 — SQLite Database (JDBC) & Collections Framework
* **Left Card: SQLite Relational Database (JDBC)**
  * **Driver & URL**: `Class.forName("org.sqlite.JDBC")` connects to local `rentals.db` via `DriverManager.getConnection()`.
  * **Automatic DDL Execution**: `Statement` creates table: `CREATE TABLE IF NOT EXISTS fleet (id INT PRIMARY KEY, model TEXT, category TEXT, rate REAL)`.
  * **Parameterized `PreparedStatement`**: Safe CRUD execution with question mark placeholders (`?`) eliminating SQL injection vulnerabilities.
  * **Update & Delete**: Executes `UPDATE fleet SET rate = ? WHERE id = ?` and `DELETE FROM fleet WHERE id = ?`.
  * **`ResultSet` Iteration**: `SELECT * FROM fleet` queries database rows and reconstructs in-memory `RentalCar` objects.
* **Right Card: Java Collections Framework**
  * **`ArrayList<RentalCar>`**: Primary dynamic list maintaining ordered vehicle records for fast indexed iteration.
  * **`HashSet<String>`**: Automatically suppresses duplicate categories, maintaining a set of unique vehicle types (e.g. `[SUV, Luxury Sedan]`).
  * **`HashMap<Integer, RentalCar>`**: Key-value mapping linking vehicle IDs to car objects for $O(1)$ constant-time lookups.
  * **`Iterator<RentalCar>`**: Standard cursor traversal using `it.hasNext()` and `it.next()` to keep memory caches synchronized.
  * **Simultaneous Sync**: All GUI CRUD operations update SQLite and in-memory collections simultaneously.

---

## Slide 9: Unit 5 — Generics & Full CRUD Desktop GUI
* **Left Card: Generic Classes & Wildcard Methods**
  * **Two-Parameter Generic Class**: `class Pair<K, V>` encapsulates two typed items without unsafe `Object` downcasting.
  * **Real-World Application**: `Pair<String, Double> mostExclusive` pairs the top vehicle model with its daily tariff (`BMW 5 Series` $\rightarrow$ Rs. 7200.0/day).
  * **Upper-Bounded Wildcard**: `inspectFleet(List<? extends Vehicle> list)` accepts any subtype of `Vehicle` polymorphically.
  * **Type Safety**: Protects against runtime `ClassCastException` by validating types at compile-time.
* **Right Card: Interactive Desktop CRUD GUI**
  * **Desktop Window**: Centered `JFrame` window (520 $\times$ 240 px) with `FlowLayout`.
  * **Input Fields**: 4 dedicated input text fields: ID, Model, Category, and Daily Rate.
  * **Add Vehicle (Create)**: Inserts a new car into SQLite, updates collections, and displays confirmation.
  * **Search ID (Read)**: Finds car by ID, automatically populating Model, Category, and Rate into text boxes.
  * **Update Rate (Update)**: Modifies daily tariff in SQLite and in-memory collections for the given ID.
  * **Delete Vehicle (Delete)**: Removes car record from SQLite and collections, clearing form fields.

---

## Slide 10: Testing, Verification & Academic Conclusion
* **Left Card: Automated Testing & Live Demo**
  * **Terminal Test Suite**: Running `./run.sh` triggers automated unit verification for all 5 units before launching the GUI.
  * **Test 1 (Search)**: Search ID `101` $\rightarrow$ Auto-populates Hyundai Creta, SUV, Rs. 2800.0/day.
  * **Test 2 (Create)**: Enter ID `301`, Fortuner, SUV, 4500 $\rightarrow$ Saved to SQLite.
  * **Test 3 (Update)**: Change ID `301` rate to `5000` $\rightarrow$ Database tariff updated immediately.
  * **Test 4 (Delete)**: Delete ID `301` $\rightarrow$ Record removed; search returns *"Vehicle not found!"*.
  * **Cross-Platform**: Fully verified on macOS, Windows (`compile.bat`/`run.bat`), and Linux.
* **Right Card: Key Takeaways & Conclusion**
  * **Single-File Elegance**: All 5 units implemented cleanly inside [`Main.java`](Main.java) (~400 lines) with plain-English comments on every line.
  * **Zero Dependency Bloat**: Relies strictly on standard Java SE and embedded `sqlite-jdbc.jar`.
  * **Curriculum Attainment**: 100% Outcome-Based Education (OBE) coverage across OOP, Multithreading, I/O, JDBC, Collections, and Generics.
  * **Open Source & GitHub**: Pushed to [github.com/aarushdubey/car-rental-system-java](https://github.com/aarushdubey/car-rental-system-java).
  * **Examiner Ready**: Every technical concept is backed by working code, making defense during viva straightforward and confident.

# PROJECT REPORT

## Title: Automated Vehicle Fleet & Car Rental Management System
**Course**: CSE 307 - Advanced Java Programming  
**Institution**: SRM University AP, Andhra Pradesh  
**File Name**: `Main.java`  
**Database**: SQLite (`rentals.db`) via JDBC  

---

## 1. Project Overview & Objectives

### Project Overview
The **Automated Vehicle Fleet & Car Rental Management System** is an end-to-end software application developed in a single, well-structured Java file (`Main.java`). The project is specifically engineered to demonstrate the theoretical foundations and practical applications of the **CSE 307: Advanced Java Programming** curriculum across all 5 units, with an emphasis on Units 1, 2, 3, and 4.

### Objectives
* **Unit 1 (OOP & Inheritance)**: Implement contract interfaces (`Rentable`), abstract base classes (`Vehicle`), and multilevel inheritance (`Vehicle` $\rightarrow$ `RentalCar` $\rightarrow$ `LuxurySedan`) with `this`, `super()`, static counters (`totalFleet`), final constants (`AGENCY`), and control statements for tariff classification.
* **Unit 2 (Polymorphism & Exception Handling)**: Demonstrate compile-time polymorphism (overloaded search by ID and by model name) and runtime polymorphism (dynamic method dispatch on vehicle inspection), backed by custom checked exceptions (`VehicleNotFoundException`) and `try-catch-finally` error handling.
* **Unit 3 (Multithreading, Byte Streams & Event Handling)**: Demonstrate concurrent execution via `Thread` subclassing (`FleetMaintenanceThread`) and `Runnable` task execution (`DailyBillingTask`), thread coordination via `synchronized`, `wait()`, and `notify()`, binary persistence via Byte Streams (`FileOutputStream` / `FileInputStream`), and event delegation via `ActionListener`.
* **Unit 4 (JDBC Database Connectivity & Collections Framework)**: Implement SQLite JDBC database connectivity (`rentals.db`), parameterized table CRUD operations with `PreparedStatement`, and in-memory data structures using the Java Collections Framework (`ArrayList`, `HashSet`, `HashMap`, and `Iterator`).
* **Unit 5 (Generics & Graphical User Interface)**: Apply generic class design (`Pair<K, V>`), wildcard collection methods (`List<? extends Vehicle>`), and build an interactive Swing desktop search interface.

---

## 2. Unit-by-Unit Implementation Details

### Unit 1: Classes, Objects & Inheritance
* **Contract Interface**: `interface Rentable` defines the contract method `displayRentalInfo()`.
* **Abstract Class**: `abstract class Vehicle implements Rentable` defines fundamental fields `vehicleId` and `modelName`, using `this` to initialize instance variables, and specifies the abstract method `getVehicleTier()`.
* **Subclass**: `class RentalCar extends Vehicle` adds `category` and `dailyRate`. Subclasses invoke the parent constructor via `super(vehicleId, modelName)` and track the fleet count using `static int totalFleet`. The constant `final String AGENCY = "SRM Rentals"` represents the company branding. An `if-else` ladder classifies cars into mileage categories based on daily rental rates.
* **Multilevel Inheritance**: `class LuxurySedan extends RentalCar` models executive vehicles by introducing the `chauffeurIncluded` boolean flag, forming the multilevel hierarchy:
  $$\text{Vehicle (Abstract)} \longrightarrow \text{RentalCar (Standard)} \longrightarrow \text{LuxurySedan (Executive)}$$

### Unit 2: Polymorphism & Exception Handling
* **Compile-Time Polymorphism (Method Overloading)**:
  * `search(List<RentalCar> list, int vehicleId)`: Fast search by numeric vehicle ID.
  * `search(List<RentalCar> list, String model)`: Case-insensitive search by car model name.
* **Runtime Polymorphism (Dynamic Method Dispatch)**:
  * The method `getVehicleTier()` is overridden in `RentalCar` and `LuxurySedan`.
  * Invoking `polyVehicle.getVehicleTier()` using a base reference of type `Vehicle` dynamically executes the subclass implementation at runtime.
* **Exception Handling**:
  * **Custom Exception**: `class VehicleNotFoundException extends Exception` is thrown when an invalid car ID or model is queried.
  * Demonstrates structured `try`, `catch`, and `finally` blocks for error management and resource cleanup.

### Unit 3: Multithreading, Byte Streams & Event Handling
* **Multithreading via `Thread` Class**: `FleetMaintenanceThread extends Thread` runs safety diagnostics and sets thread priority using `setPriority(Thread.MAX_PRIORITY)`.
* **Multithreading via `Runnable` Interface**: `DailyBillingTask implements Runnable` runs revenue audits in the background.
* **Inter-Thread Communication**: `FleetDispatchSync` synchronizes the safety inspection thread and customer dispatch thread using `synchronized`, `wait()`, and `notify()`. The vehicle hand-over thread pauses until inspection passes.
* **Byte Streams I/O**: Raw byte output is written to `rental_agreement.dat` using `FileOutputStream` and read back using `FileInputStream`.
* **Event Handling**: Implemented via the Java **Delegation Event Model** using `JButton`, `JTextField`, `JLabel`, and `ActionListener` in a desktop search dialog.

### Unit 4: JDBC Database & Collections Framework
* **SQLite JDBC**: Connects to `jdbc:sqlite:rentals.db`, creates table `fleet`, and executes parameterized queries using `PreparedStatement` to prevent SQL injection.
* **Collections Framework**:
  * `ArrayList<RentalCar>`: In-memory fleet inventory and data source for searches.
  * `HashSet<String>`: Automatically stores unique car categories (SUV, Sedan).
  * `HashMap<Integer, RentalCar>`: Enables constant-time ($O(1)$) car lookup by vehicle ID.
  * `Iterator`: Traversal demonstrated with `while (it.hasNext())`.

### Unit 5: Generics & Simple GUI
* **Generic Class**: `Pair<K, V>` stores the most premium vehicle paired with its daily tariff (`Pair<String, Double>`).
* **Generic Wildcard Method**: `inspectFleet(List<? extends Vehicle> list)` enables polymorphic processing across any collection subtype of `Vehicle`.
* **Desktop GUI**: Clean, 15-line Swing search window built using `JFrame`, `FlowLayout`, `JTextField`, `JButton`, and `JLabel`.

---

## 3. Database Schema

* **Database Engine**: SQLite 3 (`rentals.db`)
* **Table**: `fleet`

| Field Name | Type | Key | Description |
|---|---|---|---|
| `id` | `INTEGER` | `PRIMARY KEY` | Unique vehicle identifier |
| `model` | `TEXT` | `NOT NULL` | Make and model name |
| `category` | `TEXT` | `NOT NULL` | Vehicle classification (SUV, Sedan) |
| `rate` | `REAL` | `NOT NULL` | Daily rental rate in INR |

---

## 4. How to Compile & Run

### macOS / Linux
```bash
./compile.sh
./run.sh
```

### Windows
```cmd
compile.bat
run.bat
```

---

## 5. Sample Execution Output

```text
=== VEHICLE / CAR RENTAL SYSTEM (CSE 307) ===

--- [UNIT 1] CLASSES & INHERITANCE ---
Agency: SRM Rentals
ID: 101, Model: Hyundai Creta [Standard Rental Car]
Category: SUV, Daily Rate: Rs.2800.0, Class: Comfort Cruiser
ID: 201, Model: Mercedes C-Class [Executive Luxury Sedan (Chauffeur: Included)]
Category: Luxury Sedan, Daily Rate: Rs.6500.0, Class: Premium Performance
Total Fleet Count (static): 2

--- [UNIT 2] POLYMORPHISM & EXCEPTIONS ---
Dynamic dispatch vehicle tier: Executive Luxury Sedan (Chauffeur: Included)
Caught Custom Exception: Vehicle ID 999 not found in fleet!
Finally block executed.

--- [UNIT 3] THREADS & BYTE STREAMS ---
[Thread 1] Fleet maintenance check running at Priority: 10
[Thread 2] Rental revenue audit finished asynchronously via Runnable.
[Sync] Safety inspection verified via wait/notify! Car keys released.
Byte Stream Read: 101,Hyundai Creta,SUV,Rate:2800

--- [UNIT 4] JDBC & COLLECTIONS ---
Unique Categories (HashSet): [SUV]
Fleet Map Keys (HashMap): [101]
Overloaded Search (ID 101): Hyundai Creta
Overloaded Search (Model 'Hyundai Creta'): ID 101

--- [UNIT 5] GENERICS & GUI ---
Generic Pair (Most Exclusive): BMW 5 Series (Rs.7200.0/day)
Inspected: Hyundai Creta (Standard Rental Car)
Inspected: Mercedes C-Class (Executive Luxury Sedan (Chauffeur: Included))
GUI window launched!
```

---

## 6. Viva Voce Cheat Sheet

1. **Q: How does dynamic method dispatch work here?**  
   *Ans*: A reference of type `Vehicle` points to an instance of `LuxurySedan`. When `getVehicleTier()` is called, Java dynamically invokes `LuxurySedan`'s overridden method at runtime.

2. **Q: Why are both `Thread` and `Runnable` used?**  
   *Ans*: To demonstrate both approaches to multithreading in Java: `FleetMaintenanceThread extends Thread` and `DailyBillingTask implements Runnable`.

3. **Q: How do `wait()` and `notify()` synchronize threads in `FleetDispatchSync`?**  
   *Ans*: The dispatch thread calls `wait()`, releasing the monitor lock until the maintenance thread completes the inspection and calls `notify()`.

4. **Q: What is the purpose of `PreparedStatement` in JDBC?**  
   *Ans*: It pre-compiles parameterized SQL queries, improves execution performance, and prevents SQL injection vulnerabilities.

5. **Q: What does the generic wildcard `List<? extends Vehicle>` do?**  
   *Ans*: Because Java collections are invariant, it allows the method to accept any list whose element type is a subtype of `Vehicle` (`RentalCar`, `LuxurySedan`).


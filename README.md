# Vehicle Fleet & Car Rental Management System (CSE 307)

A single Java file of clean, commented code that implements all 5 syllabus units of **CSE 307: Advanced Java Programming**:
👉 [`Main.java`](Main.java)

📄 **Official Project Report (20 Pages)**: [`JAVA_PROJECT_REPORT.pdf`](JAVA_PROJECT_REPORT.pdf)  
*Submitted to: Prof. Gokaramaiah Thota*  
*Submitted by: Aarush Dubey (AP24110010270) & Sommayadeep (AP24110010181)*  
*Department of Computer Science and Engineering, SRM University AP*

Every line of code includes an inline comment explaining its role in simple language.

---

## Features & Full CRUD Capabilities

The application includes an interactive desktop GUI supporting full **CRUD** operations linked to the SQLite database (`rentals.db`):
* **Create (Add Vehicle)**: Enter ID, Model, Category, and Daily Rate, then click **Add Vehicle** to insert the record into SQLite via `PreparedStatement`.
* **Read (Search ID)**: Enter Vehicle ID and click **Search ID** to locate and auto-populate all car fields.
* **Update (Update Rate)**: Enter Vehicle ID and new Daily Rate, then click **Update Rate** to update the database tariff via SQL `UPDATE`.
* **Delete (Delete Vehicle)**: Enter Vehicle ID and click **Delete Vehicle** to remove the record from SQLite and in-memory collections via SQL `DELETE`.

---

## 5-Unit Breakdown & Viva Explanation

1. **[UNIT 1] Classes, Objects & Inheritance**:
   - `interface Rentable`: Common display contract.
   - `abstract class Vehicle`: Abstract base class with `vehicleId`, `modelName`, and abstract method `getVehicleTier()`.
   - `class RentalCar extends Vehicle`: Subclass using `super()`, `this`, `static int totalFleet`, and `final String AGENCY`. Calculates tariff class using an `if-else` ladder.
   - `class LuxurySedan extends RentalCar`: Shows **multilevel inheritance** (`Vehicle` -> `RentalCar` -> `LuxurySedan`).

2. **[UNIT 2] Polymorphism & Exception Handling**:
   - **Compile-Time Polymorphism**: Method overloading (`search(fleetList, int id)` vs `search(fleetList, String model)`).
   - **Runtime Polymorphism**: Dynamic method dispatch on `Vehicle polyVehicle = new LuxurySedan(...)`.
   - **Exception Handling**: User-defined checked exception `VehicleNotFoundException`, managed using `try`, `catch`, and `finally`.

3. **[UNIT 3] Multithreading, Byte Streams & Event Handling**:
   - **Multithreading**:
     - `FleetMaintenanceThread extends Thread` with `setPriority(Thread.MAX_PRIORITY)`.
     - `DailyBillingTask implements Runnable` executed in background.
   - **Inter-Thread Communication**: `FleetDispatchSync` synchronizes threads using `synchronized`, `wait()`, and `notify()`.
   - **Byte Streams**: `FileOutputStream` writes binary/text rental agreements to `rental_agreement.dat` and `FileInputStream` reads them back.
   - **Event Handling**: Full CRUD GUI window built with the Delegation Event Model (`ActionListener`).

4. **[UNIT 4] JDBC Database & Collections Framework**:
   - **JDBC Connectivity**: SQLite database (`rentals.db`) via `lib/sqlite-jdbc.jar`, table creation, and parameterized CRUD operations via `PreparedStatement`.
   - **Collections**: `ArrayList<RentalCar>` for fleet list, `HashSet<String>` for unique vehicle categories, `HashMap<Integer, RentalCar>` for ID lookups, and traversal via `Iterator`.

5. **[UNIT 5] Generics & Desktop GUI**:
   - **Generic Class**: `Pair<K, V>` (pairs the most premium vehicle with its daily rental rate).
   - **Wildcard Method**: `inspectFleet(List<? extends Vehicle> list)`.
   - **Full CRUD Search & Edit GUI**: A Swing window with inputs for ID, Model, Category, Rate, and buttons for Add, Search, Update, and Delete.

---

## How to Run

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

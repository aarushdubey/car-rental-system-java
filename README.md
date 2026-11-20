# Vehicle Fleet & Car Rental Management System (CSE 307)

**Advanced Java Programming — Comprehensive Laboratory Project**  
*Department of Computer Science and Engineering, SRM University-AP*  
*Submitted to: Prof. Gokaramaiah Thota*  
*Submitted by: Aarush Dubey (AP24110010270) & Sommayadeep (AP24110010181)*

---

## 📁 Submission Package Directory Structure
This folder is organized in accordance with the official laboratory evaluation requirements:

1. **`1_Project_Report/`**
   - `Car_Rental_Management_System_Project_Report.pdf`: 20-page formal laboratory project report covering all 6 evaluation rubric criteria.
   - `report.tex`: Complete LaTeX source code.
2. **`2_Java_Source_Code/`**
   - `Main.java`: Complete source code covering Units 1 to 5 (410 lines).
   - `lib/sqlite-jdbc.jar`: Vendored SQLite JDBC driver.
   - `compile.sh` / `run.sh`: Automated macOS/Linux build and run scripts with auto-compilation.
   - `compile.bat` / `run.bat`: Windows build and run scripts.
   - `RUN_COMMANDS.txt`: Execution commands and evaluation guide.
   - `rentals.db`: Pre-populated SQLite database file.
   - `rental_agreement.dat`: Binary byte stream persistence file.
3. **`3_Program_Output_Screenshots/`**
   - `Program_Output_Screenshots.pdf`: 6-page verification document compiling all terminal and GUI output figures.
   - `images/`: Individual high-resolution PNG screenshots (01 to 08).
4. **`4_Java_Certificates/`**
   - `Java_Certificate_Aarush_Dubey.pdf`: *Advanced Java Concepts* certification (Edureka via Coursera).
5. **`5_Project_Presentation/`**
   - Folder reserved for the final presentation slide deck (`.pptx`).

---

## ⚡ Quick Start: Compilation and Execution

### On macOS / Linux:
```bash
# Option A: From inside 2_Java_Source_Code
cd 2_Java_Source_Code
./compile.sh
./run.sh

# Option B: From the project root
./compile.sh
./run.sh

# Option C: Manual single commands
javac -cp "lib/sqlite-jdbc.jar" Main.java
java -cp ".:lib/sqlite-jdbc.jar" Main
```

The SQLite JDBC driver is included in `lib/sqlite-jdbc.jar`; no separate SQLite
installation is required. In VS Code, open this project folder and select
**Run Car Rental Management System** from the Java launch configurations. On macOS,
you can also double-click `run.command` (you may need to allow it in Privacy & Security
the first time).

### On Windows (Command Prompt):
```cmd
cd 2_Java_Source_Code
compile.bat
run.bat
```

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
# Car-Rental-System-

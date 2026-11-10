import java.awt.FlowLayout;                           // We import FlowLayout so buttons and text boxes line up neatly in a row.
import java.awt.GraphicsEnvironment;                  // We check this to make sure a display screen is connected before opening the window.
import java.io.FileInputStream;                       // We use this to read raw bytes from our file on the computer.
import java.io.FileOutputStream;                      // We use this to write raw bytes into our file on the computer.
import java.sql.Connection;                           // This creates a bridge connecting our Java program to the SQLite database.
import java.sql.DriverManager;                        // This is the driver manager that opens our database connection.
import java.sql.PreparedStatement;                    // We use prepared statements to send safe SQL queries without any security risks.
import java.sql.ResultSet;                            // This holds the rows and columns returned from our database table.
import java.sql.Statement;                            // We use this to run basic SQL commands like creating our table.
import java.util.ArrayList;                           // This is a resizable list where we keep our cars in memory.
import java.util.HashMap;                             // This lets us look up any car instantly using its ID as a key.
import java.util.HashSet;                             // This stores unique items, making sure we do not have duplicate categories.
import java.util.Iterator;                            // This helps us loop through our list of cars one by one.
import java.util.List;                                // This is the standard Java List interface for holding collections.
import java.util.Map;                                 // This is the standard Java Map interface for key and value pairs.
import java.util.Set;                                 // This is the standard Java Set interface for storing unique values.
import javax.swing.JButton;                           // This lets us create clickable buttons on our screen.
import javax.swing.JFrame;                            // This creates the main window frame for our application.
import javax.swing.JLabel;                            // This lets us show text labels and status messages on screen.
import javax.swing.JTextField;                        // This gives the user an input box where they can type words or numbers.

// ==========================================================
// UNIT 1: CLASSES, OBJECTS & INHERITANCE
// ==========================================================

interface Rentable {                                  // This interface promises that any vehicle can display its rental information.
    void displayRentalInfo();                         // Any class that implements this interface must provide this display method.
}                                                     // This marks the end of our Rentable interface.

abstract class Vehicle implements Rentable {          // We make Vehicle abstract because a generic vehicle cannot be rented without specifics.
    protected int vehicleId;                          // This variable stores the unique roll number or ID of the vehicle.
    protected String modelName;                       // This variable stores the model name of the car like Creta or BMW.

    public Vehicle(int vehicleId, String modelName) { // This constructor runs whenever a new Vehicle object is created.
        this.vehicleId = vehicleId;                   // We use 'this' to tell Java we mean the car's own ID variable, not the input parameter.
        this.modelName = modelName;                   // We assign the given model name parameter to this object's modelName field.
    }                                                 // This marks the end of our Vehicle constructor.

    public abstract String getVehicleTier();          // We leave this method abstract so each child class can define its own tier.

    public void displayRentalInfo() {                 // This common method prints out basic vehicle details to the console.
        System.out.println("ID: " + vehicleId + ", Model: " + modelName + " [" + getVehicleTier() + "]"); // Prints the car ID, model, and tier.
    }                                                 // This marks the end of the displayRentalInfo method.
}                                                     // This marks the end of the abstract Vehicle class.

class RentalCar extends Vehicle {                     // RentalCar inherits all common variables and methods from the Vehicle parent class.
    public static int totalFleet = 0;                 // We use a static variable so all cars share one single counter for total fleet size.
    public static final String AGENCY = "SRM Rentals";// We make this final so our agency name cannot be changed anywhere in the program.
    protected String category;                        // This stores the car category such as SUV, Sedan, or Hatchback.
    protected double dailyRate;                       // This stores the price to rent this car for one day in rupees.

    public RentalCar(int vehicleId, String modelName, String category, double dailyRate) { // This constructor accepts all 4 details of a car.
        super(vehicleId, modelName);                  // We use 'super' to pass the ID and name to the parent Vehicle constructor.
        this.category = category;                     // We save the category into our car object's own category variable.
        this.dailyRate = dailyRate;                   // We save the daily rate into our car object's own dailyRate variable.
        totalFleet++;                                 // We increase our total car count by 1 every time a new car is created.
    }                                                 // This marks the end of our 4-parameter constructor.

    public RentalCar(int vehicleId, String modelName, String category) { // This is an overloaded constructor that needs only 3 parameters.
        this(vehicleId, modelName, category, 1500.0); // We call our main constructor and give it a default rental rate of 1500 rupees.
    }                                                 // This marks the end of our overloaded constructor.

    public String getFuelEfficiencyClass() {          // We use an if-else ladder to figure out the car's efficiency class based on rate.
        if (dailyRate >= 3500) return "Premium Performance"; // If the car costs 3500 or more per day, we mark it as Premium.
        else if (dailyRate >= 2000) return "Comfort Cruiser";// If the car costs between 2000 and 3499 per day, we mark it as Comfort.
        else return "Economy Mileage";                // For any price below 2000, we mark it as Economy.
    }                                                 // This marks the end of our efficiency rating method.

    public String getVehicleTier() {                  // Here we override the parent method to give standard cars their tier name.
        return "Standard Rental Car";                 // We return a simple text saying this is a Standard Rental Car.
    }                                                 // This marks the end of the getVehicleTier method.

    public void displayRentalInfo() {                 // Here we override the parent display method to show full rental specifics.
        super.displayRentalInfo();                    // We first call the parent display method to print ID and model.
        System.out.println("Category: " + category + ", Daily Rate: Rs." + dailyRate + ", Class: " + getFuelEfficiencyClass()); // Prints extra details.
    }                                                 // This marks the end of our overridden display method.
}                                                     // This marks the end of the RentalCar class.

class LuxurySedan extends RentalCar {                 // This shows multilevel inheritance where LuxurySedan extends RentalCar, which extends Vehicle.
    protected boolean chauffeurIncluded;              // This true or false flag tells us if a professional driver is included with the car.

    public LuxurySedan(int vehicleId, String modelName, String category, double dailyRate, boolean chauffeurIncluded) { // Constructor for luxury cars.
        super(vehicleId, modelName, category, dailyRate); // We pass all basic car details upward to the RentalCar constructor using super.
        this.chauffeurIncluded = chauffeurIncluded;   // We save the driver option into this luxury car's own chauffeur variable.
    }                                                 // This marks the end of our LuxurySedan constructor.

    public String getVehicleTier() {                  // We override getVehicleTier again to show dynamic method dispatch in action.
        return "Executive Luxury Sedan (Chauffeur: " + (chauffeurIncluded ? "Included" : "Self-Drive") + ")"; // Returns full luxury description.
    }                                                 // This marks the end of the overridden getVehicleTier method.
}                                                     // This marks the end of the LuxurySedan class.

// ==========================================================
// UNIT 2: POLYMORPHISM & EXCEPTION HANDLING
// ==========================================================

class VehicleNotFoundException extends Exception {    // We create our own custom checked exception by extending Java's Exception class.
    public VehicleNotFoundException(String msg) {     // When an error happens, this constructor takes a helpful error message string.
        super(msg);                                   // We send the error message up to Java's built-in Exception class.
    }                                                 // This marks the end of our custom exception constructor.
}                                                     // This marks the end of our VehicleNotFoundException class.

// ==========================================================
// UNIT 3: MULTITHREADING, BYTE STREAMS & EVENT HANDLING
// ==========================================================

class FleetMaintenanceThread extends Thread {         // We show the first way to make a thread in Java: by extending the Thread class.
    public void run() {                               // This run method contains the actual work the thread will do in the background.
        System.out.println("[Thread 1] Fleet maintenance check running at Priority: " + getPriority()); // Shows thread is running and prints its priority.
    }                                                 // This marks the end of the run method.
}                                                     // This marks the end of our FleetMaintenanceThread class.

class DailyBillingTask implements Runnable {          // We show the second way to make a thread in Java: by implementing the Runnable interface.
    public void run() {                               // This method contains the background code that runs when this task starts.
        System.out.println("[Thread 2] Rental revenue audit finished asynchronously via Runnable."); // Shows our background revenue audit has finished.
    }                                                 // This marks the end of the Runnable run method.
}                                                     // This marks the end of our DailyBillingTask class.

class FleetDispatchSync {                             // This class shows how two threads communicate safely using wait() and notify().
    private boolean inspectionDone = false;           // This boolean variable acts as a signal flag between the two threads.

    public synchronized void completeInspection() {   // We use synchronized so only one thread can modify this inspection flag at a time.
        inspectionDone = true;                        // We set the inspection flag to true because safety checks have passed.
        notify();                                     // We call notify() to wake up the waiting customer hand-over thread.
    }                                                 // This marks the end of the completeInspection method.

    public synchronized void dispatchVehicle() throws InterruptedException { // This method makes the car wait until the inspection is finished.
        while (!inspectionDone) wait();               // The car pauses and waits here until the inspection gives the green signal.
        System.out.println("[Sync] Safety inspection verified via wait/notify! Car keys released."); // Informs us that keys are released safely.
    }                                                 // This marks the end of the dispatchVehicle method.
}                                                     // This marks the end of the FleetDispatchSync class.

// ==========================================================
// UNIT 5: GENERICS (CLASS & METHODS)
// ==========================================================

class Pair<K, V> {                                    // This is a generic class with two types K and V, so it can hold any two related items.
    public K key;                                     // This variable holds the first item, like a car model name.
    public V value;                                   // This variable holds the second item, like the daily rental price.

    public Pair(K k, V v) {                           // This constructor takes any two items and saves them together into a pair.
        this.key = k;                                 // We save the first item into our key variable.
        this.value = v;                               // We save the second item into our value variable.
    }                                                 // This marks the end of our generic Pair constructor.
}                                                     // This marks the end of our generic Pair class.

// ==========================================================
// MAIN CLASS: UNIT 4 (JDBC & COLLECTIONS) + FULL CRUD GUI
// ==========================================================

public class Main {                                   // This is our main public class where all the core program execution happens.
    private static final String DB_URL = "jdbc:sqlite:rentals.db"; // This tells Java where our SQLite database file rentals.db is stored on disk.

    public static Connection getDbConnection() throws Exception { // This helper method connects our program to the SQLite database.
        Class.forName("org.sqlite.JDBC");              // We load the SQLite JDBC driver so Java understands how to talk to SQLite.
        return DriverManager.getConnection(DB_URL);    // We open and return an active connection to our rentals.db file.
    }                                                 // This marks the end of our getDbConnection method.

    // CRUD: CREATE / INSERT
    public static void saveVehicle(RentalCar c) throws Exception { // This method saves a new car permanently into our SQLite database.
        try (Connection conn = getDbConnection()) {   // We open a database connection using try-with-resources so it closes automatically.
            String sql = "INSERT OR REPLACE INTO fleet VALUES (?, ?, ?, ?)"; // We use question marks so user input is safely added without SQL injection.
            try (PreparedStatement ps = conn.prepareStatement(sql)) { // We prepare the SQL query so the database can execute it fast and securely.
                ps.setInt(1, c.vehicleId);             // We fill the 1st question mark with the vehicle's numeric ID.
                ps.setString(2, c.modelName);          // We fill the 2nd question mark with the car model name.
                ps.setString(3, c.category);           // We fill the 3rd question mark with the car category.
                ps.setDouble(4, c.dailyRate);          // We fill the 4th question mark with the daily rental rate.
                ps.executeUpdate();                    // We run the insert query to save the car row in the database table.
            }                                         // The prepared statement is closed automatically here.
        }                                             // The database connection is closed automatically here.
    }                                                 // This marks the end of our saveVehicle method.

    // CRUD: UPDATE
    public static void updateVehicleRate(int id, double newRate) throws Exception { // This method updates the daily rate of an existing car.
        try (Connection conn = getDbConnection()) {   // We open our database connection with automatic closing.
            String sql = "UPDATE fleet SET rate = ? WHERE id = ?"; // This SQL command tells the database to update the rate for a matching ID.
            try (PreparedStatement ps = conn.prepareStatement(sql)) { // We prepare our update statement.
                ps.setDouble(1, newRate);              // We set the new rental rate into the first question mark.
                ps.setInt(2, id);                      // We set the target vehicle ID into the second question mark.
                ps.executeUpdate();                    // We execute the update query to save the new rate in SQLite.
            }                                         // The prepared statement closes automatically here.
        }                                             // The database connection closes automatically here.
    }                                                 // This marks the end of our updateVehicleRate method.

    // CRUD: DELETE
    public static void deleteVehicle(int id) throws Exception { // This method permanently removes a car from the database by its ID.
        try (Connection conn = getDbConnection()) {   // We open our database connection safely.
            String sql = "DELETE FROM fleet WHERE id = ?"; // This SQL command tells the database to delete the row matching this vehicle ID.
            try (PreparedStatement ps = conn.prepareStatement(sql)) { // We prepare our delete query statement.
                ps.setInt(1, id);                      // We fill the question mark with the ID of the vehicle we want to delete.
                ps.executeUpdate();                    // We execute the delete query to remove the row from SQLite.
            }                                         // The prepared statement closes automatically here.
        }                                             // The database connection closes automatically here.
    }                                                 // This marks the end of our deleteVehicle method.

    // Overloaded search methods (Unit 2: Method Overloading - Compile-time Polymorphism)
    public static RentalCar search(List<RentalCar> list, int vehicleId) throws VehicleNotFoundException { // We overload search to find cars by numeric ID.
        for (RentalCar c : list) if (c.vehicleId == vehicleId) return c; // We check each car in the list, and if the ID matches, we return that car.
        throw new VehicleNotFoundException("Vehicle ID " + vehicleId + " not found in fleet!"); // If we find no match after checking all, we throw our custom error.
    }                                                 // This marks the end of our search by ID method.

    public static RentalCar search(List<RentalCar> list, String model) throws VehicleNotFoundException { // We overload search to find cars by model name.
        for (RentalCar c : list) if (c.modelName.equalsIgnoreCase(model)) return c; // We check each car comparing names ignoring uppercase or lowercase.
        throw new VehicleNotFoundException("Vehicle model '" + model + "' not found!"); // If no matching model name is found, we throw our custom error.
    }                                                 // This marks the end of our search by model name method.

    public static void inspectFleet(List<? extends Vehicle> list) { // We use a generic wildcard '? extends Vehicle' to accept any subtype of Vehicle.
        for (Vehicle v : list) System.out.println("Inspected: " + v.modelName + " (" + v.getVehicleTier() + ")"); // We inspect and print each vehicle polymorphically.
    }                                                 // This marks the end of our inspectFleet method.

    public static void main(String[] args) {          // This is the main method where our Java program begins execution.
        System.out.println("=== VEHICLE / CAR RENTAL SYSTEM (CSE 307) ===\n"); // We print the project title banner in the console terminal.

        // [UNIT 1] CLASSES, INHERITANCE, STATIC & FINAL
        System.out.println("--- [UNIT 1] CLASSES & INHERITANCE ---");          // We print the Unit 1 section heading in the terminal.
        System.out.println("Agency: " + RentalCar.AGENCY);                     // We print the static final agency name constant.
        RentalCar c1 = new RentalCar(101, "Hyundai Creta", "SUV", 2800.0);    // We create a standard RentalCar object with ID 101.
        LuxurySedan c2 = new LuxurySedan(201, "Mercedes C-Class", "Luxury Sedan", 6500.0, true); // We create a LuxurySedan object with ID 201.
        c1.displayRentalInfo();                                                // We call the display method on our standard car.
        c2.displayRentalInfo();                                                // We call the display method on our luxury car.
        System.out.println("Total Fleet Count (static): " + RentalCar.totalFleet); // We print our shared static counter showing total cars created.

        // [UNIT 2] POLYMORPHISM & EXCEPTION HANDLING
        System.out.println("\n--- [UNIT 2] POLYMORPHISM & EXCEPTIONS ---");    // We print the Unit 2 section heading in the terminal.
        Vehicle polyVehicle = new LuxurySedan(202, "BMW 5 Series", "Executive", 7200.0, true); // We store a LuxurySedan object inside a parent Vehicle reference.
        System.out.println("Dynamic dispatch vehicle tier: " + polyVehicle.getVehicleTier());   // Java dynamically calls the child LuxurySedan method at runtime.

        try {                                                                  // We begin a try block to test our custom exception handling.
            List<RentalCar> temp = new ArrayList<>();                          // We make a small temporary list of cars for testing.
            temp.add(c1);                                                      // We add car c1 to this temporary list.
            search(temp, 999);                                                 // We search for a non-existent ID 999 to purposely trigger our exception.
        } catch (VehicleNotFoundException e) {                                 // We catch our custom VehicleNotFoundException when it occurs.
            System.out.println("Caught Custom Exception: " + e.getMessage());  // We print out the exact error message carried by our exception.
        } finally {                                                            // The finally block is guaranteed to run whether an error happened or not.
            System.out.println("Finally block executed.");                     // We print confirmation that our finally cleanup block has executed.
        }                                                                      // This marks the end of our try-catch-finally block.

        // [UNIT 3] MULTITHREADING, BYTE STREAMS & EVENT HANDLING
        System.out.println("\n--- [UNIT 3] THREADS & BYTE STREAMS ---");       // We print the Unit 3 section heading in the terminal.
        FleetMaintenanceThread t1 = new FleetMaintenanceThread();              // We create an instance of our thread that extends Thread.
        t1.setPriority(Thread.MAX_PRIORITY);                                   // We set its priority to 10 so the CPU knows it is high priority.
        t1.start();                                                            // We start Thread 1 so it runs independently in the background.
        new Thread(new DailyBillingTask()).start();                            // We start Thread 2 by wrapping our Runnable task inside a Thread.

        try {                                                                  // We begin a try block to test thread sync and file byte streams.
            FleetDispatchSync sync = new FleetDispatchSync();                  // We create our synchronization coordinator object.
            new Thread(() -> sync.completeInspection()).start();                // We start a background thread that finishes the inspection and notifies.
            sync.dispatchVehicle();                                            // Our main thread pauses and waits until the notification arrives.

            // Byte Streams (FileOutputStream & FileInputStream)
            FileOutputStream fos = new FileOutputStream("rental_agreement.dat");// We open a byte output stream to write raw bytes into a file on disk.
            fos.write("101,Hyundai Creta,SUV,Rate:2800".getBytes());            // We turn our rental agreement text into bytes and write it to disk.
            fos.close();                                                       // We close our output stream to ensure all data is flushed and saved.

            FileInputStream fis = new FileInputStream("rental_agreement.dat");  // We open a byte input stream to read the raw bytes back from the file.
            byte[] bytes = new byte[fis.available()];                          // We create a byte array of the exact size of the file.
            fis.read(bytes);                                                   // We read all bytes from the file into our byte array buffer.
            fis.close();                                                       // We close our input stream after reading is complete.
            System.out.println("Byte Stream Read: " + new String(bytes));       // We convert the byte array back into readable text and print it.
        } catch (Exception ignored) {}                                         // If any file error occurs, we catch it safely.

        // [UNIT 4] JDBC DATABASE & COLLECTIONS FRAMEWORK
        System.out.println("\n--- [UNIT 4] JDBC & COLLECTIONS ---");           // We print the Unit 4 section heading in the terminal.
        List<RentalCar> fleetList = new ArrayList<>();                          // We make an ArrayList collection to hold our cars in memory.
        Set<String> categories = new HashSet<>();                              // We make a HashSet collection to store our unique car categories.
        Map<Integer, RentalCar> map = new HashMap<>();                         // We make a HashMap collection so we can find any car using its ID as a key.

        try {                                                                  // We begin a try block to set up and query our SQLite database.
            try (Connection conn = getDbConnection(); Statement st = conn.createStatement()) { // We connect to SQLite and create a statement.
                st.executeUpdate("CREATE TABLE IF NOT EXISTS fleet (id INT PRIMARY KEY, model TEXT, category TEXT, rate REAL)"); // We create our fleet table if it does not exist.
            }                                                                  // The connection and statement close automatically here.
            saveVehicle(c1);                                                   // We save car c1 into our SQLite database table.
            saveVehicle(c2);                                                   // We save car c2 into our SQLite database table.

            try (Connection conn = getDbConnection(); Statement st = conn.createStatement(); // We open a connection to read back our stored cars.
                 ResultSet rs = st.executeQuery("SELECT * FROM fleet")) {      // We run a SELECT query to get all rows from our fleet table.
                while (rs.next()) {                                            // We loop through each row returned from the database table.
                    fleetList.add(new RentalCar(rs.getInt("id"), rs.getString("model"), rs.getString("category"), rs.getDouble("rate"))); // We turn each database row into a RentalCar object.
                }                                                              // This marks the end of our database reading loop.
            }                                                                  // The database result set and connection close automatically here.
        } catch (Exception e) {                                                // If database is not available, we catch the error gracefully.
            fleetList.add(c1);                                                 // We add our sample car c1 directly to our memory list.
            fleetList.add(c2);                                                 // We add our sample car c2 directly to our memory list.
        }                                                                      // This marks the end of our database setup block.

        // Iterator traversal
        Iterator<RentalCar> it = fleetList.iterator();                         // We get an Iterator to walk through our ArrayList one car at a time.
        while (it.hasNext()) {                                                 // As long as there is another car left in the list, we keep looping.
            RentalCar c = it.next();                                           // We grab the next car from our iterator.
            categories.add(c.category);                                        // We add its category to our HashSet, which automatically ignores duplicates.
            map.put(c.vehicleId, c);                                           // We store the car in our HashMap using its vehicleId as the lookup key.
        }                                                                      // This marks the end of our collection iteration loop.
        System.out.println("Unique Categories (HashSet): " + categories);      // We print our unique set of car categories stored in the HashSet.
        System.out.println("Fleet Map Keys (HashMap): " + map.keySet());       // We print all the registered vehicle ID keys stored in the HashMap.

        try {                                                                  // We begin a try block to test our overloaded search methods.
            System.out.println("Overloaded Search (ID 101): " + search(fleetList, 101).modelName); // We search by integer ID and print the car model.
            System.out.println("Overloaded Search (Model 'Hyundai Creta'): ID " + search(fleetList, "Hyundai Creta").vehicleId); // We search by string model and print the car ID.
        } catch (Exception ignored) {}                                         // We safely ignore any search errors during this terminal demo.

        // [UNIT 5] GENERICS & FULL CRUD GUI
        System.out.println("\n--- [UNIT 5] GENERICS & GUI ---");               // We print the Unit 5 section heading in the terminal.
        Pair<String, Double> mostExclusive = new Pair<>("BMW 5 Series", 7200.0);// We use our generic Pair to hold the top car model and its price together.
        System.out.println("Generic Pair (Most Exclusive): " + mostExclusive.key + " (Rs." + mostExclusive.value + "/day)"); // We print both items stored inside our generic Pair.

        List<Vehicle> vehicles = new ArrayList<>();                            // We create a list that holds items of the base Vehicle type.
        vehicles.add(c1);                                                      // We add our standard car to this vehicle list.
        vehicles.add(c2);                                                      // We add our luxury car to this vehicle list.
        inspectFleet(vehicles);                                                // We pass our list to the generic wildcard method inspectFleet.

        // Full Interactive CRUD Swing GUI (Delegation Event Model)
        if (!GraphicsEnvironment.isHeadless()) {                               // We make sure a computer monitor is available before showing the window.
            JFrame f = new JFrame("Car Rental Management System - Full CRUD (SRM AP)"); // We create our main desktop window with a title bar.
            f.setSize(520, 240);                                               // We set our window width to 520 pixels and height to 240 pixels.
            f.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 8));             // We arrange all labels, text boxes, and buttons centered with neat spacing.
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                  // When the user clicks the X button, we close the entire program.

            JTextField tfId = new JTextField(5);                               // We create a small text box where the user enters the vehicle ID.
            JTextField tfModel = new JTextField(10);                           // We create a text box where the user enters the car model name.
            JTextField tfCategory = new JTextField(8);                         // We create a text box where the user enters the car category like SUV.
            JTextField tfRate = new JTextField(6);                             // We create a text box where the user enters the daily rental price.

            JButton btnAdd = new JButton("Add Vehicle");                       // We create a clickable button to add a new car to the database.
            JButton btnSearch = new JButton("Search ID");                      // We create a clickable button to search and view a car by its ID.
            JButton btnUpdate = new JButton("Update Rate");                    // We create a clickable button to update the daily rate of an existing car.
            JButton btnDelete = new JButton("Delete Vehicle");                 // We create a clickable button to delete a car from the database.
            JLabel res = new JLabel("Status: System ready. Perform any CRUD operation above."); // We create a status label to show feedback messages.

            // Action 1: CREATE / ADD VEHICLE
            btnAdd.addActionListener(e -> {                                    // We listen for when the user clicks the 'Add Vehicle' button.
                try {                                                          // We use a try block to handle any typing mistakes by the user.
                    int id = Integer.parseInt(tfId.getText().trim());          // We convert the text typed in the ID box into a real number.
                    String model = tfModel.getText().trim();                   // We read the car model name text typed by the user.
                    String cat = tfCategory.getText().trim();                  // We read the car category text typed by the user.
                    double rate = Double.parseDouble(tfRate.getText().trim()); // We convert the text typed in the rate box into a decimal number.
                    RentalCar newCar = new RentalCar(id, model, cat, rate);    // We create a new RentalCar object using the user's typed values.
                    saveVehicle(newCar);                                       // We call our save method to store this new car permanently into SQLite.
                    fleetList.removeIf(c -> c.vehicleId == id);                // We remove any old car with this same ID from our memory list.
                    fleetList.add(newCar);                                     // We add our newly created car into our memory list.
                    map.put(id, newCar);                                       // We update our HashMap so this new car can be found instantly by ID.
                    categories.add(cat);                                       // We add this category to our HashSet so it stays up to date.
                    res.setText("Status: Added vehicle " + model + " (ID: " + id + ") successfully!"); // We display a success message on the screen.
                } catch (Exception ex) {                                       // If the user entered empty or invalid text, we catch the error.
                    res.setText("Error: Enter valid ID, Model, Category, and Rate!"); // We guide the user to fill in all boxes properly.
                }                                                              // This marks the end of our Add button error handling.
            });                                                                // This marks the end of our Add button listener.

            // Action 2: RETRIEVE / SEARCH VEHICLE
            btnSearch.addActionListener(e -> {                                 // We listen for when the user clicks the 'Search ID' button.
                try {                                                          // We use a try block in case the car is not found or ID is invalid.
                    int id = Integer.parseInt(tfId.getText().trim());          // We read the vehicle ID typed by the user and turn it into a number.
                    RentalCar c = search(fleetList, id);                       // We search our list for the car matching this ID using our search method.
                    tfModel.setText(c.modelName);                              // We automatically fill the model text box with the car's model name.
                    tfCategory.setText(c.category);                            // We automatically fill the category text box with the car's category.
                    tfRate.setText(String.valueOf(c.dailyRate));               // We automatically fill the rate text box with the car's daily rate.
                    res.setText("Found: " + c.modelName + " (" + c.category + ", Rs." + c.dailyRate + "/day)"); // We display the found car details.
                } catch (Exception ex) {                                       // If the ID was not found in our fleet, we catch the exception.
                    res.setText("Error: Vehicle ID not found in fleet!");      // We inform the user on screen that this car ID does not exist.
                }                                                              // This marks the end of our Search button error handling.
            });                                                                // This marks the end of our Search button listener.

            // Action 3: UPDATE VEHICLE RATE
            btnUpdate.addActionListener(e -> {                                 // We listen for when the user clicks the 'Update Rate' button.
                try {                                                          // We use a try block to handle any update or typing errors.
                    int id = Integer.parseInt(tfId.getText().trim());          // We get the vehicle ID of the car whose rate we want to update.
                    double newRate = Double.parseDouble(tfRate.getText().trim()); // We get the new price typed by the user.
                    updateVehicleRate(id, newRate);                            // We call our database update method to update SQLite permanently.
                    RentalCar c = search(fleetList, id);                       // We find the car in our memory list.
                    c.dailyRate = newRate;                                     // We update the dailyRate variable inside the car object in memory.
                    res.setText("Status: Updated rate for ID " + id + " to Rs." + newRate + "/day!"); // We show a success update message on screen.
                } catch (Exception ex) {                                       // If the ID was wrong or numbers were invalid, we catch the error.
                    res.setText("Error: Enter valid ID and new Rate to update!"); // We tell the user to enter a valid ID and price.
                }                                                              // This marks the end of our Update button error handling.
            });                                                                // This marks the end of our Update button listener.

            // Action 4: DELETE VEHICLE
            btnDelete.addActionListener(e -> {                                 // We listen for when the user clicks the 'Delete Vehicle' button.
                try {                                                          // We use a try block to handle any deletion or parsing errors.
                    int id = Integer.parseInt(tfId.getText().trim());          // We read the ID of the vehicle the user wants to delete.
                    deleteVehicle(id);                                         // We call our database method to remove the car from SQLite.
                    fleetList.removeIf(c -> c.vehicleId == id);                // We remove the car from our ArrayList in memory.
                    map.remove(id);                                            // We remove the car from our HashMap in memory.
                    tfModel.setText("");                                       // We clear the model text box on screen.
                    tfCategory.setText("");                                    // We clear the category text box on screen.
                    tfRate.setText("");                                        // We clear the rate text box on screen.
                    res.setText("Status: Deleted vehicle ID " + id + " from database and fleet!"); // We display a success deletion message on screen.
                } catch (Exception ex) {                                       // If the user typed an invalid number, we catch the error.
                    res.setText("Error: Enter valid ID to delete!");           // We tell the user to enter a valid ID number to delete.
                }                                                              // This marks the end of our Delete button error handling.
            });                                                                // This marks the end of our Delete button listener.

            // Add all visual form components to GUI window frame
            f.add(new JLabel("ID:"));                                          // We add the label 'ID:' to our window.
            f.add(tfId);                                                       // We add our ID input box to our window.
            f.add(new JLabel("Model:"));                                       // We add the label 'Model:' to our window.
            f.add(tfModel);                                                    // We add our model input box to our window.
            f.add(new JLabel("Category:"));                                    // We add the label 'Category:' to our window.
            f.add(tfCategory);                                                 // We add our category input box to our window.
            f.add(new JLabel("Daily Rate:"));                                  // We add the label 'Daily Rate:' to our window.
            f.add(tfRate);                                                     // We add our rate input box to our window.
            f.add(btnAdd);                                                     // We add our 'Add Vehicle' button to our window.
            f.add(btnSearch);                                                  // We add our 'Search ID' button to our window.
            f.add(btnUpdate);                                                  // We add our 'Update Rate' button to our window.
            f.add(btnDelete);                                                  // We add our 'Delete Vehicle' button to our window.
            f.add(res);                                                        // We add our status text label to show feedback messages.
            f.setLocationRelativeTo(null);                                     // We center our application window right in the middle of the screen.
            f.setVisible(true);                                                // We make our application window visible so the user can interact with it.
            System.out.println("Full CRUD GUI window launched!");              // We print confirmation in the console that the window has launched.
        }                                                                      // This marks the end of our desktop check.
    }                                                                          // This marks the end of our main method.
}                                                                              // This marks the end of our Main class.

import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

interface Rentable {
    void displayRentalInfo();
}

abstract class Vehicle implements Rentable {
    protected int vehicleId;
    protected String modelName;

    public Vehicle(int vehicleId, String modelName) {
        this.vehicleId = vehicleId;
        this.modelName = modelName;
    }

    public abstract String getVehicleTier();

    public void displayRentalInfo() {
        System.out.println("ID: " + vehicleId + ", Model: " + modelName + " [" + getVehicleTier() + "]");
    }
}

class RentalCar extends Vehicle {
    public static int totalFleet = 0;
    public static final String AGENCY = "SRM Rentals";
    protected String category;
    protected double dailyRate;

    public RentalCar(int vehicleId, String modelName, String category, double dailyRate) {
        super(vehicleId, modelName);
        this.category = category;
        this.dailyRate = dailyRate;
        totalFleet++;
    }

    public RentalCar(int vehicleId, String modelName, String category) {
        this(vehicleId, modelName, category, 1500.0);
    }

    public String getFuelEfficiencyClass() {
        if (dailyRate >= 3500) return "Premium Performance";
        else if (dailyRate >= 2000) return "Comfort Cruiser";
        else return "Economy Mileage";
    }

    public String getVehicleTier() {
        return "Standard Rental Car";
    }

    public void displayRentalInfo() {
        super.displayRentalInfo();
        System.out.println("Category: " + category + ", Daily Rate: Rs." + dailyRate + ", Class: " + getFuelEfficiencyClass());
    }
}

class LuxurySedan extends RentalCar {
    protected boolean chauffeurIncluded;

    public LuxurySedan(int vehicleId, String modelName, String category, double dailyRate, boolean chauffeurIncluded) {
        super(vehicleId, modelName, category, dailyRate);
        this.chauffeurIncluded = chauffeurIncluded;
    }

    public String getVehicleTier() {
        return "Executive Luxury Sedan (Chauffeur: " + (chauffeurIncluded ? "Included" : "Self-Drive") + ")";
    }
}

class VehicleNotFoundException extends Exception {
    public VehicleNotFoundException(String msg) {
        super(msg);
    }
}

class FleetMaintenanceThread extends Thread {
    public void run() {
        System.out.println("[Thread 1] Fleet maintenance check running at Priority: " + getPriority());
    }
}

class DailyBillingTask implements Runnable {
    public void run() {
        System.out.println("[Thread 2] Rental revenue audit finished asynchronously via Runnable.");
    }
}

class FleetDispatchSync {
    private boolean inspectionDone = false;

    public synchronized void completeInspection() {
        inspectionDone = true;
        notify();
    }

    public synchronized void dispatchVehicle() throws InterruptedException {
        while (!inspectionDone) wait();
        System.out.println("[Sync] Safety inspection verified via wait/notify! Car keys released.");
    }
}

class Pair<K, V> {
    public K key;
    public V value;

    public Pair(K k, V v) {
        this.key = k;
        this.value = v;
    }
}

public class Main {
    private static final String DB_URL = "jdbc:sqlite:rentals.db";

    public static Connection getDbConnection() throws Exception {
        Class.forName("org.sqlite.JDBC");
        return DriverManager.getConnection(DB_URL);
    }

    public static void saveVehicle(RentalCar c) throws Exception {
        try (Connection conn = getDbConnection()) {
            String sql = "INSERT OR REPLACE INTO fleet VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, c.vehicleId);
                ps.setString(2, c.modelName);
                ps.setString(3, c.category);
                ps.setDouble(4, c.dailyRate);
                ps.executeUpdate();
            }
        }
    }

    public static void updateVehicleRate(int id, double newRate) throws Exception {
        try (Connection conn = getDbConnection()) {
            String sql = "UPDATE fleet SET rate = ? WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setDouble(1, newRate);
                ps.setInt(2, id);
                ps.executeUpdate();
            }
        }
    }

    public static void deleteVehicle(int id) throws Exception {
        try (Connection conn = getDbConnection()) {
            String sql = "DELETE FROM fleet WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        }
    }

    public static RentalCar search(List<RentalCar> list, int vehicleId) throws VehicleNotFoundException {
        for (RentalCar c : list) if (c.vehicleId == vehicleId) return c;
        throw new VehicleNotFoundException("Vehicle ID " + vehicleId + " not found in fleet!");
    }

    public static RentalCar search(List<RentalCar> list, String model) throws VehicleNotFoundException {
        for (RentalCar c : list) if (c.modelName.equalsIgnoreCase(model)) return c;
        throw new VehicleNotFoundException("Vehicle model '" + model + "' not found!");
    }

    public static void inspectFleet(List<? extends Vehicle> list) {
        for (Vehicle v : list) System.out.println("Inspected: " + v.modelName + " (" + v.getVehicleTier() + ")");
    }

    public static void main(String[] args) {
        System.out.println("=== VEHICLE / CAR RENTAL SYSTEM (CSE 307) ===\n");

        System.out.println("--- [UNIT 1] CLASSES & INHERITANCE ---");
        System.out.println("Agency: " + RentalCar.AGENCY);
        RentalCar c1 = new RentalCar(101, "Hyundai Creta", "SUV", 2800.0);
        LuxurySedan c2 = new LuxurySedan(201, "Mercedes C-Class", "Luxury Sedan", 6500.0, true);
        c1.displayRentalInfo();
        c2.displayRentalInfo();
        System.out.println("Total Fleet Count (static): " + RentalCar.totalFleet);

        System.out.println("\n--- [UNIT 2] POLYMORPHISM & EXCEPTIONS ---");
        Vehicle polyVehicle = new LuxurySedan(202, "BMW 5 Series", "Executive", 7200.0, true);
        System.out.println("Dynamic dispatch vehicle tier: " + polyVehicle.getVehicleTier());

        try {
            List<RentalCar> temp = new ArrayList<>();
            temp.add(c1);
            search(temp, 999);
        } catch (VehicleNotFoundException e) {
            System.out.println("Caught Custom Exception: " + e.getMessage());
        } finally {
            System.out.println("Finally block executed.");
        }

        System.out.println("\n--- [UNIT 3] THREADS & BYTE STREAMS ---");
        FleetMaintenanceThread t1 = new FleetMaintenanceThread();
        t1.setPriority(Thread.MAX_PRIORITY);
        t1.start();
        new Thread(new DailyBillingTask()).start();

        try {
            FleetDispatchSync sync = new FleetDispatchSync();
            new Thread(() -> sync.completeInspection()).start();
            sync.dispatchVehicle();

            FileOutputStream fos = new FileOutputStream("rental_agreement.dat");
            fos.write("101,Hyundai Creta,SUV,Rate:2800".getBytes());
            fos.close();

            FileInputStream fis = new FileInputStream("rental_agreement.dat");
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            fis.close();
            System.out.println("Byte Stream Read: " + new String(bytes));
        } catch (Exception ignored) {}

        System.out.println("\n--- [UNIT 4] JDBC & COLLECTIONS ---");
        List<RentalCar> fleetList = new ArrayList<>();
        Set<String> categories = new HashSet<>();
        Map<Integer, RentalCar> map = new HashMap<>();

        try {
            try (Connection conn = getDbConnection(); Statement st = conn.createStatement()) {
                st.executeUpdate("CREATE TABLE IF NOT EXISTS fleet (id INT PRIMARY KEY, model TEXT, category TEXT, rate REAL)");
            }
            saveVehicle(c1);
            saveVehicle(c2);

            try (Connection conn = getDbConnection(); Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery("SELECT * FROM fleet")) {
                while (rs.next()) {
                    fleetList.add(new RentalCar(rs.getInt("id"), rs.getString("model"), rs.getString("category"), rs.getDouble("rate")));
                }
            }
        } catch (Exception e) {
            fleetList.add(c1);
            fleetList.add(c2);
        }

        Iterator<RentalCar> it = fleetList.iterator();
        while (it.hasNext()) {
            RentalCar c = it.next();
            categories.add(c.category);
            map.put(c.vehicleId, c);
        }
        System.out.println("Unique Categories (HashSet): " + categories);
        System.out.println("Fleet Map Keys (HashMap): " + map.keySet());

        try {
            System.out.println("Overloaded Search (ID 101): " + search(fleetList, 101).modelName);
            System.out.println("Overloaded Search (Model 'Hyundai Creta'): ID " + search(fleetList, "Hyundai Creta").vehicleId);
        } catch (Exception ignored) {}

        System.out.println("\n--- [UNIT 5] GENERICS & GUI ---");
        Pair<String, Double> mostExclusive = new Pair<>("BMW 5 Series", 7200.0);
        System.out.println("Generic Pair (Most Exclusive): " + mostExclusive.key + " (Rs." + mostExclusive.value + "/day)");

        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(c1);
        vehicles.add(c2);
        inspectFleet(vehicles);

        if (!GraphicsEnvironment.isHeadless()) {
            JFrame f = new JFrame("Car Rental Management System - Full CRUD (SRM AP)");
            f.setSize(520, 240);
            f.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 8));
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JTextField tfId = new JTextField(5);
            JTextField tfModel = new JTextField(10);
            JTextField tfCategory = new JTextField(8);
            JTextField tfRate = new JTextField(6);

            JButton btnAdd = new JButton("Add Vehicle");
            JButton btnSearch = new JButton("Search ID");
            JButton btnUpdate = new JButton("Update Rate");
            JButton btnDelete = new JButton("Delete Vehicle");
            JLabel res = new JLabel("Status: System ready. Perform any CRUD operation above.");

            btnAdd.addActionListener(e -> {
                try {
                    int id = Integer.parseInt(tfId.getText().trim());
                    String model = tfModel.getText().trim();
                    String cat = tfCategory.getText().trim();
                    double rate = Double.parseDouble(tfRate.getText().trim());
                    RentalCar newCar = new RentalCar(id, model, cat, rate);
                    saveVehicle(newCar);
                    fleetList.removeIf(c -> c.vehicleId == id);
                    fleetList.add(newCar);
                    map.put(id, newCar);
                    categories.add(cat);
                    res.setText("Status: Added vehicle " + model + " (ID: " + id + ") successfully!");
                } catch (Exception ex) {
                    res.setText("Error: Enter valid ID, Model, Category, and Rate!");
                }
            });

            btnSearch.addActionListener(e -> {
                try {
                    int id = Integer.parseInt(tfId.getText().trim());
                    RentalCar c = search(fleetList, id);
                    tfModel.setText(c.modelName);
                    tfCategory.setText(c.category);
                    tfRate.setText(String.valueOf(c.dailyRate));
                    res.setText("Found: " + c.modelName + " (" + c.category + ", Rs." + c.dailyRate + "/day)");
                } catch (Exception ex) {
                    res.setText("Error: Vehicle ID not found in fleet!");
                }
            });

            btnUpdate.addActionListener(e -> {
                try {
                    int id = Integer.parseInt(tfId.getText().trim());
                    double newRate = Double.parseDouble(tfRate.getText().trim());
                    updateVehicleRate(id, newRate);
                    RentalCar c = search(fleetList, id);
                    c.dailyRate = newRate;
                    res.setText("Status: Updated rate for ID " + id + " to Rs." + newRate + "/day!");
                } catch (Exception ex) {
                    res.setText("Error: Enter valid ID and new Rate to update!");
                }
            });

            btnDelete.addActionListener(e -> {
                try {
                    int id = Integer.parseInt(tfId.getText().trim());
                    deleteVehicle(id);
                    fleetList.removeIf(c -> c.vehicleId == id);
                    map.remove(id);
                    tfModel.setText("");
                    tfCategory.setText("");
                    tfRate.setText("");
                    res.setText("Status: Deleted vehicle ID " + id + " from database and fleet!");
                } catch (Exception ex) {
                    res.setText("Error: Enter valid ID to delete!");
                }
            });

            f.add(new JLabel("ID:"));
            f.add(tfId);
            f.add(new JLabel("Model:"));
            f.add(tfModel);
            f.add(new JLabel("Category:"));
            f.add(tfCategory);
            f.add(new JLabel("Daily Rate:"));
            f.add(tfRate);
            f.add(btnAdd);
            f.add(btnSearch);
            f.add(btnUpdate);
            f.add(btnDelete);
            f.add(res);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
            System.out.println("Full CRUD GUI window launched!");
        }
    }
}

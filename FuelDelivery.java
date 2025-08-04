import java.sql.*;
import java.util.*;
import static java.lang.System.out;

public class FuelDeliveryApp {
    static Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/fuel_delivery", "root", "your_password");
    }

    public static void addDriver(String name) throws SQLException {
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO drivers(name, is_available) VALUES(?, true)")) {
            ps.setString(1, name);
            ps.executeUpdate();
        }
    }

    public static void addCustomer(Customer c) throws SQLException {
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO customers(name, phone_number, location, fuel_type, payment_amount) VALUES (?, ?, ?, ?, ?)")) {
            ps.setString(1, c.name);
            ps.setString(2, c.phoneNumber);
            ps.setString(3, c.location);
            ps.setString(4, c.fuelType);
            ps.setDouble(5, c.paymentAmount);
            ps.executeUpdate();
        }
    }

    public static List<Customer> getCustomersDescending() throws SQLException {
        List<Customer> list = new ArrayList<>();
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM customers ORDER BY payment_amount DESC")) {
            while (rs.next()) {
                list.add(new Customer(
                    rs.getString("name"),
                    rs.getString("phone_number"),
                    rs.getString("location"),
                    rs.getString("fuel_type"),
                    rs.getDouble("payment_amount")
                ));
            }
        }
        return list;
    }

    public static Driver getAvailableDriver() throws SQLException {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM drivers WHERE is_available = true LIMIT 1")) {
            if (rs.next()) {
                return new Driver(rs.getString("name"), true);
            }
        }
        return null;
    }

    public static void assignDriver(String driverName) throws SQLException {
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement("UPDATE drivers SET is_available = false WHERE name = ?")) {
            ps.setString(1, driverName);
            ps.executeUpdate();
        }
    }

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        while (true) {
            out.println("\n1. Add Driver\n2. Add Customer\n3. Assign Deliveries\n4. Exit");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    out.print("Enter driver name: ");
                    String dName = sc.nextLine();
                    addDriver(dName);
                    out.println("Driver added.");
                }
                case 2 -> {
                    out.print("Enter customer name: ");
                    String cName = sc.nextLine();
                    out.print("Phone number: ");
                    String phone = sc.nextLine();
                    out.print("Location: ");
                    String loc = sc.nextLine();
                    out.print("Fuel type: ");
                    String fuel = sc.nextLine();
                    out.print("Payment amount: ");
                    double amount = sc.nextDouble();
                    sc.nextLine();
                    Customer c = new Customer(cName, phone, loc, fuel, amount);
                    addCustomer(c);
                    out.println("Customer added.");
                }
                case 3 -> {
                    List<Customer> customers = getCustomersDescending();
                    for (Customer c : customers) {
                        Driver d = getAvailableDriver();
                        if (d != null) {
                            out.println("Assigning driver " + d.name + " to " + c.name + " at " + c.location);
                            assignDriver(d.name);
                        } else {
                            out.println("No available drivers for " + c.name);
                            break;
                        }
                    }
                }
                case 4 -> {
                    out.println("Exiting...");
                    sc.close();
                    return;
                }
                default -> out.println("Invalid choice.");
            }
        }
    }
}

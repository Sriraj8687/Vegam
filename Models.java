public class Customer {
    public String name;
    public String phoneNumber;
    public String location;
    public String fuelType;
    public double paymentAmount;

    public Customer(String name, String phoneNumber, String location, String fuelType, double paymentAmount) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.fuelType = fuelType;
        this.paymentAmount = paymentAmount;
    }
}

public class Driver {
    public String name;
    public boolean isAvailable;

    public Driver(String name, boolean isAvailable) {
        this.name = name;
        this.isAvailable = isAvailable;
    }
}

public class Truck extends Vehicle {
    public Truck(String model, String registration, String subtype, int mileage)
    {
        super(model, registration, subtype, mileage);
    }
    @Override public String getCategory()
    {
        return "TRUCK";
    }
}

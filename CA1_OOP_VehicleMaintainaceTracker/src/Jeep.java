public class Jeep extends Vehicle {
    public Jeep(String model, String registration, String subtype, int mileage)
    {
        super(model, registration, subtype, mileage);
    }
    @Override public String getCategory()
    {
        return "JEEP";
    }
}

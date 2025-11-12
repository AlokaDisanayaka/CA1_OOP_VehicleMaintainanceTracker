public class Car extends Vehicle {
    public Car(String model, String registration, String subtype, int mileage)
    {
        super(model, registration, subtype, mileage);
    }
    @Override public String getCategory()
    {
        return "CAR";
    }
}


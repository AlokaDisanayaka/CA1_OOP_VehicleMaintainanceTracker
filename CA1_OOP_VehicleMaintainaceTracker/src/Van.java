public class Van extends Vehicle {
    public Van(String model, String registration, String subtype, int mileage)
    {
        super(model, registration, subtype, mileage);
    }
    @Override public String getCategory()
    {
        return "VAN";
    }
}

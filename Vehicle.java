public abstract class Vehicle extends RecordBase {

    private final String model;
    private final String registration;
    private final String subtype;
    private final int mileage;

    public Vehicle(String model, String registration, String subtype, int mileage)
    {
        this.model = requireNonBlank(model, "model");
        this.registration = requireNonBlank(registration, "registration");
        if (subtype == null)
        {
            this.subtype = "OTHER";
        }
        else
        {
            this.subtype = subtype;
        }

        this.mileage = requireNonNegative(mileage, "mileage");
    }

    public abstract String getCategory();

    public String getModel()
    {
        return model;
    }
    public String getRegistration()
    {
        return registration;
    }
    public String getSubtype()
    {
        return subtype;
    }
    public int getMileage()
    {
        return mileage;
    }

    @Override
    public String toString() {
        String output = "";
        output =output+"-------------------------------\n";
        output =output+"Vehicle Model   : " + model + "\n";
        output =output+"Registration    : " + registration + "\n";
        output =output+"Subtype         : " + subtype + "\n";
        output =output+"Mileage         : " + mileage + "\n";
        output =output+"-------------------------------";
        return output;
    }
}
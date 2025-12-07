import java.time.LocalDate;

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

    //Checking if registration is equal
    //Equals
    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof Vehicle))
        {
            return false;
        }
        Vehicle vehicle = (Vehicle) o;

        return registration.equalsIgnoreCase(vehicle.registration);
    }

    //Hash Code
    @Override
    public int hashCode()
    {
        return registration.toLowerCase().hashCode();
    }

    //new enhancer methods for stage 2

    //find all high mileage vehicles
    public boolean isHighMileage()
    {
        return mileage > 150_000;
    }

    //get the vehicle age based on the registration year
    public int getVehicleAge()
    {
        try
        {
            String[] parts = registration.split("-");
            int year = Integer.parseInt(parts[0]);
            int fullYear = 2000 + year;
            return LocalDate.now().getYear() - fullYear;
        }
        catch (Exception e)
        {
            return -1; //invalid registration format
        }
    }

    //check whether electric or hybrid
    public boolean isElectricOrHybrid()
    {
        String lowerM = model.toLowerCase();
        return lowerM.contains("electric")
                || lowerM.contains("hybrid")
                || lowerM.contains("ev")
                || lowerM.contains("tesla");
    }

    //assign mileage category (low,medium,low)
    public String getMileageCategory()
    {
        if (mileage < 80_000)
        {
            return "LOW";
        }
        else if (mileage < 150_000)
        {
            return "MEDIUM";
        }
        else
        {
            return "HIGH";
        }

    }


}

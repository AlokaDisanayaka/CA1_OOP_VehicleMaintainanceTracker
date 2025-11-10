import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
public final class ServiceRecord implements Comparable <ServiceRecord>{

    //fields
    private String ownerName;
    private String vehicleModel;
    private String registration;
    private VehicleCategory category;
    private VehicleSubType subtype;
    private int mileage;
    private double serviceCost;
    private boolean underWarranty;
    private LocalDate serviceDate;
    private LocalDateTime startTime;

    //constructor with validation
    public ServiceRecord(String ownerName, String vehicleModel, String registration, VehicleCategory category, VehicleSubType subtype, int mileage, double serviceCost, boolean underWarranty, LocalDate serviceDate, LocalDateTime startTime)
    {
        this.ownerName     = requireNonBlank(ownerName, "ownerName");
        this.vehicleModel  = requireNonBlank(vehicleModel, "vehicleModel");
        this.registration  = requireNonBlank(registration, "registration");
        this.category      = Objects.requireNonNull(category, "category");
        this.subtype       = Objects.requireNonNull(subtype, "subtype");
        this.mileage       = requireNonNegative(mileage, "mileage");
        this.serviceCost   = requirePositive(serviceCost, "serviceCost");
        this.underWarranty = underWarranty;
        this.serviceDate   = Objects.requireNonNull(serviceDate, "serviceDate");
        this.startTime     = Objects.requireNonNull(startTime, "startTime");

        // the date part of startTime should match the serviceDate
        if (!startTime.toLocalDate().equals(serviceDate))
        {
            throw new IllegalArgumentException("startTime date must match serviceDate");
        }

        //reject future service dates (as the date of service cannot be in the future)
         if (serviceDate.isAfter(LocalDate.now()))
        {
           throw new IllegalArgumentException("serviceDate cannot be in the future");
        }

    }

    //getters
    public String getOwnerName()
    {
        return this.ownerName;
    }
    public String getVehicleModel()
    {
        return this.vehicleModel;
    }
    public String getRegistration()
    {
        return this.registration;
    }
    public VehicleCategory getCategory()
    {
        return this.category;
    }
    public VehicleSubType getSubtype()
    {
        return this.subtype;
    }
    public int getMileage()
    {
        return this.mileage;
    }
    public double getServiceCost()
    {
        return this.serviceCost;
    }
    public boolean isUnderWarranty()
    {
        return this.underWarranty;
    }
    public LocalDate getServiceDate()
    {
        return this.serviceDate;
    }
    public LocalDateTime getStartTime()
    {
        return this.startTime;
    }
    //compareTo
    public int compareTo(ServiceRecord other)
    {
        //compare by service date
        int byDate = this.serviceDate.compareTo(other.serviceDate);
        if (byDate != 0)
        {
            return byDate;
        }

        //compare by the time of service
        int byTime = this.startTime.compareTo(other.startTime);
        if (byTime != 0)
        {
            return byTime;
        }

        //compare by registration
        return this.registration.compareToIgnoreCase(other.registration);
    }

    //to string
   public String toString()
   {
       String output = "";
       output =output+"-------------------------------\n";
       output =output+"Owner Name      : " + ownerName + "\n";
       output =output+"Vehicle Model   : " + vehicleModel + "\n";
       output =output+"Registration    : " + registration + "\n";
       output =output+"Category/Subtype: " + category + " / " + subtype + "\n";
       output =output+"Mileage         : " + mileage + " km\n";
       output =output+"Service Cost    : €" + serviceCost + "\n";
       output =output+"Under Warranty  : " + underWarranty + "\n";
       output =output+"Service Date    : " + serviceDate + "\n";
       output =output+"Start Time      : " + startTime.toLocalTime() + "\n";
       output =output+"-------------------------------";
       return output;

   }
    //validations for constructors
    private static String requireNonBlank(String value, String field)
    {
        if (value == null)
        {
            throw new IllegalArgumentException(field + " must not be null");
        }
        //The trim() method removes whitespace from both ends of a string. Note: This method does not change the original string.
        String trimmed = value.trim();
        if (trimmed.isEmpty())
        {
            throw new IllegalArgumentException(field + " must not be blank");
        }
        return trimmed;
    }

    private static int requireNonNegative(int value, String field)
    {
        if (value < 0)
        {
            throw new IllegalArgumentException(field + " must be >= 0");
        }
        return value;
    }

    private static double requirePositive(double value, String field) {
        if (value <= 0.0)
        {
            throw new IllegalArgumentException(field + " must be > 0");
        }
        return value;
    }

    // equals/hashCode will be added in Stage 2

}

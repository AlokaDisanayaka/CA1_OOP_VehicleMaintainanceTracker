import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
public final class ServiceRecord extends RecordBase implements Comparable<ServiceRecord>
{
    //fields
    private String ownerName;
    private String workOrderId;
    private String serviceType;
    private double serviceCost;
    private boolean underWarranty;
    private LocalDate serviceDate;
    private LocalDateTime startTime;
    private Vehicle vehicle;


    //constructor with validation
    public ServiceRecord(String ownerName, String workOrderId, String serviceType, double serviceCost, boolean underWarranty, LocalDate serviceDate, LocalDateTime startTime, Vehicle vehicle)
    {
        this.ownerName = requireNonBlank(ownerName, "ownerName");
        this.workOrderId = requireNonBlank(workOrderId, "workOrderId");
        this.serviceType = requireNonBlank(serviceType, "serviceType");
        this.serviceCost = requirePositive(serviceCost, "serviceCost");
        this.underWarranty = underWarranty;
        this.serviceDate = Objects.requireNonNull(serviceDate, "serviceDate");
        this.startTime = Objects.requireNonNull(startTime, "startTime");
        this.vehicle = Objects.requireNonNull(vehicle, "vehicle");

        // the date part of startTime should match the serviceDate
        if (!startTime.toLocalDate().equals(serviceDate))
        {
            throw new IllegalArgumentException("startTime date must match serviceDate");
        }

        //do not take in future service dates (as the date of service cannot be in the future)
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
    public String getWorkOrderId()
    {
        return this.workOrderId;
    }
    public String getServiceType()
    {
        return this.serviceType;
    }
    public double getServiceCost()
    {
        return this.serviceCost;
    }
    public boolean getUnderWarranty()
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
    public Vehicle getVehicle()
    {
        return this.vehicle;
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
        return this.vehicle.getRegistration().compareToIgnoreCase(other.vehicle.getRegistration());
    }

    //to string
    public String toString()
    {
        String output = "";
        output =output+"-------------------------------\n";
        output =output+"Owner Name      : " + ownerName + "\n";
        output =output+"Work Order ID   : " + workOrderId + "\n";
        output =output+"Service Type    : " + serviceType + "\n";
        output =output+"Service Cost    : " + serviceCost + "\n";
        output =output+"Under Warranty  : " + underWarranty + "\n";
        output =output+"Service Date    : " + serviceDate + "\n";
        output =output+"Start Time      : " + startTime.toLocalTime() + "\n";
        output =output+"Vehicle Info    : " + vehicle + "\n";
        output =output+"-------------------------------";
        return output;

    }

    //Records with same work order id are equal
    //Equals
    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof ServiceRecord))
        {
            return false;
        }
        ServiceRecord serviceRecord = (ServiceRecord) o;
        return workOrderId.equalsIgnoreCase(serviceRecord.workOrderId);
    }

    //Hash Code
    @Override
    public int hashCode()
    {
        return workOrderId.toLowerCase().hashCode();
    }

}

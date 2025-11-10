import java.util.Comparator;
public class Comparators {
    // 1. Sort by cost of service (lowest → highest)
    public static class ByCostAscending implements Comparator<ServiceRecord>
    {
        @Override
        public int compare(ServiceRecord r1, ServiceRecord r2)
        {
            if (r1.getServiceCost() < r2.getServiceCost())
            {
                return -1;
            }
            else if (r1.getServiceCost() > r2.getServiceCost())
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
    }

    // 2. Sort by mileage (highest → lowest)
    public static class ByMileageDescending implements Comparator<ServiceRecord>
    {
        @Override
        public int compare(ServiceRecord r1, ServiceRecord r2)
        {
            if (r1.getMileage() > r2.getMileage())
            {
                return -1;
            }
            else if (r1.getMileage() < r2.getMileage())
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
    }

    // 3. Sort by owner name (alphabetically, A–Z)
    public static class ByOwnerName implements Comparator<ServiceRecord>
    {
        @Override
        public int compare(ServiceRecord r1, ServiceRecord r2)
        {
            return r1.getOwnerName().compareToIgnoreCase(r2.getOwnerName());
        }
    }

    // 4. Sort by date ( show most recent first)
    public static class ByDateDescending implements Comparator<ServiceRecord>
    {
        @Override
        public int compare(ServiceRecord r1, ServiceRecord r2)
        {
            return r2.getServiceDate().compareTo(r1.getServiceDate());
        }
    }

    // 5. Sort by vehicle model (alphabetically, A–Z)
    public static class ByVehicleModel implements Comparator<ServiceRecord>
    {
        @Override
        public int compare(ServiceRecord r1, ServiceRecord r2)
        {
            return r1.getVehicleModel().compareToIgnoreCase(r2.getVehicleModel());
        }
    }

    // Private constructor to prevent creating objects of this class
    private Comparators() {}
}

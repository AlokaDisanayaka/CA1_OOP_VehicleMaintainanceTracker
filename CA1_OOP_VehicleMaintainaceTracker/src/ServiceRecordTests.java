import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;


public class ServiceRecordTests
{
    // Vehicles
    private final Vehicle car = new Car("Accord", "REG111", "Sedan", 15000);
    private final Vehicle jeep = new Jeep("Wrangler", "REG222", "Sport", 20000);
    private final Vehicle truck = new Truck("F-150", "REG333", "Pickup", 30000);
    private final Vehicle van = new Van("Transit", "REG444", "Cargo", 12000);

    // ServiceRecord
    private final ServiceRecord sr1 = new ServiceRecord("Alice", "W001", "Oil", 100.0, false,
                                                        LocalDate.of(2024,1,10),
                                                        LocalDateTime.of(2024,1,10,10,0), car);

    private final ServiceRecord sr2 = new ServiceRecord("Bob", "W002", "Tyres", 250.0, true,
                                                        LocalDate.of(2024,1,12),
                                                        LocalDateTime.of(2024,1,12,9,30), jeep);

    private final ServiceRecord sr3 = new ServiceRecord("Cara", "W003", "Brakes", 180.0, false,
                                                        LocalDate.of(2024,1,10),
                                                        LocalDateTime.of(2024,1,10,9,0), truck);

    private final ServiceRecord sr4 = new ServiceRecord("Dan", "W004", "Battery", 150.0, true,
                                                        LocalDate.of(2024,1,11),
                                                        LocalDateTime.of(2024,1,11,11,0), van);

    //1) Comparable ordering - Natural order
        //natural order: service date, start time, vehicle registration if date is equal to time
    @Test
    void comparable_sameDateComparesByTimeThenReg()
    {
        assertTrue(sr3.compareTo(sr1)<0);
        assertTrue(sr1.compareTo(sr3)>0);
    }

    @Test
    void comparable_differentDatesMoreRecentIsGreater()
    {
        assertTrue(sr2.compareTo(sr1)>0);
        assertTrue(sr1.compareTo(sr2)<0);
    }

    //2) Comparator ordering - Sort by cost of service (lowest -> highest)

    //3) Comparator ordering - Sort by mileage (highest -> lowest)

    //4) Comparator ordering - Sort by owner name (alphabetically, A–Z)

    //5) Comparator ordering - Sort by date (show most recent first)

    //6) Comparator ordering - Sort by vehicle model (alphabetically, A–Z)

}

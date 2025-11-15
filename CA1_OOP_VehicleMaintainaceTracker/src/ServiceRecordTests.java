import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
                                                        LocalDate.of(2025,1,10),
                                                        LocalDateTime.of(2025,1,10,10,0), car);

    private final ServiceRecord sr2 = new ServiceRecord("Bob", "W002", "Tyres", 250.0, true,
                                                        LocalDate.of(2025,1,12),
                                                        LocalDateTime.of(2025,1,12,9,30), jeep);

    private final ServiceRecord sr3 = new ServiceRecord("Cara", "W003", "Brakes", 180.0, false,
                                                        LocalDate.of(2025,1,10),
                                                        LocalDateTime.of(2025,1,10,9,0), truck);

    private final ServiceRecord sr4 = new ServiceRecord("Dan", "W004", "Battery", 150.0, true,
                                                        LocalDate.of(2025,1,11),
                                                        LocalDateTime.of(2025,1,11,11,0), van);

    //1) Comparable ordering - Natural order
    //   natural order: service date, start time, vehicle registration if date is equal to time
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

    //2) Comparator sorting
    private List<ServiceRecord> sortByCost(List<ServiceRecord> input)
    {
        List<ServiceRecord> copy = new ArrayList<>(input);
        copy.sort(new Comparators.compareByCostAscending());
        return copy;
    }

    //Sort by cost of service (lowest -> highest)
    @Test
    void comparator_costOfServiceAscending()
    {
        List<ServiceRecord> sorted = sortByCost(List.of(sr1,sr2,sr3,sr4));
        assertIterableEquals(List.of(sr1,sr4,sr3,sr2), sorted);
    }

    //Sort by mileage (highest -> lowest)
    @Test
    void comparator_mileageDescending()
    {
        List<ServiceRecord> list = new ArrayList<>(List.of(sr1,sr2,sr3,sr4));
        list.sort(new Comparators.compareByMileageDescending());
        assertEquals(sr3, list.get(0));
        assertEquals(sr4, list.get(3));
    }

    //Sort by owner name (alphabetically, A–Z)
    @Test
    void comparator_ownerName()
    {
        List<ServiceRecord> list = new ArrayList<>(List.of(sr3,sr1,sr4,sr2));
        list.sort(new Comparators.compareByOwnerName());
        assertIterableEquals(List.of(sr1,sr2,sr3,sr4), list);
    }

    //Sort by date (show most recent first)
    @Test
    void comparator_dateDescending()
    {
        List<ServiceRecord> list = new ArrayList<>(List.of(sr1,sr2,sr3,sr4));
        list.sort(new Comparators.compareByDateDescending());
        assertEquals(sr2, list.get(0));
    }

    //Sort by vehicle model (alphabetically, A–Z)
    @Test
    void comparator_vehicleModel()
    {
        List<ServiceRecord> list = new ArrayList<>(List.of(sr1,sr2,sr3,sr4));
        list.sort(new Comparators.compareByVehicleModel());
        assertIterableEquals(List.of(sr1,sr3,sr4,sr2), list);
    }

    //Sorting service cost without mutating
    @Test
    void sort_noMutationServiceCost()
    {
        List<ServiceRecord> original = new ArrayList<>(List.of(sr1,sr2,sr3,sr4));
        List<ServiceRecord> before = List.copyOf(original); //copy of original list
        List<ServiceRecord> sorted = new ArrayList<>(original); //copied list before sorting

        sorted.sort(new Comparators.compareByCostAscending()); //sorting sorted
        assertIterableEquals(before, original); //original list unchanged
        assertNotEquals(original, sorted); //sorted list different to original
    }

    //Search by Owner name prefix (names that begin with a letter)
    private List<ServiceRecord> ownerNameStartsWith(List<ServiceRecord> list, String prefix)
    {
        List<ServiceRecord> out = new ArrayList<>();
        for (int i=0; i<list.size(); i++)
        {
            ServiceRecord sr = list.get(i);
            if (sr.getOwnerName().startsWith(prefix))
            {
                out.add(sr);
            }
        }
        return out;
    }

    //Search by names beginning with C
    @Test
    void search_ownerNamePrefix()
    {
        List<ServiceRecord> out = ownerNameStartsWith(List.of(sr1,sr2,sr3,sr4),"C");
        assertIterableEquals(List.of(sr3), out); //sr3 = "Cara"
    }

    @Test
    void search_ownerNamePrefixNoMatches()
    {
        List<ServiceRecord> out = ownerNameStartsWith(List.of(sr1, sr2, sr3, sr4), "G");
        assertIterableEquals(List.of(), out); //No names begin with "G"
    }


    //Search by service record cost under 200
    @Test
    void search_costUnder200()
    {
        List<ServiceRecord> list = List.of(sr1, sr2, sr3, sr4);
        List<ServiceRecord> out = new ArrayList<>();

        for (int i=0; i<list.size(); i++)
        {
            ServiceRecord sr = list.get(i);
            if (sr.getServiceCost() < 200)
            {
                out.add(sr);
            }
        }
        assertIterableEquals(List.of(sr1, sr3, sr4), out); //sr1 = 100, sr3 = 180, sr4 =150
    }

    //Parameterised test - running the test multiple times with different values
    @ParameterizedTest @ValueSource(doubles = {100, 150, 180, 250})
    void serviceCost_parameterised(double cost)
    {
        //Each test creates new ServiceRecord
        ServiceRecord sr = new ServiceRecord("Test", "W999", "Service", cost, false,
                                            LocalDate.of(2025,1,1),
                                            LocalDateTime.of(2025,1,1,10,0), car);
        assertEquals(cost, sr.getServiceCost()); //checks that the cost is correct
    }

    //Test if an owner name is blank
    @Test
    void constructor_blankOwnerName_throws()
    {
        //empty string
        assertThrows(IllegalArgumentException.class, () ->
        {
            new ServiceRecord(" ", "W001", "Service",100, false,
                                LocalDate.of(2025, 1, 1),
                                LocalDateTime.of(2025, 1, 1, 10, 0), car);
        });

        // null owner name
        assertThrows(IllegalArgumentException.class, () ->
        {
            new ServiceRecord(null, "W002","Service", 120, false,
                                LocalDate.of(2025, 1, 2),
                                LocalDateTime.of(2025, 1, 2, 11, 0), car);
        });
    }

    //startTime date must match serviceDate
    @Test
    void constructor_startTimeDateMismatch_throws()
    {
        LocalDate serviceDate = LocalDate.of(2025, 1, 1);
        LocalDateTime startTime = LocalDateTime.of(2025, 1, 2, 10, 0); //date doesn't match

        assertThrows(IllegalArgumentException.class, () ->
        {
            new ServiceRecord("Alice", "W001", "Service", 100, false, serviceDate, startTime, car);
        });
    }

    //serviceDate cannot be in the future
    @Test
    void constructor_futureServiceDate_throws()
    {
        LocalDate futureDate = LocalDate.now().plusDays(1); //the next day
        LocalDateTime startTime = futureDate.atTime(10, 0);

        assertThrows(IllegalArgumentException.class, () ->
        {
            new ServiceRecord("Bob", "W002", "Service", 120, false, futureDate, startTime, car);
        });
    }

    //Null vehicle
    @Test
    void constructor_nullVehicle_throws()
    {
        LocalDate serviceDate = LocalDate.of(2025,1,1);
        LocalDateTime startTime = LocalDateTime.of(2025,1,1,10,0);

        assertThrows(NullPointerException.class, () ->
        {
            new ServiceRecord("Alice","W001", "Service", 100, false, serviceDate, startTime, null);
        });
    }

    //Null Service date
    @Test
    void constructor_nullServiceDate_throws()
    {
        LocalDateTime startTime = LocalDateTime.of(2025,1,1,10,0);

        assertThrows(NullPointerException.class, () ->
        {
            new ServiceRecord("Alice","W001", "Service", 100, false, null, startTime, car);
        });
    }

    //Null start time
    @Test
    void constructor_nullStartTime_throws()
    {
        LocalDate serviceDate = LocalDate.of(2025,1,1);

        assertThrows(NullPointerException.class, () ->
        {

            new ServiceRecord("Alice","W001", "Service", 100, false, serviceDate, null, car);
        });
    }


}

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ServiceRepositoryTests
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

    //Methods
    //Add a record to the list
    @Test
    void add_recordsToList()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr1);
        repository.add(sr2);
        assertEquals(2, repository.count());
    }
    //Add a null record
    @Test
    void add_nullRecord_()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(null);
        assertEquals(0, repository.count());
    }

    //Display all records




    //Count total number of records
    @Test
    void count_totalNumberOfRecords()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr2);
        repository.add(sr3);
        assertEquals(2, repository.count());
    }


    //Remove a record by given registration
    @Test
    void remove_ByRegistration()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr1);
        repository.add(sr2);

        repository.removeByRegistration("REG111");
        assertEquals(1, repository.count());
    }
    //Remove a record that doesn't exist
    @Test
    void remove_ByRegistrationNotExisting()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr1);
        repository.add(sr2);

        repository.removeByRegistration("Test123");
        assertEquals(2, repository.count());
    }

    //Find a record by registration number

    //Find all the records that contain a part of the owners name

    //Find records of vehicles of a certain make

    //Sorting
    //Sort by natural order (serviceDate -> startTime -> registration)
    @Test
    void sort_naturalOrder()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr2);
        repository.add(sr4);
        repository.add(sr1);
        repository.add(sr3);

        repository.sortNatural();
        List<ServiceRecord> expected = List.of(sr3, sr1, sr4, sr2);

        for (int i=0; i < expected.size(); i++)
        {
            assertEquals(expected.get(i), repository.getRecords().get(i));
        }
    }

    //Sort by service cost
    @Test
    void sort_serviceCostAscending()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr1);
        repository.add(sr2);
        repository.add(sr3);
        repository.add(sr4);
        repository.sortByCostAscending();

        List<ServiceRecord> expected = List.of(sr1, sr4, sr3, sr2);
        assertIterableEquals(expected, repository.getRecords());
    }

    //Sort by mileage descending
    @Test
    void sort_mileageDescending()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr1);
        repository.add(sr2);
        repository.add(sr3);
        repository.add(sr4);
        repository.sortByMileageDescending();

        List<ServiceRecord> expected = List.of(sr3, sr2, sr1, sr4);
        assertIterableEquals(expected, repository.getRecords());
    }

    //Sort by owner name (A–Z)
    @Test
    void sort_ownerName()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr3);
        repository.add(sr1);
        repository.add(sr4);
        repository.add(sr2);
        repository.sortByOwnerName();

        List<ServiceRecord> expected = List.of(sr1, sr2, sr3, sr4);
        assertIterableEquals(expected, repository.getRecords());
    }

    //Sort by vehicle model (A–Z)
    @Test
    void sort_vehicleModel()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr1);
        repository.add(sr2);
        repository.add(sr3);
        repository.add(sr4);
        repository.sortByVehicleModel();

        List<ServiceRecord> expected = List.of(sr1, sr3, sr4, sr2);
        assertIterableEquals(expected, repository.getRecords());
    }

    //Sort by service date (most recent first)
    @Test
    void sort_serviceDateDescending()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr1);
        repository.add(sr2);
        repository.add(sr3);
        repository.add(sr4);
        repository.sortByDateDescending();

        List<ServiceRecord> expected = List.of(sr2, sr4, sr1, sr3);
        assertIterableEquals(expected, repository.getRecords());
    }

    //Calculate total service cost (record is empty)
    @Test
    void calculate_totalServiceCost_returnZero()
    {
        ServiceRepository repository = new ServiceRepository();
        assertEquals(0, repository.getTotalServiceCost());
    }

    //Calculate total service cost
    @Test
    void calculate_totalServiceCost()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr1);
        repository.add(sr2);
        repository.add(sr3);
        repository.add(sr4);

        assertEquals(680, repository.getTotalServiceCost());
    }

    //Calculate average service cost (record is empty)
    @Test
    void average_serviceCost_returnZero()
    {
        ServiceRepository repository = new ServiceRepository();
        assertEquals(0, repository.getAverageServiceCost());
    }
    @Test
    void average_serviceCost()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr1);
        repository.add(sr2);
        repository.add(sr3);
        repository.add(sr4);

        assertEquals(170, repository.getAverageServiceCost());
    }

    //Clear all the records
    @Test
    void remove_allRecords()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr1);
        repository.add(sr2);
        repository.removeAll();

        assertEquals(0, repository.count());
    }

    //Parameterized test
    @ParameterizedTest @ValueSource(strings = {"REG111", "REG222", "Test123"})
    void remove_byRegistration_parameterised(String registration)
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr1);
        repository.add(sr2); //adding two records initially
        int initialCount = repository.count(); //number of records before removing (2)
        repository.removeByRegistration(registration);

        if (registration.equals("REG111") || registration.equals("REG222"))
        {
            assertEquals(initialCount-1, repository.count()); //record removed, decrease count by 1
        }
        else
        {
            assertEquals(initialCount, repository.count());
        }
    }


}

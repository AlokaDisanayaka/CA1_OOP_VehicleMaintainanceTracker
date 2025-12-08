import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Test
    void displayAllRecords()
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream(); //Store printed output
        PrintStream originalOut = System.out; //original console output
        System.setOut(new PrintStream(out)); //printing into out

        try
        {
            ServiceRepository repository = new ServiceRepository();
            repository.add(sr1);
            repository.add(sr2);
            repository.displayRecords();
            String output = out.toString(); //printed text is stored in out, puts it as a string

            assertTrue(output.contains(sr1.toString())); //checking if printed text contains the records
            assertTrue(output.contains(sr2.toString()));
        }
        finally
        {
            System.setOut(originalOut); //reset console output
        }
    }

    //Display all records (no records found)
    @Test
    void displayRecords_NoRecords()
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        try
        {
            ServiceRepository repository = new ServiceRepository();
            repository.displayRecords();
            String output = out.toString().trim(); //trim to remove \n at the end

            assertEquals("No records available in the repository", output);
        }
        finally
        {
            System.setOut(originalOut);
        }
    }

    //Count total number of records
    @Test
    void count_totalNumberOfRecords()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr2);
        repository.add(sr3);
        assertEquals(2, repository.count());
    }


    //HashMap test - Remove a record by given registration
    @Test
    void remove_ByRegistration()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr1);
        repository.add(sr2);

        repository.removeByRegistration("REG111");
        assertEquals(1, repository.count());
    }

    //HashMap test - Remove a record by given registration that doesn't exist
    @Test
    void remove_ByRegistrationNotExisting()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr1);
        repository.add(sr2);

        repository.removeByRegistration("Test123");
        assertEquals(2, repository.count());
    }

    //HashMap test - Find a record by registration number
    @Test
    void find_ByRegistration()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr1);

        assertDoesNotThrow(() -> repository.findByRegistration("REG111"));
    }

    //HashMap test - Find a record by registration number that doesn't exist
    @Test
    void find_ByRegistrationNotExisting()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr1);

        assertDoesNotThrow(() -> repository.findByRegistration("Test123"));
    }

    //Find all the records that contain a part of the owners name
    @Test
    void find_ByOwnerNamePart()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr1);
        repository.add(sr2);
        repository.add(sr3);

        assertDoesNotThrow(() -> repository.findByOwner("Al")); //matches Alice
    }

    //Find all the records that contain a part that doesn't exist of the owners name
    @Test
    void find_ByOwnerNamePartNotExisting()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr1);
        repository.add(sr2);
        repository.add(sr3);

        assertDoesNotThrow(() -> repository.findByOwner("Xyz")); //matches no one
    }

    //Find records of vehicles of a certain make
    @Test
    void find_ByMake()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr1);
        repository.add(sr2);
        repository.add(sr3);

        assertDoesNotThrow(() -> repository.findByMake("wrangler"));
    }

    //Find records of vehicles of a certain make that doesn't exist
    @Test
    void find_ByMakeNotExisting()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr1);
        repository.add(sr2);
        repository.add(sr3);

        assertDoesNotThrow(() -> repository.findByMake("Xyz"));
    }

    //Get all records
    @Test
    void get_AllRecords()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr1);
        repository.add(sr2);
        repository.add(sr3);
        ArrayList<ServiceRecord> all = repository.getAll();

        assertEquals(3, all.size());

        assertTrue(all.contains(sr1));
        assertTrue(all.contains(sr2));
        assertTrue(all.contains(sr3));

        assertNotSame(repository.getRecords(), all);
    }


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

    //HashSet test - Duplicate work order Id
    @Test
    void workOrderId_DuplicateTest()
    {
        ServiceRepository repository = new ServiceRepository();
        ServiceRecord duplicate = new ServiceRecord("Alice", "W001", "Oil", 100.0, false,
                                                LocalDate.now(), LocalDateTime.now(), car);
        repository.add(sr1);
        repository.add(duplicate);

        assertEquals(1, repository.count(), "Duplicate work order ID must be ignored");
    }

    //Find the most expensive service record
    @Test
    void get_MostExpensiveService()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr1);
        repository.add(sr2);
        repository.add(sr3);

        assertEquals(sr2, repository.getMostExpensiveService());
    }

    //Find the cheapest service record
    @Test
    void get_CheapestService_FirstRecordCheapest()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr1); //100
        repository.add(sr2); //250
        repository.add(sr3); //180

        assertEquals(sr1, repository.getCheapestService());
    }

    //Find the cheapest service record
    @Test
    void get_CheapestService_SecondRecordCheapest()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr2); //250
        repository.add(sr1); //100
        repository.add(sr3); //180

        assertEquals(sr1, repository.getCheapestService());
    }

    //Get all the records that are Not under warranty
    @Test
    void get_NotUnderWarranty()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr1);
        repository.add(sr2);
        repository.add(sr3);
        repository.add(sr4);

        ArrayList<ServiceRecord> result = repository.getNotUnderWarranty();
        assertEquals(2, result.size()); //two (sr1, sr3) that arent under warranty
        assertTrue(result.contains(sr1)); //checking the two records
        assertTrue(result.contains(sr3));
    }

    //Get all the records belonging to a certain category
    @Test
    void get_ByCategory()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr1);
        repository.add(sr2);
        repository.add(sr3);
        repository.add(sr4);

        ArrayList<ServiceRecord> categorised = repository.getByCategory("CAR");
        assertEquals(1, categorised.size());
        assertEquals(sr1, categorised.get(0));
    }

    //Get the records of a certain service type
    @Test
    void get_ByServiceType()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr1);
        repository.add(sr2);
        repository.add(sr3);
        repository.add(sr4);

        ArrayList<ServiceRecord> list = repository.getByServiceType("Oil");
        assertEquals(1, list.size());
        assertEquals(sr1, list.get(0));
    }

    //Get all the records of a given vehicle subtype
    @Test
    void get_BySubtype()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr1);
        repository.add(sr2);
        repository.add(sr3);
        repository.add(sr4);

        ArrayList<ServiceRecord> list = repository.getBySubtype("Pickup");
        assertEquals(1, list.size());
        assertEquals(sr3, list.get(0));
    }

    //Return the count of the services which are under warranty
    @Test
    void count_WarrantyServices()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr1);
        repository.add(sr2);
        repository.add(sr3);
        repository.add(sr4);

        int count = repository.countWarrantyServices();
        assertEquals(2, count);
    }

    //Find the services within a given date range
    @Test
    void get_ByDateRange()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr1);
        repository.add(sr2);
        repository.add(sr3);
        repository.add(sr4);

        ArrayList<ServiceRecord> list = repository.getByDateRange(LocalDate.of(2025,1,10),LocalDate.of(2025,1,11));
        assertEquals(3, list.size());
        assertTrue(list.contains(sr1));
        assertTrue(list.contains(sr3));
        assertTrue(list.contains(sr4));
    }

    //Return the average cost of service by a vehicle category
    @Test
    void average_CostByCategory()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr1);
        repository.add(sr2);
        repository.add(sr3);
        repository.add(sr4);

        assertEquals(100.0, repository.averageCostByCategory("CAR"));
    }

    //Return the average cost of service by a vehicle category
    @Test
    void average_CostByCategory_NoMatch()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr1);
        repository.add(sr2);
        repository.add(sr3);
        repository.add(sr4);

        assertEquals(0, repository.averageCostByCategory("BUS"));
    }

    //Get most frequently serviced vehicle model
    @Test
    void get_MostServicedModel()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr1);
        repository.add(sr2);
        repository.add(sr3);
        repository.add(sr4);

        assertEquals("Accord", repository.getMostServicedModel()); //every model is used once so the first model is given
    }

    //Get the services done before today
    @Test
    void get_PastServices()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr1);
        repository.add(sr2);

        ArrayList<ServiceRecord> list = repository.getPastServices();
        assertEquals(2, list.size());
        assertTrue(list.contains(sr1));
        assertTrue(list.contains(sr2));
    }

    //Get total service cost by owner
    @Test
    void totalCostByOwner_returnsCorrectTotal()
    {
        ServiceRepository repository = new ServiceRepository();
        repository.add(sr1);
        repository.add(sr2);
        repository.add(sr3);

        assertEquals(100.0, repository.totalCostByOwner("Alice"));
    }
}

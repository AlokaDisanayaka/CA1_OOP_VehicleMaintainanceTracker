import java.time.LocalDate;
import java.util.*;

public class ServiceRepository
{
    private final ArrayList <ServiceRecord> records = new ArrayList<>();

    //HashSet
    //Unique workOrderId's - Duplicate handling
    private final HashSet<String> workOrderId_HashSet = new HashSet<>();

    //HashMap
    //Registration - Fast lookup
    private final HashMap<String, List<ServiceRecord>> registration_HashMap = new HashMap<>();//One record per registration number
    //Owner name service records list - Fast grouping
    private final HashMap<String, List<ServiceRecord>> ownerName_HashMap = new HashMap<>(); //Group records by owner names if an owner has multiple vehicles
    //vehicle model's list of records - Fast grouping
    private final HashMap<String, List<ServiceRecord>> model_HashMap = new HashMap<>(); //Group vehicles by model if multiple vehicles have same model

    //METHODS
    //Add a record to the list
    public void add (ServiceRecord record)
    {
        if (record == null)
        {
            return;
        }

        //Prevent duplicate work Order Ids
        if (workOrderId_HashSet.contains(record.getWorkOrderId()))
        {
            System.out.println("Duplicate work order ID skipped: " + record.getWorkOrderId());
            return;
        }

        records.add(record);
        workOrderId_HashSet.add(record.getWorkOrderId()); //track work order to avoid duplicates
        String regKey = record.getVehicle().getRegistration().toLowerCase();

        registration_HashMap
                .computeIfAbsent(regKey, k -> new ArrayList<>()) //computeIfAbsent creates a new list if the key doesnt exist. if key exists returns existing list.
                .add(record); //then add record

        ownerName_HashMap.computeIfAbsent(record.getOwnerName().toLowerCase(), k -> new ArrayList<>()).add(record); //If owner name doesn't exist then one is created and adds record to list
        model_HashMap.computeIfAbsent(record.getVehicle().getModel().toLowerCase(), k -> new ArrayList<>()).add(record);
    }

    //Display all the records
    public void displayRecords()
    {
        if (!records.isEmpty())
        {
            for (int i =0;i<records.size();i++)
            {
                System.out.println(this.records.get(i));
            }

        }
        else
        {
            System.out.println("No records available in the repository");

        }
    }

    // count total number of records
    public int count()
    {
        return records.size();
    }

    //remove a record by given registration
    public void removeByRegistration(String registration)
    {
        String key = registration.toLowerCase();
        List<ServiceRecord> list = registration_HashMap.remove(key);

        if (list == null || list.isEmpty()) {
            System.out.println("No records found: " + registration);
            return;
        }

        // remove each record from other structures
        for (ServiceRecord record : list) {

            records.remove(record);
            workOrderId_HashSet.remove(record.getWorkOrderId());

            // Remove from ownerName_HashMap
            ownerName_HashMap.get(record.getOwnerName().toLowerCase()).remove(record);
            // Remove from model_HashMap
            model_HashMap.get(record.getVehicle().getModel().toLowerCase()).remove(record);
        }

        System.out.println("Removed ALL records for registration: " + registration);

    }


    //find a record by registration number
    public void findByRegistration (String registration)
    {
        List<ServiceRecord> list = registration_HashMap.get(registration.toLowerCase());

        if (list == null || list.isEmpty())
        {
            System.out.println("No records found for registration: " + registration);
            return;
        }

        for (int i = 0; i < list.size(); i++)
        {
            System.out.println(list.get(i));
        }
    }

    //find all the records that contain a part of the owners name
    public void findByOwner (String namePart)
    {
        boolean name = false;
        for (int i =0; i<records.size();i++)
        {
            if (this.records.get(i).getOwnerName().toLowerCase().contains(namePart.toLowerCase()))
            {
                name=true;
                System.out.println(this.records.get(i));
            }

        }
        if(!name)
        {
            System.out.println("NO records with "+namePart+" is found");
        }
    }

    //find records of vehicles of a certain make
    public void findByMake (String make)
    {
        boolean makeFound = false;
        for (int i =0; i< records.size();i++)
        {
            if (this.records.get(i).getVehicle().getModel().toLowerCase().contains(make.toLowerCase()))
            {
                makeFound = true;
                System.out.println(this.records.get(i));
            }
        }
        if (!makeFound)
        {
            System.out.println("No records found with model containing: "+make);
        }
    }

    //sorting
    //Added getter
    public List<ServiceRecord> getRecords()
    {
        return new ArrayList<>(records);
    }

    //get all the records
    public ArrayList <ServiceRecord> getAll()
    {
        ArrayList <ServiceRecord> allRecords=new ArrayList<>();
        for (int i=0;i<this.records.size();i++)
        {
            allRecords.add(this.records.get(i));

        }

        return allRecords;
    }

    // Sort by natural order (serviceDate => startTime => registration)
    public void sortNatural()
    {
        Collections.sort(records);
    }

    // Sort by service cost
    public void sortByCostAscending()
    {
        records.sort(new Comparators.compareByCostAscending());
    }

    // Sort by mileage descending
    public void sortByMileageDescending()
    {
        records.sort(new Comparators.compareByMileageDescending());
    }

    // Sort by owner name (A–Z)
    public void sortByOwnerName()
    {
        records.sort(new Comparators.compareByOwnerName());
    }

    // Sort by vehicle model (A–Z)
    public void sortByVehicleModel()
    {
        records.sort(new Comparators.compareByVehicleModel());
    }

    // Sort by service date (most recent first)
    public void sortByDateDescending()
    {
        records.sort(new Comparators.compareByDateDescending());
    }

    // Calculate total service cost
    public double getTotalServiceCost()
    {
        if (records.isEmpty())
        {
            return 0.0;
        }
        double total = 0;
        for (int i =0; i< records.size();i++)
        {
            total =total+this.records.get(i).getServiceCost();
        }
        return total;
    }

    // Calculate average service cost
    public double getAverageServiceCost()
    {
        if (records.isEmpty())
        {
            return 0.0;
        }

        double total = 0;
        for (int i =0; i< records.size();i++)
        {
            total =total+this.records.get(i).getServiceCost();
        }
        return total / records.size();
    }

    //clear all the records
    public void removeAll()
    {
        this.records.clear();
        this.registration_HashMap.clear();
        this.ownerName_HashMap.clear();
        this.model_HashMap.clear();
        this.workOrderId_HashSet.clear();
    }

    // find the most expensive service record
    public ServiceRecord getMostExpensiveService()
    {
        ServiceRecord max= this.records.get(0);
        for (int i=0;i<records.size();i++)
        {
            if (this.records.get(i).getServiceCost()>max.getServiceCost())
            {
                max=this.records.get(i);
            }
        }
        return max;

    }

    //find the cheapest service record
    public ServiceRecord getCheapestService()
    {
        ServiceRecord min= this.records.get(0);
        for (int i=0;i<records.size();i++)
        {
            if (this.records.get(i).getServiceCost()<min.getServiceCost())
            {
                min=this.records.get(i);
            }
        }
        return min;

    }

    //get all the records that are Not under wararnty
    public ArrayList <ServiceRecord> getNotUnderWarranty()
    {
        ArrayList<ServiceRecord> notUnderWarranty = new ArrayList<>();
        for(int i =0;i<records.size();i++)
        {
            if (!this.records.get(i).getUnderWarranty())
            {
                notUnderWarranty.add(this.records.get(i));
            }
        }
        return notUnderWarranty;
    }

    //get all the records belonging to a certain category
    public ArrayList <ServiceRecord> getByCategory(String category)
    {
        ArrayList<ServiceRecord> categorized= new ArrayList<>();
        for(int i =0;i<records.size();i++)
        {
            if (this.records.get(i).getVehicle().getCategory().equalsIgnoreCase(category))
            {
                categorized.add(this.records.get(i));
            }
        }
        return categorized;
    }

    //get the records of a certain service type
    public ArrayList<ServiceRecord> getByServiceType(String type)
    {
        ArrayList<ServiceRecord> list = new ArrayList<>();

        for (int i = 0; i < records.size(); i++)
        {

            if (this.records.get(i).getServiceType().equalsIgnoreCase(type))
            {
                list.add(this.records.get(i));
            }
        }

        return list;
    }

    //get all the records of a given vehicle subtype
    public ArrayList<ServiceRecord> getBySubtype(String subtype)
    {
        ArrayList<ServiceRecord> list = new ArrayList<>();

        for (int i = 0; i < records.size(); i++)
        {
            if (this.records.get(i).getVehicle().getSubtype().equalsIgnoreCase(subtype))
            {
                list.add(this.records.get(i));
            }
        }

        return list;
    }

    //return the count of the services which are under warranty
    public int countWarrantyServices()
    {
        int count = 0;

        for (int i = 0; i < records.size(); i++)
        {
            if (records.get(i).getUnderWarranty())
            {
                count++;
            }
        }

        return count;
    }

    //find the services within a given date range
    public ArrayList<ServiceRecord> getByDateRange(LocalDate start, LocalDate end)
    {
        ArrayList<ServiceRecord> list = new ArrayList<>();

        for (int i = 0; i < records.size(); i++)
        {

            if (!this.records.get(i).getServiceDate().isBefore(start) && !this.records.get(i).getServiceDate().isAfter(end))
            {
                list.add(this.records.get(i));
            }
        }

        return list;
    }

    // return the average cost of service by a vehicle category
    public double averageCostByCategory(String category)
    {
        double total = 0;
        int count = 0;

        for (int i = 0; i < records.size(); i++)
        {

            if (this.records.get(i).getVehicle().getCategory().equalsIgnoreCase(category))
            {
                total =total+ this.records.get(i).getServiceCost();
                count++;
            }
        }

        if (count == 0)
        {
            return 0;
        }

        return total / count;
    }

    //get most frequently serviced vehicle model
    public String getMostServicedModel()
    {
        String mostModel = "";
        int highestCount = 0;

        for (int i = 0; i < records.size(); i++)
        {
            String model = records.get(i).getVehicle().getModel();
            int count = 0;

            for (int j = 0; j < records.size(); j++)
            {
                if (records.get(j).getVehicle().getModel().equalsIgnoreCase(model))
                {
                    count++;
                }
            }

            if (count > highestCount)
            {
                highestCount = count;
                mostModel = model;
            }
        }

        return mostModel;
    }

    //get the services done before today (todays date)
    public ArrayList<ServiceRecord> getPastServices()
    {
        ArrayList<ServiceRecord> list = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (int i = 0; i < records.size(); i++)
        {
            if (records.get(i).getServiceDate().isBefore(today))
            {
                list.add(records.get(i));
            }
        }

        return list;
    }

    //get total service cost by owner
    public double totalCostByOwner(String owner)
    {
        double total = 0;

        for (int i = 0; i < records.size(); i++)
        {
            if (this.records.get(i).getOwnerName().equalsIgnoreCase(owner))
            {
                total = total+ this.records.get(i).getServiceCost();
            }
        }

        return total;
    }

}

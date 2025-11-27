import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServiceRepository {
    private final ArrayList <ServiceRecord> records= new ArrayList<>();

    //METHODS
    //Add a record to thr list
    public void add (ServiceRecord record)
    {
        if (record != null)
        {
            this.records.add(record);
        }
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
            System.out.println("No records available in the repositary");

        }
    }
    //getter for testing
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
    // count total number of records
    public int count()
    {
        return records.size();
    }
    //remove a record by given registration
    public void removeByRegistration(String registration)
    {
        boolean registrartionFound=false;
        for (int i =0;i<records.size();i++)
        {
            if (this.records.get(i).getVehicle().getRegistration().equalsIgnoreCase(registration))
            {
                registrartionFound=true;
                records.remove(i);
                System.out.println("Removed record:"+registration);
            }
        }

        if (registrartionFound)
        {
            System.out.println("record NOT found: "+registration);
        }

    }
    //find a record by registration number
    public void findByRegistration (String registration)
    {
        boolean found = false;
        for (int i =0;i<records.size();i++)
        {
            if (this.records.get(i).getVehicle().getRegistration().equalsIgnoreCase(registration))
            {
                found=true;
                System.out.println(this.records.get(i));
            }

        }
        if (found)
        {
            System.out.println("No records matches the registration :"+registration);
        }
    }
    //find all the records that contain a part of the owners name
    public void findByOwner (String namePart)
    {
        boolean name=false;
        for (int i =0; i<records.size();i++)
        {
            if (this.records.get(i).getOwnerName().toLowerCase().contains(namePart.toLowerCase()))
            {
                name=true;
                System.out.println(this.records.get(i));
            }

        }
        if(name)
        {
            System.out.println("NO records with "+namePart+" is found");
        }

    }
    //find records of vehicles of a certain make
    public void findBymake (String make)
    {
        boolean makeFound=false;
        for (int i =0; i< records.size();i++)
        {
            if (this.records.get(i).getVehicle().getModel().toLowerCase().contains(make.toLowerCase()))
            {
                System.out.println(this.records.get(i));
            }

        }


    }
    //sortings
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
    public ArrayList <ServiceRecord> getUnderWarranty()
    {
        ArrayList<ServiceRecord> underWarranty = new ArrayList<>();
        for(int i =0;i<records.size();i++)
        {
            if (!this.records.get(i).getUnderWarranty())
            {
                underWarranty.add(this.records.get(i));
            }
        }
        return underWarranty;
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

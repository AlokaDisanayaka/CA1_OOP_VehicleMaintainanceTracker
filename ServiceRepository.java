import java.util.ArrayList;
import java.util.Collections;

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


}

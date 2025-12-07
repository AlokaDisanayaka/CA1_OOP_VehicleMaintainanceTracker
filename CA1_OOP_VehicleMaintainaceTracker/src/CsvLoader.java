import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.StringTokenizer;

public class CsvLoader {

    private final String filePath;
    private final ServiceRepository repository;

    public CsvLoader(String filePath, ServiceRepository repository)
    {
        this.filePath = filePath;
        this.repository = repository;
    }

    public void loadSampleData(int maxRows) {
        int invalidCount = 0;
        int validCount = 0;
        int totalCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Read up to 10 lines
            while ((line = br.readLine()) != null && totalCount < maxRows) {
                totalCount++;
                if (line.trim().isEmpty())
                {
                    continue; // skip blank lines
                }

                try {
                    // Split line using StringTokenizer
                    StringTokenizer st = new StringTokenizer(line, ",");

                    String ownerName    = st.nextToken().trim();
                    String workOrderId  = st.nextToken().trim();
                    String serviceType  = st.nextToken().trim();
                    double serviceCost  = Double.parseDouble(st.nextToken().trim());
                    boolean underWarranty = Boolean.parseBoolean(st.nextToken().trim());
                    LocalDate serviceDate = LocalDate.parse(st.nextToken().trim());
                    LocalDateTime startTime= LocalDateTime.parse(st.nextToken().trim());
                    String category     = st.nextToken().trim().toUpperCase();
                    String model        = st.nextToken().trim();
                    String registration = st.nextToken().trim();
                    String subtype      = st.nextToken().trim();
                    int mileage         = Integer.parseInt(st.nextToken().trim());

                    // Create vehicle based on category
                    Vehicle vehicle;
                    if (category.equals("CAR"))
                    {
                        vehicle = new Car(model, registration, subtype, mileage);
                    }
                    else if (category.equals("JEEP"))
                    {
                        vehicle = new Jeep(model, registration, subtype, mileage);
                    }
                    else if (category.equals("TRUCK"))
                    {
                        vehicle = new Truck(model, registration, subtype, mileage);
                    }
                    else if (category.equals("VAN"))
                    {
                        vehicle = new Van(model, registration, subtype, mileage);
                    }
                    else
                    {
                        System.out.println("Unknown category: " + category + " (skipping line)");
                        continue;
                    }

                    ServiceRecord record = new ServiceRecord(ownerName, workOrderId, serviceType, serviceCost, underWarranty, serviceDate, startTime, vehicle);
                    repository.add(record);
                    validCount++;

                }
                catch (Exception e)
                {
                    invalidCount++;
                    System.out.println("Invalid data on line " + totalCount + ": " + e.getMessage());
                }
            }

            System.out.println("\nCSV load complete!");
            System.out.println("Total lines read: " + totalCount);
            System.out.println("Valid records added: " + validCount);
            System.out.println("Invalid records  : " + invalidCount);
            System.out.println("\n--- Sample Listing ---");
            for (int i = 0; i < repository.getAll().size(); i++)
            {
                ServiceRecord r = repository.getAll().get(i);
                System.out.println(r);
            }

        }
        catch (IOException e)
        {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}

public class MainApp {
    public static void main (String [] args )
    {
        ServiceRepository repo = new ServiceRepository();

        CsvLoader loader = new CsvLoader("sample_10.csv", repo);

        loader.loadSampleData();

        System.out.println("\n--- Printing Records ---");
        for (int i=0;i<repo.getAll().size();i++)
        {
            System.out.println(repo.getAll().get(i));
        }

    }
}

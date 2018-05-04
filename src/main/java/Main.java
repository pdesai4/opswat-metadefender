import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by Priyanka Desai
 */
public class Main {

    public static void main(String[] args) {
        // 0. Read the API key value
        String apiKey = readApiKey();

        if (args.length < 1) {
            System.err.println("Invalid number of arguments");
            System.exit(1);
        }

        // 1. Calculate the hash of the given file
        String filePath = args[0];
        File inputFile = new File(filePath);
        if (!inputFile.exists() || !inputFile.isFile()) {
            System.err.println("Invalid file path. Enter file path in the format \"/path/to/file\" ");
            System.exit(1);
        }
        String fileHash = Util.getFileHash(inputFile);

        NetworkManager networkManager = new NetworkManagerImpl(apiKey);

        // 2. Perform a hash lookup against metadefender.opswat.com and see if their are previously cached results for the file
        ScanHashResult lookupResult = networkManager.performFileHashLookup(fileHash);

        // 3. If results found then skip to 6
        if (lookupResult != null) {
            lookupResult.printInFormat();
        } else {
            // 4. If results not found then upload the file, receive a data_id
            boolean scanComplete;
            ScanResult scanResult = networkManager.scanFile(inputFile);

            // 5. Repeatedly pull on the data_id to retrieve results
            int maxWaitTimeInSeconds = 10, elapsedWaitTime = 0;

            // Get progress percentage status
            do {
                lookupResult = networkManager.retrieveScanProgress(scanResult.data_id);
                scanComplete = (lookupResult.scan_results.progress_percentage == 100);
                try {
                    Thread.sleep(1000);
                    elapsedWaitTime++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (!scanComplete && elapsedWaitTime < maxWaitTimeInSeconds);

            // 6. Display results in format below
            if (scanComplete) {
                lookupResult.printInFormat();
            } else {
                System.err.println("Scan timeout");
            }
        }
    }

    private static String readApiKey() {
        String apiKey = "";
        try {
            FileReader fileReader = new FileReader("api-key.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            apiKey = bufferedReader.readLine();
            bufferedReader.close();
            fileReader.close();
        } catch (Exception e) {
            System.err.println("Error reading api-key.txt");
            System.err.println("Make sure that you have api-key.txt file in root directory " +
                    "with your api key as its content without any whitespaces");
            e.printStackTrace();
            System.exit(1);
        }
        return apiKey;
    }
}

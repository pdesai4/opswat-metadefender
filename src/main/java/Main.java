import java.io.File;

/**
 * Created by Priyanka Desai
 */
public class Main {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Invalid number of arguments");
            System.exit(1);
        }

        // 1. Calculate the hash of the given samplefile.txt
        String filePath = args[0];
        File fileIn = new File(filePath);
        if (!fileIn.exists() || !fileIn.isFile()) {
            System.err.println("Invalid file path. Enter file path in the format \"/path/to/file\" ");
            System.exit(1);
        }
        String fileHash = Util.getFileHash(fileIn);

        // 2. Perform a hash lookup against metadefender.opswat.com and see if their are previously cached results for the file
        NetworkManager networkManager = new NetworkManagerImpl();
        ScanHashResult lookupResult = networkManager.performFileHashLookup(fileHash);
        System.out.println(lookupResult);


        // 3. If results found then skip to 6

        // 4. If results not found then upload the file, receive a data_id
        if(lookupResult == null) {
            // Scanning a file by file upload
            try {
                ScanResult scanResult = networkManager.scanFile(fileIn);
                System.out.println(scanResult);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 5. Repeatedly pull on the data_id to retrieve results

        // 6. Display results in format below
        if (lookupResult != null) {
            lookupResult.printInFormat();
        }
    }
    
}

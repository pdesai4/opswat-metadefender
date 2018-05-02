
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
        String filePath = args[0];
        File fileIn = new File(filePath);
        if (!fileIn.exists() || !fileIn.isFile()) {
            System.err.println("Invalid file path. Enter file path in the format \"/path/to/file\" ");
            System.exit(1);
        }
        String fileHash = Util.getFileHash(fileIn);
        System.out.println(fileHash);
        try {
            NetworkManager networkManager = new NetworkManagerImpl();
            ScanResult scanResult = networkManager.scanFile(fileIn);
            System.out.println(scanResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

import java.io.File;

/**
 * Created by Priyanka Desai
 */
public interface NetworkManager {
    ScanResult scanFile(File fileIn);
    ScanHashResult performFileHashLookup(String fileHash);
}

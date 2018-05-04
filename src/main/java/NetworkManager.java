import java.io.File;

/**
 * Created by Priyanka Desai
 */
public interface NetworkManager {

    ScanHashResult performFileHashLookup(String fileHash);

    ScanResult scanFile(File fileIn);

    ScanHashResult retrieveScanProgress(String data_id);
}

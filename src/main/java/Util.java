import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Priyanka Desai
 */
class Util {

    private static final String HASH_ALGORITHM = "SHA-256";
    private static Moshi moshi = new Moshi.Builder().build();

    /**
     * Calculate SHA-256 of the given file
     *
     * @param fileIn Input file
     * @return SHA-256 of the given file
     */
    static String getFileHash(File fileIn) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
            return getFileChecksum(messageDigest, fileIn);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Invalid hash algorithm: " + HASH_ALGORITHM);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Generate checksum hash of the given file
     * // Referred from https://howtodoinjava.com/core-java/io/how-to-generate-sha-or-md5-file-checksum-hash-in-java/
     *
     * @param digest {@link MessageDigest} object
     * @param file   Input file whose SHA-256 is to be calculated
     * @return SHA-256 of the input file
     */
    private static String getFileChecksum(MessageDigest digest, File file) {
        // Get file input stream for reading the file content
        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
            // Create byte array to read data in chunks
            byte[] byteArray = new byte[1024];
            int bytesCount;

            // Read file data and update in message digest
            while ((bytesCount = fis.read(byteArray)) != -1) {
                digest.update(byteArray, 0, bytesCount);
            }

            // Close the stream; We don't need it now.
            fis.close();

            // Get the hash's bytes
            byte[] bytes = digest.digest();

            // This bytes[] has bytes in decimal format;
            // Convert it to hexadecimal format
            StringBuilder stringBuilder = new StringBuilder();
            for (byte aByte : bytes) {
                stringBuilder.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }

            // Return complete hash
            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            System.err.println("Invalid file path");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error reading file");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Convert the json response string to {@link ScanResult} object
     *
     * @param jsonResponse Json response in {@link String} format
     * @return {@link ScanResult} object
     */
    static ScanResult jsonToScanResult(String jsonResponse) {
        JsonAdapter<ScanResult> scanResultJsonAdapter = moshi.adapter(ScanResult.class);
        try {
            ScanResult scanResult = scanResultJsonAdapter.fromJson(jsonResponse);
            System.out.println(scanResult);
            return scanResult;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Convert the json response to {@link ScanHashResult} object
     *
     * @param jsonResponse Json response in {@link String} format
     * @return {@link ScanHashResult} object
     */
    static ScanHashResult scanHashResult(String jsonResponse) {
        JsonAdapter<ScanHashResult> scanHashResultJsonAdapter = moshi.adapter(ScanHashResult.class);
        try {
            return scanHashResultJsonAdapter.fromJson(jsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Convert the json response to {@link ScanHashResult} object
     *
     * @param jsonResponse Json response in {@link String} format
     * @return {@link ScanHashResult} object
     */
    static ScanHashResult retrieveScanProgress(String jsonResponse) {
        JsonAdapter<ScanHashResult> scanProgressJsonAdapter = moshi.adapter(ScanHashResult.class);
        try {
            return scanProgressJsonAdapter.fromJson(jsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

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

    // Referred from https://howtodoinjava.com/core-java/io/how-to-generate-sha-or-md5-file-checksum-hash-in-java/
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
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }

            // Return complete hash
            return sb.toString();
        } catch (FileNotFoundException e) {
            System.err.println("Invalid file path");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error reading file");
            e.printStackTrace();
        }

        return null;
    }
}

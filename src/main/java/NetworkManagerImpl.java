import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.File;
import java.io.IOException;

/**
 * Created by Priyanka Desai
 */
public class NetworkManagerImpl implements NetworkManager {

    private static final String BASE_URL = "https://api.metadefender.com/v2";
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/octet-stream");
    private String apiKey = "apiKey";
    private OkHttpClient okHttpClient;

    NetworkManagerImpl(String apiKeyIn) {
        okHttpClient = new OkHttpClient();
        apiKey = apiKeyIn;
    }

    /**
     * Retrieving scan reports using a data hash
     *
     * @param fileHash SHA-256 of the file
     * @return {@link ScanHashResult} object
     */
    public ScanHashResult performFileHashLookup(String fileHash) {
        Request request = new Request.Builder()
                .url(BASE_URL + "/hash/" + fileHash)
                .header("apikey", apiKey)
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            if (response.isSuccessful() && response.code() == 200) {
                ResponseBody body = response.body();
                if (body != null) {
                    ScanHashResult scanHashResult = Util.scanHashResult(body.string());
                    // If it's a failed scan request, file id will be null
                    if (scanHashResult != null && scanHashResult.file_id != null) {
                        return scanHashResult;
                    }
                }
            } else {
                System.err.println("Request " + request.url() + " failed with status code: " + response.code());
            }

        } catch (IOException e) {
            System.err.println("Request " + request.url() + " failed with with IO error");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Scanning a file by file upload
     *
     * @param fileIn File to be scanned
     * @return {@link ScanResult} object
     */
    public ScanResult scanFile(File fileIn) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(fileIn.getName(), fileIn.getName(), RequestBody.create(MEDIA_TYPE, fileIn))
                .build();

        Request request = new Request.Builder()
                .url(BASE_URL + "/file")
                .header("apikey", apiKey)
                .header("filename", fileIn.getName())
                .post(requestBody)
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.err.println("Request " + request.url() + " failed with status code: " + response.code());
                return null;
            }

            ResponseBody body = response.body();
            if (body != null) {
                return Util.jsonToScanResult(body.string());
            }
        } catch (IOException e) {
            System.err.println("Request " + request.url() + " failed with with IO error");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Retrieve scan reports using data_id
     *
     * @param data_id The dataId received on upload
     * @return {@link ScanHashResult} object
     */
    public ScanHashResult retrieveScanProgress(String data_id) {
        Request request = new Request.Builder()
                .url(BASE_URL + "/file/" + data_id)
                .header("apikey", apiKey)
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            if (response.isSuccessful() && response.code() == 200) {
                ResponseBody body = response.body();
                if (body != null) {
                    return Util.retrieveScanProgress(body.string());
                }
            } else {
                System.err.println("Request " + request.url() + " failed with status code: " + response.code());
            }

        } catch (IOException e) {
            System.err.println("Request " + request.url() + " failed with with IO error");
            e.printStackTrace();
        }

        return null;
    }
}

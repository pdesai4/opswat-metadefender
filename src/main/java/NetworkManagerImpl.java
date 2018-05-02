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
    private static final String API_KEY = "API_KEY";
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/octet-stream");

    private OkHttpClient okHttpClient;

    NetworkManagerImpl() {
        okHttpClient = new OkHttpClient();
    }

    public ScanResult scanFile(File fileIn) {

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(fileIn.getName(), fileIn.getName(), RequestBody.create(MEDIA_TYPE, fileIn))
                .build();

        Request request = new Request.Builder()
                .url(BASE_URL + "/file")
                .header("apikey", API_KEY)
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

}

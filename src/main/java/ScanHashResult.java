import java.util.Map;

/**
 * Created by Priyanka Desai
 */
public class ScanHashResult {
    String file_id;
    String data_id;
    boolean archived;
    ProcessInfo process_info;
    ScanResults scan_results;
    FileInfo file_info;
    int top_threat;
    int share_file;
    int rest_version;


    @Override
    public String toString() {
        return "ScanHashResult{" +
                "file_id='" + file_id + '\'' +
                ", data_id='" + data_id + '\'' +
                ", archived=" + archived +
                ", process_info=" + process_info +
                ", scan_results=" + scan_results +
                ", file_info=" + file_info +
                ", top_threat=" + top_threat +
                ", share_file=" + share_file +
                ", rest_version=" + rest_version +
                '}';
    }

    void printInFormat() {
        System.out.println("filename: " + file_info.display_name);
        for (String key : scan_results.scan_details.keySet()) {
            System.out.println("engine: " + key);
            System.out.println(scan_results.scan_details.get(key).threat_found);
            System.out.println(scan_results.scan_details.get(key).scan_result_i);
            System.out.println(scan_results.scan_details.get(key).def_time + "\n");
        }
    }

    static class ProcessInfo {
        String user_agent;
        String result;
        int progress_percentage;
        String profile;
        boolean file_type_skipped_scan;
        String blocked_reason;

        @Override
        public String toString() {
            return "ProcessInfo{" +
                    "user_agent='" + user_agent + '\'' +
                    ", result='" + result + '\'' +
                    ", progress_percentage=" + progress_percentage +
                    ", profile='" + profile + '\'' +
                    ", file_type_skipped_scan=" + file_type_skipped_scan +
                    ", blocked_reason='" + blocked_reason + '\'' +
                    '}';
        }
    }

    static class ScanResults {
        Map<String, ScanDetailValue> scan_details;
        boolean rescan_available;
        String data_id;
        int scan_all_result_i;
        String start_time;
        long total_time;
        int total_avs;
        int total_detected_avs;
        int progress_percentage;
        int in_queue;
        String scan_all_result_a;

        @Override
        public String toString() {
            return "ScanResults{" +
                    "scan_details=" + scan_details +
                    ", rescan_available=" + rescan_available +
                    ", data_id='" + data_id + '\'' +
                    ", scan_all_result_i=" + scan_all_result_i +
                    ", start_time='" + start_time + '\'' +
                    ", total_time=" + total_time +
                    ", total_avs=" + total_avs +
                    ", total_detected_avs=" + total_detected_avs +
                    ", progress_percentage=" + progress_percentage +
                    ", in_queue=" + in_queue +
                    ", scan_all_result_a='" + scan_all_result_a + '\'' +
                    '}';
        }
    }

    static class ScanDetailValue {
        long wait_time;
        int scan_time;
        String threat_found;
        int scan_result_i;
        String def_time;

        @Override
        public String toString() {
            return "ScanDetailValue{" +
                    "wait_time=" + wait_time +
                    ", scan_time=" + scan_time +
                    ", threat_found='" + threat_found + '\'' +
                    ", scan_result_i=" + scan_result_i +
                    ", def_time='" + def_time + '\'' +
                    '}';
        }
    }

    static class FileInfo {
        int file_size;
        String upload_timestamp;
        String md5;
        String sha1;
        String sha256;
        String file_type_category;
        String file_type_description;
        String file_type_extension;
        String display_name;

        @Override
        public String toString() {
            return "FileInfo{" +
                    "file_size=" + file_size +
                    ", upload_timestamp='" + upload_timestamp + '\'' +
                    ", md5='" + md5 + '\'' +
                    ", sha1='" + sha1 + '\'' +
                    ", sha256='" + sha256 + '\'' +
                    ", file_type_category='" + file_type_category + '\'' +
                    ", file_type_description='" + file_type_description + '\'' +
                    ", file_type_extension='" + file_type_extension + '\'' +
                    ", display_name='" + display_name + '\'' +
                    '}';
        }
    }
}

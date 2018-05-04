/**
 * Created by Priyanka Desai
 */
public class ScanResult {
    String data_id;
    String status;
    int in_queue;
    String queue_priority;
    String rest_ip;

    @Override
    public String toString() {
        return "ScanResult{" +
                "data_id='" + data_id + '\'' +
                ", status='" + status + '\'' +
                ", in_queue=" + in_queue +
                ", queue_priority='" + queue_priority + '\'' +
                ", rest_ip='" + rest_ip + '\'' +
                '}';
    }
}

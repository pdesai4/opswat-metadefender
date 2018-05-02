/**
 * Created by Priyanka Desai
 */
public class ScanResult {
    private final String data_id;
    private final String status;
    private int in_queue;
    private String queue_priority;
    private String rest_ip;

    public ScanResult() {
        data_id = "";
        status = "";
        in_queue = -1;
        queue_priority = "";
        rest_ip = "";
    }

    public String getData_id() {
        return data_id;
    }

    public String getStatus() {
        return status;
    }

    public int getIn_queue() {
        return in_queue;
    }

    public void setIn_queue(int in_queue) {
        this.in_queue = in_queue;
    }

    public String getQueue_priority() {
        return queue_priority;
    }

    public void setQueue_priority(String queue_priority) {
        this.queue_priority = queue_priority;
    }

    public String getRest_ip() {
        return rest_ip;
    }

    public void setRest_ip(String rest_ip) {
        this.rest_ip = rest_ip;
    }

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

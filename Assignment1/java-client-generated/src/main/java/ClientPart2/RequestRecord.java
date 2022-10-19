package ClientPart2;

/**
 * Represents a post request record
 */
public class RequestRecord {
  private final long startTime;
  private final String requestType = "POST";
  private final int latency;
  private final int responseCode;

  public RequestRecord(long startTime, int latency, int responseCode) {
    this.startTime = startTime;
    this.latency = latency;
    this.responseCode = responseCode;
  }

  @Override
  public String toString() {
    return "{" +
        "startTime=" + startTime +
        ", requestType='" + requestType + '\'' +
        ", latency=" + latency +
        ", responseCode='" + responseCode + '\'' +
        '}' + '\n';
  }
}

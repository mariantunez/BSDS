package ClientPart2;

import ClientPart1.LiftRideEvent;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.api.SkiersApi;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Runnable Client 2 class that perform post requests and records latency
 */
public class Client2 implements Runnable {

  private final String LOCAL_SERVER = "http://localhost:8080/SkierServer_war_exploded";
  private final String EC2_SERVER = "http://52.32.205.55:8080/SkierServer_war";
  private final int MAX_ATTEMPTS = 5;
  final private int REQUESTS_TO_POST;
  private final AtomicInteger failedRequests;
  private final AtomicInteger successRequests;

  private final LinkedBlockingQueue<LiftRideEvent> requests;
  private List<Integer> latencies;
  private StringBuffer records;

  public Client2(LinkedBlockingQueue<LiftRideEvent> requests, int requestsToPost,
      AtomicInteger failedRequests, AtomicInteger successRequest, StringBuffer records,
      List<Integer> latencies) {

    this.requests = requests;
    this.REQUESTS_TO_POST = requestsToPost;
    this.failedRequests = failedRequests;
    this.successRequests = successRequest;
    this.records = records;
    this.latencies = latencies;
  }

  @Override
  public void run() {
    // Start Api
    SkiersApi apiInstance = new SkiersApi();
    apiInstance.getApiClient().setBasePath(EC2_SERVER);


    for (int i = 0; i < REQUESTS_TO_POST; i++) {

      // Retrieve a request
      LiftRideEvent request = requests.poll();

      ApiResponse<Void> response = null;
      long startTime = 0;
      long endTime = 0;

      // Attempt sending post request
      for (int j = 0; j < MAX_ATTEMPTS; j++) {

        startTime = System.currentTimeMillis();
        try {
          response = apiInstance.writeNewLiftRideWithHttpInfo(
              request.getLiftRide(),
              request.getResortID(),
              request.getSeasonId(),
              request.getDayId(),
              request.getSkierID()
          );

          // Break loop and count request result if 201 response is received
          if (response.getStatusCode() == 201) {
            failedRequests.getAndDecrement();
            successRequests.getAndIncrement();

            endTime = System.currentTimeMillis();
            break;
          }

        } catch (ApiException e) {
          throw new RuntimeException(e);
        }
      }

      // Save request record and latency
      final int latency = (int) (endTime - startTime);
      records.append(new RequestRecord(startTime, latency, response.getStatusCode()));
      latencies.add(latency);
    }
  }

  /**
   * Starts a given number of threads to run a client
   * @param numThreads int - number of threads to create
   * @param requests Queue - group of LiftRideEvent requests to post
   * @param posts int - number of post requests per thread
   * @param failedRequests AtomicInteger - number of failed post requests
   * @param successRequests AtomicInteger - number of successful post requests
   */
  public static void startClientThreads(int numThreads, LinkedBlockingQueue<LiftRideEvent> requests,
      int posts ,AtomicInteger failedRequests, AtomicInteger successRequests, StringBuffer records,
      List<Integer> latencies)  throws InterruptedException {

    List<Thread> threads = new ArrayList<>();

    // Initialize client and threads
    for(int i=0; i< numThreads; i++) {
      Client2 client = new Client2(requests, posts , failedRequests, successRequests, records, latencies);
      Thread thread = new Thread(client);
      threads.add(thread);
      thread.start();
    }

    // Wait for threads to terminate
    for(Thread thread: threads) {
        thread.join();
    }
  }

  /**
   * Prints performance of post requests
   */
  public static void printPerformance(List<Integer> latencies) {
    String ANSI_BLUE = "\u001B[34m";
    String ANSI_RESET = "\u001B[0m";
    String format = "%27s %10s%n";

    Collections.sort(latencies);

    final int median =  latencies.get(latencies.size()/2);
    final int sum = latencies.stream().mapToInt(Integer::intValue).sum();
    final int mean = sum/latencies.size();
    final int minResponse =  latencies.get(0);
    final int maxResponse = latencies.get(latencies.size() - 1);
    final int percentile99 = latencies.get((int)((latencies.size() - 1) *0.99));

    System.out.printf(format, "Latencies Median ----> ", ANSI_BLUE + median + ANSI_RESET + " ms" );
    System.out.printf(format,"Latencies Mean ----> ", ANSI_BLUE +mean + ANSI_RESET + " ms" );
    System.out.printf(format,"Min Response Time ----> ", ANSI_BLUE + minResponse+ ANSI_RESET + " ms");
    System.out.printf(format,"Max Response Time ---->", ANSI_BLUE + maxResponse + ANSI_RESET + " ms");
    System.out.printf(format, "99th Percentile ----> ", ANSI_BLUE + percentile99 + ANSI_RESET);
  }
}

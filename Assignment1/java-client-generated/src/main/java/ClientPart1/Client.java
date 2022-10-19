package ClientPart1;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.api.SkiersApi;

import java.util.ArrayList;
import java.util.List;


/**
 * Runnable Client class that perform post requests
 */
public class Client implements Runnable {

  private final String LOCAL_SERVER = "http://localhost:8080/SkierServer_war_exploded";
  private final String EC2_SERVER = "http://52.32.205.55:8080/SkierServer_war";
  private final int MAX_ATTEMPTS = 5;
  final private int REQUESTS_TO_POST;
  private final AtomicInteger failedRequests;
  private final AtomicInteger successRequests;

  private final LinkedBlockingQueue<LiftRideEvent> requests;

  public Client(LinkedBlockingQueue<LiftRideEvent> requests, int requestsToPost,
      AtomicInteger failedRequests, AtomicInteger successRequest) {
    this.requests = requests;
    this.REQUESTS_TO_POST = requestsToPost;
    this.failedRequests = failedRequests;
    this.successRequests = successRequest;
  }

  @Override
  public void run() {
    // Start Api
    SkiersApi apiInstance = new SkiersApi();
    apiInstance.getApiClient().setBasePath(EC2_SERVER);

    for (int i = 0; i < REQUESTS_TO_POST; i++) {

      // Retrieve a request
      LiftRideEvent request = requests.poll();
      for (int j=0 ; j < MAX_ATTEMPTS; j++) {

        ApiResponse<Void> response;
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
            break;
          }

        } catch (ApiException e) {
          throw new RuntimeException(e);
        }
      }
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
      int posts ,AtomicInteger failedRequests, AtomicInteger successRequests)
      throws InterruptedException {

    List<Thread> threads = new ArrayList<>();

    for(int i=0; i< numThreads; i++) {
      Client client = new Client(requests, posts , failedRequests, successRequests);
      Thread thread = new Thread(client);
      threads.add(thread);
      thread.start();
    }

    // Wait for threads to terminate
    for(Thread thread: threads) {
        thread.join();
    }
  }
}

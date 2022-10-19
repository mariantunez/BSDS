package ClientPart1;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Runs the client and threads with the given specifications
 */
public class Main {

  public static void main(String[] args) throws InterruptedException {

    final int TOTAL_REQUESTS = 200000;

    // Initialize count of fail and success requests
    AtomicInteger failedRequests = new AtomicInteger(TOTAL_REQUESTS);
    AtomicInteger successRequests = new AtomicInteger();

    // Generate queue of request on separate thread
    LinkedBlockingQueue<LiftRideEvent> requests = new LinkedBlockingQueue<>(TOTAL_REQUESTS);
    new Thread(new RequestGenerator(TOTAL_REQUESTS, requests)).start();


    final long startTime = System.currentTimeMillis();

    // Start Phase 1
    final int INITIAL_THREADS = 32;
    final int INITIAL_REQUESTS = 1000;
    Client.startClientThreads(INITIAL_THREADS, requests, INITIAL_REQUESTS, failedRequests, successRequests);


    //Start Phase 2
    final int FINAL_THREADS = 200;
    final int FINAL_REQUESTS = 840;
    Client.startClientThreads(FINAL_THREADS, requests, FINAL_REQUESTS, failedRequests, successRequests);


    final long endTime = System.currentTimeMillis();

    // Determine wall time and throughput in seconds
    final double wallTime = (endTime - startTime) * 0.001;
    final int throughput = (int) (TOTAL_REQUESTS/wallTime);

    String ANSI_BLUE = "\u001B[34m";
    String ANSI_RESET = "\u001B[0m";

    System.out.println("Client Results Part 1");
    System.out.printf("%27s %10s%n", "Successful Request ----> ", ANSI_BLUE + successRequests.get() + ANSI_RESET);
    System.out.printf("%27s %10s%n", "Failed Request ----> ", ANSI_BLUE + failedRequests.get() + ANSI_RESET);
    System.out.printf("%27s %10s %8s%n", "Wall Time ----> ", ANSI_BLUE + wallTime + ANSI_RESET, "seconds");
    System.out.printf("%27s %10s %15s%n", "Throughput ----> ", ANSI_BLUE + "~"+ throughput + ANSI_RESET, " requests/second");
  }
}

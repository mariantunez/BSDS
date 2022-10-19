package ClientPart1;

import io.swagger.client.model.LiftRide;
import java.util.Random;

/**
 * Represents a random LiftRide event request
 */
public class LiftRideEvent {

  private final int MAX_SKIER_ID = 100000;
  private final int MAX_RESORT_ID = 10;
  private final int MAX_LIFT_ID = 40;
  private final int MAX_TIME = 360;
  private final String SEASON_ID = "2022";
  private final String DAY_ID = "1";

  private final int skierID;
  private final int resortID;
  private final LiftRide liftRide;


  public LiftRideEvent() {
    Random random = new Random();

    // Define random values for each attribute
    this.liftRide = new LiftRide();
    liftRide.setLiftID(randomValue(random, MAX_LIFT_ID));
    liftRide.setTime(randomValue(random, MAX_TIME));

    this.skierID = randomValue(random, MAX_SKIER_ID );
    this.resortID = randomValue(random, MAX_RESORT_ID);
  }

  private int randomValue(Random rand, int upperBound) {
    return rand.nextInt(upperBound + 1);
  }

  public LiftRide getLiftRide() {
    return liftRide;
  }

  public int getSkierID() {
    return skierID;
  }

  public int getResortID() {
    return resortID;
  }

  public String getSeasonId() {
    return SEASON_ID;
  }

  public String getDayId() {
    return DAY_ID;
  }
}

import io.swagger.client.ApiException;
import io.swagger.client.api.ResortsApi;
import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.LiftRide;
import io.swagger.client.model.ResortIDSeasonsBody;

public class SkiersApiExample {

  public static void main(String[] args) {

    SkiersApi apiInstance = new SkiersApi();
    //apiInstance.getApiClient().setBasePath("http://localhost:8080/SkierServer_war_exploded");
    apiInstance.getApiClient().setBasePath("http://18.237.165.97:8080/SkierServer_war");


    LiftRide body = new LiftRide();
    Integer time = 20;
    Integer liftID = 30;

    body.setTime(time);
    body.setLiftID(liftID);

    Integer resortID = 1;
    String seasonID = "2022";
    String dayID = "3";
    Integer skierID = 40;

    try {
      apiInstance.writeNewLiftRide(body, resortID, seasonID, dayID, skierID);
    } catch (ApiException e) {
      //System.err.println("Exception when calling ResortsApi#addSeason");
      System.err.println(e.getCode());
      e.printStackTrace();
    }
  }
}

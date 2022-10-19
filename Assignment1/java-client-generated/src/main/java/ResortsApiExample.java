import io.swagger.client.*;
import io.swagger.client.auth.*;
import io.swagger.client.model.*;
import io.swagger.client.api.ResortsApi;

import java.io.File;
import java.util.*;

public class ResortsApiExample {

  public static void main(String[] args) {

    ResortsApi apiInstance = new ResortsApi();
    //apiInstance.getApiClient().setBasePath("http://localhost:8080/Lab2_war_exploded/labs/1/sskers");
    apiInstance.getApiClient().setBasePath("http://localhost:8080/SkierServer_war_exploded/skiers/1/seasons/2019/day/1/skier/123");
    ResortIDSeasonsBody body = new ResortIDSeasonsBody(); // ResortIDSeasonsBody | Specify new Season value
    Integer resortID = 56; // Integer | ID of the resort of interest
    try {
      apiInstance.addSeason(body, resortID);
    } catch (ApiException e) {
      System.err.println("Exception when calling ResortsApi#addSeason");
      e.printStackTrace();
    }
  }
}


//public class ResortsApiExample {
//
//  public static void main(String[] args) {
//
//    ResortsApi apiInstance = new ResortsApi();
//    apiInstance.getApiClient().setBasePath("http://localhost:8080/Lab2_war_exploded/labs/");
//    Integer resortID = 56; // Integer | ID of the resort of interest
//    try {
//      SeasonsList result = apiInstance.getResortSeasons(resortID);
//      System.out.println(result);
//    } catch (ApiException e) {
//      System.err.println("Exception when calling ResortsApi#getResortSeasons");
//      e.printStackTrace();
//    }
//  }
//}



//public class ResortsApiExample {
//
//  public static void main(String[] args) {
//
//    ResortsApi apiInstance = new ResortsApi();
//    Integer resortID = 56; // Integer | ID of the resort of interest
//    Integer seasonID = 56; // Integer | ID of the resort of interest
//    Integer dayID = 56; // Integer | ID of the resort of interest
//    try {
//      ResortSkiers result = apiInstance.getResortSkiersDay(resortID, seasonID, dayID);
//      System.out.println(result);
//    } catch (ApiException e) {
//      System.err.println("Exception when calling ResortsApi#getResortSkiersDay");
//      e.printStackTrace();
//    }
//  }
//}


//public class ResortsApiExample {
//
//  public static void main(String[] args) {
//
//    ResortsApi apiInstance = new ResortsApi();
//    try {
//      ResortsList result = apiInstance.getResorts();
//      System.out.println(result);
//    } catch (ApiException e) {
//      System.err.println("Exception when calling ResortsApi#getResorts");
//      e.printStackTrace();
//    }
//  }
//}
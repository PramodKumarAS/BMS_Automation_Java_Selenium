package api.client;

import config.ConfigReader;
import io.restassured.RestAssured;

public class RestAssuredClient {
    public static void setUp() {
        RestAssured.baseURI = ConfigReader.get("apiBaseUrl");
    }
}

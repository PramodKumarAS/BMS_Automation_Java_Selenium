package base;

import io.restassured.RestAssured;

public class BaseApi {

	static {
		RestAssured.baseURI="https://bookmyshow-ihmd.onrender.com";
		RestAssured.basePath="/api";
	}
}

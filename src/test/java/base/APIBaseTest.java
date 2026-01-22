package base;

import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;

public class APIBaseTest {
	
	@BeforeClass
	public void setUp() {
		RestAssured.baseURI="https://bookmyshow-ihmd.onrender.com";
		RestAssured.basePath="/api";
	}
}
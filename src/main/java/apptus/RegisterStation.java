package apptus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import ReusableMethodsPackage.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class RegisterStation {
	
	SoftAssert softAssertion= new SoftAssert();
	
	@Test
	public void verifyRegisterWeatherWithoutAPIKey()
	{
	RestAssured.baseURI="http://api.openweathermap.org/";
	RestAssured.useRelaxedHTTPSValidation();
		
	String response= given().log().all().header("Content-Type","application/json")
			.body("{\r\n" + 
			"  \"external_id\": \"SF_TEST001\",\r\n" + 
			"  \"name\": \"San Francisco Test Station\",\r\n" + 
			"  \"latitude\": 37.76,\r\n" + 
			"  \"longitude\": -122.43,\r\n" + 
			"  \"altitude\": 150\r\n" + 
			"}")
			.when().post("data/3.0/stations")
			.then().log().all().assertThat().statusCode(401)
			.body("message", equalTo("Invalid API key. Please see http://openweathermap.org/faq#error401 for more info."))
			.extract().response().asString();
	
	//System.out.println("Response is:"+response);
	JsonPath js=ReusableMethods.rawToJson(response);
	String actResponseCode=js.getString("cod");
	String actResponseMessage=js.getString("message");
	String expCode="401";
	String expMessage="Invalid API key. Please see http://openweathermap.org/faq#error401 for more info.";

	softAssertion.assertEquals(actResponseCode,expCode,"Both codes are not matching");
	softAssertion.assertEquals(actResponseMessage,expMessage,"Both messages are not matching");
	softAssertion.assertAll();
	}
	
	
	@Test
	public void verifyRegisterWeatherWithAPIKey()
	{
	RestAssured.baseURI="http://api.openweathermap.org/";
	RestAssured.useRelaxedHTTPSValidation();
	
	//Creating station with valid API key
	String response= given().log().all().queryParam("appid","229efdfacf9c9a651679f2995516801a")
			.header("Content-Type","application/json")
			.body("{\r\n" + 
			"  \"external_id\": \"DEMO_TEST001\",\r\n" + 
			"  \"name\": \"Interview Station365\",\r\n" + 
			"  \"latitude\": 33.33,\r\n" + 
			"  \"longitude\": -111.43,\r\n" + 
			"  \"altitude\": 444\r\n" + 
			"}")
			.when().post("data/3.0/stations")
			.then().log().all().assertThat().statusCode(201).extract().response().asString();
	
	JsonPath js=ReusableMethods.rawToJson(response);
	String id=js.getString("ID");
	String stationName=js.getString("name");
	
	//Getting created station details and comparing created station name and get API returned station name
	String stationDetails=given().log().all().queryParam("appid","229efdfacf9c9a651679f2995516801a")
			.header("Content-Type","application/json")
			.when().get("data/3.0/stations/"+id+"")
			.then().log().all().assertThat().statusCode(200).extract().asString();
	
	JsonPath js1=ReusableMethods.rawToJson(stationDetails);
	String stationNameOfGetAPI=js1.getString("name");
	softAssertion.assertEquals(stationName,stationNameOfGetAPI,"Created and get api station names are not matching");
	
	//Deleting created station by passing that station id in delete API
			given().log().all().queryParam("appid","229efdfacf9c9a651679f2995516801a")
			.header("Content-Type","application/json")
			.when().delete("data/3.0/stations/"+id+"")
			.then().log().all().assertThat().statusCode(204).extract().asString();
			
	//Trying to delete the deleted station and verifying status code is 404 and message
	String deleteStation=given().log().all().queryParam("appid","229efdfacf9c9a651679f2995516801a")
			.header("Content-Type","application/json")
			.when().delete("data/3.0/stations/"+id+"")
			.then().log().all().assertThat().statusCode(404).extract().asString();
	
	JsonPath js2=ReusableMethods.rawToJson(deleteStation);
	String actMessage=js2.getString("message");
	String expMessae="Station not found";
	
	softAssertion.assertTrue(actMessage.equalsIgnoreCase(expMessae),"Station is not deleted");
	softAssertion.assertAll();
	}
}



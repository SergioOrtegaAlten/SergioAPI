package bdd.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class IMDBPetStoreSteps {


	private static final Logger LOGGER = LogManager.getLogger(IMDBPetStoreSteps.class);

	private static String PET_GET = "/pet/";
	private static String PET_POST = "/pet";
	private static String PET_PUT = "/pet/";
	private static String PET_DELETE = "/pet/";

	private long currentIDPet = 0;
	private String petName = "";
	private String petTag = "";
	private Response currentResponse = null;

	//GET
	@Given("^An Pet with ID equals to (\\d+)$")
	public void an_Pet_with_ID_equals_to(int id) throws Throwable {
		currentIDPet = id;
	}

	@When("^I send a Get Request$")
	public void i_send_a_Get_Request() throws Throwable {
		LOGGER.info("send a GET Request");
		Response response = given().get(PET_GET+this.getCurrentIDPet());
		LOGGER.info("statusCode="+response.getStatusCode());
		this.setCurrentResponse(response);


	}

	@Then("^the response return the status code (\\d+)$")
	public void the_response_return_the_status_code(int status) throws Throwable {
		LOGGER.info("the_response_return_the_status_code="+status);
		assertEquals(status, this.getCurrentResponse().getStatusCode());
	}

	//POST
	@Given("^Add a pet with name (.*) and tag (.*)$")
	public void add_a_Pet_with_name_and_tag(String petName, String petTag) throws Throwable {
		LOGGER.info("add_a_Pet_with_name_and_tag=");
		setCurrentNamePet(petName);
		setCurrentNameTag(petTag);
	}

	@When("^I send a POST Request$")
	public void i_send_a_POST_Request() throws Throwable {
		LOGGER.info("send a POST Request");
		String postBody = "{\n" +
					"\"name\" : \"" + this.petName + "\",\n" +
					"\"tags\" : [\n" +
				" { \n" +
					" \"name\": \"" + this.petTag + "\"\n" +
				" }\n" +
				" ]\n" +
				"}";
		Response response = given().header("Content-type", "application/json").and().body(postBody).when().post(PET_POST);

		LOGGER.info("statusCode="+response.getStatusCode());
		setCurrentResponse(response);
		setCurrentIDPet(response.getBody().path("id"));



	}

	@And("Verify with a Get Request to data is correct")
	public void verify_with_a_get_request_to_data_is_correct(){
		LOGGER.info("Verify with a Get Request to data is correct");
		Response response = given().get(PET_GET+this.getCurrentIDPet());
		Assert.assertTrue(response.jsonPath().getString("name").equals(petName));
		Assert.assertTrue(response.jsonPath().getString("tags.name").equals("[" + petTag + "]"));

	}

	//PUT
	@When("^I Modify the pet name with (.*) and remove the tags$")
	public void i_modify_the_pet_name_with_and_remove_the_tags(String name) throws Throwable {
		LOGGER.info("send a PUT Request");

		setCurrentNamePet(name);
		setCurrentNameTag("");

	}

	@When("^I send a PUT Request$")
	public void i_send_a_put_Request() throws Throwable {
		LOGGER.info("send a PUT Request");
		String postBody = ("{\n" +
				" \"id\": " + currentIDPet + ",\n" +
				" \"name\": \"" + petName + "\",\n" +
				" \"tags\": [\n" +
				" {\n" +
				" \"name\": \"\"\n" +
				" }\n" +
				" ]\n" +
				"}");


		Response response = given().accept(ContentType.JSON).contentType(ContentType.JSON).body(postBody).put(PET_PUT);
		LOGGER.info("statusCode="+response.getStatusCode());



	}

	//DELETE
	@When("^I send a DELETE Request$")
	public void i_send_a_delete_Request() throws Throwable {
		LOGGER.info("send a DELETE Request");
		Response response = given().delete(PET_DELETE + getCurrentIDPet());
		LOGGER.info("statusCode="+response.getStatusCode());
		this.setCurrentResponse(response);


	}
	//currentResponse

	public long getCurrentIDPet() {
		return currentIDPet;
	}

	public void setCurrentIDPet(long currentIDPet) {
		this.currentIDPet = currentIDPet;
	}

	public void setCurrentNamePet(String petName){this.petName = petName;}

	public void setCurrentNameTag(String petTag){this.petTag = petTag;}

	public Response getCurrentResponse() {
		return currentResponse;
	}

	public void setCurrentResponse(Response currentResponse) {
		this.currentResponse = currentResponse;
		Hooks.setLastResponse(currentResponse);
	}

}

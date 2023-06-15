package bdd.steps;

import bdd.utils.RestUtils ;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


public class Hooks {

	private static final Logger LOGGER = LogManager.getLogger(Hooks.class);
	private static Response lastResponse = null;

	@Before
    public void setup(Scenario scenario) throws Exception{
		LOGGER.info("setup");
        RestUtils.setup();
    } 
	
    
    @After
    public void tearDown(Scenario scenario) throws IOException {
    	    		
		LOGGER.info("tearDown");
		LOGGER.info( Hooks.lastResponse.asString());

    }

	public static void setLastResponse(Response lastResponse) {
		Hooks.lastResponse = lastResponse;
	}


}

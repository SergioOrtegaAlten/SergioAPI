package bdd.runner;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		  features = "classpath:cucumber/pet_store.feature" ,
		  glue = "bdd.steps",
		monochrome = true


)
public class RunPetStoreTest {
}

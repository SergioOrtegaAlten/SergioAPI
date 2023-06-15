package bdd.runner;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/cucumber/pet_store.feature",
		glue = "bdd.steps",
		monochrome = true,
		plugin = {"pretty", "html:build/RunPetStoreTest_cucumber-report.html",
				"io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm",
				"json:build/RunPetStoreTest_cucumber.json",
				"rerun:build/RunPetStoreTest_failed_scenarios.txt",
				"junit:build/RunPetStoreTest_cucumber-results.xml"},
		tags = "not @Ignore"
)
public class RunPetStoreTest {
}

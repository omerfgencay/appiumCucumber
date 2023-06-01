package runner;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(format = { "pretty", "html:/home/ninad/eclipse-workspace/Reports/html/",
		"json:/home/ninad/eclipse-workspace/Reports/cucumber-report.json" }, features = {
				"src/test/resource" }, glue = "stepDefinitions", tags = { "@runfile" })

public class Runner {

}

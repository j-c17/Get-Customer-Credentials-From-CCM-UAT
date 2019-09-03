import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetLoginFromCCM {

    @Test
    public void GrabCredentials() throws IOException {

        int customerID = 1000000;
        CSVGenerator csvGenerator = new CSVGenerator();

        for (int i = 0; i < 55; i++) {

            String customerIDstring = Integer.toString(customerID);
            RestAssured.baseURI = "https://ccm-api-ext-uat.countdown.co.nz";
            RequestSpecification httpRequest = RestAssured.given().auth().preemptive().basic("affinity", "pass1");
            Response response = httpRequest.get("/CardServices/CardholderAdministration/GetCustomerDetails/" + customerIDstring);

            // Retrieve the body of the Response
            ResponseBody body = response.getBody();


            // Get JSON Representation from Response Body
            JsonPath jsonPathEvaluator = response.jsonPath();


            if (jsonPathEvaluator.get("ErrorType") == null) {
                String cardNumber = jsonPathEvaluator.get("Data.CardNumber");
                String LastName = jsonPathEvaluator.get("Data.LastName");
                String DOB = jsonPathEvaluator.get("Data.DOB");
                String[] parts = DOB.split("-", 3);
                String part1 = parts[0];
                String part2 = parts[1];
                String part3 = parts[2];
                String part3split = StringUtils.left(part3, 2);

                String DOBparsed = part1 + part2 + part3split;

                csvGenerator.generateCSV(cardNumber, LastName, DOBparsed);
            }

            customerID++;
        }
        //CSVGenerator.flush();

    }

}

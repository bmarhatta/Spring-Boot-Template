package bishwo.springboot.template.ComponentTest.ParameterizedTest;


import bishwo.springboot.template.ComponentTest.ParameterizedTest.Entity.TestData;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import wiremock.com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.stream.Stream;

public class PostApi extends BaseTest {

    private static final String POST = "src/test/resources/componentTestData/SamplePostApi";

    private static Stream<String> getData () throws IOException {
//          return singleTestCase(POST + "/ChatGpt-500.json");
        return listOfTestData(POST).stream();
    }

    @ParameterizedTest
    @MethodSource("getData")
    void test(final String testData) throws JsonProcessingException {
        this.postApi(testData);
    }

    void postApi(final String test) throws JsonProcessingException {
        TestData data = this.jsonToObj(test);
        validateRequestData(data);

        this.dbSetUp(data.getRequestDbResults());

        final HttpEntity httpEntity = new HttpEntity(data.getRequestJson(), this.setHeaders(data.getRequestHeaders()));
        final ResponseEntity response = restTemplate.exchange("/v1/template/post", HttpMethod.POST, httpEntity, String.class);
        if (response.getStatusCode().toString().equals("200 OK")) {
            this.assert200Response(response.getBody().toString(), data.getResponseJson(), String.class);
        } else {
            assertErrorResponse(response, data.getResponseStatus(), data.getResponseJson());
        }

        if (data.getResponseDbResults() != null) {
            this.assertDb(data.getResponseDbResults());
        }
    }

    void testSituationThatRequireAMock () {

    }
}

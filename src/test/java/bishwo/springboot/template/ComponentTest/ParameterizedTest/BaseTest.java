package bishwo.springboot.template.ComponentTest.ParameterizedTest;

import bishwo.springboot.template.ComponentTest.ParameterizedTest.Entity.TestData;
import bishwo.springboot.template.ComponentTest.ParameterizedTest.TestConfig.TestConfigurations;
import bishwo.springboot.template.entity.entitys.OpenAiTable;
import bishwo.springboot.template.MainClass;
import bishwo.springboot.template.entity.postRequeat.PostRequest;
import bishwo.springboot.template.repository.OpenAiRepo;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import wiremock.com.fasterxml.jackson.core.JsonProcessingException;
import wiremock.com.fasterxml.jackson.databind.DeserializationFeature;
import wiremock.com.fasterxml.jackson.databind.ObjectMapper;
import wiremock.com.fasterxml.jackson.databind.SerializationFeature;
import wiremock.org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureWireMock(port = 8040, stubs = "classpath:/stubs/")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = {MainClass.class, TestConfigurations.class})
@ActiveProfiles("test")
public class BaseTest {

    public ListAppender<ILoggingEvent> mockAppender;

    final Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    protected ObjectMapper mapper = new ObjectMapper();

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    OpenAiRepo openAiRepo;

    @BeforeEach
    public void setup() {
        root.setLevel(Level.INFO);
        this.mockAppender = new ListAppender<>();
        root.addAppender(mockAppender);

        this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    }

    @AfterEach
    public void tearDown() {
        mockAppender.stop();
    }

    protected static List<String> listOfTestData(String dir) {

        List<String> listOfTestData = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dir), "*.json")) {
            for (Path p : stream) {

                Stream<String> lines = Files.lines(p);
                String data = lines.collect(Collectors.joining(System.lineSeparator()));
                listOfTestData.add(data);
                lines.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid Test Data");
        }
        return listOfTestData;
    }

    protected static Stream<String> singleTestCase(final String filePath) throws IOException {
        return Stream.of(FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8));
    }

    protected TestData jsonToObj (final String json) throws JsonProcessingException {
        return mapper.readValue(json, TestData.class);
    }

    protected void validateRequestData (TestData testData) {
        Assertions.assertNotNull(testData.getRequestJson(), "Missing Request Data");
        Assertions.assertNotNull(testData.getResponseJson(), "Missing Response Data");
        Assertions.assertNotNull(testData.getResponseStatus(),  "Missing Response Code");
    }

    protected void dbSetUp(List<OpenAiTable> requestDbResults) {
        try {
            if (requestDbResults != null) {
                openAiRepo.saveAll(requestDbResults);
            }
        } catch (Exception e) {
            throw new RuntimeException("Test Failed while persisting your db data");
        }
    }

    protected PostRequest setRequest (String request) throws JsonProcessingException {
        return mapper.readValue(request, PostRequest.class);
    }

    protected HttpHeaders setHeaders (Map<String, String> map) {
        final HttpHeaders headers = new HttpHeaders();
        map.forEach(headers::set);
        return headers;
    }

    protected void assertDb(List<OpenAiTable> openAiTableList) {
        for(OpenAiTable expected: openAiTableList) {
            OpenAiTable actual = openAiRepo.findByaiRequest(expected.getAiRequest());
            Assertions.assertNotNull(actual, "expected db result not found");

            assertEquals(expected.getAiRequest(), actual.getAiRequest());
            assertEquals(expected.getAiResponse(), actual.getAiResponse());
        }
        openAiRepo.deleteAll();
    }

    protected void assert200Response(String acutal, String exspected, Class clazz) {
        if(clazz.isInstance(String.class)) {
            assertEquals(acutal, exspected);
        }
    }

    protected void assertErrorResponse(ResponseEntity response, String responseStatus, String responseJson) {
        assertEquals(response.getStatusCode().toString(), responseStatus);
        assertEquals(response.getBody(), responseJson);
    }

}

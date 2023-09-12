package bishwo.springboot.template.ComponentTest.ParameterizedTest.Entity;

import bishwo.springboot.template.entity.entitys.OpenAiTable;
import bishwo.springboot.template.entity.postRequeat.PostRequest;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class TestData {
    String testName;
    String testDescription;
    String url;
    List<OpenAiTable> requestDbResults;
    List<String> pathVariables;
    PostRequest requestJson;
    Map<String, String> requestHeaders;
    String responseStatus;
    String responseJson;
    List<OpenAiTable> responseDbResults;
}

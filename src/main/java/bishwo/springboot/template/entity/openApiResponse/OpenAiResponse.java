package bishwo.springboot.template.entity.openApiResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class OpenAiResponse {
    private String id;
    private String object;
    private int created;
    private String model;
    private ArrayList<Choice> choices;
    private Usage usage;
}

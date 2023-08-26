package bishwo.springboot.template.entity.openAiRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenAiRequest {
    private String model;
    private String prompt;
    private int max_tokens;
    private double temperature;
    private int top_p;
    private int n;
    private boolean stream;
    private Object logprobs;
    private String stop;
}

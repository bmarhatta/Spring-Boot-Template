package bishwo.springboot.template.entity.openApiResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Choice {
    public String text;
    public int index;
    public Object logprobs;
    public String finish_reason;
}

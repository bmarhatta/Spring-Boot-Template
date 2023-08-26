package bishwo.springboot.template.entity.openApiResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usage {
    public int prompt_tokens;
    public int completion_tokens;
    public int total_tokens;
}

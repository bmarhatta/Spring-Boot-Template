package bishwo.springboot.template.entity.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class OpenAiTable {

    @Id
    @Generated
    private String id;

    @Column
    private String aiRequest;

    @Column
    private String aiResponse;

}

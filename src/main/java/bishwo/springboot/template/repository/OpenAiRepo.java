package bishwo.springboot.template.repository;

import bishwo.springboot.template.entity.entitys.OpenAiTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpenAiRepo extends JpaRepository<OpenAiTable, String> {
    OpenAiTable findByaiRequest(String request);
}

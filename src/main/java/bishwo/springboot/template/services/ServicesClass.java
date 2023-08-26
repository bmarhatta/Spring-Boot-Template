package bishwo.springboot.template.services;

import bishwo.springboot.template.client.BaseClient;
import bishwo.springboot.template.entity.openAiRequest.OpenAiRequest;
import bishwo.springboot.template.entity.entitys.OpenAiTable;
import bishwo.springboot.template.entity.openApiResponse.Choice;
import bishwo.springboot.template.entity.openApiResponse.OpenAiResponse;
import bishwo.springboot.template.repository.OpenAiRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static bishwo.springboot.template.entity.Constants.DB_ERROR;

@Service
@Slf4j
public class ServicesClass {

    @Autowired
    BaseClient baseClient;

    @Autowired
    OpenAiRepo openAiTableDbRepo;

    public OpenAiResponse openAiServices (@Payload OpenAiRequest openAiRequest, @Header(name = "AUTH") String auth) {
        OpenAiResponse openAiResponse =  baseClient.openAiResponse(openAiRequest, auth);
        this.storeInDb(openAiRequest, openAiResponse);
        return openAiResponse;
    }

    @Async //Just to demonstrate that my h2 db works.
    protected void storeInDb (final OpenAiRequest openAiRequest, final OpenAiResponse openAiResponse) {
        try {
            String request = openAiRequest.getPrompt();
            String response = Optional.of(openAiResponse)
                    .map(OpenAiResponse::getChoices)
                    .map(choices -> choices.get(0))
                    .map(Choice::getText).orElse(null);

            this.openAiTableDbRepo.save(OpenAiTable.builder().id(UUID.randomUUID().toString()).aiRequest(request).aiResponse(response).build());
        } catch (Exception e) {
            log.error(DB_ERROR);
        }
    }

}

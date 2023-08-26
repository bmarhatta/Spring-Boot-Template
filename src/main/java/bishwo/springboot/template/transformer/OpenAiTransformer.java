package bishwo.springboot.template.transformer;

import bishwo.springboot.template.entity.openAiRequest.OpenAiRequest;
import bishwo.springboot.template.entity.openApiResponse.Choice;
import bishwo.springboot.template.entity.openApiResponse.OpenAiResponse;
import bishwo.springboot.template.exceptionHandler.Template400Exception;
import bishwo.springboot.template.repository.OpenAiRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

import static bishwo.springboot.template.entity.Constants.MODEL;
import static bishwo.springboot.template.entity.Constants.OPEN_AI_BLANK_RESPONSE;

@Component
public class OpenAiTransformer {

    @Autowired
    OpenAiRepo openAiTableDbRepo;

    public OpenAiRequest requestTransformer(@Payload String request) {
        return OpenAiRequest.builder()
                .model(MODEL)
                .prompt(request)
                .max_tokens(100)
                .temperature(0.02)
                .build();
    }

    public String responseTransformer(@Payload OpenAiResponse openAiResponse) {

        ArrayList<Choice> response = Optional.ofNullable(openAiResponse).map(OpenAiResponse::getChoices).orElse(null);

        if (response == null) {
            throw new Template400Exception(OPEN_AI_BLANK_RESPONSE);
        }

        return response.get(0).getText();

    }

}

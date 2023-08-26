package bishwo.springboot.template.client;

import bishwo.springboot.template.entity.Constants;
import bishwo.springboot.template.entity.openAiRequest.OpenAiRequest;
import bishwo.springboot.template.entity.openApiResponse.OpenAiResponse;
import bishwo.springboot.template.exceptionHandler.Template400Exception;
import bishwo.springboot.template.exceptionHandler.Template500Exception;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static bishwo.springboot.template.entity.Constants.DOWNSTREAM_ERROR;
import static bishwo.springboot.template.entity.Constants.INVALID_REQUEST;

import static org.springframework.http.HttpMethod.POST;

@Component
@Slf4j
public class BaseClient {

    @Value("${completion.url}")
    private String completionUrl;

    @Value("${base.url}")
    private String baseUrl;

    @Autowired
    RestTemplate restTemplate;

    public OpenAiResponse openAiResponse(final OpenAiRequest openAiRequest, final String auth) {
        ResponseEntity<OpenAiResponse> responseEntity = null;

        final HttpEntity<OpenAiRequest> httpEntity = new HttpEntity<>(openAiRequest, setHeaders(auth));

        try {
            String url = this.baseUrl + this.completionUrl;
            responseEntity = restTemplate.exchange(url, POST, httpEntity, OpenAiResponse.class);
            log.info(Constants.SUCCESSFUL_RESPONSE, openAiRequest.toString());
            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            throw new Template400Exception(INVALID_REQUEST);
        } catch (Exception e) {
            throw new Template500Exception(DOWNSTREAM_ERROR);
        }
    }

    private HttpHeaders setHeaders(final String auth) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(auth);
        return headers;
    }

}

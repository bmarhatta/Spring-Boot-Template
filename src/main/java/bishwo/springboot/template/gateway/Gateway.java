package bishwo.springboot.template.gateway;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

public interface Gateway {

    String springIntegrationExample(@Payload String request, @Header("AUTH") String auth);

}

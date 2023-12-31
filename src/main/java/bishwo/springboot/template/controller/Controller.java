package bishwo.springboot.template.controller;

import bishwo.springboot.template.entity.postRequeat.PostRequest;
import bishwo.springboot.template.gateway.Gateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static bishwo.springboot.template.entity.Constants.AUTH;

@RestController
@RequestMapping(path = "/v1/template")
public class Controller {

    @Autowired
    Gateway gateway;

    @PostMapping(value = "/post")
    public ResponseEntity<String> samplePost(@RequestBody final PostRequest postRequest,
                                             @RequestHeader(name = AUTH) final String auth) {
        String response = this.gateway.springIntegrationExample(postRequest.getRequest(), auth);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

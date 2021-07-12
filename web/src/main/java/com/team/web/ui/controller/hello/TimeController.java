package com.team.web.ui.controller.hello;

import com.team.web.ui.model.response.ServiceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import timestamp.TimeStamp;

@RestController public class TimeController {

    @GetMapping("getTest") public ResponseEntity<Object> getUser() {
        ServiceResponse<String> response =
                new ServiceResponse<>("success", TimeStamp.getTimeStamp());
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

}

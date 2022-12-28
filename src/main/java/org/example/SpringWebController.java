package org.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringWebController {

    @RequestMapping("/name")
    public productObj greeting(@RequestParam(value="data", required=false, defaultValue="Spring") String data){
        return new productObj("shop", data);
    }

}
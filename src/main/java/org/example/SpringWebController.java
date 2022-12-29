package org.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SpringWebController {

    @RequestMapping("/name")
    public Map<Integer, productObj> greeting(@RequestParam(value="data", required=false, defaultValue="") String data) {
        Map<Integer, productObj> map = new HashMap();
        map.put(1, new productObj("shop", data));
        map.put(2, new productObj("shop", data));

        return map;
    }

}
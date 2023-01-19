package org.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SpringWebController {
    final String tableName = "shop";
    final String[] columnsTrgt = new String[]{"prod_name","prod_type","prod_amount","prod_price","prod_discount"};

    @GetMapping("/shopDB")
    public Map<Integer, productObj> greeting(@RequestParam(value="data", required=false, defaultValue="") String data) {

        String[] dataStr = data.split(" ");
        String[] columns = new String[dataStr.length / 2];

        for(int i = 0; i < dataStr.length / 2; i++)
            columns[i] = dataStr[i * 2] + " = '" + dataStr[i * 2 + 1] + "'";

        List<String[]> tableRows = SqlTerminal.getInstance().select(tableName, columnsTrgt, columns);

        Map<Integer, productObj> map = new HashMap();

        if(tableRows != null)
            for(int i = 0; i < tableRows.size(); i++)
                map.put(i, new productObj(tableRows.get(i)));

        return map;
    }


    //@PutMapping("/shopDB/{id}")

}
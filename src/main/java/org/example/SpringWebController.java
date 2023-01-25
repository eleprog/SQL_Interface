package org.example;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SpringWebController {
    final String tableName = "shop";
    final String[] columnsTrgt = new String[]{"prod_name","prod_type","prod_amount","prod_price","prod_discount"};

    @GetMapping(value = "/shopDB")
    public ResponseEntity<?> greeting(@RequestParam(value="data", required = false, defaultValue = "") String data) {

        String[] dataStr = data.split(" ");
        String[] columns = new String[dataStr.length / 2];

        for(int i = 0; i < dataStr.length / 2; i++)
            columns[i] = dataStr[i * 2] + " = '" + dataStr[i * 2 + 1] + "'";

        List<String[]> tableRows = SqlTerminal.getInstance().select(tableName, columnsTrgt, columns);

        Map<Integer, ProductObj> map = new HashMap();

        if(tableRows != null) {
            for (int i = 0; i < tableRows.size(); i++)
                map.put(i, new ProductObj(tableRows.get(i)));
            return ResponseEntity.ok(map);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/shopDB/insert")
    public ResponseEntity<?> greeting2(@RequestBody ProductObj data) {
        List<String[]> rowAdd = new ArrayList<>();

        String[] str = new String[5];
        str[0] = "'" + data.getName() + "'";
        str[1] = "'" + data.getType() + "'";
        str[2] = "'" + data.getAmount() + "'";
        str[3] = String.valueOf(data.getPrice());
        str[4] = String.valueOf(data.getDiscount());

        rowAdd.add(str);
        if(SqlTerminal.getInstance().insert(tableName, columnsTrgt, rowAdd) > 0)
            return ResponseEntity.ok(data);

        return ResponseEntity.noContent().build();
    }
}
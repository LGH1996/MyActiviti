package com.lgh.activiti;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import jakarta.annotation.Resource;
import org.assertj.core.internal.Maps;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MyController {
    @Resource
    private DataSource dataSource;

    @GetMapping("/message")
    public Map<String, Object> getMessage() {
        Map<String, Object> map = new HashMap<>();
        map.put("data", dataSource.getClass().getSimpleName());
        return map;
    }
}

package com.ym.mybatisproxy;

import com.alibaba.fastjson2.JSON;
import com.ym.mybatisproxy.service.BuserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class Controller {
    @Value("${db.select}")
    String db;

    @Resource
    BuserService buserService;
    @GetMapping("/sql")
    public String list(){
        System.out.println(db);
        return JSON.toJSONString(buserService.list());
    }
}

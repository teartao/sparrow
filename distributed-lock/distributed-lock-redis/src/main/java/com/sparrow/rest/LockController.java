package com.sparrow.rest;


import com.sparrow.service.DistributeLockService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class LockController {
    @Resource(name = "com.sparrow.service.DistributeLockService")
    private DistributeLockService distributeLockService;

    @RequestMapping("/secKill")
    public String doSecKill() {
        distributeLockService.secKill();
        return "success";
    }
}

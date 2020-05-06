package com.sparrow.rest;


import com.sparrow.service.DistributeLockService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@RestController
public class UserController {
    @Resource(name = "com.sparrow.service.DistributeLockService")
    private DistributeLockService distributeLockService;

    @RequestMapping("/getUser")
    public String getUser() {
        distributeLockService.secKill();
        return "xxx";
    }


    @RequestMapping("/uid")
    String uid(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return session.getId();
    }
}

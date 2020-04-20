package com.sparrow.web;

import java.util.List;

import com.sparrow.mapper.read.CommonReadMapper;
import com.sparrow.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparrow.mapper.write.CommonWriteMapper;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource(name = "com.sparrow.mapper.write.CommonWriteMapper")
    private CommonWriteMapper commonWriteMapper;

    @Resource(name = "com.sparrow.mapper.read.CommonReadMapper")
    private CommonReadMapper commonReadMapper;

    @RequestMapping("/getUsers")
    public List<User> getUsers() {
        List<User> users = commonReadMapper.getAll();
        return users;
    }

    @RequestMapping("/getUser")
    public User getUser(Long id) {
        User user = commonWriteMapper.getOne(id);
        return user;
    }

    @RequestMapping("/add")
    public void save(User user) {
        commonWriteMapper.insert(user);
    }

    @RequestMapping(value = "update")
    public void update(User user) {
        commonWriteMapper.update(user);
    }

    @RequestMapping(value = "/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        commonReadMapper.delete(id);
    }

}
package com.sparrow.mapper;

import com.sparrow.enums.UserSexEnum;
import com.sparrow.mapper.write.CommonWriteMapper;
import com.sparrow.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CommonWriteMapperTest {

    @Resource(name = "com.sparrow.mapper.write.CommonWriteMapper")
    private CommonWriteMapper commonWriteMapper;

    @Test
    public void testInsert() throws Exception {
        commonWriteMapper.insert(new User("aa", "a123456", UserSexEnum.MAN));
        commonWriteMapper.insert(new User("bb", "b123456", UserSexEnum.WOMAN));
        commonWriteMapper.insert(new User("cc", "b123456", UserSexEnum.WOMAN));

        Assert.assertEquals(3, commonWriteMapper.getAll().size());
    }

    @Test
    public void testQuery() throws Exception {
        List<User> users = commonWriteMapper.getAll();
        if (users == null || users.size() == 0) {
            System.out.println("is null");
        } else {
            System.out.println(users.toString());
        }
    }


    @Test
    public void testUpdate() throws Exception {
        User user = commonWriteMapper.getOne(6l);
        System.out.println(user.toString());
        user.setNickName("neo");
        commonWriteMapper.update(user);
        Assert.assertTrue(("neo".equals(commonWriteMapper.getOne(6l).getNickName())));
    }

}
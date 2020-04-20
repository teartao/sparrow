package com.sparrow.mapper;

import java.util.List;

import com.sparrow.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sparrow.enums.UserSexEnum;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CommonReadMapperTest {

	@Resource
	private com.sparrow.mapper.read.CommonReadMapper commonReadMapper;

	@Test
	public void testInsert() throws Exception {
		commonReadMapper.insert(new User("Tom", "password", UserSexEnum.MAN));
		commonReadMapper.insert(new User("Jack", "password", UserSexEnum.WOMAN));
		commonReadMapper.insert(new User("Lily", "password", UserSexEnum.WOMAN));

		Assert.assertEquals(3, commonReadMapper.getAll().size());
	}

	@Test
	public void testQuery() throws Exception {
		List<User> users = commonReadMapper.getAll();
		if(users==null || users.size()==0){
			System.out.println("is null");
		}else{
			System.out.println(users.size());
		}
	}
	
	
	@Test
	public void testUpdate() throws Exception {
		User user = commonReadMapper.getOne(6l);
		System.out.println(user.toString());
		user.setNickName("Mike");
		commonReadMapper.update(user);
		Assert.assertTrue(("Mike".equals(commonReadMapper.getOne(6l).getNickName())));
	}

}
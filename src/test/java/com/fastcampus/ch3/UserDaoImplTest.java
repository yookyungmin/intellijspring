package com.fastcampus.ch3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLOutput;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class) //pom 파일 TEST 메이븐 설정필요 // ac를 자동으로 만들어주고
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class UserDaoImplTest {
    @Autowired
    UserDao userDao;
    @Test
    public void deleteUser() {
    }

    @Test
    public void selectUser() {
    }

    @Test
    public void insertUser() {
    }

    @Test
    public void updateUser() throws Exception {
        Calendar cal= Calendar.getInstance();
        cal.clear(); //시간 필드 초기하
        cal.set(221,1,1);


        userDao.deleteAll();
        User user = new User("asdf", "1234", "abc","aaa@aaa.com", new Date(cal.getTimeInMillis()), "fb", new Date());
        int rowCnt =  userDao.insertUser(user);
        assertTrue(rowCnt==1); //1이면 잘들어간것

        user.setPwd("4321");
        user.setEmail("bbb@bbb.com");
        rowCnt = userDao.updateUser(user);
        assertTrue(rowCnt ==1);

        User user2= userDao.selectUser(user.getId());
        System.out.println("user="+user);
        System.out.println("user2="+user2);
        assertTrue(user.equals(user2));
    }
}
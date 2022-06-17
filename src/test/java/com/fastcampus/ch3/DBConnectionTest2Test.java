package com.fastcampus.ch3;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.junit.Assert.*;

//TDD
public class DBConnectionTest2Test {

    @Test
    public void main() throws  Exception{


        ApplicationContext ac = new GenericXmlApplicationContext("file:src/main/webapp/WEB-INF/spring/**/root-context.xml");
        DataSource ds = ac.getBean(DataSource.class);

        Connection conn = ds.getConnection(); // 데이터베이스의 연결을 얻는다.

        System.out.println("conn = " + conn);
        //    assertTrue(conn!=null); //테스트가 성공햇는지 확인
    }
}
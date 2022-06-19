package com.fastcampus.ch3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.junit.Assert.*;

//TDD //테스트 자동화          System.out.println("conn = " + conn); 처럼 실행결과를 눈으로 직접 확인 할필요없게 하기 위한
//테스트 코드가 많아도 일괄적을 돌려서 어던 테스트가 실패, 성공햇는지 여부 확인
@RunWith(SpringJUnit4ClassRunner.class) //pom 파일 TEST 메이븐 설정필요 // ac를 자동으로 만들어주고
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
//깃헙 DBConnectionTest3 내용
public class DBConnectionTest2Test {
    @Autowired
    DataSource ds;

    @Test
    public void springJdbcConnectionTest() throws  Exception{


//        ApplicationContext ac = new GenericXmlApplicationContext("file:src/main/webapp/WEB-INF/spring/**/root-context.xml"); //수동으로 가져옴
//        DataSource ds = ac.getBean(DataSource.class);  //@Autowired Datasource ds; 로 자동 주입가능

        Connection conn = ds.getConnection(); // 데이터베이스의 연결을 얻는다.

        System.out.println("conn = " + conn); 
        //    assertTrue(conn!=null); //테스트가 성공햇는지 확인 ture면 테스트성공, 아니면실패
    }
}
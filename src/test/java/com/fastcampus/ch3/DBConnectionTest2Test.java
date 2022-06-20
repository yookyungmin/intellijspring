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
import java.sql.PreparedStatement;
import java.util.Date;

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
    public void insertUserTest() throws  Exception{
        User user = new User("asdf8", "1234", "abc", "aaaa@aaaa.com", new Date(), "fb", new Date());
        deleteAll();
        int rowCnt = insertUser(user); //빨간줄 에러 나오면, 아래 예외 선언되어있는데 try catch 없어서

        assertTrue(rowCnt ==1); //성공 1 , 실패 0
    }

    private void deleteAll() {
    }

    //사용자 정보를 user_info 테이블에 저장하는 메서드
    public int insertUser(User user) throws Exception{
        Connection conn = ds.getConnection(); //db 연결 가져오기

//        insert into user_info (id, pwd, name, email, birth, sns, reg_date)
//        values ('asdf2', '1234', 'smith', 'aaa@aaa.com', '2021-01-01','facebook',now());
        String sql = "insert into user_info  values (?, ?, ?, ?, ?,?,now())"; //실행할 sql문

        PreparedStatement pstmt = conn.prepareStatement(sql); // ? 사용 //SQL Injection 공격예방, 성능향상
        pstmt.setString(1, user.getId());
        pstmt.setString(2,user.getPwd());
        pstmt.setString(3, user.getName());
        pstmt.setString(4,user.getEmail());
        pstmt.setDate(5, new java.sql.Date(user.getBirth().getTime())); //getBirth 유틸 데이트인데 setDate가 sql 데이트로 필요하기떄문
        pstmt.setString(6, user.getSns()); //?에 해당하는 값들채우기
        
        int rowCnt = pstmt.executeUpdate(); //insert, delete, update 일떄 사용
        return 0;
    }
    @Test
    public void springJdbcConnection() throws  Exception{


//        ApplicationContext ac = new GenericXmlApplicationContext("file:src/main/webapp/WEB-INF/spring/**/root-context.xml"); //수동으로 가져옴
//        DataSource ds = ac.getBean(DataSource.class);  //@Autowired Datasource ds; 로 자동 주입가능

        Connection conn = ds.getConnection(); // 데이터베이스의 연결을 얻는다.

        System.out.println("conn = " + conn); 
        //    assertTrue(conn!=null); //테스트가 성공햇는지 확인 ture면 테스트성공, 아니면실패
    }
}
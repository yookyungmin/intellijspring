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
import java.sql.ResultSet;
import java.sql.SQLException;
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
        User user = new User("asdf2", "1234", "abc", "aaaa@aaaa.com", new Date(), "fb", new Date());
        deleteAll();
        int rowCnt = insertUser(user); //빨간줄 에러 나오면, 아래 예외 선언되어있는데 try catch 없어서

        assertTrue(rowCnt==1); //성공 1 , 실패 0  //작업 결과 확인
    }

    @Test
    public  void deleteUserTest() throws Exception{
        deleteAll();
        int rowCnt = deleteUser("asdf");
        assertTrue(rowCnt==0);

        User user = new User("asdf2", "1234", "abc", "aaaa@aaaa.com", new Date(), "fb", new Date());
        rowCnt = insertUser(user);
        assertTrue(rowCnt==1);

        rowCnt = deleteUser(user.getId());
        assertTrue(rowCnt==1);

        assertTrue(selectUser(user.getId())==null);
        


    }
    
    //매개변수로 받은 사용자 정보로 user_info 테이블을 update하는메서드
    public int updateUser(User user) throws Exception{
        Connection conn = ds.getConnection(); //db 연결 가져오기


        String sql = "select from user_info where id=?"; //실행할 sql문

        return 0;
    }
    public int deleteUser(String id) throws Exception{
        Connection conn = ds.getConnection(); //db 연결 가져오기
        String sql = "delete from user_info where id=?"; //실행할 sql문
        PreparedStatement pstmt = conn.prepareStatement(sql); // ? 사용 //SQL Injection 공격예방, 성능향상
        pstmt.setString(1,id);
       
       // int rowCnt = pstmt.executeUpdate(); //insert, delete, update 일떄 사용
        //return rowCnt;
        
        return pstmt.executeUpdate(); // 위 두줄과 같으
        
    }
    @Test
    public void selectUserTest()throws Exception{
        deleteAll();
        User user = new User("asdf2", "1234", "abc", "aaaa@aaaa.com", new Date(), "fb", new Date());
        int rowCnt = insertUser(user); //빨간줄 에러 나오면, 아래 예외 선언되어있는데 try catch 없어서
        User user2 = selectUser("asdf2");

        assertTrue(user.getId().equals("asdf2"));
    }
    public User selectUser(String id) throws  Exception{ //참조형반환타입 //id를 주면 id에 해당하느사람의 정보를 가져옴
        Connection conn = ds.getConnection(); //db 연결 가져오기


        String sql = "select from user_info where id=?"; //실행할 sql문

        PreparedStatement pstmt = conn.prepareStatement(sql); // ? 사용 //SQL Injection 공격예방, 성능향상
        pstmt.setString(1,id);
        ResultSet rs =  pstmt.executeQuery(); //select

        if(rs.next()){ //쿼리 결과가 있으면
            User user = new User();
            user.setId(rs.getString(1));
            user.setPwd(rs.getString(2));
            user.setName(rs.getString(3));
            user.setEmail((rs.getString(4)));
            user.setBirth(new Date(rs.getDate(5).getTime()));
            user.setSns(rs.getString(6));
            user.setReg_date(rs.getDate(7));
            //객체 새로 만들고 값을 새로 채워서 반환을 하는데, 결과가 없으면 rs.next()가 false 가 되어서 null반환
            return user;
        }
        return null;
    }
    private void deleteAll() throws Exception{

        Connection conn = ds.getConnection(); //db 연결 가져오기


        String sql = "delete from user_info"; //실행할 sql문

        PreparedStatement pstmt = conn.prepareStatement(sql); // ? 사용 //SQL Injection 공격예방, 성능향상
              pstmt.executeUpdate(); //insert, delete, update 일떄 사용
    }

    @Test
    public void transactionTest()throws Exception {
        Connection conn=null;
        try {
            deleteAll();
            conn = ds.getConnection(); //db 연결 가져오기
            conn.setAutoCommit(false); //conn.setAutoCommit(true); 기본값
//        insert into user_info (id, pwd, name, email, birth, sns, reg_date)
//        values ('asdf2', '1234', 'smith', 'aaa@aaa.com', '2021-01-01','facebook',now());
            String sql = "insert into user_info  values (?, ?, ?, ?, ?,?,now())"; //실행할 sql문

            PreparedStatement pstmt = conn.prepareStatement(sql); // ? 사용 //SQL Injection 공격예방, 성능향상
            pstmt.setString(1, "asdf");
            pstmt.setString(2, "1234");
            pstmt.setString(3, "abc");
            pstmt.setString(4, "aaa@aaa.com");
            pstmt.setDate(5, new java.sql.Date(new Date().getTime())); //getBirth 유틸 데이트인데 setDate가 sql 데이트로 필요하기떄문
            pstmt.setString(6, "fb"); //?에 해당하는 값들채우기

            int rowCnt = pstmt.executeUpdate(); //insert, delete, update 일떄 사용
            pstmt.setString(1, "asdf");//1 첫번쨰 id
            rowCnt = pstmt.executeUpdate(); //insert, delete, update 일떄 사용

            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            e.printStackTrace();
        } finally {
        }

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
        return rowCnt;
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
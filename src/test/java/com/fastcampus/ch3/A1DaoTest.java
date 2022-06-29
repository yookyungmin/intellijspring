package com.fastcampus.ch3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;

//테스트코드 작성후
//java.lang.NullPointerException 나오면 아래 두줄
@RunWith(SpringJUnit4ClassRunner.class) //pom 파일 TEST 메이븐 설정필요 // ac를 자동으로 만들어주고
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class A1DaoTest {
    @Autowired
    A1Dao a1Dao;
    @Autowired
    DataSource ds;
    @Test
    public void insetTest() throws Exception{
        //TxManager생성 //둘다 성공해야 적용하게끔 하기위해선
        PlatformTransactionManager tm = new DataSourceTransactionManager(ds);
        TransactionStatus status = tm.getTransaction(new DefaultTransactionDefinition());
        //Tx시작

        try {
            a1Dao.deleteAll();
            a1Dao.insert(1, 100);
            a1Dao.insert(1, 200);
            tm.commit(status); //성공하면 commit
        } catch (Exception e) {
            e.printStackTrace();
            tm.rollback(status); //예외 발생시 롤백 // 하나라도 실패하면 롤백으로 Transactionl 적용
        } finally {
        }
    }
}
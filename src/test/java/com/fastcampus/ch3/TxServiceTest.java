package com.fastcampus.ch3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class) //pom 파일 TEST 메이븐 설정필요 // ac를 자동으로 만들어주고
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class TxServiceTest {

    @Autowired
    TxService txService;

    @Test
    public void insertA1WithoutTxTest() throws Exception {
        //txService.insertA1WithTxSuccess();
        txService.insertA1WithTxFail();
    }
}

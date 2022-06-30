package com.fastcampus.ch3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TxService {

    @Autowired
    A1Dao a1Dao; //Dao 주입
    @Autowired
    B1Dao b1Dao;

    public void insertA1WithoutTx() throws Exception {
        a1Dao.insert(1, 100);
        a1Dao.insert(1, 200);

    }

    @Transactional(rollbackFor = Exception.class)//있어야 롤백이됨
    public void insertA1WithTxFail() throws Exception {
        a1Dao.insert(1, 100); //성공
        a1Dao.insert(1, 200); //실패
    }

    @Transactional
    public void insertA1WithTxSuccess() throws Exception {
        a1Dao.insert(1, 100);
        a1Dao.insert(2, 200);
    }
}

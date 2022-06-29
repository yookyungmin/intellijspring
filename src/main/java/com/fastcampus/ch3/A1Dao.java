package com.fastcampus.ch3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository //Dao에 붙임 db에 접근
public class A1Dao {
    @Autowired
     DataSource ds;

    public int insert(int key, int value)throws Exception{
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
//            conn = ds.getConnection();
            conn = DataSourceUtils.getConnection(ds); //Transactionl 사용하기위해
            System.out.println("conn = " + conn);
            pstmt = conn.prepareStatement("INSERT INTO a1 values (?,?)");
            pstmt.setInt(1, key); //setter
            pstmt.setInt(2, value);

            return pstmt.executeUpdate(); //insert, delete, update 일떄 사용
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; //예외가 발생하면 다시 던져서 insertTest가 받아서 예외처리 롤백이 실행되어야함
        } finally {
//            close(conn, pstmt);
            close(pstmt);
            DataSourceUtils.releaseConnection(conn, ds);
        }

    }

    private void close(AutoCloseable... acs) {
        for(AutoCloseable ac :acs)
            try { if(ac!=null) ac.close(); } catch(Exception e) { e.printStackTrace(); }
    }

    public void deleteAll() throws Exception{
        Connection conn = ds.getConnection(); 
        //deleteAll은 TX와 별개로 동작해야 하므로 위에문장 사용
   

        String sql = "delete from a1";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate();
        close(pstmt);
    }
}

package com.cks.bogeom.dms;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;

@SpringBootTest
public class dbSettingTests {
    @Test
    public void testConnection() {

        try(Connection connection =
                    DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/dms",
                            "newuser",
                            "Toast1234!")){            //****에 패스워드 입력
            System.out.println(connection);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

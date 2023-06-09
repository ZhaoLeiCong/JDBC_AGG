package com.atguigu.demo05LIANJIECHI;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.atguigu.demo04DAO.Admin;
import com.atguigu.demo04DAO.AdminBaseDAO;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * Billkin
 * 2023/6/7
 */
public class DruidTest {
    public static void main(String[] args) throws Exception {
        testDruid02();
    }
    public static void testDurid01() throws Exception {
        DruidDataSource dds = new DruidDataSource();
        Properties p = new Properties();
        InputStream in = DBCPTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
        p.load(in);
        DataSource ds = DruidDataSourceFactory.createDataSource(p);
        Connection conn = ds.getConnection();
        AdminBaseDAO abd = new AdminBaseDAO();
        List<Admin> all = abd.getAll(conn);
        all.forEach(System.out::println);
    }
    public static void testDruid02(){
        try {
            Connection conn = JDBCUtils.getConnectionOfDruid();
            AdminBaseDAO abd = new AdminBaseDAO();
            List<Admin> all = abd.getAll(conn);
            all.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}

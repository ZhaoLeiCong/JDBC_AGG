package com.atguigu.demo05LIANJIECHI;

import com.atguigu.demo04DAO.Admin;
import com.atguigu.demo04DAO.AdminBaseDAO;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

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
 * 测试DBCP的数据库连接池技术
 */
public class DBCPTest {
    public static void main(String[] args) throws Exception {
        //testDBCP01();
        //testDBCP02();
        Connection conn = JDBCUtils.getConnectionOfDBCP();
        AdminBaseDAO abd = new AdminBaseDAO();
        List<Admin> all = abd.getAll(conn);
        all.forEach(System.out::println);
    }
    public static void testDBCP01() throws SQLException {
        //创建BDCP的数据库连接池对象
        BasicDataSource source = new BasicDataSource();
        //设置基本信息
        source.setDriverClassName("com.mysql.jdbc.Driver");
        source.setUrl("jdbc:mysql://localhost:3306/atguigudb?rewriteBatchedStatements=true&useUnicode=true&characterEncoding=utf8");
        source.setUsername("root");
        source.setPassword("root");

        source.setInitialSize(10);
        source.setMaxActive(10);
        Connection conn = source.getConnection();
        AdminBaseDAO abd = new AdminBaseDAO();
        List<Admin> all = abd.getAll(conn);
        all.forEach(System.out::println);
    }
    //方式二:使用配置文件
    public static void testDBCP02() throws Exception {
        System.out.println("使用配置文件获取连接");
        Properties p = new Properties();
        InputStream in = DBCPTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
        p.load(in);
        DataSource ds = BasicDataSourceFactory.createDataSource(p);
        Connection conn = ds.getConnection();
        AdminBaseDAO abd = new AdminBaseDAO();
        List<Admin> all = abd.getAll(conn);
        all.forEach(System.out::println);
    }
}

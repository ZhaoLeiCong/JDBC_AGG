package com.atguigu.demo01;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * Billkin
 * 2023/6/4
 */
public class JDBCUtils {
    public static Connection getConnection() throws Exception {
        //读取配置文件的基本信息
        InputStream in = JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties p = new Properties();
        p.load(in);
        String user = p.getProperty("user");
        String password = p.getProperty("password");
        String url = p.getProperty("url");
        String driverClass = p.getProperty("driverClass");
        /*System.out.println(user+"\r\n"+password+"\r\n"+url+"\r\n"+driverClass);*/
        //加载驱动
        Class.forName(driverClass);
        //获取连接
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }
}

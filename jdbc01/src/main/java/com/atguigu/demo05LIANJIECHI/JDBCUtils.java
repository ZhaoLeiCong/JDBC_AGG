package com.atguigu.demo05LIANJIECHI;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.atguigu.demo04DAO.Admin;
import com.atguigu.demo04DAO.AdminBaseDAO;
import com.mchange.v2.c3p0.ComboPooledDataSource;
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
 */
public class JDBCUtils {
    //单例模式,都获取唯一的连接池.数据库连接池只提供一个
    private static final ComboPooledDataSource cpds = new ComboPooledDataSource();
    public static Connection getConnectionOfC3p0() throws Exception {
        return cpds.getConnection();

    }
    private static final DataSource dbcpDS;
    static {
        final Properties p = new Properties();
        final InputStream in = JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
        try {
            p.load(in);
            dbcpDS = BasicDataSourceFactory.createDataSource(p);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //使用DBCP的技术获取连接
    public static Connection getConnectionOfDBCP() throws Exception {
        return dbcpDS.getConnection();
    }

    private static final DataSource druidDS;
    static {
        final Properties p = new Properties();
        final InputStream in = JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
        try {
            p.load(in);
            druidDS = DruidDataSourceFactory.createDataSource(p);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnectionOfDruid() throws SQLException {
        return druidDS.getConnection();
    }
}

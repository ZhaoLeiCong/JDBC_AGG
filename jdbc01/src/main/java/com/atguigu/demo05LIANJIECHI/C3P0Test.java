package com.atguigu.demo05LIANJIECHI;

import com.atguigu.demo04DAO.Admin;
import com.atguigu.demo04DAO.AdminBaseDAO;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Billkin
 * 2023/6/7
 */
public class C3P0Test {
    public static void main(String[] args) throws Exception {
        //c3p0Test01();
        //c3p0Test02();
        //testUtils();
        showFile(new File("E:\\mymaven\\repository"));
    }
    public static void c3p0Test01()throws Exception{
        //方式一
        //创建连接池对象
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        //设置连接参数
        cpds.setDriverClass("com.mysql.jdbc.Driver");
        cpds.setJdbcUrl("jdbc:mysql://localhost:3306/atguigudb?rewriteBatchedStatements=true&useUnicode=true&characterEncoding=utf8");
        cpds.setUser("root");
        cpds.setPassword("root");
        //设置连接池参数
        cpds.setInitialPoolSize(10);//初始连接数,刚创建好连接池的时候准备的连接数量
        cpds.setMaxPoolSize(100);//最大连接数,连接池中最多可以放多少个连接
        cpds.setCheckoutTimeout(5000);//最大等待时间,连接池中没有连接时最长等待时间
        cpds.setMaxIdleTime(2000);//最大空闲回收时间,连接池中的空闲连接多久没有使用就会回收
        Connection conn = cpds.getConnection();
        AdminBaseDAO abd = new AdminBaseDAO();
        List<Admin> all = abd.getAll(conn);
        all.forEach(System.out::println);
        //一般情况下不会销毁连接池,只会关闭连接
        //销毁连接池
        //DataSources.destroy(cpds);
    }
    //方式二使用配置文件,将配置信息写到xml文件中
    public static void c3p0Test02() throws SQLException {
        System.out.println("02");
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        Connection conn = cpds.getConnection();
        AdminBaseDAO abd = new AdminBaseDAO();
        List<Admin> all = abd.getAll(conn);
        all.forEach(System.out::println);
    }
    //使用JDBC工具类
    public static void testUtils() throws Exception {
        Connection conn = JDBCUtils.getConnectionOfC3p0();
        AdminBaseDAO abd = new AdminBaseDAO();
        List<Admin> all = abd.getAll(conn);
        all.forEach(System.out::println);
    }
    public static void showFile(File f){
        File[] files = f.listFiles();
        for (File file : files) {
            if (file.isDirectory()) showFile(file);
            else if (file.isFile()&&(file.getName().toLowerCase().contains("pool"))) System.out.println(file);
        }
    }
}

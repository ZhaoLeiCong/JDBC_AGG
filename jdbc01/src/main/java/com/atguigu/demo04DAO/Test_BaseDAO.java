package com.atguigu.demo04DAO;

import com.atguigu.demo01.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.SimpleFormatter;

/**
 * Billkin
 * 2023/6/6
 *
 */
public class Test_BaseDAO {
    public static void main(String[] args) {
        new Thread(()->{
            AdminBaseDAO abd = new AdminBaseDAO();
            Connection conn = null;
            try {
                conn = JDBCUtils.getConnection();
                conn .setAutoCommit(false);
                /*Admin admin = new Admin(1, "王俊勇", new SimpleDateFormat("yyyy-MM-dd").parse("2001-3-20"), "perth", 10000.0D);
                abd.insert(conn,admin);
                System.out.println("添加成功!!!稍后提交事务");*/
                //修改数据
                Admin admin1 = new Admin(6, "柯智辉", new SimpleDateFormat("yyyy-MM-dd").parse("1994-12-4"), "up", 100000.0);;
                /*Admin admin1 = new Admin();
                admin1.setId(6);
                admin1.setUser_name("刘义民");
                admin1.setPwd("lym");*/
                abd.updateByID(conn,admin1);
                System.out.println("修改成功!!!稍后提交事务");
                conn.commit();
                //abd.deleteByID(conn,11);
                //System.out.println(abd.getAdminByID(conn, 8));

                System.out.println(Thread.currentThread()+"查看表中所有数据");
                List<Admin> all = abd.getAll(conn);
                all.forEach(System.out::println);
                System.out.println(abd.getCount(conn));
                conn.commit();
            } catch (Exception e) {
                System.out.println("测试程序抛出错误,该连接的事务开始回滚");
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
                System.out.println(e);
            }finally{
                System.out.println("开始关闭资源");
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println(e);
                }
            }
        }).start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        new Thread(()->{
            AdminBaseDAO abd = new AdminBaseDAO();
            Connection conn = null;
            System.out.println(Thread.currentThread()+"新线程查询表中所有数据");
            try {
                conn = JDBCUtils.getConnection();
                conn.setAutoCommit(false);
                List<Admin> all = abd.getAll(conn);
                all.forEach(System.out::println);
            } catch (Exception e) {
                System.out.println(e);
            }finally{
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println(e);
                }
            }
        }).start();
    }

}

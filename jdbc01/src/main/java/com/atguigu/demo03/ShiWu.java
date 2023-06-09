package com.atguigu.demo03;

import com.atguigu.demo01.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Billkin
 * 2023/6/5
 * 事务回滚:rollBack();
 * 数据库事务,使数据从一种状态变换到另一种状态
 * 一组逻辑操作单元一个或多个DM操作
 * 数据一旦提交不可回滚
 * DDL操作一旦执行都会自动提交
 * 增删改查默认情况下一旦执行就会自动提交,可以将自动提交关闭
 * 默认在关闭连接的时候都会自动提交,可以让一组操作使用同一连接,针对数据库连接池的操作需要把连接属性设置回自动提交
 */
public class ShiWu {
    public static void main(String[] args) throws Exception {
        //获取连接,
        java.sql.Connection conn = JDBCUtils.getConnection();
        try{
            //将自动提交关闭
            conn.setAutoCommit(false);
            String sql1 = "update `order` set deposit=(deposit-?) where order_id = ?";
            update(conn,sql1,100,1);
            //System.out.println(10/0);
            String sql2 = "update `order` set deposit=(deposit+?) where order_id = ?";
            update(conn,sql2,100,2);
            //提交事务
            System.out.println(10/0);
            System.out.println("转账成功");
            conn.commit();
        }catch(Exception e){
            System.out.println(e);
            //如果出现错误事务回滚
            System.out.println("转账失败");
            conn.rollback();
        }finally {

            //将事务设置回自动提交
            conn.setAutoCommit(true);
            //关闭连接
            conn.close();
        }

    }
    //考虑数据库事务后的转账操作,需要使用同一连接,等一组操作都执行完毕之后再提交事务
    public static void update(Connection conn, String sql, Object...args) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i+1,args[i]);
        }
        ps.execute();
        ps.close();
    }
}

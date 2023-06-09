package com.atguigu.demo03;

import com.atguigu.demo01.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * Billkin
 * 2023/6/6
 * 原子性:是指事务是一个不可分割的工作单位,事务中的操作要么都发生,要么都不发生
 * 一致性:事务必须使数据库从一个一致性状态转换到另一个一致性状态
 * 隔离性:一个事务的执行不能被其他事务干扰,一个事务内部的操作及使用的数据并发的其他事务是隔离的,
 * 持久性:一个事务一旦被提交,他对数据库中数据的改变是永久性的
 *
 * 数据库的并发问题
 * 脏读:读取已经更新但没有被提交的字段
 * 不可重复读:同一个连接读取到已提交更新的不同数据,一般不处理
 * 幻读:同一个连接读取到已提交更新的数据,一般不处理
 * 四种隔离级别:
 * READ UNCOMMITTED
 * 读未提交数据:允许事务读取未被q事务提交的变更,脏读,不可重复读和幻读的问题都会出现
 * READ COMMITTED
 * 读已提交数据:只允许事务读取已经被其他事务提交的变更,可以避免脏读,但不可重复读和幻读问题仍然可能出现
 * REPEATABLE READ
 * 可重复读:避免脏读和不可重复读,但幻读的问题存在
 * SERIALIZABLE
 * 串行化:所有并发问题都可以避免,但性能十分低下
 * MySQL支持四种事务隔离级别,MySQL默认的事务隔离级别为:REPEATABLE READ
 * 一般情况下只解决脏读问题就可以
 */
public class ShiWu_ACID {
    public static void main(String[] args) throws Exception {
        new Thread(()->{
            String sql="update admin set deposit = (deposit-?) where id = ?";
            Connection conn = null;
            try {
                conn = JDBCUtils.getConnection();
                conn.setAutoCommit(false);
                testUpdate(conn,sql,1000,2);
                Thread.sleep(800);
                conn.commit();
            } catch (Exception e) {
                System.out.println(e);
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }finally{
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    System.out.println(e);
                } finally{
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        System.out.println(e);
                    }
                }
            }
        }).start();
        new Thread(()->{
            String sql="select id,user_name,pwd,deposit from admin";
            Connection conn = null;
            try {
                conn = JDBCUtils.getConnection();
                conn.setAutoCommit(false);
                conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                for (int i = 0; i < 5; i++) {
                    Admin admin = getInstance(conn, Admin.class, sql);
                    System.out.println(admin);
                    Thread.sleep(5000);
                }
                conn.commit();
            } catch (Exception e) {
                System.out.println(e);
            }finally{
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    System.out.println(e);
                    try {
                        conn.rollback();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } finally{
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        System.out.println(e);
                    }
                }
            }
        }).start();

    }
    //考虑事务的通用查询操作,用于返回数据表中的一条记录,不然事务自动提交,使用同一个连接,一组操作结束之后连接再进行关闭
    public static <T> T getInstance(Connection conn,Class<T> clazz,String sql,Object...args) throws SQLException {
        System.out.println(conn.getTransactionIsolation());
        //获取PreparedStatement对象用于操作SQL语句
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            //执行sql
            rs = ps.executeQuery();
            //获取元数据
            ResultSetMetaData rsmd = rs.getMetaData();
            //获取列数
            int count = rsmd.getColumnCount();
            //处理每个列值
            while(rs.next()){
                //通过反射创建值
                T t = clazz.newInstance();

                for (int i = 1; i <= count; i++) {
                    //获取列值
                    Object columnValue = rs.getObject(i);
                    //获取列名
                    String columnLabel = rsmd.getColumnLabel(i);
                    //给指定属性赋值
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t,columnValue);
                }
                return t;
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally{
            try {
                if (ps!=null) ps.close();

            } catch (SQLException e) {
                System.out.println(e);
            } finally {
                {
                    try {
                        if (rs!=null) rs.close();
                    } catch (SQLException e) {
                        System.out.println(e);
                    }
                }
            }
        }
        return null;
    }
    public static void testUpdate(Connection conn,String sql,Object...args) throws Exception{
        conn.setAutoCommit(false);
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i+1,args[i]);
        }
        ps.execute();
        ps.close();

    }
}

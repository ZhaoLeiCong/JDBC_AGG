package com.atguigu.demo06DML;

import com.atguigu.demo04DAO.Admin;
import com.atguigu.demo05LIANJIECHI.JDBCUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.*;
import org.junit.Test;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Billkin
 * 2023/6/7
 * commons-dbutils是Apache组织提供的一个开源JDBC工具类库,封装了这很对于数据库的增删改查操作
 * QueryRunner类中提供了查询操作
 */
public class QueryRunnerTest {
    /*public static void main(String[] args) throws Exception {
        //testInsert();
        *//*System.out.println("---------------------------------------------------");
        testQuery01();
        System.out.println("---------------------------------------------------");
        testQuery02();
        System.out.println("---------------------------------------------------");
        testQuery03();
        System.out.println("---------------------------------------------------");
        testQuery04();*//*
        System.out.println("---------------------------------------------------");
        testQuery05();
        testQuery07();
    }*/
    @Test
    public void Test(){
        testQuery07();
    }
    public void testInsert(){
        QueryRunner qr = new QueryRunner();
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnectionOfDruid();
            conn.setAutoCommit(false);
            String sql = "insert into admin (user_name,birth,pwd,deposit) value(?,?,?,?)";
            int update = qr.update(conn, sql, "黄明明", "1998-4-17", "saint", 10000);
            System.out.println("添加了"+update+"条记录");
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }finally{
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    //测试查询
    //BeanHandler:是ResultSetHandler接口的实现类,用于封装表中的一条记录
    public void testQuery01() throws Exception {
        QueryRunner qr = new QueryRunner();
        Connection conn = JDBCUtils.getConnectionOfDruid();
        String sql = "select id,user_name,birth,pwd,deposit from admin where id =?";
        BeanHandler<Admin> handler = new BeanHandler<>(Admin.class);
        Admin admin = qr.query(conn, sql, handler, 6);
        System.out.println(admin);
    }
    //BeanListHandler:是ResultSetHandler接口的实现类,用于封装表中的多条记录构成的集合
    public void testQuery02()  {
        QueryRunner qr = new QueryRunner();
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnectionOfDruid();
            String sql = "select id,user_name,birth,pwd,deposit from admin where id <?";
            BeanListHandler<Admin> handler = new BeanListHandler<>(Admin.class);
            List<Admin> list = qr.query(conn, sql, handler, 13);
            list.forEach(System.out::println);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
    //MapHandler:是ResultSetHandler接口的实现类,对应表中的一条记录,将字段及其相应的字段值作为map中的key和value
    public void testQuery03()  {
        QueryRunner qr = new QueryRunner();
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnectionOfDruid();
            String sql = "select id,user_name,birth,pwd,deposit from admin where id = ?";
            MapHandler handler = new MapHandler();
            Map<String, Object> map = qr.query(conn, sql, handler, 6);
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            Iterator<Map.Entry<String, Object>> it = entries.iterator();
            while (it.hasNext()) {
                Map.Entry<String, Object> entry = it.next();
                String key = entry.getKey();
                Object value = entry.getValue();
                System.out.println(key +"," +value);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DbUtils.closeQuietly(conn);
        }
    }

    //MapListHandler:是ResultSetHandler接口的实现类,对应表中的多条记录,
    // 将字段及其相应的字段值作为map中的key和value,将这些map添加到list中
    public void testQuery04()  {
        QueryRunner qr = new QueryRunner();
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnectionOfDruid();
            String sql = "select id,user_name,birth,pwd,deposit from admin where id < ?";
            MapListHandler handler = new MapListHandler();
            List<Map<String, Object>> list = qr.query(conn, sql, handler, 6);
            list.forEach(System.out::println);
            /*Set<Map.Entry<String, Object>> entries = map.entrySet();
            Iterator<Map.Entry<String, Object>> it = entries.iterator();
            while (it.hasNext()) {
                Map.Entry<String, Object> entry = it.next();
                String key = entry.getKey();
                Object value = entry.getValue();
                System.out.println(key +"," +value);
            }*/
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    //ScalarHandler:用于查询表的特殊值
    public void testQuery05(){
        QueryRunner qr = new QueryRunner();
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnectionOfDruid();
            String sql = "select count(*) from admin";
            ScalarHandler<Long> handler = new ScalarHandler<>();
            Long count = qr.query(conn,sql, handler);
            System.out.println(count);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void testQuery06(){
        QueryRunner qr = new QueryRunner();
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnectionOfDruid();
            String sql = "select max(birth) from admin";
            ScalarHandler<Date> handler = new ScalarHandler<>();
            Date date = qr.query(conn,sql, handler);
            System.out.println(date);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    //自定义ResultSetHandler的实现类
    public void testQuery07(){
        QueryRunner qr = new QueryRunner();
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnectionOfDruid();
            String sql = "select id,user_name,birth,pwd,deposit from admin where id =?";
            ResultSetHandler<Admin> handler = new ResultSetHandler<Admin>() {
                @Override
                public Admin handle(ResultSet rs) throws SQLException {
                    ResultSetMetaData rsmd = rs.getMetaData();
                    Admin admin = null;
                    int count = rsmd.getColumnCount();
                    if (rs.next()){
                        int id = rs.getInt("id");
                        String user_name = rs.getString("user_name");
                        java.sql.Date birth = rs.getDate("birth");
                        String pwd = rs.getString("pwd");
                        double deposit = rs.getDouble("deposit");
                        admin = new Admin(id,user_name,birth,pwd,deposit);
                    }
                    return admin;
                }
            };
            Admin admin = qr.query(conn, sql, handler, 6);
            System.out.println(admin);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

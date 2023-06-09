package com.atguigu.demo02;

import com.atguigu.demo01.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * Billkin
 * 2023/6/5
 * 使用PreparedStatement实现批量数据的操作
 * update delete本身就具有批量操作的效果
 * 此时的批量操作主要值得是批量插入,实现更高效的批量插入
 * 方式一:使用Statement
 * 方式二:使用PreparedStatement
 * 方式三:addBatch()积攒SQL,executeBatch()执行Batch,clearBatch()清空batch
 * 方式四:设置不允许自动提交数据,最后设置同一提交数据,调用提交事务方法的对象是Connection类型的对象
 */
public class Piliangshujvcaozuo {
    public static void main(String[] args) throws Exception {
        truncate();
        long t1 = System.currentTimeMillis();
        //testPiLiang02();//方式二20000条数据插入使用时间:20.915秒
        //testPiLiang03();//方式三2000000条数据插入使用时间:12.646秒
        testPiLiang04();//方式四2000000条数据插入使用时间:8.846秒
        long t2 = System.currentTimeMillis();
        System.out.println("批量插入数据使用的时间:"+((t2-t1)/1000.0));
        count();
    }
    public static void testPiLiang02() throws Exception {
        System.out.println("开始向表中批量插入数据......");
        Connection conn = JDBCUtils.getConnection();
        String sql = "insert into test_piliang (`name`) values (?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 1; i <= 20000; i++) {
            ps.setObject(1,"name_"+i);
            ps.execute();
        }
        ps.close();
        conn.close();
    }
    //方式三:addBatch(),executeBatch(),clearBatch()
    public static void testPiLiang03()throws Exception {
        System.out.println("开始向表中批量插入数据......");
        Connection conn = JDBCUtils.getConnection();
        String sql = "insert into test_piliang (`name`) values (?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 1; i <= 2000000; i++) {
            ps.setObject(1,"name_"+i);
            //积攒sql
            ps.addBatch();
            //执行batch
            if (i%500==0){
                ps.executeBatch();
                //清空batch
                ps.clearBatch();
            }

        }
        ps.close();
        conn.close();
    }
    //方式四:设置不会默认提交事务,在结束后提交,
    public static void testPiLiang04()throws Exception {
        System.out.println("开始向表中批量插入数据......");
        Connection conn = JDBCUtils.getConnection();
        conn.setAutoCommit(false);
        String sql = "insert into test_piliang (`name`) values (?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 1; i <= 2000000; i++) {
            ps.setObject(1,"name_"+i);
            //攒sql
            ps.addBatch();
            if (i%500==0){
                //执行batch
                ps.executeBatch();
                //清空batch
                ps.clearBatch();
            }
        }
        //提交事务
        conn.commit();
        ps.close();
        conn.close();
    }
    public static void count() throws Exception {
        Connection conn = JDBCUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement("select COUNT(*) count from test_piliang");
        ResultSet rs = ps.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        if (rs.next()){
            String columnLabel = rsmd.getColumnLabel(1);
            //System.out.println(columnLabel);
            System.out.println("此时表中的数据个数:"+rs.getInt(columnLabel));
            ps.close();
            conn.close();
        }

    }
    public static void truncate() throws Exception {
        System.out.println("正在清空表中的原有数据......");
        Connection conn = JDBCUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement("truncate test_piliang");
        ps.execute();
        ps.close();
        conn.close();
    }

}

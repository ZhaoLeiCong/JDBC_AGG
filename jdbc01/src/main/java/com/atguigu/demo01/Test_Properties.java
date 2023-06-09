package com.atguigu.demo01;

import org.junit.*;

import java.io.*;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

/**
 * Billkin
 * 2023/6/4
 */
public class Test_Properties {
    public static void main(String[] args) throws Exception {
        /*testProperties();
        testQuery("select * from admin",Admin.class);*/
        /*testCreateAndAdd();
        testQuery("Select * from examstudent",ExamStudent.class);*/
        //testSelect();
        //testDelete();
    }
    public static void testProperties() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println(sc.nextLine());
        Connection conn = JDBCUtils.getConnection();
        String sql = "insert into admin(user_name,pwd) values(?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        /*ps.setObject(1,"林祎凯");
        ps.setObject(2,"pp");*/
        for (int i = 0; i < 2; i++) {
            System.out.println("输入用户名:");
            String name = sc.next();
            ps.setObject(1,name);
            System.out.println("请输入密码:");
            String pwd = sc.next();
            ps.setObject(2,pwd);
        }
        ps.execute();
        System.out.println("添加结束");

        ps.close();
        conn.close();
    }
    public static void testCreateAndAdd() throws Exception{

        Connection conn = JDBCUtils.getConnection();
        String sql = "create table if not EXISTS examstudent(\n" +
                "FlowID int(10),\n" +
                "`Type` int(5),\n" +
                "IDCard VARCHAR(18),\n" +
                "ExamCard varchar(15),\n" +
                "StudentName varchar(20),\n" +
                "Location varchar(20),\n" +
                "Grade int(10)\n" +
                ")CHARACTER set 'utf8'";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.execute();
        ps.close();
        sql = "insert into examstudent values(?,?,?,?,?,?,?)";
        PreparedStatement ps2 = conn.prepareStatement(sql);
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入人数:");
        int num = sc.nextInt();
        for (int i = 1; i <= num; i++) {
            System.out.println("请输入流水号:");
            ps2.setObject(1,sc.nextInt());

            System.out.println("请输入考试类型(4/6):");
            ps2.setObject(2, sc.nextInt());

            System.out.println("请输入身份证号码:");
            ps2.setObject(3,sc.next());

            System.out.println("请输入准考证号码:");
            ps2.setObject(4, sc.next());

            System.out.println("请输入学生姓名:");
            String name = sc.next();
            ps2.setObject(5, name );

            System.out.println("请输入区域:");
            ps2.setObject(6,sc.next());

            System.out.println("请输入成绩:");
            ps2.setObject(7, sc.nextInt());

            ps2.execute();
            System.out.println(name+"信息添加成功");
        }

    }
    public static void testSelect() throws Exception {
        String sql = "Select * from examstudent where ";
        Connection conn = JDBCUtils.getConnection();
        Scanner sc = new Scanner(System.in);
        System.out.println("请选择你要输入的类型:");
        System.out.println("a:准考证号");
        System.out.println("b:身份证号");
        String c = sc.next();
        switch(c){
            case "a":
                System.out.println("请输入准考证号:");
                String ec = sc.next();
                sql=sql+"ExamCard"+" = ?";
                List<ExamStudent> students = show(ExamStudent.class, sql, ec);
                students.forEach(System.out::println);
                break;
            case "b":
                System.out.println("请输入身份证号:");
                String ic = sc.next();
                sql=sql+"IDCard"+" = ?";
                List<ExamStudent> students2 = show(ExamStudent.class, sql, ic);
                students2.forEach(System.out::println);
                break;
            default:
                System.out.println("输入有误!请重新进入程序");

        }
    }
    public static void testDelete() throws Exception{
        Connection conn = JDBCUtils.getConnection();
        String sql = "delete from examstudent where ExamCard = ? ";
        PreparedStatement ps = conn.prepareStatement(sql);
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入学生的考号:");

        while(true){
            String ec = sc.next();
            ps.setObject(1,ec);
            int i = ps.executeUpdate();
            if (i==1){
                System.out.println("删除成功");
                break;
            }
            else System.out.println("查无此人,请重新输入!");
        }
    }
    public static <T> void  testQuery(String sql,Class<T> clazz,Object... args) throws Exception {
        //System.out.println("---------------获取admin表的数据--------------------");
        //String sql = "select * from admin";
        List<T> tList = show(clazz, sql,args);
        //for (Admin admin : admins) System.out.println(admin);
        for (T t : tList) System.out.println(t);

    }
    //获取表中数据
    public static <T> List<T> show(Class<T> clazz, String sql, Object... args) throws Exception {
        ArrayList<T> list = new ArrayList<>();
        //获取连接
        Connection conn = JDBCUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i+1,args[i]);
        }
        ResultSet rs = ps.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        while(rs.next()){
            T t = clazz.newInstance();
            for (int i = 0; i < count; i++) {
                Object columnValue = rs.getObject(i + 1);
                String columnLabel = rsmd.getColumnLabel(i + 1);
                if (columnValue!=null) {
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t,columnValue);
                }
            }
            list.add(t);
        }
        return list;
    }
}

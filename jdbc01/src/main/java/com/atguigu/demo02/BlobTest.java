package com.atguigu.demo02;

import com.atguigu.demo01.JDBCUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;

/**
 * Billkin
 * 2023/6/5
 * 使用PreparedStatement操作Blob类型的数据
 * TinyBlob 最大 255
 * Blob 最大 65K
 * MediumBlob 最大 16M 在使用中,5.7默认使用的4M,8.0默认使用的是 8M ,可以到my.ini中添加一行数据:max_allowed_packet=16M
 * LongBlob 最大 4G
 */
public class BlobTest {
    public static void main(String[] args) throws Exception {
        //testInsert();
        //testQuery();
    }
    //向数据表中插入Blob类型的字段
    public static void testInsert() throws Exception{
        Connection conn = JDBCUtils.getConnection();
        //String sql = "insert into admin (user_name,pwd,photo) values(?,?,?)";
        String sql = "update admin set photo = ? where user_name = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        /*ps.setObject(1,"汪始慧");
        ps.setObject(2,"mix");*/
        InputStream is = new FileInputStream(new File("E:\\以你的心诠释我的爱壁纸.zip"));
        ps.setBlob(1,is);
        ps.setObject(2,"Billkin");
        int i = ps.executeUpdate();
        if (i==1) System.out.println("添加成功");
        else System.out.println("添加失败");
        is.close();
        ps.close();
        conn.close();

    }
    //查询数据表中的Blob类型的字段
    public static void testQuery() throws Exception{
        Connection conn = JDBCUtils.getConnection();
        String sql = "select * from admin where user_name = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setObject(1,"马群耀");
        ResultSet rs = ps.executeQuery();
        //获取元数据
        ResultSetMetaData rsmd = rs.getMetaData();
        //获取每个列值和列名
        while(rs.next()){
            Admin admin = new Admin();
            for (int i = 1; i <= 3; i++) {
                //获取列值
                Object columnValue = rs.getObject(i);
                //获取列名
                String columnLabel = rsmd.getColumnLabel(i);
                //通过反射设置属性值
                Field field = Admin.class.getDeclaredField(columnLabel);
                field.setAccessible(true);
                field.set(admin,columnValue);
            }
            System.out.println(admin);
            //将Blob类型的数据下载下来,以文件的方式保存在本地
            Blob photo = rs.getBlob("photo");
            InputStream is = photo.getBinaryStream();
            //创建文件输出流
            FileOutputStream out = new FileOutputStream(new File("马群耀.jpg"));
            //循环读写数据
            byte[] bytes = new byte[1024];
            int i = 0;
            while((i=is.read(bytes))!=-1){
                out.write(bytes,0,i);
            }
        }
        System.out.println("成功");
    }
}

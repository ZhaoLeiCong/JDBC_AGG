
package com.atguigu.demo04DAO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Billkin
 * 2023/6/4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    private int id;
    private String user_name;
    private Date birth;
    private String pwd;
    private Double deposit;

}


package com.atguigu.demo03;

import java.util.Objects;
import lombok.*;
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
    private String pwd;
    private Double deposit;
}

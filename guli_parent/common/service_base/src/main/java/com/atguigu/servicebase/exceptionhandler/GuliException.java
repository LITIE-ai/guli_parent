package com.atguigu.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: litie
 * @Date: 2021/11/10/11:09
 */
@Data
@AllArgsConstructor//生成有参构造方法
@NoArgsConstructor//生成无参构造方法
public class GuliException extends RuntimeException {

    private Integer code;//状态码

    private String msg;//异常信息

}

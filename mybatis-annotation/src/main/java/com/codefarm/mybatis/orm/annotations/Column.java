package com.codefarm.mybatis.orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.ibatis.type.JdbcType;

/**
 * 数据库列配置
 * @author zhangjian
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column
{
    
    String name() default "";
    
    boolean sysdate() default false;
    
    String test() default "";
    
    JdbcType jdbcType();
    
}

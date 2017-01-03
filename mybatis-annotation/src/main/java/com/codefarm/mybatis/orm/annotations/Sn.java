package com.codefarm.mybatis.orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.codefarm.mybatis.dialect.Dialect;

/**
 * 序列号注释
 * @author zhangjian
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Sn
{
    Dialect.DialectName dialect() default Dialect.DialectName.MYSQL;
    
    int step() default 1;
    
    String table() default "T_SN";
    
    String stub() default "F_SN_NAME";
    
    String stubValue() default "DEFAULT";
    
    String sn() default "F_SN_NUMBER";
    
    String stubValueProperty() default "";
    
    String pattern() default "";
    
    boolean appendStubValue() default true;
}

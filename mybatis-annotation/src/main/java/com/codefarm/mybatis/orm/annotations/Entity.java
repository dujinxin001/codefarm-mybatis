package com.codefarm.mybatis.orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 实体注释
 * @author zhangjian
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Entity
{
    Class<?> mapper() default Void.class;
    
    boolean useCache() default true;
    
    boolean flushCache() default false;
    
    int fetchSize() default -1;
    
    int timeout() default -1;
}

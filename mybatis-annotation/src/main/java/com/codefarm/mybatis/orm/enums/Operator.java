package com.codefarm.mybatis.orm.enums;

/**
 * 数据库常用操作符
 * @author zhangjian
 *
 */
public enum Operator
{
    EQUAL("="), GREATER(">"), LESS("<"), GREATERANDEQUAL(">="), LESSANDEQUAL(
            "<="), LIKE("like"),NOTEQUAL("<>"),OR("or");
    private String operator;
    
    private Operator(String operator)
    {
        this.operator = operator;
    }
    
    public String getOperator()
    {
        return operator;
    }
    
}

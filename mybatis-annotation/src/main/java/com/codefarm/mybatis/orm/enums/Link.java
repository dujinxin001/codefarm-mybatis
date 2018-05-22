package com.codefarm.mybatis.orm.enums;

/**
 * 数据库常用操作符
 * @author zhangjian
 *
 */
public enum Link
{
    AND("and"),OR("or");
    private String link;
    
    private Link(String link)
    {
        this.link = link;
    }
    
    public String getLink()
    {
        return link;
    }
    
}

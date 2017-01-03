package com.codefarm.mybatis.orm.annotations;

/**
 * 主键生成策略
 * @author zhangjian
 *
 */
public enum GenerationType
{
    AUTO, IDENTITY, SEQUENCE, TABLE, UUID;
}

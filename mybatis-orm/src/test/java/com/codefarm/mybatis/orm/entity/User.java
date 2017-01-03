package com.codefarm.mybatis.orm.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.JdbcType;

import com.codefarm.mybatis.orm.annotations.Column;
import com.codefarm.mybatis.orm.annotations.Entity;
import com.codefarm.mybatis.orm.annotations.Id;
import com.codefarm.mybatis.orm.annotations.Table;
import com.codefarm.mybatis.orm.annotations.Version;
import com.codefarm.mybatis.orm.mapper.UserMapper;
import com.codefarm.mybatis.pagination.Pagable;

@Entity(mapper = UserMapper.class)
@Table(name = "user")
public class User extends Pagable implements Serializable
{
    
    private static final long serialVersionUID = 6275980778279891698L;
    
    @Id(column = "id", generatedKeys = true, jdbcType = JdbcType.BIGINT)
    private Long userId;
    
    @Column(name = "username", jdbcType = JdbcType.VARCHAR)
    private String userName;
    
    @Column(name = "birthday", jdbcType = JdbcType.DATE)
    private Date birthDay;
    
    @Column(name = "created", jdbcType = JdbcType.TIMESTAMP)
    private Date createAt;
    
    @Version()
    @Column(name = "version", jdbcType = JdbcType.INTEGER)
    private Integer version = 0;
    
    public Long getUserId()
    {
        return userId;
    }
    
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }
    
    public String getUserName()
    {
        return userName;
    }
    
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    
    public Date getBirthDay()
    {
        return birthDay;
    }
    
    public void setBirthDay(Date birthDay)
    {
        this.birthDay = birthDay;
    }
    
    public Date getCreateAt()
    {
        return createAt;
    }
    
    public void setCreateAt(Date createAt)
    {
        this.createAt = createAt;
    }
    
    @Override
    public String toString()
    {
        return "User [userId=" + userId + ", userName=" + userName
                + ", birthDay=" + birthDay + ", createAt=" + createAt + "]";
    }
    
    public Integer getVersion()
    {
        return version;
    }
    
    public void setVersion(Integer version)
    {
        this.version = version;
    }
    
}

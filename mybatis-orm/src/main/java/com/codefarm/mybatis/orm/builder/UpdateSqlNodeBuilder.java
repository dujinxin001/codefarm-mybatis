package com.codefarm.mybatis.orm.builder;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.scripting.xmltags.IfSqlNode;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SetSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;
import org.apache.ibatis.session.Configuration;

import com.codefarm.mybatis.orm.annotations.Column;
import com.codefarm.mybatis.orm.annotations.Criteria;
import com.codefarm.mybatis.orm.annotations.Criterias;
import com.codefarm.mybatis.orm.annotations.Version;

/**
 * 更新sql构造器
 * @author zhangjian
 *
 */
public class UpdateSqlNodeBuilder extends SqlNodeBuilder
{
    
    @Override
    public SqlNode build(Configuration configuration, MappedMethod method)
    {
        List<SqlNode> contents = new ArrayList<SqlNode>();
        contents.add(new TextSqlNode("UPDATE " + method.getTable() + " "));
        contents.add(getUpdateColumns(configuration, method));
        //---------默认根据主键更新----------//
        if (method.getMethod().getParameters().length == 1)
            contents.add(new TextSqlNode(
                    " WHERE " + getColumnNameByField(method.getIdField())
                            + " = #{" + method.getIdField().getName() + "}"));
        else
            contents.add(
                    new CriteriaSqlNodeBuilder().build(configuration, method));
        if (method.getVersionField() != null)
        {
            contents.add(buildVersionSql(method));
        }
        return new MixedSqlNode(contents);
    }
    
    private SqlNode buildVersionSql(MappedMethod method)
    {
        String prefix = "";
        Parameter[] parameters = method.getMethod().getParameters();
        if (parameters.length > 1)
        {
            prefix = PARAM + findNewObjectIndex(parameters) + ".";
        }
        
        return new IfSqlNode(new TextSqlNode(getVersionSQL(prefix, method)),
                buildIfTest(prefix, method.getVersionField()));
    }
    
    private int findNewObjectIndex(Parameter[] parameters)
    {
        int index = 1;
        for (Parameter parameter : parameters)
        {
            if (!parameter.isAnnotationPresent(Criteria.class) && !parameter
                    .getType().isAnnotationPresent(Criterias.class))
                return index;
            index++;
        }
        return index;
    }
    
    /**
     * 获取更新实体在参数列表中的索引
     * @param method
     * @return
     */
    private String getObjectIndex(MappedMethod method)
    {
        String prefix = "";
        Parameter[] parameters = method.getMethod().getParameters();
        
        if (parameters.length > 1)
        {
            int index = 1;
            for (Parameter parameter : parameters)
            {
                if (parameter.getType().equals(method.getEntityType()))
                    prefix = PARAM + index + ".";
                index++;
            }
        }
        return prefix;
    }
    
    private SqlNode getUpdateColumns(Configuration configuration,
            MappedMethod method)
    {
        List<SqlNode> contents = new ArrayList<SqlNode>();
        StringBuilder sb = new StringBuilder();
        String prefix = getObjectIndex(method);
        for (Field field : method.getColumnFields())
        {
            sb.delete(0, sb.length());
            if (field.equals(method.getIdField()))
                continue;
            
            if (Date.class.isAssignableFrom(field.getType())
                    && field.getAnnotation(Column.class) != null
                    && field.getAnnotation(Column.class).sysdate() == true)
            {
                sb.append(prefix);
                sb.append(getColumnNameByField(field));
                sb.append(" = now(),");
            }
            else if (!field.isAnnotationPresent(Version.class))
            {
                sb.append(getColumnNameByField(field));
                sb.append(" = #{");
                sb.append(prefix);
                sb.append(field.getName());
                sb.append(",jdbcType=");
                sb.append(field.getAnnotation(Column.class).jdbcType());
                sb.append("},");
            }
            contents.add(new IfSqlNode(new TextSqlNode(sb.toString()),
                    buildIfTest(prefix, field)));
        }
        if (method.getVersionField() != null)
        {
            contents.add(new TextSqlNode(
                    getColumnNameByField(method.getVersionField()) + "="
                            + getColumnNameByField(method.getVersionField())
                            + "+1"));
        }
        
        return new SetSqlNode(configuration, new MixedSqlNode(contents));
    }
    
    private String getVersionSQL(String prefix, MappedMethod method)
    {
        Field versionField = method.getVersionField();
        if (versionField != null)
        {
            return " AND " + getColumnNameByField(versionField) + " = #{"
                    + prefix + versionField.getName() + "}";
        }
        return StringUtils.EMPTY;
    }
    
}

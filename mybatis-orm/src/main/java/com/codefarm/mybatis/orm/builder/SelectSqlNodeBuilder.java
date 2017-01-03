package com.codefarm.mybatis.orm.builder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;
import org.apache.ibatis.scripting.xmltags.TrimSqlNode;
import org.apache.ibatis.session.Configuration;

/**
 * 查询sql构造器
 * 
 * @author zhangjian
 *
 */
public class SelectSqlNodeBuilder extends SqlNodeBuilder {

	@Override
	public SqlNode build(Configuration configuration, MappedMethod method) {
		List<SqlNode> contents = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		// String sql = "SELECT " + getIdColumnName() + " AS " +
		// getIdFieldName();

		for (Field field : method.getColumnFields()) {
			sb.append(getColumnNameByField(field));
			sb.append(" AS ");
			sb.append(field.getName());
			sb.append(",");
		}
		contents.add(new TrimSqlNode(configuration, new TextSqlNode(sb.toString()), null, null, null, ","));
		sb.delete(0, sb.length());
		sb.append(" FROM ");
		sb.append(method.getTable());
		contents.add(new TextSqlNode(sb.toString()));
		contents.add(new TrimSqlNode(configuration, new CriteriaSqlNodeBuilder().build(configuration, method), null,
				null, null, "AND"));
		SqlNode orderbys = new OrderBySqlNodeBuilder().build(configuration, method);
		contents.add(orderbys);
		return new MixedSqlNode(contents);
	}

}

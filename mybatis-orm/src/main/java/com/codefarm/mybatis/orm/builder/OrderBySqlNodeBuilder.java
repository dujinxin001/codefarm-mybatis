package com.codefarm.mybatis.orm.builder;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;
import org.apache.ibatis.session.Configuration;

import com.codefarm.mybatis.orm.annotations.OrderBy;
import com.codefarm.mybatis.orm.annotations.Select;

public class OrderBySqlNodeBuilder extends SqlNodeBuilder {

	private static final String ORDER_BY = " order by ";

	@Override
	public SqlNode build(Configuration configuration, MappedMethod method) {
		List<SqlNode> contents = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		sb.append(ORDER_BY);
		Select select = method.getMethod().getAnnotation(Select.class);
		if (com.codefarm.spring.modules.util.StringUtils.isNotEmpty(select.orderby())) {
			sb.append(select.orderby());
			sb.append(",");
			// contents.add(new TextSqlNode(sb.toString()));
		}
		Parameter[] parameters = method.getMethod().getParameters();
		int index = 0;
		for (Parameter parameter : parameters) {
			if (parameter.isAnnotationPresent(OrderBy.class)) {
				sb.append("${");
				sb.append(index);
				sb.append("},");
			}
		}

		if (sb.length() > ORDER_BY.length())
			contents.add(new TextSqlNode(sb.substring(0, sb.length() - 1)));
		return new MixedSqlNode(contents);
	}

}

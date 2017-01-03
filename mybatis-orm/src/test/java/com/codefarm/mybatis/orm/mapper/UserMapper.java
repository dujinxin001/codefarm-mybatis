package com.codefarm.mybatis.orm.mapper;

import java.util.List;
import java.util.Set;

import com.codefarm.mybatis.orm.annotations.Criteria;
import com.codefarm.mybatis.orm.annotations.Delete;
import com.codefarm.mybatis.orm.annotations.Insert;
import com.codefarm.mybatis.orm.annotations.Select;
import com.codefarm.mybatis.orm.annotations.Update;
import com.codefarm.mybatis.orm.criteria.UserCriterias;
import com.codefarm.mybatis.orm.entity.User;

/**
 * 注意接口方法名称必须唯一，用作MappedStatementId
 * 
 * @author zhangjian
 *
 */
public interface UserMapper {

	@Insert
	int insertUser(User user);

	@Insert
	int insert(User user);

	@Insert
	int insertUserList(List<User> users);

	@Insert
	int insertUserArray(User[] users);

	@Update
	int updateSingle(User user);

	@Update
	int update(@Criteria(column = "username") String username, User newUser);

	@Update
	int updateUser(User user);

	@Update
	int updateUsers(User newUser, UserCriterias criterias);

	@Select(orderby = "id desc")
	User getById(@Criteria(column = "id") Long i);

	@Select
	User select(@Criteria(column = "id") Long id, @Criteria(column = "username") String username);

	@Select
	List<User> selectByCriteriasAndUsername(UserCriterias criterias, @Criteria(column = "username") String username);

	@Select(orderby = "id desc")
	List<User> selectByIds(@Criteria(column = "id") Long[] ids);

	@Select
	List<User> selectByIdList(@Criteria(column = "id") List<Long> ids);

	@Select
	List<User> selectByIdSet(@Criteria(column = "id") Set<Long> ids);

	@Select
	List<User> selectByCriterias(UserCriterias criterias);

	@Delete
	int delete(@Criteria(column = "id") Long id);

	@Delete
	int deleteByIdAndName(@Criteria(column = "id") Long id, @Criteria(column = "username") String name);

	@Delete
	int deleteByCriterias(UserCriterias criterias);

	@Delete
	int deleteByCriteriasAndUserName(UserCriterias criterias, @Criteria(column = "username") String username);

	@Select(orderby = "username desc")
	List<User> selectAndOrderBy(@Criteria(column = "username") String username);

}

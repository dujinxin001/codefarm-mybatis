package com.codefarm.mybatis.orm.unit;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.codefarm.mybatis.orm.criteria.UserCriterias;
import com.codefarm.mybatis.orm.entity.User;
import com.codefarm.mybatis.orm.mapper.UserMapper;
import com.codefarm.spring.modules.util.Identities;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ActiveProfiles("test")
public class UserMapperTest {

	@Autowired(required = true)
	private UserMapper userMapper;

	private static User buildUser() {
		User user = new User();
		user.setUserName(Identities.randomBase62(15));
		user.setCreateAt(new Date());
		return user;
	}

	
	public void testSelectOrderby() {
		// userMapper.selectByCriterias(new UserCriterias());
		// userMapper.selectAndOrderBy("");
		UserCriterias criterias = new UserCriterias();
		criterias.setCreated(new Date());
		criterias.setUserIds(new Long[] { 90013l });
		// criterias.setOrderby("id asc,username desc");
		userMapper.selectByCriterias(criterias);
		// userMapper.selectAndOrderBys("id asc", "", "");
	}

	@Transactional
	public void testInserts() {
		// ----单条插入----//
		userMapper.insertUser(buildUser());
		userMapper.insert(buildUser());
		long startMills = new Date().getTime();
		for (int i = 0; i < 10000; i++) {
			userMapper.insertUser(buildUser());
		}
		long endMills = new Date().getTime();
		System.out.println("Sigle insert 10000 records,Total time used:" + (endMills - startMills) / 1000 + " s");
		// Total time used:277 s
		// ----批量插入----//
		startMills = new Date().getTime();
		for (int i = 0; i < 100; i++) {
			userMapper.insertUserList(buildUserList());
		}
		endMills = new Date().getTime();
		System.out.println("Batch insert 10000 records by 100 records per batch,Total time used:"
				+ (endMills - startMills) / 1000 + " s");
		// Total time used:4 s

		startMills = new Date().getTime();
		for (int i = 0; i < 50; i++) {
			userMapper.insertUserArray(buildUserArray());
		}
		endMills = new Date().getTime();
		System.out.println("Batch insert 10000 records by 200 records per batch,Total time used:"
				+ (endMills - startMills) / 1000 + " s");
		// Total time used:3 s

	}

	@Transactional(readOnly = true)
	public void testSelects() {
		User user = userMapper.getById(90012l);
		System.out.println(user.toString());
		user = userMapper.select(90012l, "waCHzpZY8Y0bcOQ");
		System.out.println(user.toString());
		user = userMapper.select(90012l, "waCHzpZY8Y0bcOQ123");
		Assert.assertNull(user);
		// ----in 查询----//
		Long[] ids = new Long[3];
		ids[0] = 90013l;
		ids[1] = 90014l;
		ids[2] = 80015l;
		List<User> users = userMapper.selectByIds(ids);
		Assert.assertTrue(users.size() == 2);
		List<Long> ids2 = new ArrayList<>();
		ids2.add(90013l);
		ids2.add(90013l);
		ids2.add(80015l);
		users = userMapper.selectByIdList(ids2);
		Assert.assertTrue(users.size() == 1);
		Set<Long> idSet = new HashSet<>();
		idSet.addAll(ids2);
		users = userMapper.selectByIdSet(idSet);
		Assert.assertTrue(users.size() == 1);
		// ----组合条件查询----//
		UserCriterias criterias = new UserCriterias();
		criterias.setCreated(new Date());
		criterias.setUserIds(new Long[] { 90013l });
		users = userMapper.selectByCriterias(criterias);
		Assert.assertTrue(users.size() == 1);
		criterias = new UserCriterias();
		criterias.setUserid(90013l);
		users = userMapper.selectByCriteriasAndUsername(criterias, "rwCeix01DrP3oxR");
		Assert.assertTrue(users.size() == 1);
	}

	public void testPageSelect() {
		UserCriterias criterias = new UserCriterias();
		// criterias.setCreated(new Date());
		// criterias.setUserIds(new Long[] { 90013l });
		criterias.setShowCount(10);
		criterias.setCurrentPage(1);
		criterias.setPagable(true);
		List<User> selectByCriterias = userMapper.selectByCriterias(criterias);
		Assert.assertTrue(selectByCriterias.size() == 10);
	}

	@Transactional
	public void testUpdates() {
		User user = userMapper.getById(150013l);
		user.setBirthDay(new Date());
		int rows = userMapper.updateSingle(user);
		Assert.assertTrue(rows == 1);
		user.setCreateAt(new Date());
		user.setBirthDay(null);
		userMapper.updateUser(user);
		Assert.assertTrue(rows == 1);
		UserCriterias criterias = new UserCriterias();
		criterias.setUserid(150013l);
		user.setUserName("ABC");
		userMapper.updateUsers(user, criterias);
	}

	@Transactional
	public void testDeletes() {
		int rows = userMapper.delete(90013l);
		Assert.assertTrue(rows == 1);
		rows = userMapper.deleteByIdAndName(90014l, "123");
		Assert.assertTrue(rows == 0);
		rows = userMapper.deleteByIdAndName(90014l, "ABC");
		Assert.assertTrue(rows == 1);
		UserCriterias criterias = new UserCriterias();
		criterias.setUserid(120011l);
		rows = userMapper.deleteByCriterias(criterias);
		Assert.assertTrue(rows == 2);
		rows = userMapper.deleteByCriteriasAndUserName(criterias, "ABC");
		Assert.assertTrue(rows == 0);
	}
	@Test
	public void testPageWithIn(){
		UserCriterias uc=new UserCriterias();
		Long [] ids=new Long[]{1111l,22222l,33333l};
		String [] names=new String[]{"a","b"};
		uc.setUserIds(ids);
		uc.setUserNames(names);
		uc.setUserid(44444l);
		uc.setPagable(true);
		userMapper.selectByCriterias(uc);
	}

	private List<User> buildUserList() {
		List<User> ret = new ArrayList<>();
		for (int i = 0; i < 100; i++)
			ret.add(buildUser());
		return ret;
	}

	private User[] buildUserArray() {
		User[] users = new User[200];
		for (int i = 0; i < 200; i++)
			users[i] = buildUser();
		return users;
	}

}

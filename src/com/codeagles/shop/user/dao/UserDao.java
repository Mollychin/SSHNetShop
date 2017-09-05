/**
 * 
 */
package com.codeagles.shop.user.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.codeagles.shop.user.vo.User;

public class UserDao extends HibernateDaoSupport {
	// �����Ʋ�ѯ������Ƿ���ڸ��û�
	public User findByUsername(String username) {
		String hql = "from User where username = ?";
		// String hql = "from User";
		List<User> list = this.getHibernateTemplate().find(hql, username);
		// List<User> list = this.getHibernateTemplate().find(hql);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * @param user
	 */
	public void save(User user) {
		this.getHibernateTemplate().save(user);

	}

	/**
	 * @param code
	 * @return
	 */
	public User findByCode(String code) {
		String hql = "from User where code=?";
		List<User> list = this.getHibernateTemplate().find(hql, code);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * @param exsitUser
	 *            �޸��û�״̬
	 */
	public void update(User exsitUser) {
		this.getHibernateTemplate().update(exsitUser);
	}

	/**
	 * @param user
	 *            �û���¼��DAO
	 */
	public User login(User user) {
		String hql = "from User " + "where username=? and password=? and state=?";
		List<User> list = this.getHibernateTemplate().find(hql, user.getUsername(), user.getPassword(), 1);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}

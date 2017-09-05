/**
 * 
 */
package com.codeagles.shop.user.service;

import org.springframework.transaction.annotation.Transactional;

import com.codeagles.shop.user.dao.UserDao;
import com.codeagles.shop.user.vo.User;
import com.codeagles.util.MailUitls;
import com.codeagles.util.UUIDUtils;

@Transactional
public class UserService {
	private UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	// ����û��Ƿ����
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	/**
	 * ҵ��㣬���ע��
	 */
	public void save(User user) {
		user.setState(0);// 0:δ���� 1���Ѽ���
		String code = UUIDUtils.getUUID() + UUIDUtils.getUUID();// 32+32=64
		user.setCode(code);
		userDao.save(user);
		// ���ͼ����ʼ�
		MailUitls.sendMail(user.getEmail(), code);
	}

	/**
	 * @param code
	 *            �������ѯ�û�
	 */
	public User findByCode(String code) {

		return userDao.findByCode(code);
	}

	/**
	 * @param exsitUser
	 *            �޸��û�״̬
	 */
	public void update(User exsitUser) {
		userDao.update(exsitUser);
	}

	/**
	 * @param user
	 *            �û���¼
	 */
	public User login(User user) {

		return userDao.login(user);
	}

}

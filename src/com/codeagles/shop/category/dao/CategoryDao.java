/**
 * 
 */
package com.codeagles.shop.category.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.codeagles.shop.category.vo.Category;

@Transactional
public class CategoryDao extends HibernateDaoSupport {

	/**
	 * @return
	 */
	public List<Category> findAll() {
		// ��ѯ����һ������ķ���
		String hql = "from Category";
		List<Category> list = this.getHibernateTemplate().find(hql);
		return list;
	}

	/**
	 * ���һ������
	 */
	public void save(Category category) {
		this.getHibernateTemplate().save(category);
	}

	public Category findByCid(Integer cid) {

		return this.getHibernateTemplate().get(Category.class, cid);
	}

	/**
	 * @param category
	 */
	public void delete(Category category) {
		this.getHibernateTemplate().delete(category);
	}

	/**
	 * @param category
	 */
	public void update(Category category) {
		this.getHibernateTemplate().update(category);

	}

}

/**
 * 
 */
package com.codeagles.shop.category.service;

import java.util.List;

import com.codeagles.shop.category.dao.CategoryDao;
import com.codeagles.shop.category.vo.Category;

public class CategoryService {
	private CategoryDao categoryDao;

	public CategoryDao getCategoryDao() {
		return categoryDao;
	}

	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	/**
	 * ��ѯ����һ������ķ���ʵ��
	 */
	public List<Category> findAll() {

		return categoryDao.findAll();
	}

	/**
	 * ���һ������ķ���
	 */
	public void save(Category category) {
		categoryDao.save(category);

	}

	/**
	 * @param cid
	 * @return
	 */
	public Category findByCid(Integer cid) {
		return categoryDao.findByCid(cid);
	}

	/**
	 * @param category
	 */
	public void delete(Category category) {
		categoryDao.delete(category);

	}

	/**
	 * @param category
	 */
	public void update(Category category) {
		categoryDao.update(category);
	}

}

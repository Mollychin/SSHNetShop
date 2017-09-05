/**
 * 
 */
package com.codeagles.shop.categorysecond.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.codeagles.shop.categorysecond.dao.CategorySecondDao;
import com.codeagles.shop.categorysecond.vo.CategorySecond;
import com.codeagles.util.PageBean;

@Transactional
public class CategorySecondService {
	private CategorySecondDao categorySecondDao = new CategorySecondDao();

	public void setCategorySecondDao(CategorySecondDao categorySecondDao) {
		this.categorySecondDao = categorySecondDao;
	}

	/**
	 * @param page
	 * @return
	 */
	public PageBean<CategorySecond> findByPage(Integer page) {
		PageBean<CategorySecond> pageBean = new PageBean<CategorySecond>();
		// ���õ�ǰҳ��
		pageBean.setPage(page);
		// ����ÿҳ�ļ�¼��
		int limit = 10;
		pageBean.setLimit(limit);
		// �����ܼ�¼��
		int totalCount = 0;
		totalCount = categorySecondDao.findCountCid();
		pageBean.setTotalCount(totalCount);
		// ������ҳ��
		int totalPage = 0;
		// Math.ceil(totalCount/limit);
		if (totalCount % limit == 0) {
			totalPage = totalCount / limit;
		} else {
			totalPage = totalCount / limit + 1;
		}
		pageBean.setTotalPage(totalPage);
		// ÿҳ��ʾ�����ݼ���
		int begin = (page - 1) * limit;
		List<CategorySecond> list = categorySecondDao.findByPageCid(begin, limit);
		pageBean.setList(list);
		return pageBean;
	}

	/**
	 * @param categorySecond
	 */
	public void save(CategorySecond categorySecond) {
		categorySecondDao.save(categorySecond);

	}

	/**
	 * @param csid
	 * @return
	 */
	public CategorySecond findByCsid(Integer csid) {
		return categorySecondDao.findByCsid(csid);
	}

	/**
	 * @param categorySecond
	 */
	public void delete(CategorySecond categorySecond) {
		categorySecondDao.delete(categorySecond);
	}

	/**
	 * @param categorySecond
	 */
	public void update(CategorySecond categorySecond) {
		categorySecondDao.update(categorySecond);

	}

	/**
	 * ��ѯ��������ķ���
	 */
	public List<CategorySecond> findAll() {
		return categorySecondDao.findAll();
	}

}

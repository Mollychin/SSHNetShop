/**
 * 
 */
package com.codeagles.shop.product.service;

import java.util.List;

import com.codeagles.shop.product.dao.ProductDao;
import com.codeagles.shop.product.vo.Product;
import com.codeagles.util.PageBean;

public class ProductService {
	// ע��Dao
	private ProductDao productDao;

	public ProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	/**
	 * ��ҳ��������Ʒ��ѯ
	 */
	public List<Product> findHot() {
		return productDao.findHot();
	}

	/**
	 * ��ѯ������Ʒ
	 */
	public List<Product> findNewProduct() {
		return productDao.findNewProduct();
	}

	/**
	 * @param pid
	 *            ������Ʒ��Id��ѯ��Ʒ
	 */
	public Product findByPid(Integer pid) {
		return productDao.findByPid(pid);
	}

	/**
	 * @param cid
	 * @param page
	 *            ��ѯһ�������cid���з�ҳ��ѯ����Ʒ
	 */
	public PageBean<Product> findByPageCid(Integer cid, int page) {
		PageBean<Product> pageBean = new PageBean<Product>();
		// ���õ�ǰҳ��
		pageBean.setPage(page);
		// ����ÿҳ�ļ�¼��
		int limit = 8;
		pageBean.setLimit(limit);
		// �����ܼ�¼��
		int totalCount = 0;
		totalCount = productDao.findCountCid(cid);
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
		List<Product> list = productDao.findByPageCid(cid, begin, limit);
		pageBean.setList(list);
		return pageBean;
	}

	/**
	 * @param csid
	 * @param page
	 *            ��ѯ����������Ʒ
	 */
	public PageBean<Product> findByPageCsid(Integer csid, int page) {
		PageBean<Product> pageBean = new PageBean<Product>();
		// ���õ�ǰҳ��
		pageBean.setPage(page);
		// ����ÿҳ�ļ�¼��
		int limit = 8;
		pageBean.setLimit(limit);
		// �����ܼ�¼��
		int totalCount = 0;
		totalCount = productDao.findCountCsid(csid);
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
		List<Product> list = productDao.findByPageCsid(csid, begin, limit);
		pageBean.setList(list);
		return pageBean;
	}

	/**
	 * @param page
	 * @return
	 */
	public PageBean<Product> findByPage(Integer page) {
		PageBean<Product> pageBean = new PageBean<Product>();
		// ���õ�ǰҳ��
		pageBean.setPage(page);
		// ����ÿҳ�ļ�¼��
		int limit = 10;
		pageBean.setLimit(limit);
		// �����ܼ�¼��
		int totalCount = 0;
		totalCount = productDao.findCount();
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
		List<Product> list = productDao.findByPage(begin, limit);
		pageBean.setList(list);
		return pageBean;
	}

	/**
	 * ������Ʒ
	 */
	public void save(Product product) {
		productDao.save(product);

	}

	/**
	 * ɾ����Ʒ
	 */
	public void delete(Product product) {
		productDao.delete(product);

	}

	/**
	 * �޸�
	 */
	public void update(Product product) {
		productDao.update(product);

	}

}

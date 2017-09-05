/**
 * 
 */
package com.codeagles.shop.product.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.codeagles.shop.product.vo.Product;
import com.codeagles.util.PageHibernateCallback;

@Transactional
public class ProductDao extends HibernateDaoSupport {

	/**
	 * ��ҳ�ϵ�������Ʒ��ѯ
	 */
	public List<Product> findHot() {

		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		// ��ѯ������Ʒ 0���ȣ�1Ϊ����
		criteria.add(Restrictions.eq("is_hot", 1));
		// ����������ʾ
		criteria.addOrder(Order.desc("pdate"));
		// ��ѯ
		List<Product> hlist = this.getHibernateTemplate().findByCriteria(criteria, 0, 10);
		return hlist;
	}

	/**
	 * ��ѯ������Ʒ
	 */
	public List<Product> findNewProduct() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		// ����������ʾ ��������ʾ
		criteria.addOrder(Order.desc("pdate"));
		// ��ѯ
		List<Product> nlist = this.getHibernateTemplate().findByCriteria(criteria, 0, 10);
		return nlist;
	}

	/**
	 * @param pid
	 * @return
	 */
	public Product findByPid(Integer pid) {
		return this.getHibernateTemplate().get(Product.class, pid);

	}

	/**
	 * @param cid
	 *            ���ݷ���id��ѯ��Ʒ���� ���ݶ��������ѯ��Ʒ����
	 */
	public int findCountCid(Integer cid) {
		String hql = "select count(*) from Product p where p.categorySecond.category.cid = ?";
		List<Long> list = this.getHibernateTemplate().find(hql, cid);
		if (list != null && list.size() > 0) {
			return list.get(0).intValue();
		}
		return 0;
	}

	/**
	 * @param cid
	 * @param begin
	 * @param limit
	 *            ���ݷ���id��ѯ��Ʒ���� ���ݶ��������ѯ��Ʒ��Ϣ
	 */
	public List<Product> findByPageCid(Integer cid, int begin, int limit) {
		// String sql="select * from category c,categorysecond cs,product p "
		// + "where c.cid=cs.cid and p.csid=cs.csid and c.cid=1";
		String hql = "select p from Product p join p.categorySecond cs join cs.category c where c.cid=?";
		// String hql =
		// "select p from Product p join p.categorySecond cs where cs.csid = ?";
		// ��ҳ������������ѯ��
		List<Product> list = this.getHibernateTemplate()
				.execute(new PageHibernateCallback<Product>(hql, new Object[] { cid }, begin, limit));
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	/**
	 * @param csid
	 *            ���ݶ��������ѯ��Ʒ����
	 */
	public int findCountCsid(Integer csid) {
		String hql = "select count(*) from Product p where p.categorySecond.csid=?";
		List<Long> list = this.getHibernateTemplate().find(hql, csid);
		if (list != null && list.size() > 0) {
			return list.get(0).intValue();
		}
		return 0;
	}

	/**
	 * @param csid
	 * @param begin
	 * @param limit
	 *            ���ݶ��������ѯ��Ʒ��Ϣ
	 */
	public List<Product> findByPageCsid(Integer csid, int begin, int limit) {
		String hql = "select p from Product p join p.categorySecond cs where cs.csid=?";
		List<Product> list = this.getHibernateTemplate()
				.execute(new PageHibernateCallback<Product>(hql, new Object[] { csid }, begin, limit));
		if (list != null && list.size() > 0) {
			return list;
		}
		return list;
	}

	/**
	 * ͳ��������Ʒ����
	 */
	public int findCount() {
		String hql = "select count(*) from Product";
		List<Long> list = this.getHibernateTemplate().find(hql);
		if (list != null && list.size() > 0) {
			return list.get(0).intValue();
		}
		return 0;
	}

	/**
	 * ����ҳ�Ĳ�ѯ
	 */
	public List<Product> findByPage(int begin, int limit) {
		String hql = "from Product order by pdate desc";
		List<Product> list = this.getHibernateTemplate()
				.execute(new PageHibernateCallback<Product>(hql, null, begin, limit));
		if (list != null && list.size() > 0) {
			return list;
		}
		return list;
	}

	/**
	 * ������Ʒ
	 */
	public void save(Product product) {
		this.getHibernateTemplate().save(product);
	}

	/**
	 * ɾ��ɾ����Ʒ
	 */
	public void delete(Product product) {
		this.getHibernateTemplate().delete(product);
	}

	/**
	 * �޸�
	 */
	public void update(Product product) {
		this.getHibernateTemplate().update(product);
	}

}

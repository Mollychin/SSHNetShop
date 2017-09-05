/**
 * 
 */
package com.codeagles.shop.orders.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.codeagles.shop.orders.vo.Order;
import com.codeagles.shop.orders.vo.OrderItem;
import com.codeagles.util.PageHibernateCallback;

public class OrderDao extends HibernateDaoSupport {

	/**
	 * order������������
	 */
	public void save(Order order) {
		this.getHibernateTemplate().save(order);

	}

	/**
	 * @param uid
	 *            �ҵĶ�����ͨ��UID��ѯ�ܼ�¼��
	 */
	public Integer findByCountUid(int uid) {
		String hql = "select count(*) from Order o where o.user.uid=?";
		List<Long> list = this.getHibernateTemplate().find(hql, uid);
		if (list != null && list.size() > 0) {
			return list.get(0).intValue();
		}
		return null;
	}

	// �ҵĶ����Ĳ�ѯ
	public List<Order> findByPageUid(int uid, Integer begin, int limit) {
		String hql = "from Order o where o.user.uid=? order by ordertime desc";
		List<Order> list = this.getHibernateTemplate()
				.execute(new PageHibernateCallback<Order>(hql, new Object[] { uid }, begin, limit));

		return list;
	}

	/**
	 * @param oid
	 * @return
	 */
	public Order findByOid(Integer oid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(Order.class, oid);
	}

	/**
	 * @param ourrOrder
	 */
	public void update(Order ourrOrder) {
		this.getHibernateTemplate().update(ourrOrder);

	}

	/**
	 * ��ѯ��������
	 */
	public Integer findByCount() {
		String hql = "select count(*) from Order ";
		List<Long> list = this.getHibernateTemplate().find(hql);
		if (list != null && list.size() > 0) {
			return list.get(0).intValue();
		}
		return 0;
	}

	/**
	 * ��ѯ�����ļ���
	 */
	public List<Order> findByPage(Integer begin, int limit) {
		String hql = "from Order order by ordertime desc";
		List<Order> list = this.getHibernateTemplate()
				.execute(new PageHibernateCallback<Order>(hql, null, begin, limit));
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	/**
	 * ���ݶ���id ��ѯ������ķ���
	 */
	public List<OrderItem> findOrderItem(Integer oid) {
		String hql = "from OrderItem oi where oi.order.oid =?";
		List<OrderItem> list = this.getHibernateTemplate().find(hql, oid);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

}

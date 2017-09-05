/**
 * 
 */
package com.codeagles.shop.orders.adminaction;

import java.util.List;

import com.codeagles.shop.orders.service.OrderService;
import com.codeagles.shop.orders.vo.Order;
import com.codeagles.shop.orders.vo.OrderItem;
import com.codeagles.util.PageBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class AdminOrderAction extends ActionSupport implements ModelDriven<Order> {
	private Order order = new Order();
	private OrderService orderService;
	private Integer page;

	public void setPage(Integer page) {
		this.page = page;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	@Override
	public Order getModel() {
		return order;
	}

	// ����ҳ�Ĳ�ѯ
	public String findAll() {
		PageBean<Order> pageBean = orderService.findByPage(page);
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findAll";
	}

	// ���ݶ���id��ѯ������
	public String findOrderItem() {
		List<OrderItem> list = orderService.findOrderItem(order.getOid());
		ActionContext.getContext().getValueStack().set("list", list);
		return "findOrderItem";
	}

	// �޸Ķ���״̬�ķ���
	public String updateStates() {
		// ���ݶ���id ��ѯ����
		Order currOrder = orderService.findByOid(order.getOid());
		// �޸Ķ���״̬
		currOrder.setState(3);
		orderService.update(currOrder);
		return "updateStates";
	}
}

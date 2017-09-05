/**
 * 
 */
package com.codeagles.shop.orders.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.codeagles.shop.cart.vo.Cart;
import com.codeagles.shop.cart.vo.CartItem;
import com.codeagles.shop.orders.service.OrderService;
import com.codeagles.shop.orders.vo.Order;
import com.codeagles.shop.orders.vo.OrderItem;
import com.codeagles.shop.user.vo.User;
import com.codeagles.util.PageBean;
import com.codeagles.util.PaymentUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class OrderAction extends ActionSupport implements ModelDriven<Order> {
	private Order order = new Order();
	private OrderService orderServie;
	private Integer page;

	public void setOrderServie(OrderService orderServie) {
		this.orderServie = orderServie;
	}

	// ����֧��ͨ��
	private String pd_FrpId;

	public String getPd_FrpId() {
		return pd_FrpId;
	}

	public void setPd_FrpId(String pd_FrpId) {
		this.pd_FrpId = pd_FrpId;
	}

	@Override
	public Order getModel() {
		return order;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String save() {
		java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String date = format.format(new Date());
		// �������ݵ����ݿ�
		order.setOrdertime(date);
		/**
		 * 1��δ���� 2���Ѹ������û�з��� 3���Ѿ�����������û��ȷ���ջ� 4���������
		 */
		order.setState(1);
		Cart cart = (Cart) ServletActionContext.getRequest().getSession().getAttribute("cart");
		if (cart == null) {
			this.addActionError("�ף��㻹û�й���������ύ�����ϣ�");
			return "msg";
		}
		order.setTotal(cart.getTotal());
		for (CartItem cartItem : cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setCount(cartItem.getCount());
			orderItem.setSubtotal(cartItem.getSubtotal());
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setOrder(order);

			order.getOrderItems().add(orderItem);
		}
		// ���ö��������û�
		User exsitUser = (User) ServletActionContext.getRequest().getSession().getAttribute("exsitUser");
		if (exsitUser == null) {
			this.addActionError("�ף��㻹û�е�¼�����ȵ�¼��");
			return "login";
		}
		order.setUser(exsitUser);
		orderServie.save(order);
		// ������������ʾ��ҳ����
		// ͨ��ֵջ����ʽ�洢������ʾ��ҳ����
		cart.clearCart();
		return "saveSuccess";
	}

	// �ҵĶ����Ĳ�ѯ
	public String findByUid() {
		// �����û�Uid��ѯ
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("exsitUser");
		PageBean<Order> pageBean = orderServie.findByPageUid(user.getUid(), page);

		// ��������ʾ��ҳ����
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findByUidSuccess";
	}

	// ���ݶ�����oid��ѯ�����ķ���
	public String findByOid() {
		order = orderServie.findByOid(order.getOid());
		return "findByOidSuccess";
	}

	public String payOrder() throws Exception {
		// �޸Ķ���
		Order ourrOrder = orderServie.findByOid(order.getOid());
		ourrOrder.setAddr(order.getAddr());
		ourrOrder.setName(order.getName());
		ourrOrder.setPhone(order.getPhone());
		orderServie.update(ourrOrder);
		// Ϊ��������
		String p0_Cmd = "Buy";
		String p1_MerId = "10001126856";
		String p2_Order = order.getOid().toString();
		String p3_Amt = "0.01";
		String p4_Cur = "CNY";
		String p5_Pid = "";
		String p6_Pcat = "";
		String p7_Pdesc = "";

		String p8_Url = "http://localhost:8080/order_callBack.action";
		String p9_SAF = "";
		String pa_MP = "";
		String pd_FrpId = this.pd_FrpId;
		String pr_NeedResponse = "1";
		String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc,
				p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);
		// �ύ����������˾
		StringBuffer stringBuffer = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
		stringBuffer.append("p0_Cmd=").append(p0_Cmd).append(" & ");
		stringBuffer.append("p1_MerId=").append(p1_MerId).append(" & ");
		stringBuffer.append("p2_Order=").append(p2_Order).append(" & ");
		stringBuffer.append("p3_Amt=").append(p3_Amt).append(" & ");
		stringBuffer.append("p4_Cur=").append(p4_Cur).append(" & ");
		stringBuffer.append("p5_Pid=").append(p5_Pid).append(" & ");
		stringBuffer.append("p6_Pcat=").append(p6_Pcat).append(" & ");
		stringBuffer.append("p7_Pdesc=").append(p7_Pdesc).append(" & ");
		stringBuffer.append("p8_Url=").append(p8_Url).append(" & ");
		stringBuffer.append("p9_SAF=").append(p9_SAF).append(" & ");
		stringBuffer.append("pa_MP=").append(pa_MP).append(" & ");
		stringBuffer.append("pd_FrpId=").append(pd_FrpId).append(" & ");
		stringBuffer.append("pr_NeedResponse=").append(pr_NeedResponse).append(" & ");
		stringBuffer.append("hmac=").append(hmac);
		// ServletActionContext.getResponse().sendRedirect(stringBuffer.toString());
		return "payOrder";
	}

	public String callBack() {
		// �޸Ķ���״̬��ʾ�Ѿ�����
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("oid");

		Order currOrder = orderServie.findByOid(Integer.parseInt(id));
		currOrder.setState(2);
		orderServie.update(currOrder);
		//
		this.addActionMessage("��������ɹ���������ţ�" + currOrder.getOid() + "    ����Ľ�" + currOrder.getTotal());
		return "msg";
	}

	// ���ȷ���ջ� Ȼ���޸Ķ���״̬�ķ���
	public String updateState() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("oid");
		Order currOrder = orderServie.findByOid(Integer.parseInt(id));
		currOrder.setState(4);
		orderServie.update(currOrder);
		return "updateState";
	}
}

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

	// 接收支付通道
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
		// 保存数据到数据库
		order.setOrdertime(date);
		/**
		 * 1：未付款 2：已付款，但是没有发货 3：已经发货，但是没有确认收货 4：交易完成
		 */
		order.setState(1);
		Cart cart = (Cart) ServletActionContext.getRequest().getSession().getAttribute("cart");
		if (cart == null) {
			this.addActionError("亲，你还没有购物，还不能提交订单呦！");
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
		// 设置订单所属用户
		User exsitUser = (User) ServletActionContext.getRequest().getSession().getAttribute("exsitUser");
		if (exsitUser == null) {
			this.addActionError("亲，你还没有登录，请先登录！");
			return "login";
		}
		order.setUser(exsitUser);
		orderServie.save(order);
		// 将订单对象显示到页面上
		// 通过值栈的形式存储，并显示在页面上
		cart.clearCart();
		return "saveSuccess";
	}

	// 我的订单的查询
	public String findByUid() {
		// 根据用户Uid查询
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("exsitUser");
		PageBean<Order> pageBean = orderServie.findByPageUid(user.getUid(), page);

		// 将数据显示在页面上
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findByUidSuccess";
	}

	// 根据订单的oid查询订单的方法
	public String findByOid() {
		order = orderServie.findByOid(order.getOid());
		return "findByOidSuccess";
	}

	public String payOrder() throws Exception {
		// 修改订单
		Order ourrOrder = orderServie.findByOid(order.getOid());
		ourrOrder.setAddr(order.getAddr());
		ourrOrder.setName(order.getName());
		ourrOrder.setPhone(order.getPhone());
		orderServie.update(ourrOrder);
		// 为订单付款
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
		// 提交到第三方公司
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
		// 修改订单状态显示已经付款
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("oid");

		Order currOrder = orderServie.findByOid(Integer.parseInt(id));
		currOrder.setState(2);
		orderServie.update(currOrder);
		//
		this.addActionMessage("订单付款成功：订单编号：" + currOrder.getOid() + "    付款的金额：" + currOrder.getTotal());
		return "msg";
	}

	// 点击确认收货 然后修改订单状态的方法
	public String updateState() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("oid");
		Order currOrder = orderServie.findByOid(Integer.parseInt(id));
		currOrder.setState(4);
		orderServie.update(currOrder);
		return "updateState";
	}
}

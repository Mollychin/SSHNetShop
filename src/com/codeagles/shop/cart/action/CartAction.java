/**
 * 
 */
package com.codeagles.shop.cart.action;

import org.apache.struts2.ServletActionContext;

import com.codeagles.shop.cart.vo.Cart;
import com.codeagles.shop.cart.vo.CartItem;
import com.codeagles.shop.product.service.ProductService;
import com.codeagles.shop.product.vo.Product;
import com.opensymphony.xwork2.ActionSupport;

public class CartAction extends ActionSupport {
	private Integer pid;
	private Integer count;
	private ProductService productService;

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	// ��ӵ����ﳵ
	public String addCart() {
		// ��װһ��CartItem����
		CartItem cartItem = new CartItem();
		// ��������
		cartItem.setCount(count);
		// ��ѯ��Ʒ
		Product product = productService.findByPid(pid);
		// ������Ʒ
		cartItem.setProduct(product);
		// ����������ӹ��ﳵ (���ﳵӦ�ô���session)
		Cart cart = getCart();
		cart.addCart(cartItem);
		return "addCart";
	}

	public String clearCart() {
		Cart cart = getCart();
		cart.clearCart();
		return "clearCart";
	}

	public String removeCart() {
		Cart cart = getCart();
		cart.removeCart(pid);
		return "removeCart";
	}

	// �ҵĹ��ﳵ
	public String myCart() {
		return "myCart";
	}

	/**
	 * ��Session ��ù��ﳵ
	 */
	private Cart getCart() {
		Cart cart = (Cart) ServletActionContext.getRequest().getSession().getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			ServletActionContext.getRequest().getSession().setAttribute("cart", cart);

		}
		return cart;
	}
}

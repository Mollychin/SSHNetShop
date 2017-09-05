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

	// 添加到购物车
	public String addCart() {
		// 封装一个CartItem对象
		CartItem cartItem = new CartItem();
		// 设置数量
		cartItem.setCount(count);
		// 查询商品
		Product product = productService.findByPid(pid);
		// 设置商品
		cartItem.setProduct(product);
		// 将购物项添加购物车 (购物车应该存在session)
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

	// 我的购物车
	public String myCart() {
		return "myCart";
	}

	/**
	 * 从Session 获得购物车
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

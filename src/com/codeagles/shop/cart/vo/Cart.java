/**
 * 
 */
package com.codeagles.shop.cart.vo;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart implements Serializable {
	// ������� key:��Ʒid value:������
	private Map<Integer, CartItem> map = new LinkedHashMap<Integer, CartItem>();

	// Cart������ ��CartItems����
	public Collection<CartItem> getCartItems() {
		return map.values();
	}

	// �ܼ�
	private double total;

	// Ҫʵ�ֵĹ���
	// 1.��������ӵ����ﳵ
	public void addCart(CartItem cartItem) {
		// �жϹ��ﳵ���Ƿ��Ѿ����ڹ����
		// ҵ���߼���
		/*
		 * *������ڣ� �������� �ܼ�=�ܼ�+������С�� *��������ڣ� ��map����ӹ�����
		 * �ܼ�=�ܼ�+������С��
		 */
		Integer pid = cartItem.getProduct().getPid();
		if (map.containsKey(pid)) {
			CartItem _cartItem = map.get(pid);
			_cartItem.setCount(_cartItem.getCount() + cartItem.getCount());
		} else {
			map.put(pid, cartItem);
		}
		// �����ܼƽ��
		total += cartItem.getSubtotal();
	}

	// 2.�ӹ��ﳵ�Ƴ�������
	public void removeCart(Integer pid) {
		// �Ƴ�������Ʒ
		CartItem cartItem = map.remove(pid);
		// �ܼ���֮�仯
		total = total - cartItem.getSubtotal();
	}

	// 3.��չ��ﳵ
	public void clearCart() {
		// �����еĹ�������� �� �ܼƽ����Ϊ0
		map.clear();
		total = 0;

	}

	public Map<Integer, CartItem> getMap() {
		return map;
	}

	public void setMap(Map<Integer, CartItem> map) {
		this.map = map;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

}

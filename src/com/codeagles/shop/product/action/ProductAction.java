/**
 * 
 */
package com.codeagles.shop.product.action;

import com.codeagles.shop.product.service.ProductService;
import com.codeagles.shop.product.vo.Product;
import com.codeagles.util.PageBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class ProductAction extends ActionSupport implements ModelDriven<Product> {
	private Product product = new Product();
	private Integer cid;
	private int page = 1;// ���յ�ǰҳ��
	private Integer csid;

	public Integer getCsid() {
		return csid;
	}

	public void setCsid(Integer csid) {
		this.csid = csid;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	@Override
	public Product getModel() {
		return product;
	}

	private ProductService productService;

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	// ������ƷID��ѯ��Ʒ
	public String findByPid() {
		product = productService.findByPid(product.getPid());
		return "findByPid";
	}

	// ���ݷ����id��ѯ��Ʒ
	public String findByCid() {
		PageBean<Product> pageBean = productService.findByPageCid(cid, page);
		// ��pageBean����ֵջ
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findByCid";
	}

	// ����csid��ѯ��Ʒ
	public String findByCsid() {
		PageBean<Product> pageBean = productService.findByPageCsid(csid, page);
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findByCsid";
	}
}

/**
 * 
 */
package com.codeagles.shop.product.adminproduct;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.codeagles.shop.categorysecond.service.CategorySecondService;
import com.codeagles.shop.categorysecond.vo.CategorySecond;
import com.codeagles.shop.product.service.ProductService;
import com.codeagles.shop.product.vo.Product;
import com.codeagles.util.PageBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class AdminProductAction extends ActionSupport implements ModelDriven<Product> {

	private Product product = new Product();
	private CategorySecondService categorySecondService;
	private ProductService productService = new ProductService();
	private Integer page;

	// �ļ��ϴ��Ĳ���
	private File upload;
	private String uploadFileName;
	private String uploadContextType;

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadContextType() {
		return uploadContextType;
	}

	public void setUploadContextType(String uploadContextType) {
		this.uploadContextType = uploadContextType;
	}

	public Integer getPage() {
		return page;
	}

	public void setCategorySecondService(CategorySecondService categorySecondService) {
		this.categorySecondService = categorySecondService;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	@Override
	public Product getModel() {
		return product;
	}

	public String findAll() {
		PageBean<Product> pageBean = productService.findByPage(page);
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findAll";
	}

	// ��ת��ҳ��ķ���
	public String addPage() {
		// ��ѯ��������ļ���
		List<CategorySecond> cslist = categorySecondService.findAll();
		ActionContext.getContext().getValueStack().set("cslist", cslist);
		return "addPageSuccess";
	}

	// �ϴ���Ʒ
	public String save() throws IOException {
		java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String date = format.format(new Date());
		product.setPdate(date);
		if (upload != null) {
			// ����ļ��ϴ��Ĵ��̾���·��
			String realPath = ServletActionContext.getServletContext().getRealPath("/products");
			// ����һ���ļ�
			File diskFile = new File(realPath + "//" + uploadFileName);
			FileUtils.copyFile(upload, diskFile);
			product.setImage("products/" + uploadFileName);
		}
		productService.save(product);
		return "saveSuccess";
	}

	public String edit() {
		HttpServletRequest request = ServletActionContext.getRequest();
		product = productService.findByPid(Integer.parseInt(request.getParameter("pid")));
		List<CategorySecond> cslist = categorySecondService.findAll();
		ActionContext.getContext().getValueStack().set("cslist", cslist);
		return "editSuccess";
	}

	public String delete() {
		// HttpServletRequest request = ServletActionContext.getRequest();
		product = productService.findByPid(product.getPid());// Integer.parseInt(request.getParameter("pid"))
		System.out.println(product.getPid());
		String path = product.getImage();
		if (path != null) {
			String realPath = ServletActionContext.getServletContext().getRealPath("/" + path);
			File file = new File(realPath);
			file.delete();
		}
		productService.delete(product);

		return "deleteSuccess";
	}

	// �޸�
	public String update() throws IOException {
		java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String date = format.format(new Date());
		product.setPdate(date);
		if (upload != null) {
			// ����ļ��ϴ��Ĵ��̾���·��
			String path = product.getImage();
			File file = new File(ServletActionContext.getServletContext().getRealPath("/" + path));
			file.delete();
			String realPath = ServletActionContext.getServletContext().getRealPath("/products");
			// ����һ���ļ�
			File diskFile = new File(realPath + "//" + uploadFileName);
			FileUtils.copyFile(upload, diskFile);
			product.setImage("products/" + uploadFileName);
		}
		productService.update(product);
		return "updateSuccess";
	}
}

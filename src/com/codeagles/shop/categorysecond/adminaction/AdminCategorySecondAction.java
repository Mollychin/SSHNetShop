/**
 * 
 */
package com.codeagles.shop.categorysecond.adminaction;

import java.util.List;

import com.codeagles.shop.category.service.CategoryService;
import com.codeagles.shop.category.vo.Category;
import com.codeagles.shop.categorysecond.service.CategorySecondService;
import com.codeagles.shop.categorysecond.vo.CategorySecond;
import com.codeagles.util.PageBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class AdminCategorySecondAction extends ActionSupport implements ModelDriven<CategorySecond> {
	private CategorySecond categorySecond = new CategorySecond();
	private CategorySecondService categorySecondService;
	private CategoryService categoryService;

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	// ����page
	private Integer page;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	@Override
	public CategorySecond getModel() {
		return categorySecond;
	}

	public void setCategorySecondService(CategorySecondService categorySecondService) {
		this.categorySecondService = categorySecondService;
	}

	// ��ѯ��������ķ���
	public String findAll() {
		PageBean<CategorySecond> pageBean = categorySecondService.findByPage(page);
		// ���浽ֵ��
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findAll";
	}

	//
	public String addPage() {
		List<Category> clist = categoryService.findAll();
		ActionContext.getContext().getValueStack().set("clist", clist);
		return "addPage";
	}

	public String save() {
		categorySecondService.save(categorySecond);
		return "addSuccess";
	}

	public String delete() {
		categorySecond = categorySecondService.findByCsid(categorySecond.getCsid());
		categorySecondService.delete(categorySecond);
		return "deleteSuccess";
	}

	public String edit() {
		categorySecond = categorySecondService.findByCsid(categorySecond.getCsid());
		List<Category> clist = categoryService.findAll();
		ActionContext.getContext().getValueStack().set("clist", clist);
		return "editSuccess";
	}

	public String update() {
		categorySecondService.update(categorySecond);
		return "updateSuccess";
	}
}

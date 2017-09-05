/**
 * 
 */
package com.codeagles.shop.category.adminaction;

import java.util.List;

import com.codeagles.shop.category.service.CategoryService;
import com.codeagles.shop.category.vo.Category;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class AdminCategoryAction extends ActionSupport implements ModelDriven<Category> {
	private Category category = new Category();

	@Override
	public Category getModel() {
		return category;
	}

	private CategoryService categoryService;

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	// ִ�в�ѯ���е�һ������
	public String findAll() {
		List<Category> clist = categoryService.findAll();
		ActionContext.getContext().getValueStack().set("clist", clist);
		return "findAll";
	}

	public String save() {
		categoryService.save(category);
		return "saveSuccess";
	}

	public String delete() {
		category = categoryService.findByCid(category.getCid());
		categoryService.delete(category);
		return "deleteSuccess";
	}

	public String edit() {
		category = categoryService.findByCid(category.getCid());
		return "editSuccess";
	}

	public String update() {
		categoryService.update(category);
		return "updateSuccess";
	}
}

/**
 * 
 */
package com.codeagles.shop.adminUser.action;

import org.apache.struts2.ServletActionContext;

import com.codeagles.shop.adminUser.service.AdminUserService;
import com.codeagles.shop.adminUser.vo.AdminUser;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class AdminUserAction extends ActionSupport implements ModelDriven<AdminUser> {

	private AdminUser adminUser = new AdminUser();
	private AdminUserService adminUserService;

	public void setAdminUserService(AdminUserService adminUserService) {
		this.adminUserService = adminUserService;
	}

	@Override
	public AdminUser getModel() {
		return adminUser;
	}

	public String login() {
		AdminUser existAdminUser = adminUserService.login(adminUser);
		if (existAdminUser == null) {
			this.addActionError("亲，用户名或者密码错误");
			return "loginFail";
		} else {
			ServletActionContext.getRequest().getSession().setAttribute("existAdminUser", existAdminUser);
			return "loginSuccess";
		}
	}

}

/**
 * 
 */
package com.codeagles.shop.user.action;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.codeagles.shop.user.service.UserService;
import com.codeagles.shop.user.vo.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class UserAction extends ActionSupport implements ModelDriven<User> {
	User user = new User();
	private UserService userService;
	private String checkcode;

	public String getCheckcode() {
		return checkcode;
	}

	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	// 模型驱动
	public User getModel() {
		return user;
	}

	public String registPage() throws Exception {

		return "registPage";
	}

	public String findByname() throws Exception {
		User esxistUser = userService.findByUsername(user.getUsername());
		HttpServletResponse respones = ServletActionContext.getResponse();
		respones.setContentType("text/html;charset=UTF-8");
		if (esxistUser != null) {
			// 用户存在 换名
			respones.getWriter().println("<font color='red'>用户名已存在</font>");
		} else {
			// 用户不存在 可以使用
			respones.getWriter().println("<font color='green'>用户名可以使用</font>");
		}
		return NONE;
	}

	public String regist() {
		// 判断验证码
		String checkcode1 = (String) ServletActionContext.getRequest().getSession().getAttribute("checkcode");
		if (!checkcode.equalsIgnoreCase(checkcode1)) {
			this.addActionError("验证码输入错误！");
			return "checkcode";
		}
		userService.save(user);
		this.addActionMessage("注册成功，请去邮箱激活。");
		return "msg";
	}

	// 点击邮件中的链接，跳到此方法中，进行激活。
	public String active() {
		// 根据激活码查询用户
		User exsitUser = userService.findByCode(user.getCode());
		if (exsitUser == null) {
			this.addActionMessage("激活失败，激活码错误");
			return "msg";
		} else {
			// 激活成功
			exsitUser.setState(1);
			exsitUser.setCode(null);
			userService.update(exsitUser);
			this.addActionMessage("激活成功，请去登录");
		}
		return "msg";
	}

	// 跳转到登录界面
	public String loginPage() {
		return "loginPage";
	}

	// 登录方法
	public String login() {
		User exsitUser = userService.login(user);
		if (exsitUser == null) {
			// 登陆失败
			this.addActionMessage("登录失败，用户名或密码错误，或者账号未激活");
			return LOGIN;
		} else {
			// 登录成功
			ServletActionContext.getRequest().getSession().setAttribute("exsitUser", exsitUser);
		}
		return "loginsuccess";
	}

	// 退出登录
	public String quit() {
		ServletActionContext.getRequest().getSession().invalidate();
		return "quit";
	}
}

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
	// ģ������
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
			// �û����� ����
			respones.getWriter().println("<font color='red'>�û����Ѵ���</font>");
		} else {
			// �û������� ����ʹ��
			respones.getWriter().println("<font color='green'>�û�������ʹ��</font>");
		}
		return NONE;
	}

	public String regist() {
		// �ж���֤��
		String checkcode1 = (String) ServletActionContext.getRequest().getSession().getAttribute("checkcode");
		if (!checkcode.equalsIgnoreCase(checkcode1)) {
			this.addActionError("��֤���������");
			return "checkcode";
		}
		userService.save(user);
		this.addActionMessage("ע��ɹ�����ȥ���伤�");
		return "msg";
	}

	// ����ʼ��е����ӣ������˷����У����м��
	public String active() {
		// ���ݼ������ѯ�û�
		User exsitUser = userService.findByCode(user.getCode());
		if (exsitUser == null) {
			this.addActionMessage("����ʧ�ܣ����������");
			return "msg";
		} else {
			// ����ɹ�
			exsitUser.setState(1);
			exsitUser.setCode(null);
			userService.update(exsitUser);
			this.addActionMessage("����ɹ�����ȥ��¼");
		}
		return "msg";
	}

	// ��ת����¼����
	public String loginPage() {
		return "loginPage";
	}

	// ��¼����
	public String login() {
		User exsitUser = userService.login(user);
		if (exsitUser == null) {
			// ��½ʧ��
			this.addActionMessage("��¼ʧ�ܣ��û�����������󣬻����˺�δ����");
			return LOGIN;
		} else {
			// ��¼�ɹ�
			ServletActionContext.getRequest().getSession().setAttribute("exsitUser", exsitUser);
		}
		return "loginsuccess";
	}

	// �˳���¼
	public String quit() {
		ServletActionContext.getRequest().getSession().invalidate();
		return "quit";
	}
}

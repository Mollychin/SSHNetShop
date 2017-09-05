/**
 * 
 */
package com.codeagles.shop.intercepter;

import org.apache.struts2.ServletActionContext;

import com.codeagles.shop.adminUser.vo.AdminUser;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class LoginIntercepter extends MethodFilterInterceptor {

	protected String doIntercept(ActionInvocation arg0) throws Exception {
		AdminUser adminUser = (AdminUser) ServletActionContext.getRequest().getSession().getAttribute("existAdminUser");
		if (adminUser == null) {
			ActionSupport as = (ActionSupport) arg0.getAction();
			as.addActionError("����û�е�¼��û��Ȩ�޲����˹���");
			return "loginFail";
		} else {
			return arg0.invoke();
		}

	}

}

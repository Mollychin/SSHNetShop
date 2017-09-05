<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'pay.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>
	订单编号:
	<s:property value="model.oid" />
	号
	<center>
		<input  type="hidden" name="oid"
			value="<s:property value="model.oid"/>" /> <a
			href="${pageContext.request.contextPath}/order_callBack.action?oid=<s:property value="model.oid"/>">
			<img src="${pageContext.request.contextPath}/images/Ali.png"
			width="25" style="height: 389px; width: 226px;" />
		</a>
	</center>
</body>
</html>

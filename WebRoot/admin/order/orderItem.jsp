<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags"  prefix="s"%>
<table border="1"  width="100%" align="center">
<tr>
	<th>商品图片</th>
	<th>数量</th>
	<th>小计</th>
</tr>
<s:iterator var="orderItem" value="list"> 
	
	<tr>
		<td align="center"><img width="40" height="45" src="${pageContext.request.contextPath }/<s:property value="#orderItem.product.image"/>"></td>
		<td align="center"><s:property value="#orderItem.count"/></td>
		<td align="center"><s:property value="#orderItem.subtotal"/></td>
	</tr>
	</s:iterator>
</table>
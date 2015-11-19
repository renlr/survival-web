<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE HTML>
<html>
<HEAD>
<TITLE><spring:message code="title"/></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<link rel="stylesheet" href="http://files.cnblogs.com/rubylouvre/bootstrap.css" />
<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/views/css/admin_top.css'>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/jquery-1.10.1.min.js"></script>
<script language="javascript">
$(document).ready(function(){
	$("#delcache").click(function(){
		$ajaxurl = $(this).attr('href');
		$.get($ajaxurl,null,function(data){
			$("#cache").show();
			$("#cache").html(' <font color=#ff0000>'+data+'</font>');
			window.setTimeout(function(){
				$("#cache").hide();
			},2000);
		});
		return false;
	});
	$("#cache").click(function(){
		$("#cache").hide();
		return false;
	});
});
function left(url){
	parent.left.show("${pageContext.request.contextPath}/left.do"+url);
}
</script>
</HEAD>
<body>
<div class="topnav">
  <div class="sitenav">
    <div class=welcome>您好：<span class="username">${user.name}</span>，欢迎来到<spring:message code="title"/></div>
  <div class="leftnav">
    <ul>
      <li class="navleft"></li>
      <c:forEach var="menu" items="${menuTop}">
      	<c:if test="${menu.id == 1}">
      		<li id="d1" style="margin-left: -1px"><a href="${pageContext.request.contextPath}/main.do" target="right" onClick="left('?id=${menu.divid}');">${menu.name}</a></li>
      	</c:if>
      	<c:if test="${menu.id != 1}">
      		<li id="d2"><a href="${pageContext.request.contextPath}/main.do" target="right" onClick="left('?id=${menu.divid}');">${menu.name}</a></li>
      	</c:if>
      </c:forEach>
      <li id="d9" style="margin-right: -1px"><a href="${pageContext.request.contextPath}/user/logout.do" target="_parent">注销登录</a></li>
    </ul>
  </div>
</div>
</body>
</HTML>

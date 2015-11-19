<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE HTML>
<html>
<head>
<title><spring:message code="title"/></title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/views/css/admin_login.css'>
<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/views/css/admin_style.css'>
<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/views/css/dialog.css'>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/views/js/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/views/js/themes/icon.css">
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/jquery-1.10.1.min.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/common.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/jquery.easyui.min.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/easyui-lang-zh_CN.js"></script>
<script>
if(self!=top){
	top.location=self.location;
}
$(document).ready(function() {
	$("#loginForm").submit(function(){
		var loginName = $("#loginName").val(); 
		var loginPwd = $("#loginPwd").val(); 
		if($.trim(loginName) == ""){
			alert("用户名不能为空！"); 
			$("#loginName").focus(); 
			return false; 
		}
		if($.trim(loginPwd) == ""){
			alert("密码不能为空！"); 
			$("#loginPwd").focus(); 
			return false; 
		}
	});
});
</script>
</head>
<body>
<div class="main">
  <div class="title"><spring:message code="title"/></div>
  <div class="login">
    <form action="${pageContext.request.contextPath}/user/login.do" method="post" name="show" id="loginForm">
      <div class="inputbox">
        <dl>
          <dd>用户名：</dd>
          <dd>
            <input type="text" name="loginName" id="loginName" size="25" onfocus="this.style.borderColor='#fc9938'" onblur="this.style.borderColor='#dcdcdc'" />
          </dd>
          <dd>密码：</dd>
          <dd>
            <input type="password" name="loginPwd" id="loginPwd" size="25" onfocus="this.style.borderColor='#fc9938'" onblur="this.style.borderColor='#dcdcdc'" />
          </dd>
          <dd>
            <input name="submit" type="submit" value="" class="input" />
          </dd>
        </dl>
      </div>
      <div class="butbox">
        <dl>
          <dd>&nbsp;</dd>
        </dl>
      </div>
      <div style="clear:both"></div>
    </form>
  </div>
</div>
</body>
</html>

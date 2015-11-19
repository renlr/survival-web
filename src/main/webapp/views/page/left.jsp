<%@page import="com.baofeng.commons.entity.MenuItem"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<title>左侧导航-暴风手机应用后台管理系统</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel='stylesheet' type='text/css' href='<%=basePath%>/views/css/admin_left.css'>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/jquery-1.10.1.min.js"></script>
<script language="javascript">
	function show(url) {
		location.href = url;
	}
</script>
</head>
<body>
	<div class="menu">
		<%
			String id = request.getParameter("id") == null ? "" : request.getParameter("id");
			List<MenuItem> topList = (List<MenuItem>)request.getAttribute("menuTop");
			List<MenuItem> leftList = (List<MenuItem>)request.getAttribute("menuLeft");
			if(topList != null && topList.size() > 0){
				for(Iterator<MenuItem> it = topList.iterator();it.hasNext();){
					MenuItem topMenus = it.next();
					if("".equals(id)){
						MenuItem divid = (MenuItem)topList.get(0);
						id = divid.getDivid();
					}
					if(id.equals(topMenus.getDivid())){
						%>
						<dl>
							<dt>
								<a onClick="showHide('items');" href="#" target="_self"><%=topMenus.getName() %></a>
							</dt>
							<dd>
								<ul id="items">
									<%
									for(Iterator<MenuItem> its = leftList.iterator();its.hasNext();){
										MenuItem leftMenus = its.next();
										if(leftMenus.getItem() != null && topMenus.getId().intValue() == leftMenus.getItem().getId().intValue()){
											%>
											<li><a href="<%=path%>/<%=leftMenus.getUrl() %>" target="right"><%=leftMenus.getName() %></a></li>
											<%							
										}							
									}	
									%>
								</ul>
							</dd>
						</dl>						
						<%
					}
				}								
			}
		%>
	</div>
	<script language="javascript">
		function showHide(objname) {
			$("#items").toggleClass("none");
		}
	</script>
</body>
</html>

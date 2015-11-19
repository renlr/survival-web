<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE HTML>
<html>
<head>
<TITLE><spring:message code="title"/></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<META content="text/html; charset=utf-8" http-equiv=Content-Type>
<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/views/css/admin_style.css'>
<script src="http://files.cnblogs.com/rubylouvre/jquery1.83.js"></script>
<script language="JavaScript1.2">if(self!=top){top.location=self.location;}</script>
<script src="http://cdn.sockjs.org/sockjs-0.3.min.js"></script>
<link rel="stylesheet" href="http://files.cnblogs.com/rubylouvre/bootstrap.css" />
<script src="http://files.cnblogs.com/rubylouvre/jquery1.83.js"> </script>
<script src="http://files.cnblogs.com/rubylouvre/bootstrap-transition.js"></script>
<script src="http://files.cnblogs.com/rubylouvre/bootstrap-modal.js"></script>
</head>
<script type="text/javascript">
$(document).ready(function() {
	$("#myModal").modal({show:true});
	var frams = '<FRAMESET id="main" border=0 frameSpacing=0 rows=76,* frameBorder=0 cols=* style="display: none">'
		+'<FRAME id="topFrame" name=topFrame src="${pageContext.request.contextPath}/top.do" noResize scrolling=no>'
		+'<FRAMESET id="bodyFrame" border=0 frameSpacing=0 frameBorder=NO cols=210,* noresize scrolling="yes">'
		+'<FRAME id="left" name=left src="${pageContext.request.contextPath}/left.do" frameBorder=0 scrolling=yes>'
		+'<FRAME id="right" name=right marginWidth=0 marginHeight=0 src="${pageContext.request.contextPath}/main.do" frameBorder=0 scrolling=yes>'
		+'</FRAMESET>'
		+'</FRAMESET>';
});
</script>
<FRAMESET id="main" border=0 frameSpacing=0 rows=76,* frameBorder=0 cols=* style="display: none">
	<FRAME id="topFrame" name=topFrame src="${pageContext.request.contextPath}/top.do" noResize scrolling=no>
	<FRAMESET id="bodyFrame" border=0 frameSpacing=0 frameBorder=NO cols=210,* noresize scrolling="yes">
		<FRAME id="left" name=left src="${pageContext.request.contextPath}/left.do" frameBorder=0 scrolling=yes>
		<FRAME id="right" name=right marginWidth=0 marginHeight=0 src="${pageContext.request.contextPath}/main.do" frameBorder=0 scrolling=yes>
	</FRAMESET>
</FRAMESET>
<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3 id="myModalLabel">管家服务</h3>
		</div>
		<div class="modal-body">
			<p>弹出层…</p>
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
			<button class="btn btn-primary">现在去服务</button>
		</div>
	</div>
</html>
	
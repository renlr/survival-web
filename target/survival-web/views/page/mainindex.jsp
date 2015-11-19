<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mainindex.html</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
</head>
<frameset border=0 rows="76,*" frameSpacing=0 frameBorder=0 cols=* style="z-index:1">
	<frame id="topFrame" name=topFrame src="${pageContext.request.contextPath}/top.do" style="z-index:1">
	<frameset id="bodyFrame" border=0 cols="210,*" frameSpacing=0 frameBorder=NO noresize scrolling="yes" style="z-index:1">
		<frame id="left" name=left src="${pageContext.request.contextPath}/left.do" style="z-index:1">
		<frame id="right" name=right src="${pageContext.request.contextPath}/main.do" style="z-index:1">
	</frameset>
</frameset>
</html>

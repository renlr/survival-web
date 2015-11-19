<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>添加资讯</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"  />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/views/css/admin_style.css'>
	<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/views/css/dialog.css'>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/views/js/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/views/js/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/views/js/kidediter/themes/default/default.css">
	<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/jquery-1.10.1.min.js"></script>
	<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/common.js"></script>
	<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/jquery.easyui.min.js"></script>
	<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/easyui-lang-zh_CN.js"></script>
	<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/kidediter/kindeditor-min.js"></script>
	<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/kidediter/lang/zh_CN.js"></script>
  </head>
  <script type="text/javascript">
	  KindEditor.ready(function(K){
			var editor = K.create("textarea[name='content']",{
				urlType : "domain",
				filterMode : false,
				allowFileManager : true,
				imageUploadLimit : 200,
				imageSizeLimit : "10MB",
				cssPath : "${pageContext.request.contextPath}/views/js/kidediter/plugins/code/prettify.css",
				uploadJson : "${pageContext.request.contextPath}/views/commons/upload_json.jsp",
				fileManagerJson : "${pageContext.request.contextPath}/views/commons/file_manager_json.jsp"
			});
		});
  </script>
  <body>
    <form action="${pageContext.request.contextPath}/dynamic/save.do" method="post" id="dynamicForm" enctype="multipart/form-data">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
		<tr class="ji">
				<td class="rt">文章标题：</td>
				<td class="ji"><input type="text" name="dynamic" id="dynamic" style="width:200px;" maxlength="400" class="input"></td>
			</tr>
		 
			<tr class="ji">
				<td class="rt">文章出处：</td>
				<td class="ji"><input type="text" name="phone" id="phone" style="width:200px;" maxlength="400" class="input"></td>
			</tr>
			<tr class="ji">
				<td class="rt">类别：</td>
				<td class="ji"><input type="text" name="email" id="email" style="width:200px;" maxlength="400" class="input"></td>
			</tr>
			<tr class="ji">
				<td class="rt">网站编辑：</td>
				<td class="ji"><input type="text" name="email" id="email" style="width:200px;" maxlength="400" class="input"></td>
			</tr>
			<tr class="ji">
				<td colspan="2">
    				<textarea name="content" id="content" style="width:100%;height:700px;visibility:hidden;"></textarea>
				</td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id" id="id">
					<input class="bginput" type="submit" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butrs" value="关闭">
				</td>
			</tr>
		</table>
		</form>
  </body>
</html>

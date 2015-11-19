<%@page import="com.baofeng.carebay.util.Constants"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>减肥瘦身内容</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
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
		K("input[name='butSubmit']").click(function(e){
			var content = editor.html();
			var url = "${pageContext.request.contextPath}/slimDetails/saveContent.do";
			$.ajax({
				type: "post",
				dataType: "json",
				url: url,
				data: "id=${details.id}&gid=${curse.id}&content="+encodeURIComponent(content),
				success: function(data){
					$("#iframeId").attr("src","<%=Constants.DEFAULT_HTTPHTML %>/${details.id}.html?t="+new Date().getTime());
				},
				error : function(data){}
			});
		});
		K("input[name='butClose']").click(function(e){
			editor.html("");
		});
	});
	$(document).ready(function() {
		$("#content").val(decodeURIComponent("${details.content}"));
		$("#butSubmit").click(function(){
			$("#iframeId").attr("src","<%=Constants.DEFAULT_HTTPHTML %>/${details.id}.html?t="+new Date().getTime());
		});
	});
	
</script>
</head>
<body>
	<table id="curseDetails_grid"></table>
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/slimDetails/save.do" method="post" id="curseDetailsForm">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji" height="50">
				<td colspan="2" align="justify">编辑${details.name}内容：</td>
			</tr>
			<tr class="ji">
				<td width="800" height="700">
					<textarea name="content" id="content" style="width:100%;height:700px;visibility:hidden;"></textarea>
				</td>
				<td>
					<iframe name="tempHtmls" id="iframeId" width="100%" height="100%" frameborder="0" border=0 scrolling="auto" marginwidth="0" marginheight="0" src="<%=Constants.DEFAULT_HTTPHTML %>/${details.id}.html"></iframe>
				</td>
			</tr>
			<tr class="tr ct">
				<td>
					<input type="hidden" name="id" id="id" value="${details.id}">
					<input type="hidden" name="groupId" id="groupId" value="${curse.id}"> 
					<input class="bginput" type="button" name="butSubmit" value="保存">
				</td>
				<td>
					<input type="hidden" name="id" id="id" value="${details.id}">
					<input type="hidden" name="groupId" id="groupId" value="${curse.id}"> 
					<input class="bginput" type="button" id="butSubmit" value="刷新">
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>
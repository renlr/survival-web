<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<title>公司地址</title>

<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"  />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/views/css/admin_style.css'>
<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/views/css/dialog.css'>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/views/js/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/views/js/themes/icon.css">
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/jquery-1.10.1.min.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/common.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/jquery.easyui.min.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/easyui-lang-zh_CN.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/views/js/kindeditor/themes/default/default.css" />
		<script charset="utf-8" src="${pageContext.request.contextPath}/views/js/kindeditor/kindeditor-min.js"></script>
		<script charset="utf-8" src="${pageContext.request.contextPath}/views/js/kindeditor/lang/zh_CN.js"></script>
	<script type="text/javascript">
	
			var editor;
			KindEditor.ready(function(K) {
				editor = K.create('textarea[name="content"]', {
					allowFileManager : true
				});
			});
			
			
		$(document).ready(function() {
		
		var option ="${dynamic.type}";
		$("#type").val(option);
		$("#butrs").click(function(){
			window.history.back(-1);
		});
		
		$("#dynamicForm").submit(function(){
			var title=$("#title").val();
			
			if($.trim(title) == ""){ 
				alert("请输入标题！"); 
				$("#title").focus(); 
				return false; 
			}
 
		});
	});
			
	</script>
</head>
<body>
		<form action="${pageContext.request.contextPath}/dynamic/save.do" method="post" id="dynamicForm" enctype="multipart/form-data">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table" >
			    <tr class="ji">
				<td  colspan="4" align="center"><span style="font-size: 20px">资讯信息</span></td>
			</tr>
		    <tr class="ji">
				<td class="rt">文章标题：<label style="color: red;">*</label></td>
				<td class="ji"><input type="text" name="title" id="title" value="${dynamic.title}" style="width:200px;" maxlength="400" class="input"></td>
				<td class="rt">文章出处：</td>
				<td class="ji"><input type="text" name="source" id="source" value="${dynamic.source}" style="width:200px;" maxlength="400" class="input"></td>
			</tr>
			<tr class="ji">
				<td class="rt">类别：</td>
				<td class="ji"><select id="type" name="type" style="width:200px;" >
							<option value="0" >暴风资讯</option>
							<option value="1" >行业资讯</option>
					         </select>
			   </td>
			   				<td class="rt">网站编辑：</td>
				<td class="ji"><input type="text" name="author" id="author" style="width:200px;" value="${dynamic.author} "  maxlength="400" class="input"></td>
		
			</tr>
			<tr class="ji">
				<td  colspan="4" align="center" >
				    <textarea name="content" style="width:900px;height:500px;visibility:hidden;"   >${dynamic.content}</textarea>
				</td>
			</tr>
			<tr class="tr ct">
				<td colspan="4" >
					<input type="hidden" name="id" id="id" value="${dynamic.id}">
					<input class="bginput" type="submit" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="button" name="Input"  id="butrs" value="关闭">
				</td>
			</tr>
			
		</table>
		</form>

</body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>游戏产品</title>

<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"  />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/views/css/admin_style.css'>
<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/views/css/dialog.css'>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/views/js/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/views/js/kindeditor/jquery-ui/css/smoothness/jquery-ui-1.9.2.custom.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/views/js/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/views/js/kindeditor/themes/default/default.css">
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/jquery-1.10.1.min.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/kindeditor/kindeditor-min.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/kindeditor/lang/zh_CN.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/common.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/jquery.easyui.min.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/easyui-lang-zh_CN.js"></script>
<!-- <script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/kindeditor/jquery-ui/js/jquery-ui-1.9.2.custom.js"></script> -->

</head>
<body>
	<table id="product_grid" title="首页轮播"></table>
	<div id="delete_div">确定删除所选内容?</div>
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/product/save.do"
			method="post" id="friendlyForm" enctype="multipart/form-data">
			<table width="98%" border="0" cellpadding="4" cellspacing="1"
				class="table">
				<tr class="ji">
					<td class="rt">游戏名称：<label style="color: red;">*</label></td>
					<td class="ji"><input type="text" name="gameName"
						id="gameName" style="width:200px;" maxlength="400" class="input"></td>
					<td class="rt">游戏大小：<label style="color: red;">*</label></td>
					<td class="ji"><input type="text" name="size" id="size"
						style="width:200px;" maxlength="400" class="input"></td>
				</tr>
				<tr class="ji">
					<td class="rt">语言：<label style="color: red;">*</label></td>
					<td class="ji"><input type="text" name="language"
						id="language" style="width:200px;" maxlength="400" class="input"></td>
					<td class="rt">游戏版本：<label style="color: red;">*</label></td>
					<td class="ji"><input type="text" name="version" id="version"
						style="width:200px;" maxlength="400" class="input"></td>
				</tr>

				<tr class="ji">
					<td class="rt">上线时间：<label style="color: red;">*</label></td>
					<td class="ji"><input type="text" name="date" id="date"
						style="width:200px;" maxlength="400" class="input"></td>
					<td class="rt">游戏logo：<label style="color: red;">*</label></td>
					<td><input type="file" name="images" id="images" size="25"></td>
				</tr>
				<tr class="ji">
					<td class="rt" colspan="1">官网地址：<label style="color: red;">*</label></td>
					<td class="ji" colspan="3"><input type="text" name="homeUrl"
						id="homeUrl" style="width:400px;" maxlength="800" class="input"></td>
				</tr>
				<tr class="tr">
					<td class="rt">安卓下载码：<label style="color: red;">*</label></td>
					<td><input type="file" name="imageAnd" id="imageAnd" size="25"></td>
					<td class="rt">安卓下载地址：<label style="color: red;">*</label></td>
					<td class="ji"><input type="text" name="androidUrl"
						id="androidUrl" style="width:200px;" maxlength="400" class="input"></td>


				</tr>

				<tr class="ji">
					<td class="rt">苹果下载码：<label style="color: red;">*</label></td>
					<td><input type="file" name="imageIOS" id="imageIOS" size="25"></td>
					<td class="rt">苹果下载地址：<label style="color: red;">*</label></td>
					<td class="ji"><input type="text" name="IOSUrl" id="IOSUrl"
						style="width:200px;" maxlength="400" class="input"></td>

				</tr>
				<tr class="tr">
					<td class="rt">苹果越狱码：<label style="color: red;">*</label></td>
					<td><input type="file" name="imageIOSY" id="imageIOSY"
						size="25"></td>
					<td class="rt">越狱下载地址：<label style="color: red;">*</label></td>
					<td class="ji"><input type="text" name="IOSYUrl" id="IOSYUrl"
						style="width:200px;" maxlength="400" class="input"></td>


				</tr>

				<tr class="ji">
					<td class="rt">是否首页推荐：<label style="color: red;">*</label></td>
					<td class="ji"><select id="isOnHomePage" name="isOnHomePage"
						class="easyui-combobox" style="width:200px;">
							<option value="0">否</option>
							<option value="1">是</option>
					</select></td>
					<td class="rt">首页显示顺序：<label style="color: red;">*</label></td>
					<td><input type="text" name="homeOrder" id="homeOrder"
						size="4" maxlength="10" class="easyui-numberbox input">降序排列</td>

				</tr>

				<tr class="ji">
					<td class="rt">是否热门推荐：<label style="color: red;">*</label></td>
					<td class="ji"><select id="isRecommend" name="isRecommend"
						class="easyui-combobox" style="width:200px;">
							<option value="0">否</option>
							<option value="1">是</option>
					</select></td>
					<td class="rt">热门显示顺序：<label style="color: red;">*</label></td>
					<td><input type="text" name="listOrder" id=listOrder size="4"
						maxlength="10" class="easyui-numberbox input">降序排列</td>
				</tr>
				<tr class="ji">
					<td class="rt" colspan="4"><textarea name="content" style="width:700px;height:200px;">输入游戏描述</textarea></td>
				</tr>
				<tr class="tr ct">
					<td colspan="4"><input type="hidden" name="id" id="id">
						<input class="bginput" type="submit" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input
						class="bginput" type="reset" name="Input" id="butrs" value="关闭">
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
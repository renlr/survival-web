<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>系统管理用户</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/views/css/admin_style.css'>
<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/views/css/dialog.css'>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/views/js/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/views/js/themes/icon.css">
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/jquery-1.10.1.min.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/common.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/jquery.easyui.min.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var pageSize = Math.round(($("#right", window.parent.document).height() - 160) / 23.5);
		$("#ops_grid").datagrid({
			url : "${pageContext.request.contextPath}/ops/readPages.do",
			datatype : "json",
			mtype : "POST",
			nowrap : false,
			striped : true,
			fitColumns : true, 
			fit : true,
			pageSize: pageSize,
			pageList: [pageSize,30,40,50],
			pagination : true,
			sortname : 'id',
			singleSelect : true,
			rownumbers : true,
			toolbar : [ '-', {
				id : 'btnClearSelections',
				text : "添加管理用户",
				iconCls : 'icon-add',
				handler : function() {
					$("#div_dialog").dialog({
						title:"添加管理用户",
						show:"slide",
						resizable:false,
						closed: false,
						resizable:false
					});
				}
			}, '-', {
				id : 'btnDel',
				text : "修改",
				iconCls : 'icon-edit',
				handler : function() {
					var row = $("#ops_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/ops/edit.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#id").val(data.id);
				          	$("#name").val(data.name);
				          	$("#phone").val(data.phone);
				          	$("#email").val(data.email);
				          	$("#accounts").val(data.accounts);
				          	$("#password").val(data.password);
				          	$("#div_dialog").dialog();
				          },
						  error : function(data){
						  	alert("修改系统管理用户失败!");
						  }
					});
				}
			}, '-', {
				id : 'btnDel',
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#ops_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var delUrl = "${pageContext.request.contextPath}/ops/delete.do?t="+new Date().getTime();
		            $.ajax({
				          type: "get",
				          dataType: "json",
				          url: delUrl,
				          data: "id="+id,//要发送的数据
				          success: function(data){},
						  error : function(data){
						  	$("#ops_grid").datagrid('reload'); 
						  }
					});
				}
			}, '-', {
				id : 'btnDel',
				text : "禁用",
				iconCls : 'icon-edit',
				handler : function() {
					var row = $("#ops_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
				}
			}],
			columns : [ [ {
				field : 'accounts',
				title : '登录帐号',
				width : $(this).width() * 0.2,
				align : 'center',
				sortable : true
			}, {
				field : 'name',
				title : '管理员名称',
				width : $(this).width() * 0.2,
				align : 'center',
				sortable : true
			}, {
				field : 'phone',
				title : '手机号码',
				width : $(this).width() * 0.2,
				align : 'center',
				sortable : true
			}, {
				field : 'email',
				title : '常用邮箱',
				width : $(this).width() * 0.2,
				align : 'center',
				sortable : true
			}, {
				field : 'accountStatus',
				title : '账号状态',
				width : $(this).width() * 0.2,
				align : 'center',
				sortable : true,
				formatter : function(value,rec){
					if(null != value){
						if(value == "ENABLED"){
							return "启用";
						}else{
							return "禁用";
						}
					}
					return "  ";
				}
			}, {
				field : 'createDT',
				title : '创建时间',
				width : $(this).width() * 0.2,
				align : 'center',
				sortable : true,
				formatter : function(dateValue,rec){
					return dateValue.toString().toDate().format("yyyy-MM-dd hh:mm:ss");
				}
			} ] ]
		});
		$($("#ops_grid").datagrid("getPager")).pagination({
			beforePageText : '第',
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		});
		
		$("#opsForm").submit(function(){
			var id=$("#id").val(); 
			var accounts=$("#accounts").val(); 
			var password=$("#password").val(); 
			var name=$("#name").val(); 
			var email=$("#email").val(); 
			var phone=$("#phone").val(); 
			
			if($.trim(accounts) == ""){ 
				alert("登录帐号不能为空！"); 
				$("#accounts").focus(); 
				return false; 
			}
			if($.trim(accounts).length < 4){
				alert("登录帐号必须4个字符以上长度！"); 
				$("#accounts").focus(); 
				return false; 
			}
			if(id == null && !isACCONTs(accounts)){
				alert("请重新输入登录帐号，此帐号已存在!");
				$("#accounts").focus(); 
			}
						
			if($.trim(password) == ""){
				alert("登录密码不能为空！"); 
				$("#password").focus(); 
				return false; 
			}
			if($.trim(password).length < 6){
				alert("登录密码必须6位以上字符！"); 
				$("#password").focus(); 
				return false; 
			}
			if($.trim(name) == ""){
				alert("管理员名称不能为空！"); 
				$("#name").focus(); 
				return false; 
			}
			if($.trim(email) == ""){
				alert("常用邮箱不能为空！"); 
				$("#email").focus(); 
				return false; 
			}
			if($.trim(phone) == ""){
				alert("手机号码不能为空！"); 
				$("#phone").focus(); 
				return false; 
			}
		});
			
		$("#detection").bind("click",function(){
			var id=$("#id").val(); 
			var accounts=$("#accounts").val(); 
			if(id == null && !isACCONTs(accounts)){
				alert("请重新输入登录帐号，此帐号已存在!");
				$("#accounts").focus(); 
			}
		});
		
		function isACCONTs(accounts){
			if($.trim(accounts) != ""){
				var delUrl = "${pageContext.request.contextPath}/ops/isAccounts.do?t="+new Date().getTime();
		        $.ajax({
				 	type: "get",
				    dataType: "json",
				    url: delUrl,
				    data: "accouncts="+accounts,//要发送的数据
				    success: function(data){
				    	return true;
				    },
					error : function(data){
				    	return false;
					}
				});
			}
		}
	});
</script>
</head>
<body>
	<table id="ops_grid" title="系统管理用户"></table>
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/ops/save.do" method="post" name="opsForm" id="opsForm">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt" align="right">登录帐号：</td>
				<td>
					<input type="text" name="accounts" id="accounts" size="25" maxlength="50" class="input">*必填
					<input class="bginput" type="button" id="detection" value="检测重复">
				</td>
			</tr>
			<tr class="ji">
				<td class="rt" align="right">登录密码：</td>
				<td><input type="password" name="password" id="password" size="25" maxlength="50" class="input">*必填</td>
			</tr>
			<tr class="tr">
				<td class="rt" align="right">管理员名称：</td>
				<td><input type="text" name="name" id="name" size="25" maxlength="50" class="input">*必填</td>
			</tr>
			<tr class="ji">
				<td class="rt" align="right">常用邮箱：</td>
				<td><input type="text" name="email" id="email" size="25" maxlength="50" class="input">*必填</td>
			</tr>
			<tr class="tr">
				<td class="rt" align="right">手机号码：</td>
				<td><input type="text" name="phone" id="phone" size="25" maxlength="50" class="input">*必填</td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id" id="id"> 
					<input class="bginput" type="submit" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" value="重置">
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>
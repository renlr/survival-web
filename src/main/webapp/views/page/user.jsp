<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<head>
<title>系统管理帐号</title>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/views/css/admin_style.css'>
<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/views/css/dialog.css'>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/views/js/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/views/js/themes/icon.css">
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/jquery-1.10.1.min.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/common.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/jquery.easyui.min.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/easyui-lang-zh_CN.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.2.1.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var pageSize = Math.round(($("#right", window.parent.document).height() - 160) / 23.5);
		$("#user_grid").datagrid({
			url : "${pageContext.request.contextPath}/user/readPages.do",
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
				text : "添加",
				iconCls : 'icon-add',
				handler : function() {
					$("#div_dialog").dialog({
						title:"添加管理帐号",
						show : "slide",
						resizable : true,
						closed : false
					});					
				}
			}, '-', {
				id : 'btnDel',
				text : "修改",
				iconCls : 'icon-edit',
				handler : function() {
					var row = $("#user_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
					var editUrl = "${pageContext.request.contextPath}/user/edit.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editUrl,
				          data: "id="+id,//要发送的数据
				          success: function(data){
				          		$("#id").val(data.id);
								$("#accounts").val(data.accounts);
								$("#accounts").attr("disabled",true);
								$("#name").val(data.name);
								$("#password").val(data.password);
								$("#phone").val(data.phone);
								$("#email").val(data.email);
								if(data.callService == 1)
									$("input[name='callService']").attr("checked","checked"); 
								$("#type").combobox("setValue",data.type);
								if(data.type == 1){
									$("#business").combobox("select",data.business);
									$("#bus").show();
								}
				          		$("#div_dialog").dialog({
				          			title:"修改管理帐号",
									show : "slide",
									resizable : true,
									closed : false
				          		});
				          },
						  error : function(data){}
					});
											            
				}
			},'-', {
				id : 'butDis',
				text : "禁用|启用",
				iconCls : 'icon-redo',
				handler : function() {
					var row = $("#user_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var disUrl = "${pageContext.request.contextPath}/user/disable.do";
		            $.ajax({
				          type: "get",
				          dataType: "json",
				          url: disUrl,
				          data: "id="+id,//要发送的数据
				          success: function(data){
				          	if("success" == data.resultMessage){
								$("#user_grid").datagrid("reload"); 
							}
				          },
						  error : function(data){}
					});
				}
			}, '-', {
				id : "btnDel",
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#user_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var delUrl = "${pageContext.request.contextPath}/user/delete.do";
		            $("#delete_div").dialog({
						title : "删除提示",
						show:"slide",
		            	height : 130,
						width : 200,
						modal : true,
						closeOnEscape : true,
						buttons: [{ 
							text: "确定", 
							handler: function() {
								$.ajax({
							          type: "get",
							          dataType: "json",
							          url: delUrl,
							          data: "id="+id,//要发送的数据
							          success: function(data){
							          	if("success" == data.resultMessage){
											$("#user_grid").datagrid("reload"); 
										}
							          },
									  error : function(data){}
								});
								$("#delete_div").dialog("close");
							}}, {
							text: "取消", 
							handler: function() { 
								$("#delete_div").dialog("close");
							} 
						}] 
		            });
				}
			}],
			columns : [ [ {
				field : "accounts",
				title : "管理员帐号",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			}, {
				field : "name",
				title : "管理员名称",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			}, {
				field : "type",
				title : "帐号类型",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					if(value == 1){
						return "会所帐号";
					}
					return "系统帐号";
				}
			},{
				field : "status",
				title : "帐号状态",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					if(value == 1)
						return "<font color='red'>禁用</font>";
					return "<font color='green'>正常</font>";
				}
			},{
				field : "chamber",
				title : "所属会所",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "phone",
				title : "联系电话",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "email",
				title : "电子邮件",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "callService",
				title : "通知服务",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					var htmls = "";
					if(value == 1)
						htmls = "是";
					return htmls;
				}
			},{
				field : "menuitems",
				title : "菜单页面",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					if(rec.type == 1)
						return '<a href="${pageContext.request.contextPath}/user/details.do?id='+rec.id+'">页面管理</a>';
				}
			},{
				field : "createDt",
				title : "创建时间",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(dateValue,rec){
					return dateValue.toString().toDate().format("yyyy-MM-dd hh:mm:ss");
				}
			}]]
		});
		$("#butClose").click(function(){
			$("#id").val("");
			$("#accounts").val("");
			$("#name").val("");
			$("#password").val("");
			$("#phone").val("");
			$("#email").val("");
			$("#div_dialog").dialog("close");
		});
		$("#type").combobox({
			onSelect:function(data){
				if(data.value == 0){
					$("#bus").hide();
				}else if(data.value == 1){
					$("#bus").show();
				}
			}
		});
		$("#chamber").combogrid({
			delay:500,
			mode:"remote",
			datatype : "json",
			fit : true,
			nowrap : true,
			striped : true,
			rownumbers : true,
			fitColumns : true,
			pagination : true,
			panelWidth: 450,
			mtype : "POST",
			url:"${pageContext.request.contextPath}/chamber/readPages.do?t="+new Date().getTime(),
			idField:"id",
			textField:"name",
			columns:[[
				{field : "ck",checkbox : true},
				{field:"name",title:"会所名称",width:120,sortable:true}
			]],
			pagination : true,
			pageSize: 30,
			pageList: [5,10,20,30,40,50]
		});
			
		$("#userForm").submit(function(){
			var reg = /^[a-zA-Z0-9]+$/;
			var name=$("#name").val(); 
			var accounts=$("#accounts").val();
			var password=$("#password").val();
			var type=$("#type").combobox("getValue");
			if(accounts==""){ 
				alert("管理帐号不能为空！"); 
				$("#accounts").focus(); 
				return false; 
			}		
			if(!reg.test(accounts)){
				alert("管理帐号必须是字母与数字组成！"); 
				$("#accounts").focus(); 
				return false; 
			}
			if(name == ""){
				alert("管理员名称不能为空！"); 
				$("#name").focus(); 
				return false; 
			}
			if(password == ""){
				alert("登录密码不能为空！"); 
				$("#password").focus(); 
				return false; 
			}
			if(password.length < 6){
				alert("登录密码不得小于6个字符！"); 
				$("#password").focus(); 
				return false; 
			}
			$("#tempType").val(type);
			if(type == 1){
				var chamber=$("#chamber").combobox("getValue");
				if(chamber == undefined || chamber == ""){
					alert("必须选择所属会所!"); 
					$("#chamber").focus(); 
					return false;						
				}
			}
		});
	});
</script>
</head>
<body>
	<table id="user_grid" title="系统管理帐号"></table>
	<div id="delete_div">确定删除所选内容?</div> 
	<div id="div_dialog">
	<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
		<form action="${pageContext.request.contextPath}/user/saveUser.do" method="post" id="userForm">
		<tr class="ji">
			<td class="rt" width="25%">管理帐号：<label style="color: red;">*</label></td>
			<td><input type="text" name="accounts" id="accounts" size="20" maxlength="10" class="input">（必须是英文字母或数字组成）</td>
		</tr>
		<tr class="tr">
			<td class="rt">管理员名称：<label style="color: red;">*</label></td>
			<td><input type="text" name="name" id="name" size="30" maxlength="20" class="input"></td>
		</tr>
		<tr class="ji" id="channelseo">
			<td class="rt">登录密码：<label style="color: red;">*</label></td>
			<td><input type="password" name="password" id="password" size="20" maxlength="32" class="input">可以修改密码</td>
		</tr>
		<tr class="tr">
			<td class="rt">联系电话：</td>
			<td><input type="text" name="phone" id="phone" size="20" maxlength="20" class="input"></td>
		</tr>
		<tr class="tr">
			<td class="rt">电子邮件：</td>
			<td><input type="text" name="email" id="email" size="50" maxlength="50" class="input"></td>
		</tr>
		<tr class="tr">
			<td class="rt">通知服务：</td>
			<td><input type="checkbox" name="callService" id="callService" value="1"/></td>
		</tr>
		<c:if test="${empty sessionScope.session_user.chamber}">
		<tr class="tr">
			<td class="rt">帐号类型：<label style="color: red;">*</label></td>
			<td>
				<select name="type" id="type" class="easyui-combobox input" style="width:200px;">
					<option value="0">系统帐号</option>
					<option value="1">会所帐号</option>
				</select>
			</td>
		</tr>
		<tr class="tr" id="bus" style="display:none;">
			<td class="rt" align="right">会所列表：<label style="color: red;">*</label></td>
			<td><input type="text" id="chamber" name="chamber" class="easyui-combobox input"></td>
		</tr>
		</c:if>
		<tr class="tr ct">
			<td colspan="2">
				<input type="hidden" name="id" id="id"> 
				<input type="hidden" name="tempType" id="tempType"> 
				<input type="submit" id="buts" value="提交" class="bginput">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butrs" value="关闭">
			</td>
		</tr>
		</form>
	</table>
	</div>
</body>
</html>

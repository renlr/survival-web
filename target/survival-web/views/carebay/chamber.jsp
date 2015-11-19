<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>会所管理</title>
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
		$("#chamber_grid").datagrid({
			url : "${pageContext.request.contextPath}/chamber/readPages.do",
			datatype : "json",
			mtype : "POST",
			fit : true,
			nowrap : false,
			striped : true,
			fitColumns : true, 
			pagination : true,
			rownumbers : true,
			singleSelect : true,
			pageSize: pageSize,
			pageList: [pageSize,30,40,50],
			sortname : 'id',
			toolbar : [ '-', {
				id : 'btnClearSelections',
				text : "添加会所",
				iconCls : 'icon-add',
				handler : function() {
					if("${sessionScope.session_user.chamber}" == ""){
						$("#div_dialog").dialog({
							title:"添加会所",
							show:"slide",
							resizable:false,
							closed: false,
							resizable:false
						});					
					}else{
						alert("无权限添加会所!！","提示消息");	
					}
				}
			},'-', {
				id : 'btnDel',
				text : "修改",
				iconCls : 'icon-edit',
				handler : function() {
					var row = $("#chamber_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/chamber/edit.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#id").val(data.id);
				          	$("#name").val(data.name);
				          	$("#telPhone").val(data.telPhone);
				          	$("#manager").val(data.manager);
				          	$("#address").val(data.address);
				          	$("#leavePWD").val(data.leavePWD);
				          	$("#resetPWD").val(data.resetPWD);
				          	$("#div_dialog").dialog();
				          },
						  error : function(data){
						  	$("#chamber_grid").datagrid("reload"); 
						  }
					});
				}
			}, '-', {
				id : 'btnDel',
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#chamber_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var ids = row[0].id;
		            var delUrl = "${pageContext.request.contextPath}/chamber/delete.do?t="+new Date().getTime();
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
								data: "id="+ids,//要发送的数据
								success: function(data){
									if("success" == data.resultMessage){
										$("#chamber_grid").datagrid("reload"); 
								    }
								},
								error : function(data){
									$("#chamber_grid").datagrid("reload"); 
								}
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
			columns : [[{
				field : "name",
				title : "会所名称",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "telPhone",
				title : "联系电话",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "manager",
				title : "会所经理",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "address",
				title : "会所地址",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "leavePWD",
				title : "离所密码",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "resetPWD",
				title : "重置密码",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "cham",
				title : "会所介绍",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return '<a href="${pageContext.request.contextPath}/chamber/details.do?gid='+rec.id+'&inds=1">会所介绍</a>';
				}
			},{
				field : "vips",
				title : "会员手册",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return '<a href="${pageContext.request.contextPath}/chamber/details.do?gid='+rec.id+'&inds=2">会员手册</a>';
				}
			},{
				field : "createDT",
				title : "创建时间",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(dateValue,rec){
					return dateValue.toString().toDate().format("yyyy-MM-dd hh:mm:ss");
				}
			}]]
		});
		$("#butrs").click(function(){
			$("#id").val("");
			$("#name").val("");
			$("#div_dialog").dialog("close");
		});
		$("#chamberForm").submit(function(){
			var name = $("#name").val(); 
			var leavePWD = $("#leavePWD").val(); 
			var resetPWD = $("#resetPWD").val(); 
			if($.trim(name) == ""){
				alert("会所名称不能为空！"); 
				$("#name").focus(); 
				return false; 
			}
			if($.trim(leavePWD) == ""){
				alert("离所密码不能为空！"); 
				$("#leavePWD").focus(); 
				return false; 
			}
			if($.trim(resetPWD) == ""){
				alert("重置密码不能为空！"); 
				$("#resetPWD").focus(); 
				return false; 
			}
		});
	});
</script>
</head>
<body>
	<table id="chamber_grid" title="会所管理"></table>
	<div id="delete_div">确定删除所选内容吗？</div> 
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/chamber/save.do" method="post" id="chamberForm">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt">会所名称：<label style="color: red;">*</label></td>
				<td class="ji"><input type="text" name="name" id="name" style="width:200px;" maxlength="200" class="input"></td>
			</tr>
			<tr class="tr">
				<td class="rt">离所密码：<label style="color: red;">*</label></td>
				<td class="ji"><input type="text" name="leavePWD" id="leavePWD" style="width:300px;" maxlength="200" class="input"></td>
			</tr>
			<tr class="tr">
				<td class="rt">重置密码：<label style="color: red;">*</label></td>
				<td class="ji"><input type="text" name="resetPWD" id="resetPWD" style="width:300px;" maxlength="200" class="input"></td>
			</tr>
			<tr class="ji">
				<td class="rt">联系电话：</td>
				<td class="ji"><input type="text" name="telPhone" id="telPhone" maxlength="20" class="input"></td>
			</tr>
			<tr class="ji">
				<td class="rt">会所经理：</td>
				<td class="ji"><input type="text" name="manager" id="manager" maxlength="20" class="input"></td>
			</tr>
			<tr class="tr">
				<td class="rt">会所地址：</td>
				<td class="ji"><input type="text" name="address" id="address" style="width:300px;" maxlength="200" class="input"></td>
			</tr>
			
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id" id="id"> 
					<input class="bginput" type="submit" id="buts" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butrs" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>
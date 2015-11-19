<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<head>
<head>
<title>馨月用户</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href='${pageContext.request.contextPath}/views/css/admin_style.css'>
<link rel="stylesheet" href='${pageContext.request.contextPath}/views/css/dialog.css'>
<link rel="stylesheet" href="${pageContext.request.contextPath}/views/js/themes/default/easyui.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/views/js/themes/icon.css">
<script src="${pageContext.request.contextPath}/views/js/jquery-1.10.1.min.js"></script>
<script src="${pageContext.request.contextPath}/views/js/common.js"></script>
<script src="${pageContext.request.contextPath}/views/js/jquery.easyui.min.js"></script>
<script src="${pageContext.request.contextPath}/views/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var pageSize = Math.round(($("#right", window.parent.document).height() - 160) / 23.5);
		$("#cuser_grid").datagrid({
			url : "${pageContext.request.contextPath}/care/readChamberPages.do",
			datatype : "json",
			mtype : "POST",
			nowrap : false,
			striped : true,
			fitColumns : true, 
			fit : true,
			pageSize: 30,
			pageList: [pageSize,30,40,50],
			pagination : true,
			sortname : 'id',
			singleSelect : true,
			rownumbers : true,
			toolbar : [{
				id : 'btnAdd',
				text : "添加用户",
				iconCls : 'icon-add',
				handler : function() {
					$("#div_dialog").dialog({
						title:"添加用户",
						show:"slide",
						resizable:true,
						closed: false
					});
				}
			},'-', {
				id : 'btnDel',
				text : "修改",
				iconCls : 'icon-edit',
				handler : function() {
					var row = $("#cuser_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/care/edit.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#id").val(data.id);
				          	$("#room").val(data.room);
				          	$("#name").val(data.name);
				          	if(data.birthDate != null)
								$("#birthDate").datebox("setValue",data.birthDate.toString().toDate().format("yyyy-MM-dd"));
							$("#homeAddress").val(data.homeAddress);
							$("#phone").val(data.phone);
							$("#emPhone").val(data.emPhone);
							if(data.checkInDT != null){
								$("#checkInDT").datebox("setValue",data.checkInDT.toString().toDate().format("yyyy-MM-dd hh:mm"));
							}
				          	$("#div_dialog").dialog();
				          },
						  error : function(data){
						  	alert("修改馨月用户失败!");
						  }
					});
				}
			}, '-', {
				id : 'btnDel',
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#cuser_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var delUrl = "${pageContext.request.contextPath}/care/delete.do?t="+new Date().getTime();
		            $.ajax({
				          type: "get",
				          dataType: "json",
				          url: delUrl,
				          data: "id="+id,//要发送的数据
				          success: function(data){
				          	$("#cuser_grid").datagrid("reload"); 
				          },
						  error : function(data){}
					});
				}
			}],
			columns : [[{
				field : "room",
				title : "会所房间",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "name",
				title : "用户姓名",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "birthDate",
				title : "出生日期",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(date,rec){
					if(date != null)
						return date.toString().toDate().format("yyyy-MM-dd");
					return "";
				}
			},{
				field : "homeAddress",
				title : "家庭住址",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "phone",
				title : "手机号码",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "emPhone",
				title : "紧急电话",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "chamber",
				title : "所在会所",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "accountStatus",
				title : "账号状态",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					if(null != value){
						if(value == "ENABLED"){
							return "正常";
						}else{
							return "禁用";
						}
					}
					return "";
				}
			},{
				field : "yinshi",
				title : "饮食调查",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return '<a href="${pageContext.request.contextPath}/qans/show.do?gid='+rec.id+'&inds=1">饮食调查</a>';
				}
			},{
				field : "service",
				title : "服务调查",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return '<a href="${pageContext.request.contextPath}/qans/show.do?gid='+rec.id+'&inds=2">服务调查</a>';
				}
			},{
				field : "checkInDT",
				title : "入所时间",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(date,rec){
					if(date != null)
						return date.toString().toDate().format("yyyy-MM-dd hh:mm");
					return "";
				}
			},{
				field : "checkOutDT",
				title : "离所时间",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(date,rec){
					if(date != null)
						return date.toString().toDate().format("yyyy-MM-dd hh:mm");
					return "&nbsp;";
				}
			},{
				field : "createDT",
				title : "创建时间",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(date,rec){
					return date.toString().toDate().format("yyyy-MM-dd hh:mm:ss");
				}
			}]]
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
		$("#butClose").click(function(){
			$("#id").val("");
			$("#room").val("");
			$("#name").val("");
			$("#birthDate").val("");
			$("#homeAddress").val("");
			$("#phone").val("");
			$("#emContact").val("");
			$("#checkInDT").val("");
			$("#div_dialog").dialog("close");
		});
		$("#cuserForm").submit(function(){
			var room=$("#room").val(); 
			var name=$("#name").val(); 
			var birthDate=$("#birthDate").datebox("getValue");
			var homeAddress=$("#homeAddress").val();
			var phone=$("#phone").numberbox("getValue");
			var emPhone=$("#emPhone").numberbox("getValue");
			var chamber=$("#chamber").combobox("getValue");
			if($.trim(room) == ""){ 
				alert("会所房间不能为空！"); 
				$("#room").focus(); 
				return false; 
			}
			if($.trim(name) == ""){ 
				alert("用户姓名不能为空！"); 
				$("#name").focus(); 
				return false; 
			}		
			if($.trim(birthDate) == ""){
				alert("出生日期必须选择!"); 
				$("#birthDate").focus(); 
				return false; 
			}
			if($.trim(homeAddress) == ""){
				alert("家庭住址不能为空！"); 
				$("#homeAddress").focus(); 
				return false; 
			}
			if($.trim(phone) == "" && phone.length != 11){
				alert("手机号码有误!"); 
				$("#phone").focus(); 
				return false; 
			}
			if($.trim(emPhone) == "" && emPhone.length != 11){
				alert("紧急电话有误!"); 
				$("#emPhone").focus(); 
				return false; 
			}
			if(chamber == undefined || chamber == ""){
				alert("必须选择所属会所!"); 
				$("#chamber").focus(); 
				return false;						
			}
		});
	});
</script>
</head>
<body>
	<table id="cuser_grid" title="馨月用户"></table>
	<div id="delete_div">确定删除所选内容?</div> 
	<div id="div_dialog" style="padding:5px;width:522px;height:370px;">
	<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
		<form action="${pageContext.request.contextPath}/care/save.do" method="post" id="cuserForm">
		<tr class="ji">
			<td class="rt">会所房间：<label style="color: red;">*</label></td>
			<td><input type="text" name="room" id="room" class="input"></td>
		</tr>
		<tr class="ji">
			<td class="rt">用户姓名：<label style="color: red;">*</label></td>
			<td><input type="text" name="name" id="name" class="input"></td>
		</tr>
		<tr class="tr">
			<td class="rt">出生日期：<label style="color: red;">*</label></td>
			<td><input type="text" class="easyui-datebox input" id="birthDate" name="birthDate" style="width:150px"></td>
		</tr>
		<tr class="ji">
			<td class="rt">家庭住址：<label style="color: red;">*</label></td>
			<td><input type="text" name="homeAddress" id="homeAddress" size="30" maxlength="20" class="input"></td>
		</tr>
		<tr class="tr">
			<td class="rt">手机号码：<label style="color: red;">*</label></td>
			<td><input type="text" class="easyui-numberbox input" name="phone" id="phone" data-options="min:0,precision:0"></td>
		</tr>
		<tr class="tr">
			<td class="rt">紧急电话：<label style="color: red;">*</label></td>
			<td><input type="text" class="easyui-numberbox input" name="emPhone" id="emPhone" data-options="min:0,precision:0"></td>
		</tr>
		<c:if test="${empty sessionScope.session_user.chamber}">
		<tr class="tr">
			<td class="rt" align="right">所属会所：<label style="color: red;">*</label></td>
			<td><input type="text" id="chamber" name="chamber" class="easyui-combobox input"></td>
		</tr>
		</c:if>
		<tr class="ji">
			<td class="rt">入所时间：</td>
			<td><input type="text" class="easyui-datetimebox input" id="checkInDT" name="checkInDT" data-options="required:true,showSeconds:false" style="width:150px"></td>
		</tr>
		<tr class="tr ct">
			<td colspan="2">
				<input type="hidden" name="id" id="id"> 
				<input class="bginput" type="submit" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" id="butClose" name="Input" value="关闭">
			</td>
		</tr>
		</form>
	</table>
	</div>
</body>
</html>
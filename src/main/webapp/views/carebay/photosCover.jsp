<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>用户图片</title>
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
		var pageSize = Math.round(($("#right", window.parent.document).height() - 160) / 105.5);
		$("#cover_grid").datagrid({
			url : "${pageContext.request.contextPath}/cover/readPages.do",
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
				text : "添加图片",
				iconCls : 'icon-add',
				handler : function() {
					$("#div_dialog").dialog({
						title:"添加用户图片",
						show:"slide",
						resizable:false,
						closed: false,
						resizable:false
					});
				}
			},'-', {
				id : 'btnEdit',
				text : "修改",
				iconCls : 'icon-edit',
				handler : function() {
					var row = $("#cover_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/cover/edit.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#id").val(data.id);
				          	$("#div_dialog").dialog();
				          },error : function(data){
						  	alert("修改失败!");
						  }
					});
				}
			},'-', {
				id : "btnDel",
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#cover_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var delUrl = "${pageContext.request.contextPath}/cover/delete.do?t="+new Date().getTime();
		            $.ajax({
				          type: "get",
				          dataType: "json",
				          url: delUrl,
				          data: "id="+id,//要发送的数据
				          success: function(data){
				          	$("#cover_grid").datagrid('reload'); 
				          },
						  error : function(data){
						  	$("#cover_grid").datagrid('reload'); 
						  }
					});
				}
			}],
			columns : [ [ {
				field : "babyImage",
				title : "宝宝头像",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return "<img src='"+value+"' width='200' height='100'/>";
				}
			},{
				field : "userImage",
				title : "用户头像",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return "<img src='"+value+"' width='200' height='100'/>";
				}
			},{
				field : "photosImage",
				title : "相册背景",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return "<img src='"+value+"' width='200' height='100'/>";
				}
			},{
				field : "personalImage",
				title : "个人中心",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return "<img src='"+value+"' width='200' height='100'/>";
				}
			},{
				field : "defaults",
				title : "",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					if(value != null){
						if(value == 1){
							return "系统数据";
						}
					}
					return "用户数据";
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
			} ] ]
		});
		$($("#cover_grid").datagrid("getPager")).pagination({
			beforePageText : "第",
			afterPageText : "页    共 {pages} 页",
			displayMsg : "当前显示 {from} - {to} 条记录   共 {total} 条记录"
		});
		$("#butSubmit").click(function(){
			$("#coverForm").form("submit",{
				onSubmit:function(){},
				success:function(data){
					$("#babyImage").val("");
					$("#userImage").val("");
					$("#photosImage").val("");
					$("#personalImage").val("");
					$("#div_dialog").dialog("close");
					$("#cover_grid").datagrid("reload"); 
				}
			});		
		});
		$("#butClose").click(function(){
			$("#babyImage").val("");
			$("#userImage").val("");
			$("#photosImage").val("");
			$("#personalImage").val("");
			$("#div_dialog").dialog("close");
		});
	});
</script>
</head>
<body>
	<table id="cover_grid" title="用户图片"></table>
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/cover/save.do" method="post" id="coverForm" enctype="multipart/form-data">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt" align="right">默认宝宝头像：</td>
				<td><input type="file" name="babyImage" id="babyImage" size="25"></td>
			</tr>
			<tr class="ji">
				<td class="rt" align="right">默认用户头像：</td>
				<td><input type="file" name="userImage" id="userImage" size="25"></td>
			</tr>
			<tr class="ji">
				<td class="rt" align="right">默认相册背景：</td>
				<td><input type="file" name="photosImage" id="photosImage" size="25"></td>
			</tr>
			<tr class="ji">
				<td class="rt" align="right">默认个人中心背景：</td>
				<td><input type="file" name="personalImage" id="personalImage" size="25"></td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id" id="id"> 
					<input class="bginput" type="button" id="butSubmit" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="button" id="butClose" name="Input" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>
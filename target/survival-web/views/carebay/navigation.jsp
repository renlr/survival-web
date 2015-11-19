<%@page import="com.baofeng.carebay.util.Constants"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>菜单导航</title>
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
		$("#naviga_grid").datagrid({
			url : "${pageContext.request.contextPath}/naviga/readPages.do?gid=${gid}",
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
				text : "添加菜单",
				iconCls : 'icon-add',
				handler : function() {
					$("#div_dialog").dialog({
						title:"添加菜单",
						show:"slide",
						resizable:false,
						closed: false,
						resizable:false
					});
				}
			},'-', {
				id : 'btnDel',
				text : "修改",
				iconCls : 'icon-edit',
				handler : function() {
					var row = $("#naviga_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/naviga/edit.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#id").val(data.id);
				          	$("#name").val(data.name);
				          	$("#navId").val(data.navId);
				          	$("#url").val(data.url);
				          	$("#indexs").val(data.indexs);
				          	$("#nkey").combobox("setValue",data.nkey);
				          	$("#div_dialog").dialog();
				          },
						  error : function(data){
						  	$("#naviga_grid").datagrid("reload"); 
						  }
					});
				}
			}, '-', {
				id : 'btnDel',
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#naviga_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var ids = row[0].id;
		            var delUrl = "${pageContext.request.contextPath}/naviga/delete.do?t="+new Date().getTime();
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
										$("#naviga_grid").datagrid("reload"); 
								    }
								},
								error : function(data){
									$("#naviga_grid").datagrid("reload"); 
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
				title : "菜单名称",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "navId",
				title : "菜单ID",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "image",
				title : "icon图标",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					if(value != null && value != "")
						return "<img src='"+value+"' width='20' height='20'/>";
					return "";
				}
			},{
				field : "nkey",
				title : "界面KEY",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "indexs",
				title : "显示顺序",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "toMenu",
				title : "下一级菜单",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					if(rec.nkey == "CAREBAY_NONE")		
						return '<a href="${pageContext.request.contextPath}/naviga/show.do?gid='+rec.id+'">下一级菜单</a>';
					return "";
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
			$("#navId").val("");
			$("#url").val("");
			$("#images").val("");
			$("#div_dialog").dialog("close");
		});
		$("#navigaForm").submit(function(){
			var name = $("#name").val(); 
			var navId = $("#navId").val(); 
			var indexs=$("#indexs").numberbox("getValue"); 
			var nkey=$("#nkey").combobox("getValue"); 
			if($.trim(name) == ""){
				alert("菜单名称不能为空！"); 
				$("#name").focus(); 
				return false; 
			}
			if($.trim(navId) == ""){
				alert("菜单ID不能为空！"); 
				$("#navId").focus(); 
				return false;
			}
			if($.trim(nkey) == ""){
				alert("关联界面必须选择！"); 
				$("#nkey").focus(); 
				return false;
			}
			if($.trim(indexs) == ""){
				alert("显示顺序必须填写！"); 
				$("#indexs").focus(); 
				return false;
			}
		});
	});
</script>
</head>
<body>
	<table id="naviga_grid" title="菜单导航"></table>
	<div id="delete_div">确定删除所选内容吗？</div> 
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/naviga/save.do" method="post" id="navigaForm" enctype="multipart/form-data">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt">菜单名称：<label style="color: red;">*</label></td>
				<td class="ji" colspan="3"><input type="text" name="name" id="name" style="width:200px;" maxlength="200" class="input"></td>
			</tr>
			<tr class="ji">
				<td class="rt">菜单ID：<label style="color: red;">*</label></td>
				<td class="ji" colspan="3"><input type="text" name="navId" id="navId" class="input"></td>
			</tr>
			<tr class="tr">
				<td class="rt">关联界面：<label style="color: red;">*</label></td>
				<td>
					<select class="easyui-combobox" id="nkey" name="nkey" style="width:200px;">
						<option value="CAREBAY_NONE">无界面</option>
						<option value="CAREBAY_NEIRONGJINGXUAN">内容精选</option>
						<option value="CAREBAY_SHOPPING">商品分类</option>
						<option value="CAREBAY_GAOJIDINGZHI">高级订制</option>
						<option value="CAREBAY_YUEZITAOCAN">月子套餐</option>
						<option value="CAREBAY_ZUIXINHUODONG">最新活动</option>
						<option value="CAREBAY_FUWUJINGXUAN">服务精选</option>
					</select>
				</td>
			</tr>
			<tr class="tr">
				<td class="rt">菜单图标：</td>
				<td><input type="file" name="images" id="images" size="25">修改时不选择</td>
			</tr>
			<tr class="tr">
				<td class="rt" align="right">显示顺序：<label style="color: red;">*</label></td>
				<td><input type="text" name="indexs" id="indexs" size="4" maxlength="10" class="easyui-numberbox input">降序排列</td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id" id="id"> 
					<input type="hidden" name="gid" id="gid" value="${gid}"> 
					<input class="bginput" type="submit" id="buts" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butrs" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>
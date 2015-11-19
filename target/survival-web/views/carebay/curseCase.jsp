<%@page import="com.baofeng.carebay.util.Constants"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>服务精选</title>
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
		var pageSize = Math.round(($("#right", window.parent.document).height() - 160) / 106);
		$("#curse_grid").datagrid({
			url : "${pageContext.request.contextPath}/curse/readPages.do?gid=${gid}",
			datatype : "json",
			mtype : "POST",
			nowrap : true,
			striped : true,
			fitColumns : true, 
			fit : true,
			pageSize: 30,
			pageList: [30,40,50],
			pagination : true,
			sortname : 'id',
			singleSelect : true,
			rownumbers : true,
			toolbar : [ '-', {
				id : 'btnClearSelections',
				text : "添加服务内容",
				iconCls : 'icon-add',
				handler : function() {
					$("#div_dialog").dialog({
						title:"添加服务内容",
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
					var row = $("#curse_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var online = row[0].online;
		            if(online == 1){
		            	alert("上线中不允许编辑！","提示消息");
		                return ;
		            }
		            var editurl = "${pageContext.request.contextPath}/curse/edit.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#id").val(data.id);
				          	$("#name").val(data.name);
				          	$("#content").val(data.content);
				          	$("#indexs").val(data.indexs);
				          	$("#div_dialog").dialog();
				          },error : function(data){
						  	alert("修改失败!");
						  }
					});
				}
			},'-', {
				id : 'btnDel',
				text : "上  | 下线",
				iconCls : 'icon-redo',
				handler : function() {
					var row = $("#curse_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/curse/online.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
							if("success" == data.resultMessage){
								$("#curse_grid").datagrid("reload"); 
							}			          	
				          },error : function(data){
						  	alert("上下线操作失败!");
						  }
					});
				}
			}, '-', {
				id : 'btnDel',
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#curse_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var ids = row[0].id;
		            var online = row[0].online;
		            if(online == 1){
		            	alert("上线中不允许删除！","提示消息");
		                return ;
		            }
		            var delUrl = "${pageContext.request.contextPath}/curse/delete.do?t="+new Date().getTime();
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
							          		$("#curse_grid").datagrid("reload"); 
							          	}
							          },
									  error : function(data){
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
			},'-', {
				id : 'btnRedo',
				text : "返回上一级",
				iconCls : 'icon-redo',
				handler : function() {
					location.href = "${pageContext.request.contextPath}/cs/show.do?gid=${pid}";
				}
			}],
			columns : [ [{
				field : "image",
				title : "导航图片",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return "<img src='"+value+"' width='200' height='100'/>";
				}
			},{
				field : "name",
				title : "服务名称",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "content",
				title : "服务介绍",
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
				field : "online",
				title : "是否上线",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					if(null != value){
						if(0 == value){
							return "未上线";
						}else if(1 == value){
							return "<font color='green'>上线中</font>";
						}else{
							return "<font color='red'>已下线</font>";
						}
					}
					return "";
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
			} ] ]
		});
		$("#butrs").click(function(){
			$("#id").val("");
			$("#name").val("");
			$("#div_dialog").dialog("close");
		});
		$("#curseForm").submit(function(){
			var id = $("#id").val();
			var name = $("#name").val(); 
			var images = $("#images").val(); 
			var indexs=$("#indexs").numberbox("getValue");  
			if($.trim(name) == ""){ 
				alert("服务名称不能为空！"); 
				$("#name").focus(); 
				return false; 
			}
			if(id == null || id == ""){
				if($.trim(images) == ""){
					alert("必须上传图片！"); 
					$("#images").focus(); 
					return false;
				}
				if(!(images.endWith(".jpg") || images.endWith(".png"))){
					alert("目前只能上传扩展名为：.jpg、.png的图片文件!"); 
					$("#images").focus(); 
					return false;
				}
				if(size > (1608 * 1114)){
					alert("图片不能大于建议尺寸,请重新上传!"); 
					$("#images").focus(); 
					return false; 				
				}
			}
			if($.trim(indexs) == ""){ 
				alert("显示顺序不能为空！"); 
				$("#indexs").focus(); 
				return false; 
			}
			if(isNaN(indexs)){
				alert("显示顺序必须是数字！"); 
				$("#indexs").focus(); 
				return false; 
			}
		});
	});
</script>
</head>
<body>
	<table id="curse_grid" title="服务精选内容"></table>
	<div id="delete_div">确定删除所选内容?</div> 
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/curse/save.do" method="post" id="curseForm" enctype="multipart/form-data">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt" align="right">服务名称：<label style="color: red;">*</label></td>
				<td><input type="text" name="name" id="name" size="25" maxlength="50" class="input"></td>
			</tr>
			<tr class="ji">
				<td class="rt">上传图片：</td>
				<td><input type="file" name="images" id="images" size="25">修改时不选择,<span class="p-size">&nbsp;图片尺寸：1608*1114</span></td>
			</tr>
			<tr class="tr">
				<td class="rt" align="right">显示顺序：<label style="color: red;">*</label></td>
				<td><input type="text" name="indexs" id="indexs" size="4" maxlength="10" class="easyui-numberbox input">降序排列</td>
			</tr>
			<tr class="ji">
				<td class="rt" align="top">服务介绍：</td>
				<td class="ji" colspan="3"><textarea id="content" name="content" style="height:60px;width:450px;"></textarea></td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id" id="id"> 
					<input type="hidden" name="gid" id="gid" value="${gid}"> 
					<input type="hidden" name="pid" id="pid" value="${pid}"> 
					<input class="bginput" type="submit" id="buts" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butrs" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>
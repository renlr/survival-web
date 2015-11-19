<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>定制内容</title>
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
		var pageSize = Math.round(($("#right", window.parent.document).height() - 160) / 105);
		$("#custom_grid").datagrid({
			url : "${pageContext.request.contextPath}/custom/readPagesDetails.do?gid=${gid}",
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
				text : "添加定制内容",
				iconCls : 'icon-add',
				handler : function() {
					$("#div_dialog").dialog({
						title:"添加定制内容",
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
					var row = $("#custom_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var online = row[0].online;
		            if(online == 1){
		            	alert("上线中不允许编辑!","提示消息");
		                return ;
		            }
		            var editurl = "${pageContext.request.contextPath}/custom/editDetails.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#id").val(data.id);
				          	$("#name").val(data.name);
				          	$("#content").val(data.content);
							$("#indexs").numberbox("setValue",data.indexs);								          
				          	$("#div_dialog").dialog();
				          },
						  error : function(data){
						  	$("#custom_grid").datagrid("reload"); 
						  }
					});
				}
			}, '-', {
				id : 'btnRedo',
				text : "上  | 下线",
				iconCls : 'icon-redo',
				handler : function() {
					var row = $("#custom_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/custom/onlineDetails.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	if("success" == data.resultMessage){
					          	$("#custom_grid").datagrid("reload"); 
				          	}
				          },
						  error : function(data){
						  	$("#custom_grid").datagrid("reload"); 
						  }
					});
				}
			}, '-', {
				id : 'btnDel',
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#custom_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var ids = row[0].id;
		            var online = row[0].online;
		            if(online == 1){
		            	alert("上线中不允许删除!","提示消息");
		                return ;
		            }
		            var delUrl = "${pageContext.request.contextPath}/custom/deleteDetails.do?t="+new Date().getTime();
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
							          		$("#custom_grid").datagrid("reload"); 
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
					location.href = "${pageContext.request.contextPath}/custom/show.do";
				}
			},'-'],
			columns : [[{
				field : "image",
				title : "内容图片",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return "<img src='"+value+"' width='200' height='100'/>";
				}
			},{
				field : "name",
				title : "定制名称",
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
				field : "content",
				title : "介绍内容",
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
				formatter : function(dateValue,rec){
					return dateValue.toString().toDate().format("yyyy-MM-dd hh:mm:ss");
				}
			}]]
		});
		$("#customForm").submit(function(){
			var id = $("#id").val();
			var images=$("#images").val();
			var indexs=$("#indexs").numberbox("getValue"); 
			if(id == null || id == ""){
				if($.trim(images) == ""){
					alert("请选择上传图片文件！"); 
					$("#images").focus(); 
					return false;
				}
				if(!(images.endWith(".jpg") || images.endWith(".png"))){
					alert("目前只能上传扩展名为：.jpg、.png的图片文件!"); 
					$("#images").focus(); 
					return false;
				}
				if(size > (1608 * 1114)){
					alert("图片不能大于建议尺寸,请重新选择!"); 
					$("#images").focus(); 
					return false; 				
				}
			}
			if($.trim(indexs) == ""){ 
				alert("显示顺序不能为空！"); 
				$("#indexs").focus(); 
				return false; 
			}
		});
		$("#butClose").click(function(){
			$("#id").val("");
			$("#images").val("");
			$("#indexs").numberbox("setValue","");
			$("#div_dialog").dialog("close");
		});
	});
</script>
</head>
<body>
	<table id="custom_grid" title="定制内容"></table>
	<div id="delete_div">确定删除所选内容?</div> 
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/custom/saveDetails.do" method="post" id="customForm" enctype="multipart/form-data">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt">定制名称：<label style="color: red;">*</label></td>
				<td class="ji"><input type="text" name="name" id="name" style="width:200px;" maxlength="200" class="input"></td>
			</tr>
			<tr class="ji">
				<td class="rt">上传图片：</td>
				<td class="ji"><input type="file" name="images" id="images" size="25">修改不选择,<span class="p-size">&nbsp;图片尺寸：1608*1114</span></td>
			</tr>
			<tr class="ji">
				<td class="rt">介绍内容：<label style="color: red;">*</label></td>
				<td class="ji"><textarea id="content" name="content" style="height:100px;width:450px;"></textarea></td>
			</tr>
			<tr class="tr">
				<td class="rt">显示顺序：<label style="color: red;">*</label></td>
				<td class="ji"><input type="text" name="indexs" id="indexs" size="5" class="easyui-numberbox" data-options="min:0,precision:0">降序排列</td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id" id="id"> 
					<input type="hidden" name="gid" id="gid" value="${gid}"> 
					<input class="bginput" type="submit" id="butSubmit" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butClose" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>
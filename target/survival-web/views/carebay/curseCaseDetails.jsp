<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>护理套餐</title>
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
		$("#curseDetails_grid").datagrid({
			url : "${pageContext.request.contextPath}/curseDetails/readPages.do?groupId=${curse.id}",
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
			},'-', {
				id : 'btnEdit',
				text : "显示顺序",
				iconCls : 'icon-edit',
				handler : function() {
					var row = $("#curseDetails_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/curseDetails/edit.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#id1").val(data.id);
				          	$("#gid1").val(data.curse.id);
				          	$("#indexs1").val(data.indexs);
				          	$("#index_dialog").dialog();
				          },
						  error : function(data){
						  	alert("修改显示顺序失败!");
						  }
					});
				}
			},'-', {
				id : 'btnDel',
				text : "上  | 下线",
				iconCls : 'icon-redo',
				handler : function() {
					var row = $("#curseDetails_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/curseDetails/online.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
							if("success" == data.resultMessage){
								$("#curseDetails_grid").datagrid("reload"); 
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
					var row = $("#curseDetails_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var ids = row[0].id;
		            var groupId = $("#groupId").val();
		            var delUrl = "${pageContext.request.contextPath}/curseDetails/delete.do?t="+new Date().getTime();
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
							          data: "id="+ids+"&groupId="+groupId,//要发送的数据
							          success: function(data){
							          	$("#curseDetails_grid").datagrid('reload'); 
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
			}],
			columns : [[{
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
				title : "套餐内容",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "curse",
				title : "护理套餐",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					if(null != value){
						return rec.curse.name;
					}
					return "";
				}
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
					return "	";
				}
			},{
				field : "content",
				title : "内容编辑",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return '<a href="${pageContext.request.contextPath}/curseDetails/editContent.do?id='+rec.id+'&gid=${curse.id}">内容编辑</a>';
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
		$($("#curseDetails_grid").datagrid("getPager")).pagination({
			beforePageText : "第",
			afterPageText : "页    共 {pages} 页",
			displayMsg : "当前显示 {from} - {to} 条记录   共 {total} 条记录"
		});
		
		$("#curseDetailsForm").submit(function(){
			var name=$("#name").val(); 
			var images=$("#images").val(); 
			var indexs=$("#indexs").val(); 
			if($.trim(name) == ""){ 
				alert("服务内容标题不能为空！"); 
				$("#name").focus(); 
				return false; 
			}
			if(id == null && $.trim(images) == ""){ 
				alert("必须上传图片！"); 
				$("#images").focus(); 
				return false; 
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
		$("#groupId").val("${curse.id}");
		$("#butrs").click(function(){
			$("#id").val("");
			$("#name").val("");
			$("#images").val("");
			$("#indexs").val(""); 
			$("#div_dialog").dialog("close");
		});
		//-----------------------------------------------------------
		$("#butClose").click(function(){
			$("#id1").val("");
			$("#gid1").val("");
			$("#indexs1").val("");
			$("#index_dialog").dialog("close");
		});
		$("#butIndexs").click(function(){
			$("#indexsForm").form("submit",{
				onSubmit:function(){
					var indexs = $("#indexs1").val();
					if($.trim(indexs) == ""){ 
						alert("显示顺序必须填写！"); 
						$("#indexs1").focus(); 
						return false; 
					}
				},
				success:function(data){
					$("#id1").val("");
					$("#gid1").val("");
					$("#indexs1").val("");
					$("#index_dialog").dialog("close");
					$("#curseDetails_grid").datagrid("reload"); 
				}
			});
		});
	});
</script>
</head>
<body>
	<table id="curseDetails_grid" title="${curse.name}内容管理"></table>
	<div id="delete_div">确定删除所选内容?</div>
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/curseDetails/save.do" method="post" id="curseDetailsForm" enctype="multipart/form-data">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt">服务内容：<label style="color: red;">*</label></td>
				<td><input type="text" name="name" id="name" size="25" maxlength="50" class="input"></td>
			</tr>
			<tr class="ji">
				<td class="rt">上传图片：<label style="color: red;">*</label></td>
				<td><input type="file" name="images" id="images" size="25">修改时不选择</td>
			</tr>
			<tr class="tr">
				<td class="rt">显示顺序：<label style="color: red;">*</label></td>
				<td><input type="text" name="indexs" id="indexs" size="5" class="easyui-numberbox input" data-options="min:0,precision:0">降序排列</td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id" id="id">
					<input type="hidden" name="groupId" id="groupId"> 
					<input class="bginput" type="submit" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butrs" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
	<div id="index_dialog" title="修改显示顺序">
		<form action="${pageContext.request.contextPath}/curseDetails/saveIndexs.do" method="post" id="indexsForm">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt" align="right">显示顺序：<label style="color: red;">*</label></td>
				<td><input type="text" name="indexs1" id="indexs1" size="5" class="easyui-numberbox input" data-options="min:0,precision:0">降序排列</td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id1" id="id1">
					<input type="hidden" name="gid1" id="gid1"> 
					<input class="bginput" type="button" id="butIndexs" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butClose" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>
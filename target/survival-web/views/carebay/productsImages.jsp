<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>展示图片</title>
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
		$("#products_grid").datagrid({
			url : "${pageContext.request.contextPath}/products/readPagesDetails.do?groupId=${duct.id}",
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
						title:"添加展示图片",
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
					var row = $("#products_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/products/editIndexs.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#id1").val(data.id);
				          	$("#groupId1").val(data.products.id);
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
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#products_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var ids = row[0].id;
		            var delUrl = "${pageContext.request.contextPath}/products/deleteImage.do?t="+new Date().getTime();
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
							          		$("#products_grid").datagrid("reload"); 
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
					location.href = "${pageContext.request.contextPath}/products/news.do?id=${pid}";
				}
			}],
			columns : [[{
				field : "image",
				title : "展示图片",
				width : $(this).width() * 0.2,
				align : "left",
				sortable : true,
				formatter : function(value,rec){
					return "<img src='"+value+"' width='100' height='100'/>";
				}
			},{
				field : "indexs",
				title : "显示顺序",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
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
		$("#groupId").val("${duct.id}");
		$("#butrs").click(function(){
			$("#images").val("");
			$("#indexs").val("");
			$("#div_dialog").dialog("close");
		});
		$("#productsForm").submit(function(){
			var images=$("#images").val(); 
			var indexs=$("#indexs").val(); 
			if($.trim(images) == ""){ 
				alert("请选择上传图片文件！"); 
				$("#images").focus(); 
				return false; 
			}
			var size = $("#images").width() * $("#images").height();
			if(!(images.endWith(".jpg") || images.endWith(".png"))){
				alert("目前只能上传扩展名为：.jpg、.png的图片文件!"); 
				$("#images").focus(); 
				return false;
			}
			if(size > (980 * 1536)){
				alert("图片不能大于建议尺寸,请重新上传!"); 
				$("#images").focus(); 
				return false; 				
			}
			if($.trim(indexs) == ""){ 
				alert("显示顺序必须填写！"); 
				$("#indexs").focus(); 
				return false; 
			}
		});
		$("#butClose").click(function(){
			$("#id1").val("");
			$("#groupId1").val("");
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
					$("#groupId1").val("");
					$("#indexs1").val("");
					$("#index_dialog").dialog("close");
					$("#products_grid").datagrid("reload"); 
				}
			});
		});
	});
</script>
</head>
<body>
	<table id="products_grid" title="展示图片"></table>
	<div id="delete_div">确定删除所选内容?</div> 
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/products/saveDetails.do" method="post" id="productsForm" enctype="multipart/form-data">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="tr">
				<td class="rt" align="right">上传图片：<label style="color: red;">*</label></td>
				<td><input type="file" name="images" id="images" size="25"><span class="p-size">&nbsp;图片尺寸：980*1536</span></td>
			</tr>
			<tr class="ji">
				<td class="rt" align="right">显示顺序：<label style="color: red;">*</label></td>
				<td><input type="text" name="indexs" id="indexs" size="4" maxlength="10" class="input">降序排列</td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id" id="id">
					<input type="hidden" name="groupId" id="groupId"> 
					<input type="hidden" name="pid" id="pid" value="${pid}"> 
					<input class="bginput" type="submit" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butrs" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
	<div id="index_dialog" title="修改显示顺序">
		<form action="${pageContext.request.contextPath}/products/saveIndexs.do" method="post" id="indexsForm">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt" align="right">显示顺序：<label style="color: red;">*</label></td>
				<td><input type="text" name="indexs1" id="indexs1" size="5" class="easyui-numberbox input" data-options="min:0,precision:0">降序排列</td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id1" id="id1">
					<input type="hidden" name="groupId1" id="groupId1"> 
					<input class="bginput" type="button" id="butIndexs" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butClose" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>
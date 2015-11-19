<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>首页轮播</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"  />
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
		$("#viwepager_grid").datagrid({
			url : "${pageContext.request.contextPath}/viwepager/readPages.do",
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
			toolbar : [ '-', {
				id : 'btnClearSelections',
				text : "添加图片",
				iconCls : 'icon-add',
				handler : function() {
				  $("#viweForm")[0].reset();
					$("#div_dialog").dialog({
						title:"添加图片",
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
					var row = $("#viwepager_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/viwepager/edit.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#id").val(data.id);
				          	$("#description").val(data.description);
				          	$("#modelParams").val(data.modelParams);
				          	$("#type").val(data.type);
				          	$("#orders").numberbox("setValue",data.orders);
				          	$("#div_dialog").dialog();
				          },
						  error : function(data){
						  	$("#viwepager_grid").datagrid("reload"); 
						  }
					});
				}
			}, '-', {
				id : 'btnDel',
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#viwepager_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var ids = row[0].id;
		            var delUrl = "${pageContext.request.contextPath}/viwepager/delete.do?t="+new Date().getTime();
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
							          		$("#viwepager_grid").datagrid("reload"); 
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
			}],
			columns : [ [ {
				field : "image",
				title : "图片",
				width : $(this).width() * 0.4,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return "<img src='"+value+"' width='300' height='150'/>";
				}
			},{
				field : "type",
				title : "图片类型",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter:function(value,rec){
				var htmls = "";
						if(0 == value){
							htmls = "首页轮播";
						}else if(1 == value){
							htmls = "产品推荐";
						}
				return htmls;
				}
			},{
				field : "modelParams",
				title : "跳转参数",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "description",
				title : "图片描述",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
			},{
				field : "orders",
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
			}]]
		});
		$("#butrs").click(function(){
			$("#id").val("");
			$("#images").val("");
			$("#indexs").val("");
			$("#div_dialog").dialog("close");
		});
		$("#viweForm").submit(function(){
			var id = $("#id").val();
			var images=$("#images").val(); 
			var orders=$("#orders").val(); 
			if(id == "" && $.trim(images) == ""){ 
				alert("请选择上传图片文件！"); 
				$("#images").focus(); 
				return false; 
			}
			if($.trim(orders) == ""){ 
				alert("显示顺序必须填写！"); 
				$("#orders").focus(); 
				return false; 
			}
			
		});
	});
</script>
</head>
<body>
	<table id="viwepager_grid" title="首页轮播"></table>
	<div id="delete_div">确定删除所选内容?</div> 
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/viwepager/save.do" method="post" id="viweForm" enctype="multipart/form-data">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="tr">
				<td class="rt">轮播图片：<label style="color: red;">*</label></td>
				<td><input type="file" name="images" id="images" size="25"></td>
			</tr>
		 
			<tr class="ji">
				<td class="rt">图片描述：</td>
				<td class="ji"><input type="text" name="description" id="description" style="width:200px;" maxlength="400" class="input"></td>
			</tr>
			
			<tr class="ji">
				<td class="rt">跳转参数：</td>
				<td class="ji"><input type="text" name="modelParams" id="modelParams" style="width:200px;" maxlength="400" class="input" value="http://"></td>
			</tr>
			<tr class="ji">
				<td class="rt">显示顺序：<label style="color: red;">*</label></td>
				<td><input type="text" name="orders" id="orders" size="4" maxlength="10" class="easyui-numberbox input">降序排列</td>
			</tr>
			<td class="rt">所属类别：<label style="color: red;">*</label></td>
				<td class="ji">
					<select id="type" name="type"  style="width:200px;">
					    <option value="0">首页轮播</option>
					    <option value="1">产品推荐</option>
					</select>
				</td>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id" id="id">
					<input class="bginput" type="submit" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butrs" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>
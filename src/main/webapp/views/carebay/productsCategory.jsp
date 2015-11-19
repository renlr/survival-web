<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<title>母婴乐购</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/views/css/admin_style.css'>
<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/views/css/dialog.css'>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/views/js/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/views/js/themes/default/easyui.css">
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/jquery-1.10.1.min.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/common.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/jquery.easyui.min.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#productsCategory_grid").datagrid({
			url : "${pageContext.request.contextPath}/productsCategory/readPages.do",
			datatype : "json",
			mtype : "POST",
			nowrap : false,
			striped : true,
			fitColumns : true, 
			fit : true,
			pageSize: 30,
			pageList: [30,40,50],
			pagination : true,
			sortname : 'id',
			singleSelect : true,
			rownumbers : true,
			toolbar : [{
				id : 'btn-add',
				text : "添加商品分类",
				iconCls : 'icon-add',
				handler : function() {
					$("#div_dialog").dialog({
						title:"添加商品分类",
						show:"slide",
						resizable:false,
						closed: false,
						resizable:false
					});
				}
			},'-', {
				id : 'btn-mid',
				text : "修改",
				iconCls : 'icon-edit',
				handler : function() {
					var row = $("#productsCategory_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/productsCategory/edit.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#id").val(data.id);
				          	$("#name").val(data.name);
				          	$("#navigtor").val(data.navigtor);
				          	$("#div_dialog").dialog();
				          },
						  error : function(data){
						  	alert("修改商品分类失败!");
						  }
					});
				}
			}, '-', {
				id : 'btn-Del',
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#productsCategory_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var ids = row[0].id;
		            var delUrl = "${pageContext.request.contextPath}/productsCategory/delete.do?t="+new Date().getTime();
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
							          		$("#productsCategory_grid").datagrid("reload"); 
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
				id : 'btnProd',
				text : "导入商品",
				iconCls : 'icon-edit',
				handler : function() {
					$("#imp_dialog").dialog({
						title:"导入商品",
						show:"slide",
						resizable:true,
						closed: false
					});					
				}
			}],
			columns : [[{
				field : "name",
				title : "分类名称",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "navigtor",
				title : "菜单ID",
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
				field : "products",
				title : "商品列表",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return '<a href="${pageContext.request.contextPath}/products/news.do?id='+rec.id+'">商品列表</a>';
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
			$("#navigtor").val("");
			$("#div_dialog").dialog("close");
		});
		$("#productsCategoryForm").submit(function(){
			var name = $("#name").val(); 
			if($.trim(name) == ""){ 
				alert("商品分类名称不能为空！"); 
				$("#name").focus(); 
				return false; 
			}
		});
		$("#importForm").submit(function(){
			var exFiles = $("#exFiles").val();
			var picFiles = $("#picFiles").val();
			if($.trim(exFiles) == "" || !exFiles.endWith(".xlsx")){ 
				alert("请上传商品数据文件,必须2007版以上的Execl文件!"); 
				$("#exFiles").focus(); 
				return false; 
			}
			if($.trim(picFiles) == "" || !picFiles.endWith(".zip")){ 
				alert("请上传商品图片文件,必须压缩成zip文件包!"); 
				$("#picFiles").focus(); 
				return false; 
			}
		});
		$("#butrs2").click(function(){
			$("#exFiles").val("");
			$("#picFiles").val("");
			$("#imp_dialog").dialog("close");
		});
		$("#navigtor").combobox({
		    url:"${pageContext.request.contextPath}/naviga/readLabelList.do",
		    valueField:"navId",
		    textField:"name"
		}); 
	});
</script>
</head>
<body>
	<table id="productsCategory_grid" title="母婴乐购"></table>
	<div id="delete_div">确定删除所选内容，请先删除下面所有商品!</div> 
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/productsCategory/save.do" method="post" id="productsCategoryForm">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt" align="right">商品分类名称：<label style="color: red;">*</label></td>
				<td><input type="text" name="name" id="name" size="25" maxlength="50" class="input"></td>
			</tr>
			<tr class="ji">
				<td class="rt" align="right">菜单导航分类：<label style="color: red;">*</label></td>
				<td><input type="text" id="navigtor" name="navigtor" size="25" maxlength="50" class="input"></td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id" id="id">
					<input class="bginput" id="buts" type="submit" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butrs" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
	<div id="imp_dialog" title="导入商品">
		<form action="${pageContext.request.contextPath}/products/imports.do" method="post" id="importForm" enctype="multipart/form-data">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt">数据文件：<label style="color: red;">*</label></td>
				<td><input type="file" name="exFiles" id="exFiles"/>只能导入execl文件(*.xlsx)</td>
			</tr>
			<tr class="ji">
				<td class="rt">图片文件：<label style="color: red;">*</label></td>
				<td><input type="file" name="picFiles" id="picFiles"/>只能导入zip文件(*.zip)</td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input class="bginput" type="submit" value="导入">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butrs2" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>
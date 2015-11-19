<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>图片管理</title>
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
		var pageSize = Math.round(($("#right", window.parent.document).height() - 160) / 126);
		$("#recduct_grid").datagrid({
			url : "${pageContext.request.contextPath}/recduct/readPagesDetails.do?groupId=${duct.id}",
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
				id : 'btnDel',
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#recduct_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            if("${duct.online}" == 0){
		            	var ids = row[0].id;
			            var delUrl = "${pageContext.request.contextPath}/recduct/deleteDetails.do?t="+new Date().getTime();
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
								          		$("#recduct_grid").datagrid("reload"); 
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
		            }else{
		            	alert("上线后不能删除!","提示消息");
		            }
				}
			}],
			columns : [ [ {
				field : "image",
				title : "图片文件",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return "<img src='"+value+"' width='200' height='100'/>";
				}
			},{
				field : "type",
				title : "跳转类型",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					if(value != null){
						if(value == 1){
							return "推荐列表";
						}else if(value == 2){
							return "单个商品";
						}
					}
					return "";
				}
			},{
				field : "paramsName",
				title : "跳转名称",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "indexs",
				title : "显示顺序",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			}, {
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
		$($("#recduct_grid").datagrid("getPager")).pagination({
			beforePageText : '第',
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		});
		$("#ductCat").combogrid({
			fit : true,
			delay:500,
			mode:"remote",
			datatype : "json",
			fitColumns: true,
			rownumbers : true,
			panelWidth: 500,
			mtype : "POST",
			url:"${pageContext.request.contextPath}/ductcy/readPages.do",
			idField:"id",
			textField:"name",
			columns:[[
				{field:"name",title:"列表名称",width:120,sortable:true}
			]],
			pagination : true,
			pageSize: 5,
			pageList: [5,10,20,30,40,50]
		});
		$("#products").combogrid({
			fit : true,
			delay:500,
			mode:"remote",
			datatype : "json",
			fitColumns: true,
			rownumbers : true,
			panelWidth: 500,
			mtype : "POST",
			url:"${pageContext.request.contextPath}/products/readPages.do",
			idField:"id",
			textField:"name",
			columns:[[
				{field:"name",title:"产品名称",width:120,sortable:true}
			]],
			pagination : true,
			pageSize: 5,
			pageList: [5,10,20,30,40,50]
		});
		$("#type").combobox({
			onSelect:function(data){
				if(data.value == 1){
					$("#duct").show();
					$("#prods").hide();
				}else if(data.value == 2){
					$("#duct").hide();
					$("#prods").show();
				}
			}
		});
		$("#groupId").val("${duct.id}");
		$("#groupId1").val("${duct.id}");
		$("#butrs").click(function(){
			$("#images").val("");
			$("#indexs").val("");
			$("#div_dialog").dialog("close");
		});
		$("#recductForm").submit(function(){
			var images=$("#images").val(); 
			var indexs=$("#indexs").val(); 
			var type = $("#type").combobox("getValue");
			var ductCat = $("#ductCat").combobox("getValue");
			var products = $("#products").combobox("getValue");
			if($.trim(images) == ""){ 
				alert("请选择上传图片文件！"); 
				$("#images").focus(); 
				return false; 
			}
			if(type == 1){
				if($.trim(ductCat) == ""){ 
					alert("请选择推荐列表！"); 
					$("#duct").show();
					$("#prods").hide();
					$("#ductCat").focus(); 
					return false; 
				}
			}else{
				if(products == ""){
					alert("请选择跳转的单个商品!"); 
					$("#duct").hide();
					$("#prods").show();
					$("#products").focus(); 
					return false;
				}
			}
			if($.trim(indexs) == ""){ 
				alert("显示顺序必须填写！"); 
				$("#indexs").focus(); 
				return false; 
			}
		});
		$("#butrs1").click(function(){
			$("#id1").val("");
			$("#indexs1").val("");
			$("#index_dialog").dialog("close");
		});
		$("#indexsForm").submit(function(){
			var indexs=$("#indexs1").val();
			if($.trim(indexs) == ""){ 
				alert("显示顺序必须填写！"); 
				$("#indexs1").focus(); 
				return false; 
			}		
		});
	});
</script>
</head>
<body>
	<table id="recduct_grid" title="更多图片"></table>
	<div id="delete_div">确定删除所选内容?</div> 
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/recduct/saveDetails.do" method="post" id="recductForm" enctype="multipart/form-data">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="tr">
				<td class="rt" align="right">上传图片：<label style="color: red;">*</label></td>
				<td><input type="file" name="images" id="images" size="25"></td>
			</tr>
			<tr class="ji">
				<td class="rt" align="right">跳转类型：<label style="color: red;">*</label></td>
				<td>
					<select id="type" class="easyui-combobox" name="type" style="width:140px;">
						<option value="1">推荐列表</option>
						<option value="2">单个商品</option>
					</select>
			    </td>
			</tr>
			<tr class="tr" id="duct" style="display:black;">
				<td class="rt" align="right">推荐列表：<label style="color: red;">*</label></td>
				<td><input id="ductCat" name="ductCat"></td>
			</tr>
			<tr class="tr" id="prods" style="display:none;">
				<td class="rt" align="right">单个商品：<label style="color: red;">*</label></td>
				<td><input id="products" name="products"></td>
			</tr>
			<tr class="ji">
				<td class="rt" align="right">显示顺序：<label style="color: red;">*</label></td>
				<td><input type="text" name="indexs" id="indexs" size="4" maxlength="10" class="easyui-numberbox input">降序排列</td>
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
		<form action="${pageContext.request.contextPath}/recduct/saveDetailsIndexs.do" method="post" id="indexsForm">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt" align="right">显示顺序：<label style="color: red;">*</label></td>
				<td><input type="text" name="indexs1" id="indexs1" size="4" maxlength="10" class="easyui-numberbox input">降序排列</td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id1" id="id1">
					<input type="hidden" name="groupId1" id="groupId1">
					<input class="bginput" type="submit" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butrs1" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>母婴商品</title>
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
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/kidediter/kindeditor-min.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/kidediter/lang/zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/views/js/kidediter/themes/default/default.css">
<script type="text/javascript">
	$.extend($.fn.datagrid.methods, {
	    editCell: function(jq,param){
	        return jq.each(function(){
	            var opts = $(this).datagrid("options");
	            var fields = $(this).datagrid("getColumnFields",true).concat($(this).datagrid("getColumnFields"));
	            for(var i=0; i<fields.length; i++){
	                var col = $(this).datagrid("getColumnOption", fields[i]);
	                col.editor1 = col.editor;
	                if (fields[i] != param.field){
	                    col.editor = null;
	                }
	            }
	            $(this).datagrid("beginEdit", param.index);
	            for(var i=0; i<fields.length; i++){
	                var col = $(this).datagrid("getColumnOption", fields[i]);
	                col.editor = col.editor1;
	            }
	        });
	    }
	});
	
	var values = undefined;
	var fields = undefined;
	var editIndex = undefined;
	function endEditing(){
	    if (editIndex == undefined){return true}
	    if ($("#products_grid").datagrid("validateRow", editIndex)){
			$("#products_grid").datagrid("endEdit", editIndex);
			var row = $("#products_grid").datagrid("getRows");
			var id = row[editIndex].id;
			var $value = row[editIndex][fields];
			if($value != null && values != $value && $value != ""){
				$.ajax({
					type: "post",
					dataType: "json",
					url: "${pageContext.request.contextPath}/products/saveEditFields.do?t="+new Date().getTime(),
					data: "id="+id+"&field="+fields+"&value="+$value,
					success: function(data){
				    	if("success" == data.resultMessage){}
				    },
					error : function(data){}
				});				
			}
			values = undefined;
			fields = undefined;
	        editIndex = undefined;
	        return true;
	    } else {
	        return false;
	    }
	}
	function clickCell(index, field,value){
	    if (endEditing()){
	        $("#products_grid").datagrid("selectRow", index).datagrid("editCell", {index:index,field:field});
	        values = value;
	        fields = field;
	        editIndex = index;
	    }
	}
	$(document).ready(function() {
		var pageSize = Math.round(($("#right", window.parent.document).height() - 160) / 50);
		$("#products_grid").datagrid({
			url : "${pageContext.request.contextPath}/products/readPages.do",
			datatype : "json",
			mtype : "POST",
			nowrap : false,
			striped : true,
			fit : true,
			fitColumns : true, 
			pageSize: pageSize,
			pageList: [pageSize,30,40,50],
			pagination : true,
			sortname : 'id',
			singleSelect : true,
			rownumbers : true,
			onClickCell : function(index,field,value){
				clickCell(index,field,value);
			},
			toolbar : [ '-', {
				id : 'btnClearSelections',
				text : "添加商品",
				iconCls : 'icon-add',
				handler : function() {
					$("#div_dialog").dialog({
						title:"添加商品",
						show:"slide",
						resizable:true,
						closed: false
					});
				}
			},'-', {
				id : 'btnRedo',
				text : "上 线",
				iconCls : 'icon-redo',
				handler : function() {
					var row = $("#products_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/products/online1.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#products_grid").datagrid('reload'); 
				          },
						  error : function(data){
						  	$("#products_grid").datagrid('reload'); 
						  }
					});
				}
			}, '-', {
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
		            var delUrl = "${pageContext.request.contextPath}/products/delete.do?t="+new Date().getTime();
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
			}],
			columns : [[{
				field : "image",
				title : "商品图标",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return "<img src='"+value+"' width='50' height='50'/>";
				}
			},{
				field : "name",
				title : "商品名称",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				editor : "textarea"
			},{
				field : "category",
				title : "分类名称",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return rec.category.name;
				}
			},{
				field : "articleNumber",
				title : "商品货号",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				editor : "text"
			},{
				field : "inventory",
				title : "库存数量",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				editor : {
					type:"numberbox",
					options:{
						precision:0
					}
				}
			},{
				field : "quotaNumber",
				title : "限购数量",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				editor : {
					type:"numberbox",
					options:{
						precision:0
					}
				}
			},{
				field : "costPrice",
				title : "成本价格",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				editor : {
					type:"numberbox",
					options:{
						precision:2
					}
				}
			},{
				field : "bazaarPrice",
				title : "市场价格",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				editor : {
					type:"numberbox",
					options:{
						precision:2
					}
				}
			},{
				field : "sellPrice",
				title : "销售价格",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				editor : {
					type:"numberbox",
					options:{
						precision:2
					}
				}
			},{
				field : "images",
				title : "展示图片",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return '<a href="${pageContext.request.contextPath}/products/addImages.do?id='+rec.id+'">展示图片</a>';
				}
			},{
				field : "params",
				title : "销售属性",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return '<a href="${pageContext.request.contextPath}/products/addParams.do?id='+rec.id+'">销售属性</a>';
				}
			},{
				field : "imageDetails",
				title : "商品描述",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return '<a href="${pageContext.request.contextPath}/products/editContent.do?id='+rec.id+'">商品描述</a>';
				}
			},{
				field : "pinglun",
				title : "评论管理",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return '<a href="${pageContext.request.contextPath}/products/addComment.do?id='+rec.id+'">评论管理</a>';
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
		$($("#products_grid").datagrid("getPager")).pagination({
			beforePageText : "第",
			afterPageText : "页    共 {pages} 页",
			displayMsg : "当前显示 {from} - {to} 条记录   共 {total} 条记录"
		});
 		$("#category").combogrid({
 			fit : true,
 			delay:500,
 			mode:"remote",
 			datatype : "json",
 			fitColumns: true,
 			rownumbers : true,
 			panelWidth: 500,
 			mtype : "POST",
 			url:"${pageContext.request.contextPath}/productsCategory/readPages.do",
 			idField:"id",
 			textField:"name",
 			columns:[[
 				{field:"name",title:"分类名称",width:120,sortable:true}
 			]],
 			pagination : true,
 			pageSize: 5,
 			pageList: [5,10,20,30,40,50]
 		});
		$("#butClose").click(function(){
			$("#id").val("");
			$("#name").val("");
			$("#price").val("");
			$("#inventory").val("");
			$("#evaluate").val("");
			$("#integral").val("");
			$("#div_dialog").dialog("close");
		});
		$("#butSubmit").click(function(){
			$("#productsForm").form("submit",{
				onSubmit:function(){
					var name=$("#name").val(); 
					var subheading=$("#subheading").val(); 
					var sellPrice=$("#sellPrice").numberbox("getValue"); 
					var inventory=$("#inventory").numberbox("getValue"); 
					var category=$("#category").combobox("getValue"); 
					if($.trim(name) == ""){ 
						alert("商品名称不能为空！"); 
						$("#name").focus(); 
						return false; 
					}
					if($.trim(subheading) == ""){ 
						alert("商品SEO名称不能为空！"); 
						$("#subheading").focus(); 
						return false; 
					}
					if($.trim(sellPrice) == ""){ 
						alert("销售价格不能为空！"); 
						$("#sellPrice").focus(); 
						return false; 
					}
					if($.trim(inventory) == ""){ 
						alert("商品数量不能为空！"); 
						$("#inventory").focus(); 
						return false; 
					}
 					if($.trim(category) == ""){ 
 						alert("所属分类必须选择！"); 
 						$("#category").focus(); 
 						return false; 
 					}
				},
				success:function(data){
					$("#div_dialog").dialog("close");
					$("#products_grid").datagrid("reload"); 
				}
			});
		});
	});
</script>
</head>
<body>
	<table id="products_grid" title="母婴商品"></table>
	<div id="delete_div">确定删除所选内容?</div> 
	<div id="div_dialog" style="height:360px;width:580px;">
		<form action="${pageContext.request.contextPath}/products/save.do" method="post" id="productsForm">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt">商品名称：<label style="color: red;">*</label></td>
				<td class="ji" colspan="3"><input type="text" name="name" id="name" style="width:450px;" maxlength="200" class="input"></td>
			</tr>
			<tr class="ji">
				<td class="rt" align="top">SEO名称：<label style="color: red;">*</label></td>
				<td class="ji" colspan="3"><textarea id="subheading" name="subheading" style="height:40px;width:450px;"></textarea></td>
			</tr>
			<tr class="tr">
				<td class="rt" align="right">商品货号：</td>
				<td class="ji" colspan="3"><input type="text" name="articleNumber" id="articleNumber" size="20" maxlength="50" class="input"></td>
			</tr>
			<tr class="tr">
				<td class="rt" align="right">成本价格：</td>
				<td class="ji"><input type="text" name="costPrice" id="costPrice" size="20" maxlength="50" class="easyui-numberbox input" data-options="min:0,precision:2"></td>
				<td class="rt" align="right">市场价格：</td>
				<td class="ji"><input type="text" name="bazaarPrice" id="bazaarPrice" size="20" maxlength="50" class="easyui-numberbox input" data-options="min:0,precision:2"></td>
			</tr>
			<tr class="tr">
				<td class="rt" align="right">销售价格：<label style="color: red;">*</label></td>
				<td class="ji" colspan="3"><input type="text" name="sellPrice" id="sellPrice" size="20" maxlength="50" class="easyui-numberbox input" data-options="min:0,precision:2"></td>
			</tr>
			<tr class="ji">
				<td class="rt" align="right">商品数量：<label style="color: red;">*</label></td>
				<td class="ji"><input type="text" class="easyui-numberbox input" name="inventory" id="inventory" size="20" maxlength="50" value="0" data-options="min:0,precision:0"></td>
				<td class="rt" align="right">限购数量：</td>
				<td class="ji"><input type="text" class="easyui-numberbox input" name="quotaNumber" id="quotaNumber" size="20" maxlength="50" value="0" data-options="min:0,precision:0"></td>
			</tr>
			<tr class="ji">
				<td class="rt" align="right">生产日期：</td>
				<td class="ji"><input type="text" class="easyui-datetimebox input" name="madeDate" id="madeDate" style="width:150px" data-options="required:true,showSeconds:false"></td>
				<td class="rt" align="right">保质期：</td>
				<td class="ji"><input type="text" class="easyui-numberbox input" name="expirationDate" id="expirationDate" size="5" maxlength="10" value="0" data-options="min:0,precision:0">天</td>
			</tr>
			<tr class="ji">
				<td class="rt" align="right">所属分类：<label style="color: red;">*</label></td>
				<td class="ji" colspan="3"><input type="text" class="easyui-combobox input" name="category" id="category" data-options="min:0,precision:0" value="${gid}"></td>
			</tr>
			<tr class="tr ct">
				<td colspan="4">
					<input type="hidden" name="id" id="id"> 
					<input class="bginput" type="button" id="butSubmit" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butClose" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>
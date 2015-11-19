<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>分类产品列表</title>
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
		var pageSize = Math.round(($("#right", window.parent.document).height() - 160) / 23.6);
		$("#ductcy_grid").datagrid({
			url : "${pageContext.request.contextPath}/ductcy/readPagesDetails.do?gid=${cat.id}",
			datatype : "json",
			mtype : "POST",
			fit : true,
			nowrap : false,
			striped : true,
			pagination : true,
			fitColumns : true, 
			rownumbers : true,
			singleSelect : false,
			pageSize: pageSize,
			pageList: [pageSize,30,40,50],
			sortname : 'id',
			toolbar : [ '-', {
				id : 'btnClearSelections',
				text : "添加产品",
				iconCls : 'icon-add',
				handler : function() {
					initProductsGrid();
					$("#div_dialog").dialog({
						title:"添加产品",
						show:"slide",
						width:810,
						height:500,
						resizable:true,
						closed: false
					});
				}
			},'-', {
				id : 'btnDel',
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#ductcy_grid").datagrid("getSelections");
		            if($(row).length < 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		             var ids = "";
		            $.each(row,function(i,item){
		            	if(i)
		            		ids += ",";
		            	ids += item.id;
		            });
		            var delUrl = "${pageContext.request.contextPath}/ductcy/deleteDetails.do?t="+new Date().getTime();
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
							          data: "ids="+ids+"&gid=${cat.id}",//要发送的数据
							          success: function(data){
							          	if("success" == data.resultMessage){
							          		$("#ductcy_grid").datagrid("reload"); 
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
				field : "ck",
				checkbox : true
			},{
				field : "duct",
				title : "产品名称",
				width : $(this).width() * 0.2,
				align : "left",
				sortable : true,
				formatter : function(value,rec){
					if(rec != null && rec.products != null){
						return rec.products.name;
					}
					return "";
				}
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
		$($("#ductcy_grid").datagrid("getPager")).pagination({
			beforePageText : '第',
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		});
	});
	
	function initProductsGrid(){
		var pageSize = 15;
		$("#products_grid").datagrid({
			url : "${pageContext.request.contextPath}/products/readPagesOnline.do",
			datatype : "json",
			mtype : "POST",
			fit : true,
			nowrap : false,
			striped : false,
			rownumbers : true,
			fitColumns : true,
			pagination : true,
			pageSize: pageSize,
			pageList: [pageSize,30,40,50],
			sortname : 'id',
			toolbar : [ '-', {
				id : 'btnClearSelections',
				text : "添加到${cat.name}",
				iconCls : 'icon-add',
				handler : function(){
					var row = $("#products_grid").datagrid("getSelections");
		            if($(row).length < 1){
			            alert("请选择记录，至少选取一行！","提示消息");
		                return ;
		            }
		            var ids = "";
		            $.each(row,function(i,item){
		            	if(i)
		            		ids += ",";
		            	ids += item.id;
		            });
		            $.ajax({
						type: "post",
						dataType: "json",
						url: "${pageContext.request.contextPath}/ductcy/addDetails.do?t="+new Date().getTime(),
						data: "ids="+ids+"&gid=${cat.id}",//要发送的数据
						success: function(data){
							if("success" == data.resultMessage){
								$("#div_dialog").dialog("close");
								$("#ductcy_grid").datagrid("reload"); 
							}
						},
						error : function(data){
							
						}
					});
				}
			}],
			columns : [[{
				field : "ck",
				checkbox : true
			},{
				field : "name",
				title : "产品名称",
				width : $(this).width() * 0.2,
				align : "left",
				sortable : true
			}]]
		});
		$($("#products_grid").datagrid("getPager")).pagination({
			beforePageText : '第',
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		});
	}
</script>
</head>
<body>
	<table id="ductcy_grid" title="${cat.name}产品列表"></table>
	<div id="delete_div">确定删除所选内容?</div> 
	<div id="div_dialog">
		<table id="products_grid" title="&nbsp;"></table>		
	</div>
</body>
</html>
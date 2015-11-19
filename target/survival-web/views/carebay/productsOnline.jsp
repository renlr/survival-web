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
	$(document).ready(function() {
		var pageSize = Math.round(($("#right", window.parent.document).height() - 160) / 50);
		$("#products_grid").datagrid({
			url : "${pageContext.request.contextPath}/products/readPagesOnline.do",
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
			toolbar : ['-', {
				id : 'btnRedo',
				text : "下 线",
				iconCls : 'icon-redo',
				handler : function() {
					var row = $("#products_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var ids = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/products/online0.do?t="+new Date().getTime();
		             $("#delete_div").dialog({
						title : "下线提示", 
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
							          url: editurl,
							          data: "id="+ids,//要发送的数据
							          success: function(data){
							          	if("success" == data.resultMessage){
							          		$("#products_grid").datagrid("reload");
							          	}
							          },
									  error : function(data){}
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
				sortable : true
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
				sortable : true
			},{
				field : "inventory",
				title : "库存数量",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "quotaNumber",
				title : "限购数量",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "costPrice",
				title : "成本价格",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "bazaarPrice",
				title : "市场价格",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "sellPrice",
				title : "销售价格",
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
		$($("#products_grid").datagrid("getPager")).pagination({
			beforePageText : '第',
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		});
	});
</script>
</head>
<body>
	<table id="products_grid" title="上线商品"></table>
	<div id="delete_div">确定下线所选商品?</div> 
</body>
</html>
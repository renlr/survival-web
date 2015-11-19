<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>馨月宝宝</title>
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
		$("#carebay_grid").datagrid({
			url : "${pageContext.request.contextPath}/carebay/readPages.do",
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
			columns : [ [ {
				field : 'image',
				title : '宝宝头像',
				width : $(this).width() * 0.2,
				align : 'center',
				sortable : true,
				formatter : function(value,rec){
					if(null != value && "" != value){
						return "<img src='"+value+"' width='200' height='100'/>";
					}
					return "";
				}
			}, {
				field : 'nickname',
				title : '宝宝昵称',
				width : $(this).width() * 0.2,
				align : 'center',
				sortable : true
			}, {
				field : 'birthday',
				title : '宝宝生日',
				width : $(this).width() * 0.2,
				align : 'center',
				sortable : true,
				formatter : function(dateValue,rec){
					return dateValue.toString().toDate().format("yyyy-MM-dd");
				}
			}, {
				field : 'sex',
				title : '宝宝性别',
				width : $(this).width() * 0.2,
				align : 'center',
				sortable : true,
				formatter : function(value,rec){
					if(null != value){
						if("0" == value){
							return "女孩";
						}else if("1" == value){
							return "男孩";
						}
					}
					return " ";
				}
			}, {
				field : 'createDT',
				title : '创建时间',
				width : $(this).width() * 0.2,
				align : 'center',
				sortable : true,
				formatter : function(dateValue,rec){
					return dateValue.toString().toDate().format("yyyy-MM-dd hh:mm:ss");
				}
			} ] ]
		});
		$($("#carebay_grid").datagrid("getPager")).pagination({
			beforePageText : '第',
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		});
	});
</script>
</head>
<body>
	<table id="carebay_grid" title="馨月宝宝"></table>
</body>
</html>
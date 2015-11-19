<%@page import="com.baofeng.carebay.util.Constants"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>调查结果</title>
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
		$("#qans_grid").datagrid({
			url : "${pageContext.request.contextPath}/qans/readPages.do?inds=${inds}&userId=${userId}",
			datatype : "json",
			mtype : "POST",
			fit : true,
			nowrap : false,
			striped : true,
			fitColumns : true, 
			pagination : true,
			rownumbers : true,
			singleSelect : true,
			pageSize: pageSize,
			pageList: [pageSize,30,40,50],
			sortname : 'id',
			toolbar : ['-', {
				id : 'btnRedo',
				text : "返回上一级",
				iconCls : 'icon-redo',
				handler : function() {
					location.href = "${pageContext.request.contextPath}/care/user.do";
				}
			},'-'],
			columns : [[{
				field : "subject",
				title : "试题名称",
				width : $(this).width() * 0.2,
				align : "left",
				sortable : true
			},{
				field : "type",
				title : "试题类型",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					var html = "";
					if(value == 1){
						html = "单选";
					}else if(value == 2){
						html = "多选";
					}else if(value == 3){
						html = "填写";
					}
					return html;
				}
			},{
				field : "options",
				title : "用户结果",
				width : $(this).width() * 0.2,
				align : "left",
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
	});
</script>
</head>
<body>
	<table id="qans_grid" title="调查结果"></table>
</body>
</html>
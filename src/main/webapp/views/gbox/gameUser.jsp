<%@page import="com.baofeng.carebay.util.Constants"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>游戏用户</title>
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
		var pageSize = Math.round(($("#right", window.parent.document).height() - 160) / 23.5);
		$("#guser_grid").datagrid({
			url : "${pageContext.request.contextPath}/guser/readPages.do",
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
				id : 'btnCoins',
				text : "充值",
				iconCls : 'icon-add',
				handler : function() {
					$("#div_dialog").dialog({
						title:"充值",
						show:"slide",
						resizable:false,
						closed: false,
						resizable:false
					});
				}
			},'-', {
				id : 'btnDel',
				text : "禁用",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#poster_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var ids = row[0].id;
		            var delUrl = "${pageContext.request.contextPath}/poster/delete.do?t="+new Date().getTime();
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
							          		$("#poster_grid").datagrid("reload"); 
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
				field : "name",
				title : "用户名称",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "email",
				title : "登录邮箱",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "gameCoins",
				title : "用户钻石",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "freeCodes",
				title : "抢激活码",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "freeDraws",
				title : "免费抽奖",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "integral",
				title : "用户积分",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "status",
				title : "用户状态",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter:function(value,rec){
					if(value == 0){
						return "禁用";
					}
					return "正常";
				}
			},{
				field : "createDT",
				title : "创建时间",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(date,rec){
					return date.toString().toDate().format("yyyy-MM-dd hh:mm:ss");
				}
			}]]
		});
	});
</script>
</head>
<body>
	<table id="guser_grid" title="游戏用户"></table>
	<div id="delete_div">确定删除所选内容吗？</div>
</body>
</html>
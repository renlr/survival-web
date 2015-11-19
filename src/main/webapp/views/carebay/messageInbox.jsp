<%@page import="com.baofeng.carebay.util.Constants"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>意见反馈</title>
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
		$("#msgInbox_grid").datagrid({
			url : "${pageContext.request.contextPath}/msgInbox/readPages.do",
			datatype : "json",
			mtype : "POST",
			fit : true,
			nowrap : false,
			striped : true,
			fitColumns : true, 
			pagination : true,
			rownumbers : true,
			singleSelect : true,
			pageSize: 30,
			pageList: [30,40,50],
			sortname : 'id',
			toolbar : [{
				id : 'btnbuy',
				text : "待｜已处理",
				iconCls : 'icon-edit',
				handler : function() {
					var row = $("#msgInbox_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/msgInbox/dealWith.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				        	 $("#msgInbox_grid").datagrid("reload");
				          },
						  error : function(data){
						  	alert("已处理操作失败!");
						  }
					});
				}
			}],
			columns : [[{
				field : "message",
				title : "消息内容",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "user",
				title : "反馈用户",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					if(rec.user != null)
						return rec.user.name;
					return "";
				}
			},{
				field : "chamber",
				title : "所在会所",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "msgtype",
				title : "意见类型",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					var htmls = ""; 
					if(value == 1){
						htmls = "投诉(餐饮)";
					}else if(value == 2){
						htmls = "投诉(服务)";
					}else if(value == 3){
						htmls = "投诉(购物)";
					}else if(value == 4){
						htmls = "建议(餐饮)";
					}else if(value == 5){
						htmls = "建议(服务)";
					}else if(value == 6){
						htmls = "建议(购物)";
					}
					return htmls;
				}
			},{
				field : "markRead",
				title : "意见状态",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					var htmls = "待处理";
					if(value == 1){
						htmls = "已处理";
					}
					return htmls;
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
	<table id="msgInbox_grid" title="意见反馈"></table>
</body>
</html>
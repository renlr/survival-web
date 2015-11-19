<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>馨月会员</title>
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
		$("#user_grid").datagrid({
			url : "${pageContext.request.contextPath}/user/readPages.do",
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
				id : 'btnDel',
				text : "修改",
				iconCls : 'icon-edit',
				handler : function() {
					var row = $("#family_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/family/edit.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#id").val(data.id);
				          	$("#appellation").val(data.appellation);
				          	$("#description").val(data.description);
				          	$("#div_dialog").dialog();
				          },
						  error : function(data){
						  	alert("修改系统管理用户失败!");
						  }
					});
				}
			}, '-', {
				id : 'btnDel',
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#family_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var delUrl = "${pageContext.request.contextPath}/family/delete.do?t="+new Date().getTime();
		            $.ajax({
				          type: "get",
				          dataType: "json",
				          url: delUrl,
				          data: "id="+id,//要发送的数据
				          success: function(data){},
						  error : function(data){
						  	$("#family_grid").datagrid('reload'); 
						  }
					});
				}
			}],
			columns : [ [ {
				field : 'image',
				title : '会员头像',
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
				field : 'accounts',
				title : '会员帐号',
				width : $(this).width() * 0.2,
				align : 'center',
				sortable : true
			}, {
				field : 'nickname',
				title : '会员昵称',
				width : $(this).width() * 0.2,
				align : 'center',
				sortable : true
			}, {
				field : 'email',
				title : '常用邮箱',
				width : $(this).width() * 0.2,
				align : 'center',
				sortable : true
			}, {
				field : 'phone',
				title : '手机号码',
				width : $(this).width() * 0.2,
				align : 'center',
				sortable : true
			}, {
				field : 'accountStatus',
				title : '账号状态',
				width : $(this).width() * 0.2,
				align : 'center',
				sortable : true,
				formatter : function(value,rec){
					if(null != value){
						if(value == "ENABLED"){
							return "正常";
						}else{
							return "禁用";
						}
					}
					return "  ";
				}
			}, {
				field : 'family',
				title : '亲属关系',
				width : $(this).width() * 0.2,
				align : 'center',
				sortable : true,
				formatter : function(value,rec){
					if(null != value){
						return value.appellation;
					}
					return "";
				}
			}, {
				field : 'carebay',
				title : '我家宝宝',
				width : $(this).width() * 0.2,
				align : 'center',
				sortable : true,
				formatter : function(value,rec){
					if(null != value){
						return value.nickname;
					}
					return "";
				}
			}, {
				field : 'third',
				title : '第三方登录',
				width : $(this).width() * 0.2,
				align : 'center',
				sortable : true
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
		$($("#user_grid").datagrid("getPager")).pagination({
			beforePageText : '第',
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		});
	});
</script>
</head>
<body>
	<table id="user_grid" title="馨月会员"></table>
</body>
</html>
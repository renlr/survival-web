<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>菜单页面</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel='stylesheet' href='${pageContext.request.contextPath}/views/css/admin_style.css'>
<link rel='stylesheet' href='${pageContext.request.contextPath}/views/css/dialog.css'>
<link rel="stylesheet" href="${pageContext.request.contextPath}/views/js/themes/default/easyui.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/views/js/themes/icon.css">
<script src="${pageContext.request.contextPath}/views/js/jquery-1.10.1.min.js"></script>
<script src="${pageContext.request.contextPath}/views/js/common.js"></script>
<script src="${pageContext.request.contextPath}/views/js/jquery.easyui.min.js"></script>
<script src="${pageContext.request.contextPath}/views/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var pageSize = Math.round(($("#right", window.parent.document).height() - 160) / 23.5);
		$("#page_grid").datagrid({
			url : "${pageContext.request.contextPath}/user/readPagesDetails.do?gid=${operator.id}",
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
				id : 'btnSelections',
				text : "添加",
				iconCls : "icon-add",
				handler : function() {
					initMenuGrid();
					$("#div_dialog").dialog({
						title:"添加菜单页面",
						show:"slide",
						width:810,
						height:500,
						resizable:true,
						closed: false
					});
				}
			},'-', {
				id : "btnDel",
				text : "删除",
				iconCls : "icon-no",
				handler : function() {
					var row = $("#page_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var delUrl = "${pageContext.request.contextPath}/user/deleteUserPages.do";
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
							          data: "id="+id,//要发送的数据
							          success: function(data){
							          	if("success" == data.resultMessage){
											$("#page_grid").datagrid("reload"); 
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
			columns : [ [ {
				field : "itemId",
				title : "菜单名称",
				width : $(this).width() * 0.2,
				align : "left",
				sortable : true
			}]]
		});
	});
	
	function initMenuGrid(){
		var pageSize = 15;
		$("#menu_grid").datagrid({
			url : "${pageContext.request.contextPath}/menu/readPages.do?t="+new Date().getTime(),
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
				text : "添加到${operator.name}帐号",
				iconCls : 'icon-add',
				handler : function(){
					var row = $("#menu_grid").datagrid("getSelections");
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
						url: "${pageContext.request.contextPath}/user/addUserPages.do?t="+new Date().getTime(),
						data: "ids="+ids+"&gid=${operator.id}",//要发送的数据
						success: function(data){
							if("success" == data.resultMessage){
								$("#div_dialog").dialog("close");
								$("#page_grid").datagrid("reload"); 
							}
						},
						error : function(data){}
					});
				}
			},'-', {
				id : 'btnRedo',
				text : "返回上一级",
				iconCls : 'icon-redo',
				handler : function() {
					location.href = "${pageContext.request.contextPath}/user/show.do";
				}
			},'-'],
			columns : [[{
				field : "ck",
				checkbox : true
			},{
				field : "name",
				title : "菜单名称",
				width : $(this).width() * 0.2,
				align : "left",
				sortable : true
			}]]
		});
	}
</script>
</head>
<body>
	<table id="page_grid" title="菜单页面管理"></table>
	<div id="delete_div">确定删除所选内容?</div> 
	<div id="div_dialog"><table id="menu_grid" title="&nbsp;"></table></div>
</body>
</html>

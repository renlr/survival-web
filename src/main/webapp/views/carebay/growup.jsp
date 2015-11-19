<%@page import="com.baofeng.carebay.util.Constants"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>成长指标</title>
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
		$("#growup_grid").datagrid({
			url : "${pageContext.request.contextPath}/growup/readPages.do",
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
				id : 'btnClearSelections',
				text : "添加指标分组",
				iconCls : 'icon-add',
				handler : function() {
					$("#div_dialog").dialog({
						title:"添加指标分组",
						show:"slide",
						resizable:false,
						closed: false,
						resizable:false
					});
				}
			}, '-', {
				id : 'btnDel',
				text : "修改",
				iconCls : 'icon-edit',
				handler : function() {
					var row = $("#growup_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var name = row[0].name;
		            var editurl = "${pageContext.request.contextPath}/growup/edit.do?t="+new Date().getTime();
		           	if("用户指标" != name){
		           		$.ajax({
					          type: "get",
					          dataType: "json",
					          url: editurl,
					          data: "id="+id,
					          success: function(data){
					          	$("#id").val(data.id);
					          	$("#name").val(data.name);
					          	$("#indexs").val(data.indexs);
					          	$("#div_dialog").dialog();
					          },error : function(data){
							  	alert("修改失败!");
							  }
						});
		           	}else{
		           		alert(name+"不允许操作!");
		           	}
				}
			},'-', {
				id : 'btnDel',
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#growup_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var ids = row[0].id;
		            var name = row[0].name;
		            var delUrl = "${pageContext.request.contextPath}/growup/delete.do?t="+new Date().getTime();
					if("用户指标" != name){
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
								          		$("#growup_grid").datagrid("reload"); 
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
						alert(name+"不允许操作!");
					}
				}
			}],
			columns : [ [ {
				field : "name",
				title : "指标分组",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			}, {
				field : "growup",
				title : "内容管理",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return '<a href="${pageContext.request.contextPath}/growup/addGrowupIndex.do?id='+rec.id+'">内容管理</a>';
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
			} ] ]
		});
		$($("#growup_grid").datagrid("getPager")).pagination({
			beforePageText : '第',
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		});
		$("#butrs").click(function(){
			$("#id").val("");
			$("#name").val("");
			$("#div_dialog").dialog("close");
		});
		function submits(){
			$("#growupForm").form("submit",{
				onSubmit:function(){
					var name = $("#name").val(); 
					if($.trim(name) == ""){ 
						alert("指标分组名称不能为空！"); 
						$("#name").focus(); 
						return false; 
					}
				},
				success:function(data){
					$("#id").val("");
					$("#name").val("");
					$("#div_dialog").dialog("close");
					$("#growup_grid").datagrid("reload"); 
				}
			});
		}
		
		$("#buts").click(function(){
			var id = $("#id").val();
			var name = $("#name").val();
			var isValiurl = "${pageContext.request.contextPath}/growup/isVildateUrl.do?t="+new Date().getTime();
			$.ajax({
				type: "get",
				dataType: "json",
				url: isValiurl,
				data: "name="+name,
				success: function(data){
					if(id == "" && data.resultMessage == "success"){
						alert(name+"已存在，请填其他名称!"); 
					}else{
						submits();
					}
				},
				error : function(data){
					submits();
				}
			});	
		});
	});
</script>
</head>
<body>
	<table id="growup_grid" title="成长指标"></table>
	<div id="delete_div">确定删除所选内容?</div> 
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/growup/save.do" method="post" id="growupForm" enctype="multipart/form-data">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt" align="right">指标分组：</td>
				<td><input type="text" name="name" id="name" size="25" maxlength="50" class="input">*必填</td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id" id="id"> 
					<input class="bginput" type="button" id="buts" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butrs" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>上传服务器</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" content="ie=edge"/>
<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/views/css/admin_style.css'>
<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/views/css/dialog.css'>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/views/js/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/views/js/themes/icon.css">
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/jquery-1.10.1.min.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/common.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/jquery.easyui.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var pageSize = Math.round(($("#right", window.parent.document).height() - 160) / 126);
		$("#upserver_grid").datagrid({
			url : "${pageContext.request.contextPath}/upserver/readPages.do",
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
				text : "添加上传服务",
				iconCls : 'icon-add',
				handler : function() {
					$("#div_dialog").dialog({
						title:"添加上传服务",
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
					var row = $("#upserver_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/upserver/edit.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#id").val(data.id);
				          	$("#host").val(data.host);
				          	$("#port").val(data.port);
				          	$("#tusdata").val(data.tusdata);
				          	$("#div_dialog").dialog();
				          },
						  error : function(data){
						  	$("#upserver_grid").datagrid('reload'); 
						  }
					});
				}
			}, '-', {
				id : 'btnDel',
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#upserver_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var ids = row[0].id;
		            var delUrl = "${pageContext.request.contextPath}/upserver/delete.do?t="+new Date().getTime();
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
										$("#upserver_grid").datagrid('reload'); 
								    }
								},
								error : function(data){
									$("#upserver_grid").datagrid('reload'); 
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
			columns : [ [ {
				field : 'host',
				title : '上传主机',
				width : $(this).width() * 0.2,
				align : 'center',
				sortable : true
			},{
				field : 'port',
				title : '上传端口',
				width : $(this).width() * 0.2,
				align : 'center',
				sortable : true
			},{
				field : 'tusdata',
				title : '存储路径',
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
		$($("#upserver_grid").datagrid("getPager")).pagination({
			beforePageText : '第',
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		});
		
		$("#upserverForm").submit(function(){
			var host=$("#host").val(); 
			var port=$("#port").val(); 
			var tusdata=$("#tusdata").val(); 
			if($.trim(host) == ""){ 
				alert("主机名称不能为空！"); 
				$("#host").focus(); 
				return false; 
			}
			if($.trim(port) == ""){ 
				alert("上传端口不能为空！"); 
				$("#port").focus(); 
				return false; 
			}
			if($.trim(tusdata) == ""){ 
				alert("上传服务器上存储路径不能为空！"); 
				$("#tusdata").focus(); 
				return false; 
			}
		});
	});
</script>
</head>
<body>
	<table id="upserver_grid" title="上传服务"></table>
	<div id="delete_div">确定删除所选内容?</div> 
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/upserver/save.do" method="post" id="upserverForm">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt" align="right">主机名称：</td>
				<td><input type="text" name="host" id="host" size="25" maxlength="50" class="input">*必填</td>
			</tr>
			<tr class="ji">
				<td class="rt" align="right">上传端口：</td>
				<td><input type="text" name="port" id="port" size="25" maxlength="50" class="input">*必填</td>
			</tr>
			<tr class="ji">
				<td class="rt" align="right">存储路径：</td>
				<td><input type="text" name="tusdata" id="tusdata" size="25" maxlength="50" class="input">*必填</td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id" id="id"> 
					<input class="bginput" type="submit" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" value="重置">
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>
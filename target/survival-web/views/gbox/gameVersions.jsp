<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>更新版本</title>
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
		var pageSize = Math.round(($("#right", window.parent.document).height() - 160) / 105);
		$("#version_grid").datagrid({
			url : "${pageContext.request.contextPath}/version/readPages.do",
			datatype : "json",
			mtype : "POST",
			nowrap : false,
			striped : true,
			fitColumns : true, 
			fit : true,
			pageSize: 30,
			pageList: [pageSize,30,40,50],
			pagination : true,
			sortname : 'id',
			singleSelect : true,
			rownumbers : true,
			toolbar : [ '-', {
				id : 'btnClearSelections',
				text : "添加版本",
				iconCls : 'icon-add',
				handler : function() {
					$("#div_dialog").dialog({
						title:"添加版本",
						show:"slide",
						resizable:false,
						closed: false,
						resizable:false
					});
				}
			},'-', {
				id : 'btnDel',
				text : "修改",
				iconCls : 'icon-edit',
				handler : function() {
					var row = $("#version_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var online = row[0].online;
		            if(online == 1){
		            	alert("上线中不允许编辑!","提示消息");
		                return ;
		            }
		            var editurl = "${pageContext.request.contextPath}/version/edit.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#id").val(data.id);
				          	$("#appname").val(data.appname);
				          	$("#versions").val(data.versions);
				          	$("#appurl").val(data.appurl);
				          	$("#androidUrl").val(data.androidUrl);
				          	$("#content").val(data.content);
				          	$("#forceVersion").val(data.forceVersion);
				          	$("#div_dialog").dialog();
				          },
						  error : function(data){
						  	$("#version_grid").datagrid("reload"); 
						  }
					});
				}
			},'-', {
				id : 'btnDel',
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#version_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var ids = row[0].id;
		            var delUrl = "${pageContext.request.contextPath}/version/delete.do?t="+new Date().getTime();
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
							          		$("#version_grid").datagrid("reload"); 
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
				field : "appname",
				title : "应用名称",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "versions",
				title : "版本号",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "appurl",
				title : "IOS地址",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "androidUrl",
				title : "ANDROID地址",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "content",
				title : "更新内容",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "forces",
				title : "是否强制",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter:function(value,rec){
					if(value == 1){
						return "是";						
					}
					return "";						
				}
			},{
				field : "forceVersion",
				title : "强制版本",
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
		$("#butrs").click(function(){
			$("#id").val("");
			$("#images").val("");
			$("#indexs").val("");
			$("#div_dialog").dialog("close");
		});
		$("#versionForm").submit(function(){
			var appname = $("#appname").val();
			var versions = $("#versions").val();
			if($.trim(appname) == ""){
				alert("应用名称必须填写！"); 
				$("#appname").focus(); 
				return false; 
			}
			if($.trim(versions) == ""){ 
				alert("版本号必须填写！"); 
				$("#versions").focus(); 
				return false; 
			}
		});
	});
</script>
</head>
<body>
	<table id="version_grid" title="更新版本"></table>
	<div id="delete_div">确定删除所选内容?</div> 
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/version/save.do" method="post" id="versionForm">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt">应用名称：<label style="color: red;">*</label></td>
				<td class="ji"><input type="text" name="appname" id="appname" class="input"></td>
			</tr>
			<tr class="tr">
				<td class="rt">版本号：<label style="color: red;">*</label></td>
				<td class="ji"><input type="text" name=versions id="versions" class="input"></td>
			</tr>
			<tr class="ji">
				<td class="rt">IOS地址：</td>
				<td class="ji"><input type="text" name="appurl" id="appurl" class="input" size="50"></td>
			</tr>
			<tr class="ji">
				<td class="rt">ANDROID地址：</td>
				<td class="ji"><input type="text" name="androidUrl" id="androidUrl" class="input" size="50"></td>
			</tr>
			<tr class="tr">
				<td class="rt">更新内容：</td>
				<td class="ji"><textarea id="content" name="content" style="height:40px;width:450px;"></textarea></td>
			</tr>
			<tr class="ji">
				<td class="rt">强制更新：</td>
				<td class="ji"><input type="checkbox" id="forces" name="forces" value="1"></td>
			</tr>
			<tr class="ji">
				<td class="rt">强制版本：</td>
				<td class="ji"><input type="text" name=forceVersion id="forceVersion" class="input"></td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id" id="id">
					<input class="bginput" type="submit" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butrs" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>
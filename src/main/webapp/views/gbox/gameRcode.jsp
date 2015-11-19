<%@page import="com.baofeng.carebay.util.Constants"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>兑换码</title>
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
		$("#prize_grid").datagrid({
			url : "${pageContext.request.contextPath}/prize/readPagesDetails.do?gid=${gid}",
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
			toolbar : [ '-', {
				id : 'btnAdd',
				text : "导入兑换码",
				iconCls : 'icon-add',
				handler : function() {
					$("#div_dialog").dialog({
						title:"导入兑换码",
						show:"slide",
						resizable:false,
						closed: false,
						resizable:false
					});
				}
			},'-', {
				id : 'btnRedo',
				text : "返回上一级",
				iconCls : 'icon-redo',
				handler : function() {
					location.href = "${pageContext.request.contextPath}/prize/show.do";
				}
			}],
			columns : [[{
				field : "code",
				title : "兑换码",
				width : $(this).width() * 0.2,
				align : "left",
				sortable : true
			},{
				field : "enabled",
				title : "状态",
				width : $(this).width() * 0.2,
				align : "left",
				sortable : true,
				formatter : function(value,rec){
					if(value == 0){
						return "未抽奖";
					}else if(value == 1){
						return "已抽奖";
					}			
					return "";
				}
			}]]
		});
		$("#butrs").click(function(){
			$("#id").val("");
			$("#div_dialog").dialog("close");
		});
		$("#prizeForm").submit(function(){
			var rcode = $("#rcode").val();
			if($.trim(rcode) == ""){ 
				alert("必须选择兑换码文件!"); 
				$("#rcode").focus(); 
				return false; 
			}
		});
	});
</script>
</head>
<body>
	<table id="prize_grid" title="兑换码"></table>
	<div id="delete_div">确定删除所选内容吗？</div> 
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/prize/saveDetails.do" method="post" id="prizeForm" enctype="multipart/form-data">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="tr">
				<td class="rt">导入兑换码：<label style="color: red;">*</label></td>
				<td class="ji"><input type="file" name="rcode" id="rcode" size="25">目前只支持*.txt文件</td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id" id="id">
					<input type="hidden" name="gid" id="gid" value="${gid}">
					<input class="bginput" type="submit" id="buts" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butrs" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>
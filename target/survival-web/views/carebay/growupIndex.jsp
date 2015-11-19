<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>成长指标数据管理</title>
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
		$("#growupIndex_grid").datagrid({
			url : "${pageContext.request.contextPath}/growupIndex/readPages.do?groupId=${growup.id}",
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
				text : "导入参考指标数据",
				iconCls : 'icon-add',
				handler : function() {
					if("用户指标" != "${growup.name}"){
						$("#div_dialog").dialog({
							title : "导入参考指标数据",
							show : "slide",
							resizable : false,
							closed : false,
							resizable : false
						});
					}else{
						alert("用户指标数据不能进行导入!");
					}
				}
			},'-', {
				id : 'btnDel',
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#growupIndex_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var ids = row[0].id;
		            var name = "${growup.name}";
		            var delUrl = "${pageContext.request.contextPath}/growupIndex/delete.do?t="+new Date().getTime();
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
								          		$("#growupIndex_grid").datagrid("reload"); 
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
						alert("用户指标数据不能进行导入!");
					}
				}
			}],
			columns : [ [ {
				field : "careBay",
				title : "宝宝昵称",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					if(null != value && rec.careBay != null){
						return rec.careBay.nickname;
					}
					return "参考数据";
				}
			},{
				field : "user",
				title : "会员昵称",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					if(null != value && rec.user != null){
						return rec.user.nickname;
					}
					return "参考数据";
				}
			},{
				field : "weight",
				title : "体重指数(kg)",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "height",
				title : "身高指数(cm)",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "bmi",
				title : "BMI指数",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "month",
				title : "宝宝月数",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "mentDT",
				title : "测量日期",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(dateValue,rec){
					if(null != dateValue)
						return dateValue.toString().toDate().format("yyyy-MM-dd");
					return "";
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
		$($("#growupIndex_grid").datagrid("getPager")).pagination({
			beforePageText : '第',
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		});
		$("#growupIndexForm").submit(function(){
			var data=$("#data").val(); 
			if($.trim(data) == ""){ 
				alert("导入数据必须选择文件！"); 
				$("#data").focus(); 
				return false; 
			}
		});
		$("#groupId").val("${growup.id}");
		$("#butrs").click(function(){
			$("#id").val("");
			$("#data").val("");
			$("#div_dialog").dialog("close");
		});
	});
</script>
</head>
<body>
	<table id="growupIndex_grid" title="${growup.name}成长指标数据管理"></table>
	<div id="delete_div">确定删除所选内容?</div>
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/growupIndex/save.do" method="post" id="growupIndexForm" enctype="multipart/form-data">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt">导入数据：</td>
				<td><input type="file" name="data" id="data" size="25">*必须选择文件</td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="groupId" id="groupId"> 
					<input type="hidden" name="id" id="id"> 
					<input class="bginput" type="submit" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butrs" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>
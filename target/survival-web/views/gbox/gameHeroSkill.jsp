<%@page import="com.baofeng.carebay.util.Constants"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>英雄资料技能</title>
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
		var pageSize = Math.round(($("#right", window.parent.document).height() - 160) / 106);
		$("#hero_grid").datagrid({
			url : "${pageContext.request.contextPath}/hero/readPagesSkill.do?gid=${gid}",
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
			pageList: [pageSize,30,40,50],
			sortname : 'id',
			toolbar : [ '-', {
				id : 'btnAdd',
				text : "添加英雄技能",
				iconCls : 'icon-add',
				handler : function() {
					$("#div_dialog").dialog({
						title:"添加英雄技能",
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
					var row = $("#hero_grid").datagrid("getSelections");
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
		            var editurl = "${pageContext.request.contextPath}/hero/editSkill.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#id").val(data.id);
				          	$("#name").val(data.name);
				          	$("#consume").val(data.consume);
				          	$("#scopes").val(data.scopes);
				          	$("#cooling").val(data.cooling);
				          	$("#describes").val(data.describes);
				          	$("#strategy").val(data.strategy);
				          	$("#div_dialog").dialog();
				          },
						  error : function(data){
						  	$("#hero_grid").datagrid("reload"); 
						  }
					});
				}
			},'-', {
				id : 'btnDel',
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#hero_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var ids = row[0].id;
		            var online = row[0].online;
		            if(online == 1){
		            	alert("上线中不允许删除!","提示消息");
		                return ;
		            }
		            var delUrl = "${pageContext.request.contextPath}/hero/deleteSkill.do?t="+new Date().getTime();
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
										$("#hero_grid").datagrid("reload"); 
								    }
								},
								error : function(data){
									$("#hero_grid").datagrid("reload"); 
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
			},'-', {
				id : 'btnRedo',
				text : "返回上一级",
				iconCls : 'icon-redo',
				handler : function() {
					location.href = "${pageContext.request.contextPath}/hero/show.do";
				}
			}],
			columns : [[{
				field : "image",
				title : "技能图片",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					if(value != null && value != "")
						return "<img src='"+value+"' width='108' height='108'/>";
					return "";
				}
			},{
				field : "name",
				title : "技能名称",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "describes",
				title : "技能描述",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "consume",
				title : "技能消耗",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "scopes",
				title : "技能范围",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "cooling",
				title : "技能冷却",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "strategy",
				title : "英雄攻略",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
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
		$("#butrs").click(function(){
			$("#id").val("");
			$("#name").val("");
			$("#navId").val("");
			$("#url").val("");
			$("#images").val("");
			$("#div_dialog").dialog("close");
		});
		$("#heroForm").submit(function(){
			var name = $("#name").val(); 
			if($.trim(name) == ""){
				alert("技能名称不能为空！"); 
				$("#name").focus(); 
				return false; 
			}
		});
	});
</script>
</head>
<body>
	<table id="hero_grid" title="英雄资料技能"></table>
	<div id="delete_div">确定删除所选内容吗？</div> 
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/hero/saveSkill.do" method="post" id="heroForm" enctype="multipart/form-data">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt">技能名称：<label style="color: red;">*</label></td>
				<td class="ji"><input type="text" name="name" id="name" style="width:200px;" maxlength="200" class="input"></td>
			</tr>
			<tr class="tr">
				<td class="rt">技能图片：</td>
				<td class="ji"><input type="file" name="images" id="images" size="25">修改时不选择,<span class="p-size">&nbsp;图片尺寸：108*108</span></td>
			</tr>
			<tr class="ji">
				<td class="rt">技能消耗：</td>
				<td class="ji"><input type="text" name="consume" id="consume" style="width:200px;" maxlength="200" class="input"></td>
			</tr>
			<tr class="ji">
				<td class="rt">技能范围：</td>
				<td class="ji"><input type="text" name="scopes" id="scopes" style="width:200px;" maxlength="200" class="input"></td>
			</tr>
			<tr class="ji">
				<td class="rt">技能冷却：</td>
				<td class="ji"><input type="text" name="cooling" id="cooling" style="width:200px;" maxlength="200" class="input"></td>
			</tr>
			<tr class="ji">
				<td class="rt">技能描述：<label style="color: red;">*</label></td>
				<td class="ji"><textarea id="describes" name="describes" style="height:40px;width:450px;"></textarea></td>
			</tr>
			<tr class="ji">
				<td class="rt">英雄攻略：<label style="color: red;">*</label></td>
				<td class="ji"><textarea id="strategy" name="strategy" style="height:40px;width:450px;"></textarea></td>
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
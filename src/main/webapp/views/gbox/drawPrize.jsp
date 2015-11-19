<%@page import="com.baofeng.carebay.util.Constants"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<title>魔盒奖品</title>
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
		var pageSize = Math.round(($("#right", window.parent.document).height() - 160) / 106);
		$("#prize_grid").datagrid({
			url : "${pageContext.request.contextPath}/prize/readPages.do",
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
				text : "添加奖品",
				iconCls : 'icon-add',
				handler : function() {
					$("#div_dialog").dialog({
						title:"添加奖品",
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
					var row = $("#prize_grid").datagrid("getSelections");
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
		            var editurl = "${pageContext.request.contextPath}/prize/edit.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#id").val(data.id);
				          	$("#name").val(data.name);
				          	$("#type").combobox("setValue",data.type);
				          	$("#freq").numberbox("setValue",data.freq);
				          	$("#totalUsers").numberbox("setValue",data.totalUsers);
				          	$("#dailyUsers").numberbox("setValue",data.dailyUsers);
				          	if(data.beginDT != null)
								$("#beginDT").datebox("setValue",data.beginDT.toString().toDate().format("yyyy-MM-dd hh:mm"));
				          	if(data.finishDT != null)
								$("#finishDT").datebox("setValue",data.finishDT.toString().toDate().format("yyyy-MM-dd hh:mm"));
				          	$("#div_dialog").dialog();
				          },
						  error : function(data){
						  	$("#prize_grid").datagrid("reload"); 
						  }
					});
				}
			},'-', {
				id : 'btnBegin',
				text : "开启 | 关闭抽奖",
				iconCls : 'icon-redo',
				handler : function() {
					var row = $("#prize_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var online = row[0].online;
		            var editurl = "${pageContext.request.contextPath}/prize/drawing.do?t="+new Date().getTime();
		            if(online == 1){
		            	$.ajax({
					          type: "get",
					          dataType: "json",
					          url: editurl,
					          data: "id="+id,
					          success: function(data){
					          	if("success" == data.resultMessage){
						          	$("#prize_grid").datagrid("reload"); 
					          	}
					          },
							  error : function(data){
							  	$("#prize_grid").datagrid("reload"); 
							  }
						});
		            }else{
		            	alert("只有上线中的奖品才能开启抽奖功能!","提示消息");
		                return ;
		            }
				}
			},'-', {
				id : 'btnRedo',
				text : "上  | 下线",
				iconCls : 'icon-redo',
				handler : function() {
					var row = $("#prize_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/prize/online.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	if("success" == data.resultMessage){
					          	$("#prize_grid").datagrid("reload"); 
				          	}
				          },
						  error : function(data){
						  	$("#prize_grid").datagrid("reload"); 
						  }
					});
				}
			},'-', {
				id : 'btnDel',
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#prize_grid").datagrid("getSelections");
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
		            var delUrl = "${pageContext.request.contextPath}/prize/delete.do?t="+new Date().getTime();
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
										$("#prize_grid").datagrid("reload"); 
								    }
								},
								error : function(data){
									$("#prize_grid").datagrid("reload"); 
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
				field : "image",
				title : "奖品图片",
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
				title : "奖品名称",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "type",
				title : "奖品类型",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter:function(value,rec){
					if(value == 1)
						return "激活码";
					else if(value == 2)
						return "大礼包";
					return "";
				}
			},{
				field : "numbers",
				title : "奖品数量",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "totalUsers",
				title : "参与人数",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "hitUsers",
				title : "已发奖数",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "dailyUsers",
				title : "每日限奖数",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "freq",
				title : "中奖频率",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter:function(value,rec){
					return value+"%";
				}
			},{
				field : "drawState",
				title : "奖品状态",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter:function(value,rec){
					if(value == 1)
						return "<font color='green'>抽奖中</font>";
					else if(value == 2)
						return "<font color='red'>已结束</font>";
					return "未开始";
				}
			},{
				field : "beginDT",
				title : "开始时间",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					if(value != null && value != "")
						return value.toString().toDate().format("MM-dd hh:mm");
					return "";
				}
			},{
				field : "finishDT",
				title : "结束时间 ",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					if(value != null && value != "")
						return value.toString().toDate().format("MM-dd hh:mm");
					return "抽完结束";
				}
			},{
				field : "online",
				title : "是否上线",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					if(null != value){
						if(0 == value){
							return "未上线";
						}else if(1 == value){
							return "<font color='green'>上线中</font>";
						}else{
							return "<font color='red'>已下线</font>";
						}
					}
					return "";
				}
			},{
				field : "rcode",
				title : "兑换码",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return '<a href="${pageContext.request.contextPath}/prize/details.do?gid='+rec.id+'">兑换码</a>';
				}
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
			$("#name").val("");
			$("#navId").val("");
			$("#url").val("");
			$("#images").val("");
			$("#div_dialog").dialog("close");
		});
		$("#prizeForm").submit(function(){
			var type=$("#type").combobox("getValue"); 
			var name = $("#name").val(); 
			var freq=$("#freq").numberbox("getValue"); 
			var dailyUsers=$("#dailyUsers").numberbox("getValue"); 
			if($.trim(type) == ""){ 
				alert("奖品类型必须填写！"); 
				$("#type").focus(); 
				return false; 
			}
			if($.trim(name) == ""){ 
				alert("奖品名称必须填写！"); 
				$("#name").focus(); 
				return false; 
			}
			if($.trim(freq) == ""){ 
				alert("中奖频率必须填写！"); 
				$("#freq").focus(); 
				return false; 
			}
			if($.trim(dailyUsers) == ""){ 
				alert("每日限奖数必须填写！"); 
				$("#dailyUsers").focus(); 
				return false; 
			}
		});
	});
</script>
</head>
<body>
	<table id="prize_grid" title="魔盒奖品"></table>
	<div id="delete_div">确定删除所选内容吗？</div> 
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/prize/save.do" method="post" id="prizeForm" enctype="multipart/form-data">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt">奖品类型：<label style="color: red;">*</label></td>
				<td class="ji">
					<select id="type" name="type" class="easyui-combobox" style="width:200px;">
					    <option value="1">激活码</option>
					    <option value="2">大礼包</option>
					</select>
				</td>
			</tr>
			<tr class="ji">
				<td class="rt">奖品名称：<label style="color: red;">*</label></td>
				<td class="ji"><input type="text" name="name" id="name" style="width:200px;" maxlength="200" class="input"></td>
			</tr>
			<tr class="tr">
				<td class="rt">奖品图片：</td>
				<td class="ji"><input type="file" name="images" id="images" size="25">修改时不选择,<span class="p-size">&nbsp;图片尺寸：108*108</span></td>
			</tr>
			<tr class="tr">
				<td class="rt">中奖频率：<label style="color: red;">*</label></td>
				<td class="ji"><input type="text" name="freq" id="freq" size="3" maxlength="10" value="5" class="easyui-numberbox input">%</td>
			</tr>
			<tr class="tr">
				<td class="rt">每日限奖数：<label style="color: red;">*</label></td>
				<td class="ji"><input type="text" name="dailyUsers" id="dailyUsers" size="3" maxlength="10" value="5" class="easyui-numberbox input">默认不限制</td>
			</tr>
			<tr class="tr">
				<td class="rt">开始时间：<label style="color: red;">*</label></td>
				<td class="ji"><input type="text" class="easyui-datetimebox input" id="beginDT" name="beginDT" data-options="required:true,showSeconds:false" style="width:150px"></td>
			</tr>
			<tr class="tr">
				<td class="rt">结束时间：</td>
				<td class="ji"><input type="text" class="easyui-datetimebox input" id="finishDT" name="finishDT" data-options="required:true,showSeconds:false" style="width:150px">默认奖品抽完结束</td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id" id="id"> 
					<input class="bginput" type="submit" id="buts" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butrs" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>
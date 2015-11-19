<%@page import="com.baofeng.carebay.util.Constants"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>英雄资料</title>
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
			url : "${pageContext.request.contextPath}/hero/readPages.do",
			datatype : "json",
			mtype : "POST",
			fit : true,
			nowrap : false,
			striped : true,
			fitColumns : true, 
			pagination : true,
			rownumbers : true,
			pageSize: 30,
			pageList: [pageSize,30,40,50],
			sortname : 'id',
			toolbar : [ '-', {
				id : 'btnAdd',
				text : "添加英雄资料",
				iconCls : 'icon-add',
				handler : function() {
					$("#div_dialog").dialog({
						title:"添加英雄资料",
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
		            var editurl = "${pageContext.request.contextPath}/hero/edit.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#id").val(data.id);
				          	$("#name").val(data.name);
				          	$("#type").combobox("setValues",data.type.split(','));
				          	$("#indexs").numberbox("setValue",data.indexs);
				          	$("#div_dialog").dialog();
				          },
						  error : function(data){
						  	$("#hero_grid").datagrid("reload"); 
						  }
					});
				}
			},'-', {
				id : 'btnRedo',
				text : "上  | 下线",
				iconCls : 'icon-redo',
				handler : function() {
					var row = $("#hero_grid").datagrid("getSelections");
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
		            var editurl = "${pageContext.request.contextPath}/hero/online.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+ids,
				          success: function(data){
				          	if("success" == data.resultMessage){
					          	$("#hero_grid").datagrid("reload"); 
				          	}
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
		            var delUrl = "${pageContext.request.contextPath}/hero/delete.do?t="+new Date().getTime();
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
			},'-',{
				id : 'btnsearch',
				text : "高级搜索",
				iconCls : 'icon-search',
				handler : function() {
					$("#search_dialog").dialog({
   						title:"高级搜索",
   						show:"slide",
   						resizable:true,
   						closed: false,
   						buttons:[{
   							text : "搜索",
   							handler:function(){
   								var names = $("#names").val();
   								var type = $("#type").combobox("getValue");
   								var text = $("#type1").combobox("getText");
   								if($.trim(text) == "")
   									type = null;
   								$("#hero_grid").datagrid("load",{name : names,type : type});
								$("#names").val("");
								$("#type").combobox("setValue","-1");
								$("#search_dialog").dialog("close");
   							}
   						},{
   							text:"关闭",
   							handler:function(){
   								$("#names").val("");
								$("#type").combobox("setValue","-1");
								$("#search_dialog").dialog("close");
   							}
   						}]
   					});
				}
			}],
			columns : [[{
				field : "ck",
				checkbox : true
			},{
				field : "image",
				title : "英雄图片",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					if(value != null && value != "")
						return "<img src='"+value+"' width='28' height='28'/>";
					return "";
				}
			},{
				field : "name",
				title : "英雄名称",
				width : $(this).width() * 0.2,
				align : "left",
				sortable : true
			},{
				field : "type",
				title : "英雄类型",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter:function(value,rec){
					var htmls = "";
					if(value != ""){
						var ids = value.split(",");
						for(var i = 0;i<ids.length;i++){
							var id = ids[i];
							if(id == "1"){
								htmls += "输出,";
							}else if(id == "2"){
								htmls += "支援,";
							}else if(id == "3"){
								htmls += "爆发,";
							}else if(id == "4"){
								htmls += "控制,";
							}else if(id == "5"){
								htmls += "先锋,";
							}
						}
						htmls = htmls.substring(0, htmls.length - 1);
					}
					return htmls;
				}
			},{
				field : "indexs",
				title : "显示顺序",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
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
				field : "toMenu",
				title : "详细内容",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return '<a href="${pageContext.request.contextPath}/hero/skill.do?gid='+rec.id+'">资料</a>&nbsp;&nbsp;'
						  +'<a href="${pageContext.request.contextPath}/hero/video.do?gid='+rec.id+'">视频</a>';
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
		$("#heroForm").submit(function(){
			var type=$("#type").combobox("getValue"); 
			var name=$("#name").val(); 
			var indexs=$("#indexs").numberbox("getValue"); 
			if($.trim(type) == ""){
				alert("英雄类型必须选择！"); 
				$("#type").focus(); 
				return false; 
			}
			if($.trim(name) == ""){
				alert("英雄名称不能为空！"); 
				$("#name").focus(); 
				return false; 
			}
			if($.trim(indexs) == ""){
				alert("显示顺序必须填写！"); 
				$("#indexs").focus(); 
				return false;
			}
		});
	});
</script>
</head>
<body>
	<table id="hero_grid" title="英雄资料"></table>
	<div id="delete_div">确定删除所选内容吗？</div> 
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/hero/save.do" method="post" id="heroForm" enctype="multipart/form-data">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt">英雄类型：<label style="color: red;">*</label></td>
				<td class="ji">
					<select id="type" name="type" class="easyui-combobox" style="width:200px;" data-options="multiple:true,panelHeight:'auto'">
					    <option value="1">输出</option>
					    <option value="2">支援</option>
					    <option value="3">爆发</option>
					    <option value="4">控制</option>
					    <option value="5">先锋</option>
					</select>
				</td>
			</tr>
			<tr class="ji">
				<td class="rt">英雄名称：<label style="color: red;">*</label></td>
				<td class="ji"><input type="text" name="name" id="name" style="width:200px;" maxlength="200" class="input"></td>
			</tr>
			<tr class="tr">
				<td class="rt">英雄图片：</td>
				<td class="ji"><input type="file" name="images" id="images" size="25">修改时不选择,<span class="p-size">&nbsp;图片尺寸：108*108</span></td>
			</tr>
			<tr class="tr">
				<td class="rt">显示顺序：<label style="color: red;">*</label></td>
				<td class="ji"><input type="text" name="indexs" id="indexs" size="4" maxlength="10" class="easyui-numberbox input">降序排列</td>
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
	<div id="search_dialog">
		<table width="99.5%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt" colspan="4">&nbsp;</td>
			</tr>
			<tr class="table_title">
				<td class="rt">英雄名称：</td>
				<td class="ji"><input type="text" name="names" id="names"></td>
				<td class="rt">英雄类型：</td>
				<td class="ji">
					<select id="type1" name="type1" class="easyui-combobox" style="width:200px;">
						<option value="-1"></option>
						<option value="1">输出</option>
					    <option value="2">支援</option>
					    <option value="3">爆发</option>
					    <option value="4">控制</option>
					    <option value="5">先锋</option>
					</select>
				</td>
			</tr>
			<tr class="ji">
				<td class="rt" colspan="4">&nbsp;</td>
			</tr>
		</table>
	</div>
</body>
</html>
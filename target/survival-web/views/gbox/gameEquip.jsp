<%@page import="com.baofeng.carebay.util.Constants"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>装备资料</title>
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
		$("#equip_grid").datagrid({
			url : "${pageContext.request.contextPath}/equip/readPages.do",
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
				text : "添加装备资料",
				iconCls : 'icon-add',
				handler : function() {
					$("#div_dialog").dialog({
						title:"添加装备资料",
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
					var row = $("#equip_grid").datagrid("getSelections");
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
		            var editurl = "${pageContext.request.contextPath}/equip/edit.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#id").val(data.id);
				          	$("#name").val(data.name);
				          	$("#type").combobox("setValue",data.type);
				          	$("#price").numberbox("setValue",data.price);
				          	$("#inputs").val(data.inputs);
				          	$("#outputs").val(data.outputs);
				          	$("#attributes").val(data.attributes);
				          	$("#indexs").numberbox("setValue",data.indexs);
				          	$("#div_dialog").dialog();
				          },
						  error : function(data){
						  	$("#equip_grid").datagrid("reload"); 
						  }
					});
				}
			},'-', {
				id : 'btnRedo',
				text : "上  | 下线",
				iconCls : 'icon-redo',
				handler : function() {
					var row = $("#equip_grid").datagrid("getSelections");
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
		            var editurl = "${pageContext.request.contextPath}/equip/online.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+ids,
				          success: function(data){
				          	if("success" == data.resultMessage){
					          	$("#equip_grid").datagrid("reload"); 
				          	}
				          },
						  error : function(data){
						  	$("#equip_grid").datagrid("reload"); 
						  }
					});
				}
			},'-', {
				id : 'btnDel',
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#equip_grid").datagrid("getSelections");
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
		            var delUrl = "${pageContext.request.contextPath}/equip/delete.do?t="+new Date().getTime();
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
										$("#equip_grid").datagrid("reload"); 
								    }
								},
								error : function(data){
									$("#equip_grid").datagrid("reload"); 
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
   								$("#equip_grid").datagrid("load",{name : names,type : type});
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
				title : "装备图片",
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
				title : "装备名称",
				width : $(this).width() * 0.2,
				align : "left",
				sortable : true
			},{
				field : "type",
				title : "装备类型",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter:function(value,rec){
					var htmls = "";
					if(value == "1"){
						htmls = "消耗品";
					}else if(value == "2"){
						htmls = "移动速度";
					}else if(value == "3"){
						htmls = "加强";
					}else if(value == "4"){
						htmls = "攻击";
					}else if(value == "5"){
						htmls = "防御";
					}else if(value == "6"){
						htmls = "魔法";
					}
					return htmls;
				}
			},{
				field : "price",
				title : "装备价格",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "inputs",
				title : "合成需求",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					var htmls = "";
					if(value != null && value != ""){
						var inputs = value.split(",");
						for(var i=0;i<inputs.length;i++){
							htmls += "<img src='"+inputs[i]+"' width='28' height='28'/>";
						}
					}
					return htmls;
				}
			},{
				field : "outputs",
				title : "可合成",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					var htmls = "";
					if(value != null && value != ""){
						var inputs = value.split(",");
						for(var i=0;i<inputs.length;i++){
							htmls += "<img src='"+inputs[i]+"' width='28' height='28'/>";
						}
					}
					
					return htmls;
				}
			},{
				field : "attributes",
				title : "物品属性",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
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
		$("#equipForm").submit(function(){
			var type = $("#type").combobox("getValue"); 
			var name = $("#name").val(); 
			var price=$("#price").numberbox("getValue"); 
			var indexs=$("#indexs").numberbox("getValue"); 
			if($.trim(type) == ""){
				alert("英雄类型必须选择！"); 
				$("#type").focus(); 
				return false; 
			}
			if($.trim(name) == ""){
				alert("装备名称不能为空！"); 
				$("#name").focus(); 
				return false; 
			}
			if($.trim(price) == ""){
				alert("装备价格不能为空！"); 
				$("#price").focus(); 
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
	<table id="equip_grid" title="装备资料"></table>
	<div id="delete_div">确定删除所选内容吗？</div> 
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/equip/save.do" method="post" id="equipForm" enctype="multipart/form-data">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt">装备类型：<label style="color: red;">*</label></td>
				<td class="ji">
					<select id="type" name="type" class="easyui-combobox" style="width:200px;">
					    <option value="1">消耗品</option>
					    <option value="2">移动速度</option>
					    <option value="3">加强</option>
					    <option value="4">攻击</option>
					    <option value="5">防御</option>
					    <option value="6">魔法</option>
					</select>
				</td>
			</tr>
			<tr class="ji">
				<td class="rt">装备名称：<label style="color: red;">*</label></td>
				<td class="ji"><input type="text" name="name" id="name" style="width:200px;" maxlength="200" class="input"></td>
			</tr>
			<tr class="tr">
				<td class="rt">装备价格：<label style="color: red;">*</label></td>
				<td class="ji"><input type="text" name="price" id="price" maxlength="10" class="easyui-numberbox input"></td>
			</tr>
			<tr class="tr">
				<td class="rt">装备图片：</td>
				<td class="ji"><input type="file" name="images" id="images" size="25">修改时不选择,<span class="p-size">&nbsp;图片尺寸：108*108</span></td>
			</tr>
			<tr class="ji">
				<td class="rt">合成需求：</td>
				<td class="ji"><textarea id="inputs" name="inputs" style="height:40px;width:450px;"></textarea>多个装备请用英文逗号分隔","</td>
			</tr>
			<tr class="tr">
				<td class="rt">可合成：</td>
				<td class="ji"><textarea id="outputs" name="outputs" style="height:40px;width:450px;"></textarea>多个装备请用英文逗号分隔","</td>
			</tr>
			<tr class="ji">
				<td class="rt">物品属性：</td>
				<td class="ji"><textarea id="attributes" name="attributes" style="height:40px;width:450px;"></textarea></td>
			</tr>	
			<tr class="tr">
				<td class="rt">显示顺序：</td>
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
				<td class="rt">装备名称：</td>
				<td class="ji"><input type="text" name="names" id="names"></td>
				<td class="rt">装备类型：</td>
				<td class="ji">
					<select id="type1" name="type1" class="easyui-combobox" style="width:200px;">
						<option value="-1"></option>
						<option value="1">消耗品</option>
						<option value="2">移动速度</option>
						<option value="3">加强</option>
						<option value="4">攻击</option>
						<option value="5">防御</option>
						<option value="6">魔法</option>
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
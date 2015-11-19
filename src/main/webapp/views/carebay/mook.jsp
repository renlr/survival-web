<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>内容精选</title>
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
		$("#mook_grid").datagrid({
			url : "${pageContext.request.contextPath}/mook/readPages.do",
			datatype : "json",
			mtype : "POST",
			nowrap : true,
			striped : true,
			fitColumns : true, 
			fit : true,
			pageSize: 30,
			pageList: [30,40,50],
			pagination : true,
			sortname : 'id',
			singleSelect : true,
			rownumbers : true,
			toolbar : [ '-', {
				id : 'btnClearSelections',
				text : "添加内容",
				iconCls : 'icon-add',
				handler : function() {
					$("#div_dialog").dialog({
						title:"添加内容",
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
					var row = $("#mook_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
					var online = row[0].online;
					if(online == 1){
						alert("上线中不允许编辑!");
		                return;
					}					
		            var editurl = "${pageContext.request.contextPath}/mook/edit.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#id").val(data.id);
				          	$("#content").val(data.name);
				          	$("#type").combobox("setValue",data.type);
							$("#indexs").numberbox("setValue",data.indexs);								          
				          	$("#div_dialog").dialog();
				          },
						  error : function(data){
						  	$("#mook_grid").datagrid('reload'); 
						  }
					});
				}
			}, '-', {
				id : 'btnRedo',
				text : "上  | 下线",
				iconCls : 'icon-redo',
				handler : function() {
					var row = $("#mook_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/mook/online.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	if("success" == data.resultMessage){
					          	$("#mook_grid").datagrid("reload"); 
				          	}
				          },
						  error : function(data){
						  	$("#mook_grid").datagrid("reload"); 
						  }
					});
				}
			},'-', {
				id : 'btnEdit',
				text : "显示顺序",
				iconCls : 'icon-edit',
				handler : function() {
					var row = $("#mook_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/mook/edit.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#id1").val(data.id);
				          	$("#indexs1").val(data.indexs);
				          	$("#index_dialog").dialog();
				          },
						  error : function(data){
						  	alert("修改显示顺序失败!");
						  }
					});
				}
			}, '-', {
				id : 'btnDel',
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#mook_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var ids = row[0].id;
		            var online = row[0].online;
					if(online == 1){
						alert("上线中不允许删除!");
		                return;
					}
		            var delUrl = "${pageContext.request.contextPath}/mook/delete.do?t="+new Date().getTime();
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
							          		$("#mook_grid").datagrid("reload"); 
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
				field : "image",
				title : "内容图片",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return "<img src='"+value+"' width='200' height='100'/>";
				}
			},{
				field : "type",
				title : "跳转类型",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter:function(value,rec){
					var htmls = "";
					if(value == 0){
						htmls = "无须跳转";	
					}else if(value == 1){
						htmls = "母婴乐购";
					}else if(value == 2){
						htmls = "高级定制";
					}else if(value == 3){
						htmls = "服务精选";
					}else if(value == 4){
						htmls = "月子套餐";
					}else if(value == 5){
						htmls = "最新活动";
					}					
					return htmls;
				}
			},{
				field : "paramsName",
				title : "跳转名称",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "name",
				title : "介绍内容",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "chamber",
				title : "所在会所",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter:function(value,rec){
					return rec.imageSha1;
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
		$("#type").combobox({
			onSelect:function(data){
				if(data.value == 0){
					$("#puds").hide();
					$("#cst").hide();
					$("#sev").hide();
					$("#mea").hide();
					$("#act").hide();
				}else if(data.value == 1){
					$("#puds").show();
					$("#cst").hide();
					$("#sev").hide();
					$("#mea").hide();
					$("#act").hide();
				}else if(data.value == 2){
					$("#puds").hide();
					$("#cst").show();
					$("#sev").hide();
					$("#mea").hide();
					$("#act").hide();
				}else if(data.value == 3){
					$("#puds").hide();
					$("#cst").hide();
					$("#sev").show();
					$("#mea").hide();
					$("#act").hide();
				}else if(data.value == 4){
					$("#puds").hide();
					$("#cst").hide();
					$("#sev").hide();
					$("#mea").show();
					$("#act").hide();
				}else if(data.value == 5){
					$("#puds").hide();
					$("#cst").hide();
					$("#sev").hide();
					$("#mea").hide();
					$("#act").show();
				}
			}
		});
		$("#products").combogrid({
			fit : true,
			delay:500,
			mode:"remote",
			datatype : "json",
			fitColumns: true,
			rownumbers : true,
			panelWidth: 500,
			mtype : "POST",
			url:"${pageContext.request.contextPath}/products/readPagesSkip.do",
			idField:"id",
			textField:"name",
			columns:[[
				{field:"name",title:"商品名称",width:120,sortable:true}
			]],
			pagination : true,
			pageSize: 5,
			pageList: [5,10,20,30,40,50]
		});
		$("#custom").combogrid({
			fit : true,
			delay:500,
			mode:"remote",
			datatype : "json",
			fitColumns: true,
			rownumbers : true,
			panelWidth: 500,
			mtype : "POST",
			url:"${pageContext.request.contextPath}/custom/readPagesSkip.do",
			idField:"id",
			textField:"name",
			columns:[[
				{field:"name",title:"定制名称",width:120,sortable:true}
			]],
			pagination : true,
			pageSize: 5,
			pageList: [5,10,20,30,40,50]
		});
		$("#services").combogrid({
			fit : true,
			delay:500,
			mode:"remote",
			datatype : "json",
			fitColumns: true,
			rownumbers : true,
			panelWidth: 500,
			mtype : "POST",
			url:"${pageContext.request.contextPath}/curse/readPagesSkip.do",
			idField:"id",
			textField:"name",
			columns:[[
				{field:"name",title:"服务名称",width:120,sortable:true}
			]],
			pagination : true,
			pageSize: 5,
			pageList: [5,10,20,30,40,50]
		});
		$("#mealId").combogrid({
			fit : true,
			delay:500,
			mode:"remote",
			datatype : "json",
			fitColumns: true,
			rownumbers : true,
			panelWidth: 500,
			mtype : "POST",
			url:"${pageContext.request.contextPath}/meal/readPages.do",
			idField:"id",
			textField:"name",
			columns:[[
				{field:"name",title:"套餐名称",width:120,sortable:true}
			]],
			pagination : true,
			pageSize: 5,
			pageList: [5,10,20,30,40,50]
		});
		$("#activity").combogrid({
			fit : true,
			delay:500,
			mode:"remote",
			datatype : "json",
			fitColumns: true,
			rownumbers : true,
			panelWidth: 500,
			mtype : "POST",
			url:"${pageContext.request.contextPath}/activity/readPages.do",
			idField:"id",
			textField:"name",
			columns:[[
				{field:"name",title:"活动名称",width:120,sortable:true}
			]],
			pagination : true,
			pageSize: 5,
			pageList: [5,10,20,30,40,50]
		});
		
		$("#mookForm").submit(function(){
			var id = $("#id").val();
			var type = $("#type").combobox("getValue");
			var products = $("#products").combobox("getValue");
			var custom = $("#custom").combobox("getValue");
			var services = $("#services").combobox("getValue");
			var mealId = $("#mealId").combobox("getValue");
			var activity = $("#activity").combobox("getValue");
			var content=$("#content").val(); 
			var indexs=$("#indexs").numberbox("getValue");
			var images = $("#images").val();
			if(images != null && images != ""){
				if(!(images.endWith(".jpg") || images.endWith(".png"))){
					alert("目前只能上传扩展名为：.jpg、.png的图片文件!"); 
					$("#images").focus(); 
					return false;
				}
			}
			if($.trim(type) == ""){ 
				alert("跳转类型必须选择！"); 
				$("#type").focus(); 
				return false; 
			}
			if(type == 1){
				if($.trim(products) == ""){ 
					alert("跳转类型必须选择！"); 
					$("#products").focus(); 
					return false; 
				}
			}else if(type == 2){
				if($.trim(custom) == ""){ 
					alert("定制内容必须选择！"); 
					$("#custom").focus(); 
					return false; 
				}
			}else if(type == 3){
				if($.trim(services) == ""){ 
					alert("服务内容必须选择！"); 
					$("#services").focus(); 
					return false; 
				}
			}else if(type == 4){
				if($.trim(mealId) == ""){ 
					alert("套餐内容必须选择！"); 
					$("#mealId").focus(); 
					return false; 
				}
			}else if(type == 5){
				if($.trim(activity) == ""){ 
					alert("最新活动必须选择！"); 
					$("#activity").focus(); 
					return false; 
				}
			}
			if($.trim(content) == ""){ 
				alert("介绍内容不能为空！"); 
				$("#content").focus(); 
				return false; 
			}
			if($.trim(indexs) == ""){ 
				alert("显示顺序不能为空！"); 
				$("#indexs").focus(); 
				return false; 
			}
		});
		$("#butClose").click(function(){
			$("#id").val("");
			$("#images").val("");
			$("#indexs").numberbox("setValue","");
			$("#div_dialog").dialog("close");
		});
		$("#indexsForm").submit(function(){
			var indexs=$("#indexs1").val();
			if($.trim(indexs) == ""){ 
				alert("显示顺序必须填写！"); 
				$("#indexs1").focus(); 
				return false; 
			}		
		});
		$("#butrs1").click(function(){
			$("#id1").val("");
			$("#indexs1").val("");
			$("#index_dialog").dialog("close");
		});
	});
</script>
</head>
<body>
	<table id="mook_grid" title="内容精选"></table>
	<div id="delete_div">确定删除所选内容?</div> 
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/mook/save.do" method="post" id="mookForm" enctype="multipart/form-data">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt" align="right">精选图片：</td>
				<td><input type="file" name="images" id="images" size="25">修改时不选择,<span class="p-size">&nbsp;图片尺寸：1608*1114</span></td>
			</tr>
			<tr class="ji">
				<td class="rt" align="right">跳转类型：<label style="color: red;">*</label></td>
				<td>
					<select id="type" class="easyui-combobox" name="type" style="width:140px;">
						<option value="0">无须跳转</option>
						<option value="1">母婴乐购</option>
						<option value="2">高级定制</option>
						<option value="3">服务精选</option>
						<option value="4">月子套餐</option>
						<%--<option value="5">最新活动</option>--%>
					</select>
			    </td>
			</tr>
			<tr class="tr" id="puds" style="display:none;">
				<td class="rt" align="right">母婴乐购：<label style="color: red;">*</label></td>
				<td><input id="products" name="products"></td>
			</tr>
			<tr class="tr" id="cst" style="display:none;">
				<td class="rt" align="right">高级定制：<label style="color: red;">*</label></td>
				<td><input id="custom" name="custom"></td>
			</tr>
			<tr class="tr" id="sev" style="display:none;">
				<td class="rt" align="right">服务精选：<label style="color: red;">*</label></td>
				<td><input id="services" name="services"></td>
			</tr>
			<tr class="tr" id="mea" style="display:none;">
				<td class="rt" align="right">月子套餐：<label style="color: red;">*</label></td>
				<td><input id="mealId" name="mealId"></td>
			</tr>
			<tr class="tr" id="act" style="display:none;">
				<td class="rt" align="right">最新活动：<label style="color: red;">*</label></td>
				<td><input id="activity" name="activity"></td>
			</tr>
			<tr class="ji">
				<td class="rt" align="top">介绍内容：<label style="color: red;">*</label></td>
				<td class="ji" colspan="3"><textarea id="content" name="content" style="height:40px;width:450px;"></textarea></td>
			</tr>
			<tr class="tr">
				<td class="rt" align="right">显示顺序：<label style="color: red;">*</label></td>
				<td><input type="text" name="indexs" id="indexs" size="5" class="easyui-numberbox" data-options="min:0,precision:0">降序排列</td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id" id="id"> 
					<input class="bginput" type="submit" id="butSubmit" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butClose" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
	<div id="index_dialog" title="修改显示顺序">
		<form action="${pageContext.request.contextPath}/mook/saveIndexs.do" method="post" id="indexsForm">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt" align="right">显示顺序：<label style="color: red;">*</label></td>
				<td><input type="text" name="indexs1" id="indexs1" size="4" maxlength="10" class="easyui-numberbox input">降序排列</td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id1" id="id1">
					<input class="bginput" type="submit" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butrs1" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>
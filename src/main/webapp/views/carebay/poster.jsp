<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>首页轮播</title>
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
		var pageSize = Math.round(($("#right", window.parent.document).height() - 160) / 105);
		$("#poster_grid").datagrid({
			url : "${pageContext.request.contextPath}/poster/readPages.do?gid=${week.id}",
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
				text : "添加轮播图片",
				iconCls : 'icon-add',
				handler : function() {
					$("#div_dialog").dialog({
						title:"添加轮播图片",
						show:"slide",
						resizable:false,
						closed: false,
						resizable:false
					});
				}
			}, '-', {
				id : 'btnDel',
				text : "显示顺序",
				iconCls : 'icon-edit',
				handler : function() {
					var row = $("#poster_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/poster/edit.do?t="+new Date().getTime();
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
						  	$("#poster_grid").datagrid("reload"); 
						  }
					});
				}
			},'-', {
				id : 'btnRedo',
				text : "上  | 下线",
				iconCls : 'icon-redo',
				handler : function() {
					var row = $("#poster_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/poster/online.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#poster_grid").datagrid('reload'); 
				          },
						  error : function(data){
						  	$("#poster_grid").datagrid('reload'); 
						  }
					});
				}
			}, '-', {
				id : 'btnDel',
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#poster_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var ids = row[0].id;
		            var delUrl = "${pageContext.request.contextPath}/poster/delete.do?t="+new Date().getTime();
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
							          		$("#poster_grid").datagrid("reload"); 
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
			columns : [ [ {
				field : "image",
				title : "图片文件",
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
				formatter : function(value,rec){
					if(null != value){
						if(1 == value){
							return "商品分类";
						}else if(2 == value){
							return "单个商品";
						}else if(3 == value){
							return "功能模块";
						}else if(4 == value){
							return "官方活动";
						}
					}
					return "";
				}
			},{
				field : "pramsName",
				title : "跳转名称",
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
					return "	";
				}
			},{
				field : "indexs",
				title : "显示顺序",
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
		$($("#poster_grid").datagrid("getPager")).pagination({
			beforePageText : '第',
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		});
		$("#butrs").click(function(){
			$("#id").val("");
			$("#images").val("");
			$("#indexs").val("");
			$("#type").combobox("select",1);
			$("#div_dialog").dialog("close");
		});
		
		$("#gid").val("${week.id}");
		$("#gid1").val("${week.id}");
		$("#posterForm").submit(function(){
			var id = $("#id").val();
			var images=$("#images").val(); 
			var indexs=$("#indexs").val(); 
			var type = $("#type").combobox("getValue");
			var ductCat = $("#ductCat").combobox("getValue");
			var products = $("#products").combobox("getValue");
			var funModule = $("#funModule").combobox("getValue");
			var activity = $("#activity").combobox("getValue");
			if(id == "" && $.trim(images) == ""){ 
				alert("请选择上传图片文件！"); 
				$("#images").focus(); 
				return false; 
			}
			if(type == ""){
				alert("请选择跳转类型!");
				$("#type").focus(); 
				return false;
			}
			if(type == 1){
				if(ductCat == ""){
					alert("请选择跳转的商品分类!"); 
					$("#cat").show();
					$("#duct").hide();
					$("#serv").hide();
					$("#avt").hide();
					$("#ductCat").focus(); 
					return false;
				}
			}else if(type == 2){
				if(products == ""){
					alert("请选择跳转的单个商品!"); 
					$("#cat").hide();
					$("#duct").show();
					$("#serv").hide();
					$("#avt").hide();
					$("#products").focus(); 
					return false;
				}
			}else if(type == 3){
				if(funModule == ""){
					alert("请选择跳转的功能模块!"); 
					$("#cat").hide();
					$("#duct").hide();
					$("#serv").show();
					$("#avt").hide();
					$("#funModule").focus(); 
					return false;
				}
			}else if(type == 4){
				if(activity == ""){
					alert("请选择跳转的官方活动!"); 
					$("#cat").hide();
					$("#duct").hide();
					$("#serv").hide();
					$("#avt").show();
					$("#activity").focus(); 
					return false;
				}
			}
			if($.trim(indexs) == ""){ 
				alert("显示顺序必须填写！"); 
				$("#indexs").focus(); 
				return false; 
			}
		});
		$("#type").combobox({
			onSelect:function(data){
				if(data.value == 1){
					$("#cat").show();
					$("#duct").hide();
					$("#serv").hide();
					$("#avt").hide();
				}else if(data.value == 2){
					$("#cat").hide();
					$("#duct").show();
					$("#serv").hide();
					$("#avt").hide();
				}else if(data.value == 3){
					$("#cat").hide();
					$("#duct").hide();
					$("#serv").show();
					$("#avt").hide();
				}else if(data.value == 4){
					$("#cat").hide();
					$("#duct").hide();
					$("#serv").hide();
					$("#avt").show();
				}
			}
		});
		
		$("#ductCat").combogrid({
			fit : true,
			delay:500,
			mode:"remote",
			datatype : "json",
			fitColumns: true,
			rownumbers : true,
			panelWidth: 500,
			mtype : "POST",
			url:"${pageContext.request.contextPath}/ductcy/readPages.do",
			idField:"id",
			textField:"name",
			columns:[[
				{field:"name",title:"分类名称",width:120,sortable:true}
			]],
			pagination : true,
			pageSize: 5,
			pageList: [5,10,20,30,40,50]
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
			url:"${pageContext.request.contextPath}/products/readPagesOnline.do",
			idField:"id",
			textField:"name",
			columns:[[
				{field:"name",title:"商品名称",width:120,sortable:true}
			]],
			pagination : true,
			pageSize: 5,
			pageList: [5,10,20,30,40,50]
		});
		
		$("#funModule").combogrid({
			fit : true,
			delay:500,
			mode:"remote",
			datatype : "json",
			fitColumns: true,
			rownumbers : true,
			panelWidth: 500,
			mtype : "POST",
			url:"${pageContext.request.contextPath}/fmod/readPages.do",
			idField:"id",
			textField:"name",
			columns:[[
				{field:"name",title:"模块名称",width:120,sortable:true}
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
			url:"${pageContext.request.contextPath}/fmod/readPages.do",
			idField:"id",
			textField:"name",
			columns:[[
				{field:"name",title:"活加名称",width:120,sortable:true}
			]],
			pagination : true,
			pageSize: 5,
			pageList: [5,10,20,30,40,50]
		});
		
		$("#butrs1").click(function(){
			$("#id1").val("");
			$("#indexs1").val("");
			$("#index_dialog").dialog("close");
		});
		$("#editIndexsForm").submit(function(){
			var indexs=$("#indexs1").val();
			if($.trim(indexs) == ""){ 
				alert("显示顺序必须填写！"); 
				$("#indexs1").focus(); 
				return false; 
			}		
		});
	});
</script>
</head>
<body>
	<table id="poster_grid" title="添加第${carousel.name}周轮播图片"></table>
	<div id="delete_div">确定删除所选内容?</div> 
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/poster/save.do" method="post" id="posterForm" enctype="multipart/form-data">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="tr">
				<td class="rt" align="right">轮播图片：<label style="color: red;">*</label></td>
				<td><input type="file" name="images" id="images" size="25"></td>
			</tr>
			<tr class="ji">
				<td class="rt" align="right">跳转类型：<label style="color: red;">*</label></td>
				<td>
					<select id="type" class="easyui-combobox" name="type" style="width:140px;">
						<option value="1">商品分类</option>
						<option value="2">单个商品</option>
						<option value="3">功能模块</option>
						<option value="4">官方活动</option>
					</select>
			    </td>
			</tr>
			<tr class="tr" id="cat" style="display:black;">
				<td class="rt" align="right">商品分类：<label style="color: red;">*</label></td>
				<td><input id="ductCat" name="ductCat"></td>
			</tr>
			<tr class="tr" id="duct" style="display:none;">
				<td class="rt" align="right">单个商品：<label style="color: red;">*</label></td>
				<td><input id="products" name="products"></td>
			</tr>
			<tr class="tr" id="serv" style="display:none;">
				<td class="rt" align="right">功能模块：<label style="color: red;">*</label></td>
				<td><input id="funModule" name="funModule"></td>
			</tr>	
			<tr class="tr" id="avt" style="display:none;">
				<td class="rt" align="right">官方活动：<label style="color: red;">*</label></td>
				<td><input id="activity" name="activity"></td>
			</tr>	
			<tr class="ji">
				<td class="rt" align="right">显示顺序：<label style="color: red;">*</label></td>
				<td><input type="text" name="indexs" id="indexs" size="4" maxlength="10" class="easyui-numberbox input">降序排列</td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id" id="id">
					<input type="hidden" name="gid" id="gid">
					<input class="bginput" type="submit" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butrs" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
	<div id="index_dialog" title="修改显示顺序">
		<form action="${pageContext.request.contextPath}/poster/saveIndexs.do" method="post" id="editIndexsForm">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt" align="right">显示顺序：<label style="color: red;">*</label></td>
				<td><input type="text" name="indexs1" id="indexs1" size="4" maxlength="10" class="easyui-numberbox input">降序排列</td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id1" id="id1">
					<input type="hidden" name="gid1" id="gid1">
					<input class="bginput" type="submit" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butrs1" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div> 
</body>
</html>
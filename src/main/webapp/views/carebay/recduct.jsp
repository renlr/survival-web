<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>首页推荐商品</title>
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
		var pageSize = Math.round(($("#right", window.parent.document).height() - 160) / 126);
		$("#recduct_grid").datagrid({
			url : "${pageContext.request.contextPath}/recduct/readPages.do?t="+new Date().getTime(),
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
				text : "推荐商品",
				iconCls : 'icon-add',
				handler : function() {
					$("#div_dialog").dialog({
						title:"推荐商品",
						show:"slide",
						resizable:false,
						closed: false,
						resizable:false
					});
				}
			},'-', {
				id : 'btnEdit',
				text : "显示顺序",
				iconCls : 'icon-edit',
				handler : function() {
					var row = $("#recduct_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/recduct/edit.do?t="+new Date().getTime();
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
						  	alert("修改推荐商品失败!");
						  }
					});
				}
			}, '-', {
				id : 'btnRedo',
				text : "上  | 下线",
				iconCls : 'icon-redo',
				handler : function() {
					var row = $("#recduct_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/recduct/online.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#recduct_grid").datagrid('reload'); 
				          },
						  error : function(data){
						  	$("#recduct_grid").datagrid('reload'); 
						  }
					});
				}
			}, '-', {
				id : 'btnDel',
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#recduct_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var ids = row[0].id;
		            var delUrl = "${pageContext.request.contextPath}/recduct/delete.do?t="+new Date().getTime();
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
							          		$("#recduct_grid").datagrid("reload"); 
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
				title : "展示首图",
				width : 670,
				align : "left",
				sortable : true,
				formatter : function(value,rec){
					if(value != null){
						var regexp = new RegExp("imageId","g");
						var online = new RegExp("online","g");
						if(rec.model == 0){
							if(value != null && value != ""){
								var ims = value.split(",");									
								style1Html  = style1Html.replace("[[images"+ims[0]+"]]",ims[1]);
							}
							return style1Html.replace("imageId", rec.id).replace("online", rec.online);
						}else if(rec.model == 2){
							var images = value.split("|");	
							for(var i=0;i<images.length;i++){
								var image = images[i];
								if(image != null && image != ""){
									var ims = image.split(",");									
									style2Html  = style2Html.replace("[[images"+ims[0]+"]]",ims[1]);
								}
							}
							return style2Html.replace(regexp, rec.id).replace(online, rec.online);
						}else if(rec.model == 3){
							var images = value.split("|");	
							for(var i=0;i<images.length;i++){
								var image = images[i];
								if(image != null && image != ""){
									var ims = image.split(",");									
									style3Html  = style3Html.replace("[[images"+ims[0]+"]]",ims[1]);
								}
							}
							return style3Html.replace(regexp, rec.id).replace(online, rec.online);
						}
					}
					return "";
				}
			},{
				field : "model",
				title : "展示样式",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					if(value != null){
						if(value == 0){
							return "单张图片样式";
						}else if(value == 1){
							return "多图片样式";
						}else if(value == 2){
							return "对半分样式";
						}else if(value == 3){
							return "不对分样式";
						}
					}
					return "";
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
					return "	";
				}
			},{
				field : "picture",
				title : "展示图片",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return '<a href="${pageContext.request.contextPath}/recduct/details.do?id='+rec.id+'">图片管理</a>';
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
			}]]
		});
		$($("#recduct_grid").datagrid("getPager")).pagination({
			beforePageText : "第",
			afterPageText : "页    共 {pages} 页",
			displayMsg : "当前显示 {from} - {to} 条记录   共 {total} 条记录"
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
				{field:"name",title:"列表名称",width:120,sortable:true}
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
				{field:"name",title:"产品名称",width:120,sortable:true}
			]],
			pagination : true,
			pageSize: 5,
			pageList: [5,10,20,30,40,50]
		});
		$("#type").combobox({
			onSelect:function(data){
				if(data.value == 1){
					$("#duct").show();
					$("#prods").hide();
				}else if(data.value == 2){
					$("#duct").hide();
					$("#prods").show();
				}
			}
		});
		$("#butrs").click(function(){
			$("#id").val("");
			$("#indexs").val("");
			$("#images").val("");
			$("#div_dialog").dialog("close");
		});
		$("#recductForm").submit(function(){
			var indexs=$("#indexs").val(); 
			var images=$("#images").val(); 
			var model = $("#model").combobox("getValue");
			var type = $("#type").combobox("getValue");
			var ductCat = $("#ductCat").combobox("getValue");
			var products = $("#products").combobox("getValue");
			if($.trim(model) == ""){ 
				alert("请选择展示样式！"); 
				$("#model").focus(); 
				return false; 
			}
			if($.trim(images) == ""){ 
				alert("必须上传首张图片！"); 
				$("#images").focus(); 
				return false; 
			}
			if(type == 1){
				if($.trim(ductCat) == ""){ 
					alert("请选择推荐列表！"); 
					$("#duct").show();
					$("#prods").hide();
					$("#ductCat").focus(); 
					return false; 
				}
			}else{
				if(products == ""){
					alert("请选择跳转的单个商品!"); 
					$("#duct").hide();
					$("#prods").show();
					$("#products").focus(); 
					return false;
				}
			}
			if($.trim(indexs) == ""){ 
				alert("显示顺序必须填写！"); 
				$("#indexs").focus(); 
				return false; 
			}
		});
		$("#butrs1").click(function(){
			$("#id1").val("");
			$("#indexs1").val("");
			$("#index_dialog").dialog("close");
		});
		$("#indexsForm").submit(function(){
			var indexs=$("#indexs1").val();
			if($.trim(indexs) == ""){ 
				alert("显示顺序必须填写！"); 
				$("#indexs1").focus(); 
				return false; 
			}		
		});
		//-----------------------------------------------------------
		$("#butrs2").click(function(){
			$("#id2").val("");
			$("#images1").val("");
			$("#upload_dialog").dialog("close");
		});
		$("#upbuttons").click(function(){
			$("#uploadForm").form("submit",{
				onSubmit:function(){
					var images2 = $("#images2").val(); 
					if($.trim(images2) == ""){ 
						alert("必须选择上传图片！"); 
						$("#images2").focus(); 
						return false; 
					}
				},
				success:function(data){
					$("#id2").val("");
					$("#images2").val("");
					$("#upload_dialog").dialog("close");
					$("#recduct_grid").datagrid("reload"); 
				}
			});
		});
		//----------------------------------------------------------------------
		$("#ductCat3").combogrid({
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
				{field:"name",title:"列表名称",width:120,sortable:true}
			]],
			pagination : true,
			pageSize: 5,
			pageList: [5,10,20,30,40,50]
		});
		$("#products3").combogrid({
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
				{field:"name",title:"产品名称",width:120,sortable:true}
			]],
			pagination : true,
			pageSize: 5,
			pageList: [5,10,20,30,40,50]
		});
		$("#type3").combobox({
			onSelect:function(data){
				if(data.value == 1){
					$("#duct3").show();
					$("#prods3").hide();
				}else if(data.value == 2){
					$("#duct3").hide();
					$("#prods3").show();
				}
			}
		});
		$("#butrs3").click(function(){
			$("#id3").val("");
			$("#images3").val("");
			$("#upload3_dialog").dialog("close");
		});
		$("#upbuttons3").click(function(){
			$("#uploadForm3").form("submit",{
				onSubmit:function(){
					var type3 = $("#type3").combobox("getValue");
					var ductCat3 = $("#ductCat3").combobox("getValue");
					var products3 = $("#products3").combobox("getValue");	
					if(type3 == 1){
						if($.trim(ductCat3) == ""){ 
							alert("请选择推荐列表！"); 
							$("#duct3").show();
							$("#prods3").hide();
							$("#ductCat3").focus(); 
							return false; 
						}
					}else{
						if(products3 == ""){
							alert("请选择跳转的单个商品!"); 
							$("#duct3").hide();
							$("#prods3").show();
							$("#products3").focus(); 
							return false;
						}
					}
				},
				success:function(data){
					$("#id2").val("");
					$("#images2").val("");
					$("#upload3_dialog").dialog("close");
					$("#recduct_grid").datagrid("refresh"); 
				}
			});
		});
	});
	
	function styleImage(vas,id,online){
		$("#id3").val(id);
		$("#model3").val(vas);
		if(online == 0){
			$("#upload3_dialog").dialog({
				title:"上传图片",
				show:"slide",
				resizable:false,
				closed: false,
				resizable:false
			});
		}
	}
	
	var style1Html = "<table id=\"__01\" width=\"320\" height=\"148\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
		+"<tr>"
		+"<td rowspan=\"3\"><a onClick=\"styleImage(1,'imageId','online')\" href=\"#\"><img src=\"[[images1]]\" width=\"296\" height=\"148\"/></a></td>"
		+"</tr>"
		+"</table>";
			
	var style2Html = "<table id=\"__01\" width=\"320\" height=\"148\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
		+"<tr>"
		+"<td rowspan=\"3\"><a onClick=\"styleImage(1,'imageId','online')\" href=\"#\"><img src=\"[[images1]]\" width=\"148\" height=\"148\" border=\"0\"></a></td>"
		+"<td colspan=\"3\"><a onClick=\"styleImage(2,'imageId','online')\" href=\"#\"><img src=\"[[images2]]\" width=\"144\" height=\"72\" border=\"0\"></a></td>"
		+"</tr>"
		+"<tr>"
		+"<td colspan=\"3\"><a onClick=\"styleImage(3,'imageId','online')\" href=\"#\"><img src=\"[[images3]]\" width=\"144\" height=\"72\" border=\"0\"></a></td>"
		+"</tr>"
		+"</table>";

	var style3Html = "<table id=\"__01\" width=\"320\" height=\"148\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
		+"<tr>"
		+"<td rowspan=\"3\"><a onClick=\"styleImage(1,'imageId','online')\" href=\"#\"><img src=\"[[images1]]\" width=\"196\" height=\"196\" border=\"0\"></a></td>"
		+"<td><a onClick=\"styleImage(2,'imageId','online')\" href=\"#\"><img src=\"[[images2]]\" width=\"96\" height=\"96\" border=\"0\"></a></td>"
		+"</tr>"
		+"<tr>"
		+"<td><a onClick=\"styleImage(3,'imageId','online')\" href=\"#\"><img src=\"[[images3]]\" width=\"96\" height=\"96\" border=\"0\"></a></td>"
		+"</tr>"
		+"</table>";
	
</script>
</head>
<body>
	<table id="recduct_grid" title="首页推荐商品"></table>
	<div id="delete_div">确定删除所选内容?</div> 
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/recduct/save.do" method="post" id="recductForm" enctype="multipart/form-data">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="tr">
				<td class="rt" align="right">展示样式：<label style="color: red;">*</label></td>
				<td>
					<select id="cc" class="easyui-combobox" id="model" name="model" style="width:200px;">
						<option value="0">单张图片样式(1张大图)</option>
						<option value="2">对半分样式(左边大图、右边2小图)</option>
						<option value="3">不对分样式(左边大图、右边2小图)</option>
					</select>
				</td>
			</tr>
			<tr class="tr">
				<td class="rt" align="right">展示首图：<label style="color: red;">*</label></td>
				<td><input type="file" name="images" id="images" size="25"></td>
			</tr>
			<tr class="ji">
				<td class="rt" align="right">跳转类型：<label style="color: red;">*</label></td>
				<td>
					<select id="type" class="easyui-combobox" name="type" style="width:140px;">
						<option value="1">推荐列表</option>
						<option value="2">单个商品</option>
					</select>
			    </td>
			</tr>
			<tr class="tr" id="duct" style="display:black;">
				<td class="rt" align="right">推荐列表：<label style="color: red;">*</label></td>
				<td><input id="ductCat" name="ductCat"></td>
			</tr>
			<tr class="tr" id="prods" style="display:none;">
				<td class="rt" align="right">单个商品：<label style="color: red;">*</label></td>
				<td><input id="products" name="products"></td>
			</tr>
			<tr class="tr">
				<td class="rt" align="right">显示顺序：<label style="color: red;">*</label></td>
				<td><input type="text" name="indexs" id="indexs" size="4" maxlength="10" class="easyui-numberbox input">降序排列</td>
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
	<div id="index_dialog" title="修改显示顺序">
		<form action="${pageContext.request.contextPath}/recduct/saveIndexs.do" method="post" id="indexsForm">
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
	<div id="upload3_dialog" title="上传图片">
		<form action="${pageContext.request.contextPath}/recduct/upLoadImagesDetails.do" method="post" id="uploadForm3" enctype="multipart/form-data">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="tr">
				<td class="rt" align="right">上传图片：<label style="color: red;">*</label></td>
				<td><input type="file" name="images3" id="images3" size="25"></td>
			</tr>
			<tr class="ji">
				<td class="rt" align="right">跳转类型：<label style="color: red;">*</label></td>
				<td>
					<select id="type3" class="easyui-combobox" name="type3" style="width:140px;">
						<option value="1">推荐列表</option>
						<option value="2">单个商品</option>
					</select>
			    </td>
			</tr>
			<tr class="tr" id="duct3" style="display:black;">
				<td class="rt" align="right">推荐列表：<label style="color: red;">*</label></td>
				<td><input id="ductCat3" name="ductCat3"></td>
			</tr>
			<tr class="tr" id="prods3" style="display:none;">
				<td class="rt" align="right">单个商品：<label style="color: red;">*</label></td>
				<td><input id="products3" name="products3"></td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id3" id="id3">
					<input type="hidden" name="model3" id="model3">
					<input class="bginput" type="button" id="upbuttons3"  value="上传">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butrs3" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>胎教音乐背景图片</title>
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
		var pageSize = Math.round(($("#right", window.parent.document).height() - 160) / 105.5);
		$("#musicbg_grid").datagrid({
			url : "${pageContext.request.contextPath}/musicbg/readPages.do?gid=${week.id}",
			datatype : "json",
			mtype : "POST",
			nowrap : false,
			striped : true,
			fitColumns : true, 
			fit : true,
			pageSize: pageSize,
			pageList: [pageSize,30,40,50],
			pagination : true,
			sortname : "id",
			singleSelect : true,
			rownumbers : true,
			toolbar : [ '-', {
				id : 'btnClearSelections',
				text : "添加图片",
				iconCls : 'icon-add',
				handler : function() {
					$("#div_dialog").dialog({
						title:"添加图片",
						show:"slide",
						resizable:false,
						closed: false,
						resizable:false
					});
				}
			},'-', {
				id : 'btnRedo',
				text : "上  | 下线",
				iconCls : 'icon-redo',
				handler : function() {
					var row = $("#musicbg_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/musicbg/online.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#musicbg_grid").datagrid("reload"); 
				          },
						  error : function(data){
						  	$("#musicbg_grid").datagrid("reload"); 
						  }
					});
				}
			},'-', {
				id : 'btnDel',
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#musicbg_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var ids = row[0].id;
		            var groupId = $("#groupId").val();
		            var delUrl = "${pageContext.request.contextPath}/musicbg/delete.do?t="+new Date().getTime();
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
							          data: "id="+ids+"&groupId="+groupId,//要发送的数据
							          success: function(data){
							          	$("#musicbg_grid").datagrid("reload"); 
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
				title : "背景图片",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return html.replace("imageId", rec.id).replace("[[images1]]", value);
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
					return "	";
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
		$($("#musicbg_grid").datagrid("getPager")).pagination({
			beforePageText : "第",
			afterPageText : "页    共 {pages} 页",
			displayMsg : "当前显示 {from} - {to} 条记录   共 {total} 条记录"
		});
		
		$("#musicbgForm").submit(function(){
			var images=$("#images").val(); 
			if($.trim(images) == ""){ 
				alert("必须上传背景图片！"); 
				$("#images").focus(); 
				return false; 
			}
		});
		$("#butrs").click(function(){
			$("#id").val("");
			$("#images").val("");
			$("#div_dialog").dialog("close");
		});
		$("#butrsClose").click(function(){
			$("#id2").val("");
			$("#images2").val("");
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
					$("#musicbg_grid").datagrid("reload"); 
				}
			});
		});
		
	});
	var html = "<a onClick=\"upLoadImage(11,'imageId')\" href=\"#\"><img src=\"[[images1]]\" width=\"200\" height=\"100\"/></a>";
	function upLoadImage(vas,id){
		$("#id2").val(id);
		$("#upload_dialog").dialog({
			title:"上传图片",
			show:"slide",
			resizable:false,
			closed: false,
			resizable:false
		});
	}
</script>
</head>
<body>
	<table id="musicbg_grid" title="第${week.name}周胎教音乐背景图片管理"></table>
	<div id="delete_div">确定删除所选内容?</div>
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/musicbg/save.do" method="post" id="musicbgForm" enctype="multipart/form-data">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt" align="right">背景图片：<label style="color: red;">*</label></td>
				<td><input type="file" name="images" id="images" size="25"></td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id" id="id"> 
					<input type="hidden" name="gid" id="gid" value="${week.id}"> 
					<input class="bginput" type="submit" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butrs" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
	<div id="upload_dialog" title="上传图片">
		<form action="${pageContext.request.contextPath}/musicbg/upLoadImages.do" method="post" id="uploadForm" enctype="multipart/form-data">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="tr">
				<td class="rt" align="right">上传图片：</td>
				<td><input type="file" name="images2" id="images2" size="25">*必须上传</td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id2" id="id2">
					<input type="hidden" name="gid" id="gid" value="${week.id}"> 
					<input class="bginput" type="button" id="upbuttons"  value="上传">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butrsClose" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>产品产品</title>

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
		var pageSize = Math.round(($("#right", window.parent.document).height() - 160) / 105);
		$("#product_grid").datagrid({
			url : "${pageContext.request.contextPath}/product/readPages.do",
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
				id : 'btnClearSelections',
				text : "添加",
				iconCls : 'icon-add',
				handler : function() {
				    $("#productForm")[0].reset();
				    $("#date").datebox("setValue", new Date().format("yyyy-MM-dd").toString());
					$("#div_dialog").dialog(
					{title:'添加'}
					);
				}
			},'-', {
				id : 'btnDel',
				text : "修改",
				iconCls : 'icon-edit',
				handler : function() {
					var row = $("#product_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/product/edit.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          show:"slide",
				          success: function(data){
				          	$("#id").val(data.id);
				          	$("#content").val(data.description);
				          	$("#gameName").val(data.gameName);
				          	$("#size").val(data.size);
				          	$("#language").val(data.language);
				          	$("#homeUrl").val(data.homeUrl);
				          	$("#androidUrl").val(data.androidUrl);
				          	$("#iosUrl").val(data.iosUrl);
				          	$("#iosYUrl").val(data.iosYUrl);
				          	$("#isRecommend").val(data.isRecommend);
				          	$("#isOnHomePage").val(data.isOnHomePage);
				          	$("#version").val(data.version);
				          	$("#homeOrder").val(data.homeOrder);
				          	$("#listOrder").val(data.listOrder);
				          	$("#type").val(data.type);
				          	$("#date").datebox("setValue", new Date(data.date).format("yyyy-MM-dd").toString());
				          	$("#orders").numberbox("setValue",data.orders);
				          	$("#div_dialog").dialog();
				          },
						  error : function(data){
						  	$("#product_grid").datagrid("reload"); 
						  }
					});
				}
			}, '-', {
				id : 'btnDel',
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#product_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var ids = row[0].id;
		            var delUrl = "${pageContext.request.contextPath}/product/delete.do?t="+new Date().getTime();
		            $("#delete_div").dialog({
						title : "删除提示",
		            	height : 130,
						width : 200,
						modal : true,
						show:"slide",
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
							          		$("#product_grid").datagrid("reload"); 
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
			columns : [ [
			{
				field : "gameName",
				title : "产品名称",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				
			},
			 {
				field : "image",
				title : "logo图片",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return "<img src='"+value+"' width='60' height='60'/>";
				}
			},{
				field : "size",
				title : "大小",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "version",
				title : "版本",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "language",
				title : "语言",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(dateValue,rec){
				    if(dateValue.length>10){
					return dateValue.substr(0,10)+'...';
					}
					return dateValue.substr(0,10);
				}
			},{
				field : "imageEdv",	
				title : "宣传海报",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return "<img src='"+value+"' width='72.5' height='92.25'/>";
				}
			},{
				field : "homeOrder",
				title : "首页顺序",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "listOrder",
				title : "热门排序",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "isRecommend",
				title : "热门推荐",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					if(value == 0){
						return "否";
					}else if(value == 1){
						return "是";
					}			
					return "";
				}
			},{
				field : "isOnHomePage",
				title : "首页推荐",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					if(value == 0){
						return "否";
					}else if(value == 1){
						return "是";
					}			
					return "";
				}
			},{
				field : "type",
				title : "产品类别",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					if(value == 0){
						return "游戏";
					}else if(value == 1){
						return "应用";
					}			
					return "";
				}
			},{
				field : "toMenu",
				title : "截图",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return '<a href="${pageContext.request.contextPath}/screencap/show.do?gid='+rec.id+'">截图</a>'+'&nbsp'+
					'<a href="${pageContext.request.contextPath}/apkapp/show.do?gid='+rec.id+'">应用</a>';
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
			$("#images").val("");
			$("#indexs").val("");
			$("#div_dialog").dialog("close");
		});
		$("#productForm").submit(function(){
			var gamename=$("#gameName").val();
			var homeUrl=$("#homeUrl").val();
			
			if($.trim(gamename) == ""){ 
				alert("请输入产品名称！"); 
				$("#gamename").focus(); 
				return false; 
			}
				if($.trim(homeUrl) == ""){ 
				alert("请输入游戏官网！"); 
				$("#homeurl").focus(); 
				return false; 
			}
 
		});
	});
	

	
</script>
</head>
<body>
	<table id="product_grid" title="产品"></table>
	<div id="delete_div">确定删除所选内容?</div>
	<div id="div_dialog" style="width:1040px;height:600px;padding:10px" >
		<form action="${pageContext.request.contextPath}/product/save.do"
			method="post" id="productForm" enctype="multipart/form-data">
			<table width="100%" border="0" cellpadding="4" cellspacing="1"
				class="table">
				<tr class="ji">
					<td class="rt" colspan="1">产品名称：<label style="color: red;">*</label></td>
					<td class="ji"><input type="text" name="gameName"
						id="gameName" style="width:200px;" maxlength="400" class="input"></td>
					<td class="rt">产品大小：</td>
					<td class="ji"><input type="text" name="size" id="size"
						style="width:200px;" maxlength="400" class="input"></td>
				</tr>
				<tr class="ji">
					<td class="rt">语言：</td>
					<td class="ji"><select id="language" name="language"
						class="" style="width:200px;">
							<option value="中文">中文</option>
							<option value="英文">英文</option>
							<option value="其他">其他</option>
					</select></td>
					<td class="rt">产品版本：</td>
					<td class="ji"><input type="text" name="version" id="version"
						style="width:200px;" maxlength="400" class="input"></td>
						
				</tr>
				<tr class="ji">
					<td class="rt">上线时间：<label style="color: red;">*</label></td>
					<td class="ji"><input class="easyui-datebox" editable="false" name="date" id="date"></input></td>
							<td class="rt" colspan="1">产品类别：<label style="color: red;">*</label></td>
					<td class="ji"><select id="type" name="type"
						class="" style="width:200px;">
							<option value="0">游戏</option>
							<option value="1">应用</option>
					</select></td>
				</tr>
				<tr class="ji">
				<td class="rt">宣传海报：</td>
					<td><input type="file" name="imageEdv" id="imageEdv" size="25"></td>
						<td class="rt">产品logo：</td>
					<td><input type="file" name="images" id="images" size="25"></td>
				</tr>
				<tr class="tr">
					<td class="rt" colspan="1">官网地址：<label style="color: red;">*</label></td>
					<td class="ji" colspan="1"><input type="text" name="homeUrl"
						id="homeUrl" style="width:200px;" maxlength="400" class="input"></td>
					<td class="rt">安卓下载地址：</td>
					<td class="ji"><input type="text" name="androidUrl"
						id="androidUrl" style="width:200px;" maxlength="400" class="input"></td>
				</tr>
				<tr class="ji">
					<td class="rt">越狱下载地址：</td>
					<td class="ji"><input type="text" name="iosYUrl" id="iosYUrl"
						style="width:200px;" maxlength="400" class="input"></td>
					<td class="rt">苹果下载地址：</td>
					<td class="ji"><input type="text" name="iosUrl" id="iosUrl"
						style="width:200px;" maxlength="400" class="input"></td>
				</tr>
				<tr class="ji">
					<td class="rt">是否首页推荐：<label style="color: red;">*</label></td>
					<td class="ji"><select id="isOnHomePage" name="isOnHomePage"
						class="" style="width:200px;">
							<option value="0">否</option>
							<option value="1">是</option>
					</select></td>
					<td class="rt">首页显示顺序：</td>
					<td><input type="text" name="homeOrder" id="homeOrder"
						size="4" maxlength="10" ><label style="color: red;">*</label>降序排列只能填数字</td>
				</tr>

				<tr class="ji">
					<td class="rt">是否热门推荐：<label style="color: red;">*</label></td>
					<td class="ji"><select id="isRecommend" name="isRecommend"
						class="" style="width:200px;">
							<option value="0">否</option>
							<option value="1">是</option>
					</select></td>
					<td class="rt">热门显示顺序：</td>
					<td><input type="text" name="listOrder" id=listOrder size="4"
						maxlength="10" ><label style="color: red;">*</label>降序排列只能填数字</td>
				</tr>
				<tr class="ji">
					<td class="rt" colspan="4"><textarea name="content" id="content" class="easyui-kindeditor" style="width: 100%; height: 180px;" onfocus="if(value=='输入产品描述...'){value=''}" onblur="if (value ==''){value='输入产品描述...'}">输入产品描述...</textarea></td>
				</tr>
				<tr class="tr ct">
					<td colspan="4"><input type="hidden" name="id" id="id">
						<input class="bginput" type="submit" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input
						class="bginput" type="reset" name="Input" id="butrs" value="关闭">
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
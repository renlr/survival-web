<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<title>公司地址</title>

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
		$("#address_grid").datagrid({
			url : "${pageContext.request.contextPath}/address/readPages.do",
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
				id : 'btnDel',
				text : "修改",
				iconCls : 'icon-edit',
				handler : function() {
					var row = $("#address_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/address/edit.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#id").val(data.id);
				          	$("#address").val(data.address);
				          	$("#email").val(data.email);
				          	$("#phone").val(data.phone);
				          	$("#div_dialog").dialog(
				          	
				          	);
				          },
						  error : function(data){
						  	$("#address_grid").datagrid("reload"); 
						  }
					});
				}
			}],
			columns : [ [ {
				field : "address",
				title : "公司地址",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "email",
				title : "邮箱",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
			},{
				field : "phone",
				title : "联系电话",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			}]]
		});
		$("#butrs").click(function(){
			$("#id").val("");
			$("#images").val("");
			$("#indexs").val("");
			$("#div_dialog").dialog("close");
		});
	
	});

</script>


</head>
<body>
	<table id="address_grid" title="公司地址"></table>
	<div id="delete_div">确定删除所选内容?</div> 
	<div id="div_dialog"   >
		<form action="${pageContext.request.contextPath}/address/save.do" method="post" id="addressForm" enctype="multipart/form-data">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
		<tr class="ji">
				<td class="rt">公司地址：</td>
				<td class="ji"><input type="text" name="address" id="address" style="width:200px;" maxlength="400" class="input"></td>
			</tr>
		 
			<tr class="ji">
				<td class="rt">联系电话：</td>
				<td class="ji"><input type="text" name="phone" id="phone" style="width:200px;" maxlength="400" class="input"></td>
			</tr>
			<tr class="ji">
				<td class="rt">公司邮箱：</td>
				<td class="ji"><input type="text" name="email" id="email" style="width:200px;" maxlength="400" class="input"></td>
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
</body>
</html>
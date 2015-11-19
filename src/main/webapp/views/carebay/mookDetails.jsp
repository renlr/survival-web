<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>期刊内容</title>
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
		$("#mookDetails_grid").datagrid({
			url : "${pageContext.request.contextPath}/mookDetails/readPages.do?gid=${mook.id}",
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
				text : "添加期刊内容",
				iconCls : 'icon-add',
				handler : function() {
					$("#div_dialog").dialog({
						title:"添加期刊内容",
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
					var row = $("#mookDetails_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/mookDetails/edit.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#id").val(data.id);
				          	$("#name").numberbox("setValue",data.name);
				          	$("#div_dialog").dialog();
				          },error : function(data){
						  	alert("修改期刊页码失败!");
						  }
					});
				}
			},'-', {
				id : 'btnDel',
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#mookDetails_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var ids = row[0].id;
		            var groupId = $("#groupId").val();
		            var delUrl = "${pageContext.request.contextPath}/mookDetails/delete.do?t="+new Date().getTime();
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
							          	if("success" == data.resultMessage){
								          	$("#mookDetails_grid").datagrid("reload"); 
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
				field : "name",
				title : "内容页码",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					if(null != value){
						return "第"+value+"页";
					}
					return "";
				}
			},{
				field : "content",
				title : "内容编辑",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return '<a href="${pageContext.request.contextPath}/mookDetails/editContent.do?id='+rec.id+'&gid=${mook.id}">内容编辑</a>';
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
		$("#butSubmit").click(function(){
			$("#mookDetailsForm").form("submit",{
				onSubmit:function(){
					var name=$("#name").numberbox("getValue"); 
					if($.trim(name) == ""){ 
						alert("期刊页码必须填写！"); 
						$("#name").focus(); 
						return false; 
					}
				},
				success:function(data){
					$("#id").val("");
					$("#name").numberbox("setValue","");
					$("#div_dialog").dialog("close");
					$("#mookDetails_grid").datagrid("reload");
				}
			});
		});
		
		$("#butClose").click(function(){
			$("#id").val("");
			$("#name").numberbox("setValue","");
			$("#div_dialog").dialog("close");
		});
	});
</script>
</head>
<body>
	<table id="mookDetails_grid" title="第${mook.name}期期刊内容"></table>
	<div id="delete_div">确定删除所选内容?</div>
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/mookDetails/save.do" method="post" id="mookDetailsForm">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt" align="right">期刊页码：</td>
				<td><input type="text" name="name" id="name" size="5" class="easyui-numberbox" data-options="min:0,precision:0">升序排列</td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id" id="id"> 
					<input type="hidden" name="gid" id="gid" value="${mook.id}"> 
					<input class="bginput" type="button" id="butSubmit" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butClose" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>
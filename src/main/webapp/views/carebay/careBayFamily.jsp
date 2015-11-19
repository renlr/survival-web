<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>亲属关系</title>
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
		var pageSize = Math.round(($("#right", window.parent.document).height() - 160) / 23.5);
		$("#family_grid").datagrid({
			url : "${pageContext.request.contextPath}/family/readPages.do",
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
				text : "添加亲属关系",
				iconCls : 'icon-add',
				handler : function() {
					$("#div_dialog").dialog({
						title:"添加亲属关系",
						show:"slide",
						resizable:false,
						closed: false,
						resizable:false
					});
				}
			}, '-', {
				id : 'btnDel',
				text : "修改",
				iconCls : 'icon-edit',
				handler : function() {
					var row = $("#family_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/family/edit.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#id").val(data.id);
				          	$("#appellation").val(data.appellation);
				          	$("#description").val(data.description);
				          	$("#div_dialog").dialog();
				          },
						  error : function(data){
						  	alert("修改系统管理用户失败!");
						  }
					});
				}
			}, '-', {
				id : 'btnDel',
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#family_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var delUrl = "${pageContext.request.contextPath}/family/delete.do?t="+new Date().getTime();
		            $.ajax({
				          type: "get",
				          dataType: "json",
				          url: delUrl,
				          data: "id="+id,//要发送的数据
				          success: function(data){},
						  error : function(data){
						  	$("#family_grid").datagrid('reload'); 
						  }
					});
				}
			}],
			columns : [ [ {
				field : 'appellation',
				title : '亲属称呼',
				width : $(this).width() * 0.2,
				align : 'center',
				sortable : true
			}, {
				field : 'description',
				title : '亲属关系介绍',
				width : $(this).width() * 0.2,
				align : 'center',
				sortable : true
			}, {
				field : 'createDT',
				title : '创建时间',
				width : $(this).width() * 0.2,
				align : 'center',
				sortable : true,
				formatter : function(dateValue,rec){
					return dateValue.toString().toDate().format("yyyy-MM-dd hh:mm:ss");
				}
			} ] ]
		});
		$($("#family_grid").datagrid("getPager")).pagination({
			beforePageText : '第',
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		});
		
		$("#familyForm").submit(function(){
			var appellation=$("#appellation").val(); 
			if($.trim(appellation) == ""){ 
				alert("亲属称呼不能为空！"); 
				$("#appellation").focus(); 
				return false; 
			}
		});
	});
</script>
</head>
<body>
	<table id="family_grid" title="亲属关系"></table>
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/family/save.do" method="post" id="familyForm">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt" align="right">亲属称呼：</td>
				<td><input type="text" name="appellation" id="appellation" size="25" maxlength="50" class="input">*必填</td>
			</tr>
			<tr class="tr">
				<td class="rt" align="right">亲属关系介绍：</td>
				<td>
					<textarea id="description" name="description" rows="6" cols="50" spellcheck="false" autocomplete="off"></textarea>
					<script>
					(function($) {
						$("#description").textbox({
							maxLength: 300
						});
					})(jQuery);
					</script>（最多只能输入300个字）
				</td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id" id="id"> 
					<input class="bginput" type="submit" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" value="重置">
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>
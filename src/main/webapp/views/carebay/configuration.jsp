<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>系统设置</title>
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
		$("#cfg_grid").datagrid({
			url : "${pageContext.request.contextPath}/cfg/readPages.do",
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
				text : "添加",
				iconCls : 'icon-add',
				handler : function() {
					$("#div_dialog").dialog({
						title:"添加系统设置项",
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
					var row = $("#cfg_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/cfg/edit.do?t"+ new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#id").val(data.id);
				          	$("#cfgkeys").val(data.cfgkeys);
				          	$("#version").val(data.version);
				          	$("#cfgvals").val(data.cfgvals);
				          	$("#comments").val(data.comments);
				          	$("#div_dialog").dialog();
				          },
						  error : function(data){
						  	alert("修改系统设置失败!");
						  }
					});
				}
			}, '-', {
				id : 'btnDel',
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#cfg_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var delUrl = "${pageContext.request.contextPath}/cfg/delete.do";
		            $.ajax({
				          type: "get",
				          dataType: "json",
				          url: delUrl,
				          data: "id="+id,//要发送的数据
				          success: function(data){},
						  error : function(data){
						  	$("#cfg_grid").datagrid('reload'); 
						  }
					});
				}
			}],
			columns : [ [ {
				field : 'cfgkeys',
				title : '配置项键',
				width : $(this).width() * 0.2,
				align : 'center',
				sortable : true
			}, {
				field : 'version',
				title : '软件版本',
				width : $(this).width() * 0.2,
				align : 'center',
				sortable : true
			}, {
				field : 'cfgvals',
				title : '配置项值',
				width : $(this).width() * 0.2,
				align : 'center',
				sortable : true
			}, {
				field : 'comments',
				title : '配置描述',
				width : $(this).width() * 0.2,
				align : 'center',
				sortable : true
			}, {
				field : 'createDt',
				title : '创建时间',
				width : $(this).width() * 0.2,
				align : 'center',
				sortable : true,
				formatter : function(dateValue,rec){
					return dateValue.toString().toDate().format("yyyy-MM-dd hh:mm:ss");
				}
			} ] ]
		});
		$($("#cfg_grid").datagrid("getPager")).pagination({
			beforePageText : '第',
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		});
		
		$("#cfgForm").submit(function(){
			var cfgkeys=$("#cfgkeys").val(); 
			var version=$("#version").val(); 
			var cfgvals=$("#cfgvals").val(); 
			if(cfgkeys==""){ 
				alert("key不能为空！"); 
				$("#cfgkeys").focus(); 
				return false; 
			}
			if(version==""){ 
				alert("软件版本必须填写！"); 
				$("#version").focus(); 
				return false; 
			}
			if(cfgvals == ""){
				alert("value不能为空！"); 
				$("#cfgvals").focus(); 
				return false; 
			}
			
		});
		initSel();
	});
	function initSel(){
		$.ajax({
	            type: "get",
	            dataType: "json",
	            url: "${pageContext.request.contextPath}/bus/listBusiness.do",
	            success: function(data){
		            $.each(data, function(i, item){
		           		var optionHtml = "<option value="+ item.id +">"+ item.name +"</option>";
		           		$("#businessId").append(optionHtml); 
				     });
				}
		});
	}
</script>
</head>
<body>
	<table id="cfg_grid" title="系统设置"></table>
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/cfg/save.do" method="post" name="cfgForm" id="cfgForm">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt" align="right">配置项键：</td>
				<td><input type="text" name="cfgkeys" id="cfgkeys" size="25" maxlength="50" class="input">*必填</td>
			</tr>
			<tr class="ji">
				<td class="rt" align="right">软件版本：</td>
				<td><input type="text" name="version" id="version" size="25" maxlength="50" class="input">*必填</td>
			</tr>
			<tr class="tr">
				<td class="rt" align="right">配置项值：</td>
				<td>
					<textarea id="cfgvals" name="cfgvals" rows="6" cols="60" spellcheck="false" autocomplete="off"></textarea>
					<script>
					(function($) {
						$("#comments").textbox({
							maxLength: 2000
						});
					})(jQuery);
					</script>（最多只能输入2000个字）
				</td>
			</tr>
			<tr class="ji">
				<td class="rt" align="right">配置描述：</td>
				<td>
					<textarea id="comments" name="comments" rows="6" cols="60" spellcheck="false" autocomplete="off"></textarea>
					<script>
					(function($) {
						$("#comments").textbox({
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

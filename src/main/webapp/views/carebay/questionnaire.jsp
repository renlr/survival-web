<%@page import="com.baofeng.carebay.util.Constants"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<title>问卷试题</title>
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
		$("#psq_grid").datagrid({
			url : "${pageContext.request.contextPath}/psq/readPages.do?inds=${inds}",
			datatype : "json",
			mtype : "POST",
			fit : true,
			nowrap : false,
			striped : true,
			fitColumns : true, 
			pagination : true,
			rownumbers : true,
			singleSelect : true,
			pageSize: pageSize,
			pageList: [pageSize,30,40,50],
			sortname : 'id',
			toolbar : [ '-', {
				id : 'btnadd',
				text : "添加试题",
				iconCls : 'icon-add',
				handler : function() {
					$("#div_dialog").dialog({
						title:"添加试题",
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
					var row = $("#psq_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/psq/edit.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#id").val(data.id);
				          	$("#subject").val(data.subject);
				          	$("#options").val(data.options);
				          	$("#type").val(data.type);
				          	$("#type").combobox("setValue",data.type);
							$("#indexs").numberbox("setValue",data.indexs); 
				          	$("#div_dialog").dialog();
				          },
						  error : function(data){
						  	$("#psq_grid").datagrid("reload"); 
						  }
					});
				}
			}, '-', {
				id : 'btnDel',
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#psq_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var ids = row[0].id;
		            var delUrl = "${pageContext.request.contextPath}/psq/delete.do?t="+new Date().getTime();
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
										$("#psq_grid").datagrid("reload"); 
								    }
								},
								error : function(data){
									$("#psq_grid").datagrid("reload"); 
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
			},'-', {
				id : 'btnRedo',
				text : "上  | 下线",
				iconCls : 'icon-redo',
				handler : function() {
					var row = $("#psq_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/psq/online.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#psq_grid").datagrid("reload"); 
				          },
						  error : function(data){
						  	$("#psq_grid").datagrid("reload"); 
						  }
					});
				}
			}],
			columns : [[{
				field : "subject",
				title : "试题名称",
				width : $(this).width() * 0.2,
				align : "left",
				sortable : true
			},{
				field : "type",
				title : "试题类型",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					var html = "";
					if(value == 1){
						html = "单选";
					}else if(value == 2){
						html = "多选";
					}else if(value == 3){
						html = "填写";
					}
					return html;
				}
			},{
				field : "options",
				title : "试题答案",
				width : $(this).width() * 0.2,
				align : "left",
				sortable : true
			},{
				field : "chamber",
				title : "所在会所",
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
					return "	";
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
			$("#name").val("");
			$("#div_dialog").dialog("close");
		});
		$("#psqForm").submit(function(){
			var subject = $("#subject").val();
			var options = $("#options").val();
			var type = $("#type").combobox("getValue");
			var indexs=$("#indexs").numberbox("getValue"); 
			if($.trim(type) == ""){
				alert("试题类型不能为空！"); 
				$("#type").focus(); 
				return false;
			}
			if($.trim(subject) == ""){
				alert("试题名称不能为空！"); 
				$("#subject").focus(); 
				return false;
			}
			if(type == 3){
				if($.trim(options) == ""){
					alert("答案选项不能为空！"); 
					$("#options").focus(); 
					return false;
				}
			}
			if(indexs == ""){
				alert("显示顺序必须填写！"); 
				$("#subject").focus(); 
				return false;
			}	
		});
	});
</script>
</head>
<body>
	<table id="psq_grid" title="问卷试题"></table>
	<div id="delete_div">确定删除所选内容吗？</div> 
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/psq/save.do" method="post" id="psqForm">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="tr">
				<td class="rt" align="right">试题类型：<label style="color: red;">*</label></td>
				<td>
					<select id="cc" class="easyui-combobox" id="type" name="type" style="width:200px;">
						<option value="1">单项选择</option>
						<option value="2">多项选择</option>
						<option value="3">文字输入</option>
					</select>
				</td>
			</tr>
			<tr class="ji">
				<td class="rt" align="top">试题名称：<label style="color: red;">*</label></td>
				<td class="ji" colspan="3"><textarea id="subject" name="subject" style="height:60px;width:550px;"></textarea></td>
			</tr>
			<tr class="ji">
				<td class="rt" align="top">答案选项：<label style="color: red;"></label></td>
				<td class="ji" colspan="3"><textarea id="options" name="options" style="height:40px;width:450px;"></textarea>样例："A、满意|B、不满意|C、很差",用英文竖线分割。</td>
			</tr>
			<tr class="tr">
				<td class="rt" align="right">显示顺序：<label style="color: red;">*</label></td>
				<td><input type="text" name="indexs" id="indexs" size="4" maxlength="10" class="easyui-numberbox input">升序排列</td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id" id="id">
					<input type="hidden" name="inds" id="inds" value="${inds}"> 
					<input class="bginput" type="submit" id="buts" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butrs" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>
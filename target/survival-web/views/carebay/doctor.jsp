<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>服务医师</title>
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
	$.extend($.fn.datagrid.methods, {
	    editCell: function(jq,param){
	        return jq.each(function(){
	            var opts = $(this).datagrid("options");
	            var fields = $(this).datagrid("getColumnFields",true).concat($(this).datagrid("getColumnFields"));
	            for(var i=0; i<fields.length; i++){
	                var col = $(this).datagrid("getColumnOption", fields[i]);
	                col.editor1 = col.editor;
	                if (fields[i] != param.field){
	                    col.editor = null;
	                }
	            }
	            $(this).datagrid("beginEdit", param.index);
	            for(var i=0; i<fields.length; i++){
	                var col = $(this).datagrid("getColumnOption", fields[i]);
	                col.editor = col.editor1;
	            }
	        });
	    }
	});
	var values = undefined;
	var fields = undefined;
	var editIndex = undefined;
	function endEditing(){
	    if (editIndex == undefined){return true}
	    if ($("#doctor_grid").datagrid("validateRow", editIndex)){
			$("#doctor_grid").datagrid("endEdit", editIndex);
			var row = $("#doctor_grid").datagrid("getRows");
			var id = row[editIndex].id;
			var $value = row[editIndex][fields];
			if($value != null && values != $value && $value != ""){
				$.ajax({
					type: "post",
					dataType: "json",
					url: "${pageContext.request.contextPath}/doctor/saveEditFields.do?t="+new Date().getTime(),
					data: "id="+id+"&field="+fields+"&value="+$value,
					success: function(data){
				    	if("success" == data.resultMessage){}
				    },
					error : function(data){}
				});				
			}
			values = undefined;
			fields = undefined;
	        editIndex = undefined;
	        return true;
	    } else {
	        return false;
	    }
	}
	function clickCell(index, field,value){
	    if (endEditing()){
	        $("#doctor_grid").datagrid("selectRow", index).datagrid("editCell", {index:index,field:field});
	        values = value;
	        fields = field;
	        editIndex = index;
	    }
	}
	$(document).ready(function() {
		var pageSize = Math.round(($("#right", window.parent.document).height() - 160) / 23.5);
		$("#doctor_grid").datagrid({
			url : "${pageContext.request.contextPath}/doctor/readPages.do",
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
			onClickCell : function(index,field,value){
				clickCell(index,field,value);
			},
			toolbar : [ '-', {
				id : "btnClearSelections",
				text : "添加服务医师",
				iconCls : 'icon-add',
				handler : function() {
					$("#div_dialog").dialog({
						title:"添加服务医师",
						show:"slide",
						resizable:false,
						closed: false,
						resizable:false
					});
				}
			},'-', {
				id : 'btnDel',
				text : "上  | 下线",
				iconCls : 'icon-redo',
				handler : function() {
					var row = $("#doctor_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/doctor/online.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
							if("success" == data.resultMessage){
								$("#doctor_grid").datagrid("reload"); 
							}			          	
				          },error : function(data){
						  	alert("上下线操作失败!");
						  }
					});
				}
			},'-', {
				id : 'btnDel',
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#doctor_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var ids = row[0].id;
		            var delUrl = "${pageContext.request.contextPath}/doctor/delete.do?t="+new Date().getTime();
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
							          		$("#doctor_grid").datagrid("reload"); 
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
				field : "doctorUser",
				title : "医师帐号",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "doctorPWD",
				title : "医师密码",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				editor : "text",
				formatter : function(value,rec){
					var html = "";
					if(value != null){
						for(var i=0;i<value.length;i++){
							html += "*";
						}
					}
					return html;
				}
			},{
				field : "doctorNickname",
				title : "医师昵称",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				editor : "text"
			},{
				field : "level",
				title : "医师等级",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				editor : {
					type : "combobox",
					options : {
						data : [{"value" : "0","text" : "普通医师"},
								{"value" : "1","text" : "会员医师"},
								{"value" : "2","text" : "主任医师"},
								],
						valueField : "value",
						textField : "text"
					},
					panelHeight : "auto"
				},
				formatter : function(value,rec){
					if(value == 0){
						return "普通医师";
					}else if(value == 1){
						return "会员医师";
					}else if(value == 2){
						return "主任医师";
					}
					return "";
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
			},{
				field : "describes",
				title : "服务描述",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				editor : "textarea"
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
		$($("#doctor_grid").datagrid("getPager")).pagination({
			beforePageText : "第",
			afterPageText : "页    共 {pages} 页",
			displayMsg : "当前显示 {from} - {to} 条记录   共 {total} 条记录"
		});
		$("#butClose").click(function(){
			$("#id").val("");
			$("#method").val("");
			$("#div_dialog").dialog("close");
		});
		$("#butSubmit").click(function(){
			$("#doctorForm").form("submit",{
				onSubmit:function(){
					var doctorUser = $("#doctorUser").val(); 
					var doctorPWD = $("#doctorPWD").val(); 
					var doctorNickname = $("#doctorNickname").val(); 
					var describes = $("#describes").val(); 
					if($.trim(doctorUser) == ""){ 
						alert("医师帐号不能为空！"); 
						$("#doctorUser").focus(); 
						return false; 
					}
					if($.trim(doctorPWD) == ""){ 
						alert("帐号密码不能为空！"); 
						$("#doctorPWD").focus(); 
						return false; 
					}
					if($.trim(doctorNickname) == ""){ 
						alert("医师昵称不能为空！"); 
						$("#doctorNickname").focus(); 
						return false; 
					}
					if($.trim(describes) == ""){ 
						alert("服务描述不能为空！"); 
						$("#describes").focus(); 
						return false; 
					}
				},
				success:function(data){
					$("#id").val("");
					$("#doctorUser").val(""); 
					$("#doctorPWD").val(""); 
					$("#doctorNickname").val(""); 
					$("#describes").val(""); 
					$("#div_dialog").dialog("close");
					$("#doctor_grid").datagrid("reload"); 
				}
			});
		});
	});
</script>
</head>
<body>
	<table id="doctor_grid" title="服务医师"></table>
	<div id="delete_div">确定删除所选内容?</div> 
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/doctor/save.do" method="post" id="doctorForm">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt" align="right">医师帐号：<label style="color: red;">*</label></td>
				<td><input class="input" type="text" name="doctorUser" id="doctorUser" data-options="required:false"></input></td>
			</tr>
			<tr class="tr">
				<td class="rt" align="right">帐号密码：<label style="color: red;">*</label></td>
				<td><input class="input" type="password" name="doctorPWD" id="doctorPWD" data-options="required:false"></input></td>
			</tr>
			<tr class="ji">
				<td class="rt" align="right">医师昵称：<label style="color: red;">*</label></td>
				<td><input class="input" type="text" name="doctorNickname" id="doctorNickname" data-options="required:false"></input></td>
			</tr>
			<tr class="ji">
				<td class="rt" align="right">医师等级：<label style="color: red;">*</label></td>
				<td>
					<select id="level" class="easyui-combobox" name="level" style="width:140px;">
						<option value="0">普通医师</option>
						<option value="1">会员医师</option>
						<option value="2">主任医师</option>
					</select>
				</td>
			</tr>
			<tr class="tr">
				<td class="rt" align="right">服务描述：<label style="color: red;">*</label></td>
				<td class="ji" colspan="3"><textarea id="describes" name="describes" style="height:100px;width:300px;"></textarea></td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id" id="id"> 
					<input class="bginput" type="button" id="butSubmit" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="button" name="Input" id="butClose" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>
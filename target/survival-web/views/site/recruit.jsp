<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>招聘信息</title>
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
		$("#recruit_grid").datagrid({
			url : "${pageContext.request.contextPath}/recruit/readPages.do",
			datatype : "json",
			mtype : "POST",
			nowrap : false,
			striped : true,
			fitColumns : true, 
			fit : true,
			pageSize: 30,
			pageList: [pageSize,30,40,50],
			pagination : true,
			sortname : 'id',
			singleSelect : true,
			rownumbers : true,
			toolbar : [ '-', {
				id : 'btnClearSelections',
				text : "新增",
				iconCls : 'icon-add',
				handler : function() {
					$("#div_dialog").dialog({
						title:"添加",
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
					var row = $("#recruit_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/recruit/edit.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#id").val(data.id);
				          	$("#job").val(data.job);
				          	$("#type").val(data.type);
				          	$("#place").val(data.place);
				          	$("#nature").val(data.nature);
				          	$("#mail").val(data.mail);
				          	$("#duty").val(data.duty);
				          	$("#demand").val(data.demand);
				          	$("#number").numberbox("setValue",data.number);
				          	$("#div_dialog").dialog();
				          },
						  error : function(data){
						  	$("#recruit_grid").datagrid("reload"); 
						  }
					});
				}
			}, '-', {
				id : 'btnDel',
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#recruit_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var ids = row[0].id;
		            var delUrl = "${pageContext.request.contextPath}/recruit/delete.do?t="+new Date().getTime();
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
							          		$("#recruit_grid").datagrid("reload"); 
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
				field : "job",
				title : "职位",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
			},{
				field : "type",
				title : "类别",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter:function(value,rec){
					var htmls = "";
					if(value != null && value != ""){
						if(0 == value){
							htmls = "程序类";
						}else if(1 == value){
							htmls = "策划类";
						}
						else if(2 == value){
							htmls = "美术类";
						}
						else if(3 == value){
							htmls = "运营类";
						}
						else if(4 == value){
							htmls = "商务类";
						}
					}
					return htmls;
				}
			},{
				field : "nature",
				title : "工作性质",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				
			},{
				field : "number",
				title : "招聘人数",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "duty",
				title : "岗位职责",
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
				field : "demand",
				title : "岗位要求",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(dateValue,rec){
				    if(dateValue.length>10){
					return dateValue.substr(0,10)+'...';
					}
					return dateValue.substr(0,10);
				}
			}
			,{
				field : "mail",
				title : "招聘邮箱",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			}
			,{
				field : "createDT",
				title : "创建时间",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(dateValue,rec){
					return dateValue.toString().toDate().format("yyyy-MM-dd hh:mm:ss");
				}
			}
			]]
		});
		$("#butrs").click(function(){
			$("#id").val("");
			$("#images").val("");
			$("#indexs").val("");
			$("#div_dialog").dialog("close");
		});
		$("#recruitForm").submit(function(){
			var job=$("#job").val(); 
			var mail=$("#mail").val(); 
			if($.trim(job) == ""){ 
				alert("请输入职位"); 
				$("#job").focus(); 
				return false; 
			}
			if($.trim(mail) == ""){ 
				alert("请输入邮箱"); 
				$("#orders").focus(); 
				return false; 
			}
		});
	});
</script>
</head>
<body>
	<table id="recruit_grid" title="招聘信息"></table>
	<div id="delete_div">确定删除所选内容?</div> 
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/recruit/save.do" method="post" id="recruitForm" enctype="multipart/form-data">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt">职位：<label style="color: red;">*</label></td>
				<td class="ji"><input type="text" name="job" id="job" style="width:200px;" maxlength="400" class="input"></td>
				<td class="rt">所属类别：<label style="color: red;">*</label></td>
				<td class="ji">
					<select id="type" name="type"  style="width:200px;">
					    <option value="0">程序类</option>
					    <option value="1">策划类</option>
					    <option value="2">美术类</option>
					    <option value="3">运营类</option>
					    <option value="4">商务类</option>
					</select>
				</td>
			</tr>
			<tr class="ji">
				<td class="rt">工作地点：</td>
				<td class="ji"><input type="text" name="place" id="place" style="width:200px;" maxlength="400" class="input"></td>
		        <td class="rt">工作性质：<label style="color: red;">*</label></td>
					<td class="ji">
					<select id="nature" name="nature" style="width:200px;">
					    <option value="全职">全职</option>
					    <option value="兼职">兼职</option>
					</select>
				</td>
			</tr>
			<tr class="ji">
				<td class="rt">招聘人数：</td>
				<td class="ji"><input type="text" name="number" id="number" style="width:200px;" maxlength="400" class="easyui-numberbox input"></td>
			<td class="rt">邮箱：<label style="color: red;">*</label></td>
				<td><input type="text" name="mail" id="mail" style="width:200px;" maxlength="400" class="input"></td>
			</tr>
			<tr class="ji"> 
				<td class="ji" colspan="4">
					<textarea rows="8" cols="70" name="duty" id="duty" onfocus="if(value=='输入岗位职责...'){value=''}" onblur="if (value ==''){value='输入岗位职责...'}">输入岗位职责...</textarea>
				</td>
			</tr>
			<tr class="ji">
					<td class="ji" colspan="4">
					<textarea  rows="8" cols="70" name="demand" id="demand" onfocus="if(value=='输入岗位要求...'){value=''}" onblur="if (value ==''){value='输入岗位要求...'}">输入岗位要求...</textarea>
				    </td>		
			</tr>
			<tr class="tr ct">
				<td colspan="4">
					<input type="hidden" name="id" id="id">
					<input class="bginput" type="submit" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butrs" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>